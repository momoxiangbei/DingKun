package com.mmxb.dingkun.blockcanary;

import android.os.Environment;
import android.os.Looper;

import com.mmxb.dingkun.blockcanary.info.BlockInfo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class BlockCanaryInternals {
    LooperMonitor monitor;
    StackSampler stackSampler;
    CpuSampler cpuSampler;

    private static BlockCanaryInternals sInstance;
    private static BlockCanaryContext sContext;

    private List<BlockInterceptor> mInterceptorChain = new LinkedList<>();

    public BlockCanaryInternals() {
        stackSampler = new StackSampler(Looper.getMainLooper().getThread(), sContext.provideDumpInterval());
        cpuSampler = new CpuSampler(sContext.provideDumpInterval());
        setMonitor(new LooperMonitor(new LooperMonitor.BlockListener() {
            @Override
            public void onBlockEvent(long realTimeStart, long realTimeEnd, long threadTimeStart, long threadTimeEnd) {
                ArrayList<String> threadStackEntries = stackSampler.getThreadStackEntries(realTimeStart, realTimeEnd);
                if (!threadStackEntries.isEmpty()) {
                    BlockInfo blockInfo = BlockInfo.newInstance()
                            .setMainThreadTimeCost(realTimeStart, realTimeEnd, threadTimeStart, threadTimeEnd)
                            .setCpuBusyFlag(cpuSampler.isCpuBusy(realTimeStart, realTimeEnd))
                            .setRecentCpuRate(cpuSampler.getCpuRateInfo())
                            .setThreadStackEntries(threadStackEntries)
                            .flushString();
                    LogWriter.save(blockInfo.toString());

                    if (mInterceptorChain.size() != 0) {
                        for (BlockInterceptor interceptor : mInterceptorChain) {
                            interceptor.onBlock(getContext().provideContext(), blockInfo);
                        }
                    }
                }
            }
        }, getContext().provideBlockThreshold(), getContext().stopWhenDebugging()));
        LogWriter.cleanObsolete();
    }

    static BlockCanaryInternals getInstance() {
        if (sInstance == null) {
            synchronized (BlockCanaryInternals.class) {
                if (sInstance == null) {
                    sInstance = new BlockCanaryInternals();
                }
            }
        }
        return sInstance;
    }


    public static void setContext(BlockCanaryContext context) {
        sContext = context;
    }

    public static BlockCanaryContext getContext() {
        return sContext;
    }

    void addBlockInterceptor(BlockInterceptor blockInterceptor) {
        mInterceptorChain.add(blockInterceptor);
    }

    private void setMonitor(LooperMonitor looperPrinter) {
        monitor = looperPrinter;
    }

    long getSampleDelay() {
        return (long) (BlockCanaryInternals.getContext().provideBlockThreshold() * 0.8f);
    }

    static String getPath() {
        String state = Environment.getExternalStorageState();
        String logPath = BlockCanaryInternals.getContext()
                == null ? "" : BlockCanaryInternals.getContext().providePath();

        if (Environment.MEDIA_MOUNTED.equals(state)
                && Environment.getExternalStorageDirectory().canWrite()) {
            return Environment.getExternalStorageDirectory().getPath() + logPath;
        }
        return getContext().provideContext().getFilesDir() + BlockCanaryInternals.getContext().providePath();
    }

    static File detectedBlockDirectory() {
        File directory = new File(getPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    public static File[] getLogFiles() {
        File f = detectedBlockDirectory();
        if (f.exists() && f.isDirectory()) {
            return f.listFiles(new BlockLogFileFilter());
        }
        return null;
    }

    private static class BlockLogFileFilter implements FilenameFilter {

        private String TYPE = ".log";

        BlockLogFileFilter() {

        }

        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(TYPE);
        }
    }
}

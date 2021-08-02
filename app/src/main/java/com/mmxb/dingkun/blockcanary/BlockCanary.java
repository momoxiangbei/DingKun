package com.mmxb.dingkun.blockcanary;

import android.content.Context;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.mmxb.dingkun.blockcanary.uploader.Uploader;

public final class BlockCanary {

    private static final String TAG = "BlockCanary";

    private static BlockCanary sInstance;
    private BlockCanaryInternals mBlockCanaryCore;
    private boolean mMonitorStarted = false;

    private BlockCanary() {
        BlockCanaryInternals.setContext(BlockCanaryContext.get());
        mBlockCanaryCore = BlockCanaryInternals.getInstance();
        mBlockCanaryCore.addBlockInterceptor(BlockCanaryContext.get());
    }

    public static BlockCanary install(Context context, BlockCanaryContext blockCanaryContext) {
        BlockCanaryContext.init(context, blockCanaryContext);
        return get();
    }

    public static BlockCanary get() {
        if (sInstance == null) {
            synchronized (BlockCanary.class) {
                if (sInstance == null) {
                    sInstance = new BlockCanary();
                }
            }
        }
        return sInstance;
    }

    public void start() {
        if (!mMonitorStarted) {
            mMonitorStarted = true;
            Looper.getMainLooper().setMessageLogging(mBlockCanaryCore.monitor);
        }
    }

    public void stop() {
        if (mMonitorStarted) {
            mMonitorStarted = false;
            Looper.getMainLooper().setMessageLogging(null);
            mBlockCanaryCore.stackSampler.stop();
            mBlockCanaryCore.cpuSampler.stop();
        }
    }

    public void upload() {
        Uploader.zipAndUpload();
    }

    /**
     * Record monitor start time to preference, you may use it when after push which tells start
     * BlockCanary.
     */
    public void recordStartTime() {
        PreferenceManager.getDefaultSharedPreferences(BlockCanaryContext.get().provideContext())
                .edit()
                .putLong("BlockCanary_StartTime", System.currentTimeMillis())
                .commit();
    }

    /**
     * Is monitor duration end, compute from recordStartTime end provideMonitorDuration.
     *
     * @return true if ended
     */
    public boolean isMonitorDurationEnd() {
        long startTime =
                PreferenceManager.getDefaultSharedPreferences(BlockCanaryContext.get().provideContext())
                        .getLong("BlockCanary_StartTime", 0);
        return startTime != 0 && System.currentTimeMillis() - startTime >
                BlockCanaryContext.get().provideMonitorDuration() * 3600 * 1000;
    }
}

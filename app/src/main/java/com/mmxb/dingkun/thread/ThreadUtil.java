package com.mmxb.dingkun.thread;

import android.util.Log;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by mmxb on 8/2/21.
 */
public class ThreadUtil {

    private static final String TAG = "ThreadUtil";

    public static void getAllThread() {
        Log.d(TAG, "getAllThread start--------------");
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        Log.d(TAG, "线程总数：" + allStackTraces.size());
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : allStackTraces.entrySet()) {
            Thread thread = (Thread) stackTrace.getKey();
            logThread(TAG, thread);
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            String strStackTrace = "堆栈：";
            for (StackTraceElement stackTraceElement : stack) {
                strStackTrace += stackTraceElement.toString() + "\n";
            }
            Log.d(TAG, strStackTrace);
        }
        Log.d(TAG, "getAllThread end--------------");
    }

    public static void logThread(String tag, Thread thread) {
        Log.e(tag, "\n  name=" + thread.getName()
                + "\n  id=" + thread.getId()
                + "\n  state=" + thread.getState()
                + "\n  priority=" + thread.getPriority() + "\n  group=" + thread.getThreadGroup()
                + "\n  stackTrace" + Arrays.toString(thread.getStackTrace()));
    }
}

package com.mmxb.dingkun.crash;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mmxb.dingkun.thread.ThreadUtil;

/**
 * Created by mmxb on 8/2/21.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private final String TAG = "CrashHandler";
    private static CrashHandler crashHandler = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return crashHandler;
    }

    public void init() {
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        Log.d(TAG, "uncaughtException: start ---------------");
        ThreadUtil.logThread(TAG, thread);
        Log.d(TAG, "uncaughtException: " + Log.getStackTraceString(throwable));  // getStackTraceString可以打印crash完整链路
        Log.d(TAG, "uncaughtException: end ---------------");
        if (mDefaultExceptionHandler != null) {
            mDefaultExceptionHandler.uncaughtException(thread, throwable);
        }
        Log.d(TAG, "uncaughtException: end2 ---------------");  // 这个log无法打印，需要在uncaughtException方法调用之前做一些操作
    }
}

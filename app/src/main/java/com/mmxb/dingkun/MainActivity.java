package com.mmxb.dingkun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mmxb.dingkun.blockcanary.BlockCanary;
import com.mmxb.dingkun.crash.CrashHandler;
import com.mmxb.dingkun.crash.CrashHandler2;
import com.mmxb.dingkun.demo.MyBlockCanaryContext;
import com.mmxb.dingkun.router.ActivityLauncher;
import com.mmxb.dingkun.router.TestCallbackActivity;
import com.mmxb.dingkun.ui.bottomsheet.BottomSheetActivity;
import com.mmxb.dingkun.ui.motionevent.MotionEventActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
//        CrashHandler.getInstance().init();
//        CrashHandler2.getInstance().init();
//        View v = null;
//        v.getWidth();
        HandlerThread mHandlerThread = new HandlerThread("check-message-coming");
        mHandlerThread.start();
        Handler handler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    public void BottomSheetActivity(View view) {
        startActivity(new Intent(this, BottomSheetActivity.class));
    }

    public void MotionEventActivity(View view) {
        startActivity(new Intent(this, MotionEventActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ActivityLauncher(View view) {
        final Intent intent = new Intent(this, TestCallbackActivity.class);
        ActivityLauncher.init(this).startActivityForResult(intent, new ActivityLauncher.Callback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                Log.d(TAG, "onActivityResult: " + resultCode);
            }
        });
    }

    public void BlockCanary(View view) {
        BlockCanary.install(this, new MyBlockCanaryContext());
        BlockCanary.get().start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 开始卡顿模拟");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "run: 模拟卡顿结束");
                Toast.makeText(MainActivity.this, "模拟卡顿结束", Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }
}
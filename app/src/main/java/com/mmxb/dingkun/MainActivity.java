package com.mmxb.dingkun;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
}
package com.mmxb.dingkun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mmxb.dingkun.ui.bottomsheet.BottomSheetActivity;
import com.mmxb.dingkun.ui.motionevent.MotionEventActivity;

public class MainActivity extends AppCompatActivity {

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
}
package com.mmxb.dingkun.router;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mmxb.dingkun.R;

/**
 * Created by mmxb on 5/28/21.
 */
public class TestCallbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_callback);
    }

    public void click(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", "test");
        intent.putExtra("value", 10);
        setResult(1024, intent);
        finish();
    }
}

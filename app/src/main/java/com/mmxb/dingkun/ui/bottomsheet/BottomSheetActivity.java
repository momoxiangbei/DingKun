package com.mmxb.dingkun.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mmxb.dingkun.R;

/**
 * Created by mmxb on 5/17/21.
 * <p>
 * note：
 * BottomSheet最大高度是一屏幕，超过一屏幕距离时底部会显示不出来
 */
public class BottomSheetActivity extends AppCompatActivity {
    private FrameLayout mRootView;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        mRootView = findViewById(R.id.root_view);
        initBottomSheetView();
    }

    private void initBottomSheetView() {
        mRootView.addView(LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, null));
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_container));
        mBottomSheetBehavior.setPeekHeight(300, true);
        mBottomSheetBehavior.setHideable(false);

        // click test
        findViewById(R.id.test_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetActivity.this, "bottomClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void topButton(View view) {
        Toast.makeText(this, "topClick", Toast.LENGTH_SHORT).show();
    }
}

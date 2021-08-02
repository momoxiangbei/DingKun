package com.mmxb.dingkun.demo;

import android.content.Context;
import android.util.Log;

import com.mmxb.dingkun.blockcanary.BlockCanaryContext;
import com.mmxb.dingkun.blockcanary.info.BlockInfo;

/**
 * Created by mmxb on 7/30/21.
 */
public class MyBlockCanaryContext extends BlockCanaryContext {
    @Override
    public void onBlock(Context context, BlockInfo blockInfo) {
        super.onBlock(context, blockInfo);
        Log.d("momotest", "onBlock: " + blockInfo.toString());
    }

    @Override
    public boolean stopWhenDebugging() {
        return false;
    }
}

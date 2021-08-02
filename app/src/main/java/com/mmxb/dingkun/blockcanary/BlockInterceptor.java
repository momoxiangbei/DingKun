package com.mmxb.dingkun.blockcanary;

import android.content.Context;

import com.mmxb.dingkun.blockcanary.info.BlockInfo;

interface BlockInterceptor {
    void onBlock(Context context, BlockInfo blockInfo);
}

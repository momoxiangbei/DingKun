package com.mmxb.dingkun.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Created by mmxb on 5/27/21.
 */
public class ActivityLauncher {
    private static final String TAG = "ActivityLauncher";
    private RouterFragment mRouterFragment;
    private RouterFragmentV4 mRouterFragmentV4;

    private ActivityLauncher(Context context) {
        if (context instanceof FragmentActivity) {
            mRouterFragmentV4 = getRouterFragmentV4((FragmentActivity) context);
        } else if (context instanceof Activity) {
            mRouterFragment = getRouterFragment((Activity) context);
        }
    }

    public static ActivityLauncher init(Fragment fragment) {
        return new ActivityLauncher(fragment.getActivity());
    }

    public static ActivityLauncher init(FragmentActivity activity) {
        return new ActivityLauncher(activity);
    }

    public static ActivityLauncher init(Activity activity) {
        return new ActivityLauncher(activity);
    }

    private RouterFragmentV4 getRouterFragmentV4(FragmentActivity activity) {
        RouterFragmentV4 routerFragmentV4 = (RouterFragmentV4) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (routerFragmentV4 == null) {
            routerFragmentV4 = RouterFragmentV4.newInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().add(routerFragmentV4, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return routerFragmentV4;
    }

    private RouterFragment getRouterFragment(Activity activity) {
        RouterFragment routerFragment = (RouterFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (routerFragment == null) {
            routerFragment = RouterFragment.newInstance();
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().add(routerFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return routerFragment;
    }

    public void startActivityForResult(Intent intent, Callback callback) {
        if (mRouterFragmentV4 != null) {
            mRouterFragmentV4.startActivityForResult(intent, callback);
        } else if (mRouterFragment != null) {
            mRouterFragment.startActivityForResult(intent, callback);
        } else {
            throw new RuntimeException("ActivityLauncher init not called");
        }
    }

    public interface Callback {
        void onActivityResult(int resultCode, Intent data);
    }
}

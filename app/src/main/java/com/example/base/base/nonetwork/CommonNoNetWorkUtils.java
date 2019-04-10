package com.example.base.base.nonetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Panpan Zhang
 */
public class CommonNoNetWorkUtils {

    private CommonNoNetWorkUtils() {
    }

    private static class Holder {
        private static final CommonNoNetWorkUtils INSTANCE = new CommonNoNetWorkUtils();
    }

    public static CommonNoNetWorkUtils getInstance() {
        return Holder.INSTANCE;
    }

    public CommonNoNetWorkFragment getNoNetworkFragment(FragmentManager fragmentManager, CommonNoNetWorkFragment fragment) {
        if (fragment != null) {
            if (!removeNetworkFragment(fragmentManager, fragment)) {
                return fragment;
            }
        }
        return new CommonNoNetWorkFragment();
    }

    public boolean removeNetworkFragment(FragmentManager fragmentManager, CommonNoNetWorkFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.remove(fragment);
            transaction.commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    /**
     * 显示无网络 fragment
     *
     * @param contentId 内容Id
     * @param fragmentManager fragment 管理类
     * @param fragment 无网络fragment
     */
    public void showNoNetWorkFragment(int contentId, FragmentManager fragmentManager, CommonNoNetWorkFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded() || fragment.getFragmentManager() != null) {
            transaction.show(fragment);
        } else {
            transaction.add(contentId, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏无网络fragment
     *
     * @param fragmentManager fragment 管理类
     * @param fragment 无网络fragment
     */
    public void hideNoNetWorkFragment(FragmentManager fragmentManager, CommonNoNetWorkFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded() || fragment.getFragmentManager() != null) {
            transaction.hide(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 判断是否有网络
     */
    public static boolean isConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }
    }
}

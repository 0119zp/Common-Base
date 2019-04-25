package com.example.base.base;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;
import com.example.base.R;
import com.example.base.base.loading.CommonLoading;
import com.example.base.base.nonetwork.CommonNoNetWorkFragment;
import com.example.base.base.nonetwork.CommonNoNetWorkUtils;
import com.example.base.utils.CommonToast;

/**
 * @author zpan
 * @date 2019/4/8 13:50
 *
 * description: Fragment 基类
 */
public abstract class BaseFragment extends BaseZpFragment {

    private CommonNoNetWorkFragment mNoNetWorkFragment;
    private CommonLoading mCommonLoading;
    private View contentView;

    @Override
    protected void buildCreateRootView(View rootView) {
        super.buildCreateRootView(rootView);
        mCommonLoading = rootView.findViewById(R.id.cl_common_loading);
        FrameLayout commonContent = rootView.findViewById(R.id.fl_common_content);
        contentView = LayoutInflater.from(mActivity).inflate(exInitFragmentLayout(), null);
        commonContent.addView(contentView);
        refreshInitView();
    }

    @Override
    public void refreshInitView() {
        if (!CommonNoNetWorkUtils.isConnected(mActivity)) {
            CommonNoNetWorkFragment.OnNoNetWorkCallBack callBack = getNoNetWorkCallback();
            if (callBack != null) {
                showNotNetwork(getNoNetWorkCallback());
                return;
            }
        }
        if (contentView != null) {
            contentView.setVisibility(View.VISIBLE);
        }
        if (mNoNetWorkFragment != null) {
            CommonNoNetWorkUtils.getInstance().hideNoNetWorkFragment(mActivity.getSupportFragmentManager(), mNoNetWorkFragment);
        }
        exInitFragmentView(contentView);
    }

    @Override
    public void showCommonLoading() {
        if (mCommonLoading.getVisibility() == View.GONE) {
            mCommonLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hindCommonLoading() {
        if (mCommonLoading.getVisibility() == View.VISIBLE) {
            mCommonLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCommonToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        CommonToast.getInstance(mActivity).setText(msg).show();
    }

    @Override
    public void showCommonToast(int msgId) {
        if (msgId <= 0) {
            return;
        }
        CommonToast.getInstance(mActivity).setTextId(msgId).show();
    }

    protected CommonNoNetWorkFragment.OnNoNetWorkCallBack getNoNetWorkCallback() {
        return new CommonNoNetWorkFragment.OnNoNetWorkCallBack() {
            @Override
            public void clickRefresh() {
                refreshInitView();
                refreshNoNetWork();
            }
        };
    }

    @Override
    public void refreshNoNetWork() {

    }

    private void showNotNetwork(CommonNoNetWorkFragment.OnNoNetWorkCallBack callback) {
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        mNoNetWorkFragment = CommonNoNetWorkUtils.getInstance().getNoNetworkFragment(fragmentManager, mNoNetWorkFragment);
        mNoNetWorkFragment.setNoNetWorkCallBack(callback);
        CommonNoNetWorkUtils.getInstance().showNoNetWorkFragment(R.id.fl_common_content, fragmentManager, mNoNetWorkFragment);
    }
}

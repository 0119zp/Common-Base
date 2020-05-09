package com.zpan.base.base;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.zpan.base.R;
import com.zpan.base.base.loading.CommonLoading;
import com.zpan.base.utils.CommonToast;

/**
 * @author zpan
 * @date 2019/4/8 13:50
 *
 * description: Fragment 基类
 */
public abstract class BaseFragment extends BaseZpFragment {

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
}

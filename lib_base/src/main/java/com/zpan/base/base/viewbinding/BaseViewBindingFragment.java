package com.zpan.base.base.viewbinding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;
import com.zpan.base.R;
import com.zpan.base.base.nonetwork.CommonNoNetWorkFragment;
import com.zpan.base.base.nonetwork.CommonNoNetWorkUtils;
import com.zpan.base.databinding.CommonFragmentBaseViewBinding;
import com.zpan.base.utils.CommonToast;
import com.zpan.base.utils.FragmentUtils;

/**
 * @author zpan
 * @date 2020/5/9 2:36 PM
 *
 * description: fragment 基类
 */
public abstract class BaseViewBindingFragment<T extends ViewBinding> extends Fragment implements View.OnAttachStateChangeListener {

    public T viewBinding;

    protected BaseViewBindingActivity mActivity;
    private CommonNoNetWorkFragment mNoNetWorkFragment;
    private CommonFragmentBaseViewBinding mBaseBinding;
    private View mContentView;

    /**
     * Fragment首次创建
     */
    private boolean isOnCreateView = false;
    /**
     * ParentActivity是否可见
     */
    private boolean mParentActivityVisible = false;
    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    private boolean mVisible = false;

    /**
     * 视图绑定
     *
     * @param inflater LayoutInflater
     * @param parent 父布局
     * @return XML 绑定类
     */
    abstract T exViewBinding(LayoutInflater inflater, ViewGroup parent);

    /**
     * 初始化操作，包括页面控件初始化，数据初始化，等等...
     *
     * @param contentView 内容视图
     */
    abstract void exInitFragment(View contentView);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (BaseViewBindingActivity) context;
        checkVisibility(true);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseViewBindingActivity) activity;
        checkVisibility(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        checkVisibility(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseBinding = CommonFragmentBaseViewBinding.inflate(inflater, container, false);
        initDefaultView(inflater, container);
        isOnCreateView = true;
        return mBaseBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.addOnAttachStateChangeListener(this);
    }

    private void initDefaultView(LayoutInflater inflater, ViewGroup container) {
        viewBinding = exViewBinding(inflater, container);
        mContentView = viewBinding.getRoot();
        mBaseBinding.flCommonContent.addView(mContentView);
        refreshInitView();
    }

    private void refreshInitView() {

        if (mContentView != null) {
            mContentView.setVisibility(View.VISIBLE);
        }
        if (mNoNetWorkFragment != null) {
            CommonNoNetWorkUtils.getInstance().hideNoNetWorkFragment(mActivity.getSupportFragmentManager(), mNoNetWorkFragment);
        }
        exInitFragment(mContentView);
    }

    public void showCommonLoading() {
        if (mBaseBinding.clCommonLoading.getVisibility() == View.GONE) {
            mBaseBinding.clCommonLoading.setVisibility(View.VISIBLE);
        }
    }

    public void hideCommonLoading() {
        if (mBaseBinding.clCommonLoading.getVisibility() == View.VISIBLE) {
            mBaseBinding.clCommonLoading.setVisibility(View.GONE);
        }
    }

    public void showCommonToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        showCommonToast(msg, 0);
    }

    public void showCommonToast(String msg, int iconId) {
        CommonToast commonToast = new CommonToast(getActivity());
        commonToast.setText(msg).setTextId(iconId).show();
    }

    protected void refreshNoNetWork() {

    }

    private CommonNoNetWorkFragment.OnNoNetWorkCallBack getNoNetWorkCallback() {
        return new CommonNoNetWorkFragment.OnNoNetWorkCallBack() {
            @Override
            public void clickRefresh() {
                refreshInitView();
                refreshNoNetWork();
            }
        };
    }

    private void showNoNetwork(CommonNoNetWorkFragment.OnNoNetWorkCallBack callback) {
        if (mContentView != null) {
            mContentView.setVisibility(View.GONE);
        }
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        mNoNetWorkFragment = CommonNoNetWorkUtils.getInstance().getNoNetworkFragment(fragmentManager, mNoNetWorkFragment);
        mNoNetWorkFragment.setNoNetWorkCallBack(callback);
        CommonNoNetWorkUtils.getInstance().showNoNetWorkFragment(R.id.fl_common_content, fragmentManager, mNoNetWorkFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        onActivityVisibilityChanged(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        onActivityVisibilityChanged(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        checkVisibility(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // hidden 表示是否是隐藏的，后续 checkVisibility 里面的 mVisible 表示是否可见
        // 所以这两个应该是相反的
        checkVisibility(!hidden);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        checkVisibility(true);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        v.removeOnAttachStateChangeListener(this);
        checkVisibility(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideCommonLoading();
        if (mContentView != null) {
            mContentView = null;
        }
        if (viewBinding != null) {
            viewBinding = null;
        }
        FragmentUtils.removeAllFragments(getChildFragmentManager());
    }

    /**
     * 检查可见性是否变化
     *
     * @param expected 可见性期望的值。只有当前值和expected不同，才需要做判断
     */
    private void checkVisibility(boolean expected) {
        if (expected == mVisible) {
            return;
        }
        final boolean parentVisible = mParentActivityVisible;
        final boolean superVisible = super.isVisible();
        final boolean hintVisible = getUserVisibleHint();
        final boolean visible = parentVisible && superVisible && hintVisible;
        if (visible != mVisible) {
            mVisible = visible;
            onVisibilityChanged(mVisible);
            if (!isOnCreateView) {
                onVisibilityChangedExceptCreate(mVisible);
            }
            isOnCreateView = false;
        }
    }

    /**
     * 可见性改变，每一次显示隐藏状态改变的监听
     */
    protected void onVisibilityChanged(boolean visible) {

    }

    /**
     * 可见性改变，首次创建时的显示状态不监听
     */
    protected void onVisibilityChangedExceptCreate(boolean visible) {
    }

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    public boolean isFragmentVisible() {
        return mVisible;
    }

    /**
     * ParentActivity可见性改变
     */
    protected void onActivityVisibilityChanged(boolean visible) {
        mParentActivityVisible = visible;
        checkVisibility(visible);
    }
}

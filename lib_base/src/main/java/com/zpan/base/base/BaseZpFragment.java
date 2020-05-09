package com.zpan.base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.zpan.base.R;

/**
 * @author zpan
 * @date 2019/4/8 13:49
 *
 * description: fragment 基类
 */
public abstract class BaseZpFragment extends Fragment {

    protected AppCompatActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_fragment_base_view, null);
        buildCreateRootView(rootView);
        return rootView;
    }

    protected void buildCreateRootView(View rootView) {

    }

    /**
     * 初始化布局：对展示布局进行设置
     *
     * @return 布局资源 ID
     */
    public abstract int exInitFragmentLayout();

    /**
     * 初始化控件参数： 在该方法中，可以对已绑定的控件数据初始化
     *
     * @param contentView 内容视图
     */
    public abstract void exInitFragmentView(View contentView);

    /**
     * 刷新初始化view
     */
    public abstract void refreshInitView();

    /**
     * 展示 Loading
     */
    public abstract void showCommonLoading();

    /**
     * 隐藏Loading
     */
    public abstract void hindCommonLoading();

    /**
     * 显示Toast
     */
    public abstract void showCommonToast(String msg);

    /**
     * 显示Toast
     */
    public abstract void showCommonToast(int msgId);
}

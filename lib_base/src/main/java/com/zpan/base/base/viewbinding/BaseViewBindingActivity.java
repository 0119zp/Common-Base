package com.zpan.base.base.viewbinding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import com.zpan.base.R;
import com.zpan.base.base.nonetwork.CommonNoNetWorkFragment;
import com.zpan.base.base.nonetwork.CommonNoNetWorkUtils;
import com.zpan.base.base.titlebar.NormalTitleBar;
import com.zpan.base.base.titlebar.TitleBarFactory;
import com.zpan.base.databinding.CommonActivityBaseViewBinding;
import com.zpan.base.theme.StatusBarCompat;
import com.zpan.base.utils.CommonToast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zpan
 * @date 2020/5/9 2:01 PM
 *
 * description: 视图绑定基类
 */
public abstract class BaseViewBindingActivity<T extends ViewBinding> extends AppCompatActivity {

    public T viewBinding;

    private CommonNoNetWorkFragment mNoNetWorkFragment;
    private CommonActivityBaseViewBinding mBaseBinding;

    /**
     * 获得 titleBar 文案
     *
     * @return titleBar 文案
     */
    @StringRes
    public abstract int exTitleBarLabel();

    /**
     * 视图绑定
     *
     * @return XML 绑定类
     */
    public abstract T exViewBinding();

    /**
     * 初始化操作，包括页面控件初始化，数据初始化，等等...
     */
    public abstract void exInit();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         *  修复  8.0 透明的Activity，不能固定它的方向，因为它的方向其实是依赖其父Activity的（因为透明)
         *  https://blog.csdn.net/starry_eve/article/details/82777160
         */
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            fixOrientation();
        } else {
            // 默认页面竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 沉浸式状态栏, 设置statusBar颜色及文字颜色模式，默认为白色底，深色文字模式
        StatusBarCompat.setStatusBarColorAndLightMode(this, Color.WHITE, false);
        init(savedInstanceState);
    }

    private void init(@Nullable Bundle savedInstanceState) {
        initDefaultView(savedInstanceState);
        rendererInitView();
    }

    private void initDefaultView(Bundle savedInstanceState) {
        mBaseBinding = CommonActivityBaseViewBinding.inflate(LayoutInflater.from(this));
        setContentView(mBaseBinding.getRoot());
        View titleBarContent = exInitTitleBar(new TitleBarFactory(this));
        mBaseBinding.ctbCommonTitleBar.removeAllViews();
        if (titleBarContent != null) {
            mBaseBinding.ctbCommonTitleBar.addView(titleBarContent);
        } else {
            mBaseBinding.ctbCommonTitleBar.setVisibility(View.GONE);
        }
        viewBinding = exViewBinding();
        View contentView = viewBinding.getRoot();
        mBaseBinding.flCommonContent.addView(contentView);
    }

    private void rendererInitView() {
        CommonNoNetWorkFragment.OnNoNetWorkCallBack callBack = noNetWorkCallback();
        if (callBack != null && !CommonNoNetWorkUtils.isConnected(this)) {
            showNoNetwork(callBack);
            return;
        }
        if (mNoNetWorkFragment != null) {
            CommonNoNetWorkUtils.getInstance().hideNoNetWorkFragment(getSupportFragmentManager(), mNoNetWorkFragment);
        }
        exInit();
    }

    /**
     * 返回定制化的titleBar布局
     *
     * @return 定制化titleBar布局
     */
    protected View exInitTitleBar(TitleBarFactory titleBarFactory) {
        NormalTitleBar normalTitleBar = (NormalTitleBar) titleBarFactory.build(TitleBarFactory.COMMON_TITLE_TYPE_NORMAL);
        String titleBarLabel = getResources().getString(exTitleBarLabel());
        normalTitleBar.setTitleLabel(titleBarLabel);
        return normalTitleBar.getContentView();
    }

    @Keep
    public void showCommonLoading() {
        if (mBaseBinding.clCommonLoading.getVisibility() == View.GONE) {
            mBaseBinding.clCommonLoading.setVisibility(View.VISIBLE);
        }
    }

    @Keep
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
        CommonToast commonToast = new CommonToast(this);
        commonToast.setText(msg).setTextId(iconId).show();
    }

    protected void refreshNoNetWork() {

    }

    public CommonNoNetWorkFragment.OnNoNetWorkCallBack noNetWorkCallback() {
        return new CommonNoNetWorkFragment.OnNoNetWorkCallBack() {
            @Override
            public void clickRefresh() {
                rendererInitView();
                refreshNoNetWork();
            }
        };
    }

    private void showNoNetwork(CommonNoNetWorkFragment.OnNoNetWorkCallBack callback) {
        if (mNoNetWorkFragment == null) {
            mNoNetWorkFragment = CommonNoNetWorkUtils.getInstance().getNoNetworkFragment(getSupportFragmentManager(), mNoNetWorkFragment);
        }
        mNoNetWorkFragment.setNoNetWorkCallBack(callback);
        CommonNoNetWorkUtils.getInstance().showNoNetWorkFragment(R.id.fl_common_content, getSupportFragmentManager(), mNoNetWorkFragment);
    }

    /**
     * 先判断，如果透明，直接把方向改为SCREEN_ORIENTATION_UNSPECIFIED：
     */
    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            @SuppressLint("PrivateApi")
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private void fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration newConfig = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (resources != null && newConfig.fontScale != 1) {
            newConfig.fontScale = 1;
            if (Build.VERSION.SDK_INT >= 17) {
                Context configurationContext = createConfigurationContext(newConfig);
                resources = configurationContext.getResources();
                displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale;
            } else {
                resources.updateConfiguration(newConfig, displayMetrics);
            }
        }
        return resources;
    }
}

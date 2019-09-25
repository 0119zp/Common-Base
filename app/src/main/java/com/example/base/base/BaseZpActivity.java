package com.example.base.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.base.base.titlebar.TitleBarFactory;
import com.example.base.theme.StatusBarCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zpan
 * @date 2019/3/29 17:28
 *
 * description: Activity 基类
 */
public abstract class BaseZpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
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
        buildInitLib();
        buildInitView(savedInstanceState);
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    /**
     * 先判断，如果透明，直接把方向改为SCREEN_ORIENTATION_UNSPECIFIED：
     */
    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
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

    /**
     * 初始化 oncreat 方法中的bundle
     *
     * @param savedInstanceState oncreat 方法中的bundle
     */
    protected abstract void buildOnCreatBundle(Bundle savedInstanceState);

    /**
     * 初始化库
     */
    protected abstract void buildInitLib();

    /**
     * 初始化视图
     *
     * @param savedInstanceState Bundle
     */
    protected abstract void buildInitView(Bundle savedInstanceState);

    /**
     * 获得 titlebar 文案
     *
     * @return titlebar 文案
     */
    public abstract int exTitleBarLabel();

    /**
     * 初始化布局：对展示布局进行设置
     *
     * @return 布局资源 ID
     */
    public abstract int exInitLayout();

    /**
     * 初始化 title bar
     *
     * @param titleBarFactory 公用titlebar工厂类
     */
    protected abstract View exInitTitleBar(TitleBarFactory titleBarFactory);

    /**
     * 初始化控件参数： 在该方法中，可以对已绑定的控件数据初始化
     */
    public abstract void exInitView();

    /**
     * 刷新初始化view
     */
    public abstract void refreshInitView();

    /**
     * 无网络页面刷新
     */
    public abstract void refreshNoNetWork();

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

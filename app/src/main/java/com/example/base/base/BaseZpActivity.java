package com.example.base.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.base.base.titlebar.TitleBarFactory;

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
        // 默认页面竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 6.0以上设置status bar颜色及文字颜色，其余版本使用默认
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //int statusColor = ContextCompat.getColor(this, R.color.common_title_bar_bg);
            //StatusUtils.setStatusColor(this, statusColor);
            //StatusUtils.setLightStatusBar(this, true);
        }
        buildInitLib();
        buildInitView(savedInstanceState);
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
    public abstract void exInitTitleBar(TitleBarFactory titleBarFactory);

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
}

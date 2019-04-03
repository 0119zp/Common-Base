package com.example.base.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * description: Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认页面竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // TODO 6.0以上设置status bar颜色及文字颜色，其余版本使用默认

        buildInitLib();
        buildInitView();
    }

    /**
     * 初始化库
     */
    private void buildInitLib() {

    }

    /**
     * 初始化视图
     */
    public void buildInitView() {
        exInitBundle();
        exInitView();
        exInitAfter();
    }

    /**
     * 初始化布局：对展示布局进行设置
     *
     * @return 布局资源 ID
     */
    public abstract int exInitLayout();

    /**
     * 初始化 title bar
     *
     * @param titleBar 公用title
     */
    public abstract void exInitTitleBar(CommonTitleBar titleBar);

    /**
     * 初始化传入参数：处理进入之前传入的数据
     */
    public abstract void exInitBundle();

    /**
     * 初始化控件参数： 在该方法中，可以对已绑定的控件数据初始化
     */
    public abstract void exInitView();

    /**
     * 初始化之后： 在基础数据初始化完成之后可以使用该方法
     */
    public abstract void exInitAfter();

}

package com.example.base.base.titlebar;

import android.app.Activity;

/**
 * @author zpan
 * @date 2019/4/3 19:55
 *
 * description: title bar 工厂类
 */
public class TitleBarFactory {

    public static final int COMMON_TITLE_TYPE_NORMAL = 0x201;

    private Activity mActivity;

    public TitleBarFactory(Activity activity) {
        this.mActivity = activity;
    }

    public BaseTitleBar build(int titleType) {
        BaseTitleBar titleBar;
        switch (titleType) {
            case COMMON_TITLE_TYPE_NORMAL:
                titleBar = new NormalTitleBar(mActivity);
                break;
            default:
                titleBar = new NormalTitleBar(mActivity);
                break;
        }
        return titleBar;
    }
}

package com.example.base.base.titlebar;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

/**
 * @author zpan
 * @date 2019/4/3 19:55
 *
 * description: title bar 工厂类
 */
public class TitleBarFactory {

    public static final int COMMON_TITLE_TYPE_NORMAL = 0x201;

    private Context mContext;
    private LinearLayout mTitleBarWrapper;

    public TitleBarFactory(Context context, LinearLayout titleBarWrapper) {
        this.mContext = context;
        this.mTitleBarWrapper = titleBarWrapper;
    }

    public BaseTitleBar getCommonTitleBar(Activity activity) {
        return getCommonTitleBar(activity, COMMON_TITLE_TYPE_NORMAL);
    }

    public BaseTitleBar getCommonTitleBar(Activity activity, int titleType) {
        BaseTitleBar titleBar;
        switch (titleType) {
            case COMMON_TITLE_TYPE_NORMAL:
                titleBar = new NormalTitleBar(mContext, activity, mTitleBarWrapper);
                break;
            default:
                titleBar = new NormalTitleBar(mContext, activity, mTitleBarWrapper);
                break;
        }
        return titleBar;
    }
}

package com.zpan.base.theme;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import com.zpan.base.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarCompat {

    private static final int STATUS_VIEW_ID = R.id.status_view;

    /**
     * 设置statusBar状态栏背景颜色以及状态栏文字颜色模式（文字显示模式只有浅色模式以及深色模式，文字具体色值根据不同手机设备表现不一致）
     *
     * @param activity 页面
     * @param color 颜色色值
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；true - 文字为深色（黑色...）
     */
    public static void setStatusBarColorAndLightMode(Activity activity, @ColorInt int color, boolean lightMode) {
        if (!needFullScreen()) {
            setStatusBarColor(activity, 0xff333333);
            return;
        }
        setStatusBarColor(activity, color);
        setLightStatusBar(activity, lightMode);
    }

    /**
     * 设置statusBar状态栏背景颜色
     *
     * @param activity 页面
     * @param color 颜色色值
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusViewToAct(activity, color);
            setRootView(activity);
        }
    }

    /**
     * 设置透明度状态栏, 此时为全屏状态
     *
     * @param activity 页面
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；false - 文字为深色（黑色...）
     */
    public static void setTranslucentStatusBar2FullScreen(Activity activity, boolean lightMode) {
        if (!needFullScreen()) {
            return;
        }
        setStatusBarColor2FullScreen(activity, Color.TRANSPARENT, lightMode);
    }

    /**
     * 设置状态栏颜色，可设置透明度，此时为全屏状态
     *
     * @param activity 页面
     * @param color 颜色色值
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；true - 文字为深色（黑色...）
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBarColor2FullScreen(Activity activity, @ColorInt int color, boolean lightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            if (lightMode) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusViewToAct(activity, color);
        }
    }

    /**
     * 设置状态栏view的颜色并添加到界面中，如果找到状态栏view则直接设置，否则创建一个再设置
     */
    private static void setStatusViewToAct(Activity activity, @ColorInt int color) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        addFakeStatusBar(activity, decorView, color);
    }

    /**
     * 创建和状态栏一样高的矩形，用于改变状态栏颜色，将虚拟状态栏添加至decorView根布局的头部
     *
     * @param activity 页面
     * @param decorView 页面根布局（根布局要求是LinearLayout线性垂直布局）
     * @param color 虚拟状态栏布局颜色值
     */
    public static void addFakeStatusBar(Activity activity, ViewGroup decorView, @ColorInt int color) {
        if (!needFullScreen()) {
            return;
        }
        View fakeStatusBarView = decorView.findViewById(STATUS_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            decorView.addView(createStatusBarView(activity, color), 0);
        }
    }

    /**
     * 创建和状态栏一样高的矩形，用于改变状态栏颜色
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        statusBarView.setId(STATUS_VIEW_ID);
        return statusBarView;
    }

    /**
     * 获取statusbar高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 全屏模式下，需要在根布局中设置FitsSystemWindows属性为true
     */
    public static void setRootView(Activity activity) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(0);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 设置文字的light mode, 文字显示模式只有浅色模式以及深色模式，文字具体色值根据不同手机设备表现不一致
     *
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；true - 文字为深色（黑色...）
     */
    private static void setLightStatusBar(Activity activity, boolean lightMode) {
        switch (RomUtils.getLightStatusBarAvailableRomType()) {
            case RomUtils.MIUI:
                setMIUILightStatusBar(activity, lightMode);
                break;
            case RomUtils.FLYME:
                setFlymeLightStatusBar(activity, lightMode);
                break;
            case RomUtils.ANDROID_NATIVE:
                setAndroidNativeLightStatusBar(activity, lightMode);
                break;
            case RomUtils.NA:
                // N/A do nothing
                break;
            default:
                break;
        }
    }

    /**
     * 小米系统改变状态栏文字颜色，只能选择是深色字体还是浅色字体，不能修改具体色值
     *
     * @param activity 页面
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；true - 文字为深色（黑色...）
     */
    private static boolean setMIUILightStatusBar(Activity activity, boolean lightMode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), lightMode ? 0 : darkModeFlag, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 魅族系统改变状态栏文字颜色，只能选择是深色字体还是浅色字体，不能修改具体色值
     *
     * @param activity 页面
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；true - 文字为深色（黑色...）
     */
    private static boolean setFlymeLightStatusBar(Activity activity, boolean lightMode) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (lightMode) {
                    value &= ~bit;
                } else {
                    value |= bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 谷歌原生方式改变状态栏文字颜色，只能选择是深色字体还是浅色字体，不能修改具体色值
     *
     * @param activity 页面
     * @param lightMode 是否为浅色模式，true - 文字为浅色（白色...）；true - 文字为深色（黑色...）
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void setAndroidNativeLightStatusBar(Activity activity, boolean lightMode) {
        View decor = activity.getWindow().getDecorView();
        if (lightMode) {
            // We want to change tint color to white again.
            // You can also record the flags in advance so that you can turn UI back completely if
            // you have set other flags before, such as translucent or full screen.
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static boolean needFullScreen() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
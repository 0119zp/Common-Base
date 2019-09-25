package com.example.base.theme;

import android.os.Build;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 设备系统工具类
 */
public class RomUtils {

    /**
     * 小米系统
     */
    public static final int MIUI = 1;
    /**
     * 魅族系统
     */
    public static final int FLYME = 2;
    /**
     * 谷歌原生系统
     */
    public static final int ANDROID_NATIVE = 3;
    public static final int NA = 4;

    /**
     * 浅色状态栏模式 是否可用
     */
    public static boolean isLightStatusBarAvailable() {
        if (isMIUIV6OrAbove() || isFlymeV4OrAbove() || isAndroidMOrAbove()) {
            return true;
        }
        return false;
    }

    /**
     * 获取状态栏系统类型
     */
    public static int getLightStatusBarAvailableRomType() {
        // 开发版 7.7.13 及以后版本采用了谷歌系统API，旧方法无效但不会报错
        if (isMIUIV7OrAbove()) {
            return ANDROID_NATIVE;
        }

        if (isMIUIV6OrAbove()) {
            return MIUI;
        }

        if (isFlymeV4OrAbove()) {
            return FLYME;
        }

        if (isAndroidMOrAbove()) {
            return ANDROID_NATIVE;
        }

        return NA;
    }

    /**
     * Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
     * Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
     */
    private static boolean isFlymeV4OrAbove() {
        String displayId = Build.DISPLAY;
        String flyme = "Flyme";
        if (!TextUtils.isEmpty(displayId) && displayId.contains(flyme)) {
            String[] displayIdArray = displayId.split(" ");
            for (String temp : displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * MIUI V6对应的versionCode是4
     * MIUI V7对应的versionCode是5
     */
    private static boolean isMIUIV6OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                int minVersionCode = 4;
                if (miuiVersionCode >= minVersionCode) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * MIUI V6对应的versionCode是4
     * MIUI V7对应的versionCode是5
     */
    private static boolean isMIUIV7OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                int minVersionCode = 5;
                if (miuiVersionCode >= minVersionCode) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Android Api 23以上
     */
    private static boolean isAndroidMOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
}

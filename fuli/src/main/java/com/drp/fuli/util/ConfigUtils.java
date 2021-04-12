package com.drp.fuli.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * @author durui
 * @date 2021/3/11
 * @description
 */
public class ConfigUtils {
    public static boolean isOrientationPortrait(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }
        return false;
    }
    public static boolean isOrientationLandscape(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        return false;
    }
}

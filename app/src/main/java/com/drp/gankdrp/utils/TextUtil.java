package com.drp.gankdrp.utils;

import android.content.Context;
import android.text.ClipboardManager;
import android.widget.TextView;

import com.drp.gankdrp.App;

/**
 * @author KG on 2017/7/20.
 */

public class TextUtil {

    public static void setFakeBoldText(TextView tv) {
        tv.getPaint().setFakeBoldText(true);
    }

    @SuppressWarnings("deprecation")
    public static void copyToClipBoard(String str) {
        ClipboardManager cm = (ClipboardManager) App.application.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(str);
    }
}

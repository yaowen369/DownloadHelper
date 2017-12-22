package com.yaoxiaowen.download.sample.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/22 18:30
 * @since 1.0.0
 */
public class Utils_Toast {

    private volatile static Toast sToast;


    public static void show(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    public static void show(Context context, CharSequence message, int time) {
        if (TextUtils.isEmpty(message))
            return;

        if (sToast == null) {
            sToast = Toast.makeText(context, message, time);
        }
        sToast.setText(message);
        sToast.show();
    }



}


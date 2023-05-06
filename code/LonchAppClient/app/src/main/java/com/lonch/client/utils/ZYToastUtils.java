package com.lonch.client.utils;

import android.text.TextUtils;
import android.widget.Toast;


/**
 * 对toast的简单封装
 */
public class ZYToastUtils {

    public static final void showShortToast(int resId) {
        CharSequence text = ContextHolder.getContext().getText(resId);
        showShortToast(text);
    }

    public static final void showShortToast(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(ContextHolder.getContext(), null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    public static final void showLongToast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast toast = Toast.makeText(ContextHolder.getContext(), null, Toast.LENGTH_LONG);
        toast.setText(text);
        toast.show();
    }


}

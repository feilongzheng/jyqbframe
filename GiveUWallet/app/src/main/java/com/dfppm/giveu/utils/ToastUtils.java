package com.dfppm.giveu.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.dfppm.giveu.base.BaseApplication;

/**
 * Created by zhengfeilong on 16/5/12.
 */
public class ToastUtils {
//    public static void showShortToast(Context context, String str) {
//        showShortToast(str);
//    }
//
//    public static void showShortToast(Context context, int strId) {
//        showShortToast(strId);
//    }

    public static void showShortToast(String str) {
        showToast(BaseApplication.getInstance(), str, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int strId) {
        String str = BaseApplication.getInstance().getResources().getString(strId);
        showShortToast(str);
    }

    public static void showLongToast(String str) {
        showToast(BaseApplication.getInstance(), str, Toast.LENGTH_LONG);
    }

    public static void showLongToast(int strId) {
        String str = BaseApplication.getInstance().getResources().getString(strId);
        showLongToast(str);
    }

//    public static void showLongToast(Context context, String str) {
//        showToast(context, str, Toast.LENGTH_LONG);
//    }

    private static void showToast(Context context, String str, int duration) {
        if (context != null && !TextUtils.isEmpty(str)) {
            Toast toast = Toast.makeText(context, str, duration);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


}

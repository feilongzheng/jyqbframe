package com.dfppm.giveu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dfppm.giveu.base.BaseApplication;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.version;

public class CommonUtils {
    /**
     * 获取本地IP地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress
                                .getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    public static String getVersionCode() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String code = "-1";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            code = String.valueOf(packInfo.versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }

    /**
     * 获取设备编号
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
//		Logger.i("CommonUtil", "tm--->" + tm.getDeviceId());
        if (tm.getDeviceId() == null) {
            String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
//		    Logger.i("CommonUtil", "android_id--->" + android_id);
            return android_id;
        }
        return tm.getDeviceId();
    }


    /**
     * 获取设备MAC地址
     *
     * @return
     */
    public static String getMacAdd(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return splitMacAdd(info.getMacAddress());
    }

    private static String splitMacAdd(String mac) {
        StringBuffer sb = new StringBuffer();
        if (null != mac && !"".equals(mac)) {
            String[] arr = mac.split(":");
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String version = "";
        try {

            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取手机型号
     *
     * @param context
     * @return
     */
    public static String getPhoneType(Context context) {

        return android.os.Build.MODEL;
    }

    /**
     * 获取SDK版本号
     *
     * @param context
     * @return
     */
    public static String getSDKVersion(Context context) {

        return android.os.Build.VERSION.SDK;
    }

    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getSystemVersion(Context context) {

        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机分辨率
     *
     * @param context
     * @return
     */
    public static String getPhoneResolution(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return width + "*" + height;
    }


    /**
     * IMEI 全称�?International Mobile Equipment Identity，中文翻译为国际移动装备辨识码， 即�?常所说的手机序列号，
     * 用于在手机网络中识别每一部独立的手机，是国际上公认的手机标志序号，相当于移动电话的身份证。序列号共有15位数字，�?位（TAC）是型号核准号码�?
     * 代表手机类型。接�?位（FAC）是�?��装配号，代表产地。后6位（SNR）是串号，代表生产顺序号。最�?位（SP）一般为0，是�?��码，备用�?
     * 国际移动装备辨识码一般贴于机身背面与外包装上，同时也存在于手机记忆体中，通过输入*#06#即可查询�?
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getDeviceId();
    }


    public static void addMoreParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        params.put("VERSION_CODE", getVersionCode());
        params.put("VERSION_NAME", getVersionName());
        params.put("DEVICE_TYPE", "2");//1:IOS :2ANDROID
        params.put("DEVICE_IDENTIFY", getIMEI(BaseApplication.getInstance()));
//        params.put("USER_ID", "");
//
//        int networkType = NetWorkUtils.getCurrentNetworkType();
//        if (networkType != -1) {
//            params.put("NETWORK_TYPE", String.valueOf(networkType));
//        }
//        if (Utility.isLogin()) {
//            LoginBean loginUserInfo = Utility.getLoginUserInfo(BaseApplication.getInstance());
//            if (loginUserInfo != null && !TextUtils.isEmpty(loginUserInfo.userSessionToken)) {
//                params.put("USER_SESSION_TOKEN", loginUserInfo.userSessionToken);
//            }
//        }

    }

    public static <T> boolean isListNull(Map map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断字符串是否为null或�?空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        boolean result = false;
        if (null == str || "".equals(str.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * str != null && !("".equals(str))
     * @param str
     * @return
     */
    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * 判断数组是否是null或size()==0
     *
     * @param list
     * @return
     */
    public static boolean isNullOrEmpty(List<?> list) {
        boolean result = false;
        if (null == list || list.size() == 0) {
            result = true;
        }
        return result;
    }

    /**
     * list != null && lists.size() != 0;
     * @param list
     * @return
     */
    public static boolean isNotNullOrEmpty(List<?> list) {
        return !isNullOrEmpty(list);
    }

    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 获取屏幕DisplayMetrics
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }



    /**
     * 打开系统软键盘
     */
    public static void openSoftKeyBoard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭系统软键盘
     */
    public static void closeSoftKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void closeSoftKeyBoard(Window window, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && window.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 设置textview中划线
     * @param textView
     */
    public static void setTextViewLineThrough(TextView textView){
        textView.getPaint().setAntiAlias(true);//抗锯齿
        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
//		textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
    }

    /**
     * 开启activity带参数
     * @param packageContext
     * @param cls
     * @param extraKey
     * @param extraValue
     */
    public static void startActivity(Context packageContext, Class<?> cls, String extraKey, String extraValue){
        Intent intent = new Intent(packageContext, cls);
        intent.putExtra(extraKey, extraValue);
        packageContext.startActivity(intent);
    }

    /**
     * 开启activity带参数
     * @param packageContext
     * @param cls
     * @param extraKey
     * @param extraValue
     */
    public static void startActivity(Context packageContext, Class<?> cls, String extraKey, Serializable extraValue){
        Intent intent = new Intent(packageContext, cls);
        intent.putExtra(extraKey, extraValue);
        packageContext.startActivity(intent);
    }

    /**
     * 开启activity不带参数
     * @param packageContext
     * @param cls
     */
    public static void startActivity(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
    }




}

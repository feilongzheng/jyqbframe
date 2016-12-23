package com.dfppm.giveu.utils.sharePref;

import android.content.Context;

/**
 * 用于本地持久化存取
 */
public class SharePrefUtil extends AbsSharePref {
    private final String spName = "com.dfppm.giveu_appsetting";
    private static SharePrefUtil sharePrefUtil;

    public static void setIsSendPushCidToServer(boolean isSend) {
        getInstance().putBoolean(SharePrefKeys.isSendDeviceToken, isSend);
    }

    public static boolean getIsSendPushCidToServer() {
        return SharePrefUtil.getInstance().getBoolean(SharePrefKeys.isSendDeviceToken, false);
    }

    public static void setPushId(String cid) {

        getInstance().putString(SharePrefKeys.sp_JPushRegistId, cid);
    }

    public static String getPushId(Context context) {

        return getInstance().getString(SharePrefKeys.sp_JPushRegistId);
    }

    @Override
    public String getSharedPreferencesName() {
        return spName;
    }

    public static synchronized SharePrefUtil getInstance(){
        if (sharePrefUtil == null)
        {
            sharePrefUtil = new SharePrefUtil();
        }
        return sharePrefUtil;
    }

    /**
     * 设置最新版本号
     *
     * @param newVersionCode
     */
    public static void setNewVersionCode(int newVersionCode) {
        getInstance().putInt(SharePrefKeys.NEW_VERSIONCODE, newVersionCode);
    }

    /**
     * 获取最新版本号
     */
    public static int getNewVersionCode() {
        return getInstance().getInt(SharePrefKeys.NEW_VERSIONCODE, 0);
    }




}

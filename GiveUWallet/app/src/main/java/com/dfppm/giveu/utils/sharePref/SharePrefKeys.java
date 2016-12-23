package com.dfppm.giveu.utils.sharePref;

import com.dfppm.giveu.utils.CommonUtils;

/**
 * Created by zhengfeilong on 16/5/6.
 */
public interface SharePrefKeys {
    String ISFISRTLOGIN = "IsFisrtLogin";
    String CACHE_BEAN_FOUND = "fm_found" + CommonUtils.getVersionCode();
    String CACHE_BEAN_PENGYE = "fm_pengye_new" + CommonUtils.getVersionCode();
    String CACHE_BEAN_STAR = "fm_star_new" + CommonUtils.getVersionCode();
    String CACHE_BEAN_MINE_NEW = "fm_mine_new" + CommonUtils.getVersionCode();
    String CACHE_BEAN_TUIJIAN_STAR = "fm_tuijian_star" + CommonUtils.getVersionCode();


    /**
     * 最新版本号
     */
    String NEW_VERSIONCODE = "new_versioncode";
    String key_image_old_host = "key_image_old_host";
    String sp_JPushRegistId = "sp_JPushRegistId";
    String sp_versionCodeKey = "sp_versionCodeKey";
    String sp_FindRedPointResponseKey = "sp_FindRedPointResponseKey";
    String sp_pingLunUnreadCountKey = "sp_pingLunUnreadCountKey";
    String loginUserBeanKey = "loginUserBeanKey";
    String isSendDeviceToken = "isSendDeviceToken";
    String KEY_setting_allow_show_notification = "isPush";
    String SHOW_UPDATE_DIALOG = "SHOW_UPDATE_DIALOG";// 显示升级对话框
    String KEY_NEW_VERSION = "NEW_VERSION";
    String ISSHOWGUIDE = "isShowGuide";
    String ISWELCOMESHOWGUIDE = "isWelcomeShowGuide";
    String key_tuijian_refresh = "key_tuijian_refresh";
    String KEY_ISSINA = "isSina";
    String KEY_ISQQ = "isQQ";
    String KEY_ISWEXIN = "isWeXin";
    String KEY_PASSWORD = "KEY_PASSWORD";
    String KEY_STAR_TYPE = "key_star_type";
    /**
     * 设备唯一编号
     */
    String DEVICEID = "deviceid_phone";
    String KEY_BottomBarGrowIconShowRedPoint = "newmessage";
    String KEY_USERID = "userIdKey";
    String SP_WELCOM = "SP_WELCOM";









}

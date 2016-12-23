package com.dfppm.giveu.utils;

import android.text.TextUtils;

import com.dfppm.giveu.utils.sharePref.SharePrefKeys;
import com.dfppm.giveu.utils.sharePref.SharePrefUtil;

/**
 * Created by zhengfeilong on 16/5/12.
 * 操作与用户信息相关的方法集合
 */
public class UserInfoUtils {
//    public static String getUserName() {
//        LoginBean loginBean = getLoginUserInfo(BaseApplication.getInstance());
//        return loginBean == null ? "" : loginBean.nickName;
//    }
//
//    public static void updateUserName(String newNick) {
//        LoginBean loginBean = getLoginUserInfo(BaseApplication.getInstance());
//        if (loginBean != null) {
//            loginBean.nickName = newNick;
//            saveLoginUserInfo(BaseApplication.getInstance(), loginBean);
//        }
//    }


    public static boolean isLogin() {
        String myId = getUserId();
        if (TextUtils.isEmpty(myId) ) {
            return false;
        }
        return true;
    }

    public static boolean isNotLogin() {
        return !isLogin();
    }

    public static String getUserId() {
        return SharePrefUtil.getInstance().getString(SharePrefKeys.KEY_USERID, "");
    }


//    public static void saveLoginUserInfo(Context context, LoginBean loginBean) {
//        if (loginBean != null && !TextUtils.isEmpty(loginBean.userid)) {
//            SharePrefUtil.getInstance().putObj(SharePrefKeys.loginUserBeanKey, loginBean);
//        }
//    }
//
//    public static LoginBean getLoginUserInfo(Context context) {
//        return (LoginBean) SharePrefUtil.getInstance().getObj(SharePrefKeys.loginUserBeanKey);
//    }




}

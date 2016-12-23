package com.dfppm.giveu.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dfppm.giveu.base.BaseWebViewActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 手机号码匹配
     * <p/>
     * 13x, 14x, 15x, 17x, 18x, 19x
     */
    public static boolean checkMobile(String mobile) {
        String regex = "^1(3[0-9]|4[0-9]|5[0-9]|7[0-9]|8[0-9]|9[0-9])\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);

        return m.find();
    }


    /**
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        if (null != string && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNull(String string) {
        if (null == string || "".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否是11位数字
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^0?\\d{11}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 验证邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static String format2(Float value) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    /**
     * 昵称是否含有特殊字符
     */
    public static boolean isUsernameAvailable(String nickname, Context context) {
        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.showShortToast("请填写姓名");
            return false;
        }
        if (nickname.length() < 2) {
            ToastUtils.showShortToast("姓名不少于2个字");
            return false;
        }
        if (!nickname.matches("[\\u4e00-\\u9fa5_a-zA-Z0-9]{1,14}[\\?:•.]{0,1}[\\u4e00-\\u9fa5_a-zA-Z0-9]{1,13}+$")) {
            ToastUtils.showShortToast("您输入的姓名含特殊符号");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkPasswordAndTipError(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShortToast("请输入密码");
            return false;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            ToastUtils.showShortToast("请输入6~16位密码");
            return false;
        }

        Pattern urlPattern = Pattern.compile("^([a-z]|[A-Z]|[0-9])*$");
        Matcher urlMatcher = urlPattern.matcher(pwd);
        if (!urlMatcher.find()) {
            ToastUtils.showShortToast("密码要是数字和字母的组合哦！");
            return false;
        }

        return true;
    }

    /**
     * 解析 输入的文字里有无url
     *
     * @param rawUrl
     * @return
     */
    public static String getReloveUrl(String rawUrl) {
        String includeUrl = null;
        if (!rawUrl.contains("://")) {
            rawUrl = "http://" + rawUrl;
        }
        Pattern urlPattern = Pattern.compile(BaseWebViewActivity.urlRegularExpression, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = urlPattern.matcher(rawUrl);
        while (urlMatcher.find()) {
            includeUrl = rawUrl.subSequence(urlMatcher.start(), urlMatcher.end()).toString();
            /**
             * 最后有表情 [] 过滤掉
             */
            if (StringUtils.isNotNull(includeUrl) && includeUrl.length() > 1) {
                String lastChar = includeUrl.charAt(includeUrl.length() - 1) + "";
                if ("[".equals(lastChar)) {
                    includeUrl = includeUrl.substring(0, includeUrl.length() - 1);
                }
            }
        }
        return includeUrl;
    }

    /**
     * 将null转成“”
     */
    public static String nullToEmptyString(String str) {
        return str == null ? "" : str;
    }


}

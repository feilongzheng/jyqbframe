package com.android.volley.mynet;

/**
 * Created by lianghuiyong on 2016/8/10.
 */
public class RespUtil {

    public final static int CODE_TOKEN_FAIL = -999;
    public final static String SUCCESS_STATUS = "success";
    public final static String FAIL_STATUS = "fail";
    public final static String ERROR_STATUS = "error";


    public static boolean isSuccess(String status){
        boolean b = SUCCESS_STATUS.equals(status);
        return b;
    }

    public static boolean isFail(String status){
        boolean b = FAIL_STATUS.equals(status);
        return b;
    }

    public static boolean isError(String status){
        boolean b = ERROR_STATUS.equals(status);
        return b;
    }

}

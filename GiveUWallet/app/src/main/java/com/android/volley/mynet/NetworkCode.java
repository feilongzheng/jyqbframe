package com.android.volley.mynet;

/**
 * Created by zhengfeilong on 16/5/5.
 */
public interface NetworkCode {
    /**
     * 值
     * 含义
     * 备注
     * -1	客户端提交的参数有误
     * -2	服务端异常
     * -3	用户被禁用
     * -4	调用已过期的接口
     * -5	用户在其他设备登陆
     */
    String NETWORK_ERROR_CODE1 = "-1"; // 客户端提交的参数有误
    String NETWORK_ERROR_CODE2 = "-2"; // 服务端异常
    String NETWORK_ERROR_CODE3 = "-3"; // 用户自己被禁用
    String NETWORK_ERROR_CODE4 = "-4"; // 调用已过期的接口
    String NETWORK_ERROR_CODE5 = "-5"; // 用户在其他设备登陆
    String NETWORK_ERROR_CODE7 = "-7"; // 用户登录sessionToken异常
    /**
     * 网络处理code
     */
    int NETWORK_NOLINK_CODE = 201; // 没有网络
    int NETWORK_TIMEOUT_CODE = 202; // 网络连接超时
    int NETWORK_SERVERERROR_CODE = 203; // 404,500等服务器错误
}

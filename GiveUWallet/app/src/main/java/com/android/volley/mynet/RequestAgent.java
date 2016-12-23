package com.android.volley.mynet;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.dfppm.giveu.base.BaseApplication;
import com.dfppm.giveu.utils.CommonUtils;
import com.dfppm.giveu.view.dialog.LoadingDialog;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestAgent {
    private RequestQueue mRequestQueue;

    public RequestAgent() {
        this.mRequestQueue = BaseApplication.getInstance().getRequestQueue();
    }

    private void addRequest(boolean isDecodeResponse, int requestMethod, Map<String, String> params, String urlPlus, Class resultClzz, Listener<BaseBean> listener, ErrorListener errorListener) {
        String url = urlPlus;
        RequestClass request = new RequestClass(isDecodeResponse, requestMethod, url, resultClzz, listener, errorListener);
        addRequestParam(url, params, request);

        LogUtils.i("请求路径：" + url);
        if (params != null) {
            for (Map.Entry<String, String> me : params.entrySet()) {
                LogUtils.i("参数：" + me.getKey() + "    值：" + me.getValue());
            }
        }

        if (!TextUtils.isEmpty(urlPlus)) {
            request.setTag(urlPlus);
        }

        mRequestQueue.add(request);
    }

    /**
     * 取消发出的请求
     *
     * @param urlPlus 就是sendPostRequest(..,String urlPlus,..)中的urlPlus;
     */
    public static void cancelRequest(Object urlPlus) {
        if (BaseApplication.getInstance().getRequestQueue() != null && urlPlus != null) {
            BaseApplication.getInstance().getRequestQueue().cancelAll(urlPlus);
        }
    }

    private void addRequestParam(String url, Map<String, String> params, RequestClass request) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        addHeaderParam(params);
        params = filterEmptyParamValue(params);

        if (isParamsNeedEncode(url)){//需不需要加密
            Gson gson = new Gson();
            String paramStr = gson.toJson(params);

            request.addParams("VERSION_NAME", CommonUtils.getVersionName());
            request.addParams("VERSION_CODE", CommonUtils.getVersionCode());
            request.addParams("inputParams", paramStr);
        }else {
            for (String key : params.keySet()) {
                String value = params.get(key);
                request.addParams(key, value);
            }
        }
    }

    private boolean isParamsNeedEncode(String url) {
//        if ( !TextUtils.isEmpty(url) && url.contains(RequestUrls.BASE_URL) && (url.contains("/a/n/") || url.contains("/a/l/")) ){
//            return true;
//        }
        return false;
    }

    private Map<String, String> filterEmptyParamValue(Map<String, String> params) {
        HashMap params2 = new HashMap<String, String>();
        if (params != null){
            for (String key : params.keySet()) {
                String value = params.get(key);// utf-8
                if (!TextUtils.isEmpty(value)) {//应服务器需要如果参数值为"",null，则不传
                    params2.put(key, value);
                }
            }
        }
        return params2;
    }

    private void addHeaderParam(Map<String, String> params) {
        CommonUtils.addMoreParams(params);
    }

    /**
     * @param keys
     * @param values
     * @return keys == null时，返回new HashMap<String, String>();
     */
    public static Map<String, String> getRequestParams(String[] keys, String[] values) {
        Map<String, String> params = new HashMap<String, String>();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                if (values[i] != null) {
                    params.put(keys[i], values[i]);
                }
            }
        }

        return params;
    }

    public static void sendPostRequest(Map<String, String> requestParams, String urlPlus, Class resultClzz, Activity loadingDialogContext, final ResponseListener responseListener) {
        sendRequest(true, Method.POST, requestParams, urlPlus, resultClzz, loadingDialogContext, responseListener);
    }

    public static void sendPostRequest(boolean isDecodeResponse, Map<String, String> requestParams, String urlPlus, Class resultClzz, Activity loadingDialogContext, final ResponseListener responseListener) {
        sendRequest(isDecodeResponse, Method.POST, requestParams, urlPlus, resultClzz, loadingDialogContext, responseListener);
    }

    public static void sendGetRequest(Map<String, String> requestParams, String urlPlus, Class resultClzz, Activity loadingDialogContext, final ResponseListener responseListener) {
        sendRequest(true, Method.GET, requestParams, urlPlus, resultClzz, loadingDialogContext, responseListener);
    }

    public static void sendGetRequest(boolean isDecodeResponse, Map<String, String> requestParams, String urlPlus, Class resultClzz, Activity loadingDialogContext, final ResponseListener responseListener) {
        sendRequest(isDecodeResponse, Method.GET, requestParams, urlPlus, resultClzz, loadingDialogContext, responseListener);
    }

    private static void sendRequest(boolean isDecodeResponse, int method, Map<String, String> requestParams, String urlPlus, final Class resultClzz, final Activity loadingDialogContext, final ResponseListener responseListener) {
        if (loadingDialogContext != null) {
            LoadingDialog.showIfNotExist(loadingDialogContext, false);
        }

        Map<String, String> requestParamsTemp = new HashMap<String, String>();
        if (requestParams != null) {
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                if (entry.getKey() == null) {
                    LogUtils.e("sendRequest的requestParams中key不能为null");
                    return;
                }
                if (entry.getValue() == null) {
                    requestParamsTemp.put(entry.getKey(), "");
                } else {
                    requestParamsTemp.put(entry.getKey(), entry.getValue());
                }
            }
        }

        BaseApplication.getInstance().getRequestAgen().addRequest(isDecodeResponse, method, requestParamsTemp, urlPlus, resultClzz, new Response.Listener<BaseBean>() {

            @Override
            public void onResponse(BaseBean response) {
                if (loadingDialogContext != null) {
                    LoadingDialog.dismissIfExist();
                }
                if (responseListener != null && responseListener instanceof ExpandResponseListener) {
                    ((ExpandResponseListener) responseListener).beforeSuccessAndError();
                }

                if (response != null) {
                    if (RespUtil.SUCCESS_STATUS.equals(response.status) ) {
                        BaseBean mResult = new BaseBean();
                        if ( !TextUtils.isEmpty(response.data)){
                            try{
                                mResult = (BaseBean) new Gson().fromJson(response.data, resultClzz);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        mResult.status = response.status;
                        mResult.data = response.data;
                        mResult.message = response.message;
                        mResult.originResultString = response.originResultString;
                        if (responseListener != null) {
                            responseListener.onSuccess(mResult);
                        }
                    } else {
                            if (responseListener != null) {
                                setErrorResponse(response);
                                responseListener.onError(response);
                            }
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (loadingDialogContext != null) {
                    LoadingDialog.dismissIfExist();
                }
                if (responseListener != null && responseListener instanceof ExpandResponseListener) {
                    ((ExpandResponseListener) responseListener).beforeSuccessAndError();
                }

                if (responseListener != null && error != null) {
                    BaseBean errorBean = new BaseBean();
                    errorBean.status = VolleyErrorHelper.getVolleyErrorCode(error) + "";
                    errorBean.message = error.getMessage();
                    if (VolleyErrorHelper.getVolleyErrorCode(error) == 200) {
                        errorBean.message = "数据解析错误";
                    }

                    responseListener.onError(errorBean);
                }
            }
        });
    }

    /**
     * 设置返回错误信息
     */
    private static void setErrorResponse(BaseBean response) {
//        if (response != null && CommonLoadingView.errorMap.containsKey(response.resultCode)) {
//            response.message = CommonLoadingView.errorMap.get(response.resultCode);
//        }
    }

    public interface ResponseListener<T extends BaseBean> {
        void onSuccess(T response);

        void onError(BaseBean errorBean);
    }

    public interface ExpandResponseListener<T extends BaseBean> extends ResponseListener<T> {
        void beforeSuccessAndError();
    }

    public static class VolleyErrorHelper {
        // AuthFailureError：如果在做一个HTTP的身份验证，可能会发生这个错误。
        // NetworkError：Socket关闭，服务器宕机，DNS错误都会产生这个错误。
        // NoConnectionError：和NetworkError类似，这个是客户端没有网络连接。
        // ParseError：在使用JsonObjectRequest或JsonArrayRequest时，如果接收到的JSON是畸形，会产生异常。
        // SERVERERROR：服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。
        // TimeoutError：Socket超时，服务器太忙或网络延迟会产生这个异常。默认情况下，Volley的超时时间为。。秒。如果得到这个错误可以使用RetryPolicy。
        public static boolean isNetworkError(Object error) {
            if (isNoConnectionError(error)) {
                return false;
            }
            return (error instanceof NetworkError);
        }

        public static boolean isNoConnectionError(Object error) {
            return (error instanceof NetworkError);
        }

        public static boolean isServerError(Object error) {
            return (error instanceof ServerError);
        }

        public static boolean isAuthFailureError(Object error) {
            return (error instanceof AuthFailureError);
        }

        public static boolean isTimeoutError(Object error) {
            return (error instanceof TimeoutError);
        }

        public static boolean isParseError(Object error) {
            return (error instanceof ParseError);
        }

        public static int getVolleyErrorCode(VolleyError error) {

            if (error != null) {
                NetworkResponse response = error.networkResponse;

                if (isServerError(error) || isAuthFailureError(error) || isNetworkError(error)) {
                    return NetworkCode.NETWORK_SERVERERROR_CODE;
                } else if (isNoConnectionError(error)) {
                    return NetworkCode.NETWORK_NOLINK_CODE;
                } else if (isTimeoutError(error)) {
                    return NetworkCode.NETWORK_TIMEOUT_CODE;
                }

                if (response != null) {
                    if (response.statusCode == 200) {
                        // 等于200,请求成功,可能自己数据操作错误
                        return 200;
                    }
                }

            }

            return NetworkCode.NETWORK_SERVERERROR_CODE;
        }
    }

    /**
     * 测试volley框架用的
     *
     * @param loadingDialogContext
     */
    private static void testRequestAgent(Activity loadingDialogContext) {
        // test
        Map<String, String> requestParams2 = RequestAgent.getRequestParams(new String[]{"userid"}, new String[]{"207077"});
        RequestAgent.sendPostRequest(requestParams2, "", TestBean.class, loadingDialogContext, new ResponseListener<TestBean>() {

            @Override
            public void onSuccess(TestBean response) {
                String dString = response.message + response.status + "----" + ((TestBean) response).toString();
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), dString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(BaseBean error) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), error.message, Toast.LENGTH_SHORT).show();
            }
        });
    }


}

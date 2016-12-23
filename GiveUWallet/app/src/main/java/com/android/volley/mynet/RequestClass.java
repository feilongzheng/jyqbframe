package com.android.volley.mynet;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;

public class RequestClass extends JsonRequest<BaseBean> {

    //	http://test.pengsi.cn:7060/service/browser/v2_getuserfisrtconnection.jsp
    private String requestUrl;
    private int requestMethod;
    private Class resultClzz;
    private boolean isDecodeResponse = false;


    public RequestClass(boolean isDecodeResponse, int requestMethod, String requestUrl, Class resultClzz, Listener<BaseBean> listener, ErrorListener errorListener) {
        super(requestMethod, requestUrl, listener, errorListener);

        this.isDecodeResponse = isDecodeResponse;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.resultClzz = resultClzz;
    }


    @Override
    protected Response<BaseBean> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = "";
//            if (isDecodeResponse) {
//                responseString = IOUtils.decrypt(response.data);
//            } else {
//                responseString = new String(response.data);
//            }
            responseString = new String(response.data);
            LogUtils.i("返回:" + this.requestUrl + " =" + responseString);

            BaseBean info = new BaseBean();
            try{
                info = (BaseBean) new Gson().fromJson(responseString, BaseBean.class);
            }catch (Exception e){
                info.status = RespUtil.SUCCESS_STATUS;
                e.printStackTrace();
            }
            info.originResultString = responseString;

            if (!isDecodeResponse && TextUtils.isEmpty(info.status)) {
                info.status = RespUtil.SUCCESS_STATUS;
            }

            return Response.success(info, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error(new VolleyError(response));
    }


}

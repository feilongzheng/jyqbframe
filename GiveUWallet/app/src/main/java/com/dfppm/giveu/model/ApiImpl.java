package com.dfppm.giveu.model;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.mynet.BaseBean;
import com.android.volley.mynet.RequestAgent;
import com.android.volley.mynet.TestBean;
import com.dfppm.giveu.base.BaseApplication;

import java.util.Map;

/**
 * 所有的网络请求放在这个类中
 */

public class ApiImpl {

	private static ApiImpl apiImpl;
	private ApiImpl(){

	}
	public static ApiImpl getInstance(){
		if (apiImpl == null){
			synchronized (apiImpl){
				apiImpl = new ApiImpl();
			}
		}
		return apiImpl;
	}

	public  void testRequestAgent(Activity loadingDialogContext) {
		// test
		Map<String, String> requestParams2 = RequestAgent.getRequestParams(new String[]{"userid"}, new String[]{"207077"});
		RequestAgent.sendPostRequest(requestParams2, "", TestBean.class, loadingDialogContext, new RequestAgent.ResponseListener<TestBean>() {

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

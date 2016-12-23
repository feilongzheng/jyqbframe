package com.android.volley.mynet;

import java.io.Serializable;

public class BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5331198215023944098L;
	public String status;
	public String message;
	public String data;
	public String originResultString;

	public static int getResultCode(String status){
		return 200;
	}

}

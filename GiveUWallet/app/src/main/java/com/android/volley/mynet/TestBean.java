package com.android.volley.mynet;


public class TestBean extends BaseBean {
//	{"message":"","balance":"10","resultCode":"0","mdw":"319f3ffbcef487564bb0581885163520","type":"-1","url":"http://m.pengsi.cn/pay/index.html"}
	public String balance;
	public String mdw;
	public String type;
	public String url;
	
	@Override
	public String toString() {
		return "TestBean [balance=" + balance + ", mdw=" + mdw + ", type=" + type + ", url=" + url + "]";
	}
	
	
	
}

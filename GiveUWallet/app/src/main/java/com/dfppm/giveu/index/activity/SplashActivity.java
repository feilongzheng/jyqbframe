package com.dfppm.giveu.index.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.dfppm.giveu.R;
import com.dfppm.giveu.base.BaseActivity;
import com.dfppm.giveu.utils.CommonUtils;
import com.dfppm.giveu.utils.sharePref.SharePrefKeys;

import cn.jpush.android.api.JPushInterface;


public class SplashActivity extends BaseActivity{
	int splashTime = 1500;

	@Override
	public void initView(Bundle savedInstanceState) {

		setContentView(R.layout.layout_splash);
		baseLayout.hideTopBar();

		start();
	}

	@Override
	public void setListener() {

	}

	@Override
	public void setData() {

	}


	private void start(){
		new Thread() {
			@Override
			public void run() {
				SystemClock.sleep(splashTime);
				startViewPagerOrActivity();
			};
		}.start();
	}
    
    
    Handler dealHandler = new Handler(){
    	public void handleMessage(Message msg) {
			
    	};
    };
    
    SharedPreferences sharedPreferences;
    protected void startViewPagerOrActivity() {
    	sharedPreferences = getSharedPreferences(SharePrefKeys.SP_WELCOM, 0);
    	if(sharedPreferences.getBoolean(CommonUtils.getVersionCode(), true)){
    		turnToWelcomeActivity();
    	}else{
    		turnToMainActivity();
    	}
    }

	private void turnToWelcomeActivity() {
		Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
		startActivityForResult(intent, 0);
	}

	private void turnToMainActivity() {
		setViewPagerFalse();
		Intent i = new Intent(getBaseContext(), MainActivity.class);
		startActivity(i);
		finish();
	}

	private void setViewPagerFalse() {
		if(sharedPreferences.getBoolean(CommonUtils.getVersionCode(), true)){
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(CommonUtils.getVersionCode(), false);
			editor.commit();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		turnToMainActivity();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
    
    
}

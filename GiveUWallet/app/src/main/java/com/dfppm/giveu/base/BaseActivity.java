package com.dfppm.giveu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.HashSet;
import java.util.Set;


public abstract class BaseActivity extends FragmentActivity implements OnClickListener ,IView{
	public BaseLayout baseLayout;
	public Activity mBaseContext;
	private Set<BasePresenter> mAllPresenters = new HashSet<>(1);

	/**
	 * 当使用mvp模式时实现这个方法
	 */
	protected BasePresenter[] initPresenters(){
		return null;
	}

	private void addPresenters(){
		BasePresenter[] presenters = initPresenters();
		if (presenters != null){
			for (int i=0;i<presenters.length;i++){
				mAllPresenters.add(presenters[i]);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBaseContext = this;

		initView(savedInstanceState);
		addPresenters();

		setListener();
		setData();
	}

	/**
	 * 重写了activity的setContentView
	 * @param layoutResID
     */
	@Override
	public void setContentView(int layoutResID) {
		View view = View.inflate(this, layoutResID, null);
		this.setContentView(view);
	}

	/**
	 * 重写了activity的setContentView
	 * @param view
     */
	@Override
	public void setContentView(View view) {
		baseLayout = new BaseLayout(this);
		baseLayout.addView(view);
		baseLayout.setBackClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});

		super.setContentView(baseLayout);
	}

	/**
	 * onCreate第一步
	 * @param savedInstanceState
	 */
	public abstract  void initView(Bundle savedInstanceState);
	/**
	 *onCreate 第二步
	 */
	public abstract void setListener();
	/**
	 * onCreate第三步
	 */
	public abstract void setData();


	@Override
	protected void onResume() {
//		MobclickAgent.onResume(this);
		super.onResume();
		notifyIPresenter("onResume");
	}

	private void notifyIPresenter(String methodName){
		for (BasePresenter presenter : mAllPresenters){
			if (presenter != null){
				switch (methodName){
					case "onStart":
						presenter.onStart();
						break;
					case "onResume":
						presenter.onResume();
						break;
					case "onStop":
						presenter.onStop();
					case "onPause":
						presenter.onPause();
					case "onDestroy":
						presenter.onDestroy();
						break;
				}
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		notifyIPresenter("onStart");
	}

	@Override
	protected void onPause() {
//		MobclickAgent.onPause(this);
		super.onPause();
		notifyIPresenter("onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		notifyIPresenter("onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		notifyIPresenter("onDestroy");
	}

	@Override
	public void onClick(View view) {

	}


	@Override
	public Activity getAct() {
		return mBaseContext;
	}


}

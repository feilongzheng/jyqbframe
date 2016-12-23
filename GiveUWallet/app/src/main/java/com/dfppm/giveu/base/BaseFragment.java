package com.dfppm.giveu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 508632 on 2016/12/12.
 */

public abstract class BaseFragment extends Fragment {
	public Activity mBaseContext;
	View myXmlView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBaseContext = this.getActivity();

		myXmlView = initView( inflater,  container,  savedInstanceState);
		setListener();
		setData();
		return myXmlView;
	}

	protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	protected abstract void setListener();
	protected abstract void setData();


}

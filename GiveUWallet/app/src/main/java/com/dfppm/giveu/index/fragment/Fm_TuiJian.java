package com.dfppm.giveu.index.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfppm.giveu.base.BaseFragment;
import com.dfppm.giveu.base.BaseLayout;

/**
 * Created by 508632 on 2016/12/13.
 */

public class Fm_TuiJian extends BaseFragment{

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		BaseLayout baseLayout = new BaseLayout(mBaseContext);
		baseLayout.showLoading();
		return baseLayout;
	}
	@Override
	protected void setListener() {

	}

	@Override
	protected void setData() {

	}



}

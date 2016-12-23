package com.dfppm.giveu.index.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.dfppm.giveu.R;
import com.dfppm.giveu.base.BaseActivity;
import com.dfppm.giveu.index.adapter.WelcomePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends BaseActivity{

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.welcome);
		baseLayout.hideTopBar();

		startViewPager();
	}

	@Override
	public void setListener() {

	}

	@Override
	public void setData() {

	}


	protected void startViewPager() {
		ViewPager viewpager = (ViewPager) this.findViewById(R.id.viewpager);
		LinearLayout ll_dot = (LinearLayout) findViewById(R.id.ll_dot);

		WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(new WelcomePagerAdapter.WelcomePagerAdapterListener() {
			@Override
			public void clickInto() {
				setResult(0);
				finish();
			}
		},getLayoutInflater(), ll_dot);

		List<Integer> imgUrls = new ArrayList<Integer>();
		imgUrls.add(R.drawable.welcome_01);
		imgUrls.add(R.drawable.welcome_02);
		imgUrls.add(R.drawable.welcome_03);

		welcomePagerAdapter.setData(imgUrls);
		viewpager.setAdapter(welcomePagerAdapter);
		viewpager.setOnPageChangeListener(welcomePagerAdapter.new MyOnPageChangeListener());
	}


}

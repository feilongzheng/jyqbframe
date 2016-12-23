package com.dfppm.giveu.index.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.dfppm.giveu.R;
import com.dfppm.giveu.base.BaseActivity;
import com.dfppm.giveu.base.BasePresenter;
import com.dfppm.giveu.index.fragment.Fm_Find;
import com.dfppm.giveu.index.fragment.Fm_Grow;
import com.dfppm.giveu.index.fragment.Fm_TuiJian;
import com.dfppm.giveu.index.fragment.Fm_me;
import com.dfppm.giveu.presenter.LoginPresenter;
import com.dfppm.giveu.presenter.constract.ILoginView;

public class MainActivity extends BaseActivity implements ILoginView{
	public Fm_TuiJian fm_rank;
	public Fm_Grow fm_grow;
	public Fm_Find fm_find;
	public Fm_me fm_me;

	private FragmentManager manager;
	private int nowPosition;
	long exitTime;
	LoginPresenter loginPresenter ;


	@Override
	protected BasePresenter[] initPresenters() {
		loginPresenter = new LoginPresenter(this);
		return new BasePresenter[]{loginPresenter};
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);

		baseLayout.hideTopBar();
		manager = getSupportFragmentManager();
	}

	@Override
	public void setListener() {

	}

	@Override
	public void setData() {
		showFragment(0);
	}

	/**
	 * 显示fragment
	 *
	 * @param index
	 */
	private void showFragment(int index) {
		nowPosition = index;

		hideFagment();

		switch (index) {
			case 0:
				if (fm_rank == null) {
					fm_rank = new Fm_TuiJian();
					if (!fm_rank.isAdded()) {
						manager.beginTransaction().add(R.id.mContainer, fm_rank).commitAllowingStateLoss();
					}
				} else {
					manager.beginTransaction().show(fm_rank).commitAllowingStateLoss();
				}
				break;
			case 1:
				if (fm_find == null) {
					fm_find = new Fm_Find();
					if (!fm_find.isAdded()) {
						manager.beginTransaction().add(R.id.mContainer, fm_find).commitAllowingStateLoss();
					}
				} else {
					manager.beginTransaction().show(fm_find).commitAllowingStateLoss();
				}
				break;
			case 2:
				if (fm_grow == null) {
					fm_grow = new Fm_Grow();
					if (!fm_grow.isAdded()) {
						manager.beginTransaction().add(R.id.mContainer, fm_grow).commitAllowingStateLoss();
					}
				} else {
					manager.beginTransaction().show(fm_grow).commitAllowingStateLoss();
				}
				break;
			case 3:
				if (fm_me == null) {
					fm_me = new Fm_me();
					if (!fm_me.isAdded()) {
						manager.beginTransaction().add(R.id.mContainer, fm_me).commitAllowingStateLoss();
					}
				} else {
					manager.beginTransaction().show(fm_me).commitAllowingStateLoss();
				}
				break;
		}
	}

	/**
	 * 隐藏fragment:
	 */
	private void hideFagment() {
		if (fm_rank != null && fm_rank.isAdded()) {
			manager.beginTransaction().hide(fm_rank).commitAllowingStateLoss();
		}
		if (fm_grow != null && fm_grow.isAdded()) {
			manager.beginTransaction().hide(fm_grow).commitAllowingStateLoss();
		}
		if (fm_find != null && fm_find.isAdded()) {
			manager.beginTransaction().hide(fm_find).commitAllowingStateLoss();
		}
		if (fm_me != null && fm_me.isAdded()) {
			manager.beginTransaction().hide(fm_me).commitAllowingStateLoss();
		}
	}

	// 返回键事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(this, R.string.tips_exit, Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
//                MobclickAgent.onKillProcess(mBaseContext);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onLoginSuccess() {

	}

	@Override
	public void onLoginFail() {

	}
}

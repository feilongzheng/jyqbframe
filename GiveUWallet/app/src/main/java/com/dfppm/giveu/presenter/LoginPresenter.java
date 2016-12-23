package com.dfppm.giveu.presenter;

import com.dfppm.giveu.base.BasePresenter;
import com.dfppm.giveu.presenter.constract.ILoginView;

/**
 * Created by 508632 on 2016/12/20.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

	ILoginView loginView;

	public LoginPresenter(ILoginView iView) {
		init(iView);
	}

	public void Login(String name, String password) {
//		ApiImpl.getInstance().testRequestAgent();
		loginView.onLoginFail();
		loginView.onLoginSuccess();
	}

	@Override
	public void init(ILoginView view) {
		loginView = view;
	}




}

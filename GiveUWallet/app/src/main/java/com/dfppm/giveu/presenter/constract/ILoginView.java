package com.dfppm.giveu.presenter.constract;

import com.dfppm.giveu.base.IView;

/**
 * 暴露某个activity特有的处理view的方法
 */

public interface ILoginView extends IView {
		void onLoginSuccess();
		void onLoginFail();

}

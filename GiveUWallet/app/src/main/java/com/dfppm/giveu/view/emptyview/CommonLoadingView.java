package com.dfppm.giveu.view.emptyview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.mynet.BaseBean;
import com.android.volley.mynet.NetworkCode;
import com.dfppm.giveu.R;
import com.dfppm.giveu.utils.DensityUtils;
import com.dfppm.giveu.utils.StringUtils;
import com.dfppm.giveu.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: loadingView
 */
public class CommonLoadingView extends RelativeLayout implements OnClickListener {

    private Activity mContext;

    public static int DEFAULT_ICON = R.drawable.base_empty_default_icon;// 默认显示图标

    @ViewInject(R.id.ll_loading)
    private LinearLayout ll_loading;
    @ViewInject(R.id.rl_empty)
    private RelativeLayout rl_empty;

    @ViewInject(R.id.iv_loading)
    private ImageView iv_loading;
    @ViewInject(R.id.icon_state)
    private ImageView icon_state;
    @ViewInject(R.id.txt_message)
    private TextView txt_message;
    @ViewInject(R.id.txt_message_samll)
    private TextView txt_message_samll;
    @ViewInject(R.id.icon_refesh)
    private ImageView icon_refesh;

    public boolean isLoading = false;
    private boolean isFirstRequest = true;
    /**
     * 异常码
     */
    private static Map<Integer, String> ExceptionMap = new HashMap<Integer, String>();// 异常code和异常消息对应的map
    /**
     * 错误码
     */
    public static Map<String, String> errorMap = new HashMap<String, String>();
    /**
     * contentView
     */
    private View contentView;

    static {
        ExceptionMap.put(NetworkCode.NETWORK_NOLINK_CODE, "网络不给力!");
        ExceptionMap.put(NetworkCode.NETWORK_TIMEOUT_CODE, "网络连接超时!");
        ExceptionMap.put(NetworkCode.NETWORK_SERVERERROR_CODE, "服务器出错了!");

        errorMap.put(NetworkCode.NETWORK_ERROR_CODE1, "客户端提交的参数有误");
        errorMap.put(NetworkCode.NETWORK_ERROR_CODE2, "服务端异常");
        errorMap.put(NetworkCode.NETWORK_ERROR_CODE3, "用户被禁用");
        errorMap.put(NetworkCode.NETWORK_ERROR_CODE4, "调用已过期的接口");
        errorMap.put(NetworkCode.NETWORK_ERROR_CODE5, "用户在其他设备登陆");

    }

    public CommonLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {//在ide的layout视图中不解析
            return;
        }
        mContext = (Activity) context;
        LayoutInflater mInflater = mContext.getLayoutInflater();
        contentView = mInflater.inflate(R.layout.view_common_loading, this, true);
        ViewUtils.inject(this);
        initView();

    }

    public void initView() {
        // 设置监听事件
        if (icon_refesh != null) {
            RelativeLayout.LayoutParams params = (LayoutParams) icon_refesh.getLayoutParams();
            params.width = (int) DensityUtils.getViewHeight(340);
            params.height = (int) DensityUtils.getViewHeight(120);
            icon_refesh.setLayoutParams(params);
            icon_refesh.setOnClickListener(this);
        }
        ll_loading.setOnClickListener(this);
        rl_empty.setOnClickListener(this);

        final AnimationDrawable mLoadingAinm = (AnimationDrawable) iv_loading.getBackground();
        iv_loading.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mLoadingAinm.start();
                return true;
            }
        });
    }

    /**
     * 显示加载框
     */
    public void showLoading() {
        if (isFirstRequest) {
            ll_loading.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
            isLoading = true;
        }
    }

    /**
     * 隐藏加载框，加载结束
     */
    public void disLoading() {
        ll_loading.setVisibility(View.GONE);
        rl_empty.setVisibility(View.GONE);
        isLoading = false;
        isFirstRequest = false;
    }

    /**
     * 显示自己定义的空界面
     */
    public void showEmptyCustom() {
        if (onDealEmptyContentListener != null && contentView != null) {
            onDealEmptyContentListener.onDealEmptyContent(contentView);
        }
    }

    /**
     * 数据加载为空，显示全屏
     *
     * @param iconResId 显示的图片资源
     * @param message   显示的信息
     */
    public void showEmptyFullScreen(int iconResId, String message, String meaageSamll) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rl_empty.setLayoutParams(params);
        rl_empty.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        setViewState(iconResId, message, meaageSamll);
    }

    public void showEmptyFullScreen(String message, String meaageSamll) {
        showEmptyFullScreen(DEFAULT_ICON, message, meaageSamll);
    }

    public void showEmptyFullScreen(EmptyType emptyType) {
        showEmptyFullScreen(emptyType.icon, emptyType.message, emptyType.messageSub);
    }

    public void showEmptyFullScreen() {
        showEmptyFullScreen(EmptyType.OPT_DEFAULT);
    }


    /**
     * 数据为空时,warpContent
     */
    public void showEmpty(int iconResId, String message, String meaageSamll) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl_empty.setLayoutParams(params);
        rl_empty.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));

        setViewState(iconResId, message, meaageSamll);
    }

    public void showEmpty(String message, String meaageSamll) {
        showEmpty(DEFAULT_ICON, message, meaageSamll);
    }

    public void showEmpty(EmptyType emptyType) {
        showEmpty(emptyType.icon, emptyType.message, emptyType.messageSub);
    }

    public void showEmpty() {
        showEmpty(EmptyType.OPT_DEFAULT);
    }


    /**
     * 控件显示状态
     *
     * @param iconResId
     * @param message
     * @param meaageSamll
     */
    private void setViewState(int iconResId, String message, String meaageSamll) {
        setRefresh();

        if (iconResId == 0) {
            icon_state.setImageResource(DEFAULT_ICON);
        } else {
            icon_state.setImageResource(iconResId);
        }
        if (StringUtils.isNull(message)) {
            txt_message.setText(mContext.getString(R.string.tip_nodata));
        } else {
            txt_message.setText(message);
        }
        if (!StringUtils.isNull(meaageSamll)) {
            txt_message_samll.setText(meaageSamll);
        }
    }

    /**
     * 设置刷新
     */
    private void setRefresh() {
        isLoading = false;
        ll_loading.setVisibility(View.GONE);
        rl_empty.setVisibility(View.VISIBLE);

        // 刷新
        if (onClickReloadListener != null) {
            icon_refesh.setVisibility(View.VISIBLE);
        } else {
            icon_refesh.setVisibility(View.GONE);
        }
    }

    /**
     * 当返回信息错误时的处理
     */
    public void showError(BaseBean errorBean) {
        int resultCode = BaseBean.getResultCode(errorBean.status);
        if (isLoading) {
            try {
                if (ExceptionMap.containsKey(resultCode)) {
                    if (resultCode == NetworkCode.NETWORK_NOLINK_CODE || resultCode == NetworkCode.NETWORK_TIMEOUT_CODE) {// 没有网络
                        showEmptyFullScreen(R.drawable.icon_network_error, ExceptionMap.get(resultCode), null);
                    } else if (resultCode == NetworkCode.NETWORK_SERVERERROR_CODE) {// 服务器错误
                        showEmptyFullScreen(DEFAULT_ICON, ExceptionMap.get(resultCode), null);
                    }
                } else {
                    showEmptyFullScreen(DEFAULT_ICON, errorBean.message, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                disLoading();
            }
        } else {
            try {
                String message = "";
                if (ExceptionMap.containsKey(resultCode)) {
                    message = ExceptionMap.get(resultCode);
                } else {
                    message = errorBean.message;
                }
                ToastUtils.showShortToast( message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 弹出错误信息
     *
     * @param error
     */
    public static void showErrorToast(BaseBean error) {
        int resultCode = BaseBean.getResultCode(error.status);
        try {
            String message = "";
            if (ExceptionMap.containsKey(resultCode)) {
                message = ExceptionMap.get(resultCode);
            } else {
                message = error.message;
            }
            ToastUtils.showShortToast( message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.icon_refesh:// 点击刷新
                if (onClickReloadListener != null) {
                    onClickReloadListener.onClickReload();
                }
                break;
        }
    }

    // 点击刷新按钮的回调
    private OnClickReloadListener onClickReloadListener;

    public interface OnClickReloadListener {
        public void onClickReload();
    }

    public void setOnClickReloadListener(OnClickReloadListener onClickReloadListener) {
        this.onClickReloadListener = onClickReloadListener;
    }

    private OnDealEmptyContentListener onDealEmptyContentListener;

    public void setOnDealEmptyContentListener(OnDealEmptyContentListener onDealEmptyContentListener) {
        this.onDealEmptyContentListener = onDealEmptyContentListener;
    }

    /**
     * 自己处理空白内容
     */
    public interface OnDealEmptyContentListener {
        public void onDealEmptyContent(View contentView);
    }




}

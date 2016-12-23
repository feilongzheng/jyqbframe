package com.dfppm.giveu.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.mynet.BaseBean;
import com.dfppm.giveu.R;
import com.dfppm.giveu.view.emptyview.CommonLoadingView;

/**
 * Created by 508632 on 2016/12/8.
 */
/**
 * 默认显示top bar的页面布局框架
 */
public class BaseLayout extends LinearLayout {

    public BaseLayout(Context context) {
        super(context);
        init(context);
    }

    public BaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Context mContext;
    public RelativeLayout baselayout_topbar;
    /**
     * top bar 的返回图标
     */
    public ImageView top_tab_left_image;
    /**
     * 外层框架中间包裹view的容器
     */
    public LinearLayout ll_baselayout_content;
    /**
     * top bar 中间的ImageView
     */
    public ImageView top_tab_center_image;
    /**
     * top bar 中间的TextView
     */
    public TextView top_tab_center_title;
    public CommonLoadingView clv;
    public ImageView top_tab_right_image;
    public TextView top_tab_right_text;


    private void init(Context context) {
        this.mContext = context;

        View base_layout = View.inflate(context, R.layout.base_layout, null);
        baselayout_topbar = (RelativeLayout) base_layout.findViewById(R.id.baselayout_topbar);
        top_tab_left_image = (ImageView) base_layout.findViewById(R.id.top_tab_left_image);
        top_tab_center_image = (ImageView) base_layout.findViewById(R.id.top_tab_center_image);
        top_tab_center_title = (TextView) base_layout.findViewById(R.id.top_tab_center_title);
        top_tab_right_image = (ImageView) base_layout.findViewById(R.id.top_tab_right_image);
        top_tab_right_text = (TextView) base_layout.findViewById(R.id.top_tab_right_text);

        ll_baselayout_content = (LinearLayout) base_layout.findViewById(R.id.ll_baselayout_content);
        clv = (CommonLoadingView) base_layout.findViewById(R.id.clv);
        super.addView(base_layout);
    }

    /**
     * 默认topbar是显示的
     */
    public void hideTopBar(){
        baselayout_topbar.setVisibility(View.GONE);
    }

	/**
     * 注意addview被重写了
     */
    @Override
    public void addView(View child) {
        if (ll_baselayout_content.getChildCount() > 0)
            ll_baselayout_content.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ll_baselayout_content.addView( child , params);
    }

    /**
     * 修改top bar 返回键头的点击事件
     * @param onClickListener
     */
    public void setBackClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            top_tab_left_image.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置页面的title
     * @param pageTitle
     */
    public void setTitle(String pageTitle){
        top_tab_center_title.setText(pageTitle);
    }

	/**
     * 显示loading页面
     */
    public void showLoading(){
        clv.showLoading();
    }

    /**
     * 隐藏loading页面
     */
    public void disLoading(){
        clv.disLoading();
    }

    public void showEmpty(){
        clv.showEmpty();
    }

    public void showError(BaseBean errorBean){
        clv.showError(errorBean);
    }







}

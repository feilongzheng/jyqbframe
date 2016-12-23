package com.dfppm.giveu.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dfppm.giveu.utils.DensityUtils;


/**
 * my dialog for many scene
 *
 * @date 2014-10-26
 */
public class CustomDialog extends Dialog {

	protected Context mContext = null;
    protected View mContentView;
    protected int mTheme = 0, mWindowGravity =0;
    protected boolean mIsFullScreen = false, mIsWrapContent = true;


	/**
	 * （match screen width or have a little margin at left and right）and "mWindowGravity" in screen;
     * @param context
     * @param layout
     * @param style
     * @param isFullScreen
     */
	public CustomDialog(Context context, int layout, int style, int windowGravity, boolean isFullScreen) {
		super(context, style);
		
		View contentView  = LayoutInflater.from(context).inflate(layout, null);
        init(context, contentView, style, windowGravity, isFullScreen, false);
	}
	
	/**
	 * （match screen width or have a little margin at left and right）and "mWindowGravity" in screen;
     * @param context
     * @param style
     * @param isFullScreen
     */
	public CustomDialog(Context context, View contentView, int style, int windowGravity, boolean isFullScreen) {
		super(context, style);

        init(context, contentView, style, windowGravity, isFullScreen, false);
	}

    private void init(Context context, View contentView, int style, int windowGravity, boolean isFullScreen, boolean isWrapContent){
        this.mContext = context;
        this.mContentView = contentView;
        this.mTheme = style;
        this.mWindowGravity = windowGravity;
        this.mIsFullScreen = isFullScreen;
        this.mIsWrapContent = isWrapContent;

        createDialog();
    }

    private void createDialog() {
        setContentView(mContentView);

        Window window = getWindow();
        WindowManager.LayoutParams windowParams = getAttrs(window.getAttributes(), mContentView);
        window.setAttributes(windowParams);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
	}

    protected WindowManager.LayoutParams getAttrs(WindowManager.LayoutParams windowParams, View contentView) {
        if (mIsWrapContent){
            FrameLayout.LayoutParams wrapContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            contentView.setLayoutParams(wrapContentParams);
            windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }else {
            // 代码修改，FILL_PARENT也会留出一个边
            int screenWidth = DensityUtils.getWidth();
            if (mIsFullScreen) {
                windowParams.width = screenWidth;
            } else {
                windowParams.width = screenWidth - DensityUtils.dip2px( 60.0f);
            }
        }

        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.gravity = mWindowGravity;

        return windowParams;
    }


    @Override
    public void show() {
        if (this != null && !this.isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (this != null && this.isShowing()) {
            super.dismiss();
        }
    }



}

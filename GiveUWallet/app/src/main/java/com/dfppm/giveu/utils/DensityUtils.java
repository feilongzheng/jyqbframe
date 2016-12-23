package com.dfppm.giveu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.widget.RelativeLayout;


import com.dfppm.giveu.base.BaseApplication;
import com.lidroid.xutils.util.LogUtils;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * 测量，测量单位转化
 */
public class DensityUtils {
    /**
     * px转DIP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = BaseApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 获取屏幕宽度
     *
     * @date 2013年7月23日
     */
    public static int getWidth() {
        return BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @date 2013年7月23日
     */
    public static int getHeight() {
        return BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据屏幕宽度来计算view的高
     * @param px      1920x1080 下对应的view高度
     * @return view的高度
     */
    public static float getViewHeight(int px) {
        int h = getHeight();
        return px * (getWidth() / 1080f);
    }

    /**
     * 获取底部蓝高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        LogUtils.i("Navi height:" + height);
        return height;
    }

    /**
     * 底部蓝是否存在
     *
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;

    }

    /**
     * 获取当前Actvity的截图
     *
     * @param activity
     */
    @SuppressWarnings("deprecation")
    public static void takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        LogUtils.i("statusBarHeight:" + statusBarHeight + "   width" + width + "   height:" + height);

        Bitmap b = null;
        if (b1 != null) {
            if (checkDeviceHasNavigationBar(activity)) {
                b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight - getNavigationBarHeight(activity));
            } else {
                b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
            }
            if (b != null) {
                view.destroyDrawingCache();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(FileUtils.getPengSiCache_CacheDir().getAbsolutePath().concat("/screenshot.jpg"));
                    if (null != fos) {
                        b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    b.recycle();
                    b1.recycle();
                    b = null;
                    b1 = null;
                }
            }
        }
    }




}

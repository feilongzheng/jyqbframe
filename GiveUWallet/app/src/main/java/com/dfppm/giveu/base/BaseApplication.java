package com.dfppm.giveu.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.mynet.FileRequestAgent;
import com.android.volley.mynet.RequestAgent;
import com.android.volley.toolbox.Volley;
import com.dfppm.giveu.R;
import com.dfppm.giveu.utils.Const;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;
    public List<Activity> undestroyActivities;
    private static PauseOnScrollListener imageLoaderPauseOnScrollListener;
    private RequestAgent requestAgen;
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        mInstance = this;
        undestroyActivities = new ArrayList<Activity>();

        initLog();
        initX5webview();
        initPush();
        initUmeng();
        initImageLoader();
    }

    private void initLog() {
        LogUtils.allowI = Const.isDebug;
    }

    private void initX5webview() {
        QbSdk.initX5Environment(this.getApplicationContext(), new QbSdk.PreInitCallback(){

            @Override
            public void onCoreInitFinished() {
                LogUtils.i("onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                LogUtils.i("onViewInitFinished=" + b);
            }
        });
    }

    private void initUmeng() {
    }

    private void initPush() {
        JPushInterface.setDebugMode(Const.isDebug); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }



    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        // LogUtils.i(cacheDir.getAbsolutePath());
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.color.white).showImageForEmptyUri(R.color.white).showImageOnFail(R.color.white).cacheInMemory(false).cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoaderConfiguration cofig = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPoolSize(4)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // default
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024))
                // .memoryCache(new WeakMemoryCache())
                // .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                // default
                // .diskCache(new UnlimitedDiscCache(cacheDir))
                // default
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100).diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // default
                // .imageDownloader(new
                // BaseImageDownloader(getApplicationContext())) // default
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000))// connectTimeout 5s readTimeout 30s
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // // default
                // .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(cofig);

    }

    public static PauseOnScrollListener getImageLoaderPauseOnScrollListener() {
        if (imageLoaderPauseOnScrollListener == null) {
            imageLoaderPauseOnScrollListener = new PauseOnScrollListener(ImageLoader.getInstance(), false, false);
        }

        return imageLoaderPauseOnScrollListener;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this.getInstance());
        }
        return mRequestQueue;
    }

    public RequestAgent getRequestAgen() {
        if (requestAgen == null) {
            requestAgen = new RequestAgent();
        }
        return requestAgen;
    }

    private FileRequestAgent fileRequestAgen;

    public FileRequestAgent getFileRequestAgen() {
        if (fileRequestAgen == null) {
            fileRequestAgen = new FileRequestAgent();
        }
        return fileRequestAgen;
    }


}

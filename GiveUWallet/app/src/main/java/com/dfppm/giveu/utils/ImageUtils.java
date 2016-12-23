package com.dfppm.giveu.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.dfppm.giveu.R;
import com.dfppm.giveu.base.BaseApplication;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 */
public class ImageUtils {

    /**
     * 压缩、旋转 图片文件
     *
     * @param imagePath 文件路径
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeScaleImage(String imagePath, int width, int height) {
        BitmapFactory.Options bitmapOptions = getBitmapOptions(imagePath);
        int scale = calculateInSampleSize(bitmapOptions, width, height);
        LogUtils.i("original wid" + bitmapOptions.outWidth + " original height:" + bitmapOptions.outHeight + " sample:" + scale);
        bitmapOptions.inSampleSize = scale;
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap scaleBitmap = BitmapFactory.decodeFile(imagePath, bitmapOptions);
        int degree = readPictureDegree(imagePath);
        Bitmap rotateBitmap = null;
        if (scaleBitmap != null && degree != 0) {
            rotateBitmap = rotateBitmap(scaleBitmap,degree);
            scaleBitmap.recycle();
            scaleBitmap = null;
            return rotateBitmap;
        } else {
            return scaleBitmap;
        }
    }

    /**
     * 压缩、旋转 图片文件
     *
     * @param path             文件路径
     * @param isNeedDefaultPic 如果压缩失败，是否需要默认图片
     * @return
     */
    public static Bitmap decodeScaleImage(String path, boolean isNeedDefaultPic) {
        Bitmap bitmap = decodeScaleImage(path, 480, 800);
        if (bitmap == null && isNeedDefaultPic) {
            try {
                bitmap = BitmapFactory.decodeResource(BaseApplication.getInstance().getResources(), R.drawable.bg_photobg_fang);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 压缩、旋转 图片文件
     *
     * @param path 文件路径
     * @return
     */
    public static Bitmap decodeScaleImage(String path) {
        return decodeScaleImage(path, true);
    }

    /**
     * 读取图片文件旋转角度
     *
     * @param path 图片的路径
     * @return
     */
    public static int readPictureDegree(String path) {
        short degress = 0;
        try {
            ExifInterface var2 = new ExifInterface(path);
            int var3 = var2.getAttributeInt("Orientation", 1);
            switch (var3) {
                case 3:
                    degress = 180;
                case 4:
                case 5:
                case 7:
                default:
                    break;
                case 6:
                    degress = 90;
                    break;
                case 8:
                    degress = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return degress;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /**
     * 旋转bitmap
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate((float)degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 获取bitmap属性
     *
     * @param imagePath
     * @return
     */
    public static BitmapFactory.Options getBitmapOptions(String imagePath) {
        BitmapFactory.Options var1 = new BitmapFactory.Options();
        var1.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, var1);
        return var1;
    }

    /**
     * 保存图片 通知系统扫描
     *
     * @param activity
     * @param newImageFile
     * @param isInsertToGallary 是否插入系统相册
     * @param isScanFile        是否扫描原来图片
     * @return
     */
    public static String notifysaveImage(Activity activity, File newImageFile, boolean isInsertToGallary, boolean isScanFile) {
        String uri = "";
        if (activity == null || newImageFile == null || newImageFile.length() == 0) {
            return uri;
        }
        try {
            if (isInsertToGallary) {
                uri = MediaStore.Images.Media.insertImage(activity.getContentResolver(), newImageFile.getAbsolutePath(), newImageFile.getName(), "");
            }
            LogUtils.i("uri:" + uri);
            LogUtils.i("ewImageFile.getAbsolutePath():" + newImageFile.getAbsolutePath());
            if (isScanFile) {
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + newImageFile.getAbsolutePath())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * 通知系统相册
     *
     * @param activity
     * @param newImageFile
     */
    public static void notifysaveImage(Activity activity, File newImageFile) {
        notifysaveImage(activity, newImageFile, false, true);
    }

    /**
     * 加载
     *
     * @param url
     * @param intoImageView
     * @param options
     * @param listener
     */
    public static void loadImage(String url, ImageView intoImageView, DisplayImageOptions options, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(url, intoImageView, options, listener);
    }

    /**
     * 加载网络图片 centerCrop
     *
     * @param url
     * @param placeholderDrawableId
     * @param errorDrawableId
     * @param intoImageView
     */
    public static void loadImage(String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView, ImageLoadingListener listener) {
        if (intoImageView == null) {
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(placeholderDrawableId)
                .showImageForEmptyUri(errorDrawableId)
                .showImageOnFail(errorDrawableId)
                .build();
        loadImage(url, intoImageView, options, listener);
    }

    public static void loadImage(String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView) {
        loadImage(url, placeholderDrawableId, errorDrawableId, intoImageView, null);
    }

    public static void loadImageCrop(String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView) {
        if (intoImageView != null) {
            intoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        loadImage(url, placeholderDrawableId, errorDrawableId, intoImageView);
    }

    public static void loadImage(String url, int placeholderDrawableId, ImageView intoImageView) {
        loadImage(url, placeholderDrawableId, placeholderDrawableId, intoImageView);
    }

    /**
     * 加載圓角圖片
     *
     * @param url
     * @param placeholderDrawableId
     * @param errorDrawableId
     * @param intoImageView
     * @param cornerRadius          圆角弧度
     */
    public static void loadImageWithCorner(String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView, int cornerRadius) {
        if (intoImageView == null) {
            return;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(placeholderDrawableId)
                .showImageForEmptyUri(errorDrawableId)
                .showImageOnFail(errorDrawableId)
                .displayer(new RoundedBitmapDisplayer(cornerRadius))
                .build();

        ImageLoader.getInstance().displayImage(url, intoImageView, options);
    }

    public static void loadImageWithCorner(String url, int placeholderDrawableId, ImageView intoImageView, int cornerRadius) {
        loadImageWithCorner(url, placeholderDrawableId, placeholderDrawableId, intoImageView, cornerRadius);
    }

    /**
     * 加载本地图片
     *
     * @param localImagePath
     * @param imageView
     */
    public static void loadImageLocal(final String localImagePath, final int placeholderDrawableId, int errorDrawableId, final ImageView imageView, ImageLoadingListener listener) {
        if (imageView == null) {
            return;
        }
        DisplayImageOptions localPicoptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(placeholderDrawableId)
                .showImageForEmptyUri(errorDrawableId)
                .showImageOnFail(errorDrawableId)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        loadImage("file://" + localImagePath, imageView, localPicoptions, listener);
    }

    public static void loadImageLocal(final String localImagePath, final int placeholderDrawableId, int errorDrawableId, final ImageView imageView) {
        loadImageLocal(localImagePath, placeholderDrawableId, errorDrawableId, imageView, null);
    }

    public static void loadImageLocal(final String localImagePath, ImageView imageView) {
        loadImageLocal(localImagePath, R.drawable.bg_photobg_fang, R.drawable.bg_photobg_fang, imageView);
    }

    /**
     * 加載本地圓角
     *
     * @param localImagePath
     * @param placeholderDrawableId
     * @param errorDrawableId
     * @param imageView
     * @param cornerRadius
     */
    public static void loadImageLocalConner(final String localImagePath, final int placeholderDrawableId, int errorDrawableId, final ImageView imageView, int cornerRadius) {
        if (imageView == null) {
            return;
        }
        DisplayImageOptions localPicoptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(placeholderDrawableId)
                .showImageForEmptyUri(errorDrawableId)
                .showImageOnFail(errorDrawableId)
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(cornerRadius))
                .build();

        loadImage("file://" + localImagePath, imageView, localPicoptions, null);
    }

    public static Bitmap getViewBitmap(View view, int width, int height) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }









}
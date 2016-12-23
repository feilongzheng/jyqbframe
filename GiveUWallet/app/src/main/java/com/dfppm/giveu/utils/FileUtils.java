package com.dfppm.giveu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;


import com.dfppm.giveu.base.BaseApplication;
import com.lidroid.xutils.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 操作文件相关的方法集合
 */
public class FileUtils {
    private static final String cache_root = getSDcardPath();
    private static final String cache_canDelete = cache_root + "//canDelete";
    private static final String cache_canDelete_image = cache_canDelete + "//image";
    private static float cacheCanDeleteSize;


    public static File saveBitmap(Bitmap bm, String picName) throws IOException {
        File f = new File(getPengSiCache_CacheDir(), picName);
        if (f.exists()) {
            f.delete();
        }

        return compressBmpToFile(bm, f);
    }

    public static File saveBitmapInAction(Bitmap bm, String picName) {
        File f = new File(getDirFile(cache_canDelete_image), picName);
        if (f.exists()) {
            f.delete();
        }

        return compressBmpToFile(bm, f);
    }


    public static File compressBmpToFile(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 300) {
            baos.reset();
            options -= 5;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public static void createFile(Context context, File file, int drawableRes) {
        try {
            file.createNewFile();
            Bitmap pic = BitmapFactory.decodeResource(context.getResources(), drawableRes);
            FileOutputStream fos = new FileOutputStream(file);
            pic.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转照片
     *
     * @param camerafile
     */
    public static void rotateCameraFile(File camerafile, Context context) {
        try {
            Bitmap bitmap = ImageUtils.decodeScaleImage(camerafile.getAbsolutePath(), DensityUtils.getWidth(), DensityUtils.getHeight());
            FileUtils.compressBmpToFile(bitmap, camerafile);
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getSDcardPath() {
        String sDir;
        if (isHasSdcard()) {
            sDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"//cache";
        } else {
//            /data/data/com.dfppm.giveu/cache
            sDir = BaseApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath();
        }
        sDir = TextUtils.isEmpty(sDir) ? "" : sDir;
        return sDir;
    }

    public static File getDirFile(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        return f;
    }


    /**
     * 返回缓存文件夹
     *
     * @return
     */
    public static File getPengSiCache_CacheDir() {
        return getDirFile(cache_canDelete_image);
    }

    /**
     * 返回头像缓存文件夹
     *
     * @return
     */
    public static File getHeadImageDirFile() {
        return getDirFile(cache_canDelete_image);
    }
    /**
     * sd缓存的根目录
     * @return
     */
    public static File getCacheRootDirFile() {
        return getDirFile(cache_root);
    }



    /**
     * 获取文件大小
     *
     * @param f
     * @return
     */
    public static void getCacheFileSize(File f) {
        try {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    getCacheFileSize(flist[i]);
                } else {
                    cacheCanDeleteSize = cacheCanDeleteSize + flist[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i("cacheCanDeleteSize:" + cacheCanDeleteSize);
    }

    /**
     * 清楚缓存
     */
    public static void clearCacheInSetting() {
        try {
            deleteAllFile(getCacheRootDirFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheCanDeleteSize = 0;
    }

    public static void deleteAllFile(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteAllFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public static String getCacheMSize() {
        cacheCanDeleteSize = 0;
        getCacheFileSize(getCacheRootDirFile());
        return StringUtils.format2(cacheCanDeleteSize / 2 / 1024 / 1024);
    }


    public static void deleteDirInSelectImagesView() {
        File dir = new File(cache_canDelete_image);
        deleteAllFile(dir);
    }








}

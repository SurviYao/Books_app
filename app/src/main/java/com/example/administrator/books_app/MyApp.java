package com.example.administrator.books_app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import com.example.administrator.books_app.utils.SharedUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private List<Activity> activitys;
    public  static DisplayImageOptions options;
    @Override
    public void onCreate() {
        super.onCreate();
        activitys=new ArrayList<>();
        SharedUtil.init(getApplicationContext(),"book");
        //imageloader框架的配置信息
        ImageLoaderConfiguration configuration
                =new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                //设置每一次下载图片的最大数量为100
                .diskCacheFileCount(100)
                //将下载的图片名字进行MD5文件加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                //从手机内存中划分出多大的内存用来存储图片
                .diskCacheSize(50*1024*1024)
                //设置图片下载方式(以栈的形式进行下载)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPriority(3)
                .writeDebugLogs().build();

        ImageLoader.getInstance().init(configuration);


        options=new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.image_no)
                .showImageOnFail(R.mipmap.image_error)
                .showImageOnLoading(R.mipmap.image_loading)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();
    }

    /**
     * 添加打开了的activity
     * @param activity
     */
    public void addActivity(Activity activity){
        //需要保存的activity是否已经存在于集合中，
        // 不存在则添加
        if (!activitys.contains(activity)){
            activitys.add(activity);
        }
    }

    /**
     * 移除指定的activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if (activitys.contains(activity)){
            activitys.remove(activity);
            activity.finish();
        }
    }

    /**
     * 释放所有打开的界面
     */
    public void removeAllActivity(){
        for (Activity a :
                activitys) {
            a.finish();
        }
    }
}

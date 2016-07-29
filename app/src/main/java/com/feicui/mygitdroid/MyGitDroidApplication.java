package com.feicui.mygitdroid;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MyGitDroidApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化universalimageloader
        initImageLoader();
    }

    private void initImageLoader() {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.gif_loading)//如果url地址获取不到显示的图片
                .showImageOnFail(R.drawable.gif_loading)//如果显示图片失败
                .showImageOnLoading(R.drawable.gif_loading)//如果正在加载图片
                //该处报错，原因可能是两个框架的处理信息冲突导致
//                .displayer(new RoundedBitmapDisplayer(getResources().getDimensionPixelOffset(R.dimen.dp_8)))
                .build();
        //图片下载的配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheSize(5*1024*1024)
                .defaultDisplayImageOptions(options)
                .build();
        //获取实例初始化信息
        ImageLoader.getInstance().init(config);
    }
}

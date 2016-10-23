package com.mlab.trainings.lastfmapp;

import android.app.Application;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class LastFmApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		        .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
		        .memoryCacheSize(10 * 1024 * 1024)
		        .diskCacheSize(50 * 1024 * 1024)
		        .diskCacheFileCount(1000)
		        .writeDebugLogs()
		        .build();
		
		ImageLoader.getInstance().init(config);
	}
}

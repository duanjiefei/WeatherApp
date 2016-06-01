package com.tianci.weather;

import android.app.Application;
import android.content.Context;

/**
@Date : 2016年4月20日
@Author : Zhan Yu
@Description : TODO
*/
public class WeatherApplication extends Application
{
	public static Context context;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		context = getApplicationContext();
		WeatherConfig.setResolutionAndDpiDiv(context);
	}

}



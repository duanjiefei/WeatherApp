package com.tianci.weather.data;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.tianci.weather.Debugger;
import com.tianci.weather.WeatherApplication;
import android.content.Context;
import android.text.TextUtils;

/**
@Date : 2016年5月5日
@Author : Zhan Yu
@Description : TODO
*/
public class CachedWeather
{
	private File parentDirFile;
	private static CachedWeather instance = new CachedWeather();
	
	private CachedWeather()
	{
		parentDirFile = WeatherApplication.context.getDir("cached_weather", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
	}
	
	public static CachedWeather getInstance()
	{
		return instance;
	}

	public TCWeatherInfo getCachedWeatherInfo(String city)
	{
		TCWeatherInfo ret = null;
		String cachedPath = getWeatherInfoPath(city);
		if(!TextUtils.isEmpty(cachedPath))
		{
			Debugger.d("find cache : " + city);
			ret = (TCWeatherInfo) DataUtils.openObjectWithAbsolutePath(cachedPath);
		} else
		{
			Debugger.d("no cached find : " + city);
		}
		
		return ret;
	}
	
	public void saveWeatherInfo(TCWeatherInfo info)
	{
		if(info == null || info.baseInfo == null || TextUtils.isEmpty(info.baseInfo.city) || info.daysInfo == null || info.daysInfo.size() <= 0)
			return ;
		String filePath = parentDirFile.getAbsolutePath() + File.separator + info.baseInfo.city;
		File cityFile = new File(filePath);
		if(!cityFile.exists())
		{
			cityFile.mkdir();
			cityFile.setWritable(true, true);
			cityFile.setReadable(true, true);
			cityFile.setExecutable(true, true);
		}
		if(cityFile.exists())
		{
			String fileName =  filePath + File.separator + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH).format(new Date());
			DataUtils.saveObjectWithAbsolutePath(fileName, info);
			Debugger.d("save cached weather info success : " + info.baseInfo.city);
		} else
		{
			Debugger.d("create file fail : " + info.baseInfo.city);
		}
	}
	
	private String getWeatherInfoPath(String city)
	{ //同一天的4个小时内的缓存视为有效
		String ret = null;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//		int minute = Calendar.getInstance().get(Calendar.MINUTE);
//		int second = Calendar.getInstance().get(Calendar.SECOND);
		Debugger.d("currentTime : " + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH).format(new Date()));
		
		File cachedFile = getCachedFile(city);
		if(cachedFile != null && cachedFile.exists())
		{
			String fileName = cachedFile.getName();
			Debugger.d("cachedTime : " + fileName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
			try
			{
				Date date = sdf.parse(fileName);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				if(cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month && cal.get(Calendar.DAY_OF_MONTH) == day
						&& ((cal.get(Calendar.HOUR_OF_DAY) - hour) <= 4))
				{    
					ret = cachedFile.getAbsolutePath();
					Debugger.d("find valid cached file : " + ret);
				} else
				{
					Debugger.d("delete invalid cached file.");
					cachedFile.delete();
				}
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	private File getCachedFile(String city)
	{
		File cachedFile = null;
		String filePath = parentDirFile.getAbsolutePath() + File.separator + city;
		File cityFile = new File(filePath);
		if(cityFile.exists())
		{
			File[] cachedFiles = cityFile.listFiles();
			if(cachedFiles != null&& cachedFiles.length>0)
				cachedFile = cachedFiles[0];
		}
		return cachedFile;
	}
}



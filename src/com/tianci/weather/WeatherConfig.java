package com.tianci.weather;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
@Date : 2015年3月3日
@Author : Zhan Yu
@Description : TODO
 */
public class WeatherConfig
{	
	
	private static float cachedResolutionDiv = 1f;
	private static float cachedDpiDiv = 1f;
	
	
	/**
	 * 得到屏幕密度
	 */
    private static float getDisplayDensity(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        float density = dm.density;
        return density;
    }

    /**
     * 得到屏幕的宽度
     */
    private static int getDisplayWidth(Context context)
    {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        if (display == null)
        {
            return 1920;
        }
        return display.getWidth();
    }
    
	/**
	 * 设置分辨率
	 */
	public static void setResolutionAndDpiDiv(Context context)
	{
		int width = getDisplayWidth(context);
		cachedResolutionDiv = 1.0f;
		cachedDpiDiv = 1.0f;
        if(width == 3840)
        	cachedResolutionDiv = 0.5f;
        else if(width == 1920)
        	cachedResolutionDiv = 1;
        else if(width == 1366)
        	cachedResolutionDiv = 1.4f;
        else if(width == 1280)
        	cachedResolutionDiv = 1.5f;
        
        cachedDpiDiv = cachedResolutionDiv * getDisplayDensity(context);
        Debugger.d("set ResolutionDiv : " + cachedResolutionDiv);
        Debugger.d("set DpiDiv : " + cachedDpiDiv);
	}
	
	/**
	 * 获取自适应分辨率的布局大小
	 * @param value
	 * @return
	 */
	public static int getResolutionValue(int value)
	{
		return (int) (value <= 0 ? value : (value / cachedResolutionDiv));
	}
	
	/**
	 * 获取自适应屏幕密度的文本大小
	 * @param value
	 * @return
	 */
	public static int getDpiValue(int value)
	{
		return (int) (value <= 0 ? value : (value / cachedDpiDiv));
	}
}



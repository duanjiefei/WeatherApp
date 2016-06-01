package com.tianci.weather.data;

import com.tianci.weather.WeatherApplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 使用SharePreferance机制存储和读取数据
 * @author Zhan Yu
 *
 */
public class ShareDataManager
{
	final static String namespace = "weather";
	
    public static void setSharedValue(String name, String value)
    {
        try
        {
            if(value == null)
                return;
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace,
                    Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(name, value);
            editor.apply();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static String getSharedValue(String name, String defaultValue)
    {
        try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace, Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            return sp.getString(name, defaultValue);
        } catch (Exception e)
        {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    public static void setFloatValue(String name, float value)
    {
    	try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace,
            		Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat(name, value);
            editor.apply();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static float getFloatValue(String name, float defaultValue)
    {
        try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace, Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            return sp.getFloat(name, defaultValue);
        } catch (Exception e)
        {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    public static void setBooleanValue(String name, boolean value)
    {
    	try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace,
            		Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(name, value);
            editor.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static boolean getBooleanValue(String name, boolean defaultValue)
    {
        try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace, Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            return sp.getBoolean(name, defaultValue);
        } catch (Exception e)
        {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    public static void setIntValue(String name, int value)
    {
    	try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace,
            		Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(name, value);
            editor.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static int getIntValue(String name, int defaultValue)
    {
        try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences(namespace, Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            return sp.getInt(name, defaultValue);
        } catch (Exception e)
        {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    public static void setLongValue(String name, long value)
    {
    	try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences("tcsetting",
            		Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(name, value);
            editor.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static long getLongValue(String name, long defaultValue)
    {
        try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences("tcsetting", Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            return sp.getLong(name, defaultValue);
        } catch (Exception e)
        {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    /**
     * 清除所有的数据
     */
    public static void cleanAllShareValue()
    {
        try
        {
            SharedPreferences sp = WeatherApplication.context.getSharedPreferences("tcsetting", Context.MODE_WORLD_WRITEABLE  | Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
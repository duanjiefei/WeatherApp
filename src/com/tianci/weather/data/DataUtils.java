package com.tianci.weather.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tianci.weather.Debugger;
import com.tianci.weather.WeatherApplication;

import android.content.Context;

/**
 * @Date : 2016年4月26日
 * @Author : Zhan Yu
 * @Description : 数据工具类
 */
public class DataUtils
{
	@SuppressWarnings("deprecation")
	private final static String path = WeatherApplication.context.getDir("cache", Context.MODE_PRIVATE).getAbsolutePath();
	/**
	 * 序列化对象到本地
	 * @param objName 序列化的文件名称 
	 * @param obj
	 */
	public static void saveObject(String objName, Object obj)
	{
		try
		{
			FileOutputStream fi = new FileOutputStream(path + File.separator + objName);
			ObjectOutputStream os = new ObjectOutputStream(fi);
			os.writeObject(obj);
			os.flush();
			os.close();
		} catch (Exception e)
		{
			Debugger.e("saveObject >> " + objName + " <<  fail !, cause " + e.toString());
		}
	}

	/**
	 * 获取本地序列化对象
	 * @param objName 序列化的文件名称 
	 * @return
	 */
	public static Object openObject(String objName)
	{
		Object o = null;
		try
		{
			FileInputStream fs = new FileInputStream(path + File.separator + objName);
			ObjectInputStream oi = new ObjectInputStream(fs);
			o = (Object) oi.readObject();
			oi.close();
		} catch (Exception e)
		{
			Debugger.e("openObject >> " + objName + " <<  fail !, cause " + e.toString());
		} 
		return o;
	}
	
	/**
	 * 序列化对象到本地
	 * @param objName 序列化的文件名称 
	 * @param obj
	 */
	public static void saveObjectWithAbsolutePath(String objPath, Object obj)
	{
		try
		{
			FileOutputStream fi = new FileOutputStream(objPath);
			ObjectOutputStream os = new ObjectOutputStream(fi);
			os.writeObject(obj);
			os.flush();
			os.close();
		} catch (Exception e)
		{
			Debugger.e("saveObject >> " + objPath + " <<  fail !, cause " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 获取本地序列化对象
	 * @param objName 序列化的文件名称 
	 * @return
	 */
	public static Object openObjectWithAbsolutePath(String objPath)
	{
		Object o = null;
		try
		{
			FileInputStream fs = new FileInputStream(objPath);
			ObjectInputStream oi = new ObjectInputStream(fs);
			o = (Object) oi.readObject();
			oi.close();
		} catch (Exception e)
		{
			Debugger.e("openObject >> " + objPath + " <<  fail !, cause " + e.toString());
		} 
		return o;
	}
}

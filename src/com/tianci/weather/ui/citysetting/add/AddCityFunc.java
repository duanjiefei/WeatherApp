package com.tianci.weather.ui.citysetting.add;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tianci.weather.Debugger;
import com.tianci.weather.WeatherApplication;
import com.tianci.weather.data.DataUtils;
import com.tianci.weather.data.ShareDataManager;
import com.tianci.weather.ui.citysetting.CitySettingManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Date : 2015年3月13日
 * @Author : Zhan Yu
 * @Description : 城市设置工具
 */
public class AddCityFunc
{
	private static AddCityFunc instance = new AddCityFunc();
	
	private String DATABASE_PATH = null;

	private SQLiteDatabase db = null;
	private Cursor cursor = null;

	private AddCityFunc()
	{
		DATABASE_PATH = "/system/etc/mini_address.db";
	}

	public static AddCityFunc getInstance()
	{
		return instance;
	}
	/**
	 * 获取直辖市列表
	 * @return
	 */
	public List<String> getMunicipalityList()
	{
		return querySubWithCache(WeatherApplication.context.getDir("cache", 0)
				.getAbsolutePath()
				+ File.separator
				+ "set_location_municipality_of_china", "直辖市");
	}

	/**
	 * 获取省列表
	 */
	public List<String> getProvinceList()
	{
		return querySubWithCache(WeatherApplication.context.getDir("cache", 0)
				.getAbsolutePath()
				+ File.separator
				+ "set_location_province_of_china", "省份");
	}
	
	/**
	 * 获取特别行政区列表
	 */
	public List<String> getSpecialRegion()
	{
		return querySubWithCache(WeatherApplication.context.getDir("cache", 0)
				.getAbsolutePath()
				+ File.separator
				+ "set_location_sepcial_region_of_china", "特区");
	}

	/**
	 * 获取市列表
	 */
	public List<String> getCityList(String provinceName)
	{
		return querySubWithCache(WeatherApplication.context.getDir("cache", 0)
				.getAbsolutePath()
				+ File.separator
				+ "set_location_city_of_"
				+ provinceName, provinceName);
	}

	@Deprecated
	/**
	 * 获取区列表（在酷开系统5.0中不需要该功能）
	 */
	public List<String> getDistrictList(String cityName)
	{
		return querySubWithCache(WeatherApplication.context.getDir("cache", 0)
				.getAbsolutePath()
				+ File.separator
				+ "set_location_district_of_" + cityName, cityName);
	}

	/**
	 * 获取当前位置
	 * 
	 * @return 位置信息，格式：<b>省,市，比如 广东省,深圳市 </b> <br />
	 *         如果没有获取到当前位置信息，则返回 null.
	 */
	public String getCurrentLocation()
	{
		return CitySettingManager.getInstance().getCurrentCity();
	}

	/**
	 * 设置城市区域
	 * 
	 * @param 省
	 * @param city
	 *            市
	 */
	public void setLocation(String province, String city)
	{
		Debugger.d("setLocation : province=" + province + ", city=" + city);
		ShareDataManager.setSharedValue("set_location", province + "," + city);
	}

	private List<String> querySubWithCache(String cachePath, String parentName)
	{
		List<String> resultList = null;
		Object obj = DataUtils.openObject(cachePath);
		if (obj == null)
		{
			resultList = querySubAreas(parentName);
			if (resultList != null && resultList.size() > 0)
			{
				DataUtils.saveObject(cachePath, resultList);
			}
		} else
		{
			resultList = (List<String>) obj;
		}
		Debugger.d("querySubWithCache : " + parentName + ", return : " + (resultList == null ? "null" : ""+resultList.size()));
		return resultList;
	}

	private List<String> querySubAreas(String parentName)
	{
		try
		{
			Long start = System.currentTimeMillis();
			List<String> resultList = new ArrayList<String>();
//			db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
			db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
			String sql = "select * from " + "mini_address where pname='"
					+ parentName + "';";
			cursor = db.rawQuery(sql, null);
			if (cursor != null && cursor.getCount() > 0)
			{
				boolean hasData = cursor.moveToFirst();
				while (hasData)
				{
					String name = cursor.getString(0);
					resultList.add(name);
					hasData = cursor.moveToNext();
				}
			}
			if (cursor != null)
			{
				cursor.close();
				cursor = null;
			}
			if (db != null)
			{
				db.close();
				db = null;
			}

			Long end = System.currentTimeMillis();
			Debugger.i("querySubAreas costs : " + (end - start) + " ms.");

			if (resultList.size() > 0)
				return resultList;
			else
				return null;
		} catch (Exception e)
		{
			Debugger.i("querySubAreas exception : " + e.toString());
			return null;
		}
	}

}

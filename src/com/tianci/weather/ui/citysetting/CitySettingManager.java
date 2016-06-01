package com.tianci.weather.ui.citysetting;

import java.util.ArrayList;
import java.util.List;

import com.tianci.weather.Debugger;
import com.tianci.weather.data.DataUtils;
import com.tianci.weather.data.ShareDataManager;

import android.R.integer;
import android.text.TextUtils;

/**
@Date : 2016年4月26日
@Author : Zhan Yu
@Description : 城市数据管理
*/
public class CitySettingManager
{
	
	private static CitySettingManager instance = new CitySettingManager();
	private List<String> cityList;
	private String currentCity;
	
	private static final String KEY_CURRENT_CITY = "default_city";
	private static final String KEY_SAVED_CITY_LIST = "saved_city_list";
	
	@SuppressWarnings("unchecked")
	private CitySettingManager()
	{
		initCurrentCity();
		
		cityList = (List<String>) DataUtils.openObject("saved_city_list");
		if(cityList == null)
			cityList = new ArrayList<String>();
		if(!cityList.contains(currentCity))
			cityList.add(currentCity);
	}
	
	public static CitySettingManager getInstance()
	{
		return instance;
	}
	
	private void initCurrentCity()
	{
		String savedCity = ShareDataManager.getSharedValue(KEY_CURRENT_CITY, "");
		Debugger.d("savedCity=" + savedCity);
		if(TextUtils.isEmpty(savedCity))
		{
			savedCity = "深圳" ;
			saveCurrentCity(savedCity);
		}
		currentCity = savedCity;
	}
	
	/**改变list中的数据
	 * @param city
	 * @param index
	 */
	public void setIndexCity(String city, int index){
		if(cityList != null){
			cityList.set(index, city);
			saveCityList();
		}
	}
	
	/**
	 * 获取当前城市
	 * @param listener
	 */
	public String getCurrentCity()
	{
		return currentCity;
	}
	
	public void setCurrentCity(String curCity)
	{
		this.currentCity = curCity;
		saveCurrentCity(curCity);
	}
	
	private void saveCurrentCity(String curCity)
	{
		ShareDataManager.setSharedValue(KEY_CURRENT_CITY, curCity);
	}
	
	public List<String> getSavedCityList()
	{
		return cityList;
	}
	
	public void addCity(String city)
	{
		if(!cityList.contains(city))
		{
			cityList.add(city);
			saveCityList();
		}
	}
	
	public void deleteCity(String city)
	{
		if(cityList.contains(city))
		{
			cityList.remove(city);
			saveCityList();
		}
	}
	
	private void saveCityList()
	{
		DataUtils.saveObject(KEY_SAVED_CITY_LIST, cityList);
	}
}



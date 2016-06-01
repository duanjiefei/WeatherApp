package com.tianci.weather.data;

public class TCWeatherCitysettingShowInfo {

	public String cityNameString;
	public int[] weatherIcons;
	public int lowTemp;
	public int highTemp;
	
	public TCWeatherCitysettingShowInfo(String name, int[] icons, int lowTempVal, int highTempVal) {
		// TODO Auto-generated constructor stub
		cityNameString = name;
		weatherIcons = icons;
		lowTemp = lowTempVal;
		highTemp = highTempVal;
	}
	
	public String getCityName()
	{
		return cityNameString;
	}

}

package com.tianci.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tianci.weather.ui.citysetting.CitySettingLayout;
import com.tianci.weather.ui.citysetting.CitysettingShowAllCitiesView;

/**
@Date : 2016年4月21日
@Author : Zhan Yu
@Description : 城市编辑Activity
*/
public class CitySettingActivity extends Activity
{
	CitySettingLayout citySettingLayout;
	
	public String addCityName;
	
	private final int START_CITY_SELECT_ACTIVITY_REQUESCODE = 1;
	
	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		citySettingLayout = new CitySettingLayout(CitySettingActivity.this);
		setContentView(citySettingLayout);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case START_CITY_SELECT_ACTIVITY_REQUESCODE:
			if(resultCode == Activity.RESULT_OK){
				addCityName = data.getStringExtra("addCityName");
				citySettingLayout.setAddCityName(addCityName);
				Debugger.d("++++++++++addCityName=" + addCityName);
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CitysettingShowAllCitiesView.citysettingShowAllCitiesView.tcWeather.shutdown();
		Debugger.d("CitySettingActivity onDestroy");
		
	}

}



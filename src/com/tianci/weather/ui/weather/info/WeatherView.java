package com.tianci.weather.ui.weather.info;

import java.util.List;

import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCDayWeatherInfo;
import com.tianci.weather.data.TCWeatherDegree;
import com.tianci.weather.data.TCWeatherInfo;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
@Date : 2016年4月20日
@Author : Zhan Yu
@Description : 天气信息布局，包含温度，五天的天气信息
*/
public class WeatherView extends LinearLayout
{
	WeatherCurrentInfoView currentInfoView;
	FiveDayDegreeView fiveDayDegreeView;
	
	private List<TCDayWeatherInfo> daysInfo; // 未来五天的天气详情
	private TCWeatherDegree[] weDegree;// 未来五天温度
	
	public WeatherView(Context context)
	{
		super(context);
		setOrientation(LinearLayout.HORIZONTAL);
		
		currentInfoView = new WeatherCurrentInfoView(context);
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.leftMargin = WeatherConfig.getResolutionValue(230);
		currentInfoView.setLayoutParams(params);
		addView(currentInfoView);
		
		fiveDayDegreeView = new FiveDayDegreeView(context);
		LinearLayout.LayoutParams fiveDayDegreeViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		fiveDayDegreeViewParams.leftMargin = WeatherConfig.getResolutionValue(100);
		fiveDayDegreeViewParams.rightMargin = WeatherConfig.getResolutionValue(230);
		fiveDayDegreeView.setLayoutParams(fiveDayDegreeViewParams);
		addView(fiveDayDegreeView);
	}
	
	public void update(TCWeatherInfo weatherInfo)
	{
		currentInfoView.update(weatherInfo);
		daysInfo = weatherInfo.daysInfo;
		weDegree = new TCWeatherDegree[daysInfo.size()];
		//将趋势图中所需要的信息进行封装
		for (int i = 0; i < daysInfo.size(); i++) {
			if (weDegree[i] == null)
				weDegree[i] = new TCWeatherDegree();
			weDegree[i].date = daysInfo.get(i).date;
			weDegree[i].weekDay = daysInfo.get(i).weekDay;
			weDegree[i].wEnum = daysInfo.get(i).WEnum;
			weDegree[i].maxDegree = Float.parseFloat(daysInfo.get(i).MaxDegree);
			weDegree[i].minDegree = Float.parseFloat(daysInfo.get(i).MinDegree);
		}
		fiveDayDegreeView.setDegree(weDegree);
		
	}
	
	public void addCityName(String cityName){
		currentInfoView.addCityName(cityName);
	}
	
}



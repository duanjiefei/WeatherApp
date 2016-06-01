package com.tianci.weather.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tianci.weather.Debugger;

import android.text.TextUtils;

public class TCWeatherComposite
{
	public static TCWeatherInfo transFromJson(String jsonString)
	{
//		Debugger.d("transFromJson : " + jsonString);
		TCWeatherInfo weatherInfo  = null;
		if(!TextUtils.isEmpty(jsonString))
		{
			weatherInfo = new TCWeatherInfo();
			JSONObject parsedObj = JSON.parseObject(jsonString);
			
			JSONObject baseInfoObj = parsedObj.getJSONObject("baseinfo");
			TCWeatherBaseInfo baseInfo = new TCWeatherBaseInfo();
			weatherInfo.baseInfo = baseInfo;
			Set<Entry<String, Object>> set = baseInfoObj.entrySet();
			Debugger.d("entrySet.size=" + set.size());
			Iterator<Entry<String, Object>> ite = set.iterator();
			while(ite.hasNext())
			{
				Entry<String, Object> next = ite.next();
				Debugger.d("key==" + next.getKey() + ", value=" + next.getValue());
			}
			baseInfo.city = baseInfoObj.getString("city");
			Debugger.d("*************** city=" + baseInfoObj.getString("city"));
			baseInfo.date = baseInfoObj.getString("date");
			baseInfo.weekDay = baseInfoObj.getString("weekDay");
			baseInfo.publishTime = baseInfoObj.getString("publishTime");
			
			JSONArray daysJsonArray = parsedObj.getJSONArray("days");
			int size = daysJsonArray.size();
			List<TCDayWeatherInfo> daysInfo = new ArrayList<TCDayWeatherInfo>(size);
			weatherInfo.daysInfo = daysInfo;
			for(int i=0; i<size; i++)
			{
				JSONObject dayJsonObject = daysJsonArray.getJSONObject(i);
				TCDayWeatherInfo dayInfo = new TCDayWeatherInfo();
				daysInfo.add(dayInfo);
				dayInfo.city = dayJsonObject.getString("city");
				dayInfo.date = dayJsonObject.getString("date");
				dayInfo.weekDay = dayJsonObject.getString("weekDay");
				dayInfo.Weather = dayJsonObject.getString("Weather");
				dayInfo.WEnum = TCWeatherEnum.valueOf(dayJsonObject.getString("WEnum"));
				dayInfo.MaxDegree = dayJsonObject.getString("MaxDegree");
				dayInfo.MinDegree = dayJsonObject.getString("MinDegree");
				dayInfo.Wind = dayJsonObject.getString("Wind");
				dayInfo.Flow = dayJsonObject.getString("Flow");
				dayInfo.WashCar = dayJsonObject.getString("WashCar");
				dayInfo.WashCar_l = dayJsonObject.getString("WashCar_l");
				dayInfo.WashCar_s = dayJsonObject.getString("WashCar_s");
				dayInfo.Allergy = dayJsonObject.getString("Allergy");
				dayInfo.ClotheIndex = dayJsonObject.getString("ClotheIndex");
				dayInfo.ClotheAbstract = dayJsonObject.getString("ClotheAbstract");
				dayInfo.ClotheDetail = dayJsonObject.getString("ClotheDetail");
				dayInfo.yd = dayJsonObject.getString("yd");
				dayInfo.yd_l = dayJsonObject.getString("yd_l");
				dayInfo.yd_s = dayJsonObject.getString("yd_s");
				dayInfo.Ultravoilet = dayJsonObject.getString("Ultravoilet");
				dayInfo.Ultravoilet_l = dayJsonObject.getString("Ultravoilet_l");
				dayInfo.Ultravoilet_s = dayJsonObject.getString("Ultravoilet_s");
				dayInfo.gm = dayJsonObject.getString("gm");
				dayInfo.gm_l = dayJsonObject.getString("gm_l");
				dayInfo.gm_s = dayJsonObject.getString("gm_s");
				dayInfo.sd = dayJsonObject.getString("sd");
			}
			
		}
		return weatherInfo;
	}
//	
//	public static TCWeatherInfo trans(WeatherInfo orgInfo)
//	{
//		TCWeatherInfo targetInfo  = null;
//		if(orgInfo != null)
//		{
//			targetInfo = new TCWeatherInfo();
//			BaseInfo baseInfo = orgInfo.baseinfo;
//			if(baseInfo != null)
//			{
//				TCWeatherBaseInfo tBaseInfo = new TCWeatherBaseInfo();
//				targetInfo.baseInfo = tBaseInfo;
//				tBaseInfo.city = baseInfo.city;
//				tBaseInfo.date = baseInfo.date;
//				tBaseInfo.publishTime = baseInfo.publishTime;
//				tBaseInfo.weekDay = baseInfo.weekDay;
//			}
//			List<DayWeather> days = orgInfo.days;
//			if(days != null && days.size() > 0)
//			{
//				List<TCDayWeatherInfo> daysInfo = new ArrayList<TCDayWeatherInfo>(days.size());
//				targetInfo.daysInfo = daysInfo;
//				int size = days.size();
//				for(int i=0; i<size; i++)
//				{
//					daysInfo.add(getTCDayWeatherInfo(days.get(i)));
//				}
//			}
//		}
//		return targetInfo;
//	}
//	
//	private static TCDayWeatherInfo getTCDayWeatherInfo(DayWeather dw)
//	{
//		TCDayWeatherInfo info = new TCDayWeatherInfo();
//		info.Allergy = dw.Allergy;
//		info.ClotheAbstract = dw.ClotheAbstract;
//		info.ClotheDetail = dw.ClotheDetail;
//		info.Flow = dw.Flow;
//		info.MaxDegree = dw.MaxDegree;
//		info.MinDegree = dw.MinDegree;
//		info.Ultravoilet = dw.Ultravoilet;
//		info.WashCar = dw.WashCar;
//		info.Weather = dw.Weather;
//		info.WEnum = getWeatherEnum(dw.WEnum);
//		info.Wind = dw.Wind;
//		
//		return info;
//	}
//	
//	private static TCWeatherEnum getWeatherEnum(WeatherEnum em)
//	{
//		return TCWeatherEnum.valueOf(em.toString());
//	}
}



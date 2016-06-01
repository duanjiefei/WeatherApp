package com.tianci.weather.data;

import java.io.Serializable;
import java.util.List;

/**
@Date : 2016年4月12日
@Author : Zhan Yu
@Description : TODO
*/
public class TCWeatherInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 基本信息，包含日期、天气发布时间等 */
	public TCWeatherBaseInfo baseInfo;
	/** 从今天起连续几天的详细天气信息*/
	public List<TCDayWeatherInfo> daysInfo; 
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("BaseInfo=");
		sb.append(baseInfo == null ? "NULL" : baseInfo.toString());
		sb.append("\n");
		sb.append("daysInfo.size=");
		sb.append(daysInfo.size());
		sb.append("; daysInfo=");
		if(daysInfo != null)
		{
			for(TCDayWeatherInfo dayInfo : daysInfo)
			{
				sb.append("{");
				sb.append(dayInfo.toString());
				sb.append("};\n");
			}
		} else
		{
			sb.append("NULL");
		}
		return sb.toString();
	}
}



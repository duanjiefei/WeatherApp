package com.tianci.weather.data;

import java.io.Serializable;

/**
@Date : 2016年4月12日
@Author : Zhan Yu
@Description : TODO
*/
public class TCWeatherBaseInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 城市名称 */
    public String city;
    /** 日期 yyyy-MM-dd*/
    public String date;
    /** 星期几 */
    public String weekDay;
    /** 天气预报发布时间 */
    public String publishTime;
    
    @Override
    public String toString()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("city=");
    	sb.append(city);
    	sb.append("; date=");
    	sb.append(date);
    	sb.append("; weekDay=");
    	sb.append(weekDay);
    	sb.append("; publishTime=");
    	sb.append(publishTime);
    	return sb.toString();
    }
}



package com.tianci.weather.data;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
@Date : 2016年4月12日
@Author : Zhan Yu
@Description : 一天的天气信息数据
*/
public class TCDayWeatherInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	public String city;
	public String date;
	
	/** 星期几，eg：星期一 */
	public String weekDay;
	
	/** 天气状况，eg：阵雨 */
    public String Weather;
    
    /** 天气状况枚举*/
    public TCWeatherEnum WEnum;
    
    public String MaxDegree;
    public String MinDegree;
    
    /** 风向，eg：无持续风向*/
    public String Wind;
    
    /** 风速，eg <=3 */
    public String Flow;
    
    /** 穿衣指数，eg：1*/
    public String ClotheIndex;
    
    /** 穿衣简单提示，eg：薄短袖类*/
    public String ClotheAbstract;
    
    /** 穿衣详细提示，eg：短袖衫、短裙、短裤、薄型T恤衫、敞领短袖棉衫*/
    public String ClotheDetail;
    
    /** 紫外线指数，eg：1*/
    public String Ultravoilet;
    
    /** 紫外线简单提示，eg：最弱*/
    public String Ultravoilet_l;
    
    /** 紫外线详细提示，eg：紫外线最弱*/
    public String Ultravoilet_s;
    
    /** 洗车指数，eg：5*/
    public String WashCar;
    
    /** 洗车简单提示，eg：不适宜 */
    public String WashCar_l;
    
    /** 洗车详细提示，eg：洗车后当日有降水、大风或沙尘天气，不适宜洗车*/
    public String WashCar_s;
    
    public String Allergy;
    
    /** 户外运动指数，eg：1*/
    public String yd;
    
    /** 户外运动简单提示，eg：不适宜*/
    public String yd_l;
    
    /** 户外运动详细提示，eg：天气炎热，不适宜户外运动；*/
    public String yd_s;
    
    /** 湿度指数：1 */
    public String gm;
    
    /** 湿度简单提示：低发期*/
    public String gm_l;
    
    /** 湿度详细提示：环境温度较高，要提防长时间在空调环境中引发的空调病；*/
    public String gm_s;
    
    public String sd;
    
    public String getStringValue(TCDayWeatherInfo obj)
    {
    	StringBuffer sb = new StringBuffer();
    	Field[] fields = TCDayWeatherInfo.class.getDeclaredFields();
    	for(Field field : fields)
    	{
    		sb.append(field.getName());
    		sb.append("=");
    		try
			{
    			if(field.get(obj) instanceof String)
    				sb.append((String)field.get(obj));
    			else if(field.get(obj) instanceof Enum)
    				sb.append(((Enum)field.get(obj)).toString());
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
    		sb.append("; ");
    	}
    	
    	return sb.toString();
    }
    
    @Override
    public String toString()
    {
    	return getStringValue(this);
    }
    
    public static void main(String[] args)
	{
		TCDayWeatherInfo info = new TCDayWeatherInfo();
		info.city = "广州";
		System.err.println(info.toString());
		
		TCDayWeatherInfo info2 = new TCDayWeatherInfo();
		info2.city = "深圳";
		info2.ClotheDetail="短袖";
		System.err.println(info2.toString());
	}
}



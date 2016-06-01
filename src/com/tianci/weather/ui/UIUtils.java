package com.tianci.weather.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherApplication;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherEnum;

/**
 * @Date : 2016年4月20日
 * @Author : Zhan Yu
 * @Description : TODO
 */
public class UIUtils {
	public final static int POS_IMG_LEFT = 0;
	public final static int POS_IMG_TOP = 1;
	public final static int POS_IMG_RIGHT = 2;
	public final static int POS_IMG_BOTTOM = 3;

	/**
	 * 在文本中添加图片
	 * 
	 * @param tv
	 *            TextView
	 * @param imgRes
	 *            图片资源
	 * @param 图片大小
	 * @param pos
	 *            图片位置： <li>0：左</li> <li>1：上</li> <li>2：右</li> <li>3：下</li>
	 */
	@SuppressWarnings("unused")
	public static void addImgToTextView(TextView tv, Drawable d, int imgSize,
			int pos) {

		if (d == null)
			return;
		d.setBounds(0, 0, imgSize, imgSize);
		switch (pos) {
		case POS_IMG_LEFT:
			tv.setCompoundDrawables(d, null, null, null);
			break;
		case POS_IMG_TOP:
			tv.setCompoundDrawables(null, d, null, null);
			break;
		case POS_IMG_RIGHT:
			tv.setCompoundDrawables(null, null, d, null);
			break;
		case POS_IMG_BOTTOM:
			tv.setCompoundDrawables(null, null, null, d);
			break;
		default:
			tv.setCompoundDrawables(null, null, d, null);
			break;
		}

		tv.setCompoundDrawablePadding(WeatherConfig.getResolutionValue(30));
	}

	/**
	 * 根据天气状况获取对应图标
	 * 
	 * @param wenum
	 * @return
	 */
	public static int[] getWeatherIconRes(TCWeatherEnum wenum) {

		int[] resId = new int[3];
		int id = WeatherApplication.context.getResources().getIdentifier(
				"weather_icon_" + wenum.toString().toLowerCase(), "drawable",
				WeatherApplication.context.getPackageName());
		int focusId = WeatherApplication.context.getResources().getIdentifier(
				"weather_icon_" + wenum.toString().toLowerCase() + "_focus",
				"drawable", WeatherApplication.context.getPackageName());
		Debugger.i("getWeatherIconRes" + wenum.toString().toLowerCase());
		int unfocusId = WeatherApplication.context.getResources()
				.getIdentifier(
						"weather_icon_" + wenum.toString().toLowerCase()
								+ "_nofocus", "drawable",
						WeatherApplication.context.getPackageName());
		if (id == 0) {
			id = R.drawable.weather_icon_default;

		}
		if (focusId == 0) {

			focusId = R.drawable.weather_icon_sunny_focus;

		}
		if (unfocusId == 0) {
			unfocusId = R.drawable.weather_icon_sunny_nofocus;
		}

		resId[0] = id;
		resId[1] = focusId;
		resId[2] = unfocusId;
		return resId;
	}

	/**
	 * 根据天气状况获取对应背景图片
	 * 
	 * @param wenum
	 * @return
	 */
	public static BitmapDrawable getWeatherBackgroundDrawable(
			TCWeatherEnum wenum) {
		Debugger.d("getWeatherBackgroundDrawable wenum : " + wenum);
		// BitmapDrawable.createFromPath(WeatherApplication.context.getDir(
		// "cached_image", Context.MODE_PRIVATE).getAbsolutePath()+
		// "/weather_bg_"+wenum.toString().toLowerCase() + ".jpg");
		// toLowerCase():将stringObject 的所有大写字符全部被转换为了小写字符
		int resId = wenum == null ? 0
				: WeatherApplication.context.getResources()
						.getIdentifier(
								"weather_bg_" + wenum.toString().toLowerCase(),
								"drawable",
								WeatherApplication.context.getPackageName());
		if (resId == 0)
			resId = R.drawable.weather_bg_default;
		BitmapDrawable bmp = (BitmapDrawable) WeatherApplication.context
				.getResources().getDrawable(resId);// R.drawable.weather_bg_default;
		return bmp;
	}

	/**
	 * 将“yyyy-MM-dd”格式的日期转化为“MM/dd”格式
	 * 
	 * @Title:getFormatDate
	 * @param string
	 * @return String
	 */
	public static String getFormatDate(String string) {
		String str = "";

		if (string != null && string.length() != 0) {
			str = string.substring(5);

			str = str.replace('-', '/');
			System.out.println(str);
		}
		return str;

	}
}

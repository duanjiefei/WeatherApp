package com.tianci.weather.ui.weather.info;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherInfo;
import com.tianci.weather.ui.UIUtils;

/**
 * @Date : 2016年4月20日
 * @Author : Zhan Yu
 * @Description : TODO
 */
public class WeatherCurrentInfoView extends FrameLayout {
	TextView cityTv;
	ImageView cityIv;
	TextView degreeTv;
	TextView weatherTv;
	TextView windTv;
	ImageView weatherIv;
	private Context context;
	
	public WeatherCurrentInfoView(Context context) {
		super(context);
		this.context = context;

		LinearLayout cityLayout = new LinearLayout(context);
		cityLayout.setOrientation(LinearLayout.HORIZONTAL);
		cityLayout.setGravity(Gravity.CENTER_VERTICAL);
		LayoutParams cityLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addView(cityLayout, cityLayoutParams);

		cityTv = createTextView(context, 36);
		android.widget.LinearLayout.LayoutParams cityTvParams = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		cityLayout.addView(cityTv, cityTvParams);

		cityIv = new ImageView(context);
		android.widget.LinearLayout.LayoutParams cityIvParams = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		cityIvParams.leftMargin = WeatherConfig.getResolutionValue(30);
		cityIv.setImageResource(R.drawable.menu);
		cityLayout.addView(cityIv, cityIvParams);

		degreeTv = createTextView(context, 100);
		LayoutParams degreeTVParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		degreeTVParams.topMargin = WeatherConfig.getResolutionValue(62)+ WeatherConfig.getDpiValue(36);
		addView(degreeTv, degreeTVParams);

		LinearLayout weatherLayout = new LinearLayout(context);
		weatherLayout.setOrientation(LinearLayout.HORIZONTAL);
		weatherLayout.setGravity(Gravity.CENTER_VERTICAL);
		LayoutParams weatherLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		weatherLayoutParams.topMargin = WeatherConfig.getResolutionValue(62+50)+ WeatherConfig.getDpiValue(36+100);
//		weatherLayoutParams.topMargin = WeatherConfig
//				.getResolutionValue(62 + 36 + 100 + 50);
		addView(weatherLayout, weatherLayoutParams);

		weatherTv = createTextView(context, 40);
		android.widget.LinearLayout.LayoutParams weatherTvParams = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		weatherTv.setLayoutParams(weatherTvParams);
		weatherLayout.addView(weatherTv, weatherTvParams);

		weatherIv = new ImageView(context);
		android.widget.LinearLayout.LayoutParams weatherIvParams = new android.widget.LinearLayout.LayoutParams(
				 WeatherConfig.getResolutionValue(80),
				 WeatherConfig.getResolutionValue(80));
		weatherIvParams.leftMargin = WeatherConfig.getResolutionValue(30);
		weatherLayout.addView(weatherIv, weatherIvParams);

		windTv = createTextView(context, 40);
		LayoutParams windTvParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		windTvParams.topMargin = WeatherConfig.getResolutionValue(62+50+24)+ WeatherConfig.getDpiValue(36+100+40);
//		windTvParams.topMargin = WeatherConfig.getResolutionValue(62 + 36 + 100
//				+ 50 + 40 + 24);
		addView(windTv, windTvParams);
	}

	private TextView createTextView(Context context, int size) {
		TextView tv = new TextView(context);
		tv.setTextColor(Color.rgb(255, 255, 255));
		tv.setTextSize(WeatherConfig.getDpiValue(size));
		return tv;
	}

	public void update(TCWeatherInfo weatherInfo) {
		cityTv.setText(weatherInfo.baseInfo.city);
		degreeTv.setText(weatherInfo.daysInfo.get(0).MinDegree + "°~"
				+ weatherInfo.daysInfo.get(0).MaxDegree + "°");
		weatherTv.setText(weatherInfo.daysInfo.get(0).Weather);
		weatherIv.setImageResource(UIUtils
				.getWeatherIconRes(weatherInfo.daysInfo.get(0).WEnum)[0]);
		windTv.setText(weatherInfo.daysInfo.get(0).Wind
				+ weatherInfo.daysInfo.get(0).Flow + "级");

	}
	
	public void addCityName(String cityName){
		cityTv.setText(cityName);
	}

}
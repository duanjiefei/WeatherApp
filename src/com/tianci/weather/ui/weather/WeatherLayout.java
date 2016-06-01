package com.tianci.weather.ui.weather;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherInfo;
import com.tianci.weather.ui.BounceImageView;
import com.tianci.weather.ui.SkyLoadingView;
import com.tianci.weather.ui.WeatherLoadingView;

/**
 * @Date : 2016年4月20日
 * @Author : Zhan Yu
 * @Description : TODO
 */
public class WeatherLayout extends FrameLayout {
	WeatherBackgroundView backgroundView;
	WeatherForegroundView foregroundView;
	WeatherLoadingView weatherLoadingView;

	BounceImageView bounceImageView;

	public WeatherLayout(Context context) {
		super(context);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		backgroundView = new WeatherBackgroundView(context);
		addView(backgroundView);
		Debugger.i("addView(backgroundView)");
		
		foregroundView = new WeatherForegroundView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				WeatherConfig.getResolutionValue(1080 - 632));
		params.topMargin = WeatherConfig.getResolutionValue(632);
		addView(foregroundView, params);
		Debugger.i("addView(foregroundView)");
		



		weatherLoadingView = new WeatherLoadingView(context);
		LayoutParams weatherLoadingLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,Gravity.CENTER);
		addView(weatherLoadingView,weatherLoadingLayoutParams);


	}
	
	public WeatherLoadingView getLoadingViewInstance(){
		return weatherLoadingView;
	}

	public void initializeForeView(String defaultCity) {
		foregroundView.initializeDefaultCity(defaultCity);

	}

	public void initializeCityList(List<String> cityList, String defaultCity) {
		foregroundView.initializeCityList(cityList, defaultCity);
	}

	/**
	 * 更新天气
	 * 
	 * @param weatherInfo
	 *            天气信息数据
	 */
	public void updateWeather(TCWeatherInfo weatherInfo) {
		foregroundView.updateWeather(weatherInfo);
		// backgroundView.changeBackground();
	}

	/**
	 * 添加城市天气...
	 */
	public void addCityWeather() {

	}

	public void changeBackground(BitmapDrawable b) {
		backgroundView.changeBackground(b);		

	}

}
package com.tianci.weather.ui.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherActivity;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherInfo;
import com.tianci.weather.ui.weather.info.WeatherView;

/**
 * @Date : 2016年4月20日
 * @Author : Zhan Yu
 * @Description : TODO
 */
public class WeatherForegroundView extends LinearLayout {
	private Context context;
	private boolean isArrowVisible = false;
	ImageView leftArrow;
	ViewFlipper weatherViewFlipper;
	ImageView rightArrow;

	WeatherView defaultWeatherView;
	Map<String, WeatherView> weatherViews = new HashMap<String, WeatherView>();

	public WeatherForegroundView(Context context) {
		super(context);
		this.context = context;
		setOrientation(LinearLayout.HORIZONTAL);
		// setGravity(Gravity.CENTER_VERTICAL);
		setBackgroundColor(Color.parseColor("#66BFBFBF"));

		leftArrow = new ImageView(context);
		leftArrow.setId(1);
		LayoutParams leftArrowParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftArrowParams.leftMargin = WeatherConfig.getResolutionValue(100);
		leftArrowParams.topMargin = WeatherConfig
				.getResolutionValue(1080 - 632 - 208 - 60);
		leftArrow.setLayoutParams(leftArrowParams);
		leftArrow.setImageResource(R.drawable.left_arrow);
		leftArrow.setVisibility(View.INVISIBLE);
		addView(leftArrow);

		weatherViewFlipper = new ViewFlipper(context);
		weatherViewFlipper.setId(2);
		LayoutParams viewFlipperParams = new LayoutParams(
				WeatherConfig.getResolutionValue(1920 - 200 - 42),
				LayoutParams.MATCH_PARENT);
		weatherViewFlipper.setLayoutParams(viewFlipperParams);
		weatherViewFlipper.setFocusable(true);
		addView(weatherViewFlipper);

		rightArrow = new ImageView(context);
		rightArrow.setId(3);
		LayoutParams rightArrowParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightArrowParams.rightMargin = WeatherConfig.getResolutionValue(100);
		rightArrowParams.topMargin = WeatherConfig
				.getResolutionValue(1080 - 632 - 208 - 60);
		rightArrow.setLayoutParams(rightArrowParams);
		rightArrow.setImageResource(R.drawable.right_arrow);
		rightArrow.setVisibility(View.INVISIBLE);
		addView(rightArrow);

		defaultWeatherView = new WeatherView(context);
		weatherViewFlipper.addView(defaultWeatherView);
	}

	public void initializeDefaultCity(String defaultCity) {
		weatherViewFlipper.removeAllViews();
		weatherViews.clear();
		defaultWeatherView.addCityName(defaultCity);//先添加城市名字
		weatherViewFlipper.addView(defaultWeatherView);
		weatherViews.put(defaultCity, defaultWeatherView);				
	}

	public void initializeCityList(List<String> cityList, String defaultCity) {
		for (String city : cityList) {
			if (!weatherViews.containsKey(city)) {
				if (!city.equals(defaultCity)) {
					WeatherView view = new WeatherView(context);
					view.addCityName(city);//先添加城市名字
					weatherViews.put(city, view);
					weatherViewFlipper.addView(view);					
				}
			}
		}
	/*	// 当城市数量多于1个的时候显示左右箭头图标
		if (cityList.size() == 1) {
			leftArrow.setVisibility(View.INVISIBLE);
			rightArrow.setVisibility(View.INVISIBLE);
		} else if (cityList.size() > 1) {
			leftArrow.setVisibility(View.VISIBLE);
			rightArrow.setVisibility(View.VISIBLE);
		}*/

	}

	public void updateWeather(TCWeatherInfo weatherInfo) {
		// if(!weatherViews.containsKey(weatherInfo.baseInfo.city))
		// { //该数据还没有View对应
		// WeatherView view = new WeatherView(context);
		// //view.update(weatherInfo);
		// weatherViews.put(weatherInfo.baseInfo.city, view);
		// weatherViewFlipper.addView(view);
		// }
		weatherViews.get(weatherInfo.baseInfo.city).update(weatherInfo);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

}

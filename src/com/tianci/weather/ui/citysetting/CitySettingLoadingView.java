package com.tianci.weather.ui.citysetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.ui.SkyLoadingView;

public class CitySettingLoadingView extends LinearLayout {
	private SkyLoadingView mSkyLoadingView;
	private Context context;
	private TextView textView_cityName;// 城市名称

	private final String TEXT_COLOR = "#000000";
	private final int CITY_NAME_TEXT_SIZE = WeatherConfig.getDpiValue(40);
	private final int CITY_NAME_TOP_PADDING = WeatherConfig
			.getResolutionValue(62);
	private final int LOAD_TOP_MARGIN = WeatherConfig.getResolutionValue(80);

	private final int CITY_VIEW_WIDTH = WeatherConfig.getResolutionValue(278);
	private final int CITY_VIEW_HIGH = WeatherConfig.getResolutionValue(378);

	private String cityName;

	public CitySettingLoadingView(Context context, String cityName) {
		super(context);
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundResource(R.drawable.city_setting_item_bg_focus);
		// setLayoutParams(new LayoutParams(CITY_VIEW_WIDTH, CITY_VIEW_HIGH));

		initLayout(cityName);
		this.cityName = cityName;

	}

	private void initLayout(String cityName) {

		textView_cityName = createTextView(context, CITY_NAME_TEXT_SIZE);
		textView_cityName.setText(cityName);
		LayoutParams textView_LayoutParam = new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		textView_LayoutParam.topMargin = CITY_NAME_TOP_PADDING;
		textView_LayoutParam.gravity = Gravity.CENTER_HORIZONTAL;
		addView(textView_cityName, textView_LayoutParam);

		mSkyLoadingView = new SkyLoadingView(context);
		LayoutParams mSkyLoadingView_LayoutParam = new LayoutParams(
				WeatherConfig.getResolutionValue(60),
				WeatherConfig.getResolutionValue(60));
		mSkyLoadingView_LayoutParam.gravity = Gravity.CENTER_HORIZONTAL;
		mSkyLoadingView_LayoutParam.topMargin = LOAD_TOP_MARGIN;
		addView(mSkyLoadingView, mSkyLoadingView_LayoutParam);

	}

	public TextView createTextView(Context context, int textSize) {

		TextView textView = new TextView(context);
		textView.setTextSize(textSize);
		textView.setTextColor(Color.parseColor(TEXT_COLOR));
		textView.setGravity(Gravity.CENTER_HORIZONTAL);

		return textView;

	}

}

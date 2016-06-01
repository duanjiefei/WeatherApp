package com.tianci.weather.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianci.weather.WeatherConfig;

public class WeatherLoadingView extends LinearLayout{
	SkyLoadingView skyLoadingView;
	LinearLayout loadingBackground,loadingLayout,loadingFailureLayout;
	TextView loadingText;
	TextView loadingFailureText;
	Button loadingRetry;

	public WeatherLoadingView(Context context) {
		super(context);
		setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(LinearLayout.VERTICAL);
		
		loadingLayout = new LinearLayout(context);
		LayoutParams llLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		loadingLayout.setGravity(Gravity.CENTER);
		loadingLayout.setOrientation(LinearLayout.VERTICAL);
		loadingLayout.setBackgroundColor(Color.TRANSPARENT);
		addView(loadingLayout,llLayoutParams);
		
		loadingBackground = new LinearLayout(context);
		LayoutParams lbLayoutParams = new LayoutParams(
				WeatherConfig.getResolutionValue(80), WeatherConfig.getResolutionValue(80));
		loadingBackground.setGravity(Gravity.CENTER);
		GradientDrawable lbDrawable = new GradientDrawable();
		lbDrawable.setCornerRadius(WeatherConfig.getResolutionValue(40));
		lbDrawable.setColor(0xaaffffff);
		loadingBackground.setBackground(lbDrawable);
		loadingLayout.addView(loadingBackground,lbLayoutParams);
		
		skyLoadingView = new SkyLoadingView(context);
		LayoutParams skyLayoutParams = new LayoutParams(
				WeatherConfig.getResolutionValue(60), WeatherConfig.getResolutionValue(60));
		skyLoadingView.setLayoutParams(skyLayoutParams);
		loadingBackground.addView(skyLoadingView);		
		
		loadingText = new TextView(context);
		LayoutParams ltLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ltLayoutParams.topMargin = WeatherConfig.getResolutionValue(20);
		loadingText.setTextColor(Color.WHITE);
		loadingText.setTextSize(WeatherConfig.getResolutionValue(24));
		loadingText.setText("正在加载中");
		loadingLayout.addView(loadingText,ltLayoutParams);
		
		loadingFailureLayout = new LinearLayout(context);
		LayoutParams lflLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		loadingFailureLayout.setGravity(Gravity.CENTER);
		loadingFailureLayout.setOrientation(LinearLayout.VERTICAL);
		loadingFailureLayout.setBackgroundColor(Color.TRANSPARENT);
		loadingFailureLayout.setVisibility(View.INVISIBLE);
		addView(loadingFailureLayout,lflLayoutParams);
		
		loadingFailureText = new TextView(context);
		LayoutParams lftLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lftLayoutParams.topMargin = WeatherConfig.getResolutionValue(20);
		loadingFailureText.setTextColor(Color.WHITE);
		loadingFailureText.setTextSize(WeatherConfig.getResolutionValue(22));
		loadingFailureText.setText("加载失败");
		loadingFailureLayout.addView(loadingFailureText,lftLayoutParams);
		
		loadingRetry = new Button(context);
		LayoutParams lrLayoutParams = new LayoutParams(
				WeatherConfig.getResolutionValue(120), WeatherConfig.getResolutionValue(50));
		lrLayoutParams.topMargin = WeatherConfig.getResolutionValue(20);
		loadingRetry.setTextColor(Color.BLACK);
		loadingRetry.setTextSize(WeatherConfig.getResolutionValue(24));
		loadingRetry.setBackgroundColor(0xaaffffff);
		loadingRetry.setText("重 试");
		loadingRetry.setFocusable(true);
		loadingRetry.requestFocus();
		loadingFailureLayout.addView(loadingRetry,lrLayoutParams);
				
	}
	
	public void loadingFailure(){
		loadingFailureLayout.setVisibility(View.VISIBLE);
		loadingLayout.setVisibility(View.INVISIBLE);
	}
	
	public void reloading(){
		loadingFailureLayout.setVisibility(View.INVISIBLE);
		loadingLayout.setVisibility(View.VISIBLE);
	}
	
	public Button getLoadingRetryInstance(){
		loadingRetry.requestFocus();
		return loadingRetry;
	}

}

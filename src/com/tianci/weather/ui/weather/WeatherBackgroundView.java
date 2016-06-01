package com.tianci.weather.ui.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;

import com.tianci.weather.R;
import com.tianci.weather.ui.WeatherAnim;

/**
@Author : Zhan Yu
@Description :天气背景
*/
public class WeatherBackgroundView extends FrameLayout
{
	public WeatherBackgroundView(Context context)
	{
		super(context);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundResource(R.drawable.weather_bg_default);
	}
	
	private Bitmap preBitmap;
	private Bitmap curBitmap;
	
	public void changeBackground(BitmapDrawable d)
	{//TODO 
		WeatherAnim.switchBackground(this, d);
	}

}



package com.tianci.weather.ui.citysetting;

import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CitysettingAddOneCityView extends LinearLayout {

	private ImageView imageView;
	
	private final int IMAGE_NOFOCUS = R.drawable.city_add_nofocus;
	private final int BACKGROUND_IMAGE = R.drawable.add_city_background;
	private final int LEFT_MARGIN = WeatherConfig.getResolutionValue(116);
	private final int TOP_MARGIN = WeatherConfig.getResolutionValue(164);
	
	public CitysettingAddOneCityView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setOrientation(LinearLayout.VERTICAL);// 线性垂直布局
		setBackgroundResource(BACKGROUND_IMAGE);
		
		imageView = new ImageView(context);
		imageView.setImageResource(IMAGE_NOFOCUS);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(LEFT_MARGIN, TOP_MARGIN, 0, 0);
		addView(imageView, layoutParams);
	}
	
	public void updateImageView(int imageId){
		imageView.setImageResource(imageId);
	}

}

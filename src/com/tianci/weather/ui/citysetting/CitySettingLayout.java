package com.tianci.weather.ui.citysetting;

import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherCitysettingShowInfo;
import com.tianci.weather.ui.MarqueeTextView;
import com.tianci.weather.ui.WeatherFocusFrame;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
@Date : 2016年4月21日
@Author : Zhan Yu
@Description : TODO
*/
public class CitySettingLayout extends FrameLayout
{

	private TextView textView_title;
	private ImageView textView_line;
	public static WeatherFocusFrame focusView;
	private HorizontalScrollView scrollView;
	private CitysettingShowAllCitiesView cityAllView;
	private Context mContext;
	
	private final int TITLE_TEXT_SIZE = WeatherConfig.getDpiValue(60);
	private final String TITLE_TEXT_COLOR = "#999999";
	private final int TITLE_TOP_MARGIN = WeatherConfig.getResolutionValue(44);
	private final int TITLE_LEFT_MARGIN = WeatherConfig.getResolutionValue(89);
	private final int LINE_WIDE = WeatherConfig.getResolutionValue(686);
	private final int LINE_HIGH = WeatherConfig.getResolutionValue(3);
	private final int LINE_TOP_MARGIN = WeatherConfig.getResolutionValue(16);
	private final int ALLCITY_TOP_MARGIN = WeatherConfig.getResolutionValue(163);

	
	public static CitySettingLayout citySettingLayout;
	
	public CitySettingLayout(Context context)
	{
		super(context);
		mContext = context;
		citySettingLayout = this;
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		focusView = new WeatherFocusFrame(context, WeatherConfig.getResolutionValue(83));
//		LayoutParams layoutParams_focusView = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		//layoutParams_scrollLayoutParams.gravity = Gravity.CENTER;
//		layoutParams_focusView.leftMargin = WeatherConfig.getResolutionValue(186);
		focusView.setImgResourse(R.drawable.ui_sdk_btn_focus_shadow_no_bg);
		focusView.setFocusable(false);
		focusView.setFocusableInTouchMode(false);
		focusView.needAnimtion(true);
		focusView.setAnimDuration(150);
		focusView.setInterpolator(new AccelerateDecelerateInterpolator());
		addView(focusView);
		
		initTitle();
		
		scrollView = new HorizontalScrollView(context);
		LayoutParams layoutParams_scrollLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//layoutParams_scrollLayoutParams.gravity = Gravity.CENTER;
//layoutParams_scrollLayoutParams.leftMargin = WeatherConfig.getResolutionValue(186);
		scrollView.setLayoutParams(layoutParams_scrollLayoutParams);
		addView(scrollView);
		
		cityAllView = new CitysettingShowAllCitiesView(context);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.topMargin = ALLCITY_TOP_MARGIN + LINE_TOP_MARGIN + LINE_HIGH + TITLE_TOP_MARGIN + TITLE_TEXT_SIZE;
		scrollView.addView(cityAllView, layoutParams);
	}
	
	private void initTitle()
	{
		// title和content布局
		LinearLayout titleAndContentLayout = new LinearLayout(mContext);
        titleAndContentLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, WeatherConfig.getResolutionValue(123)));
        titleAndContentLayout.setOrientation(LinearLayout.VERTICAL);
        titleAndContentLayout.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        addView(titleAndContentLayout);

        LinearLayout titleAndIconLayout = new LinearLayout(mContext);
        titleAndIconLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, WeatherConfig.getResolutionValue(118)));
        titleAndIconLayout.setOrientation(LinearLayout.HORIZONTAL);
        
        MarqueeTextView titleTextView = new MarqueeTextView(mContext);
        titleTextView.setTextSize(WeatherConfig.getDpiValue(66));
        titleTextView.setTextColor(Color.parseColor("#999999"));
        titleTextView.setText(R.string.citysetting_title);
        LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleTextParams.leftMargin = WeatherConfig.getResolutionValue(78);
        titleTextParams.topMargin = WeatherConfig.getResolutionValue(25);
        titleAndIconLayout.addView(titleTextView, titleTextParams);
        
        titleAndContentLayout.addView(titleAndIconLayout);
        
        ImageView titleTopLineImgView = new ImageView(mContext);
        LayoutParams topLineImgParams = new LayoutParams(WeatherConfig.getResolutionValue(686), LayoutParams.WRAP_CONTENT);
        topLineImgParams.topMargin = WeatherConfig.getResolutionValue(3);
        titleTopLineImgView.setLayoutParams(topLineImgParams);
        titleTopLineImgView.setBackgroundResource(R.drawable.ui_sdk_menu_title_line);
        titleAndContentLayout.addView(titleTopLineImgView);
	}
	
	public void setAddCityName(String city){
		cityAllView.setAddCityName(city);
	}
	
	//滚动到最左边
	public void setScrollLeft()
	{
		if(scrollView != null)
		{
			scrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
		}
	}

}



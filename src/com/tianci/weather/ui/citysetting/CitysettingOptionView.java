package com.tianci.weather.ui.citysetting;

import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;

import android.R.drawable;
import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CitysettingOptionView extends LinearLayout {

	private TextView textView_option;
	private ImageView imageView_option;
	private Context context;
	private int optionImage;
	private String optionString;

	private final int WORD_PADDING = WeatherConfig.getResolutionValue(20);// 选项文字在显示框中的间隔
	private final int OPTION_VIEW_WIDTH = WeatherConfig.getResolutionValue(278);
	private final int OPTION_VIEW_HIGH = WeatherConfig.getResolutionValue(70);
	private final int OPTION_IMAGE_SIZE = WeatherConfig.getResolutionValue(32);
	private final int OPTION_IMAGE_LEFT_MARGIN = WeatherConfig.getResolutionValue(47);
	private final int OPTION_IMAGE_TOP_MARGIN = WeatherConfig.getResolutionValue(20);
	private final int OPTION_IMAGE_BUTTOM_MARGIN = WeatherConfig.getResolutionValue(18);
	private final int OPTION_STRING_LEFT_MARGIN = WeatherConfig.getResolutionValue(26);
	private final int OPTION_STRING_TOP_MARGIN = WeatherConfig.getResolutionValue(17);
	private final int TEXT_SIZE = WeatherConfig.getDpiValue(30);
	private final String BACKGROUND_COLOR = "#FFFFFF";
	private final String TEXT_COLOR = "#5B5B5B";

	public CitysettingOptionView(Context context, int optionImage, String optionString) {	
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.optionImage = optionImage;
		this.optionString = optionString;
		initLayout();
		initView();
	}

	public void initView() {
		imageView_option = new ImageView(context);
		imageView_option.setImageResource(optionImage);
		LayoutParams imageLayoutParams = new LayoutParams(OPTION_IMAGE_SIZE, OPTION_IMAGE_SIZE);
		imageLayoutParams.setMargins(OPTION_IMAGE_LEFT_MARGIN, OPTION_IMAGE_TOP_MARGIN, 0, 0);
		addView(imageView_option, imageLayoutParams);
		
		textView_option = new TextView(context);
		textView_option.setText(optionString);
		textView_option.setTextSize(TEXT_SIZE);
		textView_option.setTextColor(Color.parseColor(TEXT_COLOR));
		LayoutParams textView_optionLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textView_optionLayoutParams.setMargins(OPTION_STRING_LEFT_MARGIN, OPTION_STRING_TOP_MARGIN, 0, 0);
		addView(textView_option, textView_optionLayoutParams);
	}

	public void updateImage(int imageId){
		imageView_option.setImageResource(imageId);
	}
	
	public void initLayout() {
		setOrientation(LinearLayout.HORIZONTAL);
		setLayoutParams(new LayoutParams(OPTION_VIEW_WIDTH,
				OPTION_VIEW_HIGH));
		setBackgroundResource(R.drawable.city_setting_item_bg_unfocus);
	}
}

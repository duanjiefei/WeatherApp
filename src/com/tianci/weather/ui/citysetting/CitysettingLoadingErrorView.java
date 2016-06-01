package com.tianci.weather.ui.citysetting;

import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class CitysettingLoadingErrorView extends LinearLayout {
	private Context context;
	private final String TEXT_COLOR = "#000000";
	private final int CITY_NAME_TEXT_SIZE = WeatherConfig.getDpiValue(40);
	private final int TEMP_NAME_TEXT_SIZE = WeatherConfig.getDpiValue(36);

	private TextView text_error;
	private TextView text_repeat;

	public CitysettingLoadingErrorView(Context context) {
		super(context);
		this.context = context;
		setBackgroundResource(R.drawable.city_setting_item_bg_focus);
		setGravity(Gravity.CENTER);
		initLayout();
		setFocusable(true);
	}

	private void initLayout() {

		LinearLayout contentLayout = new LinearLayout(context);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		contentLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		LayoutParams contentLayoutpParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		text_error = createTextView(context, CITY_NAME_TEXT_SIZE);
		text_error.setText("加载失败");
		contentLayout.addView(text_error);

		text_repeat = createTextView(context, TEMP_NAME_TEXT_SIZE);
		text_repeat.setText("点击重试");
		contentLayout.addView(text_repeat);

		addView(contentLayout, contentLayoutpParams);

	}

	public TextView createTextView(Context context, int textSize) {
		TextView textView = new TextView(context);
		textView.setTextSize(textSize);
		textView.setTextColor(Color.parseColor(TEXT_COLOR));
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		return textView;
	}
	
	@Override
	public void setOnFocusChangeListener(OnFocusChangeListener l) {
		// TODO Auto-generated method stub
		super.setOnFocusChangeListener(l);
	}
	
	@Override
	public void setOnKeyListener(OnKeyListener l) {
		// TODO Auto-generated method stub
		super.setOnKeyListener(l);
	}


	
	
	

}

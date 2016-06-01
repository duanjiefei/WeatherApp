package com.tianci.weather.ui.citysetting;

import com.tianci.weather.Debugger;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherCitysettingShowInfo;
import com.tianci.weather.ui.SkyLoadingView;

import com.tianci.weather.R;
import android.R.anim;
import android.R.color;
import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author strongheart
 *
 */
/**
 * @author strongheart
 *
 */
/**
 * @author strongheart
 * 
 */
public class CitysettingOneCityView extends LinearLayout {

	private ImageView imageView_default;// 默认图标
	private TextView textView_cityName;// 城市名称
	private ImageView imageView_weather;// 天气标识图片
	private TextView textView_temperature;// 温度显示
	private Context context;
	public SkyLoadingView mSkyLoadingView;

	private final int CITY_VIEW_WIDTH = WeatherConfig.getResolutionValue(278);
	private final String BACKGROUND_COLOR = "#FFFFFF";
	private final int CITY_NAME_TOP_PADDING = WeatherConfig
			.getResolutionValue(12);
	private final int CITY_NAME_TEXT_SIZE = WeatherConfig.getDpiValue(40);
	private final int TEMP_NAME_TEXT_SIZE = WeatherConfig.getDpiValue(36);
	private final String TEXT_COLOR = "#000000";
	private final int WEATHER_IMAGE_SIZE = WeatherConfig.getResolutionValue(80);
	private final int WEATHER_IMAGE_TOP_MAGRIN = WeatherConfig
			.getResolutionValue(74);
	private final int TEMP_TOP_MARGIN = WeatherConfig.getResolutionValue(24);
	private final int IMAGE_DEFAULT_LEFT_MARGIN = WeatherConfig
			.getResolutionValue(113);

	private TCWeatherCitysettingShowInfo data;

	public CitysettingOneCityView(Context context,
			TCWeatherCitysettingShowInfo dataVal) {
		super(context);
		this.context = context;
		data = dataVal;
		initLayout();
		initView(dataVal);
	}

	public void initView(TCWeatherCitysettingShowInfo dataVal) {
		imageView_default = new ImageView(context);
		imageView_default.setImageResource(R.drawable.default_icon);
		LayoutParams imageView_default_LayoutParam = new LayoutParams(
				WeatherConfig.getResolutionValue(54),
				WeatherConfig.getResolutionValue(50));
		imageView_default_LayoutParam.leftMargin = IMAGE_DEFAULT_LEFT_MARGIN;
		addView(imageView_default, imageView_default_LayoutParam);
		if (dataVal != null) {

			textView_cityName = createTextView(context, CITY_NAME_TEXT_SIZE);
			textView_cityName.setText(dataVal.cityNameString);
			textView_cityName.setPadding(0, CITY_NAME_TOP_PADDING, 0, 0);
			addView(textView_cityName);

			imageView_weather = new ImageView(context);
			imageView_weather.setImageResource(dataVal.weatherIcons[1]);
			LayoutParams image_LayoutParams = new LayoutParams(
					WEATHER_IMAGE_SIZE, WEATHER_IMAGE_SIZE);
			image_LayoutParams.setMargins(0, WEATHER_IMAGE_TOP_MAGRIN, 0, 0);
			addView(imageView_weather, image_LayoutParams);

			textView_temperature = createTextView(context, TEMP_NAME_TEXT_SIZE);
			LayoutParams textView_temperature_LayoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			textView_temperature_LayoutParams.setMargins(0, TEMP_TOP_MARGIN, 0,
					0);
			textView_temperature.setText(dataVal.lowTemp + "°~"
					+ dataVal.highTemp + "°");
			addView(textView_temperature, textView_temperature_LayoutParams);
		}

		setCityDefault(false);
	}

	public void initLayout() {
		setOrientation(LinearLayout.VERTICAL);// 线性垂直布局
		setGravity(Gravity.CENTER_HORIZONTAL);
		setBackgroundResource(R.drawable.city_setting_item_bg_unfocus);
		setLayoutParams(new LayoutParams(CITY_VIEW_WIDTH,
				LayoutParams.WRAP_CONTENT));
	}

	/**
	 * 得到当前view的城市名称
	 * 
	 * @return
	 */
	public String getCityName() {
		return textView_cityName.getText().toString();
	}

	public void updateWeatherIcon(int imageId) {
		imageView_weather.setImageResource(imageId);
	}

	public void setCityDefault(boolean isDefaulted) {
		if (isDefaulted) {
			imageView_default.setVisibility(VISIBLE);
		} else {
			imageView_default.setVisibility(INVISIBLE);
		}
	}

	public TextView createTextView(Context context, int textSize) {
		TextView textView = new TextView(context);
		textView.setTextSize(textSize);
		textView.setTextColor(Color.parseColor(TEXT_COLOR));
		textView.setGravity(Gravity.CENTER_HORIZONTAL);

		return textView;
	}

	/**
	 * 更新当前显示的信息
	 * 
	 * @param imageView
	 *            天气图标
	 * @param lowTemp
	 *            最低温度
	 * @param highTemp
	 *            最高温度
	 */
	public void updateInfo(TCWeatherCitysettingShowInfo data) {
		Debugger.d("data=" + data);
		textView_cityName.setText(data.cityNameString);
		imageView_weather.setImageResource(data.weatherIcons[1]);
		textView_temperature.setText(data.lowTemp + "°~" + data.highTemp + "°");
	}

}

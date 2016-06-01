package com.tianci.weather.ui.citysetting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherCitysettingShowInfo;

public class CitysettingCityAllView extends LinearLayout {

	private CitysettingOneCityView citysettingOneCityView;
	private CitysettingOptionView citySetDefaultOptionView;
	private CitysettingOptionView cityDeletetOptionView;
	private CitySettingManager citySettingManager;
	private boolean isDefault;

	private final int DIVIDE_PADDING = WeatherConfig.getResolutionValue(25);
	private final int TWO_OPTIONS_DIVIDE_PADDING = WeatherConfig
			.getResolutionValue(15);
	private final int CITY_VIEW_WIDTH = WeatherConfig.getResolutionValue(278);
	private final int CITY_VIEW_HIGH = WeatherConfig.getResolutionValue(378);
	private final String BACKGROUND_COLOR = "#FFFFFF";
	private final String OPTION_BACKGROUND_COLOR = "#FFFFFF";
	private final int OPTION_VIEW_WIDTH = WeatherConfig.getResolutionValue(278);
	private final int OPTION_VIEW_HIGH = WeatherConfig.getResolutionValue(70);

	private final int WEATHER_DEFAULT_ICON_FOCUS = R.drawable.weather_icon_sunny_focus;
	private final int WEATHER_DEFAULT_ICON_NO_FOCUS = R.drawable.weather_icon_sunny_nofocus;
	private final int OPTION_SET_DEFAULT_FOCUS = R.drawable.set_default_focus;
	private final int OPTION_SET_DEFAULT_NO_FOCUS = R.drawable.set_default_nofocus;
	private final int OPTION_DELETE_FOCUS = R.drawable.delete_focus;
	private final int OPTION_DELETE_NO_FOCUS = R.drawable.delete_nofocus;

	private Context context;
	private boolean lockFocus = false;
	private List<View> cityViewList = new ArrayList<View>();
	private List<String> cityList;

	public CitysettingCityAllView(Context context,
			TCWeatherCitysettingShowInfo dataVal) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		citySettingManager = CitySettingManager.getInstance();
		initLayout();
		initView(dataVal);
	}

	public void initLayout() {
		setOrientation(LinearLayout.VERTICAL);// 线性垂直分布
		setLayoutParams(new LayoutParams(CITY_VIEW_WIDTH,
				LayoutParams.WRAP_CONTENT));
	}

	public void initView(final TCWeatherCitysettingShowInfo data) {
		citysettingOneCityView = new CitysettingOneCityView(context, data);
		if (data != null) {
			citysettingOneCityView.setLayoutParams(new LayoutParams(
					CITY_VIEW_WIDTH, CITY_VIEW_HIGH));
			citysettingOneCityView.setFocusable(true);
			citysettingOneCityView
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							Debugger.d("citysettingOneCityView data="
									+ data.getCityName() + ", focusChange : "
									+ hasFocus);
							if (hasFocus) {
								setOptionVisible(true);
								citysettingOneCityView
										.setBackgroundResource(R.drawable.city_setting_item_bg_focus);
								citysettingOneCityView
										.updateWeatherIcon(data.weatherIcons[1]);
								CitySettingLayout.focusView
										.changeFocusPos(citysettingOneCityView);
							} else {
								if (!lockFocus)
									setOptionVisible(false);
								citysettingOneCityView
										.setBackgroundResource(R.drawable.city_setting_item_bg_unfocus);
								citysettingOneCityView
										.updateWeatherIcon(data.weatherIcons[2]);
							}
						}
					});
			citysettingOneCityView.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// cityViewList =
					// CitysettingShowAllCitiesView.citysettingShowAllCitiesView
					// .getCityViewList();
					// cityList = citySettingManager.getSavedCityList();
					// int index = getIndexByCityName(da)

					if (event.getAction() == KeyEvent.ACTION_UP)
						return true;
					Debugger.d("citysettingOneCityView, data="
							+ data.getCityName() + ", onKeyDown : " + keyCode);
					switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_DOWN:
						lockFocus = true;
						if (isDefault) {
							cityDeletetOptionView.requestFocus();
							citySetDefaultOptionView.setFocusable(false);
							// citySetDefaultOptionView.setVisibility(View.GONE);
						}
						if (CitysettingShowAllCitiesView.citysettingShowAllCitiesView
								.isOnlyOneView()) {
							cityDeletetOptionView.setFocusable(false);
							lockFocus = false;
						}
						citySetDefaultOptionView.requestFocus();
						return true;
					case KeyEvent.KEYCODE_DPAD_LEFT:
						// CitysettingShowAllCitiesView.citysettingShowAllCitiesView
						// .focusFrontView(citysettingOneCityView
						// .getCityName());
						CitysettingShowAllCitiesView.citysettingShowAllCitiesView
								.focusFrontView();
						return true;
					case KeyEvent.KEYCODE_DPAD_RIGHT:
						// CitysettingShowAllCitiesView.citysettingShowAllCitiesView
						// .focusNextView(citysettingOneCityView
						// .getCityName());
						CitysettingShowAllCitiesView.citysettingShowAllCitiesView
								.focusNextView();
						return true;
					case KeyEvent.KEYCODE_BACK:
						((Activity) context).finish();
						return true;
					case KeyEvent.KEYCODE_DPAD_CENTER:

						Intent intent = new Intent();
						intent.putExtra("cityName", data.cityNameString);
						((Activity) context).setResult(Activity.RESULT_OK,
								intent);
						((Activity) context).finish();
						Debugger.i("KeyEvent.KEYCODE_DPAD_CENTER====="
								+ data.cityNameString);

						return true;
					default:
						break;
					}
					return false;
				}
			});

			addView(citysettingOneCityView);

			citySetDefaultOptionView = new CitysettingOptionView(context,
					OPTION_SET_DEFAULT_NO_FOCUS,
					context.getString(R.string.citysetting_option_default));
			LayoutParams layoutParams_citySetDefault = new LayoutParams(
					OPTION_VIEW_WIDTH, OPTION_VIEW_HIGH);
			layoutParams_citySetDefault.setMargins(0, DIVIDE_PADDING, 0, 0);
			citySetDefaultOptionView.setVisibility(View.INVISIBLE);
			citySetDefaultOptionView.setFocusable(true);
			citySetDefaultOptionView
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							Debugger.d("citySetDefaultOptionView "
									+ data.getCityName() + ", focusChange : "
									+ hasFocus);
							if (hasFocus) {
								citySetDefaultOptionView
										.setBackgroundResource(R.drawable.city_setting_item_bg_focus);
								setOptionVisible(true);
								citySetDefaultOptionView
										.updateImage(OPTION_SET_DEFAULT_FOCUS);
								CitySettingLayout.focusView
										.changeFocusPos(citySetDefaultOptionView);
							} else {
								citySetDefaultOptionView
										.setBackgroundResource(R.drawable.city_setting_item_bg_unfocus);
								citySetDefaultOptionView
										.updateImage(OPTION_SET_DEFAULT_NO_FOCUS);
							}

						}
					});
			citySetDefaultOptionView.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_UP)
						return true;
					Debugger.d("citySetDefaultOptionView " + data.getCityName()
							+ ", onKeyDown : " + keyCode);
					switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_DOWN:
						Debugger.d("citySetDefaultOptionView onKeyDown !!");
						cityDeletetOptionView.requestFocus();
						return true;
					case KeyEvent.KEYCODE_DPAD_UP:
						lockFocus = false;
						citysettingOneCityView.requestFocus();
						return true;
					case KeyEvent.KEYCODE_DPAD_CENTER:
						CitysettingShowAllCitiesView.citysettingShowAllCitiesView
								.setCityDefault(citysettingOneCityView
										.getCityName());
						Toast.makeText(context, "设置默认城市成功！", Toast.LENGTH_LONG)
								.show();
						return true;
					case KeyEvent.KEYCODE_DPAD_LEFT:
						citysettingOneCityView.requestFocus();
						Debugger.d("citySetDefaultOptionView keyLeft down!");
						lockFocus = false;
						return true;
					case KeyEvent.KEYCODE_DPAD_RIGHT:
						citysettingOneCityView.requestFocus();
						Debugger.d("citySetDefaultOptionView keyRight down!");
						lockFocus = false;
						return true;
					case KeyEvent.KEYCODE_BACK:
						((Activity) context).finish();
						return true;
					default:
						break;
					}
					return false;
				}
			});

			addView(citySetDefaultOptionView, layoutParams_citySetDefault);

			cityDeletetOptionView = new CitysettingOptionView(context,
					OPTION_DELETE_NO_FOCUS,
					context.getString(R.string.citysetting_option_delete));
			LayoutParams layoutParams_cityDeletet = new LayoutParams(
					OPTION_VIEW_WIDTH, OPTION_VIEW_HIGH);
			layoutParams_cityDeletet.setMargins(0, TWO_OPTIONS_DIVIDE_PADDING,
					0, 0);
			cityDeletetOptionView.setVisibility(View.INVISIBLE);
			cityDeletetOptionView.setFocusable(true);
			// cityDeletetOptionView.setFocusableInTouchMode(true);
			cityDeletetOptionView
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							// TODO Auto-generated method stub
							if (hasFocus) {
								Debugger.d("cityDeletetOptionView hasFocus !");
								setOptionVisible(true);
								cityDeletetOptionView
										.setBackgroundResource(R.drawable.city_setting_item_bg_focus);
								cityDeletetOptionView
										.updateImage(OPTION_DELETE_FOCUS);
								CitySettingLayout.focusView
										.changeFocusPos(cityDeletetOptionView);
							} else {
								cityDeletetOptionView
										.setBackgroundResource(R.drawable.city_setting_item_bg_unfocus);
								cityDeletetOptionView
										.updateImage(OPTION_DELETE_NO_FOCUS);
							}
						}
					});

			cityDeletetOptionView.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_UP)
						return true;
					switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_CENTER:
						// 删除城市
						CitysettingShowAllCitiesView.citysettingShowAllCitiesView
								.deleteOneCity(citysettingOneCityView
										.getCityName());
						Toast.makeText(context, "城市删除成功！", Toast.LENGTH_LONG)
								.show();
						return true;
					case KeyEvent.KEYCODE_DPAD_UP:
						if (isDefault) {
							citysettingOneCityView.requestFocus();
							lockFocus = false;
						} else {
							citySetDefaultOptionView.requestFocus();
						}
						return true;
					case KeyEvent.KEYCODE_DPAD_LEFT:
						citysettingOneCityView.requestFocus();
						lockFocus = false;
						return true;
					case KeyEvent.KEYCODE_DPAD_RIGHT:
						citysettingOneCityView.requestFocus();
						lockFocus = false;
						return true;
					case KeyEvent.KEYCODE_BACK:
						((Activity) context).finish();
						return true;
					default:
						break;
					}
					return false;
				}
			});
			addView(cityDeletetOptionView, layoutParams_cityDeletet);

		}
	}

	public void setDefault(boolean isSet) {
		if (isSet) {
			isDefault = true;
			citysettingOneCityView.setCityDefault(isSet);
		} else {
			isDefault = false;
			citysettingOneCityView.setCityDefault(false);
			citySetDefaultOptionView.setFocusable(true);
			cityDeletetOptionView.setFocusable(true);
		}
	}

	public void setOptionVisible(boolean isVisible) {
		if (isVisible) {
			if (isDefault) {
				citySetDefaultOptionView.setVisibility(View.GONE);
				cityDeletetOptionView.setVisibility(VISIBLE);
			} else {
				citySetDefaultOptionView.setVisibility(VISIBLE);
				cityDeletetOptionView.setVisibility(VISIBLE);
			}

		} else {
			citySetDefaultOptionView.setVisibility(INVISIBLE);
			cityDeletetOptionView.setVisibility(INVISIBLE);
		}
	}

	public void updateViewData(TCWeatherCitysettingShowInfo data) {
		citysettingOneCityView.updateInfo(data);
	}

	public void setLockFocus(boolean isLock) {
		lockFocus = isLock;
	}

	public String getViewCityName() {
		return citysettingOneCityView.getCityName();
	}

	/**
	 * 根据城市名称找到在list中的index
	 * 
	 * @param city
	 * @return
	 */
	public int getIndexByCityName(String city) {
		if (cityList != null) {
			cityList = citySettingManager.getSavedCityList();
			for (int i = 0; i < cityList.size(); i++) {
				Debugger.d("getIndexByCityName city=" + cityList.get(i));
				if (cityList.get(i).equals(city)) {
					return i;
				}
			}
		}

		return -1;
	}

}

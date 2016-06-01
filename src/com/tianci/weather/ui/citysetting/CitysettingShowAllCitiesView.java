package com.tianci.weather.ui.citysetting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tianci.weather.CitySelectActivity;
import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.TCWeather;
import com.tianci.weather.TCWeather.TCWeatherCallback;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.CachedWeather;
import com.tianci.weather.data.TCWeatherCitysettingShowInfo;
import com.tianci.weather.data.TCWeatherEnum;
import com.tianci.weather.data.TCWeatherInfo;
import com.tianci.weather.ui.UIUtils;
import com.tianci.weather.ui.Utils;

public class CitysettingShowAllCitiesView extends FrameLayout implements
		TCWeatherCallback {

	private CitysettingCityAllView cityDefaultView;
	private CitysettingAddOneCityView citysettingAddOneCityView;
	private Context context;
	private List<String> cityList; // 城市名称列表
	private List<TCWeatherCitysettingShowInfo> cityViewDataList; // 城市view显示数据list
	private List<View> cityViewList; // 城市view list

	private String defaultCityNameString;
	private String addCityNameString;
	private CitySettingManager citySettingManager;
	private CachedWeather cachedWeather;
	public TCWeather tcWeather;
	private boolean isScorlled;
	private boolean isError;

	public static CitysettingShowAllCitiesView citysettingShowAllCitiesView;

	private final int TOP_MARGIN = WeatherConfig.getResolutionValue(163);
	private final int DIVIDE_LEFT_MARGIN = WeatherConfig.getResolutionValue(60);
	private final int CITY_VIEW_WIDTH = WeatherConfig.getResolutionValue(278);
	private final int CITY_VIEW_HIGH = WeatherConfig.getResolutionValue(378);
	private final int ALLCITY_LEFT_MARGIN = WeatherConfig
			.getResolutionValue(186);

	private final int ADD_CITY_VIEW_HIGH = WeatherConfig
			.getResolutionValue(378);
	private final String BACKGROUND_COLOR = "#FFFFFF";
	private final int ADD_CITY_BACKGROUND_IMAGE = R.drawable.add_city_background;
	private final int ADD_CITY_FOCUS_IMAGE = R.drawable.city_add_focus;
	private final int ADD_CITY_NO_FOCUS_IMAGE = R.drawable.city_add_nofocus;
	private final int START_CITY_SELECT_ACTIVITY_REQUESCODE = 1;
	private final int VIEW_MOVE_OFFSET = CITY_VIEW_WIDTH + DIVIDE_LEFT_MARGIN;
	private final int MOVE_TIME = 300;
	private final int MAX_CITY_COUNT = 10;

	private CitysettingLoadingErrorView citysettingLoadingErrorView;
	private CitySettingLoadingView citySettingLoadingView;

	private int index = 0;

	public CitysettingShowAllCitiesView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		citysettingShowAllCitiesView = this;
		isScorlled = false;
		citySettingManager = CitySettingManager.getInstance();
		cachedWeather = CachedWeather.getInstance();
		tcWeather = new TCWeather();
		cityViewDataList = new ArrayList<TCWeatherCitysettingShowInfo>();
		cityViewList = new ArrayList<View>();

		initLayout();
		initData();
		initView();
	}

	public void initLayout() {
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	/**
	 * 初始化数据： 根据cityList获取到每一个city对应的天气信息
	 */
	public void initData() {
		cityList = citySettingManager.getSavedCityList();
		defaultCityNameString = citySettingManager.getCurrentCity();
		TCWeatherCitysettingShowInfo tempCitysettingShowInfo;
		TCWeatherInfo tempInfo;

		if (cityList.size() > 4) {
			isScorlled = true;
		}

		for (int i = 0; i < cityList.size(); i++) {
			String city = cityList.get(i);
			tempInfo = cachedWeather.getCachedWeatherInfo(city);
			if (tempInfo != null) {
				tempCitysettingShowInfo = new TCWeatherCitysettingShowInfo(
						city, UIUtils.getWeatherIconRes(tempInfo.daysInfo
								.get(0).WEnum),
						Integer.valueOf(tempInfo.daysInfo.get(0).MinDegree),
						Integer.valueOf(tempInfo.daysInfo.get(0).MaxDegree));
				cityViewDataList.add(tempCitysettingShowInfo);
			} else {
				tempCitysettingShowInfo = new TCWeatherCitysettingShowInfo(
						city, UIUtils.getWeatherIconRes(TCWeatherEnum.SUNNY),
						10, 30);
				cityViewDataList.add(tempCitysettingShowInfo);
			}

		}
	}

	public void initView() {
		// 添加默认城市view
		index = 0;
		cityViewList.clear();
		removeAllViews();
		Debugger.d("defaultCityNameString=" + defaultCityNameString);
		cityDefaultView = new CitysettingCityAllView(context,
				cityViewDataList.get(0));
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.leftMargin = ALLCITY_LEFT_MARGIN;
		cityDefaultView.setLayoutParams(layoutParams);
		cityDefaultView.setDefault(true);
		addView(cityDefaultView);
		cityViewList.add(cityDefaultView);
		Debugger.d("cityDefaultView.getLeft()=" + cityDefaultView.getLeft());
		// 添加其他城市view
		CitysettingCityAllView tempCitysettingCityAllView;
		for (int i = 1; i < cityList.size(); i++) {
			String cityName = cityList.get(i);
			Debugger.d("cityName=" + cityName);
			tempCitysettingCityAllView = new CitysettingCityAllView(context,
					cityViewDataList.get(i));
			tempCitysettingCityAllView
					.setLayoutParams(getCityViewLayoutParams(i));
			addView(tempCitysettingCityAllView);
			cityViewList.add(tempCitysettingCityAllView);
		}

		citysettingAddOneCityView = new CitysettingAddOneCityView(context);
		citysettingAddOneCityView
				.setLayoutParams(getAddOneCityViewLayoutParams(cityList.size()));
		citysettingAddOneCityView.setFocusable(true);
		addView(citysettingAddOneCityView);
		citysettingAddOneCityView
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							// citysettingAddOneCityView.setBackgroundResource(R.drawable.boder);
							citysettingAddOneCityView
									.setBackgroundResource(R.drawable.city_setting_item_bg_focus);
							citysettingAddOneCityView
									.updateImageView(ADD_CITY_FOCUS_IMAGE);
							CitySettingLayout.focusView
									.changeFocusPos(citysettingAddOneCityView);
							Debugger.d("citysettingAddOneCityView : "
									+ hasFocus);
						} else {
							citysettingAddOneCityView
									.setBackgroundResource(ADD_CITY_BACKGROUND_IMAGE);
							citysettingAddOneCityView
									.updateImageView(ADD_CITY_NO_FOCUS_IMAGE);
							Debugger.d("citysettingAddOneCityView : "
									+ hasFocus);
						}
					}
				});
		citysettingAddOneCityView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP)
					return true;
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_CENTER:
					// 判断当前城市数量是否已经达到最大限制
					if (citySettingManager.getSavedCityList().size() >= 10) {
						Toast.makeText(context, R.string.add_city_full,
								Toast.LENGTH_LONG).show();
					} else {
						Intent intent = new Intent(context,
								CitySelectActivity.class);
						((Activity) context).startActivityForResult(intent,
								START_CITY_SELECT_ACTIVITY_REQUESCODE);

					}
					return true;
				case KeyEvent.KEYCODE_BACK:
					((Activity) context).finish();
					return true;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					focusFrontView();
					// cityViewList.get(cityViewList.size() - 1).requestFocus();
					return true;
				default:
					break;
				}
				return false;
			}
		});

	}

	public void setAddCityName(String city) {
		Debugger.d("addCityName=" + city);
		if (Utils.isOnline(context)) {
			citySettingManager.addCity(city);
			addOneCity(city);
			tcWeather.getWeather(city, this);
		} else {
			addLoadingErrorView(city);
		}

	}

	public void addOneCity(String city) {
		Debugger.d("addOneCity   addCityName=" + city);
		final String cityName = city;
		cityList = citySettingManager.getSavedCityList();
		int size = cityList.size();
		if (size > 4) {
			isScorlled = true;
		}
		Debugger.d("addOneCity Size=" + size);
		removeView(citysettingAddOneCityView);
		citySettingLoadingView = new CitySettingLoadingView(context, city);
		LayoutParams citySettingLoadingViewParams = new LayoutParams(
				CITY_VIEW_WIDTH, CITY_VIEW_HIGH);
		citySettingLoadingViewParams.leftMargin = VIEW_MOVE_OFFSET * (size - 1)
				+ ALLCITY_LEFT_MARGIN;
		addView(citySettingLoadingView, citySettingLoadingViewParams);
		cityViewList.add(citySettingLoadingView);
		citySettingLoadingView.setFocusable(true);
		citySettingLoadingView.requestFocus();

		citySettingLoadingView
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							citySettingLoadingView.requestFocus();
							CitySettingLayout.focusView
									.changeFocusPos(citySettingLoadingView);
						} else {

						}

					}
				});
		citySettingLoadingView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP) {
					return true;
				}
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_CENTER:
					Intent intent = new Intent();
					intent.putExtra("cityName", cityName);
					((Activity) context).setResult(Activity.RESULT_OK, intent);
					((Activity) context).finish();
					Debugger.i("KeyEvent.KEYCODE_DPAD_CENTER=====" + cityName);

					return true;
				case KeyEvent.KEYCODE_BACK:
					((Activity) context).finish();
					return true;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					focusFrontView();
					return true;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					focusNextView();
					return true;
				default:
					break;
				}
				return false;
			}
		});

		citysettingAddOneCityView
				.setLayoutParams(getAddOneCityViewLayoutParams(size));
		addView(citysettingAddOneCityView);

		index = size - 1;

	}

	public void updateOneCity(TCWeatherCitysettingShowInfo data) {
		// initView();
		cityList = citySettingManager.getSavedCityList();
		int size = cityList.size();
		if (size > 4) {
			isScorlled = true;
		}
		Debugger.d("updateOneCity Size=" + size);
		removeView(citysettingAddOneCityView);
		removeView(cityViewList.get(size - 1));
		cityViewList.remove(size - 1);
		CitysettingCityAllView citysettingCityAllView = new CitysettingCityAllView(
				context, data);
		citysettingCityAllView
				.setLayoutParams(getCityViewLayoutParams(size - 1));
		addView(citysettingCityAllView);
		citysettingAddOneCityView
				.setLayoutParams(getAddOneCityViewLayoutParams(size));
		addView(citysettingAddOneCityView);
		cityViewList.add(citysettingCityAllView);
		citysettingCityAllView.requestFocus();
		isError = false;
		index = size - 1;
	}

	@Override
	public void onWeatherUpdate(TCWeatherInfo info) {

		if (info != null) {
			Debugger.d("add city weather info:" + info.baseInfo.city + ":"
					+ info.baseInfo.publishTime);
			TCWeatherCitysettingShowInfo data = new TCWeatherCitysettingShowInfo(
					info.baseInfo.city, UIUtils.getWeatherIconRes(info.daysInfo
							.get(0).WEnum), Integer.valueOf(info.daysInfo
							.get(0).MinDegree), Integer.valueOf(info.daysInfo
							.get(0).MaxDegree));
			cityViewDataList.add(data);
			updateOneCity(data);
			Toast.makeText(context, "添加城市\"" + info.baseInfo.city + "\"成功！",
					Toast.LENGTH_LONG).show();
		}
	}

	private void addLoadingErrorView(final String city) {
		cityList = citySettingManager.getSavedCityList();
		final int size = cityList.size();
		if (size > 4) {
			isScorlled = true;
		}
		Debugger.d("addOneCity Size=" + size);
		removeView(citysettingAddOneCityView);
		removeView(cityViewList.get(size - 1));
		cityViewList.remove(size - 1);
		citysettingLoadingErrorView = new CitysettingLoadingErrorView(context);
		LayoutParams citysettingLoadingErrorViewpParams = new LayoutParams(
				CITY_VIEW_WIDTH, CITY_VIEW_HIGH);
		citysettingLoadingErrorViewpParams.leftMargin = VIEW_MOVE_OFFSET
				* (size - 1) + ALLCITY_LEFT_MARGIN;
		addView(citysettingLoadingErrorView, citysettingLoadingErrorViewpParams);

		citysettingAddOneCityView
				.setLayoutParams(getAddOneCityViewLayoutParams(size));
		addView(citysettingAddOneCityView);
		citysettingLoadingErrorView.setFocusable(true);
		citysettingLoadingErrorView.requestFocus();
		CitySettingLayout.focusView.changeFocusPos(citysettingLoadingErrorView);
		cityViewList.add(citysettingLoadingErrorView);
		isError = true;

		index = size - 1;

		citysettingLoadingErrorView
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						Debugger.i("citysettingLoadingErrorView>>>>>>>"
								+ hasFocus);
						if (hasFocus) {
							citysettingLoadingErrorView.requestFocus();
							CitySettingLayout.focusView
									.changeFocusPos(citysettingLoadingErrorView);
						} else {

						}
					}
				});

		citysettingLoadingErrorView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP)
					return true;
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_CENTER:

					tcWeather.getWeather(city, citysettingShowAllCitiesView);

					return true;

				case KeyEvent.KEYCODE_DPAD_RIGHT:
					// citysettingAddOneCityView.requestFocus();
					focusNextView();
					return true;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					// cityViewList.get(size - 2).requestFocus();
					focusFrontView();
					return true;

				default:
					break;
				}

				return false;
			}
		});

	}

	@Override
	public void onWeatherUpdateError(String city) {
		addLoadingErrorView(city);
	}

	@Override
	public void onWeatherUpdateBefore() {

	}

	public List<View> getCityViewList() {
		return cityViewList;
	}

	/**
	 * 设置默认城市
	 * 
	 * @param city
	 */
	public void setCityDefault(String city) {
		cityList = citySettingManager.getSavedCityList();
		if (city != null) {
			int targetIndex = getIndexByCityName(city);
			Debugger.d("targetIndex=" + targetIndex);
			TCWeatherCitysettingShowInfo targetCitysettingShowInfo = cityViewDataList
					.get(targetIndex);
			CitysettingCityAllView targetCityAllView = (CitysettingCityAllView) cityViewList
					.get(targetIndex);
			((CitysettingCityAllView) cityViewList.get(0)).setDefault(false);

			// 将targetView移动到最左端
			if (cityViewList.size() > 1) {
				moveView(targetCityAllView, (VIEW_MOVE_OFFSET * targetIndex)
						* (-1));
				targetCityAllView.setLockFocus(false);
				targetCityAllView.setDefault(true);
				// 将target之前view右移一个单位
				for (int i = 0; i <= targetIndex - 1; i++) {
					moveView(cityViewList.get(i), VIEW_MOVE_OFFSET);
				}

				// 调整list数据顺序
				for (int i = targetIndex; i > 0; i--) {
					Debugger.d("cityName=" + cityList.get(i - 1));
					cityViewList.set(i, cityViewList.get(i - 1));
					cityViewDataList.set(i, cityViewDataList.get(i - 1));
					citySettingManager.setIndexCity(cityList.get(i - 1), i);
				}
				cityViewDataList.set(0, targetCitysettingShowInfo);
				cityViewList.set(0, targetCityAllView);
				citySettingManager.setIndexCity(city, 0);
				citySettingManager.setCurrentCity(city);

				if (cityList.size() >= 5) {
					Debugger.d("cityList.size()=" + cityList.size());
					CitySettingLayout.citySettingLayout.setScrollLeft();
					cityViewList.get(0).requestFocus();
					// cityViewList.get(1).requestFocus();
				} else {
					cityViewList.get(0).requestFocus();
				}
			}
		}
		index = 0;

		Debugger.d("SET_DEFAULT citySettingManager.getSavedCityList():");
		for (int i = 0; i < citySettingManager.getSavedCityList().size(); i++) {
			Debugger.d(citySettingManager.getSavedCityList().get(i) + " ");
		}

		Debugger.d("SET_DEFAULT cityViewDataList:");
		for (int i = 0; i < cityViewDataList.size(); i++) {
			Debugger.d(cityViewDataList.get(i).getCityName() + " ");
		}

		Debugger.d("SET_DEFAULT cityViewList:");
		for (int i = 0; i < cityViewList.size(); i++) {
			/* Debugger.d(cityViewList.get(i).getViewCityName() + " "); */
		}

		Debugger.d("DEFAULT_CITY=" + citySettingManager.getCurrentCity());
	}

	public boolean isOnlyOneView() {
		if (citySettingManager.getSavedCityList().size() <= 1) {
			return true;
		}

		return false;
	}

	/**
	 * 删除城市操作
	 * 
	 * @param city
	 *            需要删除城市的名称
	 */
	public void deleteOneCity(String city) {
		if (city != null) {
			cityList = citySettingManager.getSavedCityList();
			int size = cityList.size();
			int targetIndex = getIndexByCityName(city);
			Debugger.d("deleteOneCity city=" + city + " targetIndex="
					+ targetIndex);
			removeView(cityViewList.get(targetIndex));

			for (int i = targetIndex + 1; i < size; i++) {
				moveView(cityViewList.get(i), VIEW_MOVE_OFFSET * (-1));
			}
			moveView(citysettingAddOneCityView, VIEW_MOVE_OFFSET * (-1));
			// list移除数据
			cityViewList.remove(targetIndex);
			cityViewDataList.remove(targetIndex);
			citySettingManager.deleteCity(city);

			if (isScorlled && citySettingManager.getSavedCityList().size() <= 4) {
				Debugger.d("isScorlled=" + isScorlled);
				initView();
				isScorlled = false;
			}

			if (targetIndex == 0) {
				cityViewList.get(0).requestFocus();
				index = 0;
			} else {
				cityViewList.get(targetIndex - 1).requestFocus();
				index--;
				//initView();
			}

			Debugger.d("DELETE citySettingManager.getSavedCityList():");
			for (int i = 0; i < citySettingManager.getSavedCityList().size(); i++) {
				Debugger.d(citySettingManager.getSavedCityList().get(i) + " ");
			}

			Debugger.d("DELETE cityViewDataList:");
			for (int i = 0; i < cityViewDataList.size(); i++) {
				Debugger.d(cityViewDataList.get(i).getCityName() + " ");
			}

			Debugger.d("DELETE cityViewList:");
			for (int i = 0; i < cityViewList.size(); i++) {
				/* Debugger.d(cityViewList.get(i).getViewCityName() + " "); */
			}
			
			// index--;
		}
	}

	public void moveView(View view, int distence) {
		view.animate().translationXBy(distence).setDuration(MOVE_TIME).start();
	}

	// public void focusNextView(String city) {
	// Debugger.d("focusNextView city=" + city);
	// int index = getIndexByCityName(city);
	// Debugger.d("focusNextView index=" + index);
	// if (index < cityViewList.size() - 1) {
	// /*
	// * Debugger.d("NextView:" + cityViewList.get(index +
	// * 1).getViewCityName());
	// */
	// cityViewList.get(index + 1).requestFocus();
	// } else {
	// citysettingAddOneCityView.requestFocus();
	// }
	// }
	//
	// public void focusFrontView(String city) {
	// Debugger.d("focusFrontView city=" + city);
	// int index = getIndexByCityName(city);
	// Debugger.d("focusFrontView index=" + index);
	// if (index > 0) {
	// /*
	// * Debugger.d("FrontView:" + cityViewList.get(index -
	// * 1).getViewCityName());
	// */
	// cityViewList.get(index - 1).requestFocus();
	// }
	// }

	public void focusNextView() {

		Debugger.d("focusNextView index=" + index);
		if (index < cityViewList.size() - 1) {
			/*
			 * Debugger.d("NextView:" + cityViewList.get(index +
			 * 1).getViewCityName());
			 */
			cityViewList.get(index + 1).requestFocus();
			index++;
		} else {
			citysettingAddOneCityView.requestFocus();
			index++;
		}
	}

	public void focusFrontView() {

		Debugger.d("focusFrontView index=" + index);
		if (index > 0) {
			/*
			 * Debugger.d("FrontView:" + cityViewList.get(index -
			 * 1).getViewCityName());
			 */
			cityViewList.get(index - 1).requestFocus();
			index--;
		}
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

	public LayoutParams getAddOneCityViewLayoutParams(int count) {
		LayoutParams layoutParams = new LayoutParams(CITY_VIEW_WIDTH,
				ADD_CITY_VIEW_HIGH);
		layoutParams.leftMargin = VIEW_MOVE_OFFSET * count
				+ ALLCITY_LEFT_MARGIN;

		return layoutParams;
	}

	/**
	 * 获得cityview的LayoutParams
	 * 
	 * @param index
	 * @return
	 */
	public LayoutParams getCityViewLayoutParams(int count) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.leftMargin = VIEW_MOVE_OFFSET * count
				+ ALLCITY_LEFT_MARGIN;
		return layoutParams;
	}
}

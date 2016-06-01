package com.tianci.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tianci.weather.TCWeather.GetWeatherRunnable;
import com.tianci.weather.TCWeather.TCWeatherCallback;
import com.tianci.weather.data.CachedWeather;
import com.tianci.weather.data.TCWeatherEnum;
import com.tianci.weather.data.TCWeatherInfo;
import com.tianci.weather.ui.UIUtils;
import com.tianci.weather.ui.Utils;
import com.tianci.weather.ui.WeatherAnim;
import com.tianci.weather.ui.WeatherLoadingView;
import com.tianci.weather.ui.citysetting.CitySettingManager;
import com.tianci.weather.ui.weather.WeatherForegroundView;
import com.tianci.weather.ui.weather.WeatherLayout;

/**
 * @Date : 2016年4月13日0
 * @Author : Zhan Yu
 * @Description : 天气Activity
 */
public class WeatherActivity extends Activity implements TCWeatherCallback {
	private WeatherLayout weatherLayout;
	private TCWeather tcWeather;
	private WeatherForegroundView weatherForegroundView; 
	public  WeatherLoadingView weatherLoadingView;
	private ViewFlipper mViewFlipper;
	private ImageView rightArrow, leftArrow;
	private WeatherAnim animation;
	private CitySettingManager citySettingManager;
	private List<String> cityList;
	private String defaultCity;
	private Map<String, TCWeatherInfo> tcWeatherInfos;
	private CachedWeather cachedWeather;
	private boolean FLAG = true;
	final int MIN = 0;
	int MAX = 0 ;
	int curIndex = 0;
	int index = 0;
	private final int START_CITY_SETTING_REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Debugger.d("WeatherActivity onCreate");
		weatherLayout = new WeatherLayout(this);		
		setContentView(weatherLayout);
		 		
		weatherLoadingView = weatherLayout.getLoadingViewInstance();
		leftArrow = (ImageView) findViewById(1);
		mViewFlipper = (ViewFlipper) findViewById(2);
		rightArrow = (ImageView) findViewById(3);

		tcWeather = new TCWeather();
		tcWeatherInfos = new HashMap<String, TCWeatherInfo>();
		citySettingManager = CitySettingManager.getInstance();// 获取CitySettingManager实例
	}

	@Override
	protected void onResume() {
		super.onResume();
		Debugger.i("====onresume");	
		setDefaultCityView();//设置显示默认城市	
		getCityWeather();//获取城市天气
		mViewFlipper.setDisplayedChild(curIndex);
		tcWeather.getWeather(cityList.get(curIndex), this);
		changeArrowVisibility();
	}

	@Override
	protected void onDestroy() {
		tcWeather.shutdown();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {		
		switch (keyCode) {
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			// String city = defaultCity;
			// Debugger.d("start getWeather : " + city);
			// tcWeather.getWeather(city, this);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:// 按下方向键（D-pad）的左键
			onKeyLeftDown();
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:// 按下方向键（D-pad）的右键
			onKeyRightDown();
			break;
		case KeyEvent.KEYCODE_MENU:// 按下菜单键
			startCitySetting();// 启动城市设置Activity
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 根据天气信息更改背景图片
	 * @param info
	 */
	private void changeBackground(TCWeatherInfo info) {
		TCWeatherEnum curWeather = null;		
		if(info != null)
			curWeather = info.daysInfo.get(0).WEnum; 
		weatherLayout.changeBackground(UIUtils.getWeatherBackgroundDrawable(curWeather));
	}
	/**
	 * 设置显示默认城市
	 */
	private void setDefaultCityView(){
		defaultCity = citySettingManager.getCurrentCity();
		weatherLayout.initializeForeView(defaultCity);
	}
	/**
	 * 获取城市天气
	 */
	private void getCityWeather(){
		cityList = citySettingManager.getSavedCityList();// 获取保存的城市列表
		Debugger.i("getCityWeather........"+citySettingManager.getCurrentCity());
		weatherLayout.initializeCityList(cityList, defaultCity);
		if (Utils.isOnline(this)) {
			//weatherLoadingView.setVisibility(View.VISIBLE);
			tcWeather.getWeather(defaultCity, this);// 获取默认城市的天气 
			for (int i = 0; i < cityList.size(); i++) {
				if (!cityList.get(i).contentEquals(defaultCity)) {	
					//weatherLoadingView.setVisibility(View.VISIBLE);
					Debugger.i("getCityWeather........"+cityList.get(i));
					tcWeather.getWeather(cityList.get(i), this);// 获取城市列表中相应城市的天气
				}
			}
		} else {	
			tcWeather.getWeather(defaultCity, this);// 获取默认城市的天气
			for (int i = 0; i < cityList.size(); i++) {
				if (!cityList.get(i).contentEquals(defaultCity)) {
					tcWeather.getWeather(cityList.get(i), this);// 获取城市列表中相应城市的天气
				}
			}
			Toast.makeText(this, "网络无连接", Toast.LENGTH_LONG).show();
		}
	}
	/**
	 * 改变左右箭头的可见性
	 */
	private void changeArrowVisibility(){
		MAX = mViewFlipper.getChildCount() - 1;// 获取ViewFliper中View的个数
		curIndex = mViewFlipper.getDisplayedChild();// 得到ViewFlipper中当前显示子View在ViewFlipper中的index（第几个）
		if (cityList.size() == 1) {
			rightArrow.setVisibility(View.INVISIBLE);
			leftArrow.setVisibility(View.INVISIBLE);
		} else if (cityList.size() > 1) {
			if(curIndex == MIN){				
				leftArrow.setVisibility(View.INVISIBLE);
				rightArrow.setVisibility(View.VISIBLE);
			}else if(MIN < curIndex && curIndex < MAX){
				rightArrow.setVisibility(View.VISIBLE);
				leftArrow.setVisibility(View.VISIBLE);
			}else if(curIndex == MAX){
				leftArrow.setVisibility(View.VISIBLE);
				rightArrow.setVisibility(View.INVISIBLE);
			}
		}
	}
	/** 
	 * 按下方向键（D-pad）的左键 
	 */
	private void onKeyLeftDown(){
		index = curIndex - 1;
		if(index < MIN){// 判断是否到了第一个子View	
			leftArrow.setVisibility(View.INVISIBLE);
			rightArrow.setVisibility(View.VISIBLE);
			mViewFlipper.setDisplayedChild(curIndex);// 设置当前显示的子View
			WeatherAnim.shakeLeft(mViewFlipper.getCurrentView());// 让第一个子View抖动，提示已经是第一个子View，从而不让ViewFlipper继续循环
		}else{
			if(index == MIN){
				leftArrow.setVisibility(View.INVISIBLE);
				rightArrow.setVisibility(View.VISIBLE);
			}else{
				leftArrow.setVisibility(View.VISIBLE);
				rightArrow.setVisibility(View.VISIBLE);
			}				
			WeatherAnim.slideLeft(mViewFlipper, this);
			mViewFlipper.setDisplayedChild(index);
			curIndex = index;
			TCWeatherInfo info = tcWeatherInfos.get(cityList.get(curIndex));
			changeBackground(info);
		}
		Debugger.i("curIndex=====" + curIndex);
	}
	/** 
	 * 按下方向键（D-pad）的右键
	 */
	private void onKeyRightDown(){
		index = curIndex + 1;
		if (index > MAX) {// 判断是否到了最后一个子View
			leftArrow.setVisibility(View.VISIBLE);
			rightArrow.setVisibility(View.INVISIBLE);
			mViewFlipper.setDisplayedChild(curIndex);
			WeatherAnim.shakeRight(mViewFlipper.getCurrentView());// 让最后一个子View抖动，提示已经是最后一个子View，从而不让ViewFlipper继续循环
		} else {
			if(index == MAX){
				leftArrow.setVisibility(View.VISIBLE);
				rightArrow.setVisibility(View.INVISIBLE);
			}else{
				leftArrow.setVisibility(View.VISIBLE);
				rightArrow.setVisibility(View.VISIBLE);
			}				
			WeatherAnim.slideRight(mViewFlipper, this);
			mViewFlipper.setDisplayedChild(index);
			curIndex = index;
			TCWeatherInfo info = tcWeatherInfos.get(cityList.get(curIndex));
			changeBackground(info);
		}
		Debugger.i("curIndex=====" + curIndex);
	}
	/**
	 *  启动城市设置Activity
	 */
	private void startCitySetting() {
		Debugger.d("startCitySetting");
		Intent intent = new Intent();
		intent.setClass(WeatherActivity.this, CitySettingActivity.class);
		startActivityForResult(intent, START_CITY_SETTING_REQUEST_CODE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Debugger.i("====onActivityResult");	
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {			
		case START_CITY_SETTING_REQUEST_CODE:
			if(resultCode == Activity.RESULT_OK){
				String cityName = data.getStringExtra("cityName");
				Debugger.i("getCityName===="+cityName);	
				int cityIndex = 0;
				for (cityIndex = 0; cityIndex < cityList.size(); cityIndex++) {
					String city = cityList.get(cityIndex);
					if(city.equals(cityName)){	
						Debugger.i("i===="+cityIndex);	
						//mViewFlipper.setDisplayedChild(cityIndex);
						curIndex = cityIndex;
					}
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// TODO 获取天气信息，应该放到其他地方

		}
	}
	/**
	 * 实现TCWeatherCallback接口的onWeatherUpdate方法
	 */
	@Override
	public void onWeatherUpdate(TCWeatherInfo info) {
		// TODO 获取到天气信息回调回来
		Debugger.d("===onWeatherUpdate:"+info.baseInfo.city);
		//更新天气信息
		weatherLayout.updateWeather(info);
		//保存城市及对应的天气信息
		tcWeatherInfos.put(info.baseInfo.city, info);
		changeBackground(tcWeatherInfos.get(info.baseInfo.city));
		//更换默认城市对应背景图片
		if(tcWeatherInfos.containsKey(defaultCity)){
			if(FLAG){	
				changeBackground(tcWeatherInfos.get(defaultCity));					
				Debugger.d("the backgroundView of defaultCity is updated!");
				FLAG = false;
			}else{
				Debugger.d("the backgroundView of defaultCity need not to be updated!");
			}								
		}			
		//取消loading动画
		weatherLoadingView.setVisibility(View.INVISIBLE); 
	}
	/**
	 * 实现TCWeatherCallback接口的onWeatherUpdateError方法
	 */
	@Override
	public void onWeatherUpdateError( String city) {
		Debugger.d("====onWeatherUpdateError:"+city);
		final String cityName = city;
		Debugger.i("final cityName==="+cityName);
		weatherLoadingView.loadingFailure();
		weatherLoadingView.getLoadingRetryInstance().setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				weatherLoadingView.reloading();
				tcWeather.getWeather(cityName, WeatherActivity.this);
			}
		});
	}
	/**
	 * 实现TCWeatherCallback接口的onWeatherUpdateBefore方法
	 */
	@Override
	public void onWeatherUpdateBefore() {
		// loadingView
		weatherLoadingView.setVisibility(View.VISIBLE);
	}
}

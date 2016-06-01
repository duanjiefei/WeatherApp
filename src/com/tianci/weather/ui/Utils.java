package com.tianci.weather.ui;

import android.app.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 
 * @author 
 * @date 2016-5-6
 * @description
 */

public class Utils {
	/***
	 * 判断网络是否连接
	 * 
	 * @Title:isOnline
	 * @param context
	 * @return boolean
	 */
	public static boolean isOnline(Context context) {

		ConnectivityManager manger = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manger.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断wifi是否处于连接状态
	 * 
	 * @Title:isWifiConnect
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifiConnect(Context context) {
		ConnectivityManager manger = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manger
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;

	}
}

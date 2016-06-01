package com.tianci.weather.ui;

public interface HttpCallBackListener {
	void onFinish(String response);

	void onError(Exception e);

}

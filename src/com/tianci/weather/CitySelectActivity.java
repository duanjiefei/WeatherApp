package com.tianci.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tianci.weather.ui.citysetting.CitySettingManager;
import com.tianci.weather.ui.citysetting.add.AddCityLayout;
import com.tianci.weather.ui.citysetting.add.AddCityLayout.IAddCityLayout;

public class CitySelectActivity extends Activity implements IAddCityLayout {

	AddCityLayout citySelectView;
	
	public CitySelectActivity() {
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		citySelectView = new AddCityLayout(this);
		citySelectView.setIAddCityLayout(this);
		setContentView(citySelectView);
		citySelectView.post(new Runnable()
		{
			
			@Override
			public void run()
			{
				citySelectView.update();
			}
		});
	}

	@Override
	public void onCitySelect(String city, String province)
	{
		if(CitySettingManager.getInstance().getSavedCityList().contains(city))
		{
			Debugger.d("city exist in savedCityList, cannot add");
			Toast.makeText(this, getResources().getText(R.string.add_city_dumplicate), Toast.LENGTH_SHORT).show();
			return ;
		}
		Debugger.d("setResultOk : " + city);
		Intent intent = new Intent();
		intent.putExtra("addCityName", city);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
}

package com.tianci.weather.ui.citysetting.add;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.ui.MarqueeTextView;
import com.tianci.weather.ui.WeatherFocusFrame;

public class AddCityLayout extends FrameLayout implements FocusChangeInf
{
	public interface IAddCityLayout
	{
		public void onCitySelect(String city, String province);
	}
	private IAddCityLayout listener;

	private String[] locationData = new String[]{"",""};
	private FrameLayout main;
	private WeatherFocusFrame focusView = null;
	private List<SkySelectItemView> allItemList;
	private FlowViewGroup dirLayout,specLayout;
	private Context mContext;
	private int dirCitySelect = -1;
	private int provSelect = -1;
	private int specSelect = -1;
	private FlowViewGroup flowGroup;
	private List<String> allData;
	private int curClickViewId = -1;
	private TextView dirCity,province,specRegion;
	private List<String> provList = null;
	private List<String> specRegionList = null;
	private List<String> dirCityList = null;
	private List<String> cityList = null;
	private List<SkySelectItemView> allCityItemList = null;
	private int topMargin = 397;
	private int leftMargin = 434;
	private int CITYBASE = 999;
	private String selectProv = null;
	private LinearLayout locationBg = null;
	private LinearLayout locLayout2 = null;

	public AddCityLayout(Context context) 
	{
		super(context);
		mContext = context;
		
		initTitle();
		
		main = new FrameLayout(mContext);
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mParams.setMargins(0, 0, 0, 0);
		main.setLayoutParams(mParams);
//		main.setBackgroundResource(R.drawable.setting_item_bg_unfocus);
		
  
		locationBg = new LinearLayout(mContext);
		FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(WeatherConfig.getResolutionValue(1553), WeatherConfig.getResolutionValue(807));
		bgParams.setMargins(WeatherConfig.getResolutionValue(186), WeatherConfig.getResolutionValue(186), 0, 0);
		locationBg.setLayoutParams(bgParams);

        focusView = new WeatherFocusFrame(context, WeatherConfig.getResolutionValue(83));
        focusView.setImgResourse(R.drawable.ui_sdk_btn_focus_shadow_no_bg);
		focusView.setFocusable(false);
		focusView.setFocusableInTouchMode(false);
		focusView.needAnimtion(true);
		focusView.setAnimDuration(120);
		focusView.setInterpolator(new AccelerateDecelerateInterpolator());
		
		allItemList = new ArrayList<SkySelectItemView>();
		allData = new ArrayList<String>();
		dirCity = new TextView(mContext);
		FrameLayout.LayoutParams dirParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dirParams.setMargins(WeatherConfig.getResolutionValue(284), WeatherConfig.getResolutionValue(345), 0, 0);
		dirCity.setLayoutParams(dirParams);
		dirCity.setTextSize(WeatherConfig.getDpiValue(36));
		dirCity.setTextColor(Color.parseColor("#1b68aa"));
		dirCity.setText("直辖市");

		province = new TextView(mContext);
		FrameLayout.LayoutParams provParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		provParams.setMargins(WeatherConfig.getResolutionValue(284), WeatherConfig.getResolutionValue(450), 0, 0);
		province.setLayoutParams(provParams);
		province.setTextSize(WeatherConfig.getDpiValue(36));
		province.setTextColor(Color.parseColor("#1b68aa"));
		province.setText("省份");
		
		specRegion = new TextView(mContext);
		FrameLayout.LayoutParams specParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		specParams.setMargins(WeatherConfig.getResolutionValue(284), WeatherConfig.getResolutionValue(920), 0, 0);
		specRegion.setLayoutParams(specParams);
		specRegion.setTextSize(WeatherConfig.getDpiValue(36));
		specRegion.setTextColor(Color.parseColor("#1b68aa"));
		specRegion.setText("特区");
		
		dirLayout = new FlowViewGroup(mContext);
		FrameLayout.LayoutParams dParams = new FrameLayout.LayoutParams(WeatherConfig.getResolutionValue(1200), WeatherConfig.getResolutionValue(68));
		dParams.setMargins(WeatherConfig.getResolutionValue(422), WeatherConfig.getResolutionValue(335), 0, 0);
		dirLayout.setLayoutParams(dParams);
		
		flowGroup = new FlowViewGroup(mContext);
		FrameLayout.LayoutParams proParams = new FrameLayout.LayoutParams(
				WeatherConfig.getResolutionValue(1200), WeatherConfig.getResolutionValue(701));
		proParams.setMargins(WeatherConfig.getResolutionValue(422), WeatherConfig.getResolutionValue(434), 0, 0);
		flowGroup.setLayoutParams(proParams);
		
		specLayout = new FlowViewGroup(mContext);
		FrameLayout.LayoutParams slParams = new FrameLayout.LayoutParams(WeatherConfig.getResolutionValue(1200), WeatherConfig.getResolutionValue(68));
		slParams.setMargins(WeatherConfig.getResolutionValue(422), WeatherConfig.getResolutionValue(908), 0, 0);
		specLayout.setLayoutParams(slParams);

		main.addView(locationBg);
		main.addView(focusView);
		main.addView(dirCity);
		main.addView(province);
		main.addView(specRegion);
		main.addView(dirLayout);
		main.addView(flowGroup);
		main.addView(specLayout);
		focusView.setVisibility(View.INVISIBLE);
		this.addView(main);
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
        titleTextView.setText(R.string.city_select_title);
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

	public void update() 
	{
		Debugger.d("setting_ym", "start update");
//		locationLayout.updateView(data);
		provList = AddCityFunc.getInstance().getProvinceList();
		specRegionList = AddCityFunc.getInstance().getSpecialRegion();
		dirCityList = AddCityFunc.getInstance().getMunicipalityList();
		Debugger.d("setting_ym", "after get time-->"+System.currentTimeMillis());
		String currentCheck = null;

		if(provList != null &&specRegionList != null &&dirCityList != null)
		{
			Debugger.d("dirCitySelect-->"+
					dirCitySelect+"specSelect-->"+specSelect+"provSelect-->"+provSelect);
			addDirCityView(dirCityList);
			addProvView(dirCityList, provList);
			addSpecRegionView(dirCityList.size() + provList.size(),
					specRegionList);
			Debugger.d("after show view time-->"+System.currentTimeMillis());
			setItemSelectFocusChange(allData, currentCheck);
		}
	}
	
	public void setIAddCityLayout(IAddCityLayout lis)
	{
		this.listener = lis;
	}
	
	private void setItemSelectFocusChange(List<String> dataList, String cur2) {
		//当前位置未知，让第一个Item（北京）拿焦点
		try
		{
			dirLayout.getChildAt(0).post(new Runnable()
			{
				@Override
				public void run()
				{
					focusView.initStarPosition(dirLayout.getChildAt(0));
					dirLayout.getChildAt(0).requestFocus();
				}
			});	
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}

	public void addDirCityView(List<String> dirCityList) {

		dirLayout.removeAllViews();
		dirLayout.clearFocus();
		for (int i = 0; i < dirCityList.size(); i++) {
			SkySelectItemView dirView = new SkySelectItemView(mContext);
			LinearLayout.LayoutParams dirViewParams = new LinearLayout.LayoutParams(
					WeatherConfig.getResolutionValue(235), WeatherConfig.getResolutionValue(75));
				dirViewParams.setMargins(0, 0, 0, 0);

			dirView.setFocusChangeInf(AddCityLayout.this);
			dirView.update(dirCityList.get(i));
			dirView.setId(i);
			allData.add(dirCityList.get(i));
			dirView.setOnClickListener(dirCityListener);
			allItemList.add(dirView);
			if (dirCitySelect != -1 && i == dirCitySelect) {
				dirView.setChecked(true);
			}
			dirLayout.addView(dirView);
		}
	}
	
	OnKeyListener cityKeyListener = new OnKeyListener(){

		@Override
		public boolean onKey(View view, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == KeyEvent.ACTION_UP) {
				return true;
			}
			
			Debugger.d("setting_ym","do keyevent-->"+keyCode);
			switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
				
						dirLayout.removeAllViews();
						flowGroup.removeAllViews();
						specLayout.removeAllViews();
						focusView.setVisibility(View.GONE);
						if(dirCityList != null&&provList != null&&specRegionList != null){
							addDirCityView(dirCityList);
							addProvView(dirCityList, provList);
							addSpecRegionView(dirCityList.size() + provList.size(),
									specRegionList);
							dirCity.setText("直辖市");
							province.setText("省份");
							specRegion.setText("特区");
							flowGroup.post(new Runnable(){

								@Override
								public void run() {
									if(!flowGroup.getChildAt(curClickViewId-dirCityList.size()).hasFocus()){
										focusView.initStarPosition(flowGroup.getChildAt(curClickViewId-dirCityList.size()));
										flowGroup.getChildAt(curClickViewId-dirCityList.size()).requestFocus();
									}
								}
								
							});
						}  
						return true;
				case KeyEvent.KEYCODE_DPAD_CENTER:
					String city = cityList.get(view.getId() - CITYBASE);
					onSetUIData(city, selectProv);
					return true;
					
				}
			return false;
		}
		
	};
   
	/**
	 * 
	 */
	/**
	 * 
	 */
	OnClickListener dirCityListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// 直辖市选择，不需要弹全屏框

			checkedSelectedItem(allData, allItemList, view.getId(), 0);
			String dirCity = allData.get(view.getId());
			onSetUIData(dirCity, "直辖市");
		}

	};

	public void addProvView(List<String> dirCityList, List<String> provList) {
		int baseId = dirCityList.size();

		Debugger.d("setting_ym", "provList size-->" + provList.size());
		flowGroup.removeAllViews();
		flowGroup.clearFocus();
		for (int i = 0; i < provList.size(); i++) {
			SkySelectItemView provView = new SkySelectItemView(mContext);
			provView.setFocusChangeInf(AddCityLayout.this);
			provView.setId(baseId + i);
			provView.update(provList.get(i));
			allData.add(provList.get(i));
			provView.setOnClickListener(provListener);
			allItemList.add(provView);
			if (provSelect != -1 && i == provSelect) {
				provView.setChecked(true);
			}
			flowGroup.addView(provView);

		}

	}

	OnClickListener provListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			// 省份选择需要，弹全屏框框
//			focusX = focusViewLp.leftMargin;
//			focusY = focusViewLp.topMargin;
			focusView.setVisibility(View.INVISIBLE);
			
			int id = view.getId();
			Debugger.d("setting_ym","onClick id is-->"+id);
			curClickViewId = id;
			selectProv = allData.get(id);
			cityList = AddCityFunc.getInstance()
					.getCityList(selectProv);
			showCityView(selectProv,cityList,locationData[1]);
		}

	};
	
	public void showCityView(String currentProv,List<String> cityList,String citySelect){
	
		allCityItemList = new ArrayList<SkySelectItemView>();
		dirLayout.removeAllViews();
		flowGroup.removeAllViews();
		specLayout.removeAllViews();
		dirLayout.clearFocus();
		flowGroup.clearFocus();
		specLayout.clearFocus();
		dirCity.setText(currentProv);
		province.setText("");
		specRegion.setText("");
		int citySelectIndex = -1;
		for (int i = 0; i < cityList.size(); i++) {
			SkySelectItemView cityItemView = new SkySelectItemView(mContext);
			cityItemView.setId(CITYBASE + i);
			if (citySelect != null && !"".equals(citySelect)) {
				if (citySelect.equals(cityList.get(i))) {
					citySelectIndex = i;
				}
			}
			cityItemView.setFocusChangeInf(AddCityLayout.this);
			cityItemView.update(cityList.get(i));
			cityItemView.setOnClickListener(cityClickListener);
			cityItemView.setOnKeyListener(cityKeyListener);
			allCityItemList.add(cityItemView);
			dirLayout.addView(cityItemView);
		}  
		setItemSelectFocusChangeDetail(cityList, citySelectIndex);
		
	}
	
	OnClickListener cityClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {			
			String city = cityList.get(view.getId() - CITYBASE);
			onSetUIData(city, selectProv);
		}  

	};

	private void setItemSelectFocusChangeDetail(List<String> dataList,
			int citySelect) {
		Debugger.d("setting_ym", "setItemSelectFocusChange-->size-->"
				+ dataList.size() + "selecIndex-->" + citySelect);
		int checkIndex = -1;
		if (citySelect == -1) {
			checkIndex = 0;
		} else {
			checkIndex = citySelect;
		}
		final int checkItem = checkIndex;
		final int sCitySelect = citySelect;
		FrameLayout.LayoutParams fParams = (FrameLayout.LayoutParams)focusView.getLayoutParams();
		Debugger.d("setting_ym","left-->"+fParams.leftMargin+"##right--->"+fParams.topMargin);
		getCityFocusLocation(checkIndex);
		Debugger.d("setting_ym", "select index is --->" + checkIndex);
		dirLayout.post(new Runnable() {
			
			@Override
			public void run() {
				focusView.initStarPosition(allCityItemList.get(checkItem));
				
				if(!allCityItemList.get(checkItem).hasFocus())
					allCityItemList.get(checkItem).requestFocus();
				if(sCitySelect != -1)
					allCityItemList.get(checkItem).setChecked(true);
			}
		});


	}
	
	public void getCityFocusLocation(int citySelect){

	}
	
	public void addSpecRegionView(int base, List<String> specRegionList) {
		int baseId = base;
		specLayout.removeAllViews();
		specLayout.clearFocus();
		for (int i = 0; i < specRegionList.size(); i++) {
			SkySelectItemView specView = new SkySelectItemView(mContext);
			LinearLayout.LayoutParams specViewParams = new LinearLayout.LayoutParams(
					WeatherConfig.getResolutionValue(235), WeatherConfig.getResolutionValue(75));
			specView.setFocusChangeInf(AddCityLayout.this);
			Debugger.d("addSpecRegionView special is "
					+ specRegionList.get(i));
			specView.update(specRegionList.get(i));
			specView.setId(i + baseId);
			allData.add(specRegionList.get(i));
			specView.setOnClickListener(specRegionListener);
			allItemList.add(specView);
			if (specSelect != -1 && i == specSelect) {
				specView.setChecked(true);
			}
			specLayout.addView(specView);
		}

	}
	
	OnClickListener specRegionListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// 直辖市选择，不需要弹全屏框

			checkedSelectedItem(allData, allItemList, view.getId(), 0);
			String dirCity = allData.get(view.getId());
			onSetUIData(dirCity, "特区");
		}

	};

	public int getSelectLayout(List<String> provList, String curProvince) {
		for (int i = 0; i < provList.size(); i++) {
			if (curProvince.equals(provList.get(i))) {
				return i;
			}
		}
		return -1;
	}    
	

	private void checkedSelectedItem(List<String> allDataList,
			List<SkySelectItemView> allItems, int id, int base)// 更改SkySelectItemView为选中状态
	{
		Debugger.d("checkedSelectedItem-dataCount-->" + allDataList.size()
				+ "allItemSize" + allItems.size() + "##id#" + id);
		if (allDataList.size() == 0) {
			return;
		}
		for (int i = 0; i < allDataList.size(); i++) {
			SkySelectItemView view = allItems.get(i);
			boolean isChecked = view.getchecked();
			if (view.getId() == (id + base)) {
				if (isChecked) {
					view.setChecked(false);
				} else {
					view.setChecked(true);
				}
			} else {
				if (isChecked) {
					view.setChecked(false);
				}
			}

		}
	}

	@Override
	public void notifyFocus(View focus) {
			focusView.changeFocusPos(focus);
			if(focusView.getVisibility()!= View.VISIBLE)
				focusView.setVisibility(View.VISIBLE);
	}

	@Override
	public void notifyItemSelect(View select) {

	}

	public int getSelectIndex(List<String> dataList, String cur) {
		for (int i = 0; i < dataList.size(); i++) {
			if (cur.contains((dataList.get(i)))) {
				return i;
			}
		}
		return -1;
	}

	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		focusView.setVisibility(hasFocus ? View.VISIBLE : View.INVISIBLE);	
	}
	
	public void onSetUIData(String city, String province)
	{
		Debugger.d("onSetUIData : " + city + ", " + province);
		if(listener != null)
			listener.onCitySelect(city, province);
	}
}

class SkySelectItemView extends RelativeLayout {
	private ImageView checkedViewBg;// 和在一起就省事咯。
	private MarqueeTextView textView;
	private Context mContext;
	private boolean isItemCheck = false;
	private FocusChangeInf listener;

	public SkySelectItemView(Context context) {
		super(context);
		mContext = context;
		initViews();
	}

	public void setFocusChangeInf(FocusChangeInf listener) {
		this.listener = listener;
	}

	private void initViews() {
		this.setFocusable(true);
		this.setClickable(true);
		this.setFocusableInTouchMode(true);
		this.setOnFocusChangeListener(itemFocusChangeListener);
		RelativeLayout.LayoutParams mainLayout = new RelativeLayout.LayoutParams(WeatherConfig.getResolutionValue(235), WeatherConfig.getResolutionValue(75));
		  
		this.setLayoutParams(mainLayout);

		RelativeLayout.LayoutParams checkedViewBgParams = new RelativeLayout.LayoutParams(WeatherConfig.getResolutionValue(32), WeatherConfig.getResolutionValue(31));
		checkedViewBgParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		checkedViewBgParams.leftMargin = WeatherConfig.getResolutionValue(20);
		checkedViewBg = new ImageView(mContext);
		checkedViewBg.setId(1999);
//		checkedViewBg.setImageResource(R.drawable.location_current_icon);
		this.addView(checkedViewBg, checkedViewBgParams);

		RelativeLayout.LayoutParams checkedViewParams = new RelativeLayout.LayoutParams(WeatherConfig.getResolutionValue(22), WeatherConfig.getResolutionValue(22));
		checkedViewParams.leftMargin = WeatherConfig.getResolutionValue(23);
		checkedViewParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);

		RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textViewParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		textViewParams.leftMargin = WeatherConfig.getResolutionValue(80);

		textView = new MarqueeTextView(mContext);
		textView.setTextColor(Color.parseColor("#FF333333"));
		textView.setTextSize(WeatherConfig.getDpiValue(36));
		textView.setWidth(WeatherConfig.getResolutionValue(150));

		this.addView(textView, textViewParams);
		checkedViewBg.setVisibility(View.INVISIBLE);
		if (isItemCheck) {
			checkedViewBg.setVisibility(View.VISIBLE);
		}
	}

	OnFocusChangeListener itemFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View view, boolean hasFocus) {
			Debugger.d("itemFocusChangeListener-->" + view.getId() + "hasFocus"
							+ hasFocus);
			if (hasFocus) {
				view.setBackgroundResource(R.drawable.city_setting_item_bg_focus);
				textView.setTextColor(Color.parseColor("#FF000000"));
				listener.notifyFocus(view);

				
			} else {
				view.setBackgroundDrawable(null);
				textView.setTextColor(Color.parseColor("#FF333333"));
			}

		}
	};

	public void update(String textStr) {

		textView.setText(textStr);

	}

	public void setChecked(boolean isChecked) {

		Debugger.d("do set checked" + this.getId() + "##"
				+ isChecked);
		isItemCheck = isChecked;
		upDateRadioImage();
	}

	public boolean getchecked() {
		return isItemCheck;
	}

	public String getItemText() {
		String item = "";

		item = textView.getText().toString();

		return item;
	}

	private void upDateRadioImage() {
		if (isItemCheck) {
			checkedViewBg.setVisibility(View.VISIBLE);
		} else {
			checkedViewBg.setVisibility(View.INVISIBLE);
		}
	}

}

class FlowViewGroup extends ViewGroup {

	public FlowViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		int childCount = getChildCount();

		int x = 0;
		int y = 0;
		int row = 0;
		for (int index = 0; index < childCount; index++) {
			final View child = getChildAt(index);
			if (child.getVisibility() != View.GONE) {
				child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
				// 此处增加onlayout中的换行判断，用于计算所需的高度
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				x += width;
				y = row * height + height;
				if (x > maxWidth) {
					x = width;
					row++;
					y = row * height + height;
				}
			}
		}
		// 设置容器所需的宽度和高度

		setMeasuredDimension(maxWidth, y);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = getChildCount();
		int maxWidth = r - l;
		int x = 0;
		int y = 0;
		int row = 0;
		for (int i = 0; i < childCount; i++) {
			final View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				x += width;
				y = row * height + height;
				if (x > maxWidth) {
					x = width;
					row++;
					y = row * height + height;
				}
				child.layout(x - width, y - height, x, y);
			}
		}
	}
}
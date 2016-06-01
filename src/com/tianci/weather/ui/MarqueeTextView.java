package com.tianci.weather.ui;

import com.tianci.weather.WeatherConfig;

import android.content.Context;
import android.widget.TextView;

/**
@Date : 2014年12月17日
@Author : Zhan Yu
@Description : TODO
 */
public class MarqueeTextView extends TextView
{
	 boolean mIsFocused = false;

	public MarqueeTextView(Context context)
	{
		super(context);
		setTextSize(WeatherConfig.getDpiValue(32));
		setSingleLine(true);
        setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);
	}
	
	public void forceFocus()
	{
		mIsFocused = true;
		CharSequence tmp = getText();
		setText("");
		setText(tmp);
	}
	
	public void loseFocus()
	{
		mIsFocused = false;
		CharSequence tmp = getText();
		setText("");
		setText(tmp);
	}
	
	@Override
	public boolean isFocused()
	{
		return mIsFocused;
	}
}



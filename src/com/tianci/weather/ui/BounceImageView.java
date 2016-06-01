package com.tianci.weather.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;

public class BounceImageView extends LinearLayout {
	private ImageView imageView;
	private TextView textview;
	private Context context;

	private AnimatorSet set;
	private ObjectAnimator ivroationAnimator;
	private ObjectAnimator ivalphaAnimator;
	private ObjectAnimator ivtranslateAnimator;
	private ObjectAnimator ivscaleAnimatorX;
	private ObjectAnimator ivscaleAnimatorY;

	private ObjectAnimator tvscaleAnimatorX;
	private ObjectAnimator tvscaleAnimatorY;
	private ObjectAnimator tvalphaAnimator;

	public BounceImageView(Context context) {
		super(context);
		this.context = context;
		//setBackgroundColor(Color.parseColor("#66BFBFBF"));
		setOrientation(LinearLayout.VERTICAL);

		imageView = new ImageView(context);
		LayoutParams imageViewlayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		imageViewlayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		imageView.setBackground(context.getResources().getDrawable(
				R.drawable.weather_icon_default));
		addView(imageView, imageViewlayoutParams);

		textview = new TextView(context);
		LayoutParams textviewlayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textviewlayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		textviewlayoutParams.topMargin = WeatherConfig.getResolutionValue(180);
		textview.setText("正在加载数据···");
		textview.setTextColor(Color.rgb(255, 255, 255));
		textview.setTextSize(WeatherConfig.getDpiValue(20));
		addView(textview, textviewlayoutParams);

		start();

	}

	private void start() {

		if (set == null) {
			set = new AnimatorSet();
		}
		setVisibility(View.VISIBLE);

		ivscaleAnimatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f,
				0.3f, 1f);
		ivscaleAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
		ivscaleAnimatorX.setRepeatMode(ValueAnimator.RESTART);

		ivscaleAnimatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f,
				0.3f, 1f);
		ivscaleAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
		ivscaleAnimatorY.setRepeatMode(ValueAnimator.RESTART);

		ivtranslateAnimator = ObjectAnimator.ofFloat(imageView, "translationY",
				0, WeatherConfig.getResolutionValue(210),
				WeatherConfig.getResolutionValue(100));
		ivtranslateAnimator.setRepeatCount(ValueAnimator.INFINITE);
		ivtranslateAnimator.setRepeatMode(ValueAnimator.RESTART);
		ivtranslateAnimator.setInterpolator(new LinearInterpolator());

		ivroationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0,
				360);
		ivroationAnimator.setRepeatCount(ValueAnimator.INFINITE);
		ivroationAnimator.setRepeatMode(ValueAnimator.RESTART);

		ivalphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0.1f, 1f);
		ivalphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
		ivalphaAnimator.setRepeatMode(ValueAnimator.RESTART);

		tvscaleAnimatorX = ObjectAnimator.ofFloat(textview, "scaleX", 1f, 0.8f,
				1f);
		tvscaleAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
		tvscaleAnimatorX.setRepeatMode(ValueAnimator.RESTART);

		tvscaleAnimatorY = ObjectAnimator.ofFloat(textview, "scaleY", 1f, 0.8f,
				1f);
		tvscaleAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
		tvscaleAnimatorY.setRepeatMode(ValueAnimator.RESTART);

		tvalphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f,
				1f);
		tvalphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
		tvalphaAnimator.setRepeatMode(ValueAnimator.RESTART);

		set.playTogether(ivroationAnimator, ivalphaAnimator,
				ivtranslateAnimator, ivscaleAnimatorX, ivscaleAnimatorY,
				tvscaleAnimatorX, tvscaleAnimatorY, tvalphaAnimator);
		set.setDuration(2 * 2000).start();
	}

	public void finish() {
		if (set != null) {
			set.cancel();
			set.end();
		}
		setVisibility(View.GONE);
	}

}

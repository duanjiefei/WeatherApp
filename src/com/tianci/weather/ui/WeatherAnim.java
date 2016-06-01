package com.tianci.weather.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;

/**
 * 天气动画
 * @author zhann
 *
 */
public class WeatherAnim
{
	static final int FADE_ANIM_DURATION = 200;
	
	/** 淡入 */
	public static void fadeIn(View v)
	{
		v.animate().cancel();
		if(v.getAlpha() != 1f)
			v.animate().alpha(1f).setDuration(FADE_ANIM_DURATION).start();
	}
	
	/** 淡出 */
	public static void fadeOut(View v)
	{
		v.animate().cancel();
		if(v.getAlpha() != 0f)
			v.animate().alpha(0f).setDuration(FADE_ANIM_DURATION).start();
	}
	
	public static void shakeLeft(final View v){
		v.animate().cancel();
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, -20, 0, 0);
		mTranslateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());//在动画开始与结束的地方速率改变比较慢，在中间的时候加速
		//mTranslateAnimation.setRepeatCount(1);//重复次数
		mTranslateAnimation.setDuration(300);//时间间隔
		v.startAnimation(mTranslateAnimation);//立即开始动画效果
		Debugger.i("=====shakeLeft" );
	}
	
	public static void shakeRight(final View v){
		v.animate().cancel();
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 20, 0, 0);
		mTranslateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());//在动画开始与结束的地方速率改变比较慢，在中间的时候加速
		//mTranslateAnimation.setRepeatCount(1);//重复次数
		mTranslateAnimation.setDuration(300);//时间间隔
		v.startAnimation(mTranslateAnimation);//立即开始动画效果;
		Debugger.i("=====shakeRight" );
	}
	
	public static void slideLeft(ViewFlipper v,Context c){
		v.animate().cancel();
		Animation inAnim = AnimationUtils.loadAnimation(c,R.anim.anim_left_in);// 添加自定义in，out动画
		inAnim.setDuration(400);
		Animation outAnim = AnimationUtils.loadAnimation(c,R.anim.anim_right_out);
		outAnim.setDuration(400);
		v.setInAnimation(inAnim);
		v.setOutAnimation(outAnim);
	}
	public static void slideRight(ViewFlipper v,Context c){
		v.animate().cancel();
		Animation inAnim = AnimationUtils.loadAnimation(c,R.anim.anim_right_in);// 添加自定义in，out动画
		inAnim.setDuration(400);
		Animation outAnim = AnimationUtils.loadAnimation(c,R.anim.anim_left_out);
		outAnim.setDuration(400);
		v.setInAnimation(inAnim);
		v.setOutAnimation(outAnim);
	}
	
	/** 交替背景图片 */
	public static void switchBackground(final View v, final BitmapDrawable newBackgroundBmp)
	{ 
        v.animate().cancel();
		if(v.getAlpha() != 0f) 
			v.animate().alpha(0.5f).setDuration(FADE_ANIM_DURATION).setListener(new Animator.AnimatorListener()
			{		
				@Override
				public void onAnimationStart(Animator arg0)
				{
				}
				
				@Override
				public void onAnimationRepeat(Animator arg0)
				{
				}
				
				@Override
				public void onAnimationEnd(Animator arg0)
				{
					v.setBackground(newBackgroundBmp);
					fadeIn(v);
				}
				
				@Override
				public void onAnimationCancel(Animator arg0)
				{
				}
			}).start();
	}
}



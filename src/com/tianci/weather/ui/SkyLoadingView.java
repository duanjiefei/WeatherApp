/**
 * Copyright (C) 2012 The SkyTvOS Project
 *
 * Version     Date           Author
 * ─────────────────────────────────────
 *           2015-11-30         yellowlgx
 *
 */

package com.tianci.weather.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.tianci.weather.Debugger;
import com.tianci.weather.R;
import com.tianci.weather.WeatherConfig;

/**
 * @ClassName SkyLoadingView2
 * @Description 无资源消耗的5.0风格loadingView，支持XML和代码使用</br>
 *              系统无背景的loadingView，也就是普通的View，直接创建就能使用，大小由使用者控制，</br>
 *              其中显示时需要调用startSpin()，消失时需要stopSpin()； 详细请查看代码注释</br>
 * @author yellowlgx
 * @date 2015-11-30
 */
public class SkyLoadingView extends View
{
    /**
     * @Fields ANGLE_INTERPOLATOR 默认进度条自转动画插值器，匀速
     */
    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    /**
     * @Fields SWEEP_INTERPOLATOR 默认进度条公转动画插值器，两头慢，中间快
     */
    private static final Interpolator SWEEP_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    /**
     * @Fields angleAnimatorDuration 圆圈自转进度动画时间
     */
    private int angleAnimatorDuration;

    /**
     * @Fields sweepAnimatorDuration 圆圈公转旋转动画时间
     */
    private int sweepAnimatorDuration;

    /**
     * @Fields minSweepAngle 进度中最小角度值
     */
    private int minSweepAngle;

    /**
     * @Fields mBorderWidth 进度条宽度
     */
    private float mBorderWidth;

    /**
     * @Fields fBounds View所有在矩形区域，对应的值有left，right，top，bottom
     */
    private final RectF fBounds = new RectF();

    /**
     * @Fields mObjectAnimatorSweep 圆环公转旋转动画
     */
    private ObjectAnimator mObjectAnimatorSweep;

    /**
     * @Fields mObjectAnimatorAngle 圆环自转进度动画
     */
    private ObjectAnimator mObjectAnimatorAngle;

    /**
     * @Fields mModeAppearing TODO(write something)
     */
    private boolean mModeAppearing = true;

    /**
     * @Fields mPaint 旋转圆环背景
     */
    private Paint mPaint, circlePaint;

    /**
     * @Fields mCurrentGlobalAngleOffset 当前圆环自转进度的缺省值
     */
    private float mCurrentGlobalAngleOffset;

    /**
     * @Fields mCurrentGlobalAngle 当前圆环自转进度值
     */
    private float mCurrentGlobalAngle;

    /**
     * @Fields mCurrentSweepAngle 当前圆环公转转进度值
     */
    private float mCurrentSweepAngle;

    /**
     * @Fields mRunning 当前动画状态值，true:旋转中，false:反之
     */
    private boolean mRunning;

    /**
     * @Fields circleColor 圆圈背景颜色
     */
    private int circleColor;

    /**
     * @Fields arcColor 进度调颜色
     */
    private int arcColor;

    /**
     * @Fields radius 圆形半径，默认为宽度的一半
     */
    private float radius;

    public SkyLoadingView(Context context)
    {
        this(context, null);
    }

    public SkyLoadingView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SkyLoadingView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initValue(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        fBounds.left = mBorderWidth / 2f + .5f;
        fBounds.right = w - mBorderWidth / 2f - .5f;
        fBounds.top = mBorderWidth / 2f + .5f;
        fBounds.bottom = h - mBorderWidth / 2f - .5f;
        radius = w / 2 - mBorderWidth / 2 + .5f;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != VISIBLE)
        {
            stopAnim();
        } else
        {
            startAnim();
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        stopAnim();
        super.onDetachedFromWindow();
    }

    /**
     * @Description 初始化XML设置的初始状态值<br/>
     * @param context
     * @param attrs
     * @param defStyleAttr
     *            void
     * @date 2015-11-30
     */
    private void initValue(Context context, AttributeSet attrs, int defStyleAttr)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularLoadingView,
                defStyleAttr, 0);
        mBorderWidth = a.getDimension(R.styleable.CircularLoadingView_borderWidth, WeatherConfig.getResolutionValue(4));
        Debugger.i("mBorderWidth=="+mBorderWidth);
        /*circleColor = a.getColor(R.styleable.CircularLoadingView_circleBackgroundColor,
                Color.TRANSPARENT);*/
          circleColor = a.getColor(R.styleable.CircularLoadingView_circleBackgroundColor,
                 Color.TRANSPARENT);
          Debugger.i("circleColor=="+circleColor);

        arcColor = a.getColor(R.styleable.CircularLoadingView_circleRunningColor, getResources()
                .getColor(R.color.circlular_default_running_color));

        sweepAnimatorDuration = a.getInt(
                R.styleable.CircularLoadingView_sweepAnimationDurationMillis, getResources()
                        .getInteger(R.integer.circular_default_sweepAnimationDuration));

        angleAnimatorDuration = a.getInt(
                R.styleable.CircularLoadingView_angleAnimationDurationMillis, getResources()
                        .getInteger(R.integer.circular_default_angleAnimationDurationMillis));

        minSweepAngle = a.getInt(R.styleable.CircularLoadingView_minSweepAngle, getResources()
                .getInteger(R.integer.circular_default_miniSweepAngle));

        a.recycle();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStrokeWidth(mBorderWidth);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(arcColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mBorderWidth);

        initAnim();
    }

    /**
     * @Fields mAngleProperty 自定义自转需要变换的属性
     */
    private Property<SkyLoadingView, Float> mAngleProperty = new Property<SkyLoadingView, Float>(
            Float.class, "angle")
    {
        @Override
        public Float get(SkyLoadingView object)
        {
            return object.getCurrentGlobalAngle();
        }

        @Override
        public void set(SkyLoadingView object, Float value)
        {
            object.setCurrentGlobalAngle(value);
        };
    };

    /**
     * @Fields mSweepProperty 自定义共转需要变换的属性
     */
    private Property<SkyLoadingView, Float> mSweepProperty = new Property<SkyLoadingView, Float>(
            Float.class, "arc")
    {
        @Override
        public Float get(SkyLoadingView object)
        {
            return object.getCurrentSweepAngle();
        }

        @Override
        public void set(SkyLoadingView object, Float value)
        {
            object.setCurrentSweepAngle(value);
        };
    };

    /**
     * @Description 初始化动画<br/>
     * @date 2015-11-30
     */
    private void initAnim()
    {
        mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, mAngleProperty, 360f);
        mObjectAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
        mObjectAnimatorAngle.setDuration(angleAnimatorDuration);
        mObjectAnimatorAngle.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorAngle.setRepeatCount(ValueAnimator.INFINITE);

        mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, mSweepProperty,
                360f - minSweepAngle * 2);
        mObjectAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
        mObjectAnimatorSweep.setDuration(sweepAnimatorDuration);
        mObjectAnimatorSweep.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorSweep.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimatorSweep.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationRepeat(Animator animation)
            {
                super.onAnimationRepeat(animation);
                toggleAppearingMode();
            }
        });
    }

    /**
     * @Description 当一次动画执行结束后循环开始执行第二次时重新计算圆环自转缺省值<br/>
     * @date 2015-11-30
     */
    private void toggleAppearingMode()
    {
        mModeAppearing = !mModeAppearing;
        if (mModeAppearing)
        {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + minSweepAngle * 2) % 360;
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, circlePaint);

        float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;
        if (mModeAppearing)
        {
            sweepAngle += minSweepAngle;
        } else
        {
            startAngle = startAngle + sweepAngle;
            sweepAngle = 360 - sweepAngle - minSweepAngle;
        }
        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint);
    }

    /**
     * @Description 获取当前自转旋转角度值<br/>
     * @return float
     * @date 2015-11-30
     */
    public float getCurrentGlobalAngle()
    {
        return mCurrentGlobalAngle;
    }

    /**
     * @Description 设置当前自转角度<br/>
     *              并调用invalidate()使当前View重绘<br/>
     * @param currentGlobalAngle
     *            void
     * @date 2015-11-30
     */
    public void setCurrentGlobalAngle(float currentGlobalAngle)
    {
        mCurrentGlobalAngle = currentGlobalAngle;
        invalidate();
    }

    /**
     * @Description 设置当前公转所在弧度<br/>
     *              并调用invalidate()重绘<br/>
     * @param currentSweepAngle
     *            void
     * @date 2015-11-30
     */
    public void setCurrentSweepAngle(float currentSweepAngle)
    {
        mCurrentSweepAngle = currentSweepAngle;
        invalidate();
    }

    /**
     * @Description 获取当前公转所在弧度<br/>
     * @return float
     * @date 2015-11-30
     */
    public float getCurrentSweepAngle()
    {
        return mCurrentSweepAngle;
    }

    /**
     * @Description 获取当前动画状态<br/>
     * @return boolean
     * @date 2015-11-30
     */
    public boolean isSpinning()
    {
        return mRunning;
    }

    /**
     * @Description 开始动画<br/>
     * @date 2015-11-30
     */
    public void startAnim()
    {
        if (isSpinning())
        {
            return;
        }
        mRunning = true;
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mObjectAnimatorAngle.start();
        mObjectAnimatorSweep.start();
        invalidate();
    }

    /**
     * @Description 停止动画<br/>
     * @date 2015-11-30
     */
    public void stopAnim()
    {
        if (!isSpinning())
        {
            return;
        }
        mRunning = false;
        this.setLayerType(View.LAYER_TYPE_NONE, null);
        mObjectAnimatorAngle.cancel();
        mObjectAnimatorSweep.cancel();
        invalidate();
    }

    /**
     * @Description 设置圆圈宽度<br/>
     * @param width
     *            void
     * @date 2015-11-30
     */
    public void setStrokeWidth(int width)
    {
        mBorderWidth = width;
        mPaint.setStrokeWidth(mBorderWidth);
        circlePaint.setStrokeWidth(mBorderWidth);
    }

    public float getStokeWidth()
    {
        return mBorderWidth;
    }

    /**
     * @Description 获取进度条颜色<br/>
     * @return int
     * @date 2015-11-30
     */
    public int getArcColor()
    {
        return arcColor;
    }

    /**
     * @Description 设置进度条颜色值<br/>
     * @param arcColor
     *            void
     * @date 2015-11-30
     */
    public void setArcColor(int arcColor)
    {
        this.arcColor = arcColor;
        mPaint.setColor(this.arcColor);
    }

    /**
     * @Description 获取背景圆圈颜色<br/>
     * @return int
     * @date 2015-11-30
     */
    public int getCircleColor()
    {
        return circleColor;
    }

    /**
     * @Description 设置背景圆圈颜色<br/>
     * @param circleColor
     *            void
     * @date 2015-11-30
     */
    public void setCircleColor(int circleColor)
    {
        this.circleColor = circleColor;
        circlePaint.setColor(this.circleColor);
    }
}

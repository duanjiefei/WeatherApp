package com.tianci.weather.ui.weather.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.view.View;

import com.tianci.weather.Debugger;
import com.tianci.weather.WeatherConfig;
import com.tianci.weather.data.TCWeatherDegree;
import com.tianci.weather.ui.UIUtils;

/**
 * 
 * @author
 * @date 2016-4-25
 * @description 将曲线图 天气日期 weeekday 天气状况图标绘制在一个View中
 */
public class FiveDayDegreeView extends View {

	private float width;
	private float height;
	private static final float LEFT = 1 / 16F, TOP = 45 / 100F,
			RIGHT = 15 / 16F, BOTTOM = 90 / 100F;// 温度区域相对位置
	private TCWeatherDegree[] degrees;
	// private Paint highPaint;
	// private Paint lowPaint;
	private Paint mTextPaint;
	private Paint mPointPaint;

	private float left;
	private float right;

	private float top;
	private float bottom;

	private float xSpace;// 横坐标的间隔

	private float maxTemperature;// 温度的最大值
	private float minTemperature;// 温度的最小值

	private float x;// 定义初始的x轴原点坐标
	private float y;// 定义的y轴的的原点坐标

	private float[] xRulers;// 存储x轴的坐标数组

	private float[] distanceH;// 存储每一个最高温度值 相对于 y轴零点距离
	private float[] distanceL;// 存储每一个最高温度值 相对于 y轴零点距离
	private float textHeight;// 文字的高度；
	private Bitmap[] bitmaps;// 得到对应天气的图标
	private String[] formatDate;// 得到转化格式后的日期

	public FiveDayDegreeView(Context context) {
		super(context);
		Debugger.i("FiveDayDegreeView()");
		initPaint();
	}

	/**
	 * 初始化画笔
	 * 
	 * @Title:initPaint void
	 */
	private void initPaint() {
		// 实例化文本画笔并设置参数
		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG
				| Paint.LINEAR_TEXT_FLAG);
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setTextSize(WeatherConfig.getDpiValue(30));
		mTextPaint.setStyle(Paint.Style.STROKE);
		mTextPaint.setColor(Color.rgb(255, 255, 255));

		// highPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		// highPaint.setColor(Color.rgb(255, 255, 255));
		// highPaint.setStyle(Paint.Style.STROKE);
		//
		// lowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		// lowPaint.setColor(Color.BLUE);
		// lowPaint.setStyle(Paint.Style.STROKE);
		//
		// // 实例化点画笔并设置参数
		mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPointPaint.setStyle(Paint.Style.FILL);
		mPointPaint.setStrokeWidth(WeatherConfig.getDpiValue(30));
		mPointPaint.setColor(Color.rgb(255, 255, 255));

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (degrees != null && degrees.length != 0) {

			width = getWidth();
			height = getHeight();

			left = width * LEFT;
			right = width * RIGHT;
			top = height * TOP;
			bottom = height * BOTTOM;

			int size = degrees.length;// 温度的天数
			bitmaps = new Bitmap[size];// 初始化天气图标数组

			xSpace = (right - left) / (size - 1);// 横轴坐标的间隔；

			x = left;// 横轴的原点坐标
			xRulers = new float[size];// 初始化横轴坐标数组
			// 初始化横轴的坐标
			for (int i = 0; i < size; i++) {
				xRulers[i] = x + xSpace * i;// 为每个横轴坐标赋值
			}

			y = top + (bottom - top) / 2;// 纵轴的原点坐标

			distanceH = new float[size];// 数组：每一天高温值对应于y轴的偏移
			distanceL = new float[size];// 数组：每一天低温值对应于y轴的偏移

			// canvas.drawColor(Color.LTGRAY);// 设置画布背景的颜色；

			FontMetrics fontMetrics = mTextPaint.getFontMetrics();
			textHeight = fontMetrics.bottom - fontMetrics.top;// 得到字体画笔的高度

			// int picH = WeatherConfig.getResolutionValue(80);// 定义天气图标的高度值

			maxTemperature = getMaxMinTemperature(degrees)[0];// 获取五天内温度的最大值
			minTemperature = getMaxMinTemperature(degrees)[1];// 获取五天内温度的最小值
			distanceH = getdistanceH(degrees);
			distanceL = getdistanceL(degrees);// 根据温度的最大值和最小值为 y轴偏移数组 赋值
			bitmaps = createBitmap(degrees);// 创建位图
			formatDate = getDate(degrees);// 格式化后的日期

			// 画最高温度的变化曲线
			for (int i = 0; i < degrees.length; i++) {

				// 当前点温度值的纵坐标
				float currentY = y - distanceH[i];

				if (i + 1 < degrees.length) {
					// 下一个点的温度值的纵坐标
					float nextY = y - distanceH[i + 1];
					canvas.drawLine(xRulers[i], currentY, xRulers[i + 1],
							nextY, mTextPaint);// 画温度曲线

				}
				canvas.drawCircle(xRulers[i], currentY, 3, mPointPaint);// 画拐点
				canvas.drawText(degrees[i].maxDegree + "°", xRulers[i],
						currentY - WeatherConfig.getResolutionValue(10), mTextPaint);// h画温度值

				if (i == 0) {
					canvas.drawText("今天", xRulers[i], textHeight, mTextPaint);
				} else {
					canvas.drawText(degrees[i].weekDay, xRulers[i], textHeight,
							mTextPaint);
				}
				canvas.drawText(formatDate[i], xRulers[i], 2.0f * textHeight,
						mTextPaint);
				canvas.drawBitmap(
						bitmaps[i],
						xRulers[i] - WeatherConfig.getResolutionValue(40),
						2.0f * textHeight
								+ WeatherConfig.getResolutionValue(10), null);
			}

			// 画最低温度的变化曲线
			for (int i = 0; i < degrees.length; i++) {
				float currentY = y - distanceL[i];

				if (i + 1 < degrees.length) {
					// 下一个点的温度值的纵坐标
					float nextY = y - distanceL[i + 1];
					canvas.drawLine(xRulers[i], currentY, xRulers[i + 1],
							nextY, mTextPaint);

				}
				canvas.drawCircle(xRulers[i], currentY, 3, mPointPaint);
				canvas.drawText(degrees[i].minDegree + "°", xRulers[i],
						currentY +  WeatherConfig.getResolutionValue(30), mTextPaint);

			}
		}

	}

	/**
	 * 得到温度的最大值和最小值
	 * 
	 * @Title:getMaxMinTemperature
	 * @param lists
	 * @return float[]
	 * 
	 *         float[0] 温度的最大值 float[1] 温度的最小值
	 */
	public float[] getMaxMinTemperature(TCWeatherDegree[] degrees) {

		float Max = degrees[0].maxDegree;

		float Min = degrees[0].minDegree;

		for (int i = 1; i < degrees.length; i++) {
			if (Max < degrees[i].maxDegree) {
				float temp = degrees[i].maxDegree;
				Max = temp;

			}
		}

		for (int i = 1; i < degrees.length; i++) {

			if (Min > degrees[i].minDegree) {
				float temp = degrees[i].minDegree;
				Min = temp;
			}
		}

		return new float[] { Max, Min };

	}

	/**
	 * 得到每一天高温相对于y轴零点的距离
	 * 
	 * @Title:getdistanceH
	 * @param list
	 * @return float[]
	 */
	private float[] getdistanceH(TCWeatherDegree[] degrees) {
		float[] distanceH = new float[degrees.length];

		for (int i = 0; i < degrees.length; i++) {

			distanceH[i] = (float) (((2 * degrees[i].maxDegree - maxTemperature - minTemperature) / (maxTemperature - minTemperature)) * 0.5 * (bottom - top));
		}
		return distanceH;
	}

	/**
	 * 得到每一天低温相对于y轴零点的距离
	 * 
	 * @Title:getdistanceL
	 * @param list
	 * @return float[]
	 */
	private float[] getdistanceL(TCWeatherDegree[] degrees) {

		float[] distanceL = new float[degrees.length];

		for (int i = 0; i < degrees.length; i++) {

			distanceL[i] = (float) (((2 * degrees[i].minDegree - maxTemperature - minTemperature) / (maxTemperature - minTemperature)) * 0.5 * (bottom - top));
		}
		return distanceL;
	}

	/**
	 * 为创建的bitmaps 创建bitmap
	 * 
	 * @Title:createBitmap
	 * @param degrees
	 *            void
	 */
	private Bitmap[] createBitmap(TCWeatherDegree[] degrees) {

		Bitmap[] bitmaps = new Bitmap[degrees.length];

		for (int i = 0; i < degrees.length; i++) {

			int id = UIUtils.getWeatherIconRes(degrees[i].wEnum)[0];

			bitmaps[i] = BitmapFactory.decodeResource(getResources(), id);

		}

		return bitmaps;

	}

	/**
	 * 对日期进行处理
	 * 
	 * @Title:getDate
	 * @param degrees
	 * @return String[]
	 */
	private String[] getDate(TCWeatherDegree[] degrees) {
		String[] date = new String[degrees.length];
		if (degrees != null && degrees.length != 0) {
			for (int i = 0; i < degrees.length; i++) {
				date[i] = UIUtils.getFormatDate(degrees[i].date);
			}
		}
		return date;

	}

	/**
	 * 更新数据来源
	 * 
	 * @Title:setDegree
	 * @param degrees
	 *            void
	 */
	public void setDegree(TCWeatherDegree[] degrees) {
		/*
		 * 数据为空直接GG
		 */
		if (null == degrees || degrees.length == 0) {
			throw new IllegalArgumentException("No data to display !");
		}

		this.degrees = degrees;
		postInvalidate();
	}
}
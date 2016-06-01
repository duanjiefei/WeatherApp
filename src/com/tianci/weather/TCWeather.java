package com.tianci.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.tianci.weather.data.CachedWeather;
import com.tianci.weather.data.TCWeatherComposite;
import com.tianci.weather.data.TCWeatherInfo;
import com.tianci.weather.ui.SkyLoadingView;
import com.tianci.weather.ui.WeatherLoadingView;
import com.tianci.weather.ui.weather.WeatherLayout;

public class TCWeather
{
	public interface TCWeatherCallback
	{
		public void onWeatherUpdate(TCWeatherInfo info);
		public void onWeatherUpdateError(String city);
		public void onWeatherUpdateBefore();
	}
	
	private Context context;
	private WeatherLayout weatherLayout;
	private ExecutorService getWeatherTask;
	private UpdateWeatherHandler handler;
	private TCWeatherCallback callback;
	BlockingQueue<Runnable> taksQueue;
	private CachedWeather cachedWeather;
	//private WeatherLoadingView weatherLoadingView;
	private final int HTTP_CONNECTION_TIME_OUT = 1*5000;
	private final int MSG_HTTP_REQUEST_FAILURE = 100;
	
	public TCWeather()
	{	
		taksQueue = new LinkedBlockingDeque<Runnable>();
		getWeatherTask = new ThreadPoolExecutor(2, 11, 60, TimeUnit.SECONDS, taksQueue);
	}
/*	public TCWeather(WeatherLoadingView weatherLoadingView)
	{	
		this.weatherLoadingView = weatherLoadingView;
		taksQueue = new LinkedBlockingDeque<Runnable>();
		getWeatherTask = new ThreadPoolExecutor(2, 11, 60, TimeUnit.SECONDS, taksQueue);
	}*/
	public void shutdown()
	{
		if(getWeatherTask != null)
			getWeatherTask.shutdownNow();
	}

	public void getWeather(String city, TCWeatherCallback callback)
	{
		this.callback = callback;
		if(cachedWeather == null)
			cachedWeather = CachedWeather.getInstance(); 
		if(Looper.myLooper() != Looper.getMainLooper())
		{
			Looper.prepare();
			handler = new UpdateWeatherHandler(this, Looper.myLooper());	
			Looper.loop();
		} else
		{
			handler = new UpdateWeatherHandler(this, Looper.myLooper());	
		}
		//weatherLoadingView.setVisibility(View.VISIBLE);
	/*	weatherLoadingView.post(new Runnable() {			
			@Override
			public void run() {
				weatherLoadingView.setVisibility(View.VISIBLE);				
			}
		});*/
		getWeatherTask.submit(new GetWeatherRunnable(city), "GetWeatherRunnable : " + city);
	}
	
	private void stopHandler()
	{
		if(handler != null){
			handler.removeMessages(MSG_UPDATE_WEATHER);
			handler.removeMessages(MSG_HTTP_REQUEST_FAILURE);
		}
		handler = null;
	}
	
	class GetWeatherRunnable implements Runnable
	{
		String city;
		public GetWeatherRunnable(String city)
		{
			this.city = city;
		}
		
		@Override
		public void run()
		{
			//weatherLoadingView.setVisibility(View.VISIBLE);
			callback.onWeatherUpdateBefore();
			try
			{
				Message msgUpdate = handler.obtainMessage(MSG_UPDATE_WEATHER);
				Message msgError = handler.obtainMessage(MSG_HTTP_REQUEST_FAILURE);
				TCWeatherInfo weatherInfo = cachedWeather.getCachedWeatherInfo(city);
				if(weatherInfo != null)
				{
					Debugger.d("getWeather from cache : " + city);
					msgUpdate.obj = weatherInfo;
					handler.sendMessage(msgUpdate);
				} else
				{
					Debugger.d("before GetWeather from net : " + city);
					weatherInfo = TCWeatherComposite.transFromJson(postRequest(city));
					cachedWeather.saveWeatherInfo(weatherInfo);
					Debugger.d("after GetWeather from net : " + city);
					if(weatherInfo == null){
						Debugger.d("getWeather from net error: " + city);
						msgError.obj = city;
			        	handler.sendMessage(msgError);			        	
					}else{
						Debugger.d("getWeather from net : " + city);
						msgUpdate.obj = weatherInfo;
						handler.sendMessage(msgUpdate);
					}
				}				
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private String postRequest(String city)
	{
		HttpEntity requestHttpEntity;
		try
		{
			NameValuePair pair1 = new BasicNameValuePair("city", city);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>();
            pairList.add(pair1);   
			requestHttpEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
	        HttpPost httpPost = new HttpPost("http://sky.tvos.skysrt.com//Framework/tvos/index.php?_r=weather/weatherAction/GetWeather");
	        httpPost.setEntity(requestHttpEntity);
	        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	        HttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONNECTION_TIME_OUT);//连接超时
	        HttpConnectionParams.setSoTimeout(httpParams, HTTP_CONNECTION_TIME_OUT);//读取数据超时	        
	        HttpClient httpClient = new DefaultHttpClient(httpParams);
	        HttpResponse response = httpClient.execute(httpPost);//执行请求，并获得响应数据	        
	        //判断是否请求成功，为200时表示成功，其他均问有问题。
	        if(response.getStatusLine().getStatusCode() == 200){
	        	HttpEntity httpEntity = response.getEntity();
		        InputStream inputStream = httpEntity.getContent();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    inputStream));
	            StringBuffer sb = new StringBuffer();
	            String line = "";
	            while (null != (line = reader.readLine()))
	            {
	            	sb.append(line);
	            }
	            return sb.toString();
	        }	        
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private final int MSG_UPDATE_WEATHER = 12;
	private static class UpdateWeatherHandler extends Handler
	{
		WeakReference<TCWeather> ref;
		public UpdateWeatherHandler(TCWeather weather, Looper looper)
		{
			super(looper);
			ref = new WeakReference<TCWeather>(weather);
		}
		
		@Override
		public void handleMessage(Message msg)
		{
			if(ref == null || ref.get() == null)
				return ;
			if(msg.what == ref.get().MSG_UPDATE_WEATHER)
			{
				if(ref.get().callback != null)
					ref.get().callback.onWeatherUpdate((TCWeatherInfo) msg.obj);				
			}
			if(msg.what == ref.get().MSG_HTTP_REQUEST_FAILURE){
				ref.get().callback.onWeatherUpdateError((String) msg.obj);
			}
		}
	}
}
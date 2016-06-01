package com.tianci.weather.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	public static void sendHttpRequest(final String address,
			final HttpCallBackListener listener) {
	
				HttpURLConnection con = null;

				try {
					URL url = new URL(address);
					con = (HttpURLConnection) url.openConnection();

					con.setRequestMethod("POST");
					con.setConnectTimeout(8000);
					con.setReadTimeout(8000);

					InputStream in = con.getInputStream();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));

					StringBuilder response = new StringBuilder();

					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}

					if (listener != null) {
						listener.onFinish(response.toString());
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (con != null) {
						con.disconnect();
					}
				}
			}
		



}

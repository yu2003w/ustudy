package com.ustudy.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ScoreApiTest {

	public static void main(String[] args) {

		long t1 =  System.currentTimeMillis();
		
		recalculateScore();
		 System.out.println();
		 System.out.println("--------------------recalculateScore-------------------");
		 System.out.println();
		
		System.out.println("--------------- " + (System.currentTimeMillis() - t1));
	}

	/**
	 * 
	 * recalculateScore[單題答案修改重新计算分数接口]
	 * 创建人:  dulei
	 * 创建时间: 2017年12月11日 下午10:24:55
	 *
	 * @Title: recalculateScore
	 */
	public static void recalculateScore() {

		String targetURL = "http://127.0.0.1:8080/exam/score/recalculate/1/1/A";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

//			OutputStream outputStream = httpConnection.getOutputStream();
//			outputStream.write(parameters.getBytes());
//			outputStream.flush();

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException(
						"HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));

			String output;
			System.out.println("Output from Server:  \n");

			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}

			httpConnection.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}

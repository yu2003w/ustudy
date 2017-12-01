package com.ustudy.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientApiTest2 {

	public static void main(String[] args) {

		long t1 =  System.currentTimeMillis();
		
		 getAnswerPapers();
		 System.out.println();
		 System.out.println("--------------------getAnswerPapers-------------------");
		 System.out.println();
		
		System.out.println("--------------- " + (System.currentTimeMillis() - t1));
	}

	public static void getAnswerPapers() {

		String targetURL = "http://127.0.0.1:8080/exam/answer/papers";

		String parameters = "{\"question_id\":1,\"class_id\":1,\"type\":\"1\",\"text\":\"1\",\"viewAnswerPaper\":1}";
		System.out.println(parameters);
		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(parameters.getBytes());
			outputStream.flush();

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

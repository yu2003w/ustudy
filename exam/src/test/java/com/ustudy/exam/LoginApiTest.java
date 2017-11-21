package com.ustudy.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginApiTest {

	public static void main(String[] args) {
		
		String username = "1381139";
//		String password = "123456";
		String password = "e10adc3949ba59abbe56e057f20f883e";
		
		login(username, password);
		System.out.println();
		System.out.println("--------------------saveTemplates-------------------");
		System.out.println();
		
	}

	public static void login(String username, String password) {

		String targetURL = "http://47.92.53.57:8080/login?username=" + username + "&password=" + password;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));

			String output;
			System.out.println("Output from Server:\n");
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

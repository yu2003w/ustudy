package com.ustudy.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PaperApiTest {

    public static void main(String[] args) {

        long t1 = System.currentTimeMillis();

        updateErrorPaper();
        System.out.println();
        System.out.println("--------------------updateErrorPaper-------------------");
        System.out.println();

        System.out.println("--------------- " + (System.currentTimeMillis() - t1));
    }

    public static void updateErrorPaper() {

        String targetURL = "http://127.0.0.1:8080/exam/exam/paper";
        String papers = "{\"egsId\":1,\"examCode\":\"181033\",\"answers\":[{\"paperid\":87,\"quesno\":1,\"answer\":\"A\"},{\"paperid\":87,\"quesno\":2,\"answer\":\"B\"},{\"paperid\":87,\"quesno\":3,\"answer\":\"C\"},{\"paperid\":87,\"quesno\":4,\"answer\":\"D\"},{\"paperid\":87,\"quesno\":5,\"answer\":\"A\"},{\"paperid\":87,\"quesno\":6,\"answer\":\"B\"},{\"paperid\":87,\"quesno\":7,\"answer\":\"C\"},{\"paperid\":87,\"quesno\":8,\"answer\":\"D\"},{\"paperid\":87,\"quesno\":9,\"answer\":\"A\"}]}";

        try {

            URL restServiceURL = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(papers.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));

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

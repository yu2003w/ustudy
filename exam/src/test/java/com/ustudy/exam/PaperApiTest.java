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
        String papers = "{\"egsId\":1,\"examCode\":\"181250\",\"paperid\":28,\"answers\":[{\"quesno\":1,\"answer\":\"A\"},{\"quesno\":2,\"answer\":\"B\"},{\"quesno\":3,\"answer\":\"C\"},{\"quesno\":4,\"answer\":\"D\"},{\"quesno\":5,\"answer\":\"A\"},{\"quesno\":6,\"answer\":\"B\"},{\"quesno\":7,\"answer\":\"C\"},{\"quesno\":8,\"answer\":\"D\"},{\"quesno\":9,\"answer\":\"A\"},{\"quesno\":10,\"answer\":\"A\"},{\"quesno\":11,\"answer\":\"A\"},{\"quesno\":12,\"answer\":\"B\"},{\"quesno\":13,\"answer\":\"C\"},{\"quesno\":14,\"answer\":\"D\"},{\"quesno\":15,\"answer\":\"A\"},{\"quesno\":16,\"answer\":\"B\"},{\"quesno\":17,\"answer\":\"C\"},{\"quesno\":18,\"answer\":\"D\"},{\"quesno\":19,\"answer\":\"A\"},{\"quesno\":20,\"answer\":\"A\"},{\"quesno\":21,\"answer\":\"A\"},{\"quesno\":22,\"answer\":\"B\"},{\"quesno\":23,\"answer\":\"C\"},{\"quesno\":24,\"answer\":\"D\"},{\"quesno\":25,\"answer\":\"A\"},{\"quesno\":26,\"answer\":\"B\"},{\"quesno\":27,\"answer\":\"C\"},{\"quesno\":28,\"answer\":\"D\"},{\"quesno\":29,\"answer\":\"A\"},{\"quesno\":30,\"answer\":\"A\"}]}";

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

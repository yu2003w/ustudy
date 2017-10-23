package com.ustudy.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.ustudy.exam.utility.Base64Util;

public class ClientApiTest {

	public static void main(String[] args) {
		
		String token = Base64Util.decode("1381139:123456");
		
		saveTemplates(token);
		System.out.println();
		System.out.println("--------------------saveTemplates-------------------");
		System.out.println();
		
//		getExamTemplate("123", "456", "789", token);
//		System.out.println();
//		System.out.println("--------------------getExamTemplate-------------------");
//		System.out.println();
//		
//		getExamSubjects("1","1", token);
//		System.out.println();
//		System.out.println("--------------------getExamSubjects-------------------");
//		System.out.println();
//		
//		getExamGrades("1", "0", token);
//		System.out.println();
//		System.out.println("--------------------getExamGrades-------------------");
//		System.out.println();
//		
//		getExams("0", token);
//		System.out.println();
//		System.out.println("--------------------getExams-------------------");
//		System.out.println();
//		
//		login(token);
//		System.out.println();
//		System.out.println("--------------------login-------------------");
//		System.out.println();
//		
//		getPermissions(token);
//		System.out.println();
//		System.out.println("--------------------getPermissions-------------------");
//		System.out.println();
//		
//		update("ustudy");
//		System.out.println();
//		System.out.println("--------------------update-------------------");
//		System.out.println();
//		
//		saveAnswerPaper(token, "1", "fileName");
//		System.out.println();
//		System.out.println("--------------------saveAnswerPaper-------------------");
//		System.out.println();
//		
//		saveQuestionsPaper(token, "1", "fileName");
//		System.out.println();
//		System.out.println("--------------------saveQuestionsPaper-------------------");
//		System.out.println();
	}

	public static void saveTemplates(String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/saveExamTemplate/1";

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			String input = "{\"token\":\""+token+"\",\"data\":{\"isDoubleSide\":1,\"imgSourceType\":0,\"Page\":[{\"pageIndex\":1,\"omrType\":1,\"Size\":0,\"fileName\":\"asdfasdfasdf\",\"localRegion\":{\"matchRegion\":{\"x\":0,\"y\":0,\"width\":100,\"height\":100,\"bottom\":100,\"right\":100},\"TitlePoints\":[{\"X\":0,\"Y\":0,\"size\":{\"Height\":100,\"Width\":100}},{\"X\":20,\"Y\":20,\"size\":{\"Height\":100,\"Width\":100}}]},\"SchoolNumType\":2,\"OmrSchoolNumBlob\":{\"x\":25,\"y\":56,\"width\":200,\"height\":50,\"bottom\":106,\"right\":225},\"omrs\":[[{\"x\":0,\"y\":0,\"width\":0,\"height\":0,\"bottom\":0,\"right\":0},{\"x\":0,\"y\":0,\"width\":0,\"height\":0,\"bottom\":0,\"right\":0}],[{\"x\":0,\"y\":0,\"width\":0,\"height\":0,\"bottom\":0,\"right\":0},{\"x\":10,\"y\":10,\"width\":10,\"height\":10,\"bottom\":10,\"right\":10}]],\"SchoolNumberPoints\":[{\"X\":12,\"Y\":56,\"size\":{\"Height\":52,\"Width\":89}},{\"X\":23,\"Y\":47,\"size\":{\"Height\":80,\"Width\":99}}],\"OmrSchoolNumberString\":123456,\"QRSchoolNumBlob\":{\"Type\":1,\"region\":{\"x\":0,\"y\":0,\"width\":0,\"height\":0,\"bottom\":0,\"right\":0},\"SchoolNumberPoints\":[{\"X\":0,\"Y\":0,\"size\":{\"Height\":0,\"Width\":0}},{\"X\":0,\"Y\":0,\"size\":{\"Height\":0,\"Width\":0}}]},\"BarCodeSchoolNumBlob\":{\"Type\":1,\"isHorizontal\":true,\"region\":{\"x\":1,\"y\":2,\"width\":3,\"height\":4,\"bottom\":5,\"right\":6}},\"OmrObjectives\":{\"topicType\":1,\"region\":{\"x\":7,\"y\":8,\"width\":9,\"height\":11,\"bottom\":12,\"right\":13},\"objectiveItems\":[{\"arrange\":2,\"num\":{\"number\":1,\"pos\":{\"X\":2,\"Y\":3,\"size\":{\"Height\":5,\"Width\":6}}},\"ItemRects\":[{\"x\":2,\"y\":4,\"width\":6,\"height\":8,\"bottom\":6,\"right\":2},{\"x\":2,\"y\":5,\"width\":7,\"height\":9,\"bottom\":11,\"right\":13}],\"OcrDoubt\":true},{\"arrange\":1,\"num\":{\"number\":12,\"pos\":{\"X\":45,\"Y\":85,\"size\":{\"Height\":269,\"Width\":378}}},\"ItemRects\":[{\"x\":125,\"y\":256,\"width\":325,\"height\":756,\"bottom\":245,\"right\":154},{\"x\":154,\"y\":454,\"width\":12,\"height\":458,\"bottom\":154,\"right\":265}],\"OcrDoubt\":false}],\"ItemBlobSort\":1,\"OcrSort\":2,\"ItemDistance\":3,\"ItemBlobDistance\":4,\"OcrBlobDistance\":6,\"originnumList\":[{\"key\":\"a\",\"value\":1},{\"key\":\"b\",\"value\":2}],\"originblobList\":[{\"x\":0,\"y\":10,\"width\":20,\"height\":30,\"bottom\":40,\"right\":50},{\"x\":602,\"y\":70,\"width\":80,\"height\":90,\"bottom\":100,\"right\":110}],\"numList\":[{\"key\":\"c\",\"value\":4},{\"key\":\"h\",\"value\":9}],\"blobList\":[{\"x\":91,\"y\":92,\"width\":93,\"height\":94,\"bottom\":95,\"right\":96},{\"x\":97,\"y\":98,\"width\":99,\"height\":100,\"bottom\":101,\"right\":102}],\"SchoolNumberPoints\":[{\"X\":125,\"Y\":169,\"size\":{\"Height\":120,\"Width\":179}},{\"X\":256,\"Y\":369,\"size\":{\"Height\":520,\"Width\":474}}],\"OmrObjectiveString\":\"1212fg\"},\"OmrSubjectiveList\":[{\"AreaID\":5,\"regionList\":[{\"x\":0,\"y\":0,\"width\":100,\"height\":100,\"bottom\":100,\"right\":100},{\"x\":20,\"y\":20,\"width\":200,\"height\":200,\"bottom\":200,\"right\":220}],\"TopicType\":2,\"StartQid\":30,\"EndQid\":40}],\"HideAreaList\":[{\"AreaID\":2,\"IsSchoolNum\":true,\"HideAreaRect\":{\"x\":0,\"y\":10,\"width\":25,\"height\":56,\"bottom\":45,\"right\":58},\"TopicType\":2}]}]}}";

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

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

	public static void getExamTemplate(String examId, String gradeId, String subjectId, String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getExamTemplate/" + examId + "/" + gradeId + "/" + subjectId;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(token.getBytes());
			outputStream.flush();
			
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

	public static void getExamSubjects(String examId, String gradeId, String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getExamSubjects/" + examId + "/" + gradeId;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(token.getBytes());
			outputStream.flush();

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

	public static void getExamGrades(String examId, String examStatus, String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getExamGrades/" + examId + "/" + examStatus;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(token.getBytes());
			outputStream.flush();

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

	public static void getExams(String examStatus, String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getExams/" + examStatus;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(token.getBytes());
			outputStream.flush();

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

	public static void getPermissions(String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getPermissions";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(token.getBytes());
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

	public static void login(String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/login";

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(token.getBytes());
			outputStream.flush();

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

	public static void update(String currentVersionNo) {

		String targetURL = "http://127.0.0.1:8080/exam/client/update?currentVersionNo=" + currentVersionNo;

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
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
	
	public static void saveAnswerPaper(String token, String id, String fileName) {

		String targetURL = "http://127.0.0.1:8080/exam/client//save/answerPaper";
		
		String parameters = "{\"token\":\""+token+"\",\"data\":{\"id\":\""+id+"\",\"fileName\":\""+fileName+"\"}}";
		System.out.println(parameters);
		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
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
	
	public static void saveQuestionsPaper(String token, String id, String fileName) {

		String targetURL = "http://127.0.0.1:8080/exam/client//save/questionsPaper";
		
		String parameters = "{\"token\":\""+token+"\",\"data\":{\"id\":\""+id+"\",\"fileName\":\""+fileName+"\"}}";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
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

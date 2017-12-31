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

		String username = "13910001137";
		String password = "123456";
		password = Base64Util.getMd5Pwd(password);
		String token = Base64Util.decode(username + ":" + password);
		
		long t1 =  System.currentTimeMillis();
		
//		 getQuestionType(token);
//		 System.out.println();
//		 System.out.println("--------------------getQuestionType-------------------");
//		 System.out.println();

		 saveTemplates(token);
		 System.out.println();
		 System.out.println("--------------------saveTemplates-------------------");
		 System.out.println();
		
//		 getExamTemplate("1", "1", "3", token);
//		 System.out.println();
//		 System.out.println("--------------------getExamTemplate-------------------");
//		 System.out.println();
		
//		 getExamTemplate("9", token);
//		 System.out.println();
//		 System.out.println("--------------------getExamTemplate-------------------");
//		 System.out.println();
		 
//		 getExamSubjects("1", "1", token);
//		 System.out.println();
//		 System.out.println("--------------------getExamSubjects-------------------");
//		 System.out.println();
		
//		 getExamGrades("1", "0", token);
//		 System.out.println();
//		 System.out.println("--------------------getExamGrades-------------------");
//		 System.out.println();
		
//		 getExams("0", token);
//		 System.out.println();
//		 System.out.println("--------------------getExams-------------------");
//		 System.out.println();
		
//		 login(token);
//		 System.out.println();
//		 System.out.println("--------------------login-------------------");
//		 System.out.println();
		
//		 getPermissions(token);
//		 System.out.println();
//		 System.out.println("--------------------getPermissions-------------------");
//		 System.out.println();
		
//		 update("ustudy");
//		 System.out.println();
//		 System.out.println("--------------------update-------------------");
//		 System.out.println();
		
//		 saveAnswerPaper(token, "1", "fileName");
//		 System.out.println();
//		 System.out.println("--------------------saveAnswerPaper-------------------");
//		 System.out.println();
		
//		 saveQuestionsPaper(token, "1", "fileName");
//		 System.out.println();
//		 System.out.println("--------------------saveQuestionsPaper-------------------");
//		 System.out.println();
		
//		 saveStudentsAnswers(token, 1, 1);
//		 System.out.println();
//		 System.out.println("--------------------saveStudentsAnswers-------------------");
//		 System.out.println();
		
//		 deleteStudentsPapers(token, 3, 2);
//		 System.out.println();
//		 System.out.println("--------------------deleteStudentsPapers-------------------");
//		 System.out.println();
		
//		 getExamTemplate(1, token);
//		 System.out.println();
//		 System.out.println("--------------------getExamTemplate-------------------");
//		 System.out.println();
		
//		 getStudentsinfo(token, 1, 1);
//		 System.out.println();
//		 System.out.println("--------------------getStudentsinfo-------------------");
//		 System.out.println();
		
//		 getStudentsinfo(1);
//		 System.out.println();
//		 System.out.println("--------------------getStudentsinfo-------------------");
//		 System.out.println();

//		 getStudentPapers(token, 1);
//		 System.out.println();
//		 System.out.println("--------------------getStudentPapers-------------------");
//		 System.out.println();
		
//		getExamSubjectStatus(token, 1L, "1", 1, "1");
//		System.out.println();
//		System.out.println("--------------------getExamSubjectStatus-------------------");
//		System.out.println();
		
//		StringBuffer logs = new StringBuffer();
//		for (int i = 0; i < 1000; i++) {
//			logs.append("--------------------addClientLogs-------------------\n");
//		}
//		for (int i = 0; i < 1; i++) {
//			addClientLogs(logs.toString());
//		}
//		System.out.println();
//		System.out.println("--------------------addClientLogs-------------------");
//		System.out.println();
		
//		getClientLogs();
//		System.out.println();
//		System.out.println("--------------------getClientLogs-------------------");
//		System.out.println();
		
//		deleteClientLogs();
//		System.out.println();
//		System.out.println("--------------------deleteClientLogs-------------------");
//		System.out.println();
		
		System.out.println("--------------- " + (System.currentTimeMillis() - t1));
	}

	public static void saveTemplates(String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/saveExamTemplate/10";

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

			String input = "{\"CSID\":10,\"AnswerSheetXmlPath\":\"tpl10.xml\",\"TemplateInfo\":{\"isDoubleSide\":true,\"imgSourceType\":0,\"pages\":[{\"pageIndex\":0,\"omrType\":0,\"Size\":0,\"fileName\":\"tpl10.0.png\",\"localRegion\":{\"matchRegion\":{\"x\":797,\"y\":194,\"width\":152,\"height\":37,\"bottom\":231,\"right\":949},\"TitlePoints\":[\"797, 194\",\"949, 194\",\"949, 231\",\"797, 231\",\"797, 194\"]},\"SchoolNumType\":2,\"OmrSchoolNumBlob\":null,\"QRSchoolNumBlob\":null,\"BarCodeSchoolNumBlob\":{\"Type\":0,\"isHorizontal\":true,\"region\":{\"x\":1021,\"y\":12,\"width\":631,\"height\":266,\"bottom\":278,\"right\":1652}},\"OmrObjectives\":[{\"topicType\":0,\"region\":{\"x\":276,\"y\":725,\"width\":967,\"height\":382,\"bottom\":1107,\"right\":1243},\"objectiveItems\":[{\"arrange\":0,\"num\":{\"number\":1,\"pos\":\"307, 741\"},\"ItemRects\":[{\"x\":332,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":362},{\"x\":372,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":402},{\"x\":412,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":442},{\"x\":452,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":2,\"pos\":\"307, 773\"},\"ItemRects\":[{\"x\":332,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":362},{\"x\":372,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":402},{\"x\":412,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":442},{\"x\":452,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":3,\"pos\":\"307, 805\"},\"ItemRects\":[{\"x\":332,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":362},{\"x\":372,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":402},{\"x\":412,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":442},{\"x\":452,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":4,\"pos\":\"307, 837\"},\"ItemRects\":[{\"x\":332,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":362},{\"x\":372,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":402},{\"x\":412,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":442},{\"x\":452,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":5,\"pos\":\"307, 869\"},\"ItemRects\":[{\"x\":332,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":362},{\"x\":372,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":402},{\"x\":412,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":442},{\"x\":452,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":6,\"pos\":\"589, 741\"},\"ItemRects\":[{\"x\":614,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":644},{\"x\":654,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":684},{\"x\":694,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":724},{\"x\":734,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":764}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":7,\"pos\":\"589, 773\"},\"ItemRects\":[{\"x\":614,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":644},{\"x\":654,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":684},{\"x\":694,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":724},{\"x\":734,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":764}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":8,\"pos\":\"589, 805\"},\"ItemRects\":[{\"x\":614,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":644},{\"x\":654,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":684},{\"x\":694,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":724},{\"x\":734,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":764}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":9,\"pos\":\"589, 837\"},\"ItemRects\":[{\"x\":614,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":644},{\"x\":654,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":684},{\"x\":694,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":724},{\"x\":734,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":764}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":10,\"pos\":\"574, 869\"},\"ItemRects\":[{\"x\":614,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":644},{\"x\":654,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":684},{\"x\":694,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":724},{\"x\":734,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":764}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":11,\"pos\":\"882, 742\"},\"ItemRects\":[{\"x\":922,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":952},{\"x\":962,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":992},{\"x\":1002,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":1032},{\"x\":1042,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":1072}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":12,\"pos\":\"882, 774\"},\"ItemRects\":[{\"x\":922,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":952},{\"x\":962,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":992},{\"x\":1002,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":1032},{\"x\":1042,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":1072}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":13,\"pos\":\"882, 806\"},\"ItemRects\":[{\"x\":922,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":952},{\"x\":962,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":992},{\"x\":1002,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":1032},{\"x\":1042,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":1072}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":14,\"pos\":\"882, 838\"},\"ItemRects\":[{\"x\":922,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":952},{\"x\":962,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":992},{\"x\":1002,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":1032},{\"x\":1042,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":1072}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":15,\"pos\":\"882, 870\"},\"ItemRects\":[{\"x\":922,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":952},{\"x\":962,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":992},{\"x\":1002,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":1032},{\"x\":1042,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":1072}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":16,\"pos\":\"292, 930\"},\"ItemRects\":[{\"x\":332,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":362},{\"x\":372,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":402},{\"x\":412,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":442},{\"x\":452,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":17,\"pos\":\"292, 962\"},\"ItemRects\":[{\"x\":332,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":362},{\"x\":372,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":402},{\"x\":412,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":442},{\"x\":452,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":18,\"pos\":\"292, 994\"},\"ItemRects\":[{\"x\":332,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":362},{\"x\":372,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":402},{\"x\":412,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":442},{\"x\":452,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":19,\"pos\":\"292, 1026\"},\"ItemRects\":[{\"x\":332,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":362},{\"x\":372,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":402},{\"x\":412,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":442},{\"x\":452,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":20,\"pos\":\"292, 1058\"},\"ItemRects\":[{\"x\":332,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":362},{\"x\":372,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":402},{\"x\":412,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":442},{\"x\":452,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":482}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":21,\"pos\":\"527, 930\"},\"ItemRects\":[{\"x\":567,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":597},{\"x\":607,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":637},{\"x\":648,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":678},{\"x\":688,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":718}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":22,\"pos\":\"527, 962\"},\"ItemRects\":[{\"x\":567,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":597},{\"x\":607,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":637},{\"x\":648,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":678},{\"x\":688,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":718}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":23,\"pos\":\"527, 994\"},\"ItemRects\":[{\"x\":567,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":597},{\"x\":607,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":637},{\"x\":648,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":678},{\"x\":688,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":718}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":24,\"pos\":\"527, 1026\"},\"ItemRects\":[{\"x\":567,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":597},{\"x\":607,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":637},{\"x\":648,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":678},{\"x\":688,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":718}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":25,\"pos\":\"527, 1058\"},\"ItemRects\":[{\"x\":567,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":597},{\"x\":607,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":637},{\"x\":648,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":678},{\"x\":688,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":718}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":26,\"pos\":\"762, 935\"},\"ItemRects\":[{\"x\":802,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":832},{\"x\":842,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":872},{\"x\":882,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":912},{\"x\":922,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":952}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":27,\"pos\":\"762, 965\"},\"ItemRects\":[{\"x\":802,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":832},{\"x\":842,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":872},{\"x\":882,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":912},{\"x\":922,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":952}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":28,\"pos\":\"762, 996\"},\"ItemRects\":[{\"x\":802,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":832},{\"x\":842,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":872},{\"x\":882,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":912},{\"x\":922,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":952}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":29,\"pos\":\"762, 1026\"},\"ItemRects\":[{\"x\":802,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":832},{\"x\":842,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":872},{\"x\":882,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":912},{\"x\":922,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":952}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":30,\"pos\":\"762, 1056\"},\"ItemRects\":[{\"x\":802,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":832},{\"x\":842,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":872},{\"x\":882,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":912},{\"x\":922,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":952}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":31,\"pos\":\"999, 935\"},\"ItemRects\":[{\"x\":1039,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1069},{\"x\":1079,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1109},{\"x\":1119,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1149},{\"x\":1159,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1189}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":32,\"pos\":\"999, 965\"},\"ItemRects\":[{\"x\":1039,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1069},{\"x\":1079,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1109},{\"x\":1119,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1149},{\"x\":1159,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1189}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":33,\"pos\":\"999, 996\"},\"ItemRects\":[{\"x\":1039,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1069},{\"x\":1079,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1109},{\"x\":1119,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1149},{\"x\":1159,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1189}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":34,\"pos\":\"999, 1026\"},\"ItemRects\":[{\"x\":1039,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1069},{\"x\":1079,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1109},{\"x\":1119,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1149},{\"x\":1159,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1189}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":35,\"pos\":\"999, 1056\"},\"ItemRects\":[{\"x\":1039,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1069},{\"x\":1079,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1109},{\"x\":1119,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1149},{\"x\":1159,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1189}],\"OcrDoubt\":false}],\"ItemBlobSort\":0,\"OcrSort\":0,\"ItemDistance\":0,\"ItemBlobDistance\":0,\"OcrBlobDistance\":0,\"originnumList\":null,\"originblobList\":null,\"numList\":[{\"Key\":1,\"Value\":\"307, 741\"},{\"Key\":2,\"Value\":\"307, 773\"},{\"Key\":3,\"Value\":\"307, 805\"},{\"Key\":4,\"Value\":\"307, 837\"},{\"Key\":5,\"Value\":\"307, 869\"},{\"Key\":6,\"Value\":\"589, 741\"},{\"Key\":7,\"Value\":\"589, 773\"},{\"Key\":8,\"Value\":\"589, 805\"},{\"Key\":9,\"Value\":\"589, 837\"},{\"Key\":10,\"Value\":\"574, 869\"},{\"Key\":11,\"Value\":\"882, 742\"},{\"Key\":12,\"Value\":\"882, 774\"},{\"Key\":13,\"Value\":\"882, 806\"},{\"Key\":14,\"Value\":\"882, 838\"},{\"Key\":15,\"Value\":\"882, 870\"},{\"Key\":16,\"Value\":\"292, 930\"},{\"Key\":17,\"Value\":\"292, 962\"},{\"Key\":18,\"Value\":\"292, 994\"},{\"Key\":19,\"Value\":\"292, 1026\"},{\"Key\":20,\"Value\":\"292, 1058\"},{\"Key\":21,\"Value\":\"527, 930\"},{\"Key\":22,\"Value\":\"527, 962\"},{\"Key\":23,\"Value\":\"527, 994\"},{\"Key\":24,\"Value\":\"527, 1026\"},{\"Key\":25,\"Value\":\"527, 1058\"},{\"Key\":26,\"Value\":\"762, 935\"},{\"Key\":27,\"Value\":\"762, 965\"},{\"Key\":28,\"Value\":\"762, 996\"},{\"Key\":29,\"Value\":\"762, 1026\"},{\"Key\":30,\"Value\":\"762, 1056\"},{\"Key\":31,\"Value\":\"999, 935\"},{\"Key\":32,\"Value\":\"999, 965\"},{\"Key\":33,\"Value\":\"999, 996\"},{\"Key\":34,\"Value\":\"999, 1026\"},{\"Key\":35,\"Value\":\"999, 1056\"}],\"blobList\":[{\"x\":332,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":362},{\"x\":372,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":402},{\"x\":412,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":442},{\"x\":452,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":482},{\"x\":332,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":362},{\"x\":372,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":402},{\"x\":412,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":442},{\"x\":452,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":482},{\"x\":332,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":362},{\"x\":372,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":402},{\"x\":412,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":442},{\"x\":452,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":482},{\"x\":332,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":362},{\"x\":372,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":402},{\"x\":412,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":442},{\"x\":452,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":482},{\"x\":332,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":362},{\"x\":372,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":402},{\"x\":412,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":442},{\"x\":452,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":482},{\"x\":614,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":644},{\"x\":654,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":684},{\"x\":694,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":724},{\"x\":734,\"y\":741,\"width\":30,\"height\":22,\"bottom\":763,\"right\":764},{\"x\":614,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":644},{\"x\":654,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":684},{\"x\":694,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":724},{\"x\":734,\"y\":773,\"width\":30,\"height\":22,\"bottom\":795,\"right\":764},{\"x\":614,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":644},{\"x\":654,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":684},{\"x\":694,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":724},{\"x\":734,\"y\":805,\"width\":30,\"height\":22,\"bottom\":827,\"right\":764},{\"x\":614,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":644},{\"x\":654,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":684},{\"x\":694,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":724},{\"x\":734,\"y\":837,\"width\":30,\"height\":22,\"bottom\":859,\"right\":764},{\"x\":614,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":644},{\"x\":654,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":684},{\"x\":694,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":724},{\"x\":734,\"y\":869,\"width\":30,\"height\":22,\"bottom\":891,\"right\":764},{\"x\":922,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":952},{\"x\":962,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":992},{\"x\":1002,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":1032},{\"x\":1042,\"y\":742,\"width\":30,\"height\":22,\"bottom\":764,\"right\":1072},{\"x\":922,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":952},{\"x\":962,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":992},{\"x\":1002,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":1032},{\"x\":1042,\"y\":774,\"width\":30,\"height\":22,\"bottom\":796,\"right\":1072},{\"x\":922,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":952},{\"x\":962,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":992},{\"x\":1002,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":1032},{\"x\":1042,\"y\":806,\"width\":30,\"height\":22,\"bottom\":828,\"right\":1072},{\"x\":922,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":952},{\"x\":962,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":992},{\"x\":1002,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":1032},{\"x\":1042,\"y\":838,\"width\":30,\"height\":22,\"bottom\":860,\"right\":1072},{\"x\":922,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":952},{\"x\":962,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":992},{\"x\":1002,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":1032},{\"x\":1042,\"y\":870,\"width\":30,\"height\":22,\"bottom\":892,\"right\":1072},{\"x\":332,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":362},{\"x\":372,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":402},{\"x\":412,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":442},{\"x\":452,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":482},{\"x\":332,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":362},{\"x\":372,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":402},{\"x\":412,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":442},{\"x\":452,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":482},{\"x\":332,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":362},{\"x\":372,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":402},{\"x\":412,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":442},{\"x\":452,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":482},{\"x\":332,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":362},{\"x\":372,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":402},{\"x\":412,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":442},{\"x\":452,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":482},{\"x\":332,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":362},{\"x\":372,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":402},{\"x\":412,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":442},{\"x\":452,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":482},{\"x\":567,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":597},{\"x\":607,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":637},{\"x\":648,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":678},{\"x\":688,\"y\":930,\"width\":30,\"height\":22,\"bottom\":952,\"right\":718},{\"x\":567,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":597},{\"x\":607,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":637},{\"x\":648,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":678},{\"x\":688,\"y\":962,\"width\":30,\"height\":22,\"bottom\":984,\"right\":718},{\"x\":567,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":597},{\"x\":607,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":637},{\"x\":648,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":678},{\"x\":688,\"y\":994,\"width\":30,\"height\":22,\"bottom\":1016,\"right\":718},{\"x\":567,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":597},{\"x\":607,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":637},{\"x\":648,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":678},{\"x\":688,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":718},{\"x\":567,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":597},{\"x\":607,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":637},{\"x\":648,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":678},{\"x\":688,\"y\":1058,\"width\":30,\"height\":22,\"bottom\":1080,\"right\":718},{\"x\":802,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":832},{\"x\":842,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":872},{\"x\":882,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":912},{\"x\":922,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":952},{\"x\":802,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":832},{\"x\":842,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":872},{\"x\":882,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":912},{\"x\":922,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":952},{\"x\":802,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":832},{\"x\":842,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":872},{\"x\":882,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":912},{\"x\":922,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":952},{\"x\":802,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":832},{\"x\":842,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":872},{\"x\":882,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":912},{\"x\":922,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":952},{\"x\":802,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":832},{\"x\":842,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":872},{\"x\":882,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":912},{\"x\":922,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":952},{\"x\":1039,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1069},{\"x\":1079,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1109},{\"x\":1119,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1149},{\"x\":1159,\"y\":935,\"width\":30,\"height\":22,\"bottom\":957,\"right\":1189},{\"x\":1039,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1069},{\"x\":1079,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1109},{\"x\":1119,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1149},{\"x\":1159,\"y\":965,\"width\":30,\"height\":22,\"bottom\":987,\"right\":1189},{\"x\":1039,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1069},{\"x\":1079,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1109},{\"x\":1119,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1149},{\"x\":1159,\"y\":996,\"width\":30,\"height\":22,\"bottom\":1018,\"right\":1189},{\"x\":1039,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1069},{\"x\":1079,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1109},{\"x\":1119,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1149},{\"x\":1159,\"y\":1026,\"width\":30,\"height\":22,\"bottom\":1048,\"right\":1189},{\"x\":1039,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1069},{\"x\":1079,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1109},{\"x\":1119,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1149},{\"x\":1159,\"y\":1056,\"width\":30,\"height\":22,\"bottom\":1078,\"right\":1189}],\"SchoolNumberPoints\":[\"276, 725\",\"1243, 725\",\"1243, 1107\",\"276, 1107\",\"276, 725\"],\"OmrObjectiveString\":\"[332,741,30,22;372,741,30,22;412,741,30,22;452,741,30,22;][332,773,30,22;372,773,30,22;412,773,30,22;452,773,30,22;][332,805,30,22;372,805,30,22;412,805,30,22;452,805,30,22;][332,837,30,22;372,837,30,22;412,837,30,22;452,837,30,22;][332,869,30,22;372,869,30,22;412,869,30,22;452,869,30,22;][614,741,30,22;654,741,30,22;694,741,30,22;734,741,30,22;][614,773,30,22;654,773,30,22;694,773,30,22;734,773,30,22;][614,805,30,22;654,805,30,22;694,805,30,22;734,805,30,22;][614,837,30,22;654,837,30,22;694,837,30,22;734,837,30,22;][614,869,30,22;654,869,30,22;694,869,30,22;734,869,30,22;][922,742,30,22;962,742,30,22;1002,742,30,22;1042,742,30,22;][922,774,30,22;962,774,30,22;1002,774,30,22;1042,774,30,22;][922,806,30,22;962,806,30,22;1002,806,30,22;1042,806,30,22;][922,838,30,22;962,838,30,22;1002,838,30,22;1042,838,30,22;][922,870,30,22;962,870,30,22;1002,870,30,22;1042,870,30,22;][332,930,30,22;372,930,30,22;412,930,30,22;452,930,30,22;][332,962,30,22;372,962,30,22;412,962,30,22;452,962,30,22;][332,994,30,22;372,994,30,22;412,994,30,22;452,994,30,22;][332,1026,30,22;372,1026,30,22;412,1026,30,22;452,1026,30,22;][332,1058,30,22;372,1058,30,22;412,1058,30,22;452,1058,30,22;][567,930,30,22;607,930,30,22;648,930,30,22;688,930,30,22;][567,962,30,22;607,962,30,22;648,962,30,22;688,962,30,22;][567,994,30,22;607,994,30,22;648,994,30,22;688,994,30,22;][567,1026,30,22;607,1026,30,22;648,1026,30,22;688,1026,30,22;][567,1058,30,22;607,1058,30,22;648,1058,30,22;688,1058,30,22;][802,935,30,22;842,935,30,22;882,935,30,22;922,935,30,22;][802,965,30,22;842,965,30,22;882,965,30,22;922,965,30,22;][802,996,30,22;842,996,30,22;882,996,30,22;922,996,30,22;][802,1026,30,22;842,1026,30,22;882,1026,30,22;922,1026,30,22;][802,1056,30,22;842,1056,30,22;882,1056,30,22;922,1056,30,22;][1039,935,30,22;1079,935,30,22;1119,935,30,22;1159,935,30,22;][1039,965,30,22;1079,965,30,22;1119,965,30,22;1159,965,30,22;][1039,996,30,22;1079,996,30,22;1119,996,30,22;1159,996,30,22;][1039,1026,30,22;1079,1026,30,22;1119,1026,30,22;1159,1026,30,22;][1039,1056,30,22;1079,1056,30,22;1119,1056,30,22;1159,1056,30,22;]\"}],\"OmrSubjectiveList\":[{\"regionList\":[{\"x\":285,\"y\":1124,\"width\":971,\"height\":548,\"bottom\":1672,\"right\":1256}],\"AreaID\":0,\"TopicType\":2,\"StartQid\":36,\"EndQid\":50},{\"regionList\":[{\"x\":274,\"y\":1665,\"width\":987,\"height\":163,\"bottom\":1828,\"right\":1261}],\"AreaID\":0,\"TopicType\":3,\"StartQid\":51,\"EndQid\":51}],\"HideAreaList\":[{\"AreaID\":1,\"IsSchoolNum\":true,\"HideAreaRect\":{\"x\":1021,\"y\":12,\"width\":631,\"height\":266,\"bottom\":278,\"right\":1652},\"TopicType\":4},{\"AreaID\":2,\"IsSchoolNum\":false,\"HideAreaRect\":{\"x\":104,\"y\":185,\"width\":107,\"height\":1624,\"bottom\":1809,\"right\":211},\"TopicType\":4}]},{\"pageIndex\":1,\"omrType\":0,\"Size\":0,\"fileName\":\"tpl10.1.png\",\"localRegion\":null,\"SchoolNumType\":0,\"OmrSchoolNumBlob\":null,\"QRSchoolNumBlob\":null,\"BarCodeSchoolNumBlob\":null,\"OmrObjectives\":null,\"OmrSubjectiveList\":[{\"regionList\":[{\"x\":205,\"y\":212,\"width\":976,\"height\":420,\"bottom\":632,\"right\":1181}],\"AreaID\":0,\"TopicType\":3,\"StartQid\":51,\"EndQid\":51},{\"regionList\":[{\"x\":207,\"y\":625,\"width\":967,\"height\":328,\"bottom\":953,\"right\":1174}],\"AreaID\":0,\"TopicType\":3,\"StartQid\":52,\"EndQid\":52},{\"regionList\":[{\"x\":214,\"y\":965,\"width\":972,\"height\":804,\"bottom\":1769,\"right\":1186}],\"AreaID\":0,\"TopicType\":3,\"StartQid\":53,\"EndQid\":53}],\"HideAreaList\":[]}]}}";
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

		String targetURL = "http://127.0.0.1:8080/exam/client/getExamTemplate/" + examId + "/" + gradeId + "/"
				+ subjectId;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void getExamTemplate(String egsId, String token) {

//		String targetURL = "http://47.92.53.57/api/client/getExamTemplate/" + egsId;
		String targetURL = "http://127.0.0.1:8080/exam/client/getExamTemplate/" + egsId;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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
			httpConnection.setRequestProperty("token", token);

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

	public static void getExamTemplate(Integer egsId, String token) {

		String targetURL = "http://47.92.53.57/api/client/getExamTemplate/" + egsId;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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
			httpConnection.setRequestProperty("token", token);

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
//		String targetURL = "http://47.92.53.57/api/client/getExams/" + examStatus;

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void getQuestionType(String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/question/type";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void getPermissions(String token) {

//		String targetURL = "http://127.0.0.1:8080/exam/client/getPermissions";
		String targetURL = "http://47.92.53.57/api/client/getPermissions";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

//		String targetURL = "http://127.0.0.1:8080/exam/client/login";
		String targetURL = "http://47.92.53.57/api/client/login";

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

		String parameters = "{\"id\":\"" + id + "\",\"fileName\":\"" + fileName + "\"}";
		System.out.println(parameters);
		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

		String parameters = "{\"id\":\"" + id + "\",\"fileName\":\"" + fileName + "\"}";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void saveStudentsAnswers(String token, int examId, int egsId) {

		String targetURL = "http://127.0.0.1:8080/exam/client//save/answers/" + examId + "/" + egsId;

		String parameters = "{\"sipisModelList\":[{\"ESID\":1,\"BatchNum\":2,\"StuAPPath\":\"aaa.png,bbb.png,ccc.png\",\"PaperStatus\":0,\"Exam_Student_Score\":[{\"questNum\":1,\"answerHas\":1,\"stuObjectAnswer\":\"A\"},{\"questNum\":2,\"answerHas\":2,\"stuObjectAnswer\":\"B\"},{\"questNum\":3,\"answerHas\":4,\"stuObjectAnswer\":\"C\"},{\"questNum\":4,\"answerHas\":8,\"stuObjectAnswer\":\"D\"}],\"HistoryErrorStatusList\":[0,1,2]}]}";
		System.out.println(parameters);
		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void getStudentPapers(String token, int egsId) {

		String targetURL = "http://127.0.0.1:8080/exam/client/exam/papers/" + egsId;

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void deleteStudentsPapers(String token, int egsId, int batchNum) {

		String targetURL = "http://127.0.0.1:8080/exam/client/delete/papers/" + egsId + "/" + batchNum;

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("DELETE");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void getStudentsinfo(String token, int examId, int gradeId) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getStudentsInfo/" + examId + "/" + gradeId;

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

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

	public static void getStudentsinfo(int egsId) {

		String targetURL = "http://127.0.0.1:8080/exam/setanswers/answers/" + egsId;

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
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
	
	public static void getExamSubjectStatus(String token, Long examId, String templateStatus, Integer gradeCode, String markingStatus){
		String targetURL = "http://127.0.0.1:8080/exam/client/exam/subject/status/"+examId+"/"+templateStatus+"/"+gradeCode+"/" + markingStatus;
//		String targetURL = "http://47.92.53.57/api/client/exam/subject/status/"+examId+"/"+templateStatus+"/"+gradeCode+"/" + markingStatus;
//		http://47.92.53.57//api/client/exam/subject/status/1/-1/1/0
		try {

			URL restServiceURL = new URL(targetURL);
			
			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);
			
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

	public static void addClientLogs(String logs) {

		String targetURL = "http://127.0.0.1:8080/exam/client/log";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(logs.getBytes());
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

	public static void getClientLogs() {

		String targetURL = "http://127.0.0.1:8080/exam/client/log";

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

	public static void deleteClientLogs() {

		String targetURL = "http://127.0.0.1:8080/exam/client/log";

		try {

			URL restServiceURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setRequestMethod("DELETE");
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

}

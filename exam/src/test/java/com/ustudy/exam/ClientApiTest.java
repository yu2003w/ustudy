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

		String username = "13810001341";
		String password = "123456";
		password = Base64Util.getMd5Pwd(password);
		String token = Base64Util.decode(username + ":" + password);
		
		long t1 =  System.currentTimeMillis();
		
//		 getQuestionType(token);
//		 System.out.println();
//		 System.out.println("--------------------getQuestionType-------------------");
//		 System.out.println();

//		 saveTemplates(token);
//		 System.out.println();
//		 System.out.println("--------------------saveTemplates-------------------");
//		 System.out.println();
		
//		 getExamTemplate("1", "1", "3", token);
//		 System.out.println();
//		 System.out.println("--------------------getExamTemplate-------------------");
//		 System.out.println();
		
//		 getExamTemplate("1", token);
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
		
		StringBuffer logs = new StringBuffer();
		for (int i = 0; i < 1000; i++) {
			logs.append("--------------------addClientLogs-------------------\n");
		}
		for (int i = 0; i < 1; i++) {
			addClientLogs(logs.toString());
		}
		System.out.println();
		System.out.println("--------------------addClientLogs-------------------");
		System.out.println();
		
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

		String targetURL = "http://127.0.0.1:8080/exam/client/saveExamTemplate/1";

		try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("token", token);

			String input = "{\"CSID\":\"1\",\"AnswerSheetXmlPath\":\"模板数据文件的名称\",\"TemplateInfo\":{\"isDoubleSide\":true,\"imgSourceType\":0,\"pages\":[{\"pageIndex\":0,\"omrType\":0,\"Size\":0,\"fileName\":\"tpl1205.0.png\",\"localRegion\":{\"matchRegion\":{\"x\":663,\"y\":285,\"width\":131,\"height\":41,\"bottom\":326,\"right\":794},\"TitlePoints\":[\"663, 285\",\"794, 285\",\"794, 326\",\"663, 326\",\"663, 285\"]},\"SchoolNumType\":2,\"OmrSchoolNumBlob\":null,\"QRSchoolNumBlob\":null,\"BarCodeSchoolNumBlob\":{\"Type\":0,\"isHorizontal\":true,\"region\":{\"x\":24,\"y\":26,\"width\":1409,\"height\":207,\"bottom\":233,\"right\":1433}},\"OmrObjectives\":[{\"topicType\":\"单选题\",\"region\":{\"x\":266,\"y\":742,\"width\":981,\"height\":783,\"bottom\":1525,\"right\":1247},\"objectiveItems\":[{\"arrange\":0,\"num\":{\"number\":1,\"pos\":\"299, 763\"},\"ItemRects\":[{\"x\":324,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":353},{\"x\":364,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":393},{\"x\":404,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":433},{\"x\":444,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":2,\"pos\":\"299, 795\"},\"ItemRects\":[{\"x\":324,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":353},{\"x\":364,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":393},{\"x\":404,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":433},{\"x\":444,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":3,\"pos\":\"299, 827\"},\"ItemRects\":[{\"x\":324,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":353},{\"x\":364,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":393},{\"x\":404,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":433},{\"x\":444,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":4,\"pos\":\"299, 859\"},\"ItemRects\":[{\"x\":324,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":353},{\"x\":364,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":393},{\"x\":404,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":433},{\"x\":444,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":5,\"pos\":\"299, 891\"},\"ItemRects\":[{\"x\":324,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":353},{\"x\":364,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":393},{\"x\":404,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":433},{\"x\":444,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":6,\"pos\":\"581, 765\"},\"ItemRects\":[{\"x\":606,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":635},{\"x\":646,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":675},{\"x\":686,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":715},{\"x\":726,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":755}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":7,\"pos\":\"581, 797\"},\"ItemRects\":[{\"x\":606,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":635},{\"x\":646,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":675},{\"x\":686,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":715},{\"x\":726,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":755}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":8,\"pos\":\"581, 829\"},\"ItemRects\":[{\"x\":606,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":635},{\"x\":646,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":675},{\"x\":686,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":715},{\"x\":726,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":755}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":9,\"pos\":\"581, 862\"},\"ItemRects\":[{\"x\":606,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":635},{\"x\":646,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":675},{\"x\":686,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":715},{\"x\":726,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":755}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":10,\"pos\":\"566, 894\"},\"ItemRects\":[{\"x\":606,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":635},{\"x\":646,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":675},{\"x\":686,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":715},{\"x\":726,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":755}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":11,\"pos\":\"871, 764\"},\"ItemRects\":[{\"x\":911,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":940},{\"x\":951,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":980},{\"x\":991,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":1020},{\"x\":1031,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":12,\"pos\":\"871, 796\"},\"ItemRects\":[{\"x\":911,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":940},{\"x\":951,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":980},{\"x\":991,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":1020},{\"x\":1031,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":13,\"pos\":\"871, 828\"},\"ItemRects\":[{\"x\":911,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":940},{\"x\":951,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":980},{\"x\":991,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":1020},{\"x\":1031,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":14,\"pos\":\"871, 860\"},\"ItemRects\":[{\"x\":911,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":940},{\"x\":951,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":980},{\"x\":991,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":1020},{\"x\":1031,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":15,\"pos\":\"871, 892\"},\"ItemRects\":[{\"x\":911,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":940},{\"x\":951,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":980},{\"x\":991,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":1020},{\"x\":1031,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":16,\"pos\":\"284, 957\"},\"ItemRects\":[{\"x\":324,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":353},{\"x\":364,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":393},{\"x\":404,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":433},{\"x\":444,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":17,\"pos\":\"284, 989\"},\"ItemRects\":[{\"x\":324,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":353},{\"x\":364,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":393},{\"x\":404,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":433},{\"x\":444,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":18,\"pos\":\"284, 1021\"},\"ItemRects\":[{\"x\":324,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":353},{\"x\":364,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":393},{\"x\":404,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":433},{\"x\":444,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":19,\"pos\":\"284, 1053\"},\"ItemRects\":[{\"x\":324,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":353},{\"x\":364,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":393},{\"x\":404,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":433},{\"x\":444,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":20,\"pos\":\"284, 1086\"},\"ItemRects\":[{\"x\":324,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":353},{\"x\":364,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":393},{\"x\":404,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":433},{\"x\":444,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":21,\"pos\":\"518, 957\"},\"ItemRects\":[{\"x\":558,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":587},{\"x\":598,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":627},{\"x\":638,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":667},{\"x\":678,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":707}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":22,\"pos\":\"518, 989\"},\"ItemRects\":[{\"x\":558,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":587},{\"x\":598,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":627},{\"x\":638,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":667},{\"x\":678,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":707}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":23,\"pos\":\"518, 1021\"},\"ItemRects\":[{\"x\":558,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":587},{\"x\":598,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":627},{\"x\":638,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":667},{\"x\":678,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":707}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":24,\"pos\":\"518, 1053\"},\"ItemRects\":[{\"x\":558,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":587},{\"x\":598,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":627},{\"x\":638,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":667},{\"x\":678,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":707}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":25,\"pos\":\"518, 1086\"},\"ItemRects\":[{\"x\":558,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":587},{\"x\":598,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":627},{\"x\":638,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":667},{\"x\":678,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":707}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":26,\"pos\":\"754, 959\"},\"ItemRects\":[{\"x\":794,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":823},{\"x\":834,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":863},{\"x\":874,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":903},{\"x\":913,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":942}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":27,\"pos\":\"754, 991\"},\"ItemRects\":[{\"x\":794,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":823},{\"x\":834,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":863},{\"x\":874,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":903},{\"x\":913,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":942}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":28,\"pos\":\"754, 1023\"},\"ItemRects\":[{\"x\":794,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":823},{\"x\":834,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":863},{\"x\":874,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":903},{\"x\":913,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":942}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":29,\"pos\":\"754, 1055\"},\"ItemRects\":[{\"x\":794,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":823},{\"x\":834,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":863},{\"x\":874,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":903},{\"x\":913,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":942}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":30,\"pos\":\"754, 1087\"},\"ItemRects\":[{\"x\":794,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":823},{\"x\":834,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":863},{\"x\":874,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":903},{\"x\":913,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":942}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":31,\"pos\":\"989, 956\"},\"ItemRects\":[{\"x\":1029,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1058},{\"x\":1069,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1098},{\"x\":1109,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1138},{\"x\":1149,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1178}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":32,\"pos\":\"989, 988\"},\"ItemRects\":[{\"x\":1029,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1058},{\"x\":1069,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1098},{\"x\":1109,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1138},{\"x\":1149,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1178}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":33,\"pos\":\"989, 1020\"},\"ItemRects\":[{\"x\":1029,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1058},{\"x\":1069,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1098},{\"x\":1109,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1138},{\"x\":1149,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1178}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":34,\"pos\":\"989, 1052\"},\"ItemRects\":[{\"x\":1029,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1058},{\"x\":1069,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1098},{\"x\":1109,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1138},{\"x\":1149,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1178}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":35,\"pos\":\"989, 1084\"},\"ItemRects\":[{\"x\":1029,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1058},{\"x\":1069,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1098},{\"x\":1109,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1138},{\"x\":1149,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1178}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":36,\"pos\":\"636, 1149\"},\"ItemRects\":[{\"x\":676,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":705},{\"x\":716,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":745},{\"x\":756,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":785},{\"x\":796,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":825}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":37,\"pos\":\"636, 1181\"},\"ItemRects\":[{\"x\":676,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":705},{\"x\":716,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":745},{\"x\":756,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":785},{\"x\":796,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":825}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":38,\"pos\":\"636, 1213\"},\"ItemRects\":[{\"x\":676,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":705},{\"x\":716,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":745},{\"x\":756,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":785},{\"x\":796,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":825}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":39,\"pos\":\"636, 1245\"},\"ItemRects\":[{\"x\":676,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":705},{\"x\":716,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":745},{\"x\":756,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":785},{\"x\":796,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":825}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":40,\"pos\":\"636, 1278\"},\"ItemRects\":[{\"x\":676,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":705},{\"x\":716,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":745},{\"x\":756,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":785},{\"x\":796,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":825}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":41,\"pos\":\"871, 1149\"},\"ItemRects\":[{\"x\":911,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":940},{\"x\":951,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":980},{\"x\":991,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":1020},{\"x\":1031,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":42,\"pos\":\"871, 1181\"},\"ItemRects\":[{\"x\":911,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":940},{\"x\":951,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":980},{\"x\":991,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":1020},{\"x\":1031,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":43,\"pos\":\"871, 1213\"},\"ItemRects\":[{\"x\":911,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":940},{\"x\":951,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":980},{\"x\":991,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":1020},{\"x\":1031,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":44,\"pos\":\"871, 1245\"},\"ItemRects\":[{\"x\":911,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":940},{\"x\":951,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":980},{\"x\":991,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":1020},{\"x\":1031,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":45,\"pos\":\"871, 1278\"},\"ItemRects\":[{\"x\":911,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":940},{\"x\":951,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":980},{\"x\":991,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":1020},{\"x\":1031,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":1060}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":46,\"pos\":\"284, 1344\"},\"ItemRects\":[{\"x\":324,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":353},{\"x\":364,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":393},{\"x\":404,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":433},{\"x\":444,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":47,\"pos\":\"284, 1376\"},\"ItemRects\":[{\"x\":324,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":353},{\"x\":364,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":393},{\"x\":404,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":433},{\"x\":444,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":48,\"pos\":\"284, 1408\"},\"ItemRects\":[{\"x\":324,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":353},{\"x\":364,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":393},{\"x\":404,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":433},{\"x\":444,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":49,\"pos\":\"284, 1440\"},\"ItemRects\":[{\"x\":324,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":353},{\"x\":364,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":393},{\"x\":404,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":433},{\"x\":444,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":50,\"pos\":\"284, 1472\"},\"ItemRects\":[{\"x\":324,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":353},{\"x\":364,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":393},{\"x\":404,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":433},{\"x\":444,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":473}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":51,\"pos\":\"523, 1344\"},\"ItemRects\":[{\"x\":563,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":592},{\"x\":602,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":631},{\"x\":642,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":671},{\"x\":682,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":711}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":52,\"pos\":\"523, 1376\"},\"ItemRects\":[{\"x\":563,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":592},{\"x\":602,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":631},{\"x\":642,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":671},{\"x\":682,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":711}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":53,\"pos\":\"523, 1408\"},\"ItemRects\":[{\"x\":563,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":592},{\"x\":602,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":631},{\"x\":642,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":671},{\"x\":682,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":711}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":54,\"pos\":\"523, 1440\"},\"ItemRects\":[{\"x\":563,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":592},{\"x\":602,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":631},{\"x\":642,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":671},{\"x\":682,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":711}],\"OcrDoubt\":false},{\"arrange\":0,\"num\":{\"number\":55,\"pos\":\"523, 1472\"},\"ItemRects\":[{\"x\":563,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":592},{\"x\":602,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":631},{\"x\":642,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":671},{\"x\":682,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":711}],\"OcrDoubt\":false}],\"ItemBlobSort\":0,\"OcrSort\":0,\"ItemDistance\":0,\"ItemBlobDistance\":0,\"OcrBlobDistance\":0,\"originnumList\":null,\"originblobList\":null,\"numList\":[{\"Key\":1,\"Value\":\"299, 763\"},{\"Key\":2,\"Value\":\"299, 795\"},{\"Key\":3,\"Value\":\"299, 827\"},{\"Key\":4,\"Value\":\"299, 859\"},{\"Key\":5,\"Value\":\"299, 891\"},{\"Key\":6,\"Value\":\"581, 765\"},{\"Key\":7,\"Value\":\"581, 797\"},{\"Key\":8,\"Value\":\"581, 829\"},{\"Key\":9,\"Value\":\"581, 862\"},{\"Key\":10,\"Value\":\"566, 894\"},{\"Key\":11,\"Value\":\"871, 764\"},{\"Key\":12,\"Value\":\"871, 796\"},{\"Key\":13,\"Value\":\"871, 828\"},{\"Key\":14,\"Value\":\"871, 860\"},{\"Key\":15,\"Value\":\"871, 892\"},{\"Key\":16,\"Value\":\"284, 957\"},{\"Key\":17,\"Value\":\"284, 989\"},{\"Key\":18,\"Value\":\"284, 1021\"},{\"Key\":19,\"Value\":\"284, 1053\"},{\"Key\":20,\"Value\":\"284, 1086\"},{\"Key\":21,\"Value\":\"518, 957\"},{\"Key\":22,\"Value\":\"518, 989\"},{\"Key\":23,\"Value\":\"518, 1021\"},{\"Key\":24,\"Value\":\"518, 1053\"},{\"Key\":25,\"Value\":\"518, 1086\"},{\"Key\":26,\"Value\":\"754, 959\"},{\"Key\":27,\"Value\":\"754, 991\"},{\"Key\":28,\"Value\":\"754, 1023\"},{\"Key\":29,\"Value\":\"754, 1055\"},{\"Key\":30,\"Value\":\"754, 1087\"},{\"Key\":31,\"Value\":\"989, 956\"},{\"Key\":32,\"Value\":\"989, 988\"},{\"Key\":33,\"Value\":\"989, 1020\"},{\"Key\":34,\"Value\":\"989, 1052\"},{\"Key\":35,\"Value\":\"989, 1084\"},{\"Key\":36,\"Value\":\"636, 1149\"},{\"Key\":37,\"Value\":\"636, 1181\"},{\"Key\":38,\"Value\":\"636, 1213\"},{\"Key\":39,\"Value\":\"636, 1245\"},{\"Key\":40,\"Value\":\"636, 1278\"},{\"Key\":41,\"Value\":\"871, 1149\"},{\"Key\":42,\"Value\":\"871, 1181\"},{\"Key\":43,\"Value\":\"871, 1213\"},{\"Key\":44,\"Value\":\"871, 1245\"},{\"Key\":45,\"Value\":\"871, 1278\"},{\"Key\":46,\"Value\":\"284, 1344\"},{\"Key\":47,\"Value\":\"284, 1376\"},{\"Key\":48,\"Value\":\"284, 1408\"},{\"Key\":49,\"Value\":\"284, 1440\"},{\"Key\":50,\"Value\":\"284, 1472\"},{\"Key\":51,\"Value\":\"523, 1344\"},{\"Key\":52,\"Value\":\"523, 1376\"},{\"Key\":53,\"Value\":\"523, 1408\"},{\"Key\":54,\"Value\":\"523, 1440\"},{\"Key\":55,\"Value\":\"523, 1472\"}],\"blobList\":[{\"x\":324,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":353},{\"x\":364,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":393},{\"x\":404,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":433},{\"x\":444,\"y\":763,\"width\":29,\"height\":21,\"bottom\":784,\"right\":473},{\"x\":324,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":353},{\"x\":364,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":393},{\"x\":404,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":433},{\"x\":444,\"y\":795,\"width\":29,\"height\":21,\"bottom\":816,\"right\":473},{\"x\":324,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":353},{\"x\":364,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":393},{\"x\":404,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":433},{\"x\":444,\"y\":827,\"width\":29,\"height\":21,\"bottom\":848,\"right\":473},{\"x\":324,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":353},{\"x\":364,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":393},{\"x\":404,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":433},{\"x\":444,\"y\":859,\"width\":29,\"height\":21,\"bottom\":880,\"right\":473},{\"x\":324,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":353},{\"x\":364,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":393},{\"x\":404,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":433},{\"x\":444,\"y\":891,\"width\":29,\"height\":21,\"bottom\":912,\"right\":473},{\"x\":606,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":635},{\"x\":646,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":675},{\"x\":686,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":715},{\"x\":726,\"y\":765,\"width\":29,\"height\":21,\"bottom\":786,\"right\":755},{\"x\":606,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":635},{\"x\":646,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":675},{\"x\":686,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":715},{\"x\":726,\"y\":797,\"width\":29,\"height\":21,\"bottom\":818,\"right\":755},{\"x\":606,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":635},{\"x\":646,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":675},{\"x\":686,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":715},{\"x\":726,\"y\":829,\"width\":29,\"height\":21,\"bottom\":850,\"right\":755},{\"x\":606,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":635},{\"x\":646,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":675},{\"x\":686,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":715},{\"x\":726,\"y\":862,\"width\":29,\"height\":21,\"bottom\":883,\"right\":755},{\"x\":606,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":635},{\"x\":646,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":675},{\"x\":686,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":715},{\"x\":726,\"y\":894,\"width\":29,\"height\":21,\"bottom\":915,\"right\":755},{\"x\":911,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":940},{\"x\":951,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":980},{\"x\":991,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":1020},{\"x\":1031,\"y\":764,\"width\":29,\"height\":21,\"bottom\":785,\"right\":1060},{\"x\":911,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":940},{\"x\":951,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":980},{\"x\":991,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":1020},{\"x\":1031,\"y\":796,\"width\":29,\"height\":21,\"bottom\":817,\"right\":1060},{\"x\":911,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":940},{\"x\":951,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":980},{\"x\":991,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":1020},{\"x\":1031,\"y\":828,\"width\":29,\"height\":21,\"bottom\":849,\"right\":1060},{\"x\":911,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":940},{\"x\":951,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":980},{\"x\":991,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":1020},{\"x\":1031,\"y\":860,\"width\":29,\"height\":21,\"bottom\":881,\"right\":1060},{\"x\":911,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":940},{\"x\":951,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":980},{\"x\":991,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":1020},{\"x\":1031,\"y\":892,\"width\":29,\"height\":21,\"bottom\":913,\"right\":1060},{\"x\":324,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":353},{\"x\":364,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":393},{\"x\":404,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":433},{\"x\":444,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":473},{\"x\":324,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":353},{\"x\":364,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":393},{\"x\":404,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":433},{\"x\":444,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":473},{\"x\":324,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":353},{\"x\":364,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":393},{\"x\":404,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":433},{\"x\":444,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":473},{\"x\":324,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":353},{\"x\":364,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":393},{\"x\":404,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":433},{\"x\":444,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":473},{\"x\":324,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":353},{\"x\":364,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":393},{\"x\":404,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":433},{\"x\":444,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":473},{\"x\":558,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":587},{\"x\":598,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":627},{\"x\":638,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":667},{\"x\":678,\"y\":957,\"width\":29,\"height\":21,\"bottom\":978,\"right\":707},{\"x\":558,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":587},{\"x\":598,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":627},{\"x\":638,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":667},{\"x\":678,\"y\":989,\"width\":29,\"height\":21,\"bottom\":1010,\"right\":707},{\"x\":558,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":587},{\"x\":598,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":627},{\"x\":638,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":667},{\"x\":678,\"y\":1021,\"width\":29,\"height\":21,\"bottom\":1042,\"right\":707},{\"x\":558,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":587},{\"x\":598,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":627},{\"x\":638,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":667},{\"x\":678,\"y\":1053,\"width\":29,\"height\":21,\"bottom\":1074,\"right\":707},{\"x\":558,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":587},{\"x\":598,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":627},{\"x\":638,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":667},{\"x\":678,\"y\":1086,\"width\":29,\"height\":21,\"bottom\":1107,\"right\":707},{\"x\":794,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":823},{\"x\":834,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":863},{\"x\":874,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":903},{\"x\":913,\"y\":959,\"width\":29,\"height\":21,\"bottom\":980,\"right\":942},{\"x\":794,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":823},{\"x\":834,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":863},{\"x\":874,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":903},{\"x\":913,\"y\":991,\"width\":29,\"height\":21,\"bottom\":1012,\"right\":942},{\"x\":794,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":823},{\"x\":834,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":863},{\"x\":874,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":903},{\"x\":913,\"y\":1023,\"width\":29,\"height\":21,\"bottom\":1044,\"right\":942},{\"x\":794,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":823},{\"x\":834,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":863},{\"x\":874,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":903},{\"x\":913,\"y\":1055,\"width\":29,\"height\":21,\"bottom\":1076,\"right\":942},{\"x\":794,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":823},{\"x\":834,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":863},{\"x\":874,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":903},{\"x\":913,\"y\":1087,\"width\":29,\"height\":21,\"bottom\":1108,\"right\":942},{\"x\":1029,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1058},{\"x\":1069,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1098},{\"x\":1109,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1138},{\"x\":1149,\"y\":956,\"width\":29,\"height\":21,\"bottom\":977,\"right\":1178},{\"x\":1029,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1058},{\"x\":1069,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1098},{\"x\":1109,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1138},{\"x\":1149,\"y\":988,\"width\":29,\"height\":21,\"bottom\":1009,\"right\":1178},{\"x\":1029,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1058},{\"x\":1069,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1098},{\"x\":1109,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1138},{\"x\":1149,\"y\":1020,\"width\":29,\"height\":21,\"bottom\":1041,\"right\":1178},{\"x\":1029,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1058},{\"x\":1069,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1098},{\"x\":1109,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1138},{\"x\":1149,\"y\":1052,\"width\":29,\"height\":21,\"bottom\":1073,\"right\":1178},{\"x\":1029,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1058},{\"x\":1069,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1098},{\"x\":1109,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1138},{\"x\":1149,\"y\":1084,\"width\":29,\"height\":21,\"bottom\":1105,\"right\":1178},{\"x\":676,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":705},{\"x\":716,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":745},{\"x\":756,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":785},{\"x\":796,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":825},{\"x\":676,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":705},{\"x\":716,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":745},{\"x\":756,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":785},{\"x\":796,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":825},{\"x\":676,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":705},{\"x\":716,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":745},{\"x\":756,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":785},{\"x\":796,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":825},{\"x\":676,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":705},{\"x\":716,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":745},{\"x\":756,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":785},{\"x\":796,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":825},{\"x\":676,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":705},{\"x\":716,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":745},{\"x\":756,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":785},{\"x\":796,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":825},{\"x\":911,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":940},{\"x\":951,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":980},{\"x\":991,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":1020},{\"x\":1031,\"y\":1149,\"width\":29,\"height\":21,\"bottom\":1170,\"right\":1060},{\"x\":911,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":940},{\"x\":951,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":980},{\"x\":991,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":1020},{\"x\":1031,\"y\":1181,\"width\":29,\"height\":21,\"bottom\":1202,\"right\":1060},{\"x\":911,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":940},{\"x\":951,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":980},{\"x\":991,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":1020},{\"x\":1031,\"y\":1213,\"width\":29,\"height\":21,\"bottom\":1234,\"right\":1060},{\"x\":911,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":940},{\"x\":951,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":980},{\"x\":991,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":1020},{\"x\":1031,\"y\":1245,\"width\":29,\"height\":21,\"bottom\":1266,\"right\":1060},{\"x\":911,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":940},{\"x\":951,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":980},{\"x\":991,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":1020},{\"x\":1031,\"y\":1278,\"width\":29,\"height\":21,\"bottom\":1299,\"right\":1060},{\"x\":324,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":353},{\"x\":364,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":393},{\"x\":404,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":433},{\"x\":444,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":473},{\"x\":324,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":353},{\"x\":364,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":393},{\"x\":404,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":433},{\"x\":444,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":473},{\"x\":324,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":353},{\"x\":364,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":393},{\"x\":404,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":433},{\"x\":444,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":473},{\"x\":324,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":353},{\"x\":364,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":393},{\"x\":404,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":433},{\"x\":444,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":473},{\"x\":324,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":353},{\"x\":364,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":393},{\"x\":404,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":433},{\"x\":444,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":473},{\"x\":563,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":592},{\"x\":602,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":631},{\"x\":642,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":671},{\"x\":682,\"y\":1344,\"width\":29,\"height\":21,\"bottom\":1365,\"right\":711},{\"x\":563,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":592},{\"x\":602,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":631},{\"x\":642,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":671},{\"x\":682,\"y\":1376,\"width\":29,\"height\":21,\"bottom\":1397,\"right\":711},{\"x\":563,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":592},{\"x\":602,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":631},{\"x\":642,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":671},{\"x\":682,\"y\":1408,\"width\":29,\"height\":21,\"bottom\":1429,\"right\":711},{\"x\":563,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":592},{\"x\":602,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":631},{\"x\":642,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":671},{\"x\":682,\"y\":1440,\"width\":29,\"height\":21,\"bottom\":1461,\"right\":711},{\"x\":563,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":592},{\"x\":602,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":631},{\"x\":642,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":671},{\"x\":682,\"y\":1472,\"width\":29,\"height\":21,\"bottom\":1493,\"right\":711}],\"SchoolNumberPoints\":[\"266, 742\",\"1247, 742\",\"1247, 1525\",\"266, 1525\",\"266, 742\"],\"OmrObjectiveString\":\"[324,763,29,21;364,763,29,21;404,763,29,21;444,763,29,21;][324,795,29,21;364,795,29,21;404,795,29,21;444,795,29,21;][324,827,29,21;364,827,29,21;404,827,29,21;444,827,29,21;][324,859,29,21;364,859,29,21;404,859,29,21;444,859,29,21;][324,891,29,21;364,891,29,21;404,891,29,21;444,891,29,21;][606,765,29,21;646,765,29,21;686,765,29,21;726,765,29,21;][606,797,29,21;646,797,29,21;686,797,29,21;726,797,29,21;][606,829,29,21;646,829,29,21;686,829,29,21;726,829,29,21;][606,862,29,21;646,862,29,21;686,862,29,21;726,862,29,21;][606,894,29,21;646,894,29,21;686,894,29,21;726,894,29,21;][911,764,29,21;951,764,29,21;991,764,29,21;1031,764,29,21;][911,796,29,21;951,796,29,21;991,796,29,21;1031,796,29,21;][911,828,29,21;951,828,29,21;991,828,29,21;1031,828,29,21;][911,860,29,21;951,860,29,21;991,860,29,21;1031,860,29,21;][911,892,29,21;951,892,29,21;991,892,29,21;1031,892,29,21;][324,957,29,21;364,957,29,21;404,957,29,21;444,957,29,21;][324,989,29,21;364,989,29,21;404,989,29,21;444,989,29,21;][324,1021,29,21;364,1021,29,21;404,1021,29,21;444,1021,29,21;][324,1053,29,21;364,1053,29,21;404,1053,29,21;444,1053,29,21;][324,1086,29,21;364,1086,29,21;404,1086,29,21;444,1086,29,21;][558,957,29,21;598,957,29,21;638,957,29,21;678,957,29,21;][558,989,29,21;598,989,29,21;638,989,29,21;678,989,29,21;][558,1021,29,21;598,1021,29,21;638,1021,29,21;678,1021,29,21;][558,1053,29,21;598,1053,29,21;638,1053,29,21;678,1053,29,21;][558,1086,29,21;598,1086,29,21;638,1086,29,21;678,1086,29,21;][794,959,29,21;834,959,29,21;874,959,29,21;913,959,29,21;][794,991,29,21;834,991,29,21;874,991,29,21;913,991,29,21;][794,1023,29,21;834,1023,29,21;874,1023,29,21;913,1023,29,21;][794,1055,29,21;834,1055,29,21;874,1055,29,21;913,1055,29,21;][794,1087,29,21;834,1087,29,21;874,1087,29,21;913,1087,29,21;][1029,956,29,21;1069,956,29,21;1109,956,29,21;1149,956,29,21;][1029,988,29,21;1069,988,29,21;1109,988,29,21;1149,988,29,21;][1029,1020,29,21;1069,1020,29,21;1109,1020,29,21;1149,1020,29,21;][1029,1052,29,21;1069,1052,29,21;1109,1052,29,21;1149,1052,29,21;][1029,1084,29,21;1069,1084,29,21;1109,1084,29,21;1149,1084,29,21;][676,1149,29,21;716,1149,29,21;756,1149,29,21;796,1149,29,21;][676,1181,29,21;716,1181,29,21;756,1181,29,21;796,1181,29,21;][676,1213,29,21;716,1213,29,21;756,1213,29,21;796,1213,29,21;][676,1245,29,21;716,1245,29,21;756,1245,29,21;796,1245,29,21;][676,1278,29,21;716,1278,29,21;756,1278,29,21;796,1278,29,21;][911,1149,29,21;951,1149,29,21;991,1149,29,21;1031,1149,29,21;][911,1181,29,21;951,1181,29,21;991,1181,29,21;1031,1181,29,21;][911,1213,29,21;951,1213,29,21;991,1213,29,21;1031,1213,29,21;][911,1245,29,21;951,1245,29,21;991,1245,29,21;1031,1245,29,21;][911,1278,29,21;951,1278,29,21;991,1278,29,21;1031,1278,29,21;][324,1344,29,21;364,1344,29,21;404,1344,29,21;444,1344,29,21;][324,1376,29,21;364,1376,29,21;404,1376,29,21;444,1376,29,21;][324,1408,29,21;364,1408,29,21;404,1408,29,21;444,1408,29,21;][324,1440,29,21;364,1440,29,21;404,1440,29,21;444,1440,29,21;][324,1472,29,21;364,1472,29,21;404,1472,29,21;444,1472,29,21;][563,1344,29,21;602,1344,29,21;642,1344,29,21;682,1344,29,21;][563,1376,29,21;602,1376,29,21;642,1376,29,21;682,1376,29,21;][563,1408,29,21;602,1408,29,21;642,1408,29,21;682,1408,29,21;][563,1440,29,21;602,1440,29,21;642,1440,29,21;682,1440,29,21;][563,1472,29,21;602,1472,29,21;642,1472,29,21;682,1472,29,21;]\"}],\"OmrSubjectiveList\":[{\"regionList\":[{\"x\":271,\"y\":1531,\"width\":964,\"height\":259,\"bottom\":1790,\"right\":1235}],\"AreaID\":0,\"TopicType\":\"填空题\",\"StartQid\":59,\"EndQid\":59}],\"HideAreaList\":[{\"AreaID\":1,\"IsSchoolNum\":true,\"HideAreaRect\":{\"x\":24,\"y\":26,\"width\":1409,\"height\":207,\"bottom\":233,\"right\":1433},\"TopicType\":\"解答题\"},{\"AreaID\":2,\"IsSchoolNum\":false,\"HideAreaRect\":{\"x\":82,\"y\":262,\"width\":145,\"height\":1570,\"bottom\":1832,\"right\":227},\"TopicType\":\"论述题\"}]},{\"pageIndex\":1,\"omrType\":0,\"Size\":0,\"fileName\":\"tpl1205.1.png\",\"localRegion\":null,\"SchoolNumType\":0,\"OmrSchoolNumBlob\":null,\"QRSchoolNumBlob\":null,\"BarCodeSchoolNumBlob\":null,\"OmrObjectives\":null,\"OmrSubjectiveList\":[{\"regionList\":[{\"x\":238,\"y\":176,\"width\":944,\"height\":733,\"bottom\":909,\"right\":1182}],\"AreaID\":0,\"TopicType\":\"单选题\",\"StartQid\":58,\"EndQid\":58},{\"regionList\":[{\"x\":221,\"y\":923,\"width\":971,\"height\":832,\"bottom\":1755,\"right\":1192}],\"AreaID\":0,\"TopicType\":\"填空题\",\"StartQid\":57,\"EndQid\":57}],\"HideAreaList\":[]}]}}";
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

	public static void getExamTemplate(String csId, String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getExamTemplate/" + csId;

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

	public static void getExamTemplate(Integer csId, String token) {

		String targetURL = "http://127.0.0.1:8080/exam/client/getExamTemplate/" + csId;

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

//		String targetURL = "http://127.0.0.1:8080/exam/client/getExams/" + examStatus;
		String targetURL = "http://47.92.53.57/api/client/getExams/" + examStatus;

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

		String targetURL = "http://127.0.0.1:8080/exam/client/getPermissions";

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

		String targetURL = "http://127.0.0.1:8080/exam/client/login";

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

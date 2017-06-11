package com.ustudy.dashboard.util;

public class JsonAssembler {

	/**
	 * The output result is similar as below, 
	 * [ "courses":{ 
	 *   "subject": "abc",
	 *   "subject": "cde" }]
	 * 
	 * @param label   label for assembling JsonArray
	 * @param array   Data for assembling JsonArrary
	 * @return JsonArray
	 */
	public static String arrayToJson(String label, String[] array) {
		String res = "[";
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (i == 0)
				res += "{\"" + label + "\":" + "\"" + array[i] + "\"}";
			else
				res += ",{\"" + label + "\":" + "\"" + array[i] + "\"}";
		}
		res += "]";
		return res;
	}
}

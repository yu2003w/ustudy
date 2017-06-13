package com.ustudy.dashboard.util;

import java.util.List;

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
	public static String arrayToJson(String label, List<String> array) {
		String res = "[";
		int len = array.size();
		for (int i = 0; i < len; i++) {
			if (i == 0)
				res += "{\"" + label + "\":" + "\"" + array.get(i) + "\"}";
			else
				res += ",{\"" + label + "\":" + "\"" + array.get(i) + "\"}";
		}
		res += "]";
		return res;
	}
}

package com.socialNetwork.utils;

import java.util.List;

public class Utils {

	public static boolean isEmpty(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
}

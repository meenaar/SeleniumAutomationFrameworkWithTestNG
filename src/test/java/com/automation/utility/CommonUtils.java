package com.automation.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	
	
	public static String getStringDateAndTimeStamp() {
		
		String value = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return value;
	}

}

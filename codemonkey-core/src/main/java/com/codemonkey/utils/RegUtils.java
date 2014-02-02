package com.codemonkey.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtils {

	public static final String EMAIL_REG = "^[a-z0-9_+.-]+@([a-z0-9-]+.)+[a-z0-9]{2,4}";
	public static final String ID_CARD_REG = "(^[1-9]\\d{5}[12]\\d{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|[1-2]\\d))\\d{3}[0-9Xx]$)|" + 		//18闰年出生日期的
											 "(^[1-9]\\d{5}[12]\\d{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|1\\d|2[0-8]))\\d{3}[0-9Xx]$)|" +	//18平年出生日期的
											 "(^[1-9]\\d{5}\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|[1-2]\\d))\\d{3}$)|" +				//15闰年出生日期的
											 "(^[1-9]\\d{5}\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|1\\d|2[0-8]))\\d{3}$)";				//15平年出生日期的
	public static final String MOBILE_PHONE_REG = "(^((\\(\\d{3}\\))|(\\d{3}\\-)|(\\(\\+\\d{2}\\)))[12][03458]\\d{9}$)|(^[12][03458]\\d{9}$)";
	public static final String FIX_PHONE_REG = "(^[0]\\d{2,3}-\\d{7,8}$)";
	public static final String GROUP_PHONE_REG = "^\\d{4}$";
	public static final String EXTENSION_PHONE_REG = "^\\d{3,4}$";
	public static final String NUM_LETTER_UNDERLINE_REG = "^\\w+$";
	public static final String NUM_LETTER_REG = "^[a-z0-9A-Z]+$";
	public static final String CAPITAL_LETTER_REG = "^[A-Z]+$";
	public static final String NON_REAL_NUM_REG = "^[0-9]+((\\.{1}?[0-9]{1,13})|(\\.{0}))?$";
	public static final String NON_INT_REG = "^\\d+$";
	public static final String POS_INT_REG = "^[0-9]*[1-9][0-9]*$";
	public static final String IP_REG = "^(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})$";
	public static final String POSTCODE_REG = "^[0-9]{1}(\\d){5}$";
	
	public static final String DATE_REG = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String DATE_TIME_REG = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
	public static final String T_DATE_TIME_REG = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";

	public static boolean matches(String reg , String value){
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
}

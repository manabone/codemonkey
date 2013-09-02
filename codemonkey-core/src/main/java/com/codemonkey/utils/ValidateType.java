package com.codemonkey.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public enum ValidateType {
	NOT_BLANK {
		@Override
		public boolean validate(String value) {
			return StringUtils.isNotBlank(value);
		}
		@Override
		public String getMessage() {
			return "不能为空";
		}
	}, 
	EMAIL {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^[a-z0-9_+.-]+@([a-z0-9-]+.)+[a-z0-9]{2,4}");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "邮箱格式不正确";
		}
	},
	ID_CARD {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
			//	Pattern pattern = Pattern.compile("^\\d{15}|(\\d{17}[0-9xX])");
				
				Pattern pattern = Pattern.compile(
						"(^[1-9]\\d{5}[12]\\d{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|[1-2]\\d))\\d{3}[0-9Xx]$)|" + 		//18闰年出生日期的
						"(^[1-9]\\d{5}[12]\\d{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|1\\d|2[0-8]))\\d{3}[0-9Xx]$)|" +	//18平年出生日期的
						"(^[1-9]\\d{5}\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|[1-2]\\d))\\d{3}$)|" +				//15闰年出生日期的
						"(^[1-9]\\d{5}\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|1\\d|2[0-8]))\\d{3}$)");				//15平年出生日期的
				
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "身份证号不正确";
		}
	},
	MOBILE_PHONE {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("(^((\\(\\d{3}\\))|(\\d{3}\\-)|(\\(\\+\\d{2}\\)))[12][03458]\\d{9}$)|(^[12][03458]\\d{9}$)");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "手机号格式不正确";
		}
	},
	FIX_PHONE {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("(^[0]\\d{2,3}-\\d{5,9}$)|(^[0]\\d{2,3}-\\d{5,9}$)");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "固定电话格式不正确(如：0411-88888888)";
		}
	},
	CONTACT_PHONE {
		@Override
		public boolean validate(String value) {
			return MOBILE_PHONE.validate(value) || FIX_PHONE.validate(value);
		}
		@Override
		public String getMessage() {
			return "联系电话格式不正确，请填写手机号或固定电话(如：0411-88888888)";
		}
	},
	GROUP_PHONE {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^\\d{4}$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "集团电话格式不正确";
		}
	},
	EXTENSION_PHONE {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^\\d{3,4}$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "分机电话格式不正确";
		}
	},
	NUM_LETTER_UNDERLINE {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^\\w+$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "必须为数字，字母或下划线";
		}
	},
	NUM_LETTER {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^[a-z0-9A-Z]+$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "必须为数字或字母";
		}
	},
	CAPITAL_LETTER {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^[A-Z]+$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "必须为大写英文字母";
		}
	},
	NON_REAL_NUM {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^[0-9]+((\\.{1}?[0-9]{1,13})|(\\.{0}))?$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "必须为非负实数";
		}
	},
	NON_INT {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^\\d+$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "必须为非负整数";
		}
	},
	POS_INT {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "请输入正整数";
		}
	},
	IP {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "ip地址格式错误";
		}
	},
	POSTCODE {
		@Override
		public boolean validate(String value) {
			if(StringUtils.isNotBlank(value)){
				Pattern pattern = Pattern.compile("^[0-9]{1}(\\d){5}$");
				Matcher matcher = pattern.matcher(value);
				return matcher.matches();
			}
			return true;
		}
		@Override
		public String getMessage() {
			return "邮政编码格式错误";
		}
	}

	;

	public abstract boolean validate(String value);

	public abstract String getMessage();

}

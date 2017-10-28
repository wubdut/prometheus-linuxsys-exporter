package com.LinuxSysExporter.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecStrFilter {

	public static String replaceSpecStr(String orgStr){  
	    if (null!=orgStr&&!"".equals(orgStr.trim())) {  
	        String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
	        Pattern p = Pattern.compile(regEx);
	        Matcher m = p.matcher(orgStr);
	        return m.replaceAll("");
	    }
	    return null;
	}
}

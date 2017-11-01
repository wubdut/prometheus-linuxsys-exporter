package com.LinuxSysExporter.basic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	public static String ip;
	public static String userName;
	public static String userPwd;
	
	static {
		try {
			getProperties("conf/target.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getProperties(String filePath) throws IOException {
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(filePath);
		pro.load(in);
		in.close();
		ip = pro.getProperty("ip");
		userName = pro.getProperty("userName");
		userPwd = pro.getProperty("userPwd");
	}
	
}

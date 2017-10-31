package com.LinuxSysExporter.basic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	public String ip;
	public String userName;
	public String userPwd;
	
	public Configuration() throws IOException {
		getProperties("conf/target.properties");
	}
	
	public Configuration(String filePath) throws IOException {
		getProperties(filePath);
	}
	
	public void getProperties(String filePath) throws IOException {
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(filePath);
		pro.load(in);
		in.close();
		ip = pro.getProperty("ip");
		userName = pro.getProperty("userName");
		userPwd = pro.getProperty("userPwd");
	}
	
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		System.out.println(conf.ip);
		System.out.println(conf.userName);
		System.out.println(conf.userPwd);
	}
}

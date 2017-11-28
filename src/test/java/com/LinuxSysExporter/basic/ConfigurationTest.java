package com.LinuxSysExporter.basic;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void test() {
		System.out.println(Configuration.ip);
		System.out.println(Configuration.userName);
		System.out.println(Configuration.userPwd);
	}

}

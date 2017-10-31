package com.LinuxSysExporter.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class IotopPidTest {

	@Test
	public void test() throws IOException {
		IotopPid iotop = new IotopPid("39.108.214.220", "root", "Abc123+-*/");
		for (String key : iotop.map.keySet()) {
			System.out.println(key + " " + iotop.map.get(key));
		}
	}

}

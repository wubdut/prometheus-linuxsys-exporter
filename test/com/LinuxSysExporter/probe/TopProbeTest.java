package com.LinuxSysExporter.probe;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TopProbeTest {

	@Test
	public void test() throws IOException {
		TopProbe top = new TopProbe("39.108.214.220", "root", "Abc123+-*/");
		for (String key : top.map.keySet()) {
			System.out.println(key + " " + top.map.get(key).first + " " + top.map.get(key).second);
		}
	}

}

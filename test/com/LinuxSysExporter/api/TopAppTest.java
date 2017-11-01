package com.LinuxSysExporter.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TopAppTest {

	@Test
	public void test() throws IOException {
		TopApp top = new TopApp();
		for (String key : top.map.keySet()) {
			System.out.println(key + " " + top.map.get(key));
		}
	}

}

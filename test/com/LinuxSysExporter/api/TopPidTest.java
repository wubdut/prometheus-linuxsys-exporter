package com.LinuxSysExporter.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TopPidTest extends TopApi {

	@Test
	public void test() throws IOException {
		TopPid top = new TopPid("10.0.67.14", "root", "rsapm");
		for (String key : top.map.keySet()) {
			System.out.println(key + " " + top.map.get(key));
		}
	}

}

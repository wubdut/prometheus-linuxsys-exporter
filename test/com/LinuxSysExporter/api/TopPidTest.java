package com.LinuxSysExporter.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TopPidTest extends ProbeApi {

	@Test
	public void test() throws IOException {
		TopPid top = new TopPid();
		for (String key : top.map.keySet()) {
			System.out.println(key + " " + top.map.get(key));
		}
	}

}

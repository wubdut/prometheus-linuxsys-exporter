package com.LinuxSysExporter.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class NethogsPidTest {

	@Test
	public void test() throws IOException {
		NethogsPid nethogsPid = new NethogsPid();
		for (String key : nethogsPid.map.keySet()) {
			System.out.println(key + ": " + nethogsPid.map.get(key));
		}
	}

}

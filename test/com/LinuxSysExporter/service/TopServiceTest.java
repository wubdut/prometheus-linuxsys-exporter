package com.LinuxSysExporter.service;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TopServiceTest {

	@Test
	public void test() throws IOException {
		TopService topService = new TopService("10.0.67.14", "root", "rsapm");
		topService.register("app").run();
		for (String key : topService.gaugeMap.keySet()) {
			System.out.println(key + " " + topService.gaugeMap.get(key).get());
		}
	}

}

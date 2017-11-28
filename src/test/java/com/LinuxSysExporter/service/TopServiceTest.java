package com.LinuxSysExporter.service;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TopServiceTest {

	@Test
	public void test() throws IOException {
		ProbeService topService = new ProbeService("10.0.67.14", "root", "rsapm");
		topService.register("topApp").run();
		for (String key : topService.gaugeMap.keySet()) {
			System.out.println(key + " " + topService.gaugeMap.get(key).get());
		}
	}

}

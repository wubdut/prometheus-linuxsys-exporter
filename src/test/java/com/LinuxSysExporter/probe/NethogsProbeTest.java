package com.LinuxSysExporter.probe;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class NethogsProbeTest {

	@Test
	public void test() throws IOException {
		NethogsProbe probe = new NethogsProbe();
		for (String key : probe.map.keySet()) {
			System.out.println(key + ": " + probe.map.get(key));
		}
	}

}

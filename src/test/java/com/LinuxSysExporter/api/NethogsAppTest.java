package com.LinuxSysExporter.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class NethogsAppTest {

	@Test
	public void test() throws IOException {
		NethogsApp nethogsApp = new NethogsApp();
		for (String key : nethogsApp.map.keySet()) {
			System.out.println(key + ": " + nethogsApp.map.get(key));
		}
	}

}

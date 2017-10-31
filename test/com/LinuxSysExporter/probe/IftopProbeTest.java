package com.LinuxSysExporter.probe;

import static org.junit.Assert.*;

import org.junit.Test;

public class IftopProbeTest {

	@Test
	public void test() {
		IftopProbe iftop = new IftopProbe("39.108.214.220", "root", "Abc123+-*/");
		System.out.println("++++++++++++++++++++++++++++++++++");
		for (String key : iftop.map.keySet()) {
			System.out.println(key + "    " + iftop.map.get(key));
		}
		
	}

}

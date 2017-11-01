package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;

import com.LinuxSysExporter.probe.IotopProbe;
import com.LinuxSysExporter.probe.TopProbe;

public class IotopPid extends ProbeApi {
	
	public IotopPid() throws IOException {
		map = new HashMap<String, Double> ();
		IotopProbe iotopProbe = new IotopProbe();
		getData(iotopProbe);
	}
	
	public void getData(IotopProbe iotopProbe) {
		for (String key : iotopProbe.map.keySet()) {
			String[] keys = key.split("_");
			map.put(keys[0] + "_diskio_" + keys[1], iotopProbe.map.get(key));
		}
	}
}

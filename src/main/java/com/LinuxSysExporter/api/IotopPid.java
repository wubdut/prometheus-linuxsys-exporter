package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;

import com.LinuxSysExporter.probe.IotopProbe;
import com.LinuxSysExporter.probe.TopProbe;

public class IotopPid extends TopApi {

	private IotopPid() {}
	
	public IotopPid(String ip, String userName, String userPwd) throws IOException {
		map = new HashMap<String, Double> ();
		IotopProbe iotopProbe = new IotopProbe(ip, userName, userPwd);
		getData(iotopProbe);
	}
	
	public void getData(IotopProbe iotopProbe) {
		for (String key : iotopProbe.map.keySet()) {
			String[] keys = key.split("_");
			map.put(keys + "_cpu_" + keys[1], iotopProbe.map.get(key));
		}
	}
}

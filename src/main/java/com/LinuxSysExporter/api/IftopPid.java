package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;

import com.LinuxSysExporter.probe.IftopProbe;
import com.LinuxSysExporter.probe.IotopProbe;

public class IftopPid extends TopApi {

	private IftopPid() {}
	
	public IftopPid(String ip, String userName, String userPwd) throws IOException {
		map = new HashMap<String, Double> ();
		IftopProbe iftopProbe = new IftopProbe(ip, userName, userPwd);
		getData(iftopProbe);
	}
	
	public void getData(IftopProbe iftopProbe) {
		for (String key : iftopProbe.map.keySet()) {
			String[] keys = key.split("_");
			map.put(keys[0] + "_network_" + keys[1], iftopProbe.map.get(key));
		}
	}
}

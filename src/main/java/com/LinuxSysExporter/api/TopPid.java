package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.probe.TopProbe;

public class TopPid extends TopApi{
	
	private TopPid() {}
	
	public TopPid(String ip, String userName, String userPwd) throws IOException {
		map = new HashMap<String, Double> ();
		TopProbe topProbe = new TopProbe(ip, userName, userPwd);
		getData(topProbe);
	}
	
	public void getData(TopProbe topProbe) {
		for (String key : topProbe.map.keySet()) {
			String[] keys = key.split("_");
			map.put(keys[0] + "_cpu_" + keys[1], topProbe.map.get(key).first);
			map.put(keys[0] + "_mem_" + keys[1], topProbe.map.get(key).second);
		}
	}
	
}

package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.probe.TopProbe;

public class TopPid extends ProbeApi{
	
	public TopPid() throws IOException {
		map = new HashMap<String, Double> ();
		TopProbe topProbe = new TopProbe();
		getData(topProbe);
	}
	
	public void getData(TopProbe topProbe) {
		for (String key : topProbe.map.keySet()) {
			String[] keys = key.split("_");
			if (topProbe.map.get(key).first > 0)
				map.put(keys[0] + "_cpu_" + keys[1], topProbe.map.get(key).first);
			if (topProbe.map.get(key).second > 0)
				map.put(keys[0] + "_mem_" + keys[1], topProbe.map.get(key).second);
		}
	}
	
}

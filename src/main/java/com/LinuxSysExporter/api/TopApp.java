package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.probe.TopProbe;

public class TopApp extends TopApi {
	
	private TopApp() {}
	
	public TopApp(String ip, String userName, String userPwd) throws IOException {
		map = new HashMap<String, Double>();
		TopPid topPid = new TopPid(ip, userName, userPwd);
		getData(topPid);
	}
	
	private void getData(TopPid topPid) {
		for (String key : topPid.map.keySet()) {
			double tmp = topPid.map.get(key);
			String[] keys = key.split("_");
			String key_tmp = keys[0] + "_" + keys[1];
			if (map.containsKey(key_tmp)) {
				double pre = map.get(key_tmp);
				map.put(key_tmp, pre+tmp);
			} else {
				map.put(key_tmp, tmp);
			}
		}
	}
	
}

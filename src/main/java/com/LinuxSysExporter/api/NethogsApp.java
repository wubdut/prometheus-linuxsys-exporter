package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;

public class NethogsApp extends ProbeApi {

	public NethogsApp() throws IOException {
		map = new HashMap<String, Double>();
		NethogsPid nethogsPid = new NethogsPid();
		getData(nethogsPid);
	}
	
	private void getData(NethogsPid nethogsPid) {
		for (String key : nethogsPid.map.keySet()) {
			double tmp = nethogsPid.map.get(key);
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

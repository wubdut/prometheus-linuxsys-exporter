package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;

public class IftopApp extends TopApi {

	private IftopApp() {}
	
	public IftopApp(String ip, String userName, String userPwd) throws IOException {
		map = new HashMap<String, Double>();
		IftopPid iftopPid = new IftopPid(ip, userName, userPwd);
		getData(iftopPid);
	}
	
	private void getData(IftopPid iftopPid) {
		for (String key : iftopPid.map.keySet()) {
			double tmp = iftopPid.map.get(key);
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

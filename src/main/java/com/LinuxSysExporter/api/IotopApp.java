package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;

public class IotopApp extends TopApi {

	private IotopApp() {}
	
	public IotopApp(String ip, String userName, String userPwd) throws IOException {
		map = new HashMap<String, Double>();
		IotopPid iotopPid = new IotopPid(ip, userName, userPwd);
		getData(iotopPid);
	}
	
	private void getData(IotopPid iotopPid) {
		for (String key : iotopPid.map.keySet()) {
			double tmp = iotopPid.map.get(key);
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

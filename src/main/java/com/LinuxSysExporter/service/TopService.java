package com.LinuxSysExporter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.LinuxSysExporter.api.IotopPid;
import com.LinuxSysExporter.api.TopApi;
import com.LinuxSysExporter.api.TopApp;
import com.LinuxSysExporter.api.TopPid;

import io.prometheus.client.Gauge;

public class TopService {

	public Map<String, Gauge> gaugeMap;
	private String ip;
	private String userName;
	private String userPwd;
	private List<TopApi> topApiList;
	
	private TopService() {}
	
	public TopService(String ip, String userName, String userPwd) {
		gaugeMap = new HashMap<String, Gauge>();
		this.ip = ip;
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	public TopService register(String... api) throws IOException {
		topApiList = new ArrayList<TopApi> ();
		for (int i = 0; i < api.length; i++) {
			if (api.equals("topApp")) {
				topApiList.add(new TopApp(ip, userName, userPwd));
			} else if (api.equals("topPid")){
				topApiList.add(new TopPid(ip, userName, userPwd));
			} else if (api.equals("iotopApp")) {
				topApiList.add(new IotopPid(ip, userName, userPwd));
			} else if (api.equals("iotopPid")) {
				topApiList.add(new IotopPid(ip, userName, userPwd));
			}
			else {
				System.out.println("\nThere is not api for your register\n");
			}
		}
		return this;
	}
	
	public void run() throws IOException {
		for (int i = 0; i < topApiList.size(); i++) {
			buildNewGauge(topApiList.get(i));
			for (String key : gaugeMap.keySet()) {
				String[] keys = key.split("_");
				String key_tmp = keys[1] + "_" + keys[2];
				if (topApiList.get(i).map.keySet().contains(key_tmp)) {
					gaugeMap.get(key).set(topApiList.get(i).map.get(key_tmp));
				} else {
					gaugeMap.get(key).set(0.0);
				}
			}
		}
	}
	
	private void buildNewGauge(TopApi topApi) {
		for (String key : topApi.map.keySet()) {
			String key_tmp = "linuxsys_" + key;
			if (!gaugeMap.containsKey(key_tmp)) {
				gaugeMap.put(key_tmp, Gauge.build()
						.name(key_tmp)
						.help("This is " + key_tmp)
						.register());
			}
		}
	}
	
}

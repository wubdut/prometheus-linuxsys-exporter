package com.LinuxSysExporter.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.api.TopApi;
import com.LinuxSysExporter.api.TopApp;
import com.LinuxSysExporter.api.TopPid;

import io.prometheus.client.Gauge;

public class TopService {

	private Map<String, Gauge> gaugeMap;
	private String ip;
	private String userName;
	private String userPwd;
	private TopApi topApi;
	
	private TopService() {}
	
	public TopService(String ip, String userName, String userPwd) {
		gaugeMap = new HashMap<String, Gauge>();
		this.ip = ip;
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	public void register(String api) throws IOException {
		if (api.equals("app")) {
			topApi = new TopApp(ip, userName, userPwd);
		} else if (api.equals("pid")){
			topApi = new TopPid(ip, userName, userPwd);
		} else {
			System.out.println("\nThere is not api for your register\n");
		}
	}
	
	public void run() throws IOException {
		buildNewGauge(topApi);
		for (String key : gaugeMap.keySet()) {
			String[] keys = key.split("_");
			String key_tmp = keys[1] + "_" + keys[2];
			if (topApi.map.keySet().contains(key_tmp)) {
				gaugeMap.get(key).set(topApi.map.get(key_tmp));
			} else {
				gaugeMap.get(key).set(topApi.map.get(key_tmp));
			}
		}
	}
	
	private void buildNewGauge(TopApi topApi) {
		for (String key : topApi.map.keySet()) {
			String key_tmp = "linuxsys_" + key;
			if (!gaugeMap.containsKey(key_tmp)) {
				gaugeMap.put(key_tmp, Gauge.build()
						.name(key_tmp)
						.help("This + is " + key_tmp)
						.register());
			}
		}
	}
	
}

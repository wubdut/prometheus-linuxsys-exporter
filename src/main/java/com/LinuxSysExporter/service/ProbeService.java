package com.LinuxSysExporter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.LinuxSysExporter.api.IotopApp;
import com.LinuxSysExporter.api.IotopPid;
import com.LinuxSysExporter.api.NethogsApp;
import com.LinuxSysExporter.api.NethogsPid;
import com.LinuxSysExporter.api.ProbeApi;
import com.LinuxSysExporter.api.TopApp;
import com.LinuxSysExporter.api.TopPid;
import com.LinuxSysExporter.basic.CheckFunction;

import io.prometheus.client.Gauge;

public class ProbeService {

	public Map<String, Gauge> gaugeMap;
	private String ip;
	private String userName;
	private String userPwd;
	private List<ProbeApi> apiList;
	
	private ProbeService() {}
	
	public ProbeService(String ip, String userName, String userPwd) {
		gaugeMap = new HashMap<String, Gauge>();
		this.ip = ip;
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	public ProbeService register(String... api) throws IOException {
		apiList = new ArrayList<ProbeApi> ();
		for (int i = 0; i < api.length; i++) {
			if (api[i].equals("topApp")) {
				apiList.add(new TopApp());
			} else if (api[i].equals("topPid")){
				apiList.add(new TopPid());
			} else if (api[i].equals("iotopApp")) {
				apiList.add(new IotopApp());
			} else if (api[i].equals("iotopPid")) {
				apiList.add(new IotopPid());
			} else if (api[i].equals("nethogsApp")) {
				apiList.add(new NethogsApp());
			} else if (api[i].equals("nethogsPid")) {
				apiList.add(new NethogsPid());
			} else {
				System.out.println("\nThere is no api for your register\n");
			}
		}
		return this;
	}
	
	public void run() throws IOException {
		setNewGauge();
		setOldGauge();
	}
	
	private void setNewGauge() {
		for (int i = 0; i < apiList.size(); i++) {
			for (String key : apiList.get(i).map.keySet()) {
				String key_tmp = "linuxsys_" + key;
				if (!gaugeMap.containsKey(key_tmp)) {
					gaugeMap.put(key_tmp, Gauge.build()
							.name(key_tmp)
							.help("This is " + key_tmp)
							.register());
				}
				gaugeMap.get(key_tmp).set(apiList.get(i).map.get(key));
			}
		}
	}
	
	private void setOldGauge() {
		
		for (String key : gaugeMap.keySet()) {
			String[] keys = key.split("_");
			String key_tmp = keys[1] + "_" + keys[2];
			if (!CheckFunction.inApiList(apiList, key_tmp)) {
				gaugeMap.get(key).set(0.0);
			}
		}
	}
	
}

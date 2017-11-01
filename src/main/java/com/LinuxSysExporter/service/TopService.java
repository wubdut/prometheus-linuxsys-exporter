package com.LinuxSysExporter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.LinuxSysExporter.api.IftopApp;
import com.LinuxSysExporter.api.IftopPid;
import com.LinuxSysExporter.api.IotopApp;
import com.LinuxSysExporter.api.IotopPid;
import com.LinuxSysExporter.api.TopApi;
import com.LinuxSysExporter.api.TopApp;
import com.LinuxSysExporter.api.TopPid;
import com.LinuxSysExporter.basic.CheckFunction;

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
			if (api[i].equals("topApp")) {
				topApiList.add(new TopApp(ip, userName, userPwd));
			} else if (api[i].equals("topPid")){
				topApiList.add(new TopPid(ip, userName, userPwd));
			} else if (api[i].equals("iotopApp")) {
				topApiList.add(new IotopApp(ip, userName, userPwd));
			} else if (api[i].equals("iotopPid")) {
				topApiList.add(new IotopPid(ip, userName, userPwd));
			} else if (api[i].equals("iftopApp")) {
				topApiList.add(new IftopApp(ip, userName, userPwd));
			} else if (api[i].equals("iftopPid")) {
				topApiList.add(new IftopPid(ip, userName, userPwd));
			}
			else {
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
		for (int i = 0; i < topApiList.size(); i++) {
//			System.out.println("swtNewGauge");
			for (String key : topApiList.get(i).map.keySet()) {
				String key_tmp = "linuxsys_" + key;
				if (!gaugeMap.containsKey(key_tmp)) {
					gaugeMap.put(key_tmp, Gauge.build()
							.name(key_tmp)
							.help("This is " + key_tmp)
							.register());
				}
				gaugeMap.get(key_tmp).set(topApiList.get(i).map.get(key));
			}
		}
	}
	
	private void setOldGauge() {
//		System.out.println("swtOldGauge");
		for (String key : gaugeMap.keySet()) {
			String[] keys = key.split("_");
			String key_tmp = keys[1] + "_" + keys[2];
			if (!CheckFunction.inApiList(topApiList, key_tmp)) {
				gaugeMap.get(key).set(0.0);
			}
		}
	}
	
}

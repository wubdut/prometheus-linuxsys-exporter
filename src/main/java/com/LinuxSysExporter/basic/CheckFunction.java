package com.LinuxSysExporter.basic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.LinuxSysExporter.api.TopApi;

public class CheckFunction {

	public static boolean inApiList(List<TopApi> apiList, String key) {
		for (int i = 0; i < apiList.size(); i++) {
			if (apiList.get(i).map.keySet().contains(key))
				return true;
		}
		return false;
	}
}

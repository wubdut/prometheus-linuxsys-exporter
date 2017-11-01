package com.LinuxSysExporter.api;

import java.io.IOException;
import java.util.HashMap;
import com.LinuxSysExporter.probe.NethogsProbe;

public class NethogsPid extends ProbeApi {

	public NethogsPid() throws IOException {
		map = new HashMap<String, Double> ();
		NethogsProbe nethogsProbe = new NethogsProbe();
		getData(nethogsProbe);
	}
	
	public void getData(NethogsProbe nethogsProbe) {
		for (String key : nethogsProbe.map.keySet()) {
			String[] keys = key.split("_");
			map.put(keys[0] + "_network_" + keys[1], nethogsProbe.map.get(key));
		}
	}
}

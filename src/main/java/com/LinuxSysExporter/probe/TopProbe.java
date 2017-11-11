package com.LinuxSysExporter.probe;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.basic.DataParse;
import com.LinuxSysExporter.basic.Pair;
import com.LinuxSysExporter.basic.RemoteExecute;
import com.LinuxSysExporter.basic.SpecStrFilter;

public class TopProbe {

	public RemoteExecute rpc;
	public Map<String, Pair<Double, Double>> map;
	
	public TopProbe() throws IOException {
		rpc = RemoteExecute.getInstance();
		map = new HashMap<String, Pair<Double, Double>> ();
		getData();
	}
	
	private void getData() throws IOException {
		String command = "top -b -n 1";
		String result = rpc.execute(command);
		if (result == null || result.equals("")) return;
		String[] lines =  result.split("\n");
		
		for (int i = 7; i < lines.length; i++) {
			String[] line = lines[i].trim().split("[ ]+");
			String pid = line[0];
			String cpu = line[8];
			String mem = line[9];
			String name = line[11];
			
			double cpu_val = DataParse.str2double(cpu);
			double mem_val = DataParse.str2double(mem);
			if (Math.max(cpu_val, mem_val) > 0.0) {
				String key = DataParse.commandParse(name) + "_" +pid;
				map.put(key, new Pair<Double, Double> (cpu_val, mem_val));
			}
		}
	}
	
}

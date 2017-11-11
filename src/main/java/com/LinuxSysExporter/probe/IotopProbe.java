package com.LinuxSysExporter.probe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.LinuxSysExporter.basic.DataParse;
import com.LinuxSysExporter.basic.RemoteExecute;
import com.LinuxSysExporter.basic.SpecStrFilter;

public class IotopProbe {

	private RemoteExecute rpc;
	public Map<String, Double> map;
	
	public IotopProbe() throws IOException {
		rpc = RemoteExecute.getInstance();
		map = new HashMap<String, Double>();
		getData();
	}
	
	private void getData() throws IOException {
		String command = "iotop -P -b -n 1";
		String result = rpc.execute(command);
		if (result == null || result.equals("")) return;
		String[] lines =  result.split("\n");
		
		for (int i = 2; i < lines.length; i++) {
			String[] line = lines[i].trim().split("[ ]+");
			String pid = line[0];
			double io_val = DataParse.str2double(line[9]);
			String name = line[11];
			
			if (io_val > 0) {
				String key = DataParse.commandParse(name) + "_" + pid;
				map.put(key, io_val);
			}
		}
	}
	
}

package com.LinuxSysExporter.probe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.LinuxSysExporter.basic.DataParse;
import com.LinuxSysExporter.basic.RemoteExecuteCommand;

public class IotopProbe {

	private RemoteExecuteCommand rpc;
	public Map<String, Double> map;
	
	private IotopProbe() {}
	
	public IotopProbe(String ip, String userName, String userPwd) throws IOException {
		rpc = new RemoteExecuteCommand(ip, userName, userPwd);
		map = new HashMap<String, Double>();
		getData();
	}
	
	private void getData() throws IOException {
		String command = "iotop -P -b -n 1";
		String result = rpc.execute(command);
		String[] lines =  result.split("\n");
		DataParse dataParse = new DataParse();
		
		for (int i = 2; i < lines.length; i++) {
			String[] line = lines[i].trim().split("[ ]+");
			String pid = line[0];
			double io_val = dataParse.str2double(line[9]);
			String name = line[11].replaceAll("\\[|\\]", "").split("/")[0];
			
			if (io_val > 0) {
				String key = name + "_" + pid;
				map.put(key, io_val);
//				System.out.println(name + " " + pid + " " + io_val);
			}
		}
	}
	
}

package com.LinuxSysExporter.probe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.basic.DataParse;
import com.LinuxSysExporter.basic.RemoteExecute;

public class NethogsProbe {

	private RemoteExecute rpc;
	public Map<String, Double> map;
	
	public NethogsProbe() throws IOException {
		rpc = RemoteExecute.getInstance();
		map = new HashMap<String, Double>();
		getData();
	}
	
	private void getData() throws IOException {
		String command = "nethogs -d 1 -t -c 2";
		String result = rpc.execute(command);
		if (result == null || result.equals("")) return;
		String[] lines =  result.split("\n");
		
		for (int i = 8; i < lines.length-1; i++) {
			String[] line = lines[i].trim().split("\t");
			
			String[] cmdInfo = line[0].trim().split("/");
			int len = cmdInfo.length;
			if (len != 3) continue;
			String name = cmdInfo[len-3];
			String pid = cmdInfo[len-2];
			
			double sent = DataParse.str2double(line[1]);
			double received = DataParse.str2double(line[2]);
			
			String key = DataParse.commandParse(name) + "_" + pid;
			map.put(key, sent + received);
			
//			System.out.println(i + ": [" + lines[i] + "]");
//			System.out.println(name + "  " + pid + "  " + "  " + sent +"  " + received );
//			System.out.println("len: " + line.length);
		}
	}
}

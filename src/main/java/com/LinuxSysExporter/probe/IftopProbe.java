package com.LinuxSysExporter.probe;

import java.util.HashMap;
import java.util.Map;

import com.LinuxSysExporter.basic.DataParse;
import com.LinuxSysExporter.basic.LsofParse;
import com.LinuxSysExporter.basic.RemoteExecuteCommand;
import com.LinuxSysExporter.basic.SpecStrFilter;

public class IftopProbe {

	private RemoteExecuteCommand rpc;
	public Map<String, Double> map;
	
	private IftopProbe() {}
	
	public IftopProbe(String ip, String userName, String userPwd) {
		rpc = new RemoteExecuteCommand(ip, userName, userPwd);
		map = new HashMap<String, Double>();
		getData();
	}
	
	private void getData() {
		String command = "iftop -Pp -Nn -B -t -s 2";
		String result = rpc.execute(command);
		if (result == null || result.equals("")) return;
		String[] lines =  result.split("[-]+\n")[1].split("\n");
	
		for (int i = 0; i < lines.length/2; i++) {
//			System.out.println(lines[2*i]);
//			System.out.println(lines[2*i+1]);
			String[] line1 = lines[2*i].trim().split("[ ]+");
			String[] line2 = lines[2*i+1].trim().split("[ ]+");
			String source = line1[1];
//			String terminal = line2[0];
//			System.out.println("source : " + source);
//			System.out.println("terminal : " + terminal);
			String tx = line1[3];
			String rx = line2[2];
//			System.out.println(tx + "    " + rx);
			String key = LsofParse.getCommand(rpc, source);
			if (key != null) {
				map.put(key, DataParse.unit2double(tx)+DataParse.unit2double(rx));
			}
		}
	}
	
}

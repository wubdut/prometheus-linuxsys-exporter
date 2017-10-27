package com.LinuxSysExporter.probe;

import java.io.IOException;

import com.LinuxSysExporter.basic.RemoteExecuteCommand;

public class IotopProbe {

	public RemoteExecuteCommand rpc;
	
	public IotopProbe() {}
	
	public IotopProbe(String ip, String userName, String userPwd) {
		rpc = new RemoteExecuteCommand(ip, userName, userPwd);
	}
	
	public void getTopData() throws IOException {
		String command = "iotop -b -n 1";
		String result = rpc.execute(command);
		
		String[] top_line =  result.split("\n");
		
//		for (int i = 6; i < top_line.length; i++) {
//			System.out.println(top_line[i]);
//		}
		
		System.out.println(top_line[6].trim());
		
		for (int i = 7; i < top_line.length; i++) {
			String[] line = top_line[i].trim().split("[ ]+");
			for (int j = 0; j < line.length; j++) {
				System.out.print(line[j] + "  ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws IOException {
		IotopProbe iotop = new IotopProbe("10.0.67.14", "root", "rsapm");
		
		iotop.getTopData();
	}
}

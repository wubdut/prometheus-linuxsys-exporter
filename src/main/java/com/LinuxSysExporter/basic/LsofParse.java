package com.LinuxSysExporter.basic;

public class LsofParse {

	public static String getCommand(RemoteExecuteCommand rpc, String source) {
		System.out.println(source);
		String port = source.split(":")[1];
		String command = "lsof -P -n -i:" + port + "|grep " + source;
		String result = rpc.execute(command);
		if (result == null || result.equals("")) return null;
		String[] lines = result.split("\n");
		if (lines.length < 2) return null;
		String[] line = lines[1].trim().split("[ ]+");
		String name = line[0];
		String pid = line[1];
		return DataParse.commandParse(name) + "_" + pid;
	}
}

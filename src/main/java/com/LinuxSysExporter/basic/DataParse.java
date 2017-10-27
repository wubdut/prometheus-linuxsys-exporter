package com.LinuxSysExporter.basic;

public class DataParse {

	public DataParse() {}
	
	public double str2double(String x) {
		double y = Double.parseDouble(x);
		return y;
	}
	
	public int str2int(String x) {
		int y = Integer.parseInt(x);
		return y;
	}
}

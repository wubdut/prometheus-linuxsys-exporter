package com.LinuxSysExporter.basic;

public class DataParse {

	public DataParse() {}
	
	public static double str2double(String x) {
		double y = Double.parseDouble(x);
		return y;
	}
	
	public static int str2int(String x) {
		int y = Integer.parseInt(x);
		return y;
	}
	
	public static double unit2double(String x) {
		int pos = 0;
		for (; pos < x.length(); pos++) {
			if (checkUpLetter(x.charAt(pos))) {
				break;
			}
		}
		if (pos == 0) return 0;
		String val = x.substring(0, pos);
		String unit = x.substring(pos);
		double res = Double.parseDouble(val);
		if (unit.equals("KB")) {
			res *= 1E3;
		} else if (unit.equals("MB")) {
			res *= 1E6;
		} else if (unit.equals("GB")) {
			res *= 1E9;
		} else if (unit.equals("TB")) {
			res *= 1E12;
		}
		return res;
	}
	
	public static String commandParse(String command) {
		return SpecStrFilter.replaceSpecStr(command, " ").trim().split(" ")[0];
	}
	
	private static boolean checkUpLetter(char ch) {
		return (ch >= 'A' && ch <= 'Z' );
	}
	
	public static void main(String[] args) {
		String str = "B";
		System.out.println(unit2double(str));
	}
	
//	public static int backIndexLine(String[] lines, String line) {
//		int pos = lines.length - 1;
//		for (; pos > -1; pos--) {
//			if (line.equals(lines[pos]))
//				continue;
//		}
//		return pos;
//	}
}

package network;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NetworkTable {
	
	private static ArrayList<List<String>> rows = new ArrayList<List<String>>();
	private static List<String> header;
	private static LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();

	public NetworkTable() {
		super();
		attributes = new LinkedHashMap<String, String>();
	}
	
	public static void addAttribute(String header, String attribute) {
		attributes.put(header, attribute);
	}
	
	public static LinkedHashMap<String, String> getAttributes() {
		return attributes;
	}

	public static void setAttributes(LinkedHashMap<String, String> attributes) {
		NetworkTable.attributes = attributes;
	}

	public static ArrayList<List<String>> getRows() {
		return rows;
	}

	public static void setRows(ArrayList<List<String>> rows) {
		NetworkTable.rows = rows;
	}

	public static List<String> getHeader() {
		return header;
	}

	public static void setHeader(List<String> header) {
		NetworkTable.header = header;
	}

	@Override
	public String toString() {
		return "NetworkTable [rows=" + rows + ", header=" + header + "]";
	}	
	
}

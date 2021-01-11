package intermediate;

import java.util.ArrayList;

import enumerations.NETWORK_FORMAT;

public class MapSettings {
	private static Boolean csvHeader;
	private static String columnSeparator;
	private static String dateformat;
	private static String attributeLabel;
	private static String fileSufix;
	private static ArrayList<NETWORK_FORMAT> networkFormat;
	private static Boolean isDirectedNetwork;

	public MapSettings() {
		networkFormat = new ArrayList<NETWORK_FORMAT>();
	}
	
	public static Boolean getCsvHeader() {
		return csvHeader;
	}
	
	public static void setCsvHeader(Boolean csvHeader) {
		MapSettings.csvHeader = csvHeader;
	}
	
	public static void setCsvHeader(String csvHeader) {
		MapSettings.csvHeader = Boolean.valueOf(csvHeader.toLowerCase());
	}
	
	public static String getColumnSeparator() {
		return columnSeparator;
	}
	
	public static void setColumnSeparator(String columnSeparator) {
		MapSettings.columnSeparator = columnSeparator;
	}
	
	public static String getDateformat() {
		return dateformat;
	}
	
	public static void setDateFormat(String dateformat) {
		MapSettings.dateformat = dateformat;
	}
	
	public static String getAttributeLabel() {
		return attributeLabel;
	}
	
	public static void setAttributeLabel(String attributeLabel) {
		MapSettings.attributeLabel = attributeLabel;
	}
	
	public static String getFileSufix() {
		return fileSufix;
	}
	
	public static void setFileSufix(String fileSufix) {
		MapSettings.fileSufix = fileSufix;
	}
	
	public static ArrayList<NETWORK_FORMAT> getNetworkFormat() {
		return networkFormat;
	}

	public static void setNetworkFormat(ArrayList<NETWORK_FORMAT> networkFormat) {
		MapSettings.networkFormat = networkFormat;
	}

	public static void setNetworkFormat(String networkFormat) {
		if(networkFormat.equals(NETWORK_FORMAT.GDF.toString())) {
			MapSettings.networkFormat.add(NETWORK_FORMAT.GDF);
		} else if (networkFormat.equals(NETWORK_FORMAT.NET.toString())){
			MapSettings.networkFormat.add(NETWORK_FORMAT.NET);
		} else {
			throw new Error("Type of network not encountered: " + networkFormat);
		}
	}
	
	public static Boolean isDirectedNetwork() {
		return isDirectedNetwork;
	}
	
	public static void setIsDirectedNetwork(Boolean isDirectedNetwork) {
		MapSettings.isDirectedNetwork = isDirectedNetwork;
	}
	
	public static void setIsDirectedNetwork(String isDirectedNetwork) {
		MapSettings.isDirectedNetwork = Boolean.valueOf(isDirectedNetwork.toLowerCase());
	}

	@Override
	public String toString() {
		return "MapSettings [csvHeader=" + csvHeader + ", columnSeparator=" + columnSeparator + ", dateformat="
				+ dateformat + ", attributeLabel=" + attributeLabel + ", fileSufix=" + fileSufix + ", networkFormat="
				+ networkFormat + ", isDirectedNetwork=" + isDirectedNetwork + "]";
	}	
	
}

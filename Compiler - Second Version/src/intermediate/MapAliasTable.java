package intermediate;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import enumerations.TYPE_OPERAND;

import java.util.AbstractMap.SimpleEntry;

public class MapAliasTable {
	private static LinkedHashMap<String, Entry<TYPE_OPERAND, String>> columnTable;

	public MapAliasTable() {
		MapAliasTable.columnTable = new LinkedHashMap<String, Entry<TYPE_OPERAND, String>>();
	}
	
	public static void addColumnEntryToTable(String aliasTableColumnHeader, TYPE_OPERAND type, String csvColumnHeader) {
		MapAliasTable.columnTable.put(aliasTableColumnHeader, new SimpleEntry<TYPE_OPERAND, String>(type, csvColumnHeader));
	}
	
	public static void addColumnEntryToTable(String aliasTableColumnHeader, String type, String csvColumnHeader) {
		String csvParsed = csvColumnHeader.substring(1, csvColumnHeader.length() - 1);
		if(type.equals(TYPE_OPERAND.BOOLEAN.toString())) {
			MapAliasTable.columnTable.put(csvParsed, new SimpleEntry<TYPE_OPERAND, String>(TYPE_OPERAND.BOOLEAN, aliasTableColumnHeader));
		} else if(type.equals(TYPE_OPERAND.NUMBER.toString())) {
			MapAliasTable.columnTable.put(csvParsed, new SimpleEntry<TYPE_OPERAND, String>(TYPE_OPERAND.NUMBER, aliasTableColumnHeader));
		} else if(type.equals(TYPE_OPERAND.TEXT.toString())) {
			MapAliasTable.columnTable.put(csvParsed, new SimpleEntry<TYPE_OPERAND, String>(TYPE_OPERAND.TEXT, aliasTableColumnHeader));
		} else if(type.equals(TYPE_OPERAND.DATE.toString())) {
			MapAliasTable.columnTable.put(csvParsed, new SimpleEntry<TYPE_OPERAND, String>(TYPE_OPERAND.DATE, aliasTableColumnHeader));
		} else {
			throw new Error("Variable type doesn't exists: " + type);
		}
			
	}
	
	public static Boolean verifyIfAliasExistsOnTable(String csvTableColumnHeader) {
		return columnTable.containsKey(csvTableColumnHeader);
	}
	
	
	public static Boolean verifyIfAliasKeyExistsOnTable(String aliasTableColumn) {
		for(Entry<TYPE_OPERAND, String> value : columnTable.values()) {
			if(value.getValue().equals(aliasTableColumn)) {
				return true;
			}
		}
		return false;
	}
	
	public static Entry<TYPE_OPERAND, String> getAliasTableColumnHeader(String aliasTableColumn) {
		for(Entry<TYPE_OPERAND, String> value : columnTable.values()) {
			if(value.getValue().equals(aliasTableColumn)) {
				return value;
			}
		}
		return null;
	}
		
	public static String getAliasValueOnTable(String csvTableColumnHeader) {
		return columnTable.get(csvTableColumnHeader).getValue();
	}
	
	public static TYPE_OPERAND getAliasTableColumnHeaderType(String aliasTableColumn) {
		for(Entry<TYPE_OPERAND, String> value : columnTable.values()) {
			if(value.getValue().equals(aliasTableColumn)) {
				return value.getKey();
			}
		}
		return null;
	}
	
	public static void deleteColumnEntrieToTable(String aliasTableColumnHeader) {
		MapAliasTable.columnTable.remove(aliasTableColumnHeader);
	}
	
	public static LinkedHashMap<String, Entry<TYPE_OPERAND, String>> getColumnTable() {
		return columnTable;
	}

	public static void setColumnTable(LinkedHashMap<String, Entry<TYPE_OPERAND, String>> columnTable) {
		MapAliasTable.columnTable = columnTable;
	}

	@Override
	public String toString() {
		return "MapAliasTable [columnTable=" + columnTable + "]";
	}
	
}

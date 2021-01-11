package intermediate;

import java.util.ArrayList;

import elementars.Table;

public class MapDataFrame {
	private static ArrayList<DataFrameComponent> dataFrameComponentList;
	private static Table variableTableList;
	
	public MapDataFrame() {
		MapDataFrame.dataFrameComponentList = new ArrayList<DataFrameComponent>();
		MapDataFrame.variableTableList = new Table();
	}
	
	public void addDataFrameComponent(DataFrameComponent dataFrameComponent) {
		dataFrameComponentList.add(dataFrameComponent);
	}
	
	public static ArrayList<DataFrameComponent> getDataFrameComponentList() {
		return dataFrameComponentList;
	}

	public static Table getVariableTable() {
		return variableTableList;
	}

	@Override
	public String toString() {
		return "MapDataFrame [dataFrameComponentList=" + dataFrameComponentList + ", variableTable=" + variableTableList
				+ "]";
	}
}

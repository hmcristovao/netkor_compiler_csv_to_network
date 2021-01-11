package network;

import intermediate.MapAdjacencyList;
import intermediate.MapAliasTable;
import intermediate.MapBipartiteProjection;
import intermediate.MapDataFrame;
import intermediate.MapRowsDeleting;
import intermediate.MapSettings;

public abstract class NetworkSections {
	private MapSettings settings;
	private MapAliasTable aliasTable;
	private MapDataFrame dataFrame;
	private MapRowsDeleting rowsDeleting;
	private MapAdjacencyList adjacencyList;
	private MapBipartiteProjection bipartiteProjection;
	
	public NetworkSections() {}

	public MapSettings getSettings() {
		return settings;
	}

	public void setSettings(MapSettings settings) {
		this.settings = settings;
	}

	public MapAliasTable getAliasTable() {
		return aliasTable;
	}

	public void setAliasTable(MapAliasTable aliasTable) {
		this.aliasTable = aliasTable;
	}

	public MapDataFrame getDataFrame() {
		return dataFrame;
	}

	public void setDataFrame(MapDataFrame dataFrame) {
		this.dataFrame = dataFrame;
	}

	public MapRowsDeleting getRowsDeleting() {
		return rowsDeleting;
	}

	public void setRowsDeleting(MapRowsDeleting rowsDeleting) {
		this.rowsDeleting = rowsDeleting;
	}

	public MapAdjacencyList getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(MapAdjacencyList adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public MapBipartiteProjection getBipartiteProjection() {
		return bipartiteProjection;
	}

	public void setBipartiteProjection(MapBipartiteProjection bipartiteProjection) {
		this.bipartiteProjection = bipartiteProjection;
	}
	
}

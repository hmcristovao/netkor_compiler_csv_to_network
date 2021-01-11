package intermediate;

import java.util.ArrayList;

import elementars.Symbol;

public class MapAdjacencyList {
	private static ArrayList<Symbol> adjacencyVariables;
	
	public MapAdjacencyList() {
		MapAdjacencyList.adjacencyVariables =  new ArrayList<Symbol>();
	}
	
	public static void addAdjacencyVariable(Symbol expression) {
		adjacencyVariables.add(expression);
	}

	public static ArrayList<Symbol> getAdjacencyVariables() {
		return adjacencyVariables;
	}

	public static void setAdjacencyVariables(ArrayList<Symbol> adjacencyVariables) {
		MapAdjacencyList.adjacencyVariables = adjacencyVariables;
	}

	@Override
	public String toString() {
		return "MapAdjacencyList [adjacencyVariables=" + adjacencyVariables + "]";
	}

}

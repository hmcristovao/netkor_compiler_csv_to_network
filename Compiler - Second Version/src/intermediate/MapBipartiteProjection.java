package intermediate;

import java.util.ArrayList;

import elementars.Symbol;

public class MapBipartiteProjection {
	
	private static ArrayList<BipartiteProjection> bipartiteProjectionList;
	
	public MapBipartiteProjection() {
		MapBipartiteProjection.bipartiteProjectionList = new ArrayList<BipartiteProjection>();
	}
	
	public void addBipartiteProjection(ArrayList<Symbol> beforeAt, ArrayList<Symbol> afterAt) {
		bipartiteProjectionList.add(new BipartiteProjection(beforeAt, afterAt));
	}

	public static ArrayList<BipartiteProjection> getBipartiteProjectionList() {
		return bipartiteProjectionList;
	}

	public static void setBipartiteProjectionList(
			ArrayList<BipartiteProjection> bipartiteProjectionList) {
		MapBipartiteProjection.bipartiteProjectionList = bipartiteProjectionList;
	}

	@Override
	public String toString() {
		return "MapBipartiteProjection [" + bipartiteProjectionList.toString() + "]";
	}
	
}

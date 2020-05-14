package intermediate;

import java.util.ArrayList;

public class VertexList {
	
	private ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
	
	public VertexList() {
		
	}

	public ArrayList<Vertex> getVertexList() {
		return vertexList;
	}
	
	public void addVertex(Vertex v) {
		this.vertexList.add(v);
	}
	
	public int getVertexListSize( ) {
		return this.vertexList.size();
	}
	
}

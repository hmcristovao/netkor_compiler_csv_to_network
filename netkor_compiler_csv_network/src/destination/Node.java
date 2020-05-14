package destination;

import java.util.ArrayList;

public class Node {

	private String name;
	private String id_node; // TODO: Is necessary?
	
	
	public Node(String name) {
		this.name = name;
	}

	protected String getName() {
		return name;
	}

	protected String getId_node() {
		return id_node;
	}
	
	
}

package destination;

import java.util.LinkedList;

/**
 * Class description goes here.
 *
 * @version 1.0 22 Abril 2020
 * @author Luis Henrique Gundes Valim
 */

public class Link {
	private LinkedList<Node> nodesLink = new LinkedList<Node>();
	private String idEdge;
	private boolean isOriented;
	
	
	public Link(boolean isOriented, Node... nodes) {
		for(Node n : nodes) {
			this.nodesLink.add(n);
		}
		this.isOriented = isOriented;
	}

	public Node getSource() {
		return this.nodesLink.getFirst();
	}
	
	public LinkedList<Node> getAll() {
		return this.nodesLink;
	}

	public Node getTarget() {
		return this.nodesLink.getLast();
	}

	public boolean isOriented() {
		return isOriented;
	}
	public String getIdEdge() {
		return idEdge;
	}
	
	public void setIdEdge(String idEdge) {
		this.idEdge = idEdge;
	}
}

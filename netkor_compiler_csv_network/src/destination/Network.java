package destination;

import java.util.LinkedList;

public class Network {
	private LinkedList<Node> nodes = new LinkedList<Node>();
	private LinkedList<Link> links = new LinkedList<Link>();
		
	public Network() {
		
	}
	public LinkedList<Node> getNodes() {
		return this.nodes;
	}
	public LinkedList<Link> getLinks() {
		return this.links;
	}
	public int getSizeNodesList() {
		return (this.nodes.size());
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	public Node getNode(Integer id) {
		for(Node node : this.nodes) {
			if(node.getId_node() == id) {
				return node;
			}
		}
		return null;
	}
	public void addLink(Link link) {
		this.links.add(link);
	}
	public int getSizeLinks() {
		return (this.links.size());
	}
}

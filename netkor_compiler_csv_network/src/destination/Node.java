package destination;

public class Node {

	private String name;
	private Integer id_node;

	public Node(String name, Integer id_node) {
		this.name = name;
		this.id_node = id_node;
	}
	public String getName() {
		return this.name;
	}
	public Integer getId_node() {
		return this.id_node;
	}
}

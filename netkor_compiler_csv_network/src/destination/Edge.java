package destination;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class description goes here.
 *
 * @version 1.0 22 Abril 2020
 * @author Luis Henrique Gundes Valim
 */

public class Edge {
	private Node source, target;
	private boolean isOriented;
	private LinkedList<Atribute> listAtributes;
	
	protected Edge(Node source, Node target, boolean isOriented) {
		this.source = source;
		this.target = target;
		this.isOriented = isOriented;
	}

	protected Node getSource() {
		return source;
	}

	protected Node getTarget() {
		return target;
	}

	protected boolean isOriented() {
		return isOriented;
	}

	protected LinkedList<Atribute> getListAtributes() {
		return listAtributes;
	}
}

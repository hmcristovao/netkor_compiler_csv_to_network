package semantico;

import java.util.LinkedList;

import parser.Token;

public class Vertex {

	LinkedList<String> listaExpPosFixa;

	public Vertex(){
		this.listaExpPosFixa = new LinkedList<String>();
	}
	
	public void addListaExpPosFixa(String var) {
		this.listaExpPosFixa.add(var);
	}
	
	public String toString() {
		return null;
	}
}

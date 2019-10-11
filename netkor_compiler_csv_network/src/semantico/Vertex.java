package semantico;

import java.util.LinkedList;

import parser.Token;

public class Vertex {

	LinkedList<String> listaExpPosFixa;
	String vertice_name;

	public Vertex(){
		this.listaExpPosFixa = new LinkedList<String>();
	}
	
	public void setVertice_name(String vertice_name)
	{
		this.vertice_name = vertice_name;
	}
	
	public String getVertice_name()
	{
		return this.vertice_name;
	}
	
	public void addListaExpPosFixa(String var) {
		this.listaExpPosFixa.add(var);
	}
	
	public String toString() {
		return null;
	}
}

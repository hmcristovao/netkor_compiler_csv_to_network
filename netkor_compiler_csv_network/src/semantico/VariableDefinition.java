package semantico;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class VariableDefinition {

	LinkedList<Variable> table;
	
	ArrayList<String> listPrimaryKeyVertices; 
	ArrayList<String> listVertices; 
	
	HashMap<String,Integer> hashVertices;
	LinkedHashMap<String,LinkedList<String>> listExpressions;
	LinkedHashMap<String,String> hashLinhaCSV;
	
	String varPrimaryKeyColumn;
	
	public VariableDefinition() {
		this.table = new LinkedList<Variable>();
		this.listPrimaryKeyVertices = new ArrayList<String>();
		this.listVertices = new ArrayList<String>();
		this.listExpressions = new LinkedHashMap<String,LinkedList<String>>();
		this.hashVertices = new HashMap<String,Integer>();
		this.hashLinhaCSV = new LinkedHashMap<String,String>();
		this.varPrimaryKeyColumn = "";
	}
	
	public LinkedList<Variable> getListaExpPosFixa() {
		return this.table;
	}

	public LinkedList<Variable> getTabela() {
		return table;
	}
	
	public void addTabela(Variable var) {
		this.table.add(var);
	}
	
	public ArrayList<String> print(){
		return this.listPrimaryKeyVertices;
	}
	
	public void addPrimaryKeyVertices(String value) {
		this.listPrimaryKeyVertices.add(value);
	}
	
	public void setlistVariable(LinkedList<Vertex> list){
		for(Vertex vertice : list) this.listVertices.add(vertice.getVertice_name());	
	}
	
	public void setHashListCSV(String column, String value) {
		this.hashLinhaCSV.put(column, value);
	}
	
	
	public LinkedHashMap<String,String> getHashListCSV() {
		return this.hashLinhaCSV;
	}
	
	public Integer setHashVertices() {
		Integer line = 0;
		for(String vertice : listPrimaryKeyVertices) {
			if(!(line == 0)) this.hashVertices.put(vertice,line);
			line++;
		}
		for(String vertice : listVertices) {
			vertice = vertice.replace("\"", "");
			this.hashVertices.put(vertice,line++);		
		}
		return line;
	}
	
	public HashMap<String,Integer> getHashVertices(){
		return this.hashVertices;
	}
	
	public void addListVertices(String value) {
		this.listVertices.add(value);
	}
	
	public void setVarPrimaryKeyColumn() {
		for(Variable var: this.table) {
			if(var.getType().equals("*")) this.varPrimaryKeyColumn = var.getLexema();
		}
	}
	
	public void setListExpressions(LinkedList<Vertex> list) {
		for(Vertex vertice: list) {
			this.listExpressions.put(vertice.getVertice_name(), vertice.getLista());
		}
	}
	
	public String getVarPrimaryKeyColumn() {
		return this.varPrimaryKeyColumn;
	}
	
	public void imprimeExpressao() {
		System.out.println(this.table);
	}
}
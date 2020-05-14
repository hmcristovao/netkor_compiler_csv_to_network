package destination;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import inOut.*;
import intermediate.*;
import setting.Configuration;

public class Network {

	private LinkedList<Node> nodes;
	private LinkedList<Edge> edges;
	
	private NetDefinition definition;
	private VariableList variableList;
	private VertexList vertexList;
	
	//listPrimaryKeyVertices - Lista contendo todos os valores da coluna de primaryKey
	LinkedList<String> listPrimaryKeyVertices = new LinkedList<String>();
	//hashArc - Hash que armazena todos as expressoes validas para cada vertice do Csv 
	LinkedHashMap<Integer,ArrayList<String>> hashArcs = new LinkedHashMap<Integer,ArrayList<String>>();
	//HashBipartite
	LinkedHashMap<String, ArrayList<String>> hashBipartite = new LinkedHashMap<String,ArrayList<String>>();
	//Hash
	LinkedHashMap<String, Integer> hashVertexVariable = new LinkedHashMap<String,Integer>();
	//HashWeight
	HashMap<ArrayList<String>,Integer> hashWeight = new LinkedHashMap<ArrayList<String>,Integer>();
	
	public Network(NetDefinition definition, VariableList variableList, VertexList vertexList) {
		this.definition = definition;
		this.variableList = variableList;
		this.vertexList = vertexList;
		
		this.generateNetwork();
	}

	public LinkedList<Node> getNodes() {
		return this.nodes;
	}

	public LinkedList<Edge> getEdges() {
		return this.edges;
	}
	
	private void addNode(Node e) {
		this.nodes.add(e);
	}
	
	public void generateNetwork()	
	{
		try {
			//Com header -> Desconsidera-se o header, logo este (valor total - 1) serve
			Integer totalLinesCsv;
			totalLinesCsv = 0;
			
			LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));
			lnr.skip(Long.MAX_VALUE);
			totalLinesCsv = lnr.getLineNumber();
			lnr.close();

			//Sem header -> Como nao ha header, todas as linhas devem ser consideradas, para isso ha o incremento
			if(definition.getHeader().toLowerCase().equals("false")) totalLinesCsv++;	
				//Leitura do cabecalho e armazenamento da posicao da variaveis associadas
				BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));	
				Integer counterColumnCsv = 0;
				boolean existingColumn = false;
				String line = csvReader.readLine();
				String[] columnsCsv = line.split(definition.getColumnSeparator());
				//Loop para associar para cada variavel definida sua coluna correspondete no csv
				
				//Talvez otimizar este processo retirando as variaveis ja selecionadas nas iteracoes posteriores
				if(definition.getHeader().toLowerCase().equals("true")) {	//Se csv possui header
						Iterator<Variable> iterator = variableList.iterator();
						while(iterator.hasNext()) {
							Variable var = iterator.next();
							counterColumnCsv = 0;
							for(String column : columnsCsv) {
								if(var.headNameInCsv.equals(column)) {
									existingColumn = true;
									var.setColumn(counterColumnCsv);
									if(var.isVariablePrimaryKey()) variableList.setPrimaryKeyPosition(counterColumnCsv);
								}
								counterColumnCsv++;
							}
							//Verifica se alguma das colunas definidas na secao 2 eh invalida (nao existe no csv)
							if(!existingColumn) SemanticActions.inexistingColumn(var.headNameInCsv, var.getPosition());
							existingColumn = false;
						}
					}
					else {														//Se csv nao possui header
						ArrayList<String> header = new ArrayList<String>();
						for(int i = 0; i < columnsCsv.length; i++) {
							header.add("Col"+(i+1)); 
						}
						for(String column : header) {
							Iterator<Variable> iterator = variableList.iterator();
							while(iterator.hasNext()) {
								Variable var = iterator.next();
								if(var.headNameInCsv.equals(column)) {
									var.setColumn(counterColumnCsv);
									if(var.isVariablePrimaryKey()) variableList.setPrimaryKeyPosition(counterColumnCsv);	
								}
							}
							counterColumnCsv++;
						}	
					}
				
				csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));

				//Em caso de haver header no arquivo CSV, a primeira linha deve ser desconsiderada neste processamento
				if(definition.getHeader().toLowerCase().equals("true")) csvReader.readLine();			
				Integer counterLineExpression = 1, counterLineCsv = 1, counterVariableVertex = 1;
				String variableVertex;
				
				//Leitura do CSV, para cada linha do Csv:
				//	1 ==== Preenchimento do listPrimaryKeyVertices com os valores da coluna Primary Key no Csv
				//	2 ==== Loop que avalia todas expressoes definidas na section 3 para cada linha do Csv
				//	 	   se a expressao for valida o arraylist expressions recebe a linha da expressao.
				//		   Ao final armazenando todas as expressoes validas para cada linha no hashArcs
				String lineCsv = null;
				while ((lineCsv = csvReader.readLine()) != null) {
				  	columnsCsv = lineCsv.split(definition.getColumnSeparator());
			/* 1 */	this.listPrimaryKeyVertices.add(columnsCsv[variableList.getPrimaryKeyPosition()]);
			
			/* 2 */	ArrayList<String> expressions = new ArrayList<>();
					counterLineExpression = 1;																	
					for(Vertex expression : vertexList.getVertexList()) {
						if(expression.isVertexVariable()) 
						{ 
							variableVertex = columnsCsv[variableList.getVariableColumnPosition(expression.getExpression().element().token)];
							if(!this.hashVertexVariable.containsKey(variableVertex)) {
								this.hashVertexVariable.put(variableVertex, 1);
							}
							else {
								counterVariableVertex = this.hashVertexVariable.get(variableVertex);
								this.hashVertexVariable.replace(variableVertex, ++counterVariableVertex);
							}
							counterVariableVertex++;
							expressions.add(variableVertex);
							counterLineExpression--;
						} 
						else if(expression.verifierCsvExpression(columnsCsv, variableList, definition)) {
							if(definition.getBipartiteProjection().toLowerCase().equals("true")){
								expressions.add(counterLineExpression.toString());
							}
							else {
								expressions.add(String.valueOf(totalLinesCsv + counterLineExpression));
							}
						}
						counterLineExpression++;	
					}
					//Se for bipartite
					if(definition.getBipartiteProjection().toLowerCase().equals("true")){
						this.hashBipartite.put(this.listPrimaryKeyVertices.get(counterLineCsv-1), expressions);
					}
					else {
						this.hashArcs.put(counterLineCsv,expressions);
					}
					counterLineCsv++;
				}
				csvReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void writeNET() {
		try {
			if(definition.getBipartiteProjection().toLowerCase().equals("false")) {
				SemanticActions.noNetwork(this.hashArcs);
				WriterNet.writeAll(this.listPrimaryKeyVertices, this.vertexList, this.hashArcs, this.hashVertexVariable, this.definition);
			//========================================ESCRITA DO ARQUIVO NET==========================================//
			}
			
			else {
			//========================================ESCRITA DO ARQUIVO NET==========================================//
				WriterNet.writeBipartite(this.vertexList, this.hashBipartite, this.hashWeight, this.hashVertexVariable, this.definition);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

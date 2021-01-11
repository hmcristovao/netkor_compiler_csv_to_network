package intermediate;

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


import setting.Configuration;

public class IntermediateGenerator {
	
	private static NetDefinition definition;
	private static VariableList variableList;
	private static VertexList vertexList;
	
	//Lista contendo todos os valores da coluna de primaryKey
	private static LinkedList<String> listPrimaryKeyVertices = new LinkedList<String>();
	//Hash que armazena todos as expressoes validas para cada vertice do Csv 
	private static LinkedHashMap<Integer,ArrayList<String>> hashArcs = new LinkedHashMap<Integer,ArrayList<String>>();
	private static LinkedHashMap<String, ArrayList<String>> hashBipartite = new LinkedHashMap<String,ArrayList<String>>();
	private static LinkedHashMap<String, Integer> hashVertexVariable = new LinkedHashMap<String,Integer>();
	private static HashMap<ArrayList<String>,Integer> hashWeight = new LinkedHashMap<ArrayList<String>,Integer>();
	
	public static void setIntermediateGenerator(NetDefinition definition, VariableList variableList, VertexList vertexList) {
		IntermediateGenerator.definition = definition;
		IntermediateGenerator.variableList = variableList;
		IntermediateGenerator.vertexList = vertexList;
		IntermediateGenerator.generateNetwork();
	}
	
	public static NetDefinition getDefinition() {
		return definition;
	}

	public static VariableList getVariableList() {
		return variableList;
	}

	public static VertexList getVertexList() {
		return vertexList;
	}

	public static LinkedList<String> getListPrimaryKeyVertices() {
		return listPrimaryKeyVertices;
	}

	public static LinkedHashMap<Integer, ArrayList<String>> getHashArcs() {
		return hashArcs;
	}

	public static LinkedHashMap<String, ArrayList<String>> getHashBipartite() {
		return hashBipartite;
	}

	public static LinkedHashMap<String, Integer> getHashVertexVariable() {
		return hashVertexVariable;
	}

	public static HashMap<ArrayList<String>, Integer> getHashWeight() {
		return hashWeight;
	}

	public static void generateNetwork() {
		try {
			//Com header -> Desconsidera-se o header, logo este (valor total - 1) serve
			Integer totalLinesCsv;
			totalLinesCsv = 0;
			
			LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));
			lnr.skip(Long.MAX_VALUE);
			totalLinesCsv = lnr.getLineNumber();
			lnr.close();

			//Sem header -> Como nao ha header, todas as linhas devem ser consideradas, para isso ha o incremento
			if(IntermediateGenerator.definition.getHeader().toLowerCase().equals("false")) totalLinesCsv++;	
				//Leitura do cabecalho e armazenamento da posicao da variaveis associadas
				BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));	
				Integer counterColumnCsv = 0;
				boolean existingColumn = false;
				String line = csvReader.readLine();
				String[] columnsCsv = line.split(IntermediateGenerator.definition.getColumnSeparator());
				//Loop para associar para cada variavel definida sua coluna correspondete no csv
				
				//Talvez otimizar este processo retirando as variaveis ja selecionadas nas iteracoes posteriores
				if(IntermediateGenerator.definition.getHeader().toLowerCase().equals("true")) {	//Se csv possui header
						Iterator<Variable> iterator = IntermediateGenerator.variableList.iterator();
						while(iterator.hasNext()) {
							Variable var = iterator.next();
							counterColumnCsv = 0;
							for(String column : columnsCsv) {
								if(var.headNameInCsv.equals(column)) {
									existingColumn = true;
									var.setColumn(counterColumnCsv);
									if(var.isVariablePrimaryKey()) IntermediateGenerator.variableList.setPrimaryKeyPosition(counterColumnCsv);
								}
								counterColumnCsv++;
							}
							//Verifica se alguma das colunas definidas na secao 2 eh invalida (nao existe no csv)
							if(!existingColumn) SemanticActions.inexistingColumn(var.headNameInCsv, var.getPosition());
							existingColumn = false;
						}
					}
					else {//Se csv nao possui header
						ArrayList<String> header = new ArrayList<String>();
						for(int i = 0; i < columnsCsv.length; i++) {
							header.add("Col"+(i+1)); 
						}
						for(String column : header) {
							Iterator<Variable> iterator = IntermediateGenerator.variableList.iterator();
							while(iterator.hasNext()) {
								Variable var = iterator.next();
								if(var.headNameInCsv.equals(column)) {
									var.setColumn(counterColumnCsv);
									if(var.isVariablePrimaryKey()) IntermediateGenerator.variableList.setPrimaryKeyPosition(counterColumnCsv);	
								}
							}
							counterColumnCsv++;
						}	
					}
				
				csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));

				//Em caso de haver header no arquivo CSV, a primeira linha deve ser desconsiderada neste processamento
				if(IntermediateGenerator.definition.getHeader().toLowerCase().equals("true")) csvReader.readLine();			
				Integer counterLineExpression = 1, counterLineCsv = 1, counterVariableVertex = 1;
				String variableVertex;
				
				//Leitura do CSV, para cada linha do Csv:
				//	1 ==== Preenchimento do listPrimaryKeyVertices com os valores da coluna Primary Key no Csv
				//	2 ==== Loop que avalia todas expressoes definidas na section 3 para cada linha do Csv
				//	 	   se a expressao for valida o arraylist expressions recebe a linha da expressao.
				//		   Ao final armazenando todas as expressoes validas para cada linha no hashArcs
				String lineCsv = null;
				while ((lineCsv = csvReader.readLine()) != null) {
				  	columnsCsv = lineCsv.split(IntermediateGenerator.definition.getColumnSeparator());
			/* 1 */	IntermediateGenerator.listPrimaryKeyVertices.add(columnsCsv[IntermediateGenerator.variableList.getPrimaryKeyPosition()]);
			
			/* 2 */	ArrayList<String> expressions = new ArrayList<>();
					counterLineExpression = 1;																	
					for(Vertex expression : IntermediateGenerator.vertexList.getVertexList()) {
						if(expression.isVertexVariable()) 
						{ 
							variableVertex = columnsCsv[IntermediateGenerator.variableList.getVariableColumnPosition(expression.getExpression().element().token)];
							if(!IntermediateGenerator.hashVertexVariable.containsKey(variableVertex)) {
								IntermediateGenerator.hashVertexVariable.put(variableVertex, 1);
							}
							else {
								counterVariableVertex = IntermediateGenerator.hashVertexVariable.get(variableVertex);
								IntermediateGenerator.hashVertexVariable.replace(variableVertex, ++counterVariableVertex);
							}
							counterVariableVertex++;
							expressions.add(variableVertex);
							counterLineExpression--;
						} 
						else if(expression.verifierCsvExpression(columnsCsv, IntermediateGenerator.variableList, IntermediateGenerator.definition)) {
							if(IntermediateGenerator.definition.getBipartiteProjection().toLowerCase().equals("true")){
								expressions.add(counterLineExpression.toString());
							}
							else {
								expressions.add(String.valueOf(totalLinesCsv + counterLineExpression));
							}
						}
						counterLineExpression++;	
					}
					//Se for bipartite
					if(IntermediateGenerator.definition.getBipartiteProjection().toLowerCase().equals("true")){
						IntermediateGenerator.hashBipartite.put(IntermediateGenerator.listPrimaryKeyVertices.get(counterLineCsv-1), expressions);
					}
					else {
						IntermediateGenerator.hashArcs.put(counterLineCsv,expressions);
					}
					counterLineCsv++;
				}
				csvReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}

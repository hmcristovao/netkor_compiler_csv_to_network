package inOut;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import intermediate.NetDefinition;
import intermediate.SemanticActions;
import intermediate.Variable;
import intermediate.VariableList;
import intermediate.Vertex;
import intermediate.VertexList;
import setting.Configuration;

public class ReaderCsv {
	public static Integer countLines() throws IOException {
		Integer totalLines = 0;
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(Configuration.csvFileInput), "UTF-8"));
		lnr.skip(Long.MAX_VALUE);
		totalLines = lnr.getLineNumber();
		lnr.close();
		return totalLines;
	}
	
	public static void readColumns(VariableList variableList, NetDefinition definition) throws IOException  {
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
		csvReader.close();
	}
	
	public static void readAllLines(LinkedList<String> listPrimaryKeyVertices, 
						VariableList variableList, VertexList vertexList, 
						Integer totalLinesCsv, LinkedHashMap<Integer, ArrayList<String>> hashArcs,
						NetDefinition definition, LinkedHashMap<String, ArrayList<String>> hashBipartite,
						LinkedHashMap<String, Integer> hashVertexVariable ) throws IOException {
		//Pulando a leitura das primeiras linhas ja que as colunas ja foram lidas anteriormente
		BufferedReader csvReader = new BufferedReader(new FileReader(Configuration.csvFileInput));		
		
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
		  	String[] columnsCsv = lineCsv.split(definition.getColumnSeparator());
	/* 1 */	listPrimaryKeyVertices.add(columnsCsv[variableList.getPrimaryKeyPosition()]);
	
	/* 2 */	ArrayList<String> expressions = new ArrayList<>();
			counterLineExpression = 1;																	
			for(Vertex expression : vertexList.getVertexList()) {
				if(expression.isVertexVariable()) 
				{ 
					variableVertex = columnsCsv[variableList.getVariableColumnPosition(expression.getExpression().element().token)];
					if(!hashVertexVariable.containsKey(variableVertex)) {
						hashVertexVariable.put(variableVertex, 1);
					}
					else {
						counterVariableVertex = hashVertexVariable.get(variableVertex);
						hashVertexVariable.replace(variableVertex, ++counterVariableVertex);
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
				hashBipartite.put(listPrimaryKeyVertices.get(counterLineCsv-1), expressions);
			}
			else {
				hashArcs.put(counterLineCsv,expressions);
			}
			counterLineCsv++;
		}
		csvReader.close();
	}
}

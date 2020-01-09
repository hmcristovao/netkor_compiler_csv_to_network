package inOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import semantic.Variable;
import semantic.VariableList;
import semantic.Vertex;
import setting.Configuration;

public class ReaderCsv {
	public static Integer countLines() throws IOException {
		Integer totalLines = 0;
		LineNumberReader lnr = new LineNumberReader(new FileReader(new File(Configuration.csvFileInput)));
		lnr.skip(Long.MAX_VALUE);
		totalLines = lnr.getLineNumber();
		lnr.close();
		return totalLines;
	}
	
	public static void readColumns(VariableList variableList) throws IOException  {
		BufferedReader csvReader = new BufferedReader(new FileReader(Configuration.csvFileInput));										//
		Integer counterColumnCsv = 0;
		String line = csvReader.readLine();																				//
		String[] columnsCsv = line.split(",");																			//
		Iterator<Variable> iterator = variableList.iterator();
		Variable var = iterator.next();
		//Loop para associar para cada variavel definida sua coluna correspondete no csv
		for(String column : columnsCsv) {
			if(var.headNameInCsv.equals(column)) {
				var.setColumn(counterColumnCsv);
				if(var.isVariablePrimaryKey()) variableList.setPrimaryKeyPosition(counterColumnCsv);
				if(iterator.hasNext()) var = iterator.next();	
			}
			counterColumnCsv++;
		}
		csvReader.close();
	}
	
	public static void readAllLines(LinkedList<String> listPrimaryKeyVertices, 
						VariableList variableList, LinkedList<Vertex> listVertex, 
						Integer totalLinesCsv, HashMap<Integer,ArrayList<Integer>> hashArcs) throws IOException {
		//Pulando a leitura das primeiras linhas ja que as colunas ja foram lidas anteriormente
		BufferedReader csvReader = new BufferedReader(new FileReader(Configuration.csvFileInput));		
		String lineCsv = null;
		csvReader.readLine();												
		Integer counterLineExpression = 1, counterLineCsv = 1;
	
		//Leitura do CSV, para cada linha do Csv:
		//	1 ==== Preenchimento do listPrimaryKeyVertices com os valores da coluna Primary Key no Csv
		//	2 ==== Loop que avalia todas expressoes definidas na section 3 para cada linha do Csv
		//	 	   se a expressao for valida o arraylist expressions recebe a linha da expressao.
		//		   Ao final armazenando todas as expressoes validas para cada linha no hashArcs
		while ((lineCsv = csvReader.readLine()) != null) {
		  	String[] columnsCsv = lineCsv.split(",");
	/* 1 */	listPrimaryKeyVertices.add(columnsCsv[variableList.getPrimaryKeyPosition()]);
	/* 2 */	ArrayList<Integer> expressions = new ArrayList<Integer>();
			counterLineExpression = 1;																	
			for(Vertex expression : listVertex) {
				if(expression.verifierCsvExpression(columnsCsv, variableList)) {
					expressions.add(totalLinesCsv + counterLineExpression);
				}
				counterLineExpression++;	
			}
			hashArcs.put(counterLineCsv++,expressions);														
		}
		csvReader.close();		
	}
}

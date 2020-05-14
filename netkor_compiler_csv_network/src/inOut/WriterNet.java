package inOut;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import intermediate.CollectionLink;
import intermediate.NetDefinition;
import intermediate.Vertex;
import intermediate.VertexList;
import setting.Configuration;

public class WriterNet {
	public static void writeAll(LinkedList<String> listPrimaryKeyVertices, VertexList vertexList, 
						HashMap<Integer, ArrayList<String>> hashArcs, LinkedHashMap<String, Integer> hashVertexVariable,
						NetDefinition definition) throws IOException {
		Integer counterLineNet = 1, counterVertexVariable = 0;
		FileWriter resultFile = new FileWriter(Configuration.csvFileOutput);
		PrintWriter resultFileWriter = new PrintWriter(resultFile);
		for(Vertex vertex : vertexList.getVertexList()) {
			if(vertex.isVertexVariable()) counterVertexVariable++;
		}
		resultFileWriter.println("*Vertices " + (listPrimaryKeyVertices.size() + vertexList.getVertexListSize()
									-counterVertexVariable + hashVertexVariable.size()));	
		for(String vertex : listPrimaryKeyVertices) {
			resultFileWriter.println(counterLineNet++ +  " \"" + vertex + "\"");							
		}
		for(Vertex vertex : vertexList.getVertexList()) {
			if(!vertex.isVertexVariable()) resultFileWriter.println(counterLineNet++ +  " " + vertex.getVertexName() + "");			
		}
		Set<Map.Entry<String, Integer>> entries = hashVertexVariable.entrySet();
		for (Map.Entry<String, Integer> entry : entries) {					
			resultFileWriter.println(counterLineNet++ +  " \"" + entry.getKey() + "\"");
			hashVertexVariable.replace(entry.getKey(), counterLineNet-1);
		}
		
		switch(definition.getDirectedNetwork().toLowerCase()) {
			case "true":
				resultFileWriter.println("*Arcs");
				break;
			case "false":
				resultFileWriter.println("*Edges");
				break;
		}
		ArrayList<Integer> expressions = new ArrayList<>();
		Integer counterLine = 1;
		Set<Map.Entry<Integer,ArrayList<String>>> entriesRelations = hashArcs.entrySet();
		for (Map.Entry<Integer,ArrayList<String>> entry : entriesRelations) {	
			for(String valueExpression : entry.getValue()) {
				if(hashVertexVariable.containsKey(valueExpression)) {
					expressions.add(hashVertexVariable.get(valueExpression));
				}
				else {
					expressions.add(Integer.valueOf(valueExpression));
				}
			}
			Collections.sort(expressions);
			for(Integer value: expressions) {
				if(counterLine.equals(listPrimaryKeyVertices.size()) && value.equals(expressions.get(expressions.size()-1))) {
					resultFileWriter.print(counterLine + " " + value + "");
				}
				else {
					resultFileWriter.println(counterLine + " " + value + "");
				}
			}
			counterLine++;
			expressions.clear();
		}
		resultFile.close();	
	}	
	
	public static void writeBipartite(VertexList vertexList, 
			LinkedHashMap<String, ArrayList<String>> hashBipartite,
			HashMap<ArrayList<String>,Integer> hashWeight, 
			LinkedHashMap<String, Integer> hashVertexVariable,
			NetDefinition definition) throws IOException {
		
		Integer counterLineNet = 1, vertexVariableQuant = hashVertexVariable.size();
		ArrayList<String> lastWeight = new ArrayList<String>();
		FileWriter resultFile = new FileWriter(Configuration.csvFileOutput);
		for(Vertex vertex : vertexList.getVertexList()) {
			if(vertex.isVertexVariable()) vertexVariableQuant--;
		}
		PrintWriter resultFileWriter = new PrintWriter(resultFile);									
		resultFileWriter.println("*Vertices " + (vertexList.getVertexListSize() + vertexVariableQuant));	
		for(Vertex vertex : vertexList.getVertexList()) {
			if(!vertex.isVertexVariable()) resultFileWriter.println(counterLineNet++ +  " " + vertex.getVertexName() + "");		
		}
		Set<Map.Entry<String, Integer>> entries = hashVertexVariable.entrySet();
		for (Map.Entry<String, Integer> entry : entries) {					
			resultFileWriter.println(counterLineNet++ +  " \"" + entry.getKey() + "\"");
			hashVertexVariable.replace(entry.getKey(), counterLineNet-1);
		}
		for (ArrayList<String> entry : hashBipartite.values()) {
		    for(int counterInitial = 0; counterInitial < entry.size()-1; counterInitial++) {
		    	for(int counter = counterInitial + 1; counter < entry.size(); counter++) {	
					List<String> link = new ArrayList<>(Arrays.asList(entry.get(counterInitial),entry.get(counter)));
					for(String vertexVariable : hashVertexVariable.keySet()) {
						if(link.contains(vertexVariable)) {
							link.set(link.indexOf(vertexVariable), String.valueOf(hashVertexVariable.get(vertexVariable)));
						}
					}
					if(hashWeight.containsKey(link)) {
						Integer value = hashWeight.get(link);
						hashWeight.replace((ArrayList<String>) link, value, ++value);
					}
					else {
						hashWeight.put((ArrayList<String>)link, 1);
					}
				}	 
			}
		}
		//Ordenando os valores    
		List<Map.Entry<ArrayList<String>,Integer>> list = new ArrayList<>(hashWeight.entrySet());
		Collections.sort(list, new CollectionLink());
		hashWeight.clear();
		
		for(Map.Entry<ArrayList<String>,Integer> test : list) {
			hashWeight.put(test.getKey(), test.getValue());
		}	
		
		switch(definition.getDirectedNetwork().toLowerCase()) {
			case "true":
				resultFileWriter.println("*Arcs");
				break;
			case "false":
				resultFileWriter.println("*Edges");
				break;
		}
		
		Integer counter = 1;
		Set<Map.Entry<ArrayList<String>,Integer>> entriesWeight = hashWeight.entrySet();
		for (Map.Entry<ArrayList<String>,Integer> entryWeight : entriesWeight) {
			ArrayList<String> link = entryWeight.getKey();
			String x = link.get(0);
			String y = link.get(1);
			if(entryWeight.getValue().equals(1)) {
				if(counter.equals(hashWeight.size()) && link.equals(hashWeight.keySet().toArray()[hashWeight.size()-1])) {
					resultFileWriter.print(x + " " + y);
				}
				else {
					resultFileWriter.println(x + " " + y);
				}										
			}
			else {
				if(counter.equals(hashWeight.size()) && link.equals(hashWeight.keySet().toArray()[hashWeight.size() -1])) {
					resultFileWriter.print(x + " " + y + " " + entryWeight.getValue());
				}
				else {
					resultFileWriter.println(x + " " + y + " " + entryWeight.getValue());
				}	
			}
			counter++;
		}
		resultFile.close();
	}
}

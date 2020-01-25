package inOut;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import semantic.Vertex;
import setting.Configuration;
import setting.NetDefinition;

public class WriterNet {
	public static void writeAll(LinkedList<String> listPrimaryKeyVertices, ArrayList<Vertex> vertexList, 
						HashMap<Integer, ArrayList<Integer>> hashArcs, NetDefinition definition) throws IOException {
		Integer counterLineNet = 1;
		FileWriter resultFile = new FileWriter(Configuration.csvFileOutput);
		PrintWriter resultFileWriter = new PrintWriter(resultFile);									
		resultFileWriter.println("*Vertices " + (listPrimaryKeyVertices.size() + vertexList.size()));	
		for(String vertex : listPrimaryKeyVertices) {
			resultFileWriter.println(counterLineNet++ +  " \"" + vertex + "\"");							
		}
		for(Vertex vertex : vertexList) {
			resultFileWriter.println(counterLineNet++ +  " " + vertex.getVertexName() + "");			
		}
		
		switch(definition.getDirectedNetwork().toLowerCase()) {
			case "true":
				resultFileWriter.println("*Arcs");
				break;
			case "false":
				resultFileWriter.println("*Edges");
				break;
		}
		resultFile.close();
	}	
	
	public static void writeBipartite(ArrayList<Vertex> vertexList, 
			LinkedHashMap<String, ArrayList<Integer>> hashBipartite,
			HashMap<ArrayList<Integer>,Integer> hashWeight, NetDefinition definition) throws IOException {
		Integer counterLineNet = 1;
		FileWriter resultFile = new FileWriter(Configuration.csvFileOutput);
		PrintWriter resultFileWriter = new PrintWriter(resultFile);									
		resultFileWriter.println("*Vertices " + (vertexList.size()));	
		for(Vertex vertex : vertexList) {
			resultFileWriter.println(counterLineNet++ +  " " + vertex.getVertexName() + "");			
		}
		
		switch(definition.getDirectedNetwork().toLowerCase()) {
			case "true":
				resultFileWriter.println("*Arcs");
				break;
			case "false":
				resultFileWriter.println("*Edges");
				break;
		}
		
		Set<Map.Entry<ArrayList<Integer>,Integer>> entries = hashWeight.entrySet();
		for (Map.Entry<ArrayList<Integer>,Integer> entry : entries) {
			ArrayList<Integer> link = entry.getKey();
			int x = link.get(0), y = link.get(1);
			if(entry.getValue() == 1) {
				resultFileWriter.println(x + " " + y);										
			}
			else {
				resultFileWriter.println(x + " " + y + " " + entry.getValue());
			}
		}
		resultFile.close();
	}
}

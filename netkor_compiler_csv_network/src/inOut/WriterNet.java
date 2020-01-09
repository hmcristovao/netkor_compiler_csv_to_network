package inOut;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import semantic.Vertex;
import setting.Configuration;
import setting.NetDefinition;

public class WriterNet {
	public static void writeAll(LinkedList<String> listPrimaryKeyVertices, LinkedList<Vertex> listVertex, 
						HashMap<Integer, ArrayList<Integer>> hashArcs, NetDefinition definition) throws IOException {
		Integer counterLineNet = 1;
		FileWriter resultFile = new FileWriter(Configuration.csvFileOutput);
		PrintWriter resultFileWriter = new PrintWriter(resultFile);									
		resultFileWriter.println("*Vertices " + (listPrimaryKeyVertices.size() + listVertex.size()));	
		for(String vertex : listPrimaryKeyVertices) {
			resultFileWriter.println(counterLineNet++ +  " \"" + vertex + "\"");							
		}
		for(Vertex vertex : listVertex) {
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
		
		Set<Map.Entry<Integer,ArrayList<Integer>>> entries = hashArcs.entrySet();
		for (Map.Entry<Integer,ArrayList<Integer>> entry : entries) {	
			for(Integer valueExpression : entry.getValue()) {
				resultFileWriter.println(entry.getKey() + " " + valueExpression + " ");
			}							
		}
		resultFile.close();
	}	
}

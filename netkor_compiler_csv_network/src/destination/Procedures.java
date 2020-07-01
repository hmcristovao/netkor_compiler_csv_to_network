package destination;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import intermediate.CollectionLink;
import intermediate.IntermediateGenerator;
import intermediate.Vertex;
import setting.Configuration;

public class Procedures {
	
	public static void GenerateNoBipartiteNetwork(Network network) {	
		Integer counterLineNet = 1, counterVertexVariable = 0;
		for(Vertex vertex : IntermediateGenerator.getVertexList().getVertexList()) {
			if(vertex.isVertexVariable()) counterVertexVariable++;
		}

		boolean isOriented = true;
		switch(IntermediateGenerator.getDefinition().getDirectedNetwork().toLowerCase()) {
		case "true":
			isOriented = true;
			break;
		case "false":
			isOriented = false;
			break;
		}
	
		
		for(String vertex : IntermediateGenerator.getListPrimaryKeyVertices()) {
			network.addNode(new Node(vertex, counterLineNet++));							
		}
		for(Vertex vertex : IntermediateGenerator.getVertexList().getVertexList()) {
			if(!vertex.isVertexVariable()) {
				network.addNode(new Node(vertex.getVertexName(), counterLineNet++));
			}
		}
		Set<Map.Entry<String, Integer>> entries = IntermediateGenerator.getHashVertexVariable().entrySet();
		for (Map.Entry<String, Integer> entry : entries) {					
			network.addNode(new Node(" \"" + entry.getKey() + "\"", counterLineNet++));
			IntermediateGenerator.getHashVertexVariable().replace(entry.getKey(), counterLineNet-1);
		}
		

		ArrayList<Integer> expressions = new ArrayList<>();
		Integer counterLine = 1;
		Set<Map.Entry<Integer,ArrayList<String>>> entriesRelations = IntermediateGenerator.getHashArcs().entrySet();
		for (Map.Entry<Integer,ArrayList<String>> entry : entriesRelations) {	
			for(String valueExpression : entry.getValue()) {
				if(IntermediateGenerator.getHashVertexVariable().containsKey(valueExpression)) {
					expressions.add(IntermediateGenerator.getHashVertexVariable().get(valueExpression));
				}
				else {
					expressions.add(Integer.valueOf(valueExpression));
				}
			}
			// TODO: Alterar forma de conexão de EDGES, usar ID ao invés de counterline
			Collections.sort(expressions);
			for(Integer value: expressions) {
				if(counterLine.equals(IntermediateGenerator.getListPrimaryKeyVertices().size()) && value.equals(expressions.get(expressions.size()-1))) {
					network.addLink(new Link(isOriented, network.getNode(counterLine), network.getNode(value)));
				}
				else {
					network.addLink(new Link(isOriented, network.getNode(counterLine), network.getNode(value)));
				}
			}
			counterLine++;
			expressions.clear();
		}
	}
		
	public static void FileWriteNoBipartiteNetwork(Network net) {
		
		FileWriter resultFile;
		try {
			resultFile = new FileWriter(Configuration.csvFileOutput);
			PrintWriter resultFileWriter = new PrintWriter(resultFile);
			
			resultFileWriter.println("* Vértices " + net.getSizeNodesList());	
			for(Node node : net.getNodes()) {
				resultFileWriter.println(node.getName());
			}
			switch(IntermediateGenerator.getDefinition().getDirectedNetwork().toLowerCase()) {
			case "true":
				resultFileWriter.println("* Arcs ");
				break;
			case "false":
				resultFileWriter.println("* Edges ");
				break;
			}
			for(Link e : net.getLinks()) {
				if(!e.isOriented()) {
					resultFileWriter.println(e.getSource().getId_node() + " " + e.getTarget().getId_node());
				}
			}
			for(Link e : net.getLinks()) {
				if(e.isOriented()) {
					resultFileWriter.println(e.getSource().getId_node() + " " + e.getTarget().getId_node());
				}
			}
			resultFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public static void FileWriteBipartiteNetwork(Network net) {
		
		FileWriter resultFile;
		try {
			resultFile = new FileWriter(Configuration.csvFileOutput);
			PrintWriter resultFileWriter = new PrintWriter(resultFile);
			
			resultFileWriter.println("* Vértices " + net.getSizeNodesList());	
			for(Node node : net.getNodes()) {
				resultFileWriter.println(node.getName());
			}
			
			switch(IntermediateGenerator.getDefinition().getDirectedNetwork().toLowerCase()) {
			case "true":
				resultFileWriter.println("* Arcs ");
				break;
			case "false":
				resultFileWriter.println("* Edges ");
				break;
			}
			
			for(Link e : net.getLinks()) {
				if(!e.isOriented()) {
					for(Node n: e.getAll()) {
						resultFileWriter.print(n.getId_node() + " ");
					}
					resultFileWriter.println();
				}
			}		
			for(Link e : net.getLinks()) {
				if(e.isOriented()) {
					for(Node n: e.getAll()) {
						resultFileWriter.print(n.getId_node() + " ");
					}
					resultFileWriter.println();
				}
			}
			resultFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void generateBipartiteNetwork(Network net) {
		Integer counterLineNet = 1;
		for(Vertex vertex : IntermediateGenerator.getVertexList().getVertexList()) {
			if(!vertex.isVertexVariable()) {
				net.addNode(new Node(vertex.getVertexName(), counterLineNet++));
				
			}
		}
		Set<Map.Entry<String, Integer>> entries = IntermediateGenerator.getHashVertexVariable().entrySet();
		for (Map.Entry<String, Integer> entry : entries) {					
			net.addNode(new Node(entry.getKey(), counterLineNet++));;
			IntermediateGenerator.getHashVertexVariable().replace(entry.getKey(), counterLineNet-1);
		}
		for (ArrayList<String> entry : IntermediateGenerator.getHashBipartite().values()) {
		    for(int counterInitial = 0; counterInitial < entry.size()-1; counterInitial++) {
		    	for(int counter = counterInitial + 1; counter < entry.size(); counter++) {	
					List<String> link = new ArrayList<>(Arrays.asList(entry.get(counterInitial),entry.get(counter)));
					for(String vertexVariable : IntermediateGenerator.getHashVertexVariable().keySet()) {
						if(link.contains(vertexVariable)) {
							link.set(link.indexOf(vertexVariable), String.valueOf(IntermediateGenerator.getHashVertexVariable().get(vertexVariable)));
						}
					}
					if(IntermediateGenerator.getHashWeight().containsKey(link)) {
						Integer value = IntermediateGenerator.getHashWeight().get(link);
						IntermediateGenerator.getHashWeight().replace((ArrayList<String>) link, value, ++value);
					}
					else {
						IntermediateGenerator.getHashWeight().put((ArrayList<String>)link, 1);
					}
				}	 
			}
		}    
		List<Map.Entry<ArrayList<String>,Integer>> list = new ArrayList<>(IntermediateGenerator.getHashWeight().entrySet());
		Collections.sort(list, new CollectionLink());
		IntermediateGenerator.getHashWeight().clear();
		
		for(Map.Entry<ArrayList<String>,Integer> test : list) {
			IntermediateGenerator.getHashWeight().put(test.getKey(), test.getValue());
		}	
		
		boolean isOriented = true;
		switch(IntermediateGenerator.getDefinition().getDirectedNetwork().toLowerCase()) {
		case "true":
			isOriented = true;
			break;
		case "false":
			isOriented = false;
			break;
		}
		
		Integer counter = 1;
		Set<Map.Entry<ArrayList<String>,Integer>> entriesWeight = IntermediateGenerator.getHashWeight().entrySet();
		for (Map.Entry<ArrayList<String>,Integer> entryWeight : entriesWeight) {
			ArrayList<String> link = entryWeight.getKey();
			String x = link.get(0);
			String y = link.get(1);
			if(entryWeight.getValue().equals(1)) {
				if(counter.equals(IntermediateGenerator.getHashWeight().size()) && link.equals(IntermediateGenerator.getHashWeight().keySet().toArray()[IntermediateGenerator.getHashWeight().size()-1])) {
					net.addLink(new Link(isOriented, net.getNode(Integer.parseInt(x)), net.getNode(Integer.parseInt(y))));
				}
				else {
					net.addLink(new Link(isOriented, net.getNode(Integer.parseInt(x)), net.getNode(Integer.parseInt(y))));
				}										
			}
			else {
				if(counter.equals(IntermediateGenerator.getHashWeight().size()) && link.equals(IntermediateGenerator.getHashWeight().keySet().toArray()[IntermediateGenerator.getHashWeight().size() -1])) {
					net.addLink(new Link(isOriented, net.getNode(Integer.parseInt(x)), net.getNode(Integer.parseInt(y)), net.getNode(entryWeight.getValue())));
				}
				else {
					net.addLink(new Link(isOriented, net.getNode(Integer.parseInt(x)), net.getNode(Integer.parseInt(y)), net.getNode(entryWeight.getValue())));
				}	
			}
			counter++;
		}
	}
	
}

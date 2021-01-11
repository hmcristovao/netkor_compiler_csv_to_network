package network;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import enumerations.NETWORK_FORMAT;
import enumerations.TYPE_OPERAND;
import intermediate.MapSettings;

public class Network extends NetworkSections {
	
	private static LinkedHashMap<String, TYPE_OPERAND> vertices;
	private static ArrayList<Edge> edges;
	private static LinkedHashMap<String, String> attributes;
	private static int countPrint = 0;
	
	public Network() {
		Network.vertices = new LinkedHashMap<String, TYPE_OPERAND>();
		Network.edges = new ArrayList<Edge>();
		Network.attributes = new LinkedHashMap<String, String>();
	}
	
	public static void clear() {
		if(vertices.size() > 0) {
			Network.getVertices().clear();
		}
		if(edges.size() > 0) {
			Network.getEdges().clear();
		}
	}
	public static void addVertice(String vertice, TYPE_OPERAND typeVertice) {
		if(!vertices.containsKey(vertice)) {
			vertices.put(vertice, typeVertice);
		}
	}
	
	public static void addEdges(String vertice1, String vertice2) {
		for(Edge edge : edges) {
			if(edge.isEqual(vertice1, vertice2)) {
				edge.setCount(1);
				return;
			}
		}
		edges.add(new Edge(vertice1, vertice2));
	}
	
	public static void addAttribute(String vertice, String attribute) {
		Network.attributes.put(vertice, attribute);
	}
	
	public static LinkedHashMap<String, TYPE_OPERAND> getVertices() {
		return vertices;
	}

	public static void setVertices(LinkedHashMap<String, TYPE_OPERAND> vertices) {
		Network.vertices = vertices;
	}

	public static ArrayList<Edge> getEdges() {
		return edges;
	}

	public static void setEdges(ArrayList<Edge> edges) {
		Network.edges = edges;
	}

	public static LinkedHashMap<String, String> getAttributes() {
		return attributes;
	}

	public static void setAttributes(LinkedHashMap<String, String> attributes) {
		Network.attributes = attributes;
	}
	
	public static void generateNetworks() {
		for(NETWORK_FORMAT net : MapSettings.getNetworkFormat()) {
			if(net.toString().equals(NETWORK_FORMAT.NET.toString())) {
				try {
					generateNetFormat();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(net.toString().equals(NETWORK_FORMAT.GDF.toString())) {
				try {
					generateGdfFormat();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void generateGdfFormat() throws IOException {
	    String fileName = MapSettings.getFileSufix().substring(1, MapSettings.getFileSufix().length() - 1).
	    		concat("GDF_").concat(String.valueOf(Network.countPrint)).concat(".gdf");
	    Network.countPrint++;
		FileWriter fileWriter = new FileWriter(fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("nodedef>name VARCHAR,label VARCHAR");
	    for(int index = 0; index < vertices.size(); index++) {
	    	String vertice = new ArrayList<String>(vertices.keySet()).get(index);
	    	String attribute = Network.getAttributes().get(vertice);
	    	vertice = vertice.replace("\"", "'");
	    	attribute = attribute.replace("\"", "'");
	    	printWriter.println(vertice + "," + attribute);
	    }
	    if(MapSettings.isDirectedNetwork()) {
	    	printWriter.println("edgedef>node1 VARCHAR,node2 VARCHAR");
	    } else {
	    	printWriter.println("edgedef>node1 VARCHAR,node2 VARCHAR, weight DOUBLE");
	    }
	    for(int index = 0; index < edges.size(); index++) {
	    	int posVertice1 = new ArrayList<String>(vertices.keySet()).indexOf(edges.get(index).getStart());
	    	int posVertice2 = new ArrayList<String>(vertices.keySet()).indexOf(edges.get(index).getEnd());
	    	if(posVertice1 >= 0 && posVertice2 >= 0) {
	    		if(edges.get(index).getCount() > 1) {
	    			String vertice1 = edges.get(index).getStart().replace("\"", "'");
	    			String vertice2 = edges.get(index).getEnd().replace("\"", "'");
		    		printWriter.println(vertice1 + "," + vertice2 + "," + (double) edges.get(index).getCount());
	    		} else {
	    			String vertice1 = edges.get(index).getStart().replace("\"", "'");
	    			String vertice2 = edges.get(index).getEnd().replace("\"", "'");	    			
		    		printWriter.println(vertice1 + "," + vertice2 + "," + "1.0");
	    		}
	    	}
	    }	    
	    printWriter.close();		
	}
	
	public static void generateNetFormat() throws IOException {
	    String fileName = MapSettings.getFileSufix().substring(1, MapSettings.getFileSufix().length() - 1).
	    		concat("NET_").concat(String.valueOf(Network.countPrint)).concat(".net");
	    Network.countPrint++;
		FileWriter fileWriter = new FileWriter(fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("*Vertices " + vertices.size());
	    for(int index = 0; index < vertices.size(); index++) {
	    	int number = index + 1;
	    	String vertice = new ArrayList<String>(vertices.keySet()).get(index);
	    	printWriter.println(number + " " + vertice);
	    }
	    if(MapSettings.isDirectedNetwork()) {
	    	printWriter.println("*Arcs");
	    } else {
	    	printWriter.println("*Edges");
	    }
	    for(int index = 0; index < edges.size(); index++) {
	    	int posVertice1 = new ArrayList<String>(vertices.keySet()).indexOf(edges.get(index).getStart());
	    	int posVertice2 = new ArrayList<String>(vertices.keySet()).indexOf(edges.get(index).getEnd());
	    	if(posVertice1 >= 0 && posVertice2 >= 0) {
	    		posVertice1++;
	    		posVertice2++;
	    		if(edges.get(index).getCount() > 1) {
		    		printWriter.println(posVertice1 + " " + posVertice2 + " " + edges.get(index).getCount());
	    		} else {
		    		printWriter.println(posVertice1 + " " + posVertice2);
	    		}
	    	}
	    }	    
	    printWriter.close();
	}
}

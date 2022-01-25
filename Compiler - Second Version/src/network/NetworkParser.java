package network;

//import java.io.File;
import java.io.*; // substituindo a linha de cima

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import commands.Attribuition;
import commands.Command;
import commands.Conditional;
import commands.Return;
import elementars.Expression;
import elementars.Operand;
import elementars.Symbol;
import enumerations.TYPE_OPERAND;
import intermediate.BipartiteProjection;
import intermediate.DataFrameComponent;
import intermediate.MapAdjacencyList;
import intermediate.MapAliasTable;
import intermediate.MapBipartiteProjection;
import intermediate.MapDataFrame;
import intermediate.MapRowsDeleting;
import settings.Configuration;

public class NetworkParser {
	
	
	private static final char DEFAULT_SEPARATOR = ';';
	private static final char DEFAULT_QUOTE = '"';
	
	public static void networkParserAll(Network network) throws Exception {
		NetworkParser.parserCsvToTable();
		NetworkParser.parseHeaderToAliasTable(network.getAliasTable(), network.getDataFrame());
		NetworkParser.parseDataFrame(network.getAliasTable(), network.getDataFrame());
		NetworkParser.parseRowsDeleting(network.getAliasTable(), network.getDataFrame(), network.getRowsDeleting());
		NetworkParser.parseAdjacencyList(network);
		NetworkParser.parseBipartiteProjection(network);
	}
	
	public static void parseAdjacencyList(Network network) {
		for(Symbol symbol : MapAdjacencyList.getAdjacencyVariables()) {
			Network.clear();
			if(MapAliasTable.verifyIfAliasKeyExistsOnTable(symbol.getName().substring(1))) {
				int indexAdjacencyVariable = NetworkTable.getHeader().indexOf(symbol.getName().substring(1));
    			for(int index = 0; index < NetworkTable.getRows().size(); ++index) {
    				List<String> row = NetworkTable.getRows().get(index);
    				Network.addVertice(row.get(indexAdjacencyVariable), MapAliasTable.getAliasTableColumnHeaderType(symbol.getName().substring(1)));
    				Network.addAttribute(row.get(indexAdjacencyVariable), NetworkTable.getAttributes().get(NetworkTable.getHeader().get(indexAdjacencyVariable)));
    				for(int columnIndex = 0; columnIndex < row.size(); ++columnIndex) {
    			
    					if(columnIndex != indexAdjacencyVariable) {
    						Network.addVertice(row.get(columnIndex), MapAliasTable.getAliasTableColumnHeaderType(NetworkTable.getHeader().get(columnIndex)));
    						Network.addAttribute(row.get(columnIndex), NetworkTable.getAttributes().get(NetworkTable.getHeader().get(columnIndex)));
    						Network.addEdges(row.get(indexAdjacencyVariable), row.get(columnIndex));
    					}
    				}
    			}
			}
			
			Network.generateNetworks();
		}
	}
	
	public static void parseBipartiteProjection(Network network) {
		network.getBipartiteProjection();
		for(BipartiteProjection bipartiteProjection : MapBipartiteProjection.getBipartiteProjectionList()) {
			Network.clear();
			for(Symbol before : bipartiteProjection.getBeforeAt()) {
				String dataFrameColumnHeaderBefore = before.getName().substring(1);
				int indexDataFrameColumnHeaderBefore = NetworkTable.getHeader().indexOf(dataFrameColumnHeaderBefore);
				if(!MapAliasTable.verifyIfAliasKeyExistsOnTable(dataFrameColumnHeaderBefore)) {
					throw new Error("DataFrame ColumnHeader doesn't exists!");
				}
				
				for(Symbol after : bipartiteProjection.getAfterAt()) {
					String dataFrameColumnHeaderAfter = after.getName().substring(1);
					int indexDataFrameColumnHeaderAfter = NetworkTable.getHeader().indexOf(dataFrameColumnHeaderAfter);
					if(!MapAliasTable.verifyIfAliasKeyExistsOnTable(dataFrameColumnHeaderAfter)) {
						throw new Error("DataFrame ColumnHeader doesn't exists!");
					}
					    				
        			for(int index = 0; index < NetworkTable.getRows().size(); ++index) {
        				
        				List<String> row = NetworkTable.getRows().get(index);
        				String vertice1 = row.get(indexDataFrameColumnHeaderBefore);
        				Network.addVertice(vertice1, MapAliasTable.getAliasTableColumnHeaderType(dataFrameColumnHeaderBefore));
        				Network.addAttribute(vertice1, NetworkTable.getAttributes().get(NetworkTable.getHeader().get(indexDataFrameColumnHeaderBefore)));
        				for(int columnIndex = 0; columnIndex < row.size(); ++columnIndex) {
        					if(columnIndex != indexDataFrameColumnHeaderBefore && columnIndex == indexDataFrameColumnHeaderAfter) {
        						String vertice2 = row.get(columnIndex);
        	        			for(int index2 = index + 1; index2 < NetworkTable.getRows().size(); ++index2) {
        	        				List<String> row2 = NetworkTable.getRows().get(index2);
        	        				String vertice3 = row2.get(indexDataFrameColumnHeaderBefore);
        	        				Network.addVertice(vertice3, MapAliasTable.getAliasTableColumnHeaderType(dataFrameColumnHeaderBefore));
        	        				Network.addAttribute(vertice3, NetworkTable.getAttributes().get(NetworkTable.getHeader().get(indexDataFrameColumnHeaderBefore)));
        	        				for(int columnIndex2 = 0; columnIndex < row2.size(); ++columnIndex) {
        	        					if(columnIndex2 == indexDataFrameColumnHeaderBefore && !vertice1.equals(vertice3)) {
        	        						String vertice4 = row2.get(columnIndex);
        	        						if(vertice2.equals(vertice4)) {
        	        							Network.addEdges(vertice1, vertice3);
        	        						}
        	        					}
        	        				}
        	        			}		

        					}
        				}
        			}
				}
			}
			
			if(bipartiteProjection.getBeforeAt().size() > 1) {
				for(int i = 0; i < bipartiteProjection.getBeforeAt().size(); i++) {
					String dataFrameColumnHeaderBefore = bipartiteProjection.getBeforeAt().get(i).getName().substring(1);
					int indexDataFrameColumnHeaderBefore = NetworkTable.getHeader().indexOf(dataFrameColumnHeaderBefore);
					for(int j = i + 1; j < bipartiteProjection.getBeforeAt().size(); j++) {
						String dataFrameColumnHeaderBefore2 = bipartiteProjection.getBeforeAt().get(j).getName().substring(1);
						int indexDataFrameColumnHeaderBefore2 = NetworkTable.getHeader().indexOf(dataFrameColumnHeaderBefore2);
						
		    			for(int index = 0; index < NetworkTable.getRows().size(); ++index) {
		    				
		    				List<String> row = NetworkTable.getRows().get(index);
		    				Network.addVertice(row.get(indexDataFrameColumnHeaderBefore), MapAliasTable.getAliasTableColumnHeaderType(dataFrameColumnHeaderBefore));
		    				Network.addVertice(row.get(indexDataFrameColumnHeaderBefore2), MapAliasTable.getAliasTableColumnHeaderType(dataFrameColumnHeaderBefore2));
		    				Network.addEdges(row.get(indexDataFrameColumnHeaderBefore), row.get(indexDataFrameColumnHeaderBefore2));
		    				Network.addAttribute(row.get(indexDataFrameColumnHeaderBefore), NetworkTable.getAttributes().get(NetworkTable.getHeader().get(indexDataFrameColumnHeaderBefore)));
		    				Network.addAttribute(row.get(indexDataFrameColumnHeaderBefore2), NetworkTable.getAttributes().get(NetworkTable.getHeader().get(indexDataFrameColumnHeaderBefore2)));
		    			}
					}
				}
			}
			
			Network.generateNetworks();
		}
	}
	
	
	
	
    public static void parseHeaderToAliasTable(MapAliasTable aliasTable, MapDataFrame dataFrame) {
    	for(String columnHeader : NetworkTable.getHeader()) {
    		if(MapAliasTable.verifyIfAliasExistsOnTable(columnHeader)) {
    			NetworkTable.getHeader().
    				set(NetworkTable.getHeader().
    						indexOf(columnHeader), MapAliasTable.getAliasValueOnTable(columnHeader));
    		}
    	}
    }
    
    
    public static void parseDataFrame(MapAliasTable aliasTable, MapDataFrame dataFrame) {	
    	for(DataFrameComponent dataFrameComponent : MapDataFrame.getDataFrameComponentList()) {
    		if(MapAliasTable.verifyIfAliasKeyExistsOnTable(dataFrameComponent.getDataFrameColumnHeader().image.substring(1))) {
    			String vertice = dataFrameComponent.getDataFrameColumnHeader().image.substring(1);
    			String attribute = dataFrameComponent.getAttributeValue().image;
    			NetworkTable.addAttribute(vertice,attribute);
    			Network.addAttribute(vertice,attribute);
    			String header = dataFrameComponent.getDataFrameColumnHeader().image.substring(1);
	    		for(Command command : dataFrameComponent.getCommandList().getCommandList()) {
	    			for(int index = 0; index < NetworkTable.getRows().size(); ++index) {
	    				List<String> row = NetworkTable.getRows().get(index);
	    				if(command instanceof Return) {
	    					Return returnCommand = (Return) command;
	    					returnCommand.resolve(header, index);
	    				} else if(command instanceof Conditional) {
	    					Conditional conditionalCommand = (Conditional) command;
	    					conditionalCommand.resolve(header, index);	    					
	    				} else if(command instanceof Attribuition) {
	    					Attribuition attribuitionCommand = (Attribuition) command;
	    					attribuitionCommand.resolve(row);		    					
	    				}	    				
	    			}
	    		}
    		}
    	}   	
    }
    
    public static void parseRowsDeleting(MapAliasTable aliasTable, MapDataFrame dataFrame, MapRowsDeleting rowsDeleting) {
    	for(Expression expression : MapRowsDeleting.getRowsDeletingExpressions()) {
    		String dataFrameColumnHeader = expression.getExpressionPreFix().getFirst().getLexema().substring(1);
    		if(MapAliasTable.verifyIfAliasKeyExistsOnTable(dataFrameColumnHeader)) {
        		for(int index = 0; index < NetworkTable.getRows().size(); index++) {
        			List<String> row = NetworkTable.getRows().get(index);
            		Operand result = expression.resolve(row);
            		if(result.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString()) && 
        					result.getLexema().equals("true")) {
            			NetworkTable.getRows().remove(index);
            			
            			index--;
            		}    			
        		}
    		} else {
    			throw new Error("Alias doesn't exists on DataFrame declaration: " + expression.getExpressionPreFix().getFirst().getLexema());
    		}
    	}
    }

	public static void parserCsvToTable() throws Exception {
				
		try {
			Scanner scanner = new Scanner(new File(Configuration.csvFileInput));

			ArrayList<List<String>> rows = new ArrayList<List<String>>();
			List<String> header = parseLine(scanner.nextLine());
			
			while (scanner.hasNext()) {
				List<String> line = parseLine(scanner.nextLine());
				rows.add(line);
				
			}
			scanner.close();
			NetworkTable.setHeader(header);
			NetworkTable.setRows(rows);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {


        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        String line = cvsLine;
        char[] chars = line.toCharArray();
        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}
package intermediate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import error.SemanticError;

public class SemanticActions {

	public static int warnings = 0;
	
	public static void isWrongInterval (String vertexName, String range, double operandA, double operandB) {
		if(operandA > operandB) {
			throw new SemanticError("Invalid interval " + range + " at vertex "+ vertexName + " -> " + "x(" +operandA + ")" +" it's greater than " + "y("+operandB+")"); 
		}
		else if(operandA == operandB) {
			throw new SemanticError("Invalid interval " + range + " at vertex "+ vertexName + " -> " + "x(" +operandA + ")" +" it's equal to " + "y("+operandB+")"); 
		}
	}
	
	public static void inexistingColumn(String variable, int position){
		throw new SemanticError("Line " + position + " (MAP file)" + ": Column \""+ variable + "\" doesn't exist!"); 
	}
	
	public static void noNetwork(LinkedHashMap<Integer,ArrayList<String>> hashArcs ){
		boolean empty = true;
		for(ArrayList<?> teste : hashArcs.values()) {
			if(!teste.isEmpty()) empty = false;
		}
		if (empty) throw new SemanticError("Network without arc/edge - Invalid definition of vertices at section 3!"); 

	}
	
	public static void unusedVariable(VariableList variableList){
		for(Variable var : variableList) {
			if (!var.isVariablePrimaryKey() && !var.getUsed()) {
				System.out.println("Warning: variable " + var.variableName + " at line " + var.positionMap + " wasn't used");
				warnings++;
			}
		}
	}
	
	public static void containsVariable(Item variable, VariableList variableList, int position){
		boolean test = false;	
		for (Variable var : variableList) {
			if (var.getVariableName().equals(variable.token)) test = true;
		}
		if(!test) throw new SemanticError("Variable " + variable.token + " at line " + position + " it's not defined!"); 
	}
	
}

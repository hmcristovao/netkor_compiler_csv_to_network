package semantic;

import java.util.LinkedList;

import error.SemanticError;

public class SemanticActions {

	public static int warnings = 0;
	
	public static void isWrongInterval (String vertexName, String range, int operandA, int operandB) {
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
	
	//Terminar!
	public static void unusedVariable(VariableList variableList, LinkedList<Vertex> vertexList){
		for(Vertex expression : vertexList) {
			//for(Variable var; variableList.)
			System.out.println(expression.getExpression());	
		}
	}
	
}

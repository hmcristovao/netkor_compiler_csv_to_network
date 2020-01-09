package semantic;

import error.SemanticError;

public class SemanticActions {

	public static int warnings = 0;
	
	public static void isWrongInterval (String vertexName, String range, int operandA, int operandB) {
		if(operandA > operandB) {
			throw new SemanticError("Invalid interval " + range+ " at vertex "+ vertexName + " - " + operandA + " bigger than " + operandB); 
		}
		else if(operandA == operandB) {
			throw new SemanticError("Invalid interval " + range+ " at vertex "+ vertexName + " - " + operandA + " equal than " + operandB); 
		}
	}
}

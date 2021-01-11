package intermediate;

import java.util.Iterator;
import java.util.LinkedList;

import error.SemanticError;

public class VariableList implements Iterable<Variable> {
	LinkedList<Variable> variableList;
	Integer primaryKeyPosition;
	int currentSize = 0;
 	
	public VariableList() {
		this.variableList = new LinkedList<Variable>();
	}
	
	public void add(Variable variable) {
		for(Variable var : variableList)  {
			if(var.variableName.equals(variable.variableName)) {
				throw new SemanticError("Defined the same variable " + variable.variableName + " at line " + var.positionMap + " and " + variable.getPosition()+"!");	
			}
			else if(var.headNameInCsv.equals(variable.headNameInCsv)) {
				throw new SemanticError("Defined the same column " + variable.headNameInCsv + " at line " + var.positionMap + " and " + variable.getPosition()+"!");	
			}
		}
		this.currentSize++;
		this.variableList.add(variable);
	}
	
	public Variable getVariable(int position) {
		return this.variableList.get(position);
	}
	
	public void variableUsed(String variableName) {
		for (Variable var : this.variableList) {
	        if (var.getVariableName().equals(variableName)) {
	            var.setUsed();
	        }
	    }
	}
	
	public LinkedList<Variable> getVariableList(){
		return this.variableList;
	}
	
	public void setPrimaryKeyPosition(Integer positionValue) {
		this.primaryKeyPosition = positionValue;
	}
	
	public Integer getPrimaryKeyPosition() {
		return this.primaryKeyPosition;
	}
	
	public Integer getVariableColumnPosition(String variableName) {
		Integer position = null;
		for(Variable variable : this.getVariableList()) {
			if(variable.variableName.equals(variableName)) {
				position = variable.getColumn();
			}
		}
		return position;
	}

	@Override
	public Iterator<Variable> iterator() {
		 Iterator<Variable> it = new Iterator<Variable>() {
			 	private int currentIndex = 0;

	            @Override
	            public boolean hasNext() {
	               return currentIndex < currentSize && variableList.get(currentIndex) != null;
	            }

	            @Override
	            public Variable next() {
	                return variableList.get(currentIndex++);
	            }

	            @Override
	            public void remove() {
	                throw new UnsupportedOperationException();
	            }
	            
	        };
	        return it;
	    }
}


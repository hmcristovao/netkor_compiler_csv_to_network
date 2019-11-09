package semantic;

import java.util.Iterator;
import java.util.LinkedList;

public class VariableList implements Iterable<Variable> {
	LinkedList<Variable> variableList;
	Integer primaryKeyPosition;
	public int currentSize = 0;
 	
	public VariableList() {
		this.variableList = new LinkedList<Variable>();
	}
	
	public void add(Variable variable) {
		this.currentSize++;
		this.variableList.add(variable);
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


package semantic;

public class Variable extends Item {

	public VariableType type;
	public String headNameInCsv;
	public String variableName;

	public Variable(VariableType type, String headNameInCsv) {
		super(headNameInCsv);
		this.headNameInCsv = headNameInCsv;
		this.variableName = headNameInCsv;
		this.type = type;
	}
	public Variable(String headNameInCsv, String variableName, VariableType type) {
		super(variableName);
		this.headNameInCsv = headNameInCsv;
		this.variableName = variableName;
		this.type = type;
	}
	
	public Variable(String variableName) {
		super(variableName);
		this.variableName = variableName;
	}

	public String getLexema()
	{
		// retorna apenas o nome no csv, jah que eh opcional o variable_name
		return this.headNameInCsv;
	}
	
	public String getVariableName()
	{
		return this.variableName;
	}
	
	public void setVariableName(String name)
	{
		this.variableName = name;
	}
	
	
	public String getType()
	{
		return this.type.toString();
	}
	
	public String toString() {
		return this.variableName;
	}
}

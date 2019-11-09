package semantic;

public class Variable extends Item {

	public VariableType type;
	public String headNameInCsv;
	public String variableName;
	public Integer column = 0;

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
	public void setColumn(Integer position)
	{
		this.column = position;
	}
	
	public Integer getColumn()
	{
		return this.column;
	}
	
	public String getType()
	{
		return this.type.toString();
	}
	
	public String toString() {
		return this.variableName;
	}
	
	public boolean isVariablePrimaryKey() {
		if(this.type == VariableType.PRIMARY) {
			return true;
		}
		else {
			return false;
		}
	}
}

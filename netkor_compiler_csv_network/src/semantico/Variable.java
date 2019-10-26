package semantico;

import parser.Token;

public class Variable {

	public Token type;
	public Token head_name_in_csv;
	public Token variable_name;
	public Integer column;

	public Variable(Token head_name_in_csv, Token type) {
		this.head_name_in_csv = head_name_in_csv;
		this.variable_name = null;
		this.type = type;
		this.column = null;
	}
	public Variable(Token head_name_in_csv, Token variable_name, Token type) {
		this.head_name_in_csv = head_name_in_csv;
		this.variable_name = variable_name;
		this.type = type;
		this.column = null;
	}
	public String getLexema()
	{
		// retorna apenas o nome no csv, jah que eh opcional o variable_name
		return this.head_name_in_csv.image;
	}
	
	public String getVariableName()
	{
		return this.variable_name.image;
	}
	
	public void setVariableName(Token name)
	{
		this.variable_name = name;
	}
	
	public Integer getColumn()
	{
		return this.column;
	}
	
	public void setColumn(Integer columnNumber)
	{
		this.column = columnNumber;
	}
	
	public String getType()
	{
		return this.type.image;
	}
	
	public String toString() {
		return null;
	}
}

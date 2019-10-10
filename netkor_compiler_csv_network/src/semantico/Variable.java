package semantico;

import parser.Token;

public class Variable {

	public Token type;
	public Token head_name_in_csv;
	public Token variable_name;

	public Variable(Token head_name_in_csv, Token type) {
		this.head_name_in_csv = head_name_in_csv;
		this.variable_name = null;
		this.type = type;
	}
	public Variable(Token head_name_in_csv, Token variable_name, Token type) {
		this.head_name_in_csv = head_name_in_csv;
		this.variable_name = variable_name;
		this.type = type;
	}
	public String getLexema()
	{
		// retorna apenas o nome no csv, jah que eh opcional o variable_name
		return this.head_name_in_csv.image;
	}
	
	public String toString() {
		return null;
	}
}

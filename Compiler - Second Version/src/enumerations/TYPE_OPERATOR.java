package enumerations;

public enum TYPE_OPERATOR {
	CONCAT("&"),
	TRUE("true"),
	FALSE("false"),
	AND("AND"),
	OR("OR"),
	SUM("+"),
	SUBTRACTION("-"),
	MULTIPLICATION("*"),
	DIVISION("/"),
	NOT("NOT"),
	GREATER(">"),
	GREATER_EQUAL(">="),
	LESS("<"),
	LESS_EQUAL("<="),
	EQUAL("=="),
	PARENTHESIS_OPEN("("),
	PARENTHESIS_CLOSE(")");
	
	private String lexema;
	
	TYPE_OPERATOR(String lexema) {
		this.lexema = lexema;
	}
	
	TYPE_OPERATOR() {}
	
	public static TYPE_OPERATOR getTypeOfOperator(String operator) {
		for(TYPE_OPERATOR op : TYPE_OPERATOR.values()) {
			if(op.toString().equals(operator)) {
				return op;
			}
		}
		return null;
	}
	
	public String getLexema() {
		return lexema;
	}
}

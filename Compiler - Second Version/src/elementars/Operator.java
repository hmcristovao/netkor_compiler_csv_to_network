package elementars;

import enumerations.TYPE_OPERATOR;
import parser.Token;

public class Operator extends Item {

	private TYPE_OPERATOR operator;
	
	public Operator(Token token, TYPE_OPERATOR operator) {		
		super(token);
		this.operator = operator;
	}
	
	public Operator(Token token, String operator) {
		super(token);
		this.operator = TYPE_OPERATOR.getTypeOfOperator(operator);
		System.out.println(this.operator);
	}
	
	public TYPE_OPERATOR getOperator() {
		return operator;
	}

	@Override
	public String toString() {
		return "Operator [operator=" + operator + "]";
	}

}

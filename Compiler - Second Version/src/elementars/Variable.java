package elementars;

import enumerations.TYPE_OPERAND;

public class Variable {

	private String name;
	private Expression expression;
	private TYPE_OPERAND type;
	private Operand value;
	
	public Variable(String name, Expression expression, TYPE_OPERAND type) {
		super();
		this.name = name;
		this.expression = expression;
		this.type = type;
		this.value = null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Expression getExpression() {
		return expression;
	}
	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public TYPE_OPERAND getType() {
		return type;
	}
	
	public void setType(TYPE_OPERAND type) {
		this.type = type;
	}
	
	public Operand getValue() {
		return value;
	}
	
	public void setValue(Operand value) {
		this.value = value;
	}
	
}

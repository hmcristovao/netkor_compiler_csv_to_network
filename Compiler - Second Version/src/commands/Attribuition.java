package commands;

import java.util.List;

import elementars.Expression;
import elementars.Operand;
import elementars.Symbol;
import elementars.Table;
import parser.Token;

public class Attribuition extends Command {
	
	private Symbol variable;
	private Expression expression;
	private Operand result;
	
	public Attribuition(Symbol variable, Expression expression, Token token) {
		this.setToken(token);
		this.variable = variable;
		this.expression = expression;
		Table.insertVariable(variable, expression);
		this.result = null;
	}
	
	public Operand getResult() {
		return result;
	}

	public void setResult(Operand result) {
		this.result = result;
	}

	public void setVariable(Symbol variable) {
		this.variable = variable;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Symbol getVariable() {
		return variable;
	}

	public Expression getExpression() {
		return expression;
	}
	
	public void resolve(List<String> row) {
		Operand item = (Operand) expression.resolve(row);
		this.result = item;
		Table.getSymbol(this.variable.getName()).setValue(item);
	}

	@Override
	public String toString() {
		return "Attribuition Command: " + "\n"
				+ "Symbol: " + this.getVariable().toString() + "\n"
				+ "Expression: " + this.getExpression().toString();
	}	
}

package commands;

import java.util.List;

import elementars.Expression;
import elementars.Operand;
import enumerations.TYPE_OPERAND;
import intermediate.MapAliasTable;
import network.NetworkTable;

public class Return extends Command {
	
	private Expression expression;
	
	public Return(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public void resolve(String header, int indexOfRow) {
		int indexOfHeader = NetworkTable.getHeader().indexOf(header);
		List<String> row = NetworkTable.getRows().get(indexOfRow);		
		Operand result = expression.resolve(row);
		if(MapAliasTable.verifyIfAliasKeyExistsOnTable(result.getLexema()) && result.getOperand().
				toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())) {		
			row.set(indexOfHeader, row.get(indexOfHeader));
		} else {
			row.set(indexOfHeader, result.getLexema());
		}
		NetworkTable.getRows().set(indexOfRow, row);
	}

	@Override
	public String toString() {
		return "Return [expression=" + expression + "]";
	}
}

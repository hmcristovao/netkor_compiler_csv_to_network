package intermediate;

import java.util.ArrayList;

import elementars.Expression;

// Componentizar -> MapRowsDeletingComponent
public class MapRowsDeleting {
	private static ArrayList<Expression> rowsDeletingExpressions;
	
	public MapRowsDeleting() {
		MapRowsDeleting.rowsDeletingExpressions = new ArrayList<Expression>();
	}
	
	public void addExpressionToRowsDeleting(Expression expression) {
		rowsDeletingExpressions.add(expression);
	}

	public static ArrayList<Expression> getRowsDeletingExpressions() {
		return rowsDeletingExpressions;
	}

	public void setRowsDeletingExpressions(ArrayList<Expression> rowsDeletingExpressions) {
		MapRowsDeleting.rowsDeletingExpressions = rowsDeletingExpressions;
	}

	@Override
	public String toString() {
		return "MapRowsDeleting [rowsDeletingExpressions=" + rowsDeletingExpressions + "]";
	}
		
}

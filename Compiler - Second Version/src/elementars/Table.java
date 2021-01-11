package elementars;

import java.util.LinkedHashMap;

import enumerations.TYPE_OPERAND;
import parser.Token;

public class Table {
	private static LinkedHashMap<String, Variable> table;
	
	public Table() {
		Table.table = new LinkedHashMap<String, Variable>();
	}

	public static LinkedHashMap<String, Variable> getTable() {
		return table;
	}

	public static void insertVariable(Symbol symbol, Expression expression) {
		Table.table.put(symbol.getName(), new Variable(symbol.getName(), expression, symbol.getType()));
	}
	
	public static boolean verifyIfTableContainsVariable(String key) {
		return Table.table.containsKey(key);
	}
	
	public static int getTableSize() {
		return Table.table.size();
	}
	
	public static Variable getSymbol(String key) {
		if(verifyIfTableContainsVariable(key)) {
			return Table.table.get(key);
		} else {
			throw new Error("This symbol doesn't exist at table!");
		}
	}
	
	public static TYPE_OPERAND getSymbolType(String key) {
		return Table.getSymbol(key).getType();
	}
	
	public void insertVariableAtTable(Token token, TYPE_OPERAND type, Expression expression) {
		if (!Table.verifyIfTableContainsVariable(token.image)) {
			Symbol symbol = new Symbol(token, type);
			Table.insertVariable(symbol, expression);
		} else {
			throw new Error("Variable already exists: " + token.image);
		}
	}
	
	public void verifyIfVariableWasDeclared(String variableName) {
		if(!Table.verifyIfTableContainsVariable(variableName)) {
			throw new Error("Variable doesn't exist: " + variableName);
		}
	}

	@Override
	public String toString() {
		return "Table [table=" + table + "]";
	}
}

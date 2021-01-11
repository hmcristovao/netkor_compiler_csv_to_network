package network;

import java.util.ArrayList;

import enumerations.TYPE_OPERAND;

public class NetworkTableColumn {
	
	private TYPE_OPERAND type;
	private String name;
	private ArrayList<String> rows;
	
	public NetworkTableColumn(TYPE_OPERAND type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	
	public void addRow(String row) {
		this.rows.add(row);
	}

	public ArrayList<String> getRows() {
		return rows;
	}

	public TYPE_OPERAND getType() {
		return type;
	}

	public String getName() {
		return name;
	}	
}

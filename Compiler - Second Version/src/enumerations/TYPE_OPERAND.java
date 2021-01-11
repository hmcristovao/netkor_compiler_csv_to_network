package enumerations;

public enum TYPE_OPERAND {
	TEXT("TEXT"),
	NUMBER("NUMBER"),
	BOOLEAN("BOOLEAN"),
	DATE("DATE"),
	ALIASTABLECOLUMNHEADER("ALIASTABLECOLUMNHEADER"),
	DATAFRAMECOLUMNHEADER("DATAFRAMECOLUMNHEADER");
	
	private String lexema;
	
	TYPE_OPERAND(String lexema) {
		this.lexema = lexema;
	}
	
	TYPE_OPERAND() {}
	
	public String getLexema() {
		return lexema;
	}
}

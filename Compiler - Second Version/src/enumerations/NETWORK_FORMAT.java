package enumerations;

public enum NETWORK_FORMAT {
	GDF("GDF"),
	NET("NET");
	
	private String lexema;
	
	NETWORK_FORMAT(String lexema) {
		this.lexema = lexema;
	}
	
	public String getLexema() {
		return lexema;
	}
}

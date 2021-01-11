package enumerations;

public enum SIGNAL {
	PLUS("+"),
	MINUS("-");
	
	private String lexema;
	
	SIGNAL(String lexema) {
		this.lexema = lexema;
	}
	
	public String getLexema() {
		return lexema;
	}
}

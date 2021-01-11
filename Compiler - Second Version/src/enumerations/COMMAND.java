package enumerations;

public enum COMMAND {
	ATTRIBUITION("="),
	IF("if"),
	ELIF("elif"),
	ELSE("else"),
	RETURN("return");
	
	private String lexema;
	
	COMMAND(String lexema) {
		this.lexema = lexema;
	}
	
	public String getLexema() {
		return lexema;
	}

}

package commands;

import parser.Token;

public abstract class Command {
	private Token token;
	
	public String getLexeme() {
		return token.image;
	}
	
	public Token getToken() {
		return token != null ? token : null;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public abstract String toString();
	
}

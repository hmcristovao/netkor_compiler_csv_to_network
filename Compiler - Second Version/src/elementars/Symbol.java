package elementars;

import enumerations.TYPE_OPERAND;
import parser.Token;

public class Symbol {
	
	private String name;
	private TYPE_OPERAND type;
	private Token token;
	
	public Symbol(Token token, TYPE_OPERAND type) {
		this.token = token;
		this.type = type;
		this.name = token.image;
	}
	
	public String getName() {
		return name;
	}

	public TYPE_OPERAND getType() {
		return type;
	}

	public Token getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "Symbol [name=" + name + ", type=" + type + ", token=" + token + "]";
	}
	
}

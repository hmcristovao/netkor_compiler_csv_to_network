package semantic;

public abstract class Item {

	public String token;

	public Item(String token) {
		this.token = token.strip();
	}
	public String getLexema() {
		return this.token;
	}
	
	public abstract String toString();
}
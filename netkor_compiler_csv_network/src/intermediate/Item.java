package intermediate;

public abstract class Item {

	public String token;

	public Item(String token) {
		this.token = token.trim();
	}
	public String getLexema() {
		return this.token;
	}
	
	public abstract String toString();
}
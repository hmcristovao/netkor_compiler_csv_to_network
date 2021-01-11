package intermediate;

import java.util.ArrayList;

import elementars.Symbol;

public class BipartiteProjection {
	
	private ArrayList<Symbol> beforeAt;
	private ArrayList<Symbol> afterAt;
		
	public BipartiteProjection(ArrayList<Symbol> beforeAt, ArrayList<Symbol> afterAt) {
		super();
		this.beforeAt = beforeAt;
		this.afterAt = afterAt;
	}
	
	public ArrayList<Symbol> getBeforeAt() {
		return beforeAt;
	}
	public void setBeforeAt(ArrayList<Symbol> beforeAt) {
		this.beforeAt = beforeAt;
	}
	public ArrayList<Symbol> getAfterAt() {
		return afterAt;
	}
	public void setAfterAt(ArrayList<Symbol> afterAt) {
		this.afterAt = afterAt;
	}

	@Override
	public String toString() {
		return "BipartiteProjection [beforeAt=" + beforeAt + ", afterAt=" + afterAt + "]";
	}
	
	
}

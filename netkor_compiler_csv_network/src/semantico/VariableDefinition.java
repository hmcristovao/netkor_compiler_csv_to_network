package semantico;

import java.util.LinkedList;

public class VariableDefinition {

	LinkedList<Variable> tabela;

	public VariableDefinition() {
		this.tabela = new LinkedList<Variable>();
	}
	public LinkedList<Variable> getListaExpPosFixa() {
		return this.tabela;
	}

	public void addTabela(Variable var) {
		this.tabela.add(var);
	}

	public void imprimeExpressao() {
		System.out.println(this.tabela);
	}
}
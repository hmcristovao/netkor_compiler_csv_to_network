package intermediate;

import java.util.LinkedList;

public class Vertex {
	
	String vertexName;
	boolean isVariable;
	boolean isUsed = false;
	LinkedList<Item> expression;
	
	public Vertex(String name) {
		this.vertexName = name;
		this.isVariable = false;
		this.expression = new LinkedList<Item>();
	}
	
	public Vertex(String name, boolean variable) {
		this.vertexName = name;
		this.isVariable = variable;
		this.expression = new LinkedList<Item>();
	}
	
	public void add(Item item) {
		this.expression.add(item);
	}
	
	public LinkedList<Item> getExpression(){
		return this.expression;
	}
	
	public String getVertexName(){
		return this.vertexName;
	}
	
	public boolean isVertexVariable(){
		return this.isVariable;
	}
	
	public boolean isUsed(){
		return this.isUsed;
	}
	
	public void setIsUsed(){
		this.isUsed = true;
	}
	
	public void addInterval(String range, Item variable) {
		range = range.replaceAll(" ","");
		Item item;
		String operandA;
		String operandB;
		if(range.startsWith("(") && range.endsWith("...)")) {	
			//Ajustando para ter apenas a componente x (retirando os "(" e "...)" do intervalo )	
			operandA = (String) range.subSequence(1, range.indexOf("..."));
			item = new Operand(OperandType.NUMBER,operandA);
			this.expression.add(item);
			item = new Operator(">");
			this.expression.add(item);
		}
	    //=========================Case [x...)======================
	    else if(range.startsWith("[") && range.endsWith("...)")) {
			//Ajustando para ter apenas a componente x (tirando "[" e "...)" do intervalo )	
	    	operandA = (String) range.subSequence(1, range.indexOf("..."));
			item = new Operand(OperandType.NUMBER,operandA);
	    	this.expression.add(item);
			item = new Operator(">=");
			this.expression.add(item);	
		}
	    //=========================Case (...x)======================
   		else if(range.startsWith("(...") && range.endsWith(")")) {
			//Ajustando para ter apenas a componente x (tirando "(..." e ")" do intervalo )	
   			operandA = (String) range.subSequence(range.indexOf("...")+3, range.indexOf(")"));
			item = new Operand(OperandType.NUMBER,operandA);		
   			this.expression.add(item);
			item = new Operator("<");
			this.expression.add(item);		
		}
	    //=========================Case (...x]======================
		else if(range.startsWith("(...") && range.endsWith("]")) {
			//Ajustando para ter apenas a componente x (tirando "(..." e "]" do intervalo )	
			operandA = (String) range.subSequence(range.indexOf("...")+3, range.indexOf("]"));
			item = new Operand(OperandType.NUMBER,operandA);
			this.expression.add(item);
			item = new Operator("<=");
			this.expression.add(item);
		}
	    //==============Case (x...y) or [x...y] or (x...y] or [x...y)================
		else if(range.contains("...")) {
			//Separando x e y em range[0] e range[1] respectivamente a partir da substring "..."
			//teremos entao:	range[0] = "(x"	 e	range[1] = "y)" 	
			
			//String destinada a armazenar a variavel do .map (SC2.idade, por exemplo)
			//correspondente a expressao tratada no caso (variavel = [x...y])
			//============================Case (x...y)=========================
			if(range.startsWith("(") && range.endsWith(")")) {	
				operandA = (String) range.subSequence(1, range.indexOf("..."));
				item = new Operand(OperandType.NUMBER,operandA);
				this.expression.add(item);
				item = new Operator(">");
				this.expression.add(item);
				//Como o intervalo eh composto de dois operandos (x e y), portanto a expressao final 	
				//precisa ter um formato tipo [variavel, operandoA, >, variavel, operandoB, <]
				//Ou seja, a variavel precisa ser inserida novamente na lista 
				//Assim como operandoB, a operacao ("<") e finalmente o AND ja que se trata de um intervalo
				this.expression.add(variable);
				operandB = (String) range.subSequence(range.indexOf("...")+3, range.indexOf(")"));
				item = new Operand(OperandType.NUMBER,operandB);
				this.expression.add(item);
				item = new Operator("<");
				this.expression.add(item);
				item = new Operator("AND");
				this.expression.add(item);
				SemanticActions.isWrongInterval(this.vertexName, range, Double.valueOf(operandA), Double.valueOf(operandB));
			}
			//============================Case [x...y)=========================
			else if(range.startsWith("[") && range.endsWith(")")) {
				//Comportamento analogo a condicao acima, mudando apenas o "["
				operandA = (String) range.subSequence(1, range.indexOf("..."));
				item = new Operand(OperandType.NUMBER,operandA);
				this.expression.add(item);
				item = new Operator(">=");
				this.expression.add(item);
				//Como o intervalo eh composto de dois operandos (x e y), portanto a expressao final 	
				//precisa ter um formato tipo [variavel, operandoA, >, variavel, operandoB, <]
				//Ou seja, a variavel precisa ser inserida novamente na lista 
				//Assim como operandoB, a operacao ("<") e finalmente o AND ja que se trata de um intervalo
				this.expression.add(variable);
				operandB = (String) range.subSequence(range.indexOf("...")+3, range.indexOf(")"));
				item = new Operand(OperandType.NUMBER,operandB);
				this.expression.add(item);
				item = new Operator("<");
				this.expression.add(item);
				item = new Operator("AND");
				this.expression.add(item);
				SemanticActions.isWrongInterval(this.vertexName, range, Double.valueOf(operandA), Double.valueOf(operandB));
			}
			//============================Case (x...y]=========================
			else if(range.startsWith("(") && range.endsWith("]")) {
				//Comportamento analogo a condicao acima, mudando apenas o "]"
				operandA = (String) range.subSequence(1, range.indexOf("..."));
				item = new Operand(OperandType.NUMBER,operandA);
				this.expression.add(item);
				item = new Operator(">");
				this.expression.add(item);
				//Como o intervalo eh composto de dois operandos (x e y), portanto a expressao final 	
				//precisa ter um formato tipo [variavel, operandoA, >, variavel, operandoB, <]
				//Ou seja, a variavel precisa ser inserida novamente na lista 
				//Assim como operandoB, a operacao ("<") e finalmente o AND ja que se trata de um intervalo
				this.expression.add(variable);
				operandB = (String) range.subSequence(range.indexOf("...")+3, range.indexOf("]"));
				item = new Operand(OperandType.NUMBER,operandB);
				this.expression.add(item);
				item = new Operator("<=");
				this.expression.add(item);
				item = new Operator("AND");
				this.expression.add(item);
				SemanticActions.isWrongInterval(this.vertexName, range, Double.valueOf(operandA), Double.valueOf(operandB));
			}
			//============================Case [x...y]=========================
			else if(range.startsWith("[") && range.endsWith("]")) {
				//Comportamento analogo a condicao acima, mudando apenas "[" e "]"
				operandA = (String) range.subSequence(1, range.indexOf("..."));
				item = new Operand(OperandType.NUMBER,operandA);
				this.expression.add(item);
				item = new Operator(">=");
				this.expression.add(item);
				//Como o intervalo eh composto de dois operandos (x e y), portanto a expressao final 	
				//precisa ter um formato tipo [variavel, operandoA, >, variavel, operandoB, <]
				//Ou seja, a variavel precisa ser inserida novamente na lista 
				//Assim como operandoB, a operacao ("<") e finalmente o AND ja que se trata de um intervalo
				this.expression.add(variable);
				operandB = (String) range.subSequence(range.indexOf("...")+3, range.indexOf("]"));
				item = new Operand(OperandType.NUMBER,operandB);
				this.expression.add(item);
				item = new Operator("<=");
				this.expression.add(item);
				item = new Operator("AND");
				this.expression.add(item);
				SemanticActions.isWrongInterval(this.vertexName, range, Double.valueOf(operandA), Double.valueOf(operandB));
			}
		}
	}

	//Metodo que recebe a linha do CSV e verifica se a expressao passada eh verdadeira para esta linha.
	
	//-===A pilha recebera tokens ate alcancar uma operacao, neste ponto ele realizara a comparacao entre o valor da expressao
	//-===e o valor contido na linha CSV. O operando da expressao, a operacao e a variavel sao desempilhados e empilha-se
	//-===o valor booleano da comparacao, se houver algum condicional, os dois ultimos valores booleanos serao avaliados
	//-===Caso haja falso no resultado final do processamento, portanto a expressao eh invalida para esta linha.
	
	//-=========a lista expression sempre tera um formato do tipo:		[variavel, operando, operacao, ...]
	
	public boolean verifierCsvExpression(String[] columnsCSV, VariableList variableList, NetDefinition definition) {
		Item item;
		Item operandExpression, operandCsv = null;
		Integer counter = 0;
		String valueCsv;
		//Pilha que empilhara cada token contido 
		LinkedList<Item> stack = new LinkedList<Item>();
		for(Item token : this.expression) {
			//Caso token OR, entao deve ser analisadas as ultimas duas posicoes da pilha
			//Se alguma das posicoes nao for falsa, portanto remove-se os dois valores booleanos anteriores
			//e eh empilhada true, se nao, a expressao esta incorreta para esta linha do csv e sai do metodo
			if(token instanceof Operator) {
				Operator operator = (Operator) token;			
				operandExpression = (stack.get(counter-1));
				//Se o operador lido for um operador logico
				//Portanto as duas ultimas posicoes da pilha serao operandos booleanos
				if(operator.getOperatorType().equals(OperatorType.OR) || operator.getOperatorType().equals(OperatorType.AND)) {
					operandCsv = new Operand(OperandType.BOOLEAN,(stack.get(counter-2)).getLexema());
					}
				//Se nao deve ser pego o valor correspondente ao operando da coluna no csv
				else {
					valueCsv = columnsCSV[variableList.getVariableColumnPosition(stack.get(counter-2).getLexema())];
					if(valueCsv.trim().isEmpty()) {
						operandCsv = new Operand(OperandType.NUMBER,("999"));
					}
					else {
						operandCsv = new Operand(OperandType.NUMBER,(valueCsv));
					}
				}
				if(operation(operandExpression, operandCsv, operator, definition)) {
					stack.remove(counter-1);
					stack.remove(counter-2);
					counter = counter -2;
					item = new Operand(OperandType.BOOLEAN, "true");
					stack.add(item);			
				}
				else {
					stack.remove(counter-1);
					stack.remove(counter-2);
					item = new Operand(OperandType.BOOLEAN, "false");
					stack.add(item);
					counter = counter -2;
				}	
			}
			else {
				stack.add(token);
				if(token instanceof Variable && !((Variable) token).isVariablePrimaryKey()) {
					variableList.variableUsed(((Variable) token).variableName);
				}
			}
			counter++;
		}
		if(stack.element().getLexema().equals("false")) return false;
		return true;
	}
	
	//Metodo que realiza a comparacao do valor do CSV, o valor da expressao e a operacao correspondente
	public static boolean operation(Item valueExpression, Item valueCsv, Operator operation, NetDefinition definition) {	
		if(operation.getOperatorType().equals(OperatorType.GREATER_EQUAL)) {
			if(Double.valueOf(valueCsv.getLexema().replace(",", ".")) >= Double.valueOf(valueExpression.getLexema())) return true;
		}
		else if(operation.getOperatorType() == OperatorType.GREATER) {
			if(Double.valueOf(valueCsv.getLexema().replace(",", ".")) > Double.valueOf(valueExpression.getLexema())) return true;
			}
		else if(operation.getOperatorType().equals(OperatorType.LESSER_EQUAL)) {
			if(Double.valueOf(valueCsv.getLexema().replace(",", ".")) <= Double.valueOf(valueExpression.getLexema())) return true;
				}
		else if(operation.getOperatorType().equals(OperatorType.LESSER)) {
			if(Double.valueOf(valueCsv.getLexema().replace(",", ".")) < Double.valueOf(valueExpression.getLexema())) return true;
		}
		else if(operation.getOperatorType().equals(OperatorType.EQUAL)) {
			if(Double.valueOf(valueCsv.getLexema().replace(",", ".")).equals(Double.valueOf(valueExpression.getLexema()))) return true;
		}
		else if(operation.getOperatorType().equals(OperatorType.OR)) {
			if(String.valueOf(valueCsv).equals("true") || String.valueOf(valueExpression).equals("true"))	return true;
		}
		else if(operation.getOperatorType().equals(OperatorType.AND)) {
			if(String.valueOf(valueCsv).equals("true") && String.valueOf(valueExpression).equals("true"))  return true;
		}
		return false;
	}
}

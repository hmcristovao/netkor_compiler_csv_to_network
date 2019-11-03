package semantico;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.text.html.HTMLEditorKit.Parser;

import parser.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Vertex {

	LinkedList<String> listaExpPosFixa;
	String vertice_name;
	Integer vertice_number;
	ArrayList<Integer> relational = new ArrayList<Integer>();
	
	public Vertex() {
		this.listaExpPosFixa = new LinkedList<String>();
	}
	
	public void setVertice_name(String vertice_name) {
		this.vertice_name = vertice_name;
	}
	
	public boolean operation(int valueExpression, int valueCsv, String operation) {
		if(operation == ">=") {
			if(valueCsv >= valueExpression){
				return true;
			}
		}
		else if(operation == ">") {
			if(valueCsv > valueExpression){
				return true;
			}	}
		else if(operation == "<=") {
			if(valueCsv <= valueExpression){
				return true;
			}}
		else if(operation == "<") {
			if(valueCsv < valueExpression) {
				return true;
			}}
		else if(operation == "=") {
			if(valueCsv == valueExpression) {
				return true;
			}
		}	
		return false;
	}
	
	public boolean ajust1(ArrayList<String> expression, String[] columnsCSV, LinkedHashMap<String,Integer> columnPosition, HashMap<String,String> hashPosition  ) {
		String operation = "";
		Integer operand; 
		Integer counter = 0;
		ArrayList<String> pilha = new ArrayList<String>();
		//System.out.println(columnsCSV);
		for(String internList : expression) {
			if(internList == "OR") {
				if((pilha.get(counter-1) == "false") && (pilha.get(counter-2) == "false")) {
					return false;
				}
				else {
					pilha.remove(counter-1);
					pilha.remove(counter-2);
					pilha.add("true");
					counter = counter-2;
				}
			}
			else if(internList == "AND") {
				if((pilha.get(counter-1) == "false") ||  (pilha.get(counter-2) == "false")) {
					return false;
				}
				else {
					pilha.remove(counter-1);
					pilha.remove(counter-2);
					pilha.add("true");
					counter = counter-2;
				}
			}
			else if(((((internList == "=" ) || (internList == ">" )) || (internList == ">=" )) || (internList == "<" )) || (internList == "<=" )) {
				Integer valueCsv; 
				operation = internList;
				operand = Integer.valueOf(pilha.get(counter-1));
				if(columnsCSV[(columnPosition.get(pilha.get(counter-2)))].isBlank()) valueCsv = 0;
				else {
					valueCsv = Integer.valueOf(columnsCSV[(columnPosition.get(pilha.get(counter-2)))]);
				}
				//Integer valueCsv = Integer.valueOf(columnsCSV[(columnPosition.get(pilha.get(counter-2)))]);
				//System.out.println(columnPosition.get(pilha.get(counter-2)) + " --- " +pilha.get(counter-2) + "  "+ valueCsv);
				if(operation(operand,valueCsv, operation)) {
					pilha.remove(counter-1);
					pilha.remove(counter-2);
					counter = counter -2;
					pilha.add("true");
				}
				else {
					//System.out.println(pilha +"\n" +operand + " " + operation + " " + valueCsv);
					pilha.remove(counter-1);
					pilha.remove(counter-2);
					pilha.add("false");
					counter = counter -2;
				}	
			}
			else {
				pilha.add(internList);
			}
			counter++;
		}
		if(pilha.contains("false")) return false;
		return true;	
	}
	
	//Metodo responsavel em tratar as expressoes com range de um token unico com "..." 
	//					Exemplos: ("(...x)", "[x...)","[x...y]", etc.)
	
	//-==O metodo consiste de receber cada expressao da lista de vertices e percorrer por meio de um listIterator
	//-==para encontrar se ha um intervalo de "...", portanto caracteriza uma expressao de token unico como "(...x)" 
	//-==Se for o caso, entao ha um processamento para cada tipo de token ajustando 
	
	//-====Exemplo de entrada: [variavel1, (x...y)], o listInterator percorre ate a posicao (x...y) e faz o processamento   
	public void ajust(ArrayList<String> list) {
		//Variaveis correspondentes para pegar os valores dos intervalos (x e y de [x...y])
		String operandA = ""; 
		String operandB = "";
		
		//Ao adicionar ou remover elementos diretamente de estruturas como ArrayList ou LinkedList
		//internamente em um loop como o ocorre abaixo, eh acusado erro de java.util.ConcurrentModificationException
		//Portanto deve ser utilizado um ListIterator para fazer esse tratamente de forma segura
		ListIterator<String> iter = list.listIterator();
		while(iter.hasNext()){
		    String internList = iter.next();
		    internList = internList.replaceAll(" ", "");
			operandA = internList;
		    //=========================Case (x...)======================
		    if(internList.startsWith("(") && internList.endsWith("...)")) {	
				//Ajustando para ter apenas a componente x (retirando os "(" e "...)" do intervalo )	
				iter.remove();
				operandA = operandA.replace("...)", "");
				operandA = operandA.replace("(", "");
				//Primeiro eh adicionado o operando e depois o operador na lista de expressao
				iter.add(operandA);
				iter.add(">");
			}
		    //=========================Case [x...)======================
		    else if(internList.startsWith("[") && internList.endsWith("...)")) {
				//Ajustando para ter apenas a componente x (tirando "[" e "...)" do intervalo )	
				iter.remove();
				operandA = operandA.replace("...)", "");
				operandA = operandA.replace("[", "");
				iter.add(operandA);
				iter.add(">=");
			}
		    //=========================Case (...x)======================
    		else if(internList.startsWith("(...") && internList.endsWith(")")) {
				//Ajustando para ter apenas a componente x (tirando "(..." e ")" do intervalo )	
				iter.remove();
				operandA = operandA.replace("(...", "");
				operandA = operandA.replace(")", "");
				//Adicionando x na lista de expressao e ">"
				iter.add(operandA);
				iter.add("<");
			}
		    //=========================Case (...x]======================
			else if(internList.startsWith("(...") && internList.endsWith("]")) {
				//Ajustando para ter apenas a componente x (tirando "(..." e "]" do intervalo )	
				iter.remove();
				operandA = operandA.replace("(...", "");
				operandA = operandA.replace("]", "");
				//Adicionando x na lista de expressao e ">"
				iter.add(operandA);
				iter.add("<=");
			}
		    //==============Case (x...y) or [x...y] or (x...y] or [x...y)================
			else if(internList.contains("...")) {
				//Separando x e y em range[0] e range[1] respectivamente a partir da substring "..."
				//teremos entao:	range[0] = "(x"	 e	range[1] = "y)" 	
				String[] range = operandA.split("\\.{3}");;
				
				//String destinada a armazenar a variavel do .map (SC2.idade, por exemplo)
				//correspondente a expressao tratada no caso (variavel = [x...y])
				String variable = "";
				//============================Case (x...y)=========================
				if(internList.startsWith("(") && internList.endsWith(")")) {	
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "(" e ")" do intervalo )	
					operandA = range[0];
					operandA = operandA.replace("(", "");
					operandB = range[1];
					operandB = operandB.replace(")", "");
					
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operandA);
					iter.add(">");
					//Como o intervalo eh composto de dois operandos (x e y), portanto a expressao final 	
					//precisa ter um formato tipo [variavel, operandoA, >, variavel, operandoB, <]
					//Ou seja, a variavel precisa ser inserida novamente na lista 
					//Assim como operandoB, a operacao ("<") e finalmente o AND ja que se trata de um intervalo
					iter.add(variable);
					iter.add(operandB);
					iter.add("<");
					iter.add("AND");
				}
				//============================Case [x...y)=========================
				else if(internList.startsWith("[") && internList.endsWith(")")) {
					//Ajustando para ter apenas a componente x (tirando "[" e "...)" do intervalo )	
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "[", ")" e "..." do intervalo )	
					operandA = range[0];
					operandA = operandA.replace("[", "");
					operandB = range[1];
					operandB = operandB.replace(")", "");
						
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operandA);
					iter.add(">=");
					
					iter.add(variable);
					iter.add(operandB);
					iter.add("<");
					iter.add("AND");
				}
				//Case (...x)
				else if(internList.startsWith("(") && internList.endsWith("]")) {
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "(", ")" e "..." do intervalo )	
					operandA = range[0];
					operandA = operandA.replace("(", "");
					operandB = range[1];
					operandB = operandB.replace("]", "");
						
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operandA);
					iter.add(">");
					
					iter.add(variable);
					iter.add(operandB);
					iter.add("<=");
					iter.add("AND");
				}
				//Case (...x]
				else if(internList.startsWith("[") && internList.endsWith("]")) {
					//Ajustando para ter apenas a componente x (tirando "(" e "...)" do intervalo )	
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "(", ")" e "..." do intervalo )	
					operandA = range[0];
					operandA = operandA.replace("[", "");
					operandB = range[1];
					operandB = operandB.replace("]", "");
						
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operandA);
					iter.add(">=");
					
					iter.add(variable);
					iter.add(operandB);
					iter.add("<=");
					iter.add("AND");
				}
			}
		}
	}
	
	public String getVertice_name() {
		return this.vertice_name;
	}
	
	public Integer getVerticeNumber() {
		return this.vertice_number;
	}
	
	public void setVerticeNumber(Integer number) {
		this.vertice_number = number;
	}
	
	public LinkedList<String> getLista() {
		return this.listaExpPosFixa;
	}
	/*
	public void addListaExpPosFixa(String var) {
		if(var==")" || var == "]") {
			Integer integerLastPosition = this.listaExpPosFixa.size()-1;
			String lastPosition = this.listaExpPosFixa.get(integerLastPosition);
			Integer integerSecondPositionRange = this.listaExpPosFixa.size()-2;
			String secondPosition = this.listaExpPosFixa.get(integerSecondPositionRange);
			if(lastPosition == "...") {
				Integer integerFirstPosition = this.listaExpPosFixa.size()-3;
				String firstPosition = this.listaExpPosFixa.get(integerFirstPosition);
				if(firstPosition == "(") {
					this.listaExpPosFixa.remove(firstPosition);
					this.listaExpPosFixa.remove(lastPosition);
					this.listaExpPosFixa.add(">");
				}
				else if(firstPosition == "[") {
					this.listaExpPosFixa.remove(firstPosition);
					this.listaExpPosFixa.remove(lastPosition);
					this.listaExpPosFixa.add(">=");		
				}
				else {
					Integer integerFirstPositionRange = this.listaExpPosFixa.size()-4;
					String firstPositionRange = this.listaExpPosFixa.get(integerFirstPositionRange);
					Integer option2 = Integer.valueOf(secondPosition);
					secondPosition = Integer.toString(--option2);
					if(firstPositionRange == "(") {
						this.listaExpPosFixa.remove(firstPositionRange);
						this.listaExpPosFixa.remove(lastPosition);
					}
					else {
						this.listaExpPosFixa.remove(firstPositionRange);
						this.listaExpPosFixa.remove(lastPosition);
					}
					if(firstPositionRange == "[" && var == ")") {
						this.listaExpPosFixa.set(integerSecondPositionRange-1, secondPosition);
						this.listaExpPosFixa.add(">=");
					}
					else if(firstPositionRange == "(" && var == ")") {
						this.listaExpPosFixa.set(integerSecondPositionRange-1, secondPosition);
						this.listaExpPosFixa.add(">");
					}
					else if(firstPositionRange == "(" && var == "]") {
						this.listaExpPosFixa.add(">");
					}
					else {
						this.listaExpPosFixa.add(">=");
					}
				}
			}
			else if(lastPosition == ";") {
				Integer integerFirstPosition = this.listaExpPosFixa.size()-3;
				Integer integerFirstPositionRange = this.listaExpPosFixa.size()-5;
				Integer last = this.listaExpPosFixa.size()-5;
				String firstPosition = this.listaExpPosFixa.get(integerFirstPosition);
				String firstPositionRange = this.listaExpPosFixa.get(integerFirstPositionRange);
				String test = this.listaExpPosFixa.get(last);
				if(firstPositionRange == "(") {
					this.listaExpPosFixa.remove(firstPosition);
					this.listaExpPosFixa.remove(firstPositionRange);
				}
				else {
					this.listaExpPosFixa.remove(firstPosition);
					this.listaExpPosFixa.remove(firstPositionRange);
				}
				this.listaExpPosFixa.remove(";");
				this.listaExpPosFixa.add("=");
			}
			else {	
				Integer integerPenultPosition = this.listaExpPosFixa.size()-2;
				String penultPosition = this.listaExpPosFixa.get(integerPenultPosition);
				if(penultPosition == "...") {
					Integer integerFirstPosition = this.listaExpPosFixa.size()-3;
					String firstPosition = this.listaExpPosFixa.get(integerFirstPosition);
					if(firstPosition == "(") {
						this.listaExpPosFixa.remove(firstPosition);
						this.listaExpPosFixa.remove(penultPosition);
					}
					else if(firstPosition == "[") {
						this.listaExpPosFixa.remove(firstPosition);
						this.listaExpPosFixa.remove(penultPosition);
					}
					if(var == ")") {
						this.listaExpPosFixa.add("<");
					}
					else {
						this.listaExpPosFixa.add("<=");	
					}
				}
			}
		}
		
		else {
			this.listaExpPosFixa.add(var);
		}
	}*/
	
	public void addListaExpPosFixa(String var) {
		this.listaExpPosFixa.add(var);
	}

			
	public void addRelational(Integer number) {
		this.relational.add(number);
	}
	
	public ArrayList<Integer> getRelational(){
		return this.relational;
	}
	/*
	public boolean metodox(VariableDefinition variables, String[] line){
			Integer x = null, y = null, operation;
			
			Iterator<String> itr = this.listaExpPosFixa.iterator();
			String element = itr.next();
			while(itr.hasNext()) 
		    {
				if((element.equals("OR") || (element.equals("AND")) || variables.getTabela().contains(element)))
				{
					
				}
				else if(element.equals("(") ||element.equals("[") || element.equals(")") || element.equals("]") )
				{
					//Tirar duvida quanto a isso
					if(element.equals("(")) x++;
					
				}
				else if(element.equals("...") || element.equals(";") )
				{
					if(element.equals("...")) operation = 0;
					else 
					{
						operation = 1;
					}
				}
				else
		    	{
		    		if(x == null) x = Integer.parseInt(element);
		    		else 
		    		{
		    			y = Integer.parseInt(element);
		    		}
		    	}   
		    }
			return true;
		 }
	
	public void print() {
		System.out.println(listaExpPosFixa);
	}
	*/
}

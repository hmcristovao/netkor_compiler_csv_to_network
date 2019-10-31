package semantico;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.text.html.HTMLEditorKit.Parser;

import parser.Token;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public LinkedList<LinkedList<String>> ajust1(LinkedList<LinkedList<String>> list) {
		ArrayList<String> operator = new ArrayList<String>();
		for(LinkedList<String> internList : list) {
			operator.add(internList.getFirst());
			//System.out.println(internList);
			//if(internList.charAt(list.size()) == ')') {
				
			//}
		}
		return list;
	}
	
	public void ajust(ArrayList<String> list) {
		//Variaveis correspondentes para pegar os valores dos intervalos (x e y de [x...y])
		String operatorA = ""; 
		String operatorB = "";
		
		//Ao adicionar ou remover elementos diretamente de estruturas como ArrayList ou LinkedList
		//dentro de um for como o caso a seguir, acusa o erro de java.util.ConcurrentModificationException
		//Portanto deve ser utilizado um ListIterator para fazer esse tratamente de forma segura
		ListIterator<String> iter = list.listIterator();
		while(iter.hasNext()){
		    String internList = iter.next();
		    internList = internList.replaceAll(" ", "");
			operatorA = internList;
		    //Caso (x...)
		    if(internList.startsWith("(") && internList.endsWith("...)")) {	
				//Ajustando para ter apenas a componente x (retirando os "(" e "...)" do intervalo )	
				iter.remove();
				operatorA = operatorA.replace("...)", "");
				operatorA = operatorA.replace("(", "");
				//Primeiro eh adicionado o operando e depois o operador na lista de expressao
				iter.add(operatorA);
				iter.add(">");
			}
			//Caso [x...)
			else if(internList.startsWith("[") && internList.endsWith("...)")) {
				//Ajustando para ter apenas a componente x (tirando "[" e "...)" do intervalo )	
				iter.remove();
				operatorA = operatorA.replace("...)", "");
				operatorA = operatorA.replace("[", "");
				iter.add(operatorA);
				iter.add(">=");
			}
			//Case (...x)
			else if(internList.startsWith("(...") && internList.endsWith(")")) {
				//Ajustando para ter apenas a componente x (tirando "(..." e ")" do intervalo )	
				iter.remove();
				operatorA = operatorA.replace("(...", "");
				operatorA = operatorA.replace(")", "");
				//Adicionando x na lista de expressao e ">"
				iter.add(operatorA);
				iter.add("<");
			}
			//Case (...x]
			else if(internList.startsWith("(...") && internList.endsWith("]")) {
				//Ajustando para ter apenas a componente x (tirando "(..." e "]" do intervalo )	
				iter.remove();
				operatorA = operatorA.replace("(...", "");
				operatorA = operatorA.replace("]", "");
				//Adicionando x na lista de expressao e ">"
				iter.add(operatorA);
				iter.add("<=");
			}
			//Case (x...y) or [x...y] or (x...y] or [x...y) 
			else if(internList.contains("...")) {
				
				//Separando x e y em range[0] e range[1] respectivamente
				String[] range = operatorA.split("\\.{3}");;
				
				//String destinada a armazenar a variavel do .map (SC2.idade, por exemplo)
				//correspondente a expressao tratada no caso (variavel = [x...y])
				String variable = "";
				
				if(internList.startsWith("(") && internList.endsWith(")")) {	
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "(", ")" e "..." do intervalo )	
					operatorA = range[0];
					operatorA = operatorA.replace("(", "");
					operatorB = range[1];
					operatorB = operatorB.replace(")", "");
					
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operatorA);
					iter.add(">");
					
					iter.add(variable);
					iter.add(operatorB);
					iter.add("<");
					iter.add("AND");
				}
				//Case [x...)
				else if(internList.startsWith("[") && internList.endsWith(")")) {
					//Ajustando para ter apenas a componente x (tirando "[" e "...)" do intervalo )	
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "[", ")" e "..." do intervalo )	
					operatorA = range[0];
					operatorA = operatorA.replace("[", "");
					operatorB = range[1];
					operatorB = operatorB.replace(")", "");
						
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operatorA);
					iter.add(">=");
					
					iter.add(variable);
					iter.add(operatorB);
					iter.add("<");
					iter.add("AND");
				}
				//Case (...x)
				else if(internList.startsWith("(") && internList.endsWith("]")) {
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "(", ")" e "..." do intervalo )	
					operatorA = range[0];
					operatorA = operatorA.replace("(", "");
					operatorB = range[1];
					operatorB = operatorB.replace("]", "");
						
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operatorA);
					iter.add(">");
					
					iter.add(variable);
					iter.add(operatorB);
					iter.add("<=");
					iter.add("AND");
				}
				//Case (...x]
				else if(internList.startsWith("[") && internList.endsWith("]")) {
					//Ajustando para ter apenas a componente x (tirando "(" e "...)" do intervalo )	
					iter.remove();
					//Ajustando para ter apenas a componente x e y (tirando "(", ")" e "..." do intervalo )	
					operatorA = range[0];
					operatorA = operatorA.replace("[", "");
					operatorB = range[1];
					operatorB = operatorB.replace("]", "");
						
					variable = list.get(iter.previousIndex());
					//Adicionando x na lista de expressao e ">"
					iter.add(operatorA);
					iter.add(">=");
					
					iter.add(variable);
					iter.add(operatorB);
					iter.add("<=");
					iter.add("AND");
				}
			}
	}
				
				
			//operator.add(internList.getLast());
			
			//System.out.println(operator);
			//if(internList.charAt(list.size()) == ')') {
				
			//}
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

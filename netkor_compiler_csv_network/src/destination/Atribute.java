package destination;

import java.util.ArrayList;

/*
 * ESTA CLASSE NAO FAZ NADA 
 * 
 * */

interface OneTypeAtribute<E> {
	public E getElement();
}
interface TwoTypeAtribute<E1, E2> {
	public E1 getElementTypeOne();
	public E2 getElementTypeTwo();
}

interface ThreeTypeAtribute<E1, E2, E3> {
	public E1 getElementTypeOne();
	public E2 getElementTypeTwo();
	public E3 getElementTypeThree();
}

public abstract class Atribute implements OneTypeAtribute<Object>, TwoTypeAtribute<Object, Object>, ThreeTypeAtribute<Object, Object, Object> {

	public enum AtributeType {
		NUMERIC,
		TEXT,
		DATE
	}
	
	private ArrayList<AtributeType> atribute_type;
	
	public ArrayList<AtributeType> getAtributeType() {
		return atribute_type;
	}
	
}

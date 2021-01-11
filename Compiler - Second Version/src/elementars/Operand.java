package elementars;

import enumerations.SIGNAL;
import enumerations.TYPE_OPERAND;
import parser.Token;

public class Operand extends Item {

	private TYPE_OPERAND operandType;
	private SIGNAL operandSignal;
	private String value;

	public Operand(Token token, TYPE_OPERAND operandType) {
		super(token);
		this.operandType = operandType;
		if(this.operandType.equals(TYPE_OPERAND.TEXT.toString())) {
			this.value = token.image.substring(1, value.length() - 1);
		} else {
			this.value = token.image;
		}
	}
	
	public Operand(String value, TYPE_OPERAND operandType) {
		super(null);
		this.value = value;
		this.operandType = operandType;
	}
	
	public Operand(String value, TYPE_OPERAND operandType, SIGNAL signal) {
		super(null);
		this.value = value;
		this.operandType = operandType;
		this.operandSignal = signal;
	}
	
	public Operand(Token token, TYPE_OPERAND operandType, SIGNAL signal) {
		super(token);
		this.value = token.image;
		this.operandType = operandType;
		this.operandSignal = signal;
	}

	public TYPE_OPERAND getOperand() {
		return operandType;
	}

	public String getLexema() {
		StringBuilder operandWithSignal = new StringBuilder();
		
		if(this.operandType.equals(TYPE_OPERAND.NUMBER.toString()) && 
				this.operandSignal == SIGNAL.MINUS) {
			operandWithSignal.append(SIGNAL.MINUS.toString());
		}
		
		operandWithSignal.append(value);
		
		return operandWithSignal.toString();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setOperandType(TYPE_OPERAND operandType) {
		this.operandType = operandType;
	}

	public void setOperandSignal(SIGNAL operandSignal) {
		this.operandSignal = operandSignal;
	}

	@Override
	public String toString() {
		return "Operand [operandType=" + operandType + ", operandSignal=" + 
				operandSignal + ", value="+ value +"]";
	}	
}

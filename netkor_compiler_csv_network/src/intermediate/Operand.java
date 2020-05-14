package intermediate;

public class Operand extends Item {

	OperandType operandType;

	public Operand(OperandType operandType, String token) {
		super(token);
		this.operandType = operandType;
	}

	public OperandType getOperandType() {
		return this.operandType;
	}

	@Override
	public String toString() {
		return this.getLexema();
	}
}
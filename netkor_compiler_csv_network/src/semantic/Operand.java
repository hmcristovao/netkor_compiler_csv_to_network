package semantic;

public class Operand extends Item {

	OperandType operandType;

	public Operand(OperandType operandType, String token) {
		super(token);
		this.operandType = operandType;
	}

	public OperandType getOperandType() {
		return this.operandType;
	}

	/*public String getLexema() {
		StringBuilder operandoCompleto = new StringBuilder();

		// coloca o sinal negativo, se for numero negativo
		if(this.sinal == Sinal.NEG)
			operandoCompleto.append("-");
		
		// se for constante numerica e inteira, coloca uma casa decimal com zero (devido a exigencia do comando assembler ldc2_w)
		if(this.tipoElemento == TipoElemento.CTE && this.tipoDado == TipoDado.NUMERO) 
		{	operandoCompleto.append( Double.toString( Double.parseDouble( this.token.image ) ) ); }
		else
		{ 	operandoCompleto.append( super.getLexema() ); } 
		
		return operandoCompleto.toString();
	}*/

	@Override
	public String toString() {
		return this.getLexema();
	}
}
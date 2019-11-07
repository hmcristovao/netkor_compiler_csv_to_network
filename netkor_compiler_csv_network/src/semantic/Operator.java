package semantic;

public class Operator extends Item{
	
	OperatorType operatorType;
	
	public Operator(OperatorType operatorType, String token) {
		super(token);
		this.operatorType = operatorType;
	}
   
	public String getLexema() {
		return "Operator";
	}
   
   public OperatorType getOperatorType() {
	     return this.operatorType;
   }

   @Override
   public String toString() {
      return this.token;
   }
}
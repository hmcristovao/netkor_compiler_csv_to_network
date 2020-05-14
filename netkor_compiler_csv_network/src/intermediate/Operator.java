package intermediate;

public class Operator extends Item{
	
	OperatorType operatorType;
	
	public Operator(String operatorType) {
		super(operatorType);
		if(operatorType.equals("=")) {
			this.operatorType = OperatorType.EQUAL;
		}
		else if(operatorType.equals(">")) {
			this.operatorType = OperatorType.GREATER;
		}
		else if(operatorType.equals(">=")) {
			this.operatorType = OperatorType.GREATER_EQUAL;
		}
		else if(operatorType.equals("<")) {
			this.operatorType = OperatorType.LESSER;
		}
		else if(operatorType.equals("<=")) {
			this.operatorType = OperatorType.LESSER_EQUAL;
		}
		else if(operatorType.equals("OR")) {
			this.operatorType = OperatorType.OR;
		}
		else {
			this.operatorType = OperatorType.AND;	
		}
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
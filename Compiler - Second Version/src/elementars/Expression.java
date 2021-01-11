package elementars;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import enumerations.SIGNAL;
import enumerations.TYPE_OPERAND;
import enumerations.TYPE_OPERATOR;
import intermediate.MapAliasTable;
import network.NetworkTable;

public class Expression {
	
	private LinkedList<Item> postFixedExpression;
	private LinkedList<Item> preFixedExpression;
	
	public Expression() {
		this.postFixedExpression = new LinkedList<Item>();
		this.preFixedExpression = new LinkedList<Item>();
	}
	
	public LinkedList<Item> getExpressionPosFixe() {
		return postFixedExpression;
	}
	
	public LinkedList<Item> getExpressionPreFix() {
		return preFixedExpression;
	}
	
	public void addItemToPreFixedExpression(Item item) {
		this.preFixedExpression.add(item);
	}
	
	public void addItemToPostFixedExpression(Item item) {
		this.postFixedExpression.add(item);
	}

	@Override
	public String toString() {
		return "Expression [postFixedExpression=" + postFixedExpression + ", preFixedExpression=" + preFixedExpression
				+ "]";
	}
	
	
	// TODO Optimizations
	//List<String> header, List<String> row, Network network
	public Operand resolve(List<String> row) {
		Stack<Item> expression = new Stack<Item>();

		for(int i = 0; i < postFixedExpression.size(); i++) {
			Item item = postFixedExpression.get(i);

			if(item instanceof Operand) {
				Operand operand = (Operand) item;
				expression.push(operand);
			} else if(item instanceof Operator) {
				Operand operand_1 = (Operand) expression.pop();
				Operand operand_2 = (Operand) expression.pop();
				Operator operator = (Operator) item;
				if(operand_1.getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())) {
					double operand1Double = Double.parseDouble(operand_1.getLexema());
					double operand2Double;
					if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
							&& Table.verifyIfTableContainsVariable(operand_2.getOperand().toString())
							&& Table.getSymbol(operand_2.getOperand().toString()).getValue() != null
							&& Table.getSymbol(operand_2.getOperand().toString()).getValue().getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())							
					) {
						operand2Double = Double.parseDouble(Table.getSymbol(operand_2.getOperand().toString()).getValue().getLexema());
					} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
							&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())						
					) {
						operand2Double = Double.parseDouble(row.get(NetworkTable.getHeader().indexOf(operand_2.getLexema())));
					} else {
						 operand2Double = Double.parseDouble(operand_2.getLexema());
					}
					if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
						double result = operand1Double + operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
						double result = operand1Double - operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
						double result = operand1Double * operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
						double result = operand1Double / operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
						Boolean result = operand1Double  == operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
						Boolean result = operand1Double  > operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
						Boolean result = operand1Double  < operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
						Boolean result = operand1Double  >= operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
						Boolean result = operand1Double  <= operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					}
					
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())) {
					String operand1 = operand_1.getLexema().substring(1, operand_1.getLexema().length() - 1);
					String operand2;
					if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
							&& Table.verifyIfTableContainsVariable(operand_2.getOperand().toString())
							&& Table.getSymbol(operand_2.getOperand().toString()).getValue() != null
							&& Table.getSymbol(operand_2.getOperand().toString()).getValue().getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())
					) {
						operand2 = Table.getSymbol(operand_2.getOperand().toString()).getValue().toString();
					} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString()) &&
							MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema().substring(1))) {
						int indexOperand1 = NetworkTable.getHeader().indexOf(operand_2.getLexema().substring(1));
						operand2 = row.get(indexOperand1);
					} else {
						operand2 = operand_2.getLexema();						
					}					
					if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
						String result = operand2.concat(operand1);
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.TEXT));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
						String result = operand2.concat(operand1);
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.TEXT));								
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
						Boolean result = operand1.equals(operand2);
						Operand newValue = new Operand(String.valueOf(result), TYPE_OPERAND.BOOLEAN);
						expression.push(newValue);								
					}
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {
					Boolean operand1 = Boolean.parseBoolean(String.valueOf(operand_1.getLexema()));
					Boolean operand2;
					if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
							&& Table.verifyIfTableContainsVariable(operand_2.getOperand().toString())
							&& Table.getSymbol(operand_2.getOperand().toString()).getValue() != null
							&& Table.getSymbol(operand_2.getOperand().toString()).getValue().getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())
					) {
						operand2 = Boolean.parseBoolean(Table.getSymbol(operand_2.getOperand().toString()).getValue().toString());
					} else {
						operand2 = Boolean.parseBoolean(String.valueOf(operand_2.getLexema()));
					}						
					if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
						Boolean result = operand1 && operand2;
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
						Boolean result = operand1 || operand2;
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
						Boolean result = operand1 == operand2;
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					}
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())) {
					if(MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_1.getLexema())) {
						int indexOperand1 = NetworkTable.getHeader().indexOf(operand_1.getLexema());
						if(MapAliasTable.getAliasTableColumnHeaderType(operand_2.getLexema()).toString().equals(TYPE_OPERAND.TEXT.toString())) {			
							if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
									&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())) {
								int indexOperand2 = NetworkTable.getHeader().indexOf(operand_2.getLexema());
								if(MapAliasTable.getAliasTableColumnHeaderType(operand_2.getLexema()).toString().equals(TYPE_OPERAND.TEXT.toString())) {
									String operand1 = row.get(indexOperand1);
									String operand2 = row.get(indexOperand2);
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
										String result = operand2.concat(operand1);
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.TEXT));									
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
										String result = operand2.concat(operand1);
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.TEXT));								
									}									
								}
							} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())) {
								String operand1 = row.get(indexOperand1);
								String operand2 = operand_2.getLexema();
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
									String result = operand2.concat(operand1);
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.TEXT));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
									String result = operand2.concat(operand1);
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.TEXT));								
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1.equals(operand2);
									Operand newValue = new Operand(String.valueOf(result), TYPE_OPERAND.BOOLEAN);
									expression.push(newValue);								
								}							
							}
							
						} else if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {
							if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
									&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())) {
								int indexOperand2 = NetworkTable.getHeader().indexOf(operand_2.getLexema());
								if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {				
									Boolean operand1 = Boolean.parseBoolean(row.get(indexOperand1));
									Boolean operand2 = Boolean.parseBoolean(row.get(indexOperand2));;						
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
										Boolean result = operand1 && operand2;
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.BOOLEAN));									
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
										Boolean result = operand1 || operand2;
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.BOOLEAN));									
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1 == operand2;
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.BOOLEAN));									
									}								
								}
							} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {				
								Boolean operand1 = Boolean.parseBoolean(row.get(indexOperand1));
								Boolean operand2 = Boolean.parseBoolean(operand_2.getLexema());					
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
									Boolean result = operand1 && operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
									Boolean result = operand1 || operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1 == operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));									
								}								
							}							
						}
						else if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.NUMBER.toString())) {
							if(operand_2.getOperand().toString().equals(TYPE_OPERAND.ALIASTABLECOLUMNHEADER.toString())
									&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())) {
								int indexOperand2 = NetworkTable.getHeader().indexOf(operand_2.getLexema());
								if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.NUMBER.toString())) {							
									double operand1Double = Double.parseDouble(row.get(indexOperand1));
									double operand2Double =  Double.parseDouble(row.get(indexOperand2));
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
										double result = operand1Double + operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
										double result = operand1Double - operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
										double result = operand1Double * operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
										double result = operand1Double / operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1Double  == operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
										Boolean result = operand1Double  > operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
										Boolean result = operand1Double  < operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
										Boolean result = operand1Double  >= operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
										Boolean result = operand1Double  <= operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
										Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
										Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									}								
								}
							} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())) {				
								double operand1Double = Double.parseDouble(row.get(indexOperand1));
								double operand2Double =  Double.parseDouble(operand_2.getLexema());
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
									double result = operand1Double + operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
									double result = operand1Double - operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
									double result = operand1Double * operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
									double result = operand1Double / operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1Double  == operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
									Boolean result = operand1Double  > operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
									Boolean result = operand1Double  < operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
									Boolean result = operand1Double  >= operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
									Boolean result = operand1Double  <= operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
									Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
									Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								}								
							}							
						}						
					} else if(Table.verifyIfTableContainsVariable(operand_1.getLexema())) {
						Variable variable = Table.getSymbol(operand_1.getLexema());
						if(variable.getValue() != null) {
							if(variable.getType().equals(TYPE_OPERAND.BOOLEAN.toString())) {
								if(operand_2.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {
									Boolean operand1 = Boolean.parseBoolean(String.valueOf(variable.getValue().getLexema()));
									Boolean operand2 = Boolean.parseBoolean(String.valueOf(operand_2.getLexema()));
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
										Boolean result = operand1 && operand2;
										Operand newValue = new Operand(String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN);
										expression.push(newValue);
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
										Boolean result = operand1 || operand2;
										Operand newValue = new Operand(String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN);
										expression.push(newValue);							
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1 == operand2;
										Operand newValue = new Operand(String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN);
										expression.push(newValue);							
									}							
								} else {
									throw new Error("Diferent types on variable expression: " + variable.getName());
								}
							} else if(variable.getType().equals(TYPE_OPERAND.TEXT.toString())) {
								if(operand_2.getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())) {
									String operand1 = variable.getValue().getLexema();
									String operand2 = operand_2.getLexema();
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
										String result = operand2.concat(operand1);
										Operand newValue = new Operand(String.valueOf(result), 
												TYPE_OPERAND.TEXT);
										expression.push(newValue);																	
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
										String result = operand2.concat(operand1);
										Operand newValue = new Operand(String.valueOf(result), 
												TYPE_OPERAND.TEXT);
										expression.push(newValue);								
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1.equals(operand2);
										Operand newValue = new Operand(String.valueOf(result), TYPE_OPERAND.BOOLEAN);
										expression.push(newValue);								
									}							
								} else {
									throw new Error("Diferent types on variable expression: " + variable.getName());
								}							
							} else if(variable.getType().equals(TYPE_OPERAND.NUMBER.toString())) {
								if(operand_2.getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())) {
									double operand1Double = Double.parseDouble(variable.getValue().getLexema());
									double operand2Double = Double.parseDouble(operand_2.getLexema());
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
										double result = operand1Double + operand2Double;
										if(result < 0) {
											Operand newValue = new Operand(String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS);
											expression.push(newValue);
											Table.getSymbol(operand_1.getLexema()).setValue(newValue);	
										} else {
											Operand newValue = new Operand(String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.PLUS);
											expression.push(newValue);							
										}
									 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
										double result = operand1Double - operand2Double;
										if(result < 0) {
											Operand newValue = new Operand(String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS);
											expression.push(newValue);
										} else {
											Operand newValue = new Operand(String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.PLUS);
											expression.push(newValue);							
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
										double result = operand1Double * operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
										double result = operand1Double / operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1Double  == operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
										Boolean result = operand1Double  > operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
										Boolean result = operand1Double  < operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
										Boolean result = operand1Double  >= operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
										Boolean result = operand1Double  <= operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
										Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
										Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									}
																			
								}							
							} 
							
						} else {
							throw new Error("Variable not initialized: " + variable.getName());
						}
					}
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString())) {
					if(MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_1.getLexema())) {
						int indexOperand1 = NetworkTable.getHeader().indexOf(operand_1.getLexema());
						if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.TEXT.toString())) {			
							if(operand_2.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString())
									&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())) {
								int indexOperand2 = NetworkTable.getHeader().indexOf(operand_2.getLexema());
								if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.TEXT.toString())) {
									String operand1 = row.get(indexOperand1);
									String operand2 = row.get(indexOperand2);				
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
										String result = operand2.concat(operand1);
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.TEXT));									
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
										String result = operand2.concat(operand1);
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.TEXT));								
									}									
								}
							} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())) {
								String operand1 = row.get(indexOperand1);
								String operand2 = operand_2.getLexema();				
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
									String result = operand2.concat(operand1);
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.TEXT));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
									String result = operand2.concat(operand1);
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.TEXT));								
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1.equals(operand2);
									Operand newValue = new Operand(String.valueOf(result), TYPE_OPERAND.BOOLEAN);
									expression.push(newValue);								
								}							
							}
							
						} else if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {
							if(operand_2.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString())
									&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())) {
								int indexOperand2 = NetworkTable.getHeader().indexOf(operand_2.getLexema());
								if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {				
									Boolean operand1 = Boolean.parseBoolean(row.get(indexOperand1));
									Boolean operand2 = Boolean.parseBoolean(row.get(indexOperand2));;						
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
										Boolean result = operand1 && operand2;
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.BOOLEAN));									
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
										Boolean result = operand1 || operand2;
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.BOOLEAN));									
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1 == operand2;
										expression.push(
												new Operand(
														String.valueOf(result), 
														TYPE_OPERAND.BOOLEAN));									
									}								
								}
							} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {				
								Boolean operand1 = Boolean.parseBoolean(row.get(indexOperand1));
								Boolean operand2 = Boolean.parseBoolean(operand_2.getLexema());					
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
									Boolean result = operand1 && operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
									Boolean result = operand1 || operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1 == operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));									
								}								
							}							
						}
						else if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.NUMBER.toString())) {
							if(operand_2.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString())
									&& MapAliasTable.verifyIfAliasKeyExistsOnTable(operand_2.getLexema())) {
								int indexOperand2 = NetworkTable.getHeader().indexOf(operand_2.getLexema());
								if(MapAliasTable.getAliasTableColumnHeaderType(operand_1.getLexema()).toString().equals(TYPE_OPERAND.NUMBER.toString())) {							
									double operand1Double = Double.parseDouble(row.get(indexOperand1));
									double operand2Double =  Double.parseDouble(row.get(indexOperand2));
									if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
										double result = operand1Double + operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
										double result = operand1Double - operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
										double result = operand1Double * operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
										double result = operand1Double / operand2Double;
										if(result < 0) {
										expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
										} else {
											expression.push(
													new Operand(
															String.valueOf(Math.abs(result)), 
															TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
										}
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
										Boolean result = operand1Double  == operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
										Boolean result = operand1Double  > operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
										Boolean result = operand1Double  < operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
										Boolean result = operand1Double  >= operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
										Boolean result = operand1Double  <= operand2Double;
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
										Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
										Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
										expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));
									}								
								}
							} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())) {				
								double operand1Double = Double.parseDouble(row.get(indexOperand1));
								double operand2Double =  Double.parseDouble(operand_2.getLexema());
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
									double result = operand1Double + operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
									double result = operand1Double - operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
									double result = operand1Double * operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
									double result = operand1Double / operand2Double;
									if(result < 0) {
									expression.push(
										new Operand(
												String.valueOf(Math.abs(result)), 
												TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
									} else {
										expression.push(
												new Operand(
														String.valueOf(Math.abs(result)), 
														TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
									}
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1Double  == operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
									Boolean result = operand1Double  > operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
									Boolean result = operand1Double  < operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
									Boolean result = operand1Double  >= operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
									Boolean result = operand1Double  <= operand2Double;
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
									Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
									Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
									expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));
								}								
							}							
						}						
					}
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString())) {
					
					String dataFrameHeader1 = operand_1.toString().substring(1);
					int indexOperand1 = NetworkTable.getHeader().indexOf(dataFrameHeader1);
					
					if(MapAliasTable.verifyIfAliasKeyExistsOnTable(dataFrameHeader1)) {
						
						if(operand_2.getOperand().toString().equals(TYPE_OPERAND.DATAFRAMECOLUMNHEADER.toString())) {
							String dataFrameHeader2 = operand_2.toString().substring(1);
							int indexOperand2 = NetworkTable.getHeader().indexOf(dataFrameHeader2);
							
							if(MapAliasTable.verifyIfAliasKeyExistsOnTable(dataFrameHeader2)) {
								String operand1 = row.get(indexOperand1);
								String operand2 =  row.get(indexOperand2);								
								if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
									Boolean result = operand1 == operand2;
									expression.push(
											new Operand(
													String.valueOf(result), 
													TYPE_OPERAND.BOOLEAN));										
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
									String result = operand1.concat(operand2);
									expression.push(
											new Operand(
													result, 
													TYPE_OPERAND.TEXT));									
								} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
									String result = operand1.concat(operand2);
									expression.push(
											new Operand(
													result, 
													TYPE_OPERAND.TEXT));									
								}
							}
						} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {			
							Boolean operand1 = Boolean.parseBoolean(row.get(indexOperand1));
							Boolean operand2 =  Boolean.parseBoolean(operand_2.getLexema());								
							if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
								Boolean result = operand1 && operand2;
								expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));									
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
								Boolean result = operand1 || operand2;
								expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));									
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
								Boolean result = operand1 == operand2;
								expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.BOOLEAN));									
							}						
						} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())) {
							String operand1 = row.get(indexOperand1);
							String operand2 = operand_2.getLexema();
							if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
								String result = operand2.concat(operand1);
								expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.TEXT));									
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
								String result = operand2.concat(operand1);
								expression.push(
										new Operand(
												String.valueOf(result), 
												TYPE_OPERAND.TEXT));								
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
								Boolean result = operand1.equals(operand2);
								
								Operand newValue = new Operand(String.valueOf(result), TYPE_OPERAND.BOOLEAN);
								expression.push(newValue);								
							} 
							
						} else if(operand_2.getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())) {
							double operand1Double = Double.parseDouble(row.get(indexOperand1));
							double operand2Double =  Double.parseDouble(operand_2.getLexema());
							if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
								double result = operand1Double + operand2Double;
								if(result < 0) {
								expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
								} else {
									expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
								}
							 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
								double result = operand1Double - operand2Double;
								if(result < 0) {
								expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
								} else {
									expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
								}
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
								double result = operand1Double * operand2Double;
								if(result < 0) {
								expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
								} else {
									expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
								}
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
								double result = operand1Double / operand2Double;
								if(result < 0) {
								expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
								} else {
									expression.push(
											new Operand(
													String.valueOf(Math.abs(result)), 
													TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
								}
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
								Boolean result = operand1Double  == operand2Double;
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
								Boolean result = operand1Double  > operand2Double;
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
								Boolean result = operand1Double  < operand2Double;
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
								Boolean result = operand1Double  >= operand2Double;
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
								Boolean result = operand1Double  <= operand2Double;
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
								Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
								Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
								expression.push(
									new Operand(
											String.valueOf(result), 
											TYPE_OPERAND.BOOLEAN));
							}	
						}
					}
				}
			} 
		}
		Operand operand = (Operand) expression.pop();
		return operand;
	}
	
	public Operand resolve() {
		
		Stack<Item> expression = new Stack<Item>();

		for(int i = 0; i < postFixedExpression.size(); i++) {
			Item item = postFixedExpression.get(i);
			if(item instanceof Operand) {
				Operand operand = (Operand) item;
				expression.push(operand);
			} else if(item instanceof Operator) {
				Operand operand_1 = (Operand) expression.pop();
				Operand operand_2 = (Operand) expression.pop();
				Operator operator = (Operator) item;
				if(operand_1.getOperand().toString().equals(TYPE_OPERAND.NUMBER.toString())) {
					double operand1Double = Double.parseDouble(operand_1.getLexema());
					double operand2Double = Double.parseDouble(operand_2.getLexema());
					if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUM.toString())) {
						double result = operand1Double + operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					 }  else if(operator.getOperator().toString().equals(TYPE_OPERATOR.SUBTRACTION.toString())) {
						double result = operand1Double - operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.MULTIPLICATION.toString())) {
						double result = operand1Double * operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.DIVISION.toString())) {
						double result = operand1Double / operand2Double;
						if(result < 0) {
						expression.push(
							new Operand(
									String.valueOf(Math.abs(result)), 
									TYPE_OPERAND.NUMBER, SIGNAL.MINUS));
						} else {
							expression.push(
									new Operand(
											String.valueOf(Math.abs(result)), 
											TYPE_OPERAND.NUMBER, SIGNAL.PLUS));									
						}
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
						Boolean result = operand1Double  == operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER.toString())) {
						Boolean result = operand1Double  > operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS.toString())) {
						Boolean result = operand1Double  < operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.GREATER_EQUAL.toString())) {
						Boolean result = operand1Double  >= operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.LESS_EQUAL.toString())) {
						Boolean result = operand1Double  <= operand2Double;
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) && Boolean.parseBoolean(String.valueOf(operand2Double));
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1Double)) || Boolean.parseBoolean(String.valueOf(operand2Double));
						expression.push(
							new Operand(
									String.valueOf(result), 
									TYPE_OPERAND.BOOLEAN));
					}
					
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.TEXT.toString())) {
					String operand1 = operand_1.getLexema();
					String operand2 = operand_2.getLexema();
					if(operator.getOperator().toString().equals(TYPE_OPERATOR.CONCAT.toString())) {
						String result = operand2.concat(operand1);
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.TEXT));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1)) && Boolean.parseBoolean(String.valueOf(operand2));
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1)) || Boolean.parseBoolean(String.valueOf(operand2));
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
						Boolean result = Boolean.parseBoolean(String.valueOf(operand1)) == Boolean.parseBoolean(String.valueOf(operand2));
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					}
				} else if(operand_1.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString())) {
					Boolean operand1 = Boolean.parseBoolean(String.valueOf(operand_1.getLexema()));
					Boolean operand2 = Boolean.parseBoolean(String.valueOf(operand_2.getLexema()));
					if(operator.getOperator().toString().equals(TYPE_OPERATOR.AND.toString())) {
						Boolean result = operand1 && operand2;
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.OR.toString())) {
						Boolean result = operand1 || operand2;
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					} else if(operator.getOperator().toString().equals(TYPE_OPERATOR.EQUAL.toString())) {
						Boolean result = operand1 == operand2;
						expression.push(
								new Operand(
										String.valueOf(result), 
										TYPE_OPERAND.BOOLEAN));									
					}
				}
			} 
		}
		Operand operand = (Operand) expression.pop();
		return operand;
	}
}
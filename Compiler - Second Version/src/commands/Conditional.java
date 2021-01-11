package commands;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import elementars.Expression;
import elementars.Operand;
import enumerations.TYPE_OPERAND;
import network.NetworkTable;

public class Conditional extends Command {

	private LinkedHashMap<Expression, CommandList> commandListForTrueIfExpression;
	private LinkedHashMap<Expression, CommandList> commandListForTrueElifExpression;
	private ArrayList<CommandList> commandListForElse;
	
	public Conditional() {
		this.commandListForTrueIfExpression = new LinkedHashMap<Expression, CommandList>();
		this.commandListForTrueElifExpression = new LinkedHashMap<Expression, CommandList>();
		this.commandListForElse = new ArrayList<CommandList>();
	}
	
	public void resolve(String header, Integer indexOfRow) {
		for(Expression expression : commandListForTrueIfExpression.keySet()) {
			List<String> row = NetworkTable.getRows().get(indexOfRow);
			Operand result = expression.resolve(row);
			if(result.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString()) && 
					result.getLexema().equals("true")) {
				for(Command command : commandListForTrueIfExpression.get(expression).getCommandList()) {
					if(command instanceof Return) {
						Return returnCommand = (Return) command;
						returnCommand.resolve(header, indexOfRow);
						return;
					} else if(command instanceof Attribuition) {
						Attribuition attribuitionCommand = (Attribuition) command;
						attribuitionCommand.resolve(NetworkTable.getRows().get(indexOfRow));
					} else if(command instanceof Conditional) {
						Conditional conditionalCommand = (Conditional) command;
						conditionalCommand.resolve(header, indexOfRow);
					}
				}
			}
		}
		
		for(Expression expression : commandListForTrueElifExpression.keySet()) {
			List<String> row = NetworkTable.getRows().get(indexOfRow);
			Operand result = expression.resolve(row);
			if(result.getOperand().toString().equals(TYPE_OPERAND.BOOLEAN.toString()) && 
					result.getLexema().equals("true")) {
				System.out.println(expression);
				for(Command command : commandListForTrueIfExpression.get(expression).getCommandList()) {
					System.out.println(command);
					if(command instanceof Return) {
						Return returnCommand = (Return) command;
						returnCommand.resolve(header, indexOfRow);
						return;
					} else if(command instanceof Attribuition) {
						Attribuition attribuitionCommand = (Attribuition) command;
						attribuitionCommand.resolve(NetworkTable.getRows().get(indexOfRow));
					} else if(command instanceof Conditional) {
						Conditional conditionalCommand = (Conditional) command;
						conditionalCommand.resolve(header, indexOfRow);
					}
				}
			}
		}

		for(Command command : commandListForElse.get(0).getCommandList()) {
			if(command instanceof Return) {
				Return returnCommand = (Return) command;
				returnCommand.resolve(header, indexOfRow);
				return;
			} else if(command instanceof Attribuition) {
				Attribuition attribuitionCommand = (Attribuition) command;
				attribuitionCommand.resolve(NetworkTable.getRows().get(indexOfRow));
			} else if(command instanceof Conditional) {
				Conditional conditionalCommand = (Conditional) command;
				conditionalCommand.resolve(header, indexOfRow);
			}
		}
	}
	
	public LinkedHashMap<Expression, CommandList> getCommandListForTrueIfExpression() {
		return commandListForTrueIfExpression;
	}

	public void addIfCommand(Expression expression, CommandList commandList) {
		if(this.commandListForTrueIfExpression.size() == 0) {
			this.commandListForTrueIfExpression.
				put(expression, commandList);
		} else {
			throw new Error("Only one IF command is allowed in a Conditional!");
		}
	}

	public void addElifCommand(Expression expression, CommandList commandList) {
		if(this.commandListForTrueIfExpression.size() > 0) {
			this.commandListForTrueElifExpression.
				put(expression, commandList);
		} else {
			throw new Error("You need to declare an IF command to declare ELIF commands!");
		}
	}
	
	public LinkedHashMap<Expression, CommandList> getCommandListForTrueElifExpression() {
		return commandListForTrueElifExpression;
	}
	
	public void addElseCommand(CommandList commandList) {
		if(this.commandListForElse.size() == 0 && this.commandListForTrueIfExpression.size() == 1) {
			this.commandListForElse.
				add(commandList);
		} else {
			throw new Error("Only one ELSE command is allowed in a Conditional!");
		}
	}

	public ArrayList<CommandList> getCommandListForElse() {
		return commandListForElse;
	}

	@Override
	public String toString() {
		return "Conditional [commandListForTrueIfExpression=" + commandListForTrueIfExpression
				+ ", commandListForTrueElifExpression=" + commandListForTrueElifExpression + ", commandListForElse="
				+ commandListForElse + "]";
	}
}

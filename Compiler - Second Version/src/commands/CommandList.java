package commands;

import java.util.LinkedList;

public class CommandList {
	private LinkedList<Command> commandList;
	
	public CommandList() {
		this.commandList = new LinkedList<Command>();
	}
	
	public void addComand(Command command) {
		this.commandList.add(command);
	}

	public LinkedList<Command> getCommandList() {
		return commandList;
	}
	
	@Override
	public String toString() {
		return  this.commandList.toString();
	}
	
}

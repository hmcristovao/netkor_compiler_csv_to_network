package intermediate;

import commands.Command;
import commands.CommandList;
import parser.Token;

public class DataFrameComponent {
	private Token dataFrameColumnHeader;
	private Token attributeValue;
	private CommandList commandList;
	
	public DataFrameComponent(Token dataFrameColumnHeader, Token attributeValue) {
		this.dataFrameColumnHeader = dataFrameColumnHeader;
		this.attributeValue = attributeValue;
		this.commandList = new CommandList();
	}
	
	public DataFrameComponent() {
		this.commandList = new CommandList();	
	}

	public void addDataFrameComponentCommand(Command command) {
		commandList.addComand(command);
	}
	
	public Token getDataFrameColumnHeader() {
		return dataFrameColumnHeader;
	}
	
	public Token getAttributeValue() {
		return attributeValue;
	}
	
	public CommandList getCommandList() {
		return commandList;
	}
	
	@Override
	public String toString() {
		return "DataFrameComponent [dataFrameColumnHeader=" + dataFrameColumnHeader + ", attributeValue="
				+ attributeValue + ", commandList=" + commandList + "]";
	}	
}

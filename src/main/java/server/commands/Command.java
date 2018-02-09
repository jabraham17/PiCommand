package server.commands;

//all commands will look like this
public interface Command {
	
	public String name();
	public String description();
	public String invocation();
	public Object execute(Object... args);

}


public class Instruction 
{
	public enum Command {START, BOUNDARY, INITIAL_POSITION, RESET, SHUTDOWN, TOGGLE_LIGHTS, FLASH_LIGHTS, ALERT, NAVIGATE_HOME, MOVE}; 
	public Command command;
	public Object[] parameters;
	public double duration;
	
	public Instruction(Command command, Object[] parameters, double duration)
	{
		this.command = command;
		this.parameters = parameters;
		this.duration = duration;
	}
	
	public String toString()
	{
		return String.format("%s %s", command, parameters);
	}
}

import java.security.InvalidParameterException;

public class Drone 
{
	private String currentCommand;
	private boolean started;
	private Coordinate boundary;
	public Coordinate position;
	private NavModule nav;
	private final double speed = 0.5;
	
	private String errorState;
	
	String testString;
	
	public Drone(NavModule nav)
	{
		this.nav = nav;
	}
	
	public String execute(String instruction)
	{
		decode(instruction);	
		return getStateString();
	}
	
	private void decode(String instruction)
	{		
		currentCommand = instruction;
		String[] parameters = instruction.split(" ");
		
		errorState = "";
		
		try
		{
			switch(parameters[0])
			{
				case "S": chkArgs(0, parameters); start(); break;
				case "B": chkArgs(2, parameters); setBoundary(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])); break;
				case "I": chkArgs(2, parameters); setInitialPos(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])); break;
				case "R": chkArgs(0, parameters); restart(); break;
				case "D": chkArgs(0, parameters); shutdown(); break;
				case "T": chkArgs(0, parameters); toggleLights(); break;
				case "F": chkArgs(0, parameters); flashLights(); break;
				case "A": chkArgs(1, parameters); soundHorn(Integer.parseInt(parameters[1])); break;
				case "H": chkArgs(0, parameters); navigateHome(); break;
				case "M": chkArgs(2, parameters); move(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])); break;
				default: throw new InvalidParameterException("No such command " + parameters[0]);
			}
		}
		catch(InvalidParameterException e)
		{
			errorState = e.getMessage();
		}
		catch(NumberFormatException e)
		{
			errorState = "Bad command parameters " + e.getMessage();
		}
		catch(BadCommandException e)
		{
			errorState = e.getMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			errorState = "unknown error";
		}
	}
	
	private void chkArgs(int number, String[] params) throws Exception
	{
		if(params.length != (number+1))
			throw new InvalidParameterException("Incorrect number of command parameters");
	}
	
	private void start() throws BadCommandException
	{
		if(started)
			throw new BadCommandException("Drone already started.");
		if(position == null)
			throw new BadCommandException("Initial position must be set before start.");
		if(boundary == null)
			throw new BadCommandException("Boundary size must be set before start");
		
		started = true;
	}
	
	private void setBoundary(int x, int y)
	{
		boundary = new Coordinate(x,  y);
	}
	
	private void setInitialPos(int x, int y)
	{
		position = new Coordinate(x,  y);
	}
	
	private void restart()
	{
		testString = "restart";
	}
	
	private void shutdown() throws BadCommandException
	{
		if(!started)
			throw new BadCommandException("Drone not started.");
		restart();
		started = false;
	}
	
	private void toggleLights()
	{
		testString = "lights toggled";
	}
	
	private void flashLights()
	{
		testString = "flash lights";
	}
	
	private void soundHorn(int duration)
	{
		testString = "Sound horn";
	}
	
	private void navigateHome()
	{
		testString = "navigate home";
	}
	
	private void move(int duration, int direction) throws BadCommandException
	{
		if(duration <= 0)
			throw new BadCommandException("Must have a positive duration of flight.");
		if(direction < 0 || direction > 360)
			throw new BadCommandException("Direction must be between 0 and 360 inclusive.");
		
		nav.updatePos(this, position, duration, direction, speed);
	}
	
	private String getStateString()
	{
		return String.format("%-10s %b %s %s %s", currentCommand, started, position, boundary, errorState);
	}
}

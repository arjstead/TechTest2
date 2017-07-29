import java.security.InvalidParameterException;

public class Drone 
{
	private Coordinate boundary;
	private Coordinate position;
	private boolean error;
	private String testString;
	
	public String execute(String instruction)
	{
		decode(instruction);	
		return testString;
	}
	
	private void decode(String instruction)
	{
		error = false;
		
		String[] parameters = instruction.split(" ");
		
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
		catch(ArrayIndexOutOfBoundsException e)
		{
			testString = "Too few parameters";
		}
		catch(InvalidParameterException e)
		{
			testString = e.getMessage();
		}
		catch(NumberFormatException e)
		{
			testString = "Bad command parameters " + e.getMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			testString = "unknown error";
		}
	}
	
	private void chkArgs(int number, String[] params) throws Exception
	{
		if(params.length != (number+1))
			throw new InvalidParameterException("Too many command parameters");
	}
	
	private void start()
	{
		testString = "Start drone";
	}
	
	private void setBoundary(int x, int y)
	{
		testString = "Boundary is (" + x + "," + y + ")";
	}
	
	private void setInitialPos(int x, int y)
	{
		testString = "Set intial position (" + x + "," + y + ").";
	}
	
	private void restart()
	{
		testString = "restart";
	}
	
	private void shutdown()
	{
		testString = "Shutdown";
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
	
	private void move(int duration, int direction)
	{
		testString = "move for " + duration + "s, at " + direction + " degrees";
	}
}

import java.security.InvalidParameterException;

public class Drone 
{
	private String currentCommand;
	private boolean started;
	private FreedomNav nav;
	private final double speed = 0.5;
	private Coordinate home;
	private boolean lightsOn = false;
	private String lights;
	private String errorState;
	private Controller controller;
		
	public Drone(Controller controller, FreedomNav nav)
	{
		this.nav = nav;
		this.controller = controller;
	}
	
	// ************ Control Block ************
	
	public void run()
	{
		nav.setDrone(this);
		
		boolean stop = false;
		String instruction;
		while(!stop)
		{
			instruction = fetchNextInstruction();
			if(instruction != null)
				controller.output(decodeAndExecute(instruction));
			else
				stop = true;
		}
	}
	
	private String fetchNextInstruction()
	{
		return controller.sendNextInstruction();
	}
	
	private String decodeAndExecute(String instruction)
	{		
		currentCommand = instruction;
		String[] parameters = instruction.split(" ");
		
		errorState = "";
		
		try
		{
			switch(parameters[0])
			{
				case "S": chkParams(0, parameters); start(); break;
				case "B": chkParams(2, parameters); nav.setBoundary(new Coordinate(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]))); break;
				case "I": chkParams(2, parameters); setInitialPos(new Coordinate(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]))); break;
				case "R": chkParams(0, parameters); restart(); break;
				case "D": chkParams(0, parameters); shutdown(); break;
				case "T": chkParams(0, parameters); toggleLights(); break;
				case "F": chkParams(0, parameters); flashLights(); break;
				case "A": chkParams(1, parameters); soundHorn(Integer.parseInt(parameters[1])); break;
				case "H": chkParams(0, parameters); navigateHome(); break;
				case "M": chkParams(2, parameters); move(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])); break;
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
		
		return getStateString();
	}
	
	private void chkParams(int number, String[] params) throws Exception
	{
		// Checks whether the number of parameters an instruction was given was correct.
		
		if(params.length != (number+1))
			throw new InvalidParameterException("Incorrect number of command parameters");
	}
	
	// ************ Instruction handler block ************
	
	private void start() throws BadCommandException
	{
		if(started)
			throw new BadCommandException("Drone already started.");
		if(nav.getPosition() == null)
			throw new BadCommandException("Initial nav.position must be set before start.");
		if(nav.getBoundary() == null)
			throw new BadCommandException("nav.boundary size must be set before start");
		
		started = true;
	}
	
	private void setInitialPos(Coordinate position) throws BadCommandException
	{
		if(nav.getBoundary() == null)
			throw new BadCommandException("Set nav.boundary before initial nav.position.");
		if(position.x > nav.getBoundary().x || position.y > nav.getBoundary().y)
			throw new BadCommandException("Starting nav.position outside of nav.boundary.");
		nav.setPosition(position);
		setHome(position);
	}
	
	private void setHome(Coordinate position)
	{
		home = new Coordinate(position.x, position.y);
	}
	
	private void restart() throws BadCommandException
	{
		if(started)
			throw new BadCommandException("Cannot restart mid flight.");
		
		nav.setBoundary(null);
		nav.setPosition(null);
		home = null;
	}
	
	private void shutdown() throws BadCommandException
	{
		if(!started)
			throw new BadCommandException("Drone not started.");
		started = false;
		restart();
	}
	
	private void toggleLights()
	{
		lightsOn = !lightsOn;
	}
	
	private void navigateHome() throws BadCommandException
	{
		nav.navigateToCoordinate(home);
	}
	
	public void move(int duration, int direction) throws BadCommandException
	{
		if(!started)
			throw new BadCommandException("Drone not started yet.");
		if(duration <= 0)
			throw new BadCommandException("Must have a positive duration of flight.");
		if(direction < 0 || direction > 360)
			throw new BadCommandException("Direction must be between 0 and 360 inclusive.");
		
		nav.updatePosition(duration, direction, speed);
	}
	
	// ************ Unimplemented instruction handlers ************
	
	public void alert()
	{
		
	}
	
	private void flashLights()
	{
		
	}
	
	private void soundHorn(int duration)
	{
		
	}
	
	// ************ State handlers ************
	
	public double getSpeed()
	{
		return speed;
	}
	
	private String getStateString()
	{
		lights = (lightsOn)? "On" : "Off";
		return String.format("%-10s %-5b %s %s %s %s", currentCommand, started, nav.getPosition(), nav.getBoundary(), lights, errorState);
	}
}

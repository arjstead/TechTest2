
public class FreedomNav 
{
	private final double DEG_TO_RAD = 0.0174533;
	private boolean alert;
	private Drone d;
	private Coordinate position;
	private Coordinate boundary;
	
	// ************* State handlers ************* 
	
	public void setDrone(Drone d)
	{
		this.d = d;
	}
	
	public void setPosition(Coordinate pos)
	{
		position = pos;
	}
	
	public Coordinate getPosition()
	{
		return position;
	}
	
	public void setBoundary(Coordinate bound)
	{
		boundary = bound;
	}
	
	public Coordinate getBoundary()
	{
		return boundary;
	}
	
	// ************* Control handlers ************* 
	
	public void updatePosition(int duration, int direction, double speed)
	{
		alert = false;
		double distance = speed*duration;
		
		position.x += getDeltaX(direction, distance);
		position.y += getDeltaY(direction, distance);
		
		//if(alert) d.alert();
	}
	
	public void navigateToCoordinate(Coordinate coords) throws BadCommandException
	{
		// Set the position of the drone at the centre of the axis.
		Coordinate differance = new Coordinate(position.x-coords.x, position.y-coords.y);
		
		// Workout the direction to the new position
		int quadrant = getQuadrant(differance);
		int direction = getDirection(differance, quadrant);

		// Work out the duration of flight.
		int distance = (int) Math.sqrt(Math.pow(differance.x, 2) + Math.pow(differance.y, 2));
		int duration = (int) (distance / d.getSpeed());
		
		// Move to the location.
		d.move(duration, direction);
	}
	
	// ************* Refactored calculations ************* 
	
	private double getDeltaX(int direction, double distance)
	{
		// Returns the distance the drone will need to travel in the x direction.
		
		double distanceToBoundaryLeft = -position.x;
		double distanceToBoundaryRight = boundary.x - position.x;
		double deltaX = distance*Math.sin(direction*DEG_TO_RAD);
		if(deltaX > distanceToBoundaryRight)
		{
			deltaX = distanceToBoundaryRight;
			alert = true;
		}
		else if(deltaX < distanceToBoundaryLeft)
		{
			deltaX = distanceToBoundaryLeft;
			alert = true;
		}
		
		return deltaX;
	}
	
	private double getDeltaY(int direction, double distance)
	{
		// Returns the distance the drone will need to travel in the y direction.

		double distanceToBoundaryBottom = -position.y;
		double distanceToBoundaryTop = boundary.y - position.y;
		double deltaY = distance*Math.cos(direction*DEG_TO_RAD);
		if(deltaY > distanceToBoundaryTop)
		{
			deltaY = distanceToBoundaryTop;
			alert = true;
		}
		else if(deltaY < distanceToBoundaryBottom)
		{
			deltaY = distanceToBoundaryBottom;
			alert = true;
		}
		
		return deltaY;
	}
	
	private int getQuadrant(Coordinate differance)
	{
		// Returns the quadrant of a 2d graph an angle will lie in.
		
		if((differance.x >= 0) && (differance.y >= 0))
			return 1;
		if((differance.x >= 0) && (differance.y <= 0))
			return 2;
		if((differance.x <= 0) && (differance.y <= 0))
			return 3;
		else
			return 4;
	}
	
	private int getDirection(Coordinate differance, int quadrant)
	{
		// Returns the angle a point, in a particular quadrant, will be to the +ve x axis.
		
		int direction;
		
		switch(quadrant)
		{
			case 1: direction = (int) Math.atan((differance.x / (double) differance.y)*DEG_TO_RAD);
			case 2: direction = (int) Math.atan((-differance.y / (double) differance.x)*DEG_TO_RAD);
			case 3: direction = (int) Math.atan((-differance.x / (double) -differance.y)*DEG_TO_RAD);
			case 4: direction = (int) Math.atan((differance.y / (double) -differance.x)*DEG_TO_RAD);
			default: direction = 0;
		}
		
		return direction;
	}
}
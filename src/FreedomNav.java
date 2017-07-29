
public class FreedomNav extends NavModule
{
	private final double DEG_TO_RAD = 0.0174533;
	
	public void updatePos(Coordinate position, int duration, int direction, double speed, Coordinate boundary)
	{
		double distance = speed*duration;
		
		position.x += getDeltaX(position, boundary, direction, distance);
		position.y += getDeltaY(position, boundary, direction, distance);
	}
	
	private double getDeltaX(Coordinate position, Coordinate boundary, int direction, double distance)
	{
		double distanceToBoundaryLeft = -position.x;
		double distanceToBoundaryRight = boundary.x - position.x;
		double deltaX = distance*Math.sin(direction*DEG_TO_RAD);
		if(deltaX > distanceToBoundaryRight)
			deltaX = distanceToBoundaryRight;
		else if(deltaX < distanceToBoundaryLeft)
			deltaX = distanceToBoundaryLeft;
		
		return deltaX;
	}
	
	private double getDeltaY(Coordinate position, Coordinate boundary, int direction, double distance)
	{
		double distanceToBoundaryBottom = -position.y;
		double distanceToBoundaryTop = boundary.y - position.y;
		double deltaY = distance*Math.cos(direction*DEG_TO_RAD);
		if(deltaY > distanceToBoundaryTop)
			deltaY = distanceToBoundaryTop;
		else if(deltaY < distanceToBoundaryBottom)
			deltaY = distanceToBoundaryBottom;
		
		return deltaY;
	}
}

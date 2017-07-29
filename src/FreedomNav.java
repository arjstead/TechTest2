
public class FreedomNav extends NavModule
{
	private final double DEG_TO_RAD = 0.0174533;
	
	public void updatePos(Drone drone, Coordinate position, int duration, int direction, double speed)
	{
		drone.position.x += speed*duration*Math.sin(direction*DEG_TO_RAD);
		drone.position.y += speed*duration*Math.cos(direction*DEG_TO_RAD);
	}
}


public class Coordinate 
{
	double x,y;
	
	public Coordinate(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String toString()
	{
		return String.format("(%.1f, %.1f)", x, y);
	}
}

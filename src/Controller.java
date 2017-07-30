import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller
{
	private Drone drone;
	private BufferedReader instructionFileReader = null;
	private FileReader fr = null;

	public static void main(String[] args) 
	{
		 String instructionFilePath = "C:\\Users\\Stead\\workspace\\AccessPay\\tests\\test7.txt";
		
		// Gets the file containing the instruction stream from the CLI argument.
		//String instructionFilePath = args[0];
		
		// Creates an instance to control the drone.
		Controller controller = new Controller(instructionFilePath);
		
		// Gives the controler a drone to fly.
		controller.drone = new Drone(controller, new FreedomNav());
		
		// Starts the control loop of the drone.
		controller.drone.run();
	}
	
	public Controller(String fileName)
	{
		File testFile = new File(fileName);
		try 
		{
			fr = new FileReader(testFile);
			instructionFileReader = new BufferedReader(fr);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}
	
	public String sendNextInstruction()
	{
		try
		{
			return instructionFileReader.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void output(String state)
	{
		System.out.println(state);
	}
}

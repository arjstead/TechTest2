import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller
{
	private static Drone drone = new Drone();
	private static BufferedReader instructionFileReader = null;
	private static FileReader fr = null;

	public static void main(String[] args)
	{
		String instructionFilePath = args[0];
		setUpFileReader(instructionFilePath);
		
		// Loop through commands in the instruction file.
		try
		{
			String instruction;
			while ((instruction = instructionFileReader.readLine()) != null)
			{
				output(drone.execute(instruction));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (instructionFileReader != null)
					instructionFileReader.close();
				if (fr != null)
					fr.close();
			} 
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public static void setUpFileReader(String fileName)
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
		finally 
		{
			try 
			{
				if (instructionFileReader != null)
					instructionFileReader.close();
				if (fr != null)
					fr.close();
			} 
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private static void output(Object obj)
	{
		System.out.println(obj);
	}
	
	
	

}

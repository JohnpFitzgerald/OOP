package Storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorage 
{
    public Object readObject(String fileName) 
    {
		Object o = null;
		
		try 
		{
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = in.readObject();
			in.close();
		} 
		catch (IOException i) 
		{
			i.printStackTrace();
		} 
		catch (Exception c) 
		{
			c.printStackTrace();
		}
		
		return o;
	}

    
	public boolean writeObject(Object o, String fileName) 
	{
		boolean success = false;
		try 
		{
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			out.close();
			fileOut.close();
			success = true;
		} 
		catch (IOException i) 
		{
			i.printStackTrace();
		}
		
		return success;
	}
}

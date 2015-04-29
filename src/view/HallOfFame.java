package view;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Biomorph;

/**
 * @author Satpal Singh (singhs23)
 *
 */
public class HallOfFame {
	
	/**
	 * @param biomorph
	 * @param fileName
	 * @return true if the Biomorph is successfully saved and false if an error occurred.
	 */
	public boolean saveHallOfFame(Biomorph biomorph, String fileName){
			
		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName + ".ser"));
			out.writeObject(biomorph);
			out.close();
			
			// Serialize data object to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
			out = new ObjectOutputStream(bos) ;
			out.writeObject(biomorph);
			out.close();

			// Get the bytes of the serialized object
			byte[] buf = bos.toByteArray();
			return true;
			} 
		catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    return false;
			}
	}
	
	/**
	 * @param fileName
	 * @return Biomorph object which is saved for hall of fame.
	 */
	public Biomorph readHallOfFame(String fileName){
		try{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName + ".ser"));
		Biomorph biomorph = (Biomorph) input.readObject();
		input.close();
		if (biomorph != null)
		return biomorph;
		
		} catch (FileNotFoundException e){
			
			System.out.println(e.toString());
			
		} catch (IOException e){
			
			System.out.println(e.toString());
			
		} catch (ClassNotFoundException e){
			
			System.out.println(e.toString());
			
		}
		
		return null;
	}
	
	public static void main(String[] args) {

	HallOfFame h = new HallOfFame();
	int[] genes = {4,5,65,3,2,54,43,24};
	Biomorph bio = new Biomorph(genes);
	boolean saved = h.saveHallOfFame(bio, "test");
	System.out.println(saved);
	Biomorph readBio = h.readHallOfFame("test");
	System.out.println(readBio);
	for(int gene: readBio.getGenes())
		System.out.println("Test : "+gene);

	}

}

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
	 *            -
	 * @param fileName
	 * @return true if the Biomorph is successfully saved and false if an error
	 *         occurred.
	 */
	public boolean saveHallOfFame(Biomorph biomorph, String fileName) {

		int savingGenes[] = new int[biomorph.getGenesLength()];
		for (int i = 0; i < biomorph.getGenesLength(); i++) {

			savingGenes[i] = biomorph.getGenes()[i];

		}
		Biomorph saving = new Biomorph(savingGenes);

		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileName + ".bio"));
			out.writeObject(saving);
			out.close();

			// Serialize data object to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(saving);
			out.close();

			// Get the bytes of the serialized object
			byte[] buf = bos.toByteArray();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Delete file associated with BioMorph stored in Hall of fame.
	 * 
	 * @param fileName
	 *            - File name associated with the biomorph that needs to be
	 *            deleted.
	 * @return True if successful, false otherwise.
	 */
	public boolean removeHallOfFame(String fileName) {
		int[] emptyGenes = { 0, 0, 0, 0, 0, 0 };
		Biomorph saving = new Biomorph(emptyGenes);
		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileName + ".bio"));
			out.writeObject(saving);
			out.close();

			// Serialize data object to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(saving);
			out.close();

			// Get the bytes of the serialized object
			byte[] buf = bos.toByteArray();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Method to read .bio files which are associated with the Biomorph in the
	 * Hall of fame.
	 * 
	 * @param fileName
	 *            Name of the file associated with the Biomorph in the Hall of
	 *            Fame
	 * @return Biomorph object which is saved for hall of fame.
	 */
	public Biomorph readHallOfFame(String fileName) {

		try {

			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream(fileName + ".bio"));
			Biomorph biomorph = (Biomorph) input.readObject();
			input.close();
			if (biomorph != null)
				return biomorph;

		} catch (FileNotFoundException e) {

			System.out.println(e.toString());

		} catch (IOException e) {

			System.out.println(e.toString());

		} catch (ClassNotFoundException e) {

			System.out.println(e.toString());

		}

		return null;
	}

}

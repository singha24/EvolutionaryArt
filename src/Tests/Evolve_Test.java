package Tests;

import static org.junit.Assert.*;
import model.Biomorph;
import model.BiomorphCreator;
import model.Evolve;

import org.junit.Test;

/**
 * @author Assa Singh
 * @category Test
 * @see Evolve
 * A Test class to assert that the genes of the original biomorph are not equal to the mutated version. This test is expected to fail-
 * due to assertArrayEquals() which returns true if the two array are equal. 
 *
 */
public class Evolve_Test {
	

	private Evolve e;
	private Biomorph b;
	private BiomorphCreator bc;
	
	@Test
	public void compareGenes(){
		bc = new BiomorphCreator();
		b = new Biomorph(bc.generateRandomBiomorph().getGenes());
		int[] original = b.getGenes();
		
		int[] modified = e.evolve(b);
		
		assertArrayEquals(original, modified); //assert not equals - expected failure.
		
		/*for(int i = 0; i<original.length; i++){
			for(int j = 0; j<modified.length; j++){
				assertNotSame(original[i], modified[j]);
			}
		}*/
		
	}
	
}

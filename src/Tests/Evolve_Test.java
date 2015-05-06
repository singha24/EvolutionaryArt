package Tests;

import static org.junit.Assert.*;
import model.Biomorph;
import model.BiomorphCreator;
import model.Evolve;

import org.junit.Test;

public class Evolve_Test {

	private Biomorph b;
	private BiomorphCreator bc;
	
	@Test
	public void compareGenes(){
		bc = new BiomorphCreator();
		b = new Biomorph(bc.generateRandomBiomorph().getGenes());
		int[] original = b.getGenes();
		
		int[] modified = Evolve.evolve(b.getGenes());
		
		assertArrayEquals(original, modified); //assert not equals - expected failure.
		
		/*for(int i = 0; i<original.length; i++){
			for(int j = 0; j<modified.length; j++){
				assertNotSame(original[i], modified[j]);
			}
		}*/
		
	}
	
}

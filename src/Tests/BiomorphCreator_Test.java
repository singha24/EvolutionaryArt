package Tests;

import static org.junit.Assert.*;
import model.Biomorph;
import model.BiomorphCreator;

import org.junit.Test;

public class BiomorphCreator_Test {
	
	private BiomorphCreator bc;
	private Biomorph b;
	
	
	@Test
	public void checkGeneLimit(){
		bc = new BiomorphCreator();
		assertNotNull(bc.GENE_LIMIT);
	}
	
	@Test
	public void checkBioMorphGenes(){
		bc = new BiomorphCreator();
		b = new Biomorph(bc.generateRandomBiomorph().getGenes());
		int[] temp = b.getGenes();
		assertNotNull(temp);
	}
	
	

}

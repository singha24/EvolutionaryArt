package model;

import java.util.Random;

/**
 * BiomorphCreator responsible to create biomorph by using 
 * Random numbers as genes values
 * 
 * @author Satpal Singh, Amy Wood
 * @version 15 Dec 2014
 */
public class BiomorphCreator {
	
	/*
	 * limit for the genes needed for the biomorph.
	 */
	private static final int GENE_LIMIT= 13;
	
	//variable to hold the geneLimit
	private int geneLimit;
	// A random number generator used for randomising the genes 
	private Random rand;
	
	private Biomorph biomorph;
	private BioWarehouse warehouse = new BioWarehouse();
	
	
	/**
	 * Constructor to initialise the fields
	 */
	public BiomorphCreator(){
		this.geneLimit = GENE_LIMIT;
		rand = new Random();
	}
	
	
	/**
	 * Method to create random genes, the number of genes is based on the gene limit.
	 * @return An array of int
	 */
	private int[] createRandomGenes(){
		
		int[] genes = new int[geneLimit];
		// Get random number and store it onto array
		for(int i = 0; i < genes.length; i++){
		genes[i] = rand.nextInt(350) + 150;
		}
		return genes;
	}
	
	/**
	 * Method to create Biomorph and returns it
	 * 
	 * @return Biomorph object with random genes values
	 */
	public Biomorph generateRandomBiomorph(){		
		Biomorph b = new Biomorph(createRandomGenes());
		warehouse.addBioMorph(b);
		return b;
	}
	
	public Biomorph extendRandomBiomorph(){
		Biomorph b = warehouse.getBiomorph(0);
		return new Biomorph(extendBiomorph(b));
	}
	
	public int[] extendBiomorph(Biomorph b){
		int[] genes = b.getGenes();
		for(int i = 0; i < genes.length; i++){
			genes[i] += rand.nextInt((350) + 150); //add values to current biomorph genes.
		}
		return genes;
		
	}
	
}

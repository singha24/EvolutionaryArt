package model;

import java.util.Random;

/**
 * BiomorphCreator responsible to create biomorph by using 
 * Random numbers as genes values
 * 
 * @author Satpal Singh, Amy Wood
 * @version 15 Dec 2014
 */
/**
 * @author singhs23
 *
 */
public class BiomorphCreator {
	
	/*
	 * limit for the genes needed for the Biomorph.
	 */
	public int GENE_LIMIT;
	
	/*
	 * Range for positioning the lines.
	 */
	public static final int LINES_PLOTTING_LIMIT = 50;

	/*
	 * limit for the genes needed for the Biomorph.
	 */
	public static final int LIMIT_FOR_GENES = 50;
	
	//variable to hold the geneLimit
	private int geneLimit;
	// A random number generator used for randomising the genes 
	private Random rand;
	
	/**
	 * Constructor to initialise the fields
	 */
	public BiomorphCreator(){
		rand = new Random();
		// sets the genes limit with help of initGenelimit
		if(getGeneLimit() == 0){
			setGeneLimit(initGeneLimit());
		}
		this.geneLimit = getGeneLimit();
	}
	
	
	/**
	 * Method to create random genes, the number of genes is based on the gene limit.
	 * @return An array of int as genes
	 */
	private int[] createRandomGenes(){
		
		int[] genes = new int[geneLimit];
		// Get random number and store it onto array
		for(int i = 0; i < genes.length; i++){
			if(i < genes.length-3){
		genes[i] = rand.nextInt(LINES_PLOTTING_LIMIT);
			}
			else{
				genes[i] = rand.nextInt(255);
				
			}
		
		}
		return genes;
	}
	
	/**
	 * Initialises the genes limit by using random generator between 25 - 76
	 * @return int as genes limit
	 */
	public int initGeneLimit(){
		return rand.nextInt(((LINES_PLOTTING_LIMIT)*2 - (LINES_PLOTTING_LIMIT)/2) + 1) + (LINES_PLOTTING_LIMIT)/2;
		
	}
	
	
	/**
	 * Returns the genes limit.
	 * @return int  - Returns the genes limit
	 */
	public int getGeneLimit(){
		return GENE_LIMIT;
	}
	
	/**
	 * Sets the genes limit.
	 * @param limit - genes limit
	 */
	public void setGeneLimit(int limit){
		GENE_LIMIT = limit;
	}
	
	/**
	 * Method to create Random Biomorph and returns it
	 * @return Biomorph object with random genes values
	 */
	public Biomorph generateRandomBiomorph(){		
		Biomorph b = new Biomorph(createRandomGenes());
		return b;
	}
	

	/**
	 * Extends Biomorph based on the biomorph provided.
	 * @param bio - used to extend biomorph
	 * @return Biomorph - returns new extended version of Biomorph.
	 */
	public int[] extendBiomorph(Biomorph b){
		
		int[] genes = b.getGenes(); 
		for(int i = 0; i<genes.length; i++){
				}
		 int[] newGenes = Evolve.evolve(genes);
		
		return newGenes;
		
	}

	
}

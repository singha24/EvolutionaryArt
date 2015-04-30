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
	public int GENE_LIMIT;
	
	public static final int LIMIT_FOR_GENES = 50;
	
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
		rand = new Random();
		if(getGeneLimit() == 0){
			setGeneLimit(initGeneLimit());
		}
		this.geneLimit = getGeneLimit();
	}
	
	
	/**
	 * Method to create random genes, the number of genes is based on the gene limit.
	 * @return An array of int
	 */
	private int[] createRandomGenes(){
		
		int[] genes = new int[geneLimit];
		// Get random number and store it onto array
		for(int i = 0; i < genes.length; i++){
			if(i < genes.length-3){
		genes[i] = rand.nextInt(LIMIT_FOR_GENES);
			}
			else{
				genes[i] = rand.nextInt(255);
				
			}
		
		}
//		genes[0]=0;
//		genes[2]=0;
//		genes[4]=0;
//		genes[6]=0;
		return genes;
	}
	
	public int initGeneLimit(){
		return rand.nextInt(((LIMIT_FOR_GENES)*2 - (LIMIT_FOR_GENES)/2) + 1) + (LIMIT_FOR_GENES)/2;
		
	}
	
	public int getGeneLimit(){
		return GENE_LIMIT;
	}
	
	public void setGeneLimit(int limit){
		GENE_LIMIT = limit;
	}
	
	/**
	 * Method to create Biomorph and returns it
	 * 
	 * @return Biomorph object with random genes values
	 */
	public Biomorph generateRandomBiomorph(){		
		Biomorph b = new Biomorph(createRandomGenes());
		//warehouse.saveBioMorph(b);
		return b;
	}
	
	
	public Biomorph extendRandomBiomorph(Biomorph bio){
//		Biomorph b = warehouse.getBiomorph(0);
//		Biomorph b1 = warehouse.getBiomorph(1);
//		extendBiomorph(b1);
		return new Biomorph(extendBiomorph(bio));
	}
	
	public int[] extendBiomorph(Biomorph b){
		int[] genes = b.getGenes(); //temporary
		System.out.println("original");
		for(int i = 0; i<genes.length; i++){
			System.out.println(genes[i]);
				}
		 int[] newGenes = Evolve.algorithm(genes);
		 //int[] genes1 = warehouse.getBiomorph(1).getGenes(); //temporary
			System.out.println("original");
//			for(int i = 0; i<genes1.length; i++){
//				System.out.println(genes1[i]);
//					}
//			 Evolve.algorithm(genes1);
		
//		System.out.println("original");
//		for(int i = 0; i<genes.length; i++){
//			System.out.println(genes[i]);
//		}
//		
//		System.out.println("------");
//		System.out.println("modified");
//		
//		Random ran = new Random();
//		//TODO: IT SHRINKS BECAUSE VALUES GET TO 40s 
//		for(int i = 0; i < genes.length; i +=   ran.nextInt(10)){
//			if(i<21){
//			if(genes[i] <= 42){
//			genes[i] += 5 ;
//			}else if(genes[i] > 42) {
//				genes[i] -= 5;
//				
//			}
//			}else{
//				
//				if(genes[i] <= 250){
//					genes[i] += 5;
//					}else if(genes[i] > 5) {
//						genes[i] -= 5;
//						
//					}
//			}
//					 //add values to current genes of biomorph genes.
//		}
//		
		for(int i = 0; i<genes.length; i++){
			System.out.println("saved GENES: "+newGenes[i]);
		}
		System.out.println("GENE LIMIT= " + getGeneLimit());
//		System.out.println("---end----");
		//Biomorph a = new Biomorph(newGenes);
		//warehouse.addBioMorph(a);
		return newGenes;
		
	}

	
}

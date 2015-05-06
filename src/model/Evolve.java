package model;

import java.util.Random;

/**
 * Evolve class - Evolves the Biomorph genes by adding/subtracting values to the genes.
 * @author Satpal Singh
 * @version 06 May 2015
 */
public final class Evolve {

	/**
	 * Static method which evolves the genes by adding and subtracting to each genes value.
	 * @param genes - an array which is used to evolve 
	 * @return int array - an evolve genes values.
	 * 
	 */
	public static int[] evolve(int genes[]){

		Random random = new Random();
		
		int[] newGenes = new int[genes.length];
		for(int i = 0; i<genes.length; i++){
			newGenes[i] = genes[i];
		}
		
		for (int i = 0; i < genes.length; i++){
			
				int rdm;
				int genesValue;
			
			if(i<genes.length-3){
				
				rdm = random.nextInt(7) - 3;
				genesValue = genes[i] + (rdm);
				genes[i] = genesValue;
				if(genes[i] > 50){
					
					genes[i] = 50;
					
					}else if(genes[i] < 0) {
						genes[i] = 0;
						
					}
				
				}else{
						rdm = random.nextInt(35) - 17;
						genesValue = genes[i] + (rdm);
						genes[i] = genesValue;
					if(genes[i] > 255){
						
						genes[i] = 255;
						}else if(genes[i] < 0) {
							genes[i] = 0;
							
						}
				}
			
			
		}
		
		return newGenes;
		
	}
	
}

package model;

import java.util.Random;

/**
 * @author singhs23
 *
 */
public final class Evolve {

	
	/**
	 * 
	 * @param biomorph
	 * 
	 */
	public static int[] evolve(Biomorph biomorph){
		
		return algorithm(biomorph.getGenes());
		
	}
	
	/**
	 * 
	 * @param genes
	 * @return
	 * 
	 */
	public static int[] algorithm(int genes[]){
		//TODO: algorithm for the evolution

		Random random = new Random();
		
		int[] newGenes = new int[genes.length];
		for(int i = 0; i<genes.length; i++){
			newGenes[i] = genes[i];
		}
		
		for (int i = 0; i < genes.length; i +=  random.nextInt(5)){
			
				int rdm;
				//int genesToMutate = random.nextInt(genes.length);
				//System.out.println(rdm);
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
			
			
			//System.out.println("NEW VALUE: "+genesValue);
			
		}
		
		return newGenes;
		
	}
	
//	public static void main(String[] args) {
//
//		Evolve ev = new Evolve();
//		Random ran = new Random();
//		int genes[] = new int[21];
//		for(int i = 0; i < 21; i++){
//		genes[i]=ran.nextInt(50);
//		System.out.println("OLD VALUE: "+genes[i]);
//		}
//		ev.algorithm(genes);
//
//	}
	
}

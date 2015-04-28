package model;

import java.util.Random;

public class EvolutionModel {
	private BiomorphCreator bioCreate = new BiomorphCreator();
	private int GENE_LIMIT = bioCreate.getGeneLimit();
	private static final int SEED = BiomorphCreator.SEED;
	private int[] parent1 = new int[GENE_LIMIT];
	private int[] parent2 = new int[GENE_LIMIT];
	private int[] child = new int[GENE_LIMIT];
	
	public EvolutionModel(){
	}
	
	public void generateParnts(){
		this.parent1 = bioCreate.generateRandomBiomorph().getGenes();
		this.parent2 = bioCreate.generateRandomBiomorph().getGenes();
	}
	
	//Child inherits a gene which is in the range of p1 and p2 if the p1 wins 
	public void compete(){
		for(int i = 0; i<parent1.length;i++){
			for(int j = 0; j<parent2.length; j++){
				if(parent1[i] > parent2[j]){
					System.out.println("P1 Wins: " + parent1[i] + "-" + parent2[j]);
					 child[i] = randInt(parent1[i], parent2[j]);
				}else{
					System.out.println(parent1[i] + "-" + parent2[j] + " :P2 Wins");
					child[i] = parent2[j];
				}
			}
		}
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random(SEED);

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	

	public static void main(String[] args) {
		EvolutionModel e = new EvolutionModel();
		e.generateParnts();
		e.compete();
	}
	
}

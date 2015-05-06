package model;

/**
 * BioWarehouse - Class that stores, accesses, modifies temporary Biomorphs stored
 * @author Matthew Chambers, Amy Wood
 * @version May 06 2015
 */
public class BioWarehouse {
	
	private Biomorph[] biomorphs;
	private int n = 0;

	
	public BioWarehouse(){
		this.biomorphs = new Biomorph[8];
	}
	
	public Biomorph getBiomorph(int i){
		
			return biomorphs[i];
		
	}
	
	public void saveBioMorph(int[] genes){
		
		Biomorph tempBiomorph = new Biomorph(genes);
		
		if(checkFreeSpace() == true){
			for(int i = 0; i < biomorphs.length; i++){
				if(biomorphs[i] == null){
					biomorphs[i] = tempBiomorph;
					break;
				}
			}	
		}

	}
	
	public void deleteBiomorph(int i){
		biomorphs[i] = null;
	}
	
	public int getStoreLength(){
		return biomorphs.length;
	}
	
	private Boolean checkFreeSpace(){
		for(int i = 0; i < biomorphs.length; i++){
			if(biomorphs[i] == null){
				return true;
			}
		}
		return false;
	}

	
}
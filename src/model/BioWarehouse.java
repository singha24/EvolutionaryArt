package model;


public class BioWarehouse {
	
	private Biomorph[] biomorphs;

	
	public BioWarehouse(){
		this.biomorphs = new Biomorph[8];
	}
	
	public Biomorph getBiomorph(int i){
		
			return biomorphs[i];
		
	}
	
	public void saveBioMorph(int[] genes){
		
		Biomorph tempBiomorph = new Biomorph(genes);
		
		for(int i = 0; i < biomorphs.length; i++){
			if(biomorphs[i] == null){
				biomorphs[i] = tempBiomorph;
				break;
			}	
		}

	}
	
	public void deleteBiomorph(int i){
		biomorphs[i] = null;
	}
	
	public int getStoreLength(){
		return biomorphs.length;
	}


}

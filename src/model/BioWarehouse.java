package model;

/**
 * BioWarehouse - Class that stores, accesses, modifies temporary Biomorphs stored
 * @author Matthew Chambers, Amy Wood
 * @version May 06 2015
 */
public class BioWarehouse {
	// Class variable
	private Biomorph[] biomorphs;

	/**
	 * Constructor - creates BioWarehouse, initialises array of biomorphs
	 */
	public BioWarehouse(){
		this.biomorphs = new Biomorph[8];
	}
	
	/**
	 * Gets the Biomorphs from the Biomorph storage
	 * @param index - used as index for biomorph to be returned.
	 * @return Biomorph - the stored Biomorph.
	 */
	public Biomorph getBiomorph(int index){
		
			return biomorphs[index];
		
	}
	
	/**
	 * Saves the Biomorph in Biomorph array, takes in array of int 
	 * @param genes - array of int which is used to construct Biomorph and stores in Biomorph storage
	 */
	public void saveBioMorph(int[] genes){
		
		Biomorph tempBiomorph = new Biomorph(genes);
		
		if(checkFreeSpace()){
			for(int i = 0; i < biomorphs.length; i++){
				if(biomorphs[i] == null){
					biomorphs[i] = tempBiomorph;
					break;
				}
			}	
		}

	}
	
	/**
	 * Deletes Biomorph from the storage based on the index provided
	 * @param index - Index used to delete Biomorph from storage.
	 */
	public void deleteBiomorph(int index){
		biomorphs[index] = null;
	}
	
	/**
	 * Returns the Biomorph storage length
	 * @return int - length of storage
	 */
	public int getStoreLength(){
		return biomorphs.length;
	}
	
	/**
	 * Checks the next or a free space in Biomorph storage.
	 * @return Boolean - return true if there is free space available and false the is not.
	 */
	private Boolean checkFreeSpace(){
		for(int i = 0; i < biomorphs.length; i++){
			if(biomorphs[i] == null){
				return true;
			}
		}
		return false;
	}

	
}
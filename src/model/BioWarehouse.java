package model;

import java.util.ArrayList;

public class BioWarehouse {
	
	private ArrayList<Biomorph> biomorphs;
	
	public BioWarehouse(){
		biomorphs = new ArrayList();
	}
	
	public Biomorph getBiomorph(int i){
		
			return biomorphs.get(i);
		
	}
	
	public int getArraySize(){
		int counter = 0;
		for(int i =0; i<biomorphs.size();i++){
			counter++;
		}
		return counter;
	}
	
	public void addBioMorph(Biomorph b){
		biomorphs.add(b);
	}

}

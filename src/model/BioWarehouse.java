package model;

import java.util.ArrayList;

public class BioWarehouse {
	
	private ArrayList<Biomorph> biomorphs;
	
	public BioWarehouse(){
		biomorphs = new ArrayList();
	}
	
	public Biomorph getBiomorph(int i){
		Biomorph bio = null;
		for(int j = 0; j <= biomorphs.size(); j++){
			bio = biomorphs.get(i);
		}
		return bio;
	}
	
	public void addBioMorph(Biomorph b){
		biomorphs.add(b);
	}

}

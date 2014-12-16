package Biomorph;

import java.util.Random;

public class BiomorphCreator extends Evolve {
	
	int geneLimit;
	
	public BiomorphCreator(){
		geneLimit = 20;
	}
	
	public void createChild(Biomorph m, Biomorph d){
		//TODO
	}
	
	public Biomorph generateRandomBiomorph(){
		return new Biomorph();
	}

	public void createChild(){
		//TODO
	}
	
	public Biomorph getChild(){
		return bioMorph;
		//TODO
	}
	
	public Biomorph getParent(){
		return bioMorph;
		//TODO
	}
	
	
}

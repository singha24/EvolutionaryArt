package model;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class BioWarehouse {
	
	private ArrayList<Biomorph> biomorphs;
	private ArrayList<Graphics2D> g2d;
	
	public BioWarehouse(){
		this.biomorphs = new ArrayList<Biomorph>();
		this.g2d = new ArrayList<Graphics2D>();
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
	
	public void storeG2D(Graphics2D g2d){
		this.g2d.add(g2d);
	}
	
	public Graphics2D getG2D(int i){
		return g2d.get(i);
	}

}

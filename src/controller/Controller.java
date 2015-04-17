package controller;

import java.util.ArrayList;

import model.BiomorphCreator;
import view.GUI;
import view.Renderer;

/**
 * Controller class is the Main source for which everything is created.
 * 
 * @author Satpal Singh, Matthew Chambers, Assa Singh
 * @version 17 Dec 2014
 */
public class Controller {

	private BiomorphCreator bioCreate;
	private Renderer bioOne;
	private Renderer bioTwo;
	private Renderer[] temp = new Renderer[8];
	private GUI gui;

	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {
		
	}
	
	public void generateParents(){
		bioOne = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
		bioTwo = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
	}
	
	public void initiliseGui(){
		gui = new GUI(bioOne, bioTwo, temp, bioCreate);
	}
	
	public void initiliseCreator(){
		bioCreate = new BiomorphCreator();
	}
	
	public void createTempBiomorphs(){
		for(int i = 0; i<temp.length; i++){
			temp[i] = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
		}
	}
	
	public Renderer[] getTempBiomorphs(){
		return temp;
	}
	
	public int getTempArraySize(){
		int counter=0;
		for(int i=0; i<temp.length;i++){
			counter++;
		}
		return counter;
	}


	/**
	 * The Main method creates controller object which Outputs the biomorph onto
	 * JFrame
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Controller control = new Controller();
		
		control.initiliseCreator();
		control.generateParents();
		control.createTempBiomorphs();
		control.initiliseGui();

	}

}

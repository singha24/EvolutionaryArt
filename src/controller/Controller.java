package controller;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.BioWarehouse;
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
	private Save save;
	private BioWarehouse warehouse;

	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {
		
	}
	
	public void initiliseHelpers(){
		warehouse = new BioWarehouse();
		bioCreate = new BiomorphCreator();
		save = new Save();
	}
	
	
	public void generateParents(){
		bioOne = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
	}
	
	
	public void createTempBiomorphs(){
//		for(int i = 0; i < temp.length; i++){
//			temp[i] = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
//		}
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
	
	public void export(JPanel biomorph){
		save.convertToImage(biomorph);
	}
	
	public void initGUI(){
		gui = new GUI(bioOne, temp, bioCreate);
	}


	/**
	 * The Main method creates controller object which Outputs the biomorph onto
	 * JFrame
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Controller control = new Controller();
		
		control.initiliseHelpers();
		control.generateParents();
		control.createTempBiomorphs();
		control.initGUI();

	}

}

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
	private Renderer render;
	private GUI gui;
	private BioWarehouse warehouse;

	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {

		warehouse = new BioWarehouse();
		bioCreate = new BiomorphCreator();
		render = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
		Renderer parent2 = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
		gui = new GUI(render, parent2, bioCreate);

	}


	/**
	 * The Main method creates controller object which Outputs the biomorph onto
	 * JFrame
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Controller control = new Controller();

	}

}

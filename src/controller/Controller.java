package controller;

import model.*;
import view.*;

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

	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {

		bioCreate = new BiomorphCreator();
		render = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
		gui = new GUI(render, bioCreate);

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

package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import view.GUI;
import view.Renderer;
import model.BioWarehouse;
import model.Biomorph;
import model.BiomorphCreator;

/**
 * Controller class is the Main source for which everything is created.
 * 
 * @author Satpal Singh, Matthew Chambers, Assa Singh
 * @version 6 May 2015
 */
public class Controller {

	//declare class variables 
	
	
	private BiomorphCreator bioCreate;
	private Renderer parent;
	private Renderer[] children;
	private BioWarehouse warehouse;
	private GUI gui;

	private static long timerStart;
	private static long timerEnd;
	private static long elapsedTime;

	private static String sysLog;


	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {

	}

	
	/**
	 * Initialise class variables creating new instances of the appropriate classes
	 */
	public void initiliseHelpers() {
		warehouse = new BioWarehouse();
		bioCreate = new BiomorphCreator();
		children = new Renderer[8];

	}

	/**
	 * Generates a new parent biomorph using the instance of the biomorph creator,
	 * and stores it inside of class variable parent.
	 * 
	 * @param width - sets the biomorphs display width
	 * @param height - sets the biomorphs display height
	 */
	public void generateParents(int width, int height) {
		parent = new Renderer(bioCreate.generateRandomBiomorph().getGenes(), width,
				height, 2.9, 2.9);

	}
	
	/**
	 * Generates a child from the the set of parent genes passed through the parameter 
	 * @param parent - the array of genes passed from the parent biomorph
	 * @return a child biomorph 
	 */
	private int[] generateChild(int[] parent){
		return bioCreate.extendBiomorph(new Biomorph(parent));
	}
	
	/**
	 * Helper method for producing the number of children as specified by the size of the children array
	 * @return a populated array with varied children derived from the parents gene values
	 */
	public Renderer[] createChildren(){
		Renderer child;
		
		for(int i = 0; i < children.length; i++){
			child = new Renderer(generateChild(parent.getGenes()), 20,20,1,1);
			children[i] = child;
		}
		return children;
	}
	
	/** 
	 * Helper method for returning the size of the child array
	 * @return int value with the size of the children array
	 */
	public int getChildArraySize(){
		return children.length;
	}
	
	/**
	 * Initialises the GUI for the program passing the appropriate variables 
	 * @return Array of Renderer objects
	 */
	public Renderer[] getTempBiomorphs() {
		return children;
	}

	/**
	 * Initialises the GUI for the program passing the appropriate variables 
	 * @return int - size of the temporary childred
	 */
	public int getTempArraySize() {
		int counter = 0;
		for (int i = 0; i < children.length; i++) {
			counter++;
		}
		return counter;
	}

	/**
	 * Initialises the GUI for the program passing the appropriate variables 
	 */
	public void initGUI() {
		this.gui = new GUI(parent, children, bioCreate, warehouse);
	}

	/**
	 * Generates a text file, saving the file name as the file name and the document text as the text param
	 * @param filename - the name of the file you wish to save the document under
	 * @param text - the text you wish to be contained in the document
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void generateTextFile(String filename, String text)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
		writer.print(text);
		writer.close();
	}

	/**
	 * Gets current system date and time returns as a string
	 * @return string representation of the current system date and time
	 */
	public static String getDateTime() {
		return new Date().toString();
	}

	/**
	 * Reads in the system file 
	 * @param fileName - name of the file to be read
	 * @return string representation of the whole system file for displaying in the program
	 * @throws IOException
	 */
	public static String readSysFile(String fileName) throws IOException {
		String temp = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			temp = sb.toString();
		} finally {
			br.close();
		}
		return temp;
	}

	/**
	 * The Main method creates controller object which Outputs the biomorph onto
	 * JFrame
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Controller control = new Controller();
		
		sysLog = "System Log: " + getDateTime()
				+ System.getProperty("line.separator");

		timerStart = System.currentTimeMillis();
		
		control.initiliseHelpers();
		
		sysLog += "Initilising helpers..."
				+ System.getProperty("line.separator");
	
		control.generateParents(40, 40);
		sysLog += "Done." + System.getProperty("line.separator");
		
		sysLog += "Generating biomorph..." 
				+ System.getProperty("line.separator");
		control.createChildren();
		
		sysLog += "Done." + System.getProperty("line.separator");
		
		control.initGUI();
		
		sysLog += "Initilising GUI..." + System.getProperty("line.separator");
		
		timerEnd = System.currentTimeMillis();
		
		sysLog += "Done." + System.getProperty("line.separator");
		
		elapsedTime = timerEnd - timerStart;

		sysLog += System.getProperty("line.separator");
		
		sysLog += "Time taken to boot application: " + elapsedTime
				+ " milliseconds";
		timerEnd = System.currentTimeMillis();
		elapsedTime = timerEnd - timerStart;

		try {
			control.generateTextFile("SystemLog", sysLog);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

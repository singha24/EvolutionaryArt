package controller;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import view.GUI;
import view.Renderer;
import model.BioWarehouse;
import model.Biomorph;
import model.BiomorphCreator;

/**
 * Controller class is the Main source for which everything is created.
 * 
 * @author Satpal Singh, Matthew Chambers, Assa Singh
 * @version 17 Dec 2014
 */
public class Controller {

	private BiomorphCreator bioCreate;
	private Renderer parent;
	//private Renderer bioTwo;
	private Renderer[] children = new Renderer[8];
	private GUI gui;
	private BioWarehouse warehouse;

	private static long timerStart;
	private static long timerEnd;
	private static long elapsedTime;

	private static String sysLog;
	private static String instructions;
	private static boolean ready = false;

	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {

	}

	public void initiliseHelpers() {
		warehouse = new BioWarehouse();
		bioCreate = new BiomorphCreator();
	}

	public void generateParents(int x, int y) {
		parent = new Renderer(bioCreate.generateRandomBiomorph().getGenes(), x,
				y, 2.9, 2.9);
		//bioTwo = new Renderer(generateChild(bioOne.getGenes()));

	}
	
	private int[] generateChild(int[] parent){
		return bioCreate.extendBiomorph(new Biomorph(parent));
		
//		int[] child = new int[parent.length];
//		
//		for(int i =0; i<parent.length; i++){
//			
//			child[i] = parent[i]; 
//	
//		}
		
		//return child;
		
	}
	
	public Renderer[] createChildren(){
		
		//bioTwo = new Renderer(generateChild(parent.getGenes()), 10,10);
		//ArrayList<Renderer> children = new ArrayList<Renderer>();
		//Renderer[] children = new Renderer[8];
		Renderer child;
		for(int i = 0; i < children.length; i++){
			child = new Renderer(generateChild(parent.getGenes()), 20,20,1,1);
			children[i] = child;
				//temp[i] = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
			
		}
		
		return children;

	}
	
	public int getChildArraySize(){
		return children.length;
	}

	public Renderer[] getTempBiomorphs() {
		return children;
	}

	public int getTempArraySize() {
		int counter = 0;
		for (int i = 0; i < children.length; i++) {
			counter++;
		}
		return counter;
	}

	public void initGUI() {
		gui = new GUI(parent, children, bioCreate, warehouse);
	}

	public void generateTextFile(String filename, String text)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
		writer.print(text);
		writer.close();
	}

	public static String getDateTime() {
		Date date = new Date();
		return date.toString();
	}

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
		control.generateParents(50, 50);
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

		sysLog += "*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*";
		sysLog += System.getProperty("line.separator");
		
		sysLog += "Time taken to boot application: " + elapsedTime
				+ " milliseconds";
		sysLog += "Initilising GUI..." + System.getProperty("line.separator");
		timerEnd = System.currentTimeMillis();
		sysLog += "Done." + System.getProperty("line.separator");
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
		sysLog += "Time taken to boot application: " + elapsedTime
				+ " milliseconds";

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

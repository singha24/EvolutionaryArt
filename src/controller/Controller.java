package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

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

	private static long timerStart;
	private static long timerEnd;
	private static long elapsedTime;

	private static String sysLog;
	private static boolean ready = false;

	/**
	 * Create and initialise all the object needed to run the prototype
	 */
	public Controller() {

	}

	public void initiliseHelpers() {
		warehouse = new BioWarehouse();
		bioCreate = new BiomorphCreator();
		save = new Save();
	}

	public void generateParents() {
		bioOne = new Renderer(bioCreate.generateRandomBiomorph().getGenes());
	}

	public void createTempBiomorphs() {
		// for(int i = 0; i < temp.length; i++){
		// temp[i] = new
		// Renderer(bioCreate.generateRandomBiomorph().getGenes());
		// }
	}

	public Renderer[] getTempBiomorphs() {
		return temp;
	}

	public int getTempArraySize() {
		int counter = 0;
		for (int i = 0; i < temp.length; i++) {
			counter++;
		}
		return counter;
	}

	public void export(JPanel biomorph) {
		save.convertToImage(biomorph);
	}

	public void initGUI() {
		gui = new GUI(bioOne, temp, bioCreate);
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

	public static String readSysLog() throws IOException {
		String temp = "";
		BufferedReader br = new BufferedReader(new FileReader("SystemLog.txt"));
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
		control.generateParents();
		sysLog += "Done." + System.getProperty("line.separator");
		sysLog += "Generating biomorph..."
				+ System.getProperty("line.separator");
		control.createTempBiomorphs();
		sysLog += "Done." + System.getProperty("line.separator");
		control.initGUI();
		sysLog += "Initilising GUI..." + System.getProperty("line.separator");
		timerEnd = System.currentTimeMillis();
		sysLog += "Done." + System.getProperty("line.separator");
		elapsedTime = timerEnd - timerStart;

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

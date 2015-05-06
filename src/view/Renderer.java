package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Renderer is responsible for converting gene values into an lines to render an
 * image and returns a Jpanel.
 * 
 * @author Satpal Singh, Mathew Chambers, Assa Singh
 * @version 06/05/2015
 */
public class Renderer extends JPanel  {

	private Graphics2D g2d;
	private int[] genes;
	private int x;
	private int y;
	private double scaleX;
	private double scaleY;

	
	/**
	 * Constructor for the JPanel to render the Biomorph as an Image.
	 * @param genes Genes are an array of Integers of different lengths. 
	 * @param x X Position on Screen
	 * @param y Y Position on screen
	 * @param scaleX The scaleX value of the biomorph
	 * @param scaleY The ScaleY value of the biomorph
	 */
	public Renderer(int[] genes, int x, int y, double scaleX, double scaleY) {
		
		this.genes = genes;
		this.x = x;
		this.y = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		
	}

	/**
	 * Method to create lines based on the parameters passed on
	 * 
	 * @param g - Graphic g object used to draw
	 * @param a - a: used to draw lines
	 * @param b - b: used to draw lines
	 * @param c - c: used to draw lines
	 * @param d - d: used to draw lines
	 * @param color- colour of the lines
	 */
	public void displayBiomorph(Graphics g, int x1, int y1, int x2, int y2,
			Color color) {

		
		g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	/**
	 * @return Returns an array of genes
	 */
	public int[] getGenes(){
		return genes;
	}
	
	/**
	 * @param genes
	 * Sets the genes of the biomorph to the provided one.
	 */
	public void setGenes(int[] genes){
		
		this.genes = genes;
	}

	/**
	 * @param g Graphic g - Part of the Graphics2D library
	 * @param genes Genes int[]
	 * A method to draw lines based upon the gene values
	 */
	private void drawLines(Graphics g, int genes[]) {
		((Graphics2D)g).scale(scaleX, scaleY);
		//For positioning the biomorph
		g.translate(x, y);
		//Color for the lines
		Color color = new Color(genes[genes.length-3], genes[genes.length-2], genes[genes.length-1]);
		// starting lines
		displayBiomorph(g, genes[0], genes[1], genes[2], genes[3], color);
		displayBiomorph(g, genes[2], genes[1], genes[0], genes[3], color);

		displayBiomorph(g, genes[2], genes[3], genes[4], genes[5], color);
		int x1 = genes[4] - genes[2];
		int y1 = genes[5] - genes[3];
		int x2 = genes[0] - x1;
		int y2 = genes[3] + y1;
		displayBiomorph(g, genes[0], genes[3], x2, y2, color);

		for (int i = 4; i < genes.length-6; i += 2) {
			displayBiomorph(g, genes[i], genes[i + 1], genes[i + 2],
					genes[i + 3], color);
			x1 = genes[i + 2] - genes[i];
			y1 = genes[i + 3] - genes[i + 1];
			int px2 = x2;
			int py2 = y2;
			x2 = x2 - x1;
			y2 = y2 + y1;
			displayBiomorph(g, px2, py2, x2, y2, color);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		drawLines(g, genes);
		
	}

}

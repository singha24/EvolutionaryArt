package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Renderer is responsible for converting gene values into an lines to render an image and returns a Jpanel.
 * 
 * @author Satpal Singh, Mathew Chambers, Assa Singh
 * @version 16 Dec 2014
 */
public class Renderer extends JPanel {

	private Graphics2D g2d;
	private int[] genes;
	
	public Renderer(int[] genes) {
		this.genes = genes;
	}
	
	/**
	 * Method to create lines based on the parameters passed on
	 * 
	 * @param g - Graphic object used to draw
	 * @param a - used to draw lines
	 * @param b - used to draw lines
	 * @param c - used to draw lines
	 * @param d - used to draw lines
	 * @param color - used for color 
	 */
	public void displayBiomorph(Graphics g, int a, int b, int c, int d, Color color) {

		g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.drawLine(a, b, c, d);
		
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		Color color = new Color(genes[12]);
		//display lines by passing genes value
		displayBiomorph(g, genes[0],genes[1],genes[2],genes[3],color);
		displayBiomorph(g, genes[3],genes[2],genes[1],genes[0],color);
		displayBiomorph(g, genes[2],genes[3],genes[4],genes[5],color);
		displayBiomorph(g, genes[5],genes[4],genes[3],genes[2],color);
		displayBiomorph(g, genes[4],genes[5],genes[6],genes[7],color);
		displayBiomorph(g, genes[7],genes[6],genes[5],genes[4],color);
		displayBiomorph(g, genes[6],genes[7],genes[8],genes[9],color);
		displayBiomorph(g, genes[9],genes[8],genes[7],genes[6],color);
		displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
		displayBiomorph(g, genes[11],genes[10],genes[9],genes[8],color);

	}    

	
}

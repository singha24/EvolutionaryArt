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
	public void displayBiomorph(Graphics g, int x1, int y1, int x2, int y2, Color color) {

		g2d = (Graphics2D) g;
		//g2d.rotate(Math.toRadians(20));
		g2d.setColor(color);
		g2d.drawLine(x1, y1, x2, y2);
		
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		Color color = new Color(genes[12]);
		//display lines by passing genes value
//		displayBiomorph(g, 0,0,100,100,color);
//		System.out.println(genes[1]);
//		System.out.println(genes[2]);
//		System.out.println(genes[3]);
//		System.out.println(genes[0]);
//		displayBiomorph(g, 100,0,0,100,color);
		displayBiomorph(g, genes[0],genes[1],genes[2],genes[3],color);
		displayBiomorph(g, genes[2],genes[1],genes[0],genes[3],color);
		
		
		displayBiomorph(g, genes[2],genes[3],genes[4],genes[5],color);
		System.out.println(genes[4]);
		System.out.println(genes[5]);
		
		int x1 =genes[4]-genes[2] ;
		int y1 =  genes[5] - genes[3];
		int x2 = genes[0] -x1;
		int y2 = genes[3] +y1;
		displayBiomorph(g, genes[0],genes[3],x2,y2,color);
		displayBiomorph(g, genes[4],genes[5],genes[6],genes[7],color);
		x1 = genes[6] - genes[4];
		y1 =  genes[7] - genes[5];
		int x3 = x2 -x1;
		int y3 = y2 +y1;
		displayBiomorph(g, x2,y2,x3,y3,color);
		displayBiomorph(g, genes[6],genes[7],genes[8],genes[9],color);
		x1 = genes[8] - genes[6];
		y1 =  genes[9] - genes[7];
		int x4 = x3 -x1;
		int y4 = y3 +y1;
		displayBiomorph(g, x3,y3,x4,y4,color);
		displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
		x1 = genes[10] - genes[8];
		y1 =  genes[11] - genes[9];
		int x5 = x4 -x1;
		int y5 = y4 +y1;
		displayBiomorph(g, x4,y4,x5,y5,color);
		
		
//		displayBiomorph(g, genes[2],genes[3],genes[4],genes[5],color);
//		displayBiomorph(g, genes[5],genes[4],genes[3],genes[2],color);
//		displayBiomorph(g, genes[4],genes[5],genes[6],genes[7],color);
//		displayBiomorph(g, genes[7],genes[6],genes[5],genes[4],color);
//		displayBiomorph(g, genes[6],genes[7],genes[8],genes[9],color);
//		displayBiomorph(g, genes[9],genes[8],genes[7],genes[6],color);
//		displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
//		displayBiomorph(g, genes[11],genes[10],genes[9],genes[8],color);
		
//			xInc[0] =  0;	
//		    yInc[0] =  this.genes[0];
//		    
//		    xInc[1] =  this.genes[1];
//		    yInc[1] =  this.genes[2];
//		    
//		    xInc[2] =  this.genes[3];
//		    yInc[2] =  0;
//		    
//		    xInc[3] =  this.genes[4];
//		    yInc[3] = -this.genes[5];
//		    
//		    xInc[4] =  0;
//		    yInc[4] = -this.genes[6];
//		    
//		    xInc[5] = -this.genes[4];
//		    yInc[5] = -this.genes[5];
//		    
//		    xInc[6] = -this.genes[3];
//		    yInc[6] =  0;
//		    
//		    xInc[7] = -this.genes[1];
//		    yInc[7] =  this.genes[2];


	}    

	
}

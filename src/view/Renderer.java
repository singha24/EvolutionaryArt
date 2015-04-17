package view;
import java.awt.BasicStroke;
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
			//Can be used for the thickness of the lines
		 //g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(x1, y1, x2, y2);
		
		
	}
	
	
	
private void drawLines(Graphics g, int genes[]){
		// Color for the lines
		Color color = new Color(genes[12]);
		//starting lines
				displayBiomorph(g, genes[0],genes[1],genes[2],genes[3],color);
				displayBiomorph(g, genes[2],genes[1],genes[0],genes[3],color);
				
				displayBiomorph(g, genes[2],genes[3],genes[4],genes[5],color);
				int x1 =genes[4]-genes[2] ;
				int y1 =  genes[5] - genes[3];
				int x2 = genes[0] -x1;
				int y2 = genes[3] +y1;
				displayBiomorph(g, genes[0],genes[3],x2,y2,color);
				
				for(int i = 4; i < 8; i+=2){
					displayBiomorph(g, genes[i],genes[i+1],genes[i+2],genes[i+3],color);
					x1 =genes[i+2]-genes[i];
					y1 =  genes[i+3] - genes[i+1];
					int px2 = x2;
					int py2 = y2;
					x2 = x2 -x1;
					y2 = y2 +y1;
					displayBiomorph(g, px2,py2,x2,y2,color);
						
					}
//				
//				displayBiomorph(g, genes[2],genes[3],genes[4],genes[5],color);
//				int x1 =genes[4]-genes[2] ;
//				int y1 =  genes[5] - genes[3];
//				int x2 = genes[0] -x1;
//				int y2 = genes[3] +y1;
//				displayBiomorph(g, genes[0],genes[3],x2,y2,color);
//				
//				displayBiomorph(g, genes[4],genes[5],genes[6],genes[7],color);
//				x1 = genes[6] - genes[4];
//				y1 =  genes[7] - genes[5];
//				int x3 = x2 -x1;
//				int y3 = y2 +y1;
//				displayBiomorph(g, x2,y2,x3,y3,color);
//				
//				displayBiomorph(g, genes[6],genes[7],genes[8],genes[9],color);
//				x1 = genes[8] - genes[6];
//				y1 =  genes[9] - genes[7];
//				int x4 = x3 -x1;
//				int y4 = y3 +y1;
//				displayBiomorph(g, x3,y3,x4,y4,color);
//				
//				displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
//				x1 = genes[10] - genes[8];
//				y1 =  genes[11] - genes[9];
//				int x5 = x4 -x1;
//				int y5 = y4 +y1;
//				displayBiomorph(g, x4,y4,x5,y5,color);  
				
//				displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
//				x1 = genes[10] - genes[8];
//				y1 =  genes[11] - genes[9];
//				int x6 = x4 -x1;
//				int y6 = y4 +y1;
//				displayBiomorph(g, x5,y5,x6,y6,color);  
//				
//				displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
//				x1 = genes[10] - genes[8];
//				y1 =  genes[11] - genes[9];
//				int x7 = x4 -x1;
//				int y7 = y4 +y1;
//				displayBiomorph(g, x6,y6,x7,y7,color);  
//				
//				displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
//				x1 = genes[10] - genes[8];
//				y1 =  genes[11] - genes[9];
//				int x8 = x4 -x1;
//				int y8 = y4 +y1;
//				displayBiomorph(g, x7,y7,x8,y8,color);  
//				displayBiomorph(g, genes[8],genes[9],genes[10],genes[11],color);
//				x1 = genes[10] - genes[8];
//				y1 =  genes[11] - genes[9];
//				int x9 = x4 -x1;
//				int y9 = y4 +y1;
//				displayBiomorph(g, x8,y8,x9,y9,color);  
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		drawLines(g, genes);
	
	}    

	
}

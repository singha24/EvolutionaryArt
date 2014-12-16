package Biomorph;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;


public class Biomorph{
	
		//private Graphics2D g2d;
		private static int geneLimit = 4;
		
		private int[] genes;

		public Biomorph(){
			this.genes = createRandomGenes();
		}
		
		public int[] getGenes(){
			return genes;
		}
		
		private int[] createRandomGenes(){
			Random rand = new Random();
			int[] genes = new int[geneLimit];
			
			for(int i = 0; i <geneLimit; i++){
			genes[i] = rand.nextInt((500 - 150) + 1) + 150;
			}
			return genes;
		}
		
	}
	

	/*public void doDrawing(Graphics g, int a, int b, int c, int d, Color color) {

		g2d = (Graphics2D) g;
		g2d.setColor(color); 
		g2d.drawLine(a, b, c, d);
		
	}

	//@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		
		for(int l=0; l<5; l++){
			
			int h = generateInt();
			int i = generateInt();
			int j = generateInt();
			int k = generateInt();
			Color color = generateColour();
			doDrawing(g,h,i,j,k,color);
			doDrawing(g,k,j,i,h, color);
			
		}
	}    

	public int generateInt(){
		int ran = random.nextInt(350) + 150;
		return ran;
	}
	
	public Color generateColour(){
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		return new Color(r,g,b);
		
	}
	
	public void saveBiomorph(){
		//TODO
	}
	
	public int[] getGenes(){
		return genes; 
		//TODO
	}*/

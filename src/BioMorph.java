import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;


public class BioMorph extends JPanel {

	private Random rand = new Random();

	public void doDrawing(Graphics g, int a, int b, int c, int d) {

		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(generateColour());
		g2d.drawLine(a, b, c, d);
		g2d.setColor(generateColour());
	} 

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int l=0; l<5; l++){
			
			int h = generateInt();
			int i = generateInt();
			int j = generateInt();
			int k = generateInt();
			
			doDrawing(g,h,i,j,k);
			doDrawing(g,k,j,i,h);
		}
	}    

	public int generateInt(){
		int i = rand.nextInt((300 - 100) + 1) + 100;
		return i;
	}
	
	public Color generateColour(){
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color rand = new Color(r,g,b);
		return rand;
	}
}
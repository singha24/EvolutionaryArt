import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;


public class BioMorph extends JPanel {

	private Random rand = new Random();

	public void doDrawing(Graphics g, int a, int b, int c, int d) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawLine(a, b, c, d);

		a *= -1;
		b *= -1;
		c *= -1;
		d *= -1;

		g2d.drawLine(a, b, c, d);

	} 

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int l=0; l<10; l++){
			
			int h = generateInt();
			int i = generateInt();
			int j = generateInt();
			int k = generateInt();

			doDrawing(g,h,i,j,k);
		}
	}    

	public int generateInt(){
		int i = rand.nextInt((300 - 100) + 1) + 100;
		return i;
	}
}
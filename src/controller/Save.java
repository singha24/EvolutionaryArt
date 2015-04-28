package controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.BioWarehouse;

public class Save {
	
	
	public void convertToImage(JPanel biomorph){
		int w = biomorph.getWidth();
	    int h = biomorph.getHeight();
	    //Graphics2D g2d = Renderer.g2D();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    //g2d = bi.createGraphics();
	    //biomorph.paint(g2d);
	    exportToFile(bi);
	}
	
	public void exportToFile(BufferedImage bi){
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(bi, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

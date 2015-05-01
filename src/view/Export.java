package view;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Export {

	public static void saveAndPrint(JPanel biomorph, boolean print, GUI gui) throws AWTException {

		// working
		BufferedImage bi = new Robot().createScreenCapture(new Rectangle(
				biomorph.getLocationOnScreen().x, biomorph
						.getLocationOnScreen().y, biomorph.getWidth(), biomorph
						.getHeight()));

		/*
		 * File outputfile = new File("image.jpg"); try { ImageIO.write(image,
		 * "png", outputfile); } catch (IOException e) { // catch block
		 * e.printStackTrace(); }
		 */
		/*
		 * int w = biomorph.getWidth(); int h = biomorph.getHeight();
		 * BufferedImage bi = new BufferedImage(w, h,
		 * BufferedImage.TYPE_INT_ARGB);
		 */
		Graphics2D g = bi.createGraphics();
		gui.paint(g);

		if (!print) {
			
			save(gui, bi);
			
		} else {
			
			print(gui, g);
			
		}

	}
	public static void print(GUI gui, Graphics2D g){
		
		PrinterJob printJob = PrinterJob.getPrinterJob();

		printJob.setPrintable(gui);

		if (printJob.printDialog()) {
			
			try {
				
				printJob.print();
				
			} catch (Exception PrintException) {
				
				PrintException.printStackTrace();
				
			}
		}
		Graphics bm = (Graphics2D) g;
		gui.print(bm, printJob.defaultPage(), 1);
		
	}
	
	public static void save(GUI gui, BufferedImage bi){
			
		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Save Biomorph");
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showSaveDialog(null);

		if (f.getSelectedFile() != null) {
			
			File outputfile = new File(f.getSelectedFile().getPath());
			
			try {
				
				ImageIO.write(bi, "PNG", outputfile);
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			JOptionPane.showMessageDialog(gui,
					"Biomorph successfully saved to:\n"
							+ f.getSelectedFile().getPath());
		}
		
	}
}

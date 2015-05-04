package view;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadataNode;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IRational;

import controller.ChangeImageResolution;

public class Export {
	public static ChangeImageResolution metaData;

	public static String outputDestination;
	private static IRational FRAME_RATE = IRational.make(3, 1);
	private static final int SECONDS_TO_RUN_FOR = 15;

	public static void saveAndPrint(JPanel biomorph, boolean print, GUI gui)
			throws AWTException {

		// working
		BufferedImage bi = convertJPanelToBI(biomorph);

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

	public static BufferedImage convertJPanelToBI(JPanel biomorph)
			throws AWTException {
		BufferedImage bi = new Robot().createScreenCapture(new Rectangle(
				biomorph.getLocationOnScreen().x, biomorph
						.getLocationOnScreen().y, biomorph.getWidth(), biomorph
						.getHeight()));
		return bi;
	}

	public static void print(GUI gui, Graphics2D g) {

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

	public static IIOMetadataNode createNode() {
		IIOMetadataNode temp = new IIOMetadataNode();
		temp.setAttribute("Test", "Assa Singh");

		return temp;
	}

	public static void save(GUI gui, BufferedImage bi) {

		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Save Biomorph");
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showSaveDialog(null);

		if (f.getSelectedFile() != null) {

			File outputfile = new File(f.getSelectedFile().getPath());
			try {
				// metaData.writeImage(outputfile, bi, createNode());
				byte[] temp = ChangeImageResolution.writeCustomData(bi, "Assa",
						"Singh");
				InputStream in = new ByteArrayInputStream(temp);
				BufferedImage bImageFromConvert = ImageIO.read(in);

				ImageIO.write(bImageFromConvert, "PNG", outputfile);
				// ChangeImageResolution.writeMime(outputfile);

			} catch (IOException e) {

				e.printStackTrace();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(gui,
					"Biomorph successfully saved to:\n"
							+ f.getSelectedFile().getPath());
		}

	}

	public static void createMovie(GUI gui) {

		/*
		 * System.out.println("INSIDE METHOD");
		 * 
		 * JFileChooser f = new JFileChooser();
		 * f.setDialogTitle("Select Destination");
		 * f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 * f.showSaveDialog(null); File output = null;
		 * 
		 * if (f.getSelectedFile() != null) { output = new
		 * File(f.getSelectedFile().getPath()); }
		 * 
		 * outputDestination = output.toString();
		 */

		final ArrayList<JPanel> biomorphs = GUI.getBiomorphs();
		String outFile = "output.mp4";
		final IMediaWriter writer = ToolFactory.makeWriter(outFile);

		for (int i = 0; i < biomorphs.size(); i++) {
			try {
				BufferedImage bi = convertJPanelToBI(biomorphs.get(i));
				

				final Rectangle screenBounds = new Rectangle(bi.getWidth(),
						bi.getHeight());

			

				writer.addVideoStream(0, 0, FRAME_RATE, screenBounds.width,
						screenBounds.height);

				long startTime = System.nanoTime();

				BufferedImage bgrScreen = convertToType(bi,
						BufferedImage.TYPE_3BYTE_BGR);

				writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime,
						TimeUnit.NANOSECONDS);

				System.out.println("encoded image: " + i);

				Thread.sleep((long) (1000 / FRAME_RATE.getDouble()));
				
			} catch (Throwable e) {
				System.err.println("an error occurred: " + e.getMessage());
			}finally{
				writer.close();
			}

		}
	}

	public static BufferedImage convertToType(BufferedImage sourceImage,
			int targetType) {
		BufferedImage image;

		// if the source image is already the target type, return the source
		// image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

		// otherwise create a new image of the target type and draw the new
		// image

		else {
			image = new BufferedImage(sourceImage.getWidth(),
					sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}
}

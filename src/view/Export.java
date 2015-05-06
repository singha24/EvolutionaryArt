package view;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadataNode;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Assa Singh, Satpal Singh, Matthiew Chambers
 * @version 06 May 2015
 */
public class Export {

	private final static String png = ".png";
	public static ChangeImageResolution metaData;

	public static String outputDestination;

	/**
	 * @param biomorph
	 *            JPanel you want to save
	 * @param print
	 *            Boolean print
	 * @param gui
	 *            GUI class
	 * @throws AWTException
	 *             Method to either save or print according to the boolean.
	 */
	public static void saveAndPrint(JPanel biomorph, boolean print, GUI gui)
			throws AWTException {

		// working
		BufferedImage bi = convertJPanelToBI(biomorph);

		Graphics2D g = bi.createGraphics();
		gui.paint(g);

		if (!print) {

			save(gui, bi);

		} else {

			print(gui, g);

		}

	}

	/**
	 * @param biomorph
	 *            JPanel Biomorph to convert into BufferedImage
	 * @return Returns BufferedImage
	 * @throws AWTException
	 *             A method to conver JPanel to BufferedImage
	 */
	public static BufferedImage convertJPanelToBI(JPanel biomorph)
			throws AWTException {
		BufferedImage bi = new Robot().createScreenCapture(new Rectangle(
				biomorph.getLocationOnScreen().x, biomorph
						.getLocationOnScreen().y, biomorph.getWidth(), biomorph
						.getHeight()));
		return bi;
	}

	/**
	 * @param gui
	 *            GUI class
	 * @param g
	 *            Graphics2D
	 * @see PrinterJob Calls on PrinterJob to print BufferedImage
	 */
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

	/**
	 * @param gui
	 *            GUI class
	 * @param bi
	 *            BufferedImage
	 * @see JFileChooser Calls on JFileChooser select save location and convert
	 *      buffered image to PNG.
	 */
	public static void save(GUI gui, BufferedImage bi) {

		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Save Biomorph");
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showSaveDialog(null);

		if (f.getSelectedFile() != null) {

			File outputfile = new File(f.getSelectedFile().getPath() + png);
			try {
				byte[] temp = ChangeImageResolution.writeCustomData(bi, "Assa",
						"Singh");
				InputStream in = new ByteArrayInputStream(temp);
				BufferedImage bImageFromConvert = ImageIO.read(in);

				ImageIO.write(bImageFromConvert, "PNG", outputfile);

			} catch (IOException e) {

				e.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(gui,
					"Biomorph successfully saved to:\n"
							+ f.getSelectedFile().getPath() + png);
		}

	}
}

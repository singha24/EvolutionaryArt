package view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.BiomorphCreator;

/**
 * GUI creates Graphical User interface by extending JFrame
 * 
 * This class has Renderer which is used as JPanel to display image based on the
 * gene values.
 * 
 * @author Matthew Chambers, Assa Singh
 * @version 16 Dec 2014
 */
public class GUI extends JFrame implements Printable {

	// Renderer variable to hold render object
	private JPanel container = new JPanel();
	private Renderer biomorph;
	private Renderer biomorphTwo;
	private Renderer[] tempBiomorphs;
	private BiomorphCreator bioCreator;
	private JPanel panel = new JPanel(); // panel for upload, save or print
	private JPanel generate = new JPanel();
	
	private final double INCH = 72;

	private JButton evolve = new JButton("Evolve");
	private JButton upload = new JButton("Upload");
	private JButton save = new JButton("Save");
	private JButton print = new JButton("Print");

	// Gridlayout for temp biomorphs
	private JPanel temporaryBiomorphPanel = new JPanel();
	private GridLayout tempBiomorphGrid = new GridLayout(4, 2); // 4 rows, 2
																// columns

	/**
	 * Constructor to initialise the Renderer and create GUI (JFrame).
	 * 
	 * @param biomorph
	 */
	public GUI(Renderer biomorph, Renderer biomorphTwo, Renderer[] temp,
			BiomorphCreator bioCreator) {
		this.biomorph = biomorph;
		this.biomorphTwo = biomorphTwo;
		this.bioCreator = bioCreator;
		this.tempBiomorphs = temp;

		initUI();
	}

	public void evolve() {
		biomorph = new Renderer(bioCreator.extendRandomBiomorph().getGenes());
		update(biomorph);

	}

	public void export(JPanel biomorph, boolean print) throws AWTException {
		/*
		 * //working BufferedImage image = new Robot().createScreenCapture(new
		 * Rectangle(biomorph.getLocationOnScreen().x,
		 * biomorph.getLocationOnScreen().y, biomorph.getWidth(),
		 * biomorph.getHeight()));
		 * 
		 * File outputfile = new File("image.jpg"); try { ImageIO.write(image,
		 * "png", outputfile); } catch (IOException e) { // catch block
		 * e.printStackTrace(); }
		 */
		int w = biomorph.getWidth();
		int h = biomorph.getHeight();
		BufferedImage bi = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		paint(g);
		if (!print) {
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
				JOptionPane.showMessageDialog(this,
						"Biomorph successfully saved to:\n"
								+ f.getSelectedFile().getPath());
			}
		}else{
			PrinterJob printJob = PrinterJob.getPrinterJob();

			printJob.setPrintable(this);

			if (printJob.printDialog()) {
				try {
					printJob.print();
				} catch (Exception PrintException) {
					PrintException.printStackTrace();
				}
			}
			Graphics bm = (Graphics2D) g;
			print(bm, printJob.defaultPage(), 1);
		}

	}

	private void update(Renderer biomorph) {
		this.biomorph = biomorph;
		validate();
		repaint();
	}

	/**
	 * Create GUI (JFrame)
	 */
	private void initUI() {
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		biomorph.setLayout(new FlowLayout(FlowLayout.LEFT));
		// biomorphTwo =
		biomorphTwo.setLayout(new FlowLayout(FlowLayout.RIGHT));

		setTitle("Evolutionary Art");
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1280, 720));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		temporaryBiomorphPanel.setLayout(tempBiomorphGrid);
		for (int i = 0; i < tempBiomorphs.length; i++) {
			temporaryBiomorphPanel.add(tempBiomorphs[i]);
		}

		panel.add(upload, BorderLayout.NORTH);
		panel.add(save, BorderLayout.CENTER);
		panel.add(print, BorderLayout.NORTH);
		generate.add(evolve); // add the generate button to its own panel

		container.add(biomorph);

		container.add(biomorphTwo);

		add(panel, BorderLayout.WEST);
		add(generate, BorderLayout.PAGE_END);
		add(container, BorderLayout.CENTER);
		add(temporaryBiomorphPanel, BorderLayout.EAST);

		setResizable(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		print.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					export(biomorph, true);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}

			}
		});

		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent save) {
				try {
					export(biomorph, false);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		});

		evolve.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				evolve();
			}
		});

	}

	public int print(Graphics g, PageFormat pageFormat, int page) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		// --- Validate the page number, we only print the first page
		if (page == 0) { // --- Create a graphic2D object a set the default
							// parameters
			g2d = (Graphics2D) g;
			//g.setColor(Color.black);

			// --- Translate the origin to be (0,0)
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			
			paint(g2d);

			// --- Print the vertical lines
			/*for (i = 0; i < pageFormat.getWidth(); i += INCH / 2) {
				line.setLine(i, 0, i, pageFormat.getHeight());
				g2d.draw(line);
			}

			// --- Print the horizontal lines
			for (i = 0; i < pageFormat.getHeight(); i += INCH / 2) {
				line.setLine(0, i, pageFormat.getWidth(), i);
				g2d.draw(line);
			}*/

			
			return (PAGE_EXISTS);
		} else
			return (NO_SUCH_PAGE);
	}

	/*
	 * public byte[] writeCustomData(BufferedImage buffImg, String key, String
	 * value) throws Exception { ImageWriter writer =
	 * ImageIO.getImageWritersByFormatName("png").next();
	 * 
	 * ImageWriteParam writeParam = writer.getDefaultWriteParam();
	 * ImageTypeSpecifier typeSpecifier =
	 * ImageTypeSpecifier.createFromBufferedImageType
	 * (BufferedImage.TYPE_INT_ARGB);
	 * 
	 * //adding metadata IIOMetadata metadata =
	 * writer.getDefaultImageMetadata(typeSpecifier, writeParam);
	 * 
	 * IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
	 * textEntry.setAttribute("keyword", key); textEntry.setAttribute("value",
	 * value);
	 * 
	 * IIOMetadataNode text = new IIOMetadataNode("tEXt");
	 * text.appendChild(textEntry);
	 * 
	 * IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
	 * root.appendChild(text);
	 * 
	 * metadata.mergeTree("javax_imageio_png_1.0", root);
	 * 
	 * //writing the data ByteArrayOutputStream baos = new
	 * ByteArrayOutputStream(); ImageOutputStream stream =
	 * ImageIO.createImageOutputStream(baos); writer.setOutput(stream);
	 * writer.write(metadata, new IIOImage(buffImg, null, metadata),
	 * writeParam); stream.close();
	 * 
	 * return baos.toByteArray(); }
	 * 
	 * public String readCustomData(byte[] imageData, String key) throws
	 * IOException{ ImageReader imageReader =
	 * ImageIO.getImageReadersByFormatName("png").next();
	 * 
	 * imageReader.setInput(ImageIO.createImageInputStream(new
	 * ByteArrayInputStream(imageData)), true);
	 * 
	 * // read metadata of first image IIOMetadata metadata =
	 * imageReader.getImageMetadata(0);
	 * 
	 * //this cast helps getting the contents PNGMetadata pngmeta =
	 * (PNGMetadata) metadata; NodeList childNodes =
	 * pngmeta.getStandardTextNode().getChildNodes();
	 * 
	 * for (int i = 0; i < childNodes.getLength(); i++) { Node node =
	 * childNodes.item(i); String keyword =
	 * node.getAttributes().getNamedItem("keyword").getNodeValue(); String value
	 * = node.getAttributes().getNamedItem("value").getNodeValue();
	 * if(key.equals(keyword)){ return value; } } return null; }
	 */
}

package view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Biomorph;
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
	private JPanel topPanel = new JPanel();

	private JMenuBar menu = new JMenuBar();
	private JMenu file;
	private JMenu preferences;

	private JMenuItem save;
	//private JMenuItem complexity;
	private JMenuItem print;
	private JMenuItem upload;
	private JMenuItem exit;

	private SpinnerModel spinnerModel = new SpinnerNumberModel(10, // initial
																	// value
			0, // min
			100, // max
			1);// step
	private JSpinner spinner = new JSpinner(spinnerModel);

	private final double INCH = 72;

	private JButton evolve = new JButton("Evolve");

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

     // TODO: working on to avoid aliasing	
		bioCreator.extendRandomBiomorph(new Biomorph(biomorph.getGenes()));
		int[] newGenes = new int[biomorph.getGenes().length];
		for (int i = 0; i < biomorph.getGenes().length; i++){
		newGenes[i] =  biomorph.getGenes()[i];
		}


		// bioCreator.extendRandomBiomorph(new
		// Biomorph(biomorphTwo.getGenes()));
		 biomorphTwo.setGenes(newGenes);
		update();

		// biomorphDisplay.add(biomorph);
		
		//test for the array aliasing
//		for(int i = 0; i < newGenes.length;i++)
//			newGenes[i] = 20;
//		for(int print: newGenes)
//			System.out.println("TEST"+print);
		
	}

	public void export(JPanel biomorph, boolean print) throws AWTException {

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
		} else {
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

	private void update() {
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
		// biomorphDisplay.setSize(200, 200);
		setTitle("Evolutionary Art");
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1280, 720));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		temporaryBiomorphPanel.setLayout(tempBiomorphGrid);
		// for (int i = 0; i < tempBiomorphs.length; i++) {
		// temporaryBiomorphPanel.add(tempBiomorphs[i]);
		// }

		file = new JMenu("File");
		file.setMnemonic('F');
		menu.add(file);

		preferences = new JMenu("Preferences");
		preferences.setMnemonic('P');
		menu.add(preferences);

		//complexity = new JMenuItem("Complexity");
		//complexity.setMnemonic(KeyEvent.VK_C);
		//complexity.setActionCommand("Complexity");

		upload = new JMenuItem("Upload");
		upload.setMnemonic(KeyEvent.VK_U);
		upload.setActionCommand("Upload");

		save = new JMenuItem("Export");
		save.setActionCommand("Export");

		print = new JMenuItem("Print");
		print.setActionCommand("Print");

		exit = new JMenuItem("Exit");
		exit.setActionCommand("Exit");

		file.add(upload);
		file.add(save);
		file.add(print);
		file.add(exit);
		//preferences.add(complexity);
		
		container.add(biomorph);
		container.add(biomorphTwo);

		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println(spinner.getValue());
			}
		});

		generate.add(evolve); // add the generate button to its own panel
		generate.add(spinner);

		add(menu, BorderLayout.NORTH);
		add(generate, BorderLayout.PAGE_END);
		add(container, BorderLayout.CENTER);
		// add(temporaryBiomorphPanel, BorderLayout.EAST);

		setResizable(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		//complexity.addActionListener(new ActionListener() {

			//public void actionPerformed(ActionEvent e) {
				//complexitySlider();

			//}
		//});

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
				for (int i = 0; i <= getSpinnerValue(); i++) {
					evolve();
				}
			}
		});

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		// shorcuts
		save.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));

		print.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));

		//complexity.setAccelerator(KeyStroke.getKeyStroke(
				//java.awt.event.KeyEvent.VK_C, java.awt.Event.ALT_MASK));

	}

	public int getSpinnerValue() {
		return (Integer) spinner.getValue();
	}

	public int print(Graphics g, PageFormat pageFormat, int page) {

		Graphics2D g2d = (Graphics2D) g;

		// --- Validate the page number, we only print the first page
		if (page == 0) { // --- Create a graphic2D object a set the default
							// parameters
			g2d = (Graphics2D) g;
			// g.setColor(Color.black);

			// --- Translate the origin to be (0,0)
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());

			paint(g2d);

			// --- Print the vertical lines
			/*
			 * for (i = 0; i < pageFormat.getWidth(); i += INCH / 2) {
			 * line.setLine(i, 0, i, pageFormat.getHeight()); g2d.draw(line); }
			 * 
			 * // --- Print the horizontal lines for (i = 0; i <
			 * pageFormat.getHeight(); i += INCH / 2) { line.setLine(0, i,
			 * pageFormat.getWidth(), i); g2d.draw(line); }
			 */

			return (PAGE_EXISTS);
		} else
			return (NO_SUCH_PAGE);
	}

	private void showSpinnerDemo() {
		// headerLabel.setText("Control in action: JSpinner");

	}

	public void complexitySlider() {
		JOptionPane optionPane = new JOptionPane();
		JSlider slider = getSlider(optionPane);
		optionPane.setMessage(new Object[] { "Complexity: ", slider });
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = optionPane.createDialog(this, "Change complexity");
		dialog.setVisible(true);

		if(optionPane.getInputValue() == JOptionPane.UNINITIALIZED_VALUE){
			optionPane.setInputValue(50); //default position on slider.
		}else{

		if (optionPane.getInputValue() == JOptionPane.UNINITIALIZED_VALUE) {
			optionPane.setInputValue(new Integer(50)); // default position on
														// slider.
		} else {

			bioCreator.setGeneLimit((Integer) optionPane.getInputValue());
		}
		bioCreator.setGeneLimit((Integer) optionPane.getInputValue());
		System.out.println("NEW GENE : " + bioCreator.getGeneLimit());
	}
	}

	static JSlider getSlider(final JOptionPane optionPane) {
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JSlider theSlider = (JSlider) changeEvent.getSource();
				if (!theSlider.getValueIsAdjusting()) {
					optionPane.setInputValue(new Integer(theSlider.getValue()));
				}
			}
		};
		slider.addChangeListener(changeListener);
		return slider;
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

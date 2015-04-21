package view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
import controller.Save;

/**
 * GUI creates Graphical User interface by extending JFrame
 * 
 * This class has Renderer which is used as JPanel to display image based on the
 * gene values.
 * 
 * @author Matthew Chambers, Assa Singh
 * @version 16 Dec 2014
 */
public class GUI extends JFrame {

	// Renderer variable to hold render object
	private JPanel container = new JPanel();
	private Renderer biomorph;
	private Renderer biomorphTwo;
	private Renderer[] tempBiomorphs;
	private BiomorphCreator bioCreator;
	private JPanel panel = new JPanel(); // panel for upload, save or print
	private JPanel generate = new JPanel();

	private Save export;

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

	public void export(JPanel biomorph) throws AWTException {
		/*
		 * //working BufferedImage image = new Robot().createScreenCapture(new
		 * Rectangle(biomorph.getLocationOnScreen().x,
		 * biomorph.getLocationOnScreen().y, biomorph.getWidth(),
		 * biomorph.getHeight()));
		 * 
		 * File outputfile = new File("image.jpg"); try { ImageIO.write(image,
		 * "png", outputfile); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		int w = biomorph.getWidth();
		int h = biomorph.getHeight();
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		paint(g);

		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Save Biomorph");
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showSaveDialog(null);

		if (f.getSelectedFile() != null) {
			File outputfile = new File(f.getSelectedFile().getPath());
			try {
				ImageIO.write(bi, "PNG", outputfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Biomorph successfully saved to:\n" + f.getSelectedFile().getPath());
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

		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent save) {
				try {
					export(biomorph);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
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
}

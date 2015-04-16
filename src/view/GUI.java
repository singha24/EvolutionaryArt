package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.BiomorphCreator;
import controller.Controller;

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
	private Renderer biomorph; 
	private Renderer parent2;
	private BiomorphCreator bioCreator;
	private JPanel panel = new JPanel(); // panel for upload, save or print
	private JPanel generate = new JPanel();
	private JPanel parentTwoPanel = new JPanel();

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
	public GUI(Renderer biomorph, Renderer parent2, BiomorphCreator bioCreator) {
		this.biomorph = biomorph;
		this.parent2 = parent2;
		this.bioCreator = bioCreator;
		initUI();
	}
	public void evolve() {
		biomorph = new Renderer(bioCreator.extendRandomBiomorph().getGenes());
		update(biomorph);
	
}
	
	private void update(Renderer biomorph){
		this.biomorph = biomorph;
		validate();
		repaint();
	}

	/**
	 * Create GUI (JFrame)
	 */
	private void initUI() {
		// set title
		setTitle("Evolutionary Art");

		// set the exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set layout
		setLayout(new BorderLayout());

		// set the size of JFrame
		setSize(1280, 720);
		setLocationRelativeTo(null);
		// set visibility to true
		setVisible(true);
		// resize to falls
		setResizable(false);

		temporaryBiomorphPanel.setLayout(tempBiomorphGrid); 
		for (int i = 1; i <= 8; i++) {
			//temporaryBiomorphPanel.add(biomorph);
		}

		panel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		panel.add(upload, BorderLayout.NORTH);
		panel.add(save, BorderLayout.CENTER);
		panel.add(print, BorderLayout.NORTH);
		generate.add(evolve); // add the generate button to its own panel

		add(parent2, BorderLayout.CENTER);
		add(panel, BorderLayout.WEST);
		add(generate, BorderLayout.PAGE_END);
		add(biomorph, BorderLayout.CENTER);
		add(temporaryBiomorphPanel, BorderLayout.EAST);
		
		evolve.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				evolve();
			}
		});

	}
}

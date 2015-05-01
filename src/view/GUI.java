package view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Biomorph;
import model.BiomorphCreator;
import controller.Controller;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/**
 * GUI creates Graphical User interface by extending JFrame
 * 
 * This class has Renderer which is used as JPanel to display image based on the
 * gene values.
 * 
 * @author Matthew Chambers, Assa Singh
 * @version 16 Dec 2014
 */
public class GUI extends JFrame implements Printable, Runnable {

	private JFrame main_frame;
	// private JPanel container = new JPanel();
	private Renderer biomorph;
	private Renderer biomorphTwo;
	private Renderer[] tempBiomorphs;
	private BiomorphCreator bioCreator;
	// private JPanel panel = new JPanel(); // panel for upload, save or print
	private JPanel generate = new JPanel();
	// private JPanel topPanel = new JPanel();

	private JTextField sysLog = new JTextField(100);

	private JMenuBar menu;
	private JMenu file;
	private JMenu preferences;
	private JMenu system;

	// private JMenuItem complexity;
	private JMenuItem save;
	private JMenuItem print;
	private JMenuItem upload;
	private JMenuItem exit;
	private JMenuItem viewSysLog;
	private JMenuItem speech;

	private Thread speechThread;
	private boolean speaking; // used to start and stop thread
	
	private JFrame loading;

	private SpinnerModel spinnerModel = new SpinnerNumberModel(10, // initial
																	// value
			0, // min
			100, // max
			1);// step
	private JSpinner spinner = new JSpinner(spinnerModel);

	private final double INCH = 72;

	private JButton evolve = new JButton("Evolve");

	// Gridlayout for temp biomorphs
	// private JPanel temporaryBiomorphPanel = new JPanel();
	// private GridLayout tempBiomorphGrid = new GridLayout(4, 2); // 4 rows, 2
	// columns

	/**
	 * Constructor to initialise the Renderer and create GUI (JFrame).
	 * 
	 * @param biomorph
	 */
	public GUI(Renderer biomorph, Renderer[] temp, BiomorphCreator bioCreator) {
		this.biomorph = biomorph;

		biomorph.setLocation(0, 100);
		// this.biomorphTwo = biomorphTwo;

		this.bioCreator = bioCreator;
		this.tempBiomorphs = temp;

		initUI();
	}

	public void startSpeachRecognition() {
		this.loading = new JFrame();

		ImageIcon loading = new ImageIcon("ajax-loader.gif");
		this.loading.add(new JLabel("loading... ", loading, JLabel.CENTER));
		this.loading.setSize(400, 300);
		this.loading.setVisible(true);
		speaking = true;
		speechThread = new Thread(this);
		speechThread.start();
		
	}

	public void evolve() {

		// TODO: working on to avoid aliasing
		bioCreator.extendRandomBiomorph(new Biomorph(biomorph.getGenes()));
		// int[] newGenes = new int[biomorph.getGenes().length];
		// for (int i = 0; i < biomorph.getGenes().length; i++){
		// newGenes[i] = biomorph.getGenes()[i];
		// }

		int[] newGenes = new int[biomorph.getGenes().length];
		for (int i = 0; i < biomorph.getGenes().length; i++) {
			newGenes[i] = biomorph.getGenes()[i];
		}

		bioCreator.extendRandomBiomorph(new Biomorph(biomorph.getGenes()));
		// biomorphTwo.setGenes(newGenes);

		update();

	}

	private void update() {
		validate();
		repaint();
	}

	/**
	 * Create GUI (JFrame)
	 */
	private void initUI() {

		// Create and adjust settings for main program window
		main_frame = new JFrame();
		main_frame.setPreferredSize(new Dimension(1024, 720));
		main_frame.setResizable(false);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.getContentPane().setLayout(null);
		main_frame.setTitle("Evolutionary Art");

		menu = new JMenuBar();

		file = new JMenu("File");
		file.setMnemonic('F');

		system = new JMenu("System");
		system.setMnemonic('S');

		upload = new JMenuItem("Upload");
		upload.setMnemonic(KeyEvent.VK_U);
		upload.setActionCommand("Upload");

		save = new JMenuItem("Export");
		save.setActionCommand("Export");

		print = new JMenuItem("Print");
		print.setActionCommand("Print");

		exit = new JMenuItem("Exit");
		exit.setActionCommand("Exit");

		viewSysLog = new JMenuItem("System Log");
		viewSysLog.setActionCommand("System Log");

		speech = new JMenuItem("Speech Recognition");
		speech.setActionCommand("Speech Recognition");

		file.add(upload);
		file.add(save);
		file.add(print);
		file.add(exit);

		system.add(viewSysLog);
		system.add(speech);

		menu.add(file);

		menu.add(system);
		main_frame.setJMenuBar(menu);

		JPanel container = new JPanel();
		container.setBounds(261, 70, 520, 587);
		container.setOpaque(true);
		main_frame.add(container);

		// container.setBackground(Color.BLACK);

		JPanel main_biomorph = new JPanel();
		main_biomorph.setBorder(new BevelBorder(BevelBorder.RAISED, UIManager
				.getColor("Button.highlight"), null, new Color(0, 0, 0),
				new Color(142, 142, 142)));
		main_biomorph.setBounds(58, 58, 400, 300);
		main_biomorph.setPreferredSize(new Dimension(400, 300));
		main_biomorph.setOpaque(true);
		main_biomorph.setLayout(new BorderLayout());
		// main_biomorph.setBackground(Color.BLACK);
		// main_biomorph.setBackground(UIManager.getColor("Button.background"));
		container.add(main_biomorph);

		JPanel child_pane = new JPanel();
		child_pane.setBounds(6, 365, 500, 200);
		child_pane.setPreferredSize(new Dimension(500, 225));
		child_pane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		container.add(child_pane);

		JButton child_1 = new JButton();
		child_1.setPreferredSize(new Dimension(100, 100));
		child_1.setLayout(new BorderLayout());
		child_pane.add(child_1);

		JButton child_2 = new JButton("Child 2");
		child_2.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_2);

		JButton child_3 = new JButton("Child 2");
		child_3.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_3);

		JButton child_4 = new JButton("Child 4");
		child_4.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_4);

		JButton child_5 = new JButton("Child 5");
		child_5.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_5);

		JButton child_6 = new JButton("Child 6");
		child_6.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_6);

		JButton child_7 = new JButton("Child 7");
		child_7.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_7);

		JButton child_8 = new JButton("Child 8");
		child_8.setPreferredSize(new Dimension(100, 100));
		child_pane.add(child_8);

		JPanel save_panel = new JPanel();
		save_panel.setBounds(33, 70, 216, 587);
		main_frame.getContentPane().add(save_panel);

		JPanel save_1 = new JPanel();
		save_1.setPreferredSize(new Dimension(100, 100));
		save_1.setOpaque(true);
		save_1.setBackground(Color.BLACK);
		save_panel.add(save_1);

		JPanel save_2 = new JPanel();
		save_2.setPreferredSize(new Dimension(100, 100));
		save_2.setOpaque(true);
		save_2.setBackground(Color.BLACK);
		save_panel.add(save_2);

		JPanel save_3 = new JPanel();
		save_3.setPreferredSize(new Dimension(100, 100));
		save_3.setOpaque(true);
		save_3.setBackground(Color.BLACK);
		save_panel.add(save_3);

		JPanel save_4 = new JPanel();
		save_4.setPreferredSize(new Dimension(100, 100));
		save_4.setOpaque(true);
		save_4.setBackground(Color.BLACK);
		save_panel.add(save_4);

		JPanel save_5 = new JPanel();
		save_5.setPreferredSize(new Dimension(100, 100));
		save_5.setOpaque(true);
		save_5.setBackground(Color.BLACK);
		save_panel.add(save_5);

		JPanel save_6 = new JPanel();
		save_6.setPreferredSize(new Dimension(100, 100));
		save_6.setOpaque(true);
		save_6.setBackground(Color.BLACK);
		save_panel.add(save_6);

		JPanel save_7 = new JPanel();
		save_7.setPreferredSize(new Dimension(100, 100));
		save_7.setOpaque(true);
		save_7.setBackground(Color.BLACK);
		save_panel.add(save_7);

		JPanel save_8 = new JPanel();
		save_8.setPreferredSize(new Dimension(100, 100));
		save_8.setOpaque(true);
		save_8.setBackground(Color.BLACK);
		save_panel.add(save_8);

		JPanel hof_panel = new JPanel();
		hof_panel.setBounds(793, 88, 216, 224);
		main_frame.getContentPane().add(hof_panel);

		JPanel hof_1 = new JPanel();
		hof_1.setPreferredSize(new Dimension(100, 100));
		hof_1.setOpaque(true);
		hof_1.setBackground(Color.BLACK);
		hof_panel.add(hof_1);

		JPanel hof_2 = new JPanel();
		hof_2.setPreferredSize(new Dimension(100, 100));
		hof_2.setOpaque(true);
		hof_2.setBackground(Color.BLACK);
		hof_panel.add(hof_2);

		JPanel hof_3 = new JPanel();
		hof_3.setPreferredSize(new Dimension(100, 100));
		hof_3.setOpaque(true);
		hof_3.setBackground(Color.BLACK);
		hof_panel.add(hof_3);

		JPanel hof_4 = new JPanel();
		hof_4.setPreferredSize(new Dimension(100, 100));
		hof_4.setOpaque(true);
		hof_4.setBackground(Color.BLACK);
		hof_panel.add(hof_4);

		// container.setLayout(new FlowLayout());
		// frame = new JFrame();
		// Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// container.setBounds(0, 0, 1024, 720);

		biomorph.setLayout(new FlowLayout(FlowLayout.LEFT));
		// biomorphTwo

		// temporaryBiomorphPanel.setLayout(tempBiomorphGrid);
		// for (int i = 0; i < tempBiomorphs.length; i++) {
		// temporaryBiomorphPanel.add(tempBiomorphs[i]);
		// }

		// preferences.add(complexity);
		main_biomorph.add(biomorph);
		// child_1.add(biomorphTwo, BorderLayout.CENTER);
		// child_2.add(biomorphTwo);

		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println(spinner.getValue());
			}
		});

		generate.add(evolve); // add the generate button to its own panel
		generate.add(spinner);

		// main_frame.add(menu, BorderLayout.NORTH);
		// main_frame.add(generate, BorderLayout.PAGE_END);
		// main_frame.add(container, BorderLayout.CENTER);
		// add(temporaryBiomorphPanel, BorderLayout.EAST);

		main_frame.pack();
		main_frame.setLocationRelativeTo(null);
		main_frame.setVisible(true);

		child_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve();
			}

		});

		speech.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				startSpeachRecognition();

			}
		});

		viewSysLog.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, Controller.readSysLog());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		print.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Export.saveAndPrint(biomorph, true, GUI.this);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}

			}
		});

		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent save) {
				try {
					Export.saveAndPrint(biomorph, false, GUI.this);
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

		// exit.addActionListener(new ActionListener() {

		// public void actionPerformed(ActionEvent e) {
		// System.exit(0);

		// }
		// });

		// shorcuts
		save.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));

		print.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));

		speech.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_P, java.awt.Event.ALT_MASK));

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

	public void run() {
		if (speaking) {
			ConfigurationManager cm;

			cm = new ConfigurationManager(
					GUI.class.getResource("hello.config.xml"));

			Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
			recognizer.allocate();

			// start the microphone or exit if the programm if this is not
			// possible
			Microphone microphone = (Microphone) cm.lookup("microphone");
			try {
				if (!microphone.startRecording()) {
					System.out.println("Cannot start microphone.");
					recognizer.deallocate();
					System.exit(1);
				}
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(null,
								"Please connect a microphone to be able to use this feature.");
			}

			System.out
					.println("Say: (Evolve | Stop | Save | Print) ( One | Two | Three | Four | Five | Six )");

			// loop the recognition until the programm exits.
			this.loading.dispose();
			while (speaking) {

				Result result = recognizer.recognize();

				if (result != null) {
					String resultText = result.getBestFinalResultNoFiller();

					if (resultText.toLowerCase().contains("evolve")) {
						evolve();
						System.out.println("You said: " + resultText + '\n');
					}

					if (resultText.toLowerCase().contains("stop")) {
						speaking = false;
						microphone.clear();
						recognizer.resetMonitors();
						recognizer.deallocate();
						recognizer = null;
						cm = null;
						JOptionPane.showMessageDialog(null,
								"Speech Recognition Turned Off.");
					}
				}
			}
		}

	}
}

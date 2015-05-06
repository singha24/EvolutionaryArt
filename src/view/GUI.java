package view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BioWarehouse;
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
	private Renderer biomorph;
	private Renderer[] children;
	private Renderer[] tempSave;
	
	private static ArrayList<JPanel> toMovie = new ArrayList<JPanel>();
	
	private BiomorphCreator bioCreator;
	private BioWarehouse warehouse;
	private JPanel generate = new JPanel();

	private static ArrayList<Renderer> back = new ArrayList<Renderer>();

	private JTextField sysLog = new JTextField(100);

	private JMenuBar menu;
	private JMenu file;
	private JMenu system;
	private JMenu help;

	private JPanel main_biomorph = new JPanel();
	private JButton child_1 = new JButton();
	private JButton child_2 = new JButton();
	private JButton child_3 = new JButton();
	private JButton child_4 = new JButton();
	private JButton child_5 = new JButton();
	private JButton child_6 = new JButton();
	private JButton child_7 = new JButton();
	private JButton child_8 = new JButton();
	

	// private JMenuItem complexity;§
	private JMenuItem save;
	private JMenuItem print;
	private JMenuItem upload;
	private JMenuItem exit;
	private JMenuItem viewSysLog;
	private JMenuItem speech;
	private JMenuItem instructions;
	private JMenuItem videoRecording;
	
	private JMenuItem saveToTemp;
	private JMenuItem saveToHOF1;
	private JMenuItem saveToHOF2;
	private JMenuItem saveToHOF3;
	private JMenuItem saveToHOF4;
	private JMenuItem clearFromHOF1;
	private JMenuItem clearFromHOF2;
	private JMenuItem clearFromHOF3;
	private JMenuItem clearFromHOF4;
	private JMenuItem previous;

	private Thread speechThread;
	private boolean speaking; // used to start and stop thread

	private volatile static JFrame loadingFrame;
	private JPopupMenu popupMenu1 = new JPopupMenu();
	private JPopupMenu popupMenu2 = new JPopupMenu();
	private JPopupMenu popupMenu3 = new JPopupMenu();
	private JPopupMenu popupMenu4 = new JPopupMenu();
	private JButton hof_1 = new JButton();
	private JButton hof_2 = new JButton();
	private JButton hof_3 = new JButton();
	private JButton hof_4 = new JButton();
	private JPopupMenu previousPopup = new JPopupMenu();

	private ArrayList<Renderer> tempStorage = new ArrayList<Renderer>();
	private ArrayList<Renderer> HOF = new ArrayList<Renderer>();
	private HallOfFame hallOfFame;
	private Biomorph[] hall_of_fame;
	private Renderer hall_of_fame_1;
	private Renderer hall_of_fame_2;
	private Renderer hall_of_fame_3;
	private Renderer hall_of_fame_4;
	
	private JButton save_1;
	private JButton save_2;
	private JButton save_3;
	private JButton save_4;
	private JButton save_5;
	private JButton save_6;
	private JButton save_7;
	private JButton save_8;

	private SpinnerModel spinnerModel = new SpinnerNumberModel(10, // initial
																	// value
			0, // min
			100, // max
			1);// step
	private JSpinner spinner = new JSpinner(spinnerModel);

	private JButton evolve = new JButton("Evolve");
	private static volatile ImageIcon loading = new ImageIcon("ajax-loader.gif");

	/**
	 * Constructor to initialise the Renderer and create GUI (JFrame).
	 * 
	 * @param biomorph
	 */
	public GUI(Renderer biomorph, Renderer[] children, BiomorphCreator bioCreator, BioWarehouse warehouse) {

		this.biomorph = biomorph;
		this.warehouse = warehouse;
		this.bioCreator = bioCreator;
		this.children = children;
		
		hallOfFame = new HallOfFame();
		biomorph.setLocation(0, 100);
		
		getHallOfFames();
		initialiseSave();
		initUI();
		
		
	}

	private void getHallOfFames(){
		
		hall_of_fame = new Biomorph[4];

		// for(int i =0; i < hall_of_fame.length; i++){
		hall_of_fame[0] = hallOfFame.readHallOfFame("first");

		hall_of_fame[1] = hallOfFame.readHallOfFame("second");
		hall_of_fame[2] = hallOfFame.readHallOfFame("third");
		hall_of_fame[3] = hallOfFame.readHallOfFame("fourth");

	}

	public static void loading() {
		loadingFrame = new JFrame();

		loadingFrame.add(new JLabel("loading... ", loading, JLabel.CENTER));

		loadingFrame.setPreferredSize((new Dimension(100, 100)));
		loadingFrame.setVisible(true);

		loadingFrame.removeNotify();
		loadingFrame.setUndecorated(true);
		loadingFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		loadingFrame.pack();
		loadingFrame.setLocationRelativeTo(null);
	}

	public void startSpeachRecognition() {
		loading();
		speaking = true;
		speechThread = new Thread(this);
		speechThread.start();

	}

	// public void evolve() {
	//
	// // TODO: working on to avoid aliasing
	// bioCreator.extendRandomBiomorph(new Biomorph(biomorph.getGenes()));
	// // int[] newGenes = new int[biomorph.getGenes().length];
	// // for (int i = 0; i < biomorph.getGenes().length; i++){
	// // newGenes[i] = biomorph.getGenes()[i];
	// // }
	//
	// int[] newGenes = new int[biomorph.getGenes().length];
	// for (int i = 0; i < biomorph.getGenes().length; i++) {
	// newGenes[i] = biomorph.getGenes()[i];
	// }
	//
	// bioCreator.extendRandomBiomorph(new Biomorph(biomorph.getGenes()));
	// // biomorphTwo.setGenes(newGenes);
	//
	// update();
	//
	// }
	//
	// private void update() {
	// validate();
	// repaint();
	// }

	public void evolve(int childIndex) {

		biomorph.setGenes(children[childIndex].getGenes());
		// int[] newGenes = new int[biomorph.getGenes().length];
		for (int i = 0; i < children.length; i++) {
			int[] newGenes = new int[biomorph.getGenes().length];
			for (int j = 0; j < biomorph.getGenes().length; j++) {
				// System.out.println("Children" +biomorph.getGenes()[j]);
				newGenes[j] = biomorph.getGenes()[j];
				// System.out.println("Children Genes" +newGenes[j]);
			}
			children[i].setGenes(newGenes);
		}
		// biomorphTwo.setGenes(newGenes);
		for (int i = 0; i < children.length; i++) {
			bioCreator
					.extendRandomBiomorph(new Biomorph(children[i].getGenes()));
		}

		for (int i = 0; i < biomorph.getGenes().length; i++) {

			System.out.println("test: " + biomorph.getGenes()[i]);

		}
		back.add(biomorph);

		update();

	}

	private void update() {
		main_biomorph.repaint();
		child_1.repaint();
		child_2.repaint();
		child_3.repaint();
		child_4.repaint();
		child_5.repaint();
		child_6.repaint();
		child_7.repaint();
		child_8.repaint();
		save_1.repaint();
		save_2.repaint();
		save_3.repaint();
		save_4.repaint();
		save_5.repaint();
		save_6.repaint();
		save_7.repaint();
		save_8.repaint();

		// toMovie.add(main_biomorph);
	}
	
	
	
	public static ArrayList<JPanel> getBiomorphs(){
		return toMovie;
	}

	/**
	 * Create GUI (JFrame)
	 */
	private void initUI() {

		// Create and adjust settings for main program window
		main_frame = new JFrame();
		main_frame.setPreferredSize(new Dimension(1050, 720));
		main_frame.setResizable(false);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.getContentPane().setLayout(null);
		main_frame.setTitle("Evolutionary Art");

		menu = new JMenuBar();

		file = new JMenu("File");
		file.setMnemonic('F');

		system = new JMenu("System");
		system.setMnemonic('S');

		help = new JMenu("Help");
		help.setMnemonic('H');

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

		instructions = new JMenuItem("Documentation");
		instructions.setActionCommand("Documentation");

		videoRecording = new JMenuItem("Start Recording");
		videoRecording.setActionCommand("R");

		speech = new JMenuItem("Speech Recognition");
		speech.setActionCommand("Speech Recognition");
		
		saveToTemp = new JMenuItem("Save to Temp Storage");
		saveToHOF1 = new JMenuItem("Save to Hall of Fame");
		saveToHOF2 = new JMenuItem("Save to Hall of Fame");
		saveToHOF3 = new JMenuItem("Save to Hall of Fame");
		saveToHOF4 = new JMenuItem("Save to Hall of Fame");
		
		clearFromHOF1 = new JMenuItem("Remove from Hall Of Fame");
		clearFromHOF2 = new JMenuItem("Remove from Hall Of Fame");
		clearFromHOF3 = new JMenuItem("Remove from Hall Of Fame");
		clearFromHOF4 = new JMenuItem("Remove from Hall Of Fame");

		file.add(upload);
		file.add(save);
		file.add(print);
		file.add(exit);

		system.add(viewSysLog);
		system.add(speech);
		//system.add(videoRecording);

		help.add(instructions);

		menu.add(file);
		menu.add(help);

		menu.add(system);
		main_frame.setJMenuBar(menu);
		
		popupMenu1.add(saveToHOF1);
		popupMenu1.add(clearFromHOF1);
		popupMenu2.add(saveToHOF2);
		popupMenu2.add(clearFromHOF2);
		popupMenu3.add(saveToHOF3);
		popupMenu3.add(clearFromHOF3);
		popupMenu4.add(saveToHOF4);
		popupMenu4.add(clearFromHOF4);
		
		

		JPanel container = new JPanel();
		container.setBounds(261, 70, 520, 587);
		container.setOpaque(true);
		main_frame.add(container);

		// container.setBackground(Color.BLACK);

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
		
		child_1.setBorderPainted(false);
		child_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_1.setFocusPainted(false);
		child_1.setContentAreaFilled(false);
		child_1.setRolloverEnabled(false);
		child_1.setPreferredSize(new Dimension(115, 115));
		child_1.setLayout(new BorderLayout());
		child_pane.add(child_1);

		child_2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		// child_2.setBorderPainted(false);
		// child_2.setFocusPainted(false);
		child_2.setContentAreaFilled(false);
		child_2.setRolloverEnabled(false);
		child_2.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_2);

		child_3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_3.setBorderPainted(false);
		child_3.setFocusPainted(false);
		child_3.setContentAreaFilled(false);
		child_3.setRolloverEnabled(false);
		child_3.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_3);

		child_4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_4.setBorderPainted(false);
		child_4.setFocusPainted(false);
		child_4.setContentAreaFilled(false);
		child_4.setRolloverEnabled(false);
		child_4.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_4);

		child_5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_5.setBorderPainted(false);
		child_5.setFocusPainted(false);
		child_5.setContentAreaFilled(false);
		child_5.setRolloverEnabled(false);
		child_5.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_5);

		child_6.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_6.setBorderPainted(false);
		child_6.setFocusPainted(false);
		child_6.setContentAreaFilled(false);
		child_6.setRolloverEnabled(false);
		child_6.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_6);

		child_7.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_7.setBorderPainted(false);
		child_7.setFocusPainted(false);
		child_7.setContentAreaFilled(false);
		child_7.setRolloverEnabled(false);
		child_7.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_7);

		child_8.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		child_8.setBorderPainted(false);
		child_8.setFocusPainted(false);
		child_8.setContentAreaFilled(false);
		child_8.setRolloverEnabled(false);
		child_8.setPreferredSize(new Dimension(115, 115));
		child_pane.add(child_8);

		JPanel save_panel = new JPanel();
		save_panel.setBounds(5, 80, 270, 600);
		main_frame.getContentPane().add(save_panel);
		
		save_1 = new JButton();
		save_1.setPreferredSize(new Dimension(115, 115));
		save_1.setFocusPainted(false);
		save_1.setContentAreaFilled(false);
		save_1.setRolloverEnabled(false);
		save_1.setEnabled(false);
		save_1.add(this.tempSave[0]);
		save_panel.add(save_1);

		save_2 = new JButton();
		save_2.setPreferredSize(new Dimension(115, 115));
		save_2.setFocusPainted(false);
		save_2.setContentAreaFilled(false);
		save_2.setRolloverEnabled(false);
		save_2.setEnabled(false);
		save_2.add(this.tempSave[1]);
		save_panel.add(save_2);

		save_3 = new JButton();
		save_3.setPreferredSize(new Dimension(115, 115));
		save_3.setFocusPainted(false);
		save_3.setContentAreaFilled(false);
		save_3.setRolloverEnabled(false);
		save_3.setEnabled(false);
		save_3.add(this.tempSave[2]);
		save_panel.add(save_3);

		save_4 = new JButton();
		save_4.setPreferredSize(new Dimension(115, 115));
		save_4.setFocusPainted(false);
		save_4.setContentAreaFilled(false);
		save_4.setRolloverEnabled(false);
		save_4.setEnabled(false);
		save_4.add(this.tempSave[3]);
		save_panel.add(save_4);

		save_5 = new JButton();
		save_5.setPreferredSize(new Dimension(115, 115));
		save_5.setFocusPainted(false);
		save_5.setContentAreaFilled(false);
		save_5.setRolloverEnabled(false);
		save_5.setEnabled(false);
		save_5.add(this.tempSave[4]);
		save_panel.add(save_5);

		save_6 = new JButton();
		save_6.setPreferredSize(new Dimension(115, 115));
		save_6.setFocusPainted(false);
		save_6.setContentAreaFilled(false);
		save_6.setRolloverEnabled(false);
		save_6.setEnabled(false);
		save_6.add(this.tempSave[5]);
		save_panel.add(save_6);

		save_7 = new JButton();
		save_7.setPreferredSize(new Dimension(115, 115));
		save_7.setFocusPainted(false);
		save_7.setContentAreaFilled(false);
		save_7.setRolloverEnabled(false);
		save_7.setEnabled(false);
		save_7.add(this.tempSave[6]);
		save_panel.add(save_7);

		save_8 = new JButton();
		save_8.setPreferredSize(new Dimension(115, 115));
		save_8.setFocusPainted(false);
		save_8.setContentAreaFilled(false);
		save_8.setRolloverEnabled(false);
		save_8.setEnabled(false);
		save_8.add(this.tempSave[7]);
		save_panel.add(save_8);
		
		final JButton tempSaveBtn = new JButton("save");
		save_panel.add(tempSaveBtn);

		JPanel hof_panel = new JPanel();
		//hof_panel.setBounds(793, 88, 216, 250);
		hof_panel.setBounds(765, 70, 270, 300);
		main_frame.getContentPane().add(hof_panel);
		
		// TODO: 
		
		if(hall_of_fame[0] != null){
			hall_of_fame_1 = new Renderer(hall_of_fame[0].getGenes(), 5,5,1,1);
			hof_1.add(hall_of_fame_1);
			}else{
				int[] emptyGenes = {0,0,0,0,0,0,0,0,0};
				hall_of_fame_1 = new Renderer(emptyGenes, 5,5,1,1);
				hof_1.add(hall_of_fame_1);
			}
		//hof_1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		//hof_1.setBorderPainted(false);
		hof_1.setFocusPainted(false);
		hof_1.setContentAreaFilled(false);
		hof_1.setRolloverEnabled(false);
		hof_1.setEnabled(false);
		hof_1.setPreferredSize(new Dimension(115, 115));
		hof_1.setOpaque(true);
		//hof_1.setBackground(Color.BLACK);
		hof_panel.add(hof_1);
		hof_1.setComponentPopupMenu(popupMenu1);

		if (hall_of_fame[1] != null) {
			hall_of_fame_2 = new Renderer(hall_of_fame[1].getGenes(), 5, 5, 1,
					1);
			hof_2.add(hall_of_fame_2);
		} else {
			int[] emptyGenes = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			hall_of_fame_2 = new Renderer(emptyGenes, 5, 5, 1, 1);
			hof_2.add(hall_of_fame_2);
		}
		// hof_2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		// hof_2.setBorderPainted(false);
		hof_2.setFocusPainted(false);
		hof_2.setContentAreaFilled(false);
		hof_2.setRolloverEnabled(false);
		hof_2.setEnabled(false);
		hof_2.setPreferredSize(new Dimension(115, 115));
		hof_2.setOpaque(true);
		hof_panel.add(hof_2);
		hof_2.setComponentPopupMenu(popupMenu2);
		
		
		if(hall_of_fame[2] != null){
			hall_of_fame_3 = new Renderer(hall_of_fame[2].getGenes(), 5,5,1,1);
			hof_3.add(hall_of_fame_3);
			}else{
				int[] emptyGenes = {0,0,0,0,0,0,0,0,0};
				hall_of_fame_3 = new Renderer(emptyGenes, 5,5,1,1);
				hof_3.add(hall_of_fame_3);
			}
		//hof_3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		//hof_3.setBorderPainted(false);
		hof_3.setFocusPainted(false);
		hof_3.setContentAreaFilled(false);
		hof_3.setRolloverEnabled(false);
		hof_3.setEnabled(false);
		hof_3.setPreferredSize(new Dimension(115, 115));
		hof_3.setOpaque(true);
		hof_panel.add(hof_3);
		hof_3.setComponentPopupMenu(popupMenu3);

		
		if(hall_of_fame[3] != null){
			hall_of_fame_4 = new Renderer(hall_of_fame[3].getGenes(), 5,5,1,1);
			hof_4.add(hall_of_fame_4);
			}else{
				int[] emptyGenes = {0,0,0,0,0,0,0,0,0};
				hall_of_fame_4 = new Renderer(emptyGenes, 5,5,1,1);
				hof_4.add(hall_of_fame_4);
			}
		//hof_4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		//hof_4.setBorderPainted(false);
		hof_4.setFocusPainted(false);
		hof_4.setContentAreaFilled(false);
		hof_4.setRolloverEnabled(false);
		hof_4.setEnabled(false);
		hof_4.setPreferredSize(new Dimension(115, 115));
		hof_4.setOpaque(true);
		hof_panel.add(hof_4);
		hof_4.setComponentPopupMenu(popupMenu4);

		biomorph.setLayout(new FlowLayout(FlowLayout.LEFT));
		// biomorphTwo

		// preferences.add(complexity);
		main_biomorph.add(biomorph);
		child_1.add(children[0]);
		child_2.add(children[1]);
		child_3.add(children[2]);
		child_4.add(children[3]);
		child_5.add(children[4]);
		child_6.add(children[5]);
		child_7.add(children[6]);
		child_8.add(children[7]);
		// child_1.add(biomorphTwo, BorderLayout.CENTER);
		// child_2.add(biomorphTwo);

		instructions.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null,
							Controller.readSysFile("instructions.txt"));
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println(spinner.getValue());
			}
		});

		generate.add(evolve); // add the generate button to its own panel
		generate.add(spinner);

		main_frame.pack();
		main_frame.setLocationRelativeTo(null);
		main_frame.setVisible(true);

		child_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(0);
			}

		});
		child_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(1);
				
			}

		}); 
		child_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(2);
			}

		});
		child_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(3);
			}

		});
		child_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(4);
			}

		});
		child_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(5);
			}

		});
		child_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(6);
			}

		});
		child_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evolve(7);
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
					JOptionPane.showMessageDialog(null,
							Controller.readSysFile("SystemLog.txt"));
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

		saveToTemp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tempStorage.add(biomorph);
				for(int i=0; i<tempStorage.get(0).getGenes().length; i++){
					System.out.println(tempStorage.get(0).getGenes()[i]);
				}
			}
		});
		
		
		
		saveToHOF1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				hallOfFame.saveHallOfFame(new Biomorph(biomorph.getGenes()),
						"first");
				hall_of_fame[0] = hallOfFame.readHallOfFame("first");
				hall_of_fame_1.setGenes(hall_of_fame[0].getGenes());
				hof_1.repaint();
				
			}
		});
		
		clearFromHOF1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				hallOfFame.removeHallOfFame("first");
				hall_of_fame[0] = hallOfFame.readHallOfFame("first");
				hall_of_fame_1.setGenes(hall_of_fame[0].getGenes());
				hof_1.repaint();
				
			}
		});
		
		clearFromHOF2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				hallOfFame.removeHallOfFame("second");
				hall_of_fame[1] = hallOfFame.readHallOfFame("second");
				hall_of_fame_2.setGenes(hall_of_fame[1].getGenes());
				hof_2.repaint();
				
			}
		});
		
		clearFromHOF3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				hallOfFame.removeHallOfFame("third");
				hall_of_fame[2] = hallOfFame.readHallOfFame("third");
				hall_of_fame_3.setGenes(hall_of_fame[2].getGenes());
				hof_3.repaint();
				
			}
		});
		
		clearFromHOF4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				hallOfFame.removeHallOfFame("fourth");
				hall_of_fame[3] = hallOfFame.readHallOfFame("fourth");
				hall_of_fame_4.setGenes(hall_of_fame[3].getGenes());
				hof_4.repaint();
				
			}
		});
		saveToHOF2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// HOF.add(biomorph);
				hallOfFame.saveHallOfFame(new Biomorph(biomorph.getGenes()),
						"second");
				hall_of_fame[1] = hallOfFame.readHallOfFame("second");
				hall_of_fame_2.setGenes(hall_of_fame[1].getGenes());
				hof_2.repaint();
			}
		});
		
		saveToHOF3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				HOF.add(biomorph);
				// HOF.add(biomorph);
				hallOfFame.saveHallOfFame(new Biomorph(biomorph.getGenes()),
						"third");
				hall_of_fame[2] = hallOfFame.readHallOfFame("third");
				hall_of_fame_3.setGenes(hall_of_fame[2].getGenes());
				hof_3.repaint();
			}
		});

		saveToHOF4.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				// HOF.add(biomorph);
				hallOfFame.saveHallOfFame(new Biomorph(biomorph.getGenes()),
						"fourth");
				hall_of_fame[3] = hallOfFame.readHallOfFame("fourth");
				hall_of_fame_4.setGenes(hall_of_fame[3].getGenes());
				hof_4.repaint();
			}
		});

		videoRecording.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Export.createMovie(GUI.this);
			}
		});
		
		tempSaveBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				warehouse.saveBioMorph(biomorph.getGenes());
				for(int i = 0; i < warehouse.getStoreLength(); i++){
					if(warehouse.getBiomorph(i) != null){
						tempSave[i].setGenes(warehouse.getBiomorph(i).getGenes());
					}	
				}
				update();
			}
		});
		
		previous.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Renderer b = back.get(0);
				biomorph.setGenes(b.getGenes());
				main_biomorph.repaint();
			}
		});

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
					.println("Say: (Evolve | Exit) ( One | Two | Three | Four | Five | Six | Seven | Eight)");

			// loop the recognition until the programm exits.
			loadingFrame.dispose();
			while (speaking) {

				Result result = recognizer.recognize();

				if (result != null) {
					String resultText = result.getBestFinalResultNoFiller();

					String[] split = resultText.split(" ");
					// String word = split[0];
					String number = split[1];

					int num = 0;

					if (resultText.toLowerCase().contains("evolve")) {

						if (number.toLowerCase().equals("one")) {
							num = 0;
						} else if (number.toLowerCase().equals("two")) {
							num = 1;
						} else if (number.toLowerCase().equals("three")) {
							num = 2;
						} else if (number.toLowerCase().equals("four")) {
							num = 3;
						} else if (number.toLowerCase().equals("five")) {
							num = 4;
						} else if (number.toLowerCase().equals("six")) {
							num = 5;
						} else if (number.toLowerCase().equals("seven")) {
							num = 6;
						} else if (number.toLowerCase().equals("eight")) {
							num = 7;
						} //dirty if statement

						try {
							for (int i = 0; i < 10; i++) {
								evolve(num);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("You said: " + split[0] + ": " + num
								+ '\n');
					}

					if (resultText.toLowerCase().contains("exit")) {
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
	private void refreshHOF(){
		
		hall_of_fame_1 = new Renderer(hall_of_fame[0].getGenes(), 5,5,1,1);
	}
	
	private void initialiseSave(){
		tempSave = new Renderer[8];
		int[] empty = { 0,0,0,0,0,0 };
		
		for(int i = 0; i < warehouse.getStoreLength(); i++){
			tempSave[i] = new Renderer(empty, 0, 0, 1, 1); 
		}
		
	}
}

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI extends JFrame {

	private JFrame mainPanel = new JFrame();
	private Graphics2D g2d;
	private Controller con;

	
	public GUI(){
		biomorph = con.createBiomorph(); //?
	}
	
	public static void main(String[] args){
		
	}

	public void initUI(){
		mainPanel.setTitle("Evo art project");
		mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setSize(800, 600);
		mainPanel.setLocationRelativeTo(null);  
		mainPanel.add(biomorph);
		
		mainPanel.setVisible(true);
		mainPanel.setResizable(false);
	}
}

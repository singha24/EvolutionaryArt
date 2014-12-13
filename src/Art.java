import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Biomorph.Biomorph;


public class Art extends JFrame {

	public Art() {

		initUI();
	}

	private void initUI() {

		setTitle("Get on or get 0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new Biomorph());

		setSize(800, 600);
		setLocationRelativeTo(null);        
	}

	public static void main(String[] args) {

		Art lines = new Art();
		lines.setVisible(true);
		lines.setResizable(false);

	}
}
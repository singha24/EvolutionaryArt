import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Art extends JFrame {
	
	private static BioMorph m = new BioMorph();

	public Art() {

		initUI();
	}

	private void initUI() {

		setTitle("Get on or get 0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new BioMorph());

		setSize(800, 600);
		setLocationRelativeTo(null);        
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				Art lines = new Art();
				lines.setVisible(true);
			}
		});
	}
}
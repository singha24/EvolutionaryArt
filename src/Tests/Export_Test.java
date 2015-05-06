package Tests;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;








import org.junit.Test;

import view.Export;

/**
 * @author Assa Test
 *@category Test
 *@see Export
 *
 *A Test class to check whether a JPanel has been converted to a BufferedImage.
 */
public class Export_Test {
	
	private JPanel p;
	private Export e;
	private BufferedImage bi;
	
	@Test
	public void testConvertJPanelTOBufferedImage() throws AWTException{
		e = new Export();
		p = new JPanel();
		JFrame f = new JFrame();
		
		p.setBackground(Color.RED);
		f.setSize(10, 10);
		f.add(p);
		f.pack();
		f.setVisible(true);
		
		assertEquals(e.convertJPanelToBI(p), bi);
	}
	

}

package Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import controller.Controller;
import view.GUI;
import view.Renderer;

public class Controller_Test {
	
	private Controller c;
	
	@Test
	public void checkControllerInitilisation(){
		assertNotNull(c = new Controller());
	}

	@Test
	public void checkSysLog(){
		c = new Controller();
		try {
			assertNotNull(c.readSysFile("SystemLog.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkInstructions(){
		c = new Controller();
		try {
			assertNotNull(c.readSysFile("instructions.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDateTime(){
		c = new Controller();
		assertNotNull(c.getDateTime());
	}
	
	@Test
	public void checkChildrenArraySize(){
		c = new Controller();
		assertTrue(c.getChildArraySize() == 8);
	}
	
	
}

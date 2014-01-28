package test.java.config;

import static org.junit.Assert.*;

import java.util.List;

import main.config.Config;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetInstance() {
		Config config = Config.getInstance();
		assertTrue("config ist NULL!", config != null);
	}

	@Test
	public void testGetConfig() {
		Config config = Config.getInstance();
		List<String[]> list = config.getConfig("einwohnerzahl");
		
		// erstmal Ergebnis auf NULL checken
		assertTrue("list ist NULL!", list != null);
		
		// es gibt 16 Bundesl�nder
		assertTrue("Einwohnerzahlen der BL != 16", list.size() == 16);
		
	}

	@Test
	public void testGetConfigField() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetConfigField() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		Config config = Config.getInstance();
		String alles = config.toString();

		// erstmal Ergebnis auf NULL checken
		assertTrue("list ist NULL!", alles != null);
		
		System.out.println(alles);
	}

}

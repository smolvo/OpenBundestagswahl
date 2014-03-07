package test.java.config;

import static org.junit.Assert.assertTrue;

import java.util.List;

import main.java.config.Config;

import org.junit.Test;

/**
 * Tests für die Config Klasse
 * 
 */
public class ConfigTest {

	@Test
	public void testGetConfig() {
		final Config config = Config.getInstance();
		final List<String[]> list = config.getConfig("einwohnerzahl");

		// erstmal Ergebnis auf NULL checken
		assertTrue("list ist NULL!", list != null);

		// es gibt 16 Bundeslï¿½nder
		assertTrue("Einwohnerzahlen der BL != 16", list.size() == 16);

	}

	/**
	 * Testet die getInstance() Methode bzw den Konstruktor von Config
	 */
	@Test
	public void testGetInstance() {
		final Config config = Config.getInstance();
		assertTrue("config ist NULL!", config != null);
	}

	@Test
	public void testToString() {
		final Config config = Config.getInstance();
		final String alles = config.toString();

		// erstmal Ergebnis auf NULL checken
		assertTrue("list ist NULL!", alles != null);

		System.out.println(alles);
	}

}

/**
 * 
 */
package test.java.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import test.java.Debug;

/**
 * Die erwarteten Testergebnisse basieren auf die, aus der csv- Datei zur
 * Bundestagswahl 2013 stammenden, Daten
 * 
 * 
 * Die Einwohnerzahlen stammen aus der im Programm bereitgestellten config-
 * Datei
 */
public class GebietTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Deutschland testDeutschland;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;
	private static Erststimme testErststimme;
	private static Wahlkreis testWahlkreis;

	private int parameter;

	public GebietTest() {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Debug.setLevel(6);
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");

		try {
			ausgangsWahl = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gültige CSV-Datei :/");
		}

		Debug.setLevel(6);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		testWahl = ausgangsWahl.deepCopy();
		testDeutschland = testWahl.getDeutschland();
		testBundeslaender = testDeutschland.getBundeslaender();
		// testBundesland ist Schleswig-Holstein
		testBundesland = testBundeslaender.get(0);
		testWahlkreise = testBundesland.getWahlkreise();
		// testWahlkreis ist Flensburg-Schleswig
		testWahlkreis = testWahlkreise.get(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetName() {
		testBundesland.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetName1() {
		testBundesland.setName("");
	}
	
	@Test
	public void testSetName2() {
		testBundesland.setName("Test");
		
		assertEquals("Test", testBundesland.getName());
	}

	// Deutschland
	// Wähler - ungültige Erststimmen
	// 44309925 - 684883 = 43625042
	@Test
	public void testGetAnzahlErststimmen() {
		assertEquals(43625042, testDeutschland.getAnzahlErststimmen());
	}

	// Schleswig-Holstein
	// Wähler - ungültige Erststimmen
	// 1645750 - 18752 = 1626998
	@Test
	public void testGetAnzahlErststimmen1() {
		assertEquals(1626998, testBundesland.getAnzahlErststimmen());
	}
	

	// Flensburg-Schleswig
	// Wähler - ungültige Erststimmen
	// 162749 - 2223 = 160526
	@Test
	public void testGetAnzahlErststimmen2() {
		assertEquals(160526, testWahlkreis.getAnzahlErststimmen());
	}

	// Deutschland
		// Wähler - ungültige Zweitstimmen
		// 44309925 - 583069 = 43726856
	@Test
	public void testGetAnzahlZweitstimmen() {
		assertEquals(43726856, testDeutschland.getAnzahlZweitstimmen());
	}
	
	// Schleswig-Holstein
		// Wähler - ungültige Zweitstimmen
		// 1645750 - 17460 = 1628290
	@Test
	public void testGetAnzahlZweitstimmen1() {
		assertEquals(1628290, testBundesland.getAnzahlZweitstimmen());
	}
	
	// Flensburg-Schleswig
		// Wähler - ungültige Zweitstimmen
		// 162749 - 2113 = 160636
	@Test
	public void testGetAnzahlZweitstimmen2() {
		assertEquals(160636, testWahlkreis.getAnzahlZweitstimmen());
	}

}

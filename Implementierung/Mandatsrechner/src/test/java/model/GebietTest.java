/**
 * 
 */
package test.java.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Wahlkreis;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	private static Wahlkreis testWahlkreis;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final ImportExportManager i = new ImportExportManager();
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");

		try {
			GebietTest.ausgangsWahl = i.importieren(csvDateien);
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gültige CSV-Datei :/");
		}
	}

	public GebietTest() {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		GebietTest.testWahl = GebietTest.ausgangsWahl.deepCopy();
		GebietTest.testDeutschland = GebietTest.testWahl.getDeutschland();
		GebietTest.testBundeslaender = GebietTest.testDeutschland
				.getBundeslaender();
		// testBundesland ist Schleswig-Holstein
		GebietTest.testBundesland = GebietTest.testBundeslaender.get(0);
		GebietTest.testWahlkreise = GebietTest.testBundesland.getWahlkreise();
		// testWahlkreis ist Flensburg-Schleswig
		GebietTest.testWahlkreis = GebietTest.testWahlkreise.get(0);
	}

	// Deutschland
	// Wähler - ungültige Erststimmen
	// 44309925 - 684883 = 43625042
	@Test
	public void testGetAnzahlErststimmen() {
		assertEquals(43625042,
				GebietTest.testDeutschland.getAnzahlErststimmen());
	}

	// Schleswig-Holstein
	// Wähler - ungültige Erststimmen
	// 1645750 - 18752 = 1626998
	@Test
	public void testGetAnzahlErststimmen1() {
		assertEquals(1626998, GebietTest.testBundesland.getAnzahlErststimmen());
	}

	// Flensburg-Schleswig
	// Wähler - ungültige Erststimmen
	// 162749 - 2223 = 160526
	@Test
	public void testGetAnzahlErststimmen2() {
		assertEquals(160526, GebietTest.testWahlkreis.getAnzahlErststimmen());
	}

	// Deutschland
	// Wähler - ungültige Zweitstimmen
	// 44309925 - 583069 = 43726856
	@Test
	public void testGetAnzahlZweitstimmen() {
		assertEquals(43726856,
				GebietTest.testDeutschland.getAnzahlZweitstimmen());
	}

	// Schleswig-Holstein
	// Wähler - ungültige Zweitstimmen
	// 1645750 - 17460 = 1628290
	@Test
	public void testGetAnzahlZweitstimmen1() {
		assertEquals(1628290, GebietTest.testBundesland.getAnzahlZweitstimmen());
	}

	// Flensburg-Schleswig
	// Wähler - ungültige Zweitstimmen
	// 162749 - 2113 = 160636
	@Test
	public void testGetAnzahlZweitstimmen2() {
		assertEquals(160636, GebietTest.testWahlkreis.getAnzahlZweitstimmen());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetName() {
		GebietTest.testBundesland.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetName1() {
		GebietTest.testBundesland.setName("");
	}

	@Test
	public void testSetName2() {
		GebietTest.testBundesland.setName("Test");

		assertEquals("Test", GebietTest.testBundesland.getName());
	}

}

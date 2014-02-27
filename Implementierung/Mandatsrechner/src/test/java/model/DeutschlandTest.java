/**
 * 
 */
package test.java.model;

import static org.junit.Assert.*;

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
import test.java.Debug;

/**
 * Die erwarteten Testergebnisse basieren auf die, aus der csv- Datei zur
 * Bundestagswahl 2013 stammenden, Daten
 * 
 * 
 * Die Einwohnerzahlen stammen aus der im Programm bereitgestellten config-
 * Datei
 */
public class DeutschlandTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Deutschland testDeutschland;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;

	private int parameter;

	public DeutschlandTest() {

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
			System.out.println("Keine gï¿½ltige CSV-Datei :/");
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
		// Testbundesland ist Schleswig-Holstein
		testBundesland = testBundeslaender.get(0);
		testWahlkreise = testBundesland.getWahlkreise();
	}

	@Test
	public void testGetErststimmenProPartei() {
		// TODO
	}

	@Test
	public void testGetZweitstimmenProPartei() {
		// TODO
	}

	@Test
	// aus der csv-Datei: Wahlberechtigte - Deutschland - 61946900
	public void testGetWahlberechtigte() {
		assertEquals(61946900, testDeutschland.getWahlberechtigte());
	}
	
	@Test
	// aus der csv-Datei: Wahlberechtigte - Deutschland - 61946900
	public void testGetEinwohnerzahl() {
		//TODO
		assertEquals(0, testDeutschland.getEinwohneranzahl());
	}

	@Test
	// aus der csv-Datei: Zweitstimmen - CDU - Deutschland: 14921877
	// aus der csv-Datei: Zweitstimmen - SPD - Deutschland: 11252215
	public void testGetAnzahlZweitstimmen() {
		// CDU
		assertEquals(14921877,
				testDeutschland.getAnzahlZweitstimmen(testBundesland
						.getParteien().get(0)));
		// SPD
		assertEquals(11252215,
				testDeutschland.getAnzahlZweitstimmen(testBundesland
						.getParteien().get(1)));
	}

	@Test
	// aus der csv-Datei: Erststimmen - CDU - Deutschland: 16233642
	// aus der csv-Datei: Erststimmen - SPD - Deutschland: 12843458
	public void testGetAnzahlErststimmen() {
		// CDU
		assertEquals(16233642, testDeutschland.getAnzahlErststimmen(testBundesland
				.getParteien().get(0)));
		// SPD
		assertEquals(12843458, testDeutschland.getAnzahlErststimmen(testBundesland
				.getParteien().get(1)));
	}

	
	@Test
	public void testGetWahlkreise() {
		//TODO
	}
	
	@Test
	// aus der csv-Datei: Erststimmen - Gesamt - Deutschland: 43625042
	public void testGetGesamtErststimmen() {
		assertEquals(43625042, testDeutschland.getGesamtErststimmen());
	}
	
	@Test
	// aus der csv-Datei: Zweitstimmen - Gesamt - Deutschland: 43726856
	public void testGetGesamtZweitstimmen() {
		assertEquals(43726856, testDeutschland.getGesamtZweitstimmen());
	}
}

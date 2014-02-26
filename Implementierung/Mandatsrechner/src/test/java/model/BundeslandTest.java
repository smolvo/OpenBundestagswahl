/**
 * 
 */
package test.java.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
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
public class BundeslandTest {

	private static Bundestagswahl testWahl;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;

	private int parameter;

	public BundeslandTest() {

	}

	// creates the test data
	@Parameters
	public static List<Object[]> data() {
		Object[][] data = new Object[][] { { 1 }, { 2 }, { 3 } };
		return Arrays.asList(data);
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
				"src/main/resources/importexport/Ergebnis2009.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");

		try {
			testWahl = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gï¿½ltige CSV-Datei :/");
		}

		testBundeslaender = testWahl.getDeutschland().getBundeslaender();
		testBundesland = testBundeslaender.get(0);
		testWahlkreise = testBundesland.getWahlkreise();
		Debug.setLevel(6);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	@Test
	// Aus der config- Datei: Schleswig-Holstein Einwohnerzahl: 2686085
	public void testSetEinwohnerzahl() {
		testBundesland.setEinwohnerzahl(1000);
		// TODO Text schreiben
		assertEquals("", 1000, testBundesland.getEinwohnerzahl());

	}

	@Test(expected = IllegalArgumentException.class)
	// Aus der config- Datei: Schleswig-Holstein Einwohnerzahl: 2686085
	public void testSetEinwohnerzahl2() {
		testBundesland.setEinwohnerzahl(-1000);

	}

	@Test
	public void testGetErststimmenProPartei() {
		// TODO
	}

	@Test
	public void testGetZweitstimmenProPartei() {
		List<Zweitstimme> zweitstimmen = testBundesland.getZweitstimmenProPartei();
		
		Debug.print(zweitstimmen.size()+"", 1);
		for (int i = 0; i < zweitstimmen.size(); i++) {
			Debug.print(i + " " +zweitstimmen.get(i).getPartei().getName(), 1);
		}
		
		assertEquals(null, 35, zweitstimmen.size() );
		assertEquals(null, "SPD", zweitstimmen.get(0).getPartei().getName());
		assertEquals(null, "Schleswig-Holstein", zweitstimmen.get(0).getGebiet().getName());
		assertEquals(null, "CDU", zweitstimmen.get(1).getPartei().getName());
		assertEquals(null, "Schleswig-Holstein", zweitstimmen.get(1).getGebiet().getName());
		assertEquals(null, "FDP", zweitstimmen.get(2).getPartei().getName());
		assertEquals(null, "Schleswig-Holstein", zweitstimmen.get(2).getGebiet().getName());
	}

	@Test
	public void testGetWahlberechtigte() {
		// TODO FRAGE: wahlberechtigte nicht direkt aus csv-Datei auslesen?
	}

	@Ignore
	// CDU - Zweitstimmen - 638756
	public void testErmittleStaerkstePartei2() {
		// TODO Text schreiben
		assertEquals("", "CDU", testBundesland.ermittleStaerkstePartei()
				.getName());

		List<Zweitstimme> zweitstimmmen = testBundesland
				.getZweitstimmenProPartei();

		for (Zweitstimme z : zweitstimmmen) {

			// Die Zweitstimmenanzahl sollte für ein Bundesland eigentlich nicht
			// geändert werden, da
			// wir nicht "runter"rechnen
			if (z.getPartei().getName().equals("SPD")) {
				z.setAnzahl(640000);
			}

		}

		assertEquals("", "SPD", testBundesland.ermittleStaerkstePartei()
				.getName());
	}

	@Test
	// CDU - Zweitstimmen - 638756
	public void testErmittleStaerkstePartei() {
		// TODO Text schreiben
		assertEquals("", "CDU", testBundesland.ermittleStaerkstePartei()
				.getName());
		Debug.print("Wahlberechtigte: " + testBundesland.getWahlberechtigte(),
				1);
		Debug.print(testBundesland.getParteien().get(0).getName() + " "
				+ testBundesland.getZweitstimmenProPartei().get(0).getAnzahl(),
				1);

		Debug.print(testBundesland.getParteien().get(1).getName() + " "
				+ testBundesland.getZweitstimmenProPartei().get(1).getAnzahl(),
				1);

		List<Zweitstimme> zweitstimmmen = testWahlkreise.get(0)
				.getZweitstimmenProPartei();

		for (Wahlkreis wk : testWahlkreise) {
			for (Zweitstimme z : wk.getZweitstimmenProPartei()) {
				if (z.getPartei().getName().equals("SPD")) {
					z.erhoeheAnzahl(30000);
				}

			}
		}
		Debug.print(testBundesland.getParteien().get(0).getName() + " "
				+ testBundesland.getZweitstimmenProPartei().get(0).getAnzahl(),
				1);
		Debug.print(testBundesland.getParteien().get(1).getName() + " "
				+ testBundesland.getZweitstimmenProPartei().get(1).getAnzahl(),
				1);
		assertEquals("", "SPD", testBundesland.ermittleStaerkstePartei()
				.getName());

		// getZweitstimmenProPartei gibt im Moment die Zweitstimmen der
		// Vorperiode
	}
}

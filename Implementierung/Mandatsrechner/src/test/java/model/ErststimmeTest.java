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
public class ErststimmeTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Deutschland testDeutschland;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;
	private static Erststimme testErststimme;

	private int parameter;

	public ErststimmeTest() {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		// testErststimme ist das Erststimmen-Objekt zu Flensburg-Schleswig und
		// CDU
		testErststimme = testWahlkreise.get(0).getErststimmenProPartei().get(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAnzahl() {
		testErststimme.setAnzahl(-1000);
	}

	@Test
	public void testSetAnzahl1() {
		testErststimme.setAnzahl(0);
		
		assertEquals(0, testErststimme.getAnzahl());
	}

	// Flensburg-Schleswig - Wahlberechtigte - 226944
	// Flensburg-Schleswig - Wähler - 162749
	// Wahlberechtigte - (Wähler - ungültige Erststimmen) = 66418
	// Flensburg-Schleswig - CDU - Wähler - 68235
	// Erststimmen die noch gesetzt werden dürfen - 134653
	@Test
	public void testSetAnzahl2() {
		testErststimme.setAnzahl(134653);
		
		assertEquals(134653, testErststimme.getAnzahl());
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void testSetAnzahl3() {
		testErststimme.setAnzahl(134654);
	}

	@Test
	public void testSetAnzahl4() {
		testErststimme.setAnzahl(10);
		
		assertEquals(10, testErststimme.getAnzahl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAnzahlWahlberechtigtePlusEins() {
		
		int wahlberechtigte = testErststimme.getGebiet().getWahlberechtigte();
		int diff = wahlberechtigte - testErststimme.getGebiet().getAnzahlErststimmen() + testErststimme.getAnzahl();
		
		
		testErststimme.setAnzahl(diff + 1);
		
		assertEquals(testErststimme.getGebiet().getWahlberechtigte() + 1,
				testErststimme.getGebiet().getAnzahlErststimmen() + 1);
	}
	
	@Test
	public void testSetAnzahlWahlberechtigte() {

		int wahlberechtigte = testErststimme.getGebiet().getWahlberechtigte();
		int diff = wahlberechtigte - testErststimme.getGebiet().getAnzahlErststimmen() + testErststimme.getAnzahl();
		
		
		testErststimme.setAnzahl(diff);
		
		assertEquals(testErststimme.getGebiet().getWahlberechtigte(),
				testErststimme.getGebiet().getAnzahlErststimmen());
	}
	
	@Test
	public void testSetAnzahlWahlberechtigteMinusEins() {

		int wahlberechtigte = testErststimme.getGebiet().getWahlberechtigte();
		int diff = wahlberechtigte - testErststimme.getGebiet().getAnzahlErststimmen() + testErststimme.getAnzahl();
		
		testErststimme.setAnzahl(diff);
		testErststimme.setAnzahl(diff - 1);
		
		assertEquals(testErststimme.getGebiet().getWahlberechtigte() -1,
				testErststimme.getGebiet().getAnzahlErststimmen());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErhoeheAnzahl1() {
		testErststimme.erhoeheAnzahl(-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testErhoeheAnzahl2() {
		
		while (true) {
			testErststimme.erhoeheAnzahl(1);
		}

	}

	// Flensburg-Schleswig - Wahlberechtigte - 226944
	// Flensburg-Schleswig - Wähler - 162749
	// Wahlberechtigte - (Wähler - ungültige Erststimmen) = 66418
	@Test(expected = IllegalArgumentException.class)
	public void testErhoeheAnzahl3() {
		testErststimme.erhoeheAnzahl(66419);
	}

	// Flensburg-Schleswig - Wahlberechtigte - 226944
	// Flensburg-Schleswig - Wähler - 162749
	// Wahlberechtigte - (Wähler - ungültige Erststimmen) = 66418
	// CDU - Anzahl Erststimmen - 68235
	// CDU - neue Anzahl Erststimmen - 134653
	@Test
	public void testErhoeheAnzahl4() {
		testErststimme.erhoeheAnzahl(66418);
		
		
		assertEquals(134653, testErststimme.getAnzahl());
	}

}

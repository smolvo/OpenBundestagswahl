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
import main.java.model.Erststimme;
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
public class ErststimmeTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Deutschland testDeutschland;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;
	private static Erststimme testErststimme;

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
			ErststimmeTest.ausgangsWahl = i.importieren(csvDateien);
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gültige CSV-Datei :/");
		}

	}

	public ErststimmeTest() {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		ErststimmeTest.testWahl = ErststimmeTest.ausgangsWahl.deepCopy();
		ErststimmeTest.testDeutschland = ErststimmeTest.testWahl
				.getDeutschland();
		ErststimmeTest.testBundeslaender = ErststimmeTest.testDeutschland
				.getBundeslaender();
		// testBundesland ist Schleswig-Holstein
		ErststimmeTest.testBundesland = ErststimmeTest.testBundeslaender.get(0);
		ErststimmeTest.testWahlkreise = ErststimmeTest.testBundesland
				.getWahlkreise();
		// testErststimme ist das Erststimmen-Objekt zu Flensburg-Schleswig und
		// CDU
		ErststimmeTest.testErststimme = ErststimmeTest.testWahlkreise.get(0)
				.getErststimmenProPartei().get(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testErhoeheAnzahl1() {
		ErststimmeTest.testErststimme.erhoeheAnzahl(-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testErhoeheAnzahl2() {

		while (true) {
			ErststimmeTest.testErststimme.erhoeheAnzahl(1);
		}

	}

	// Flensburg-Schleswig - Wahlberechtigte - 226944
	// Flensburg-Schleswig - Wähler - 162749
	// Wahlberechtigte - (Wähler - ungültige Erststimmen) = 66418
	@Test(expected = IllegalArgumentException.class)
	public void testErhoeheAnzahl3() {
		ErststimmeTest.testErststimme.erhoeheAnzahl(66419);
	}

	// @Test(expected = IllegalArgumentException.class)
	// public void testSetAnzahl3() {
	// testErststimme.setAnzahl(134654);
	// }

	// Flensburg-Schleswig - Wahlberechtigte - 226944
	// Flensburg-Schleswig - Wähler - 162749
	// Wahlberechtigte - (Wähler - ungültige Erststimmen) = 66418
	// CDU - Anzahl Erststimmen - 68235
	// CDU - neue Anzahl Erststimmen - 134653
	@Test
	public void testErhoeheAnzahl4() {
		ErststimmeTest.testErststimme.erhoeheAnzahl(66418);

		assertEquals(134653, ErststimmeTest.testErststimme.getAnzahl());
	}

	// @Test(expected = IllegalArgumentException.class)
	// public void testSetAnzahlWahlberechtigtePlusEins() {
	//
	// int wahlberechtigte = testErststimme.getGebiet().getWahlberechtigte();
	// int diff = wahlberechtigte -
	// testErststimme.getGebiet().getAnzahlErststimmen() +
	// testErststimme.getAnzahl();
	//
	//
	// testErststimme.setAnzahl(diff + 1);
	//
	// assertEquals(testErststimme.getGebiet().getWahlberechtigte() + 1,
	// testErststimme.getGebiet().getAnzahlErststimmen() + 1);
	// }

	@Test(expected = IllegalArgumentException.class)
	public void testSetAnzahl() {
		ErststimmeTest.testErststimme.setAnzahl(-1000);
	}

	@Test
	public void testSetAnzahl1() {
		ErststimmeTest.testErststimme.setAnzahl(0);

		assertEquals(0, ErststimmeTest.testErststimme.getAnzahl());
	}

	// Flensburg-Schleswig - Wahlberechtigte - 226944
	// Flensburg-Schleswig - Wähler - 162749
	// Wahlberechtigte - (Wähler - ungültige Erststimmen) = 66418
	// Flensburg-Schleswig - CDU - Wähler - 68235
	// Erststimmen die noch gesetzt werden dürfen - 134653
	@Test
	public void testSetAnzahl2() {
		ErststimmeTest.testErststimme.setAnzahl(134653);

		assertEquals(134653, ErststimmeTest.testErststimme.getAnzahl());
	}

	@Test
	public void testSetAnzahl4() {
		ErststimmeTest.testErststimme.setAnzahl(10);

		assertEquals(10, ErststimmeTest.testErststimme.getAnzahl());
	}

	@Test
	public void testSetAnzahlWahlberechtigte() {

		final int wahlberechtigte = ErststimmeTest.testErststimme.getGebiet()
				.getWahlberechtigte();
		final int diff = wahlberechtigte
				- ErststimmeTest.testErststimme.getGebiet()
						.getAnzahlErststimmen()
				+ ErststimmeTest.testErststimme.getAnzahl();

		ErststimmeTest.testErststimme.setAnzahl(diff);

		assertEquals(ErststimmeTest.testErststimme.getGebiet()
				.getWahlberechtigte(), ErststimmeTest.testErststimme
				.getGebiet().getAnzahlErststimmen());
	}

	@Test
	public void testSetAnzahlWahlberechtigteMinusEins() {

		final int wahlberechtigte = ErststimmeTest.testErststimme.getGebiet()
				.getWahlberechtigte();
		final int diff = wahlberechtigte
				- ErststimmeTest.testErststimme.getGebiet()
						.getAnzahlErststimmen()
				+ ErststimmeTest.testErststimme.getAnzahl();

		ErststimmeTest.testErststimme.setAnzahl(diff);
		ErststimmeTest.testErststimme.setAnzahl(diff - 1);

		assertEquals(ErststimmeTest.testErststimme.getGebiet()
				.getWahlberechtigte() - 1, ErststimmeTest.testErststimme
				.getGebiet().getAnzahlErststimmen());
	}

}

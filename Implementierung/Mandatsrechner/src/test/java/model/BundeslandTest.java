/**
 * 
 */
package test.java.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
public class BundeslandTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;

	private int parameter;

	public BundeslandTest() {

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

		Mandatsrechner2013.getInstance().berechne(ausgangsWahl);
		Debug.setLevel(6);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		testWahl = ausgangsWahl.deepCopy();
		testBundeslaender = testWahl.getDeutschland().getBundeslaender();
		// Testbundesland ist Schleswig-Holstein
		testBundesland = testBundeslaender.get(0);
		testWahlkreise = testBundesland.getWahlkreise();
	}

	@Test
	// Aus der config- Datei: Schleswig-Holstein Einwohnerzahl: 2686085
	public void testSetEinwohnerzahl() {
		testBundesland.setEinwohnerzahl(1000);

		assertEquals(1000, testBundesland.getEinwohnerzahl());

	}

	@Test(expected = IllegalArgumentException.class)
	// Aus der config- Datei: Schleswig-Holstein Einwohnerzahl: 2686085
	public void testSetEinwohnerzahl2() {
		testBundesland.setEinwohnerzahl(-1000);

	}

	
	// aus der csv-Datei: SPD - Erststimmen - 596882
	// aus der csv-Datei: CDU - Erststimmen - 708702
	// aus der csv-Datei: FDP - Erststimmen - 37526
	
//erststimmen.get(1).getKandidat() liefert Kandidat zurück, der nur mit "nulls" initialisiert ist 
	//-> Bundesland.getErststimmenProPartei() macht nicht viel Sinn
	@Test
	public void testGetErststimmenProPartei() {
		List<Erststimme> erststimmen = testBundesland
				.getErststimmenProPartei();

		assertEquals(35, erststimmen.size());
		
		assertEquals("SPD", erststimmen.get(1).getKandidat().getPartei().getName());
		assertEquals("Schleswig-Holstein", erststimmen.get(0).getGebiet()
				.getName());
		assertEquals(596882, erststimmen.get(1).getAnzahl());
		
		assertEquals("CDU", erststimmen.get(0).getKandidat().getPartei().getName());
		assertEquals("Schleswig-Holstein", erststimmen.get(1).getGebiet()
				.getName());
		assertEquals(708702, erststimmen.get(0).getAnzahl());
		
		assertEquals("FDP", erststimmen.get(2).getKandidat().getPartei().getName());
		assertEquals("Schleswig-Holstein", erststimmen.get(2).getGebiet()
				.getName());
		assertEquals(37526, erststimmen.get(2).getAnzahl());
	}

	@Test
	public void testGetZweitstimmenProPartei() {
		List<Zweitstimme> zweitstimmen = testBundesland
				.getZweitstimmenProPartei();

		assertEquals(35, zweitstimmen.size());
		assertEquals("SPD", zweitstimmen.get(1).getPartei().getName());
		assertEquals("Schleswig-Holstein", zweitstimmen.get(0).getGebiet()
				.getName());
		assertEquals("CDU", zweitstimmen.get(0).getPartei().getName());
		assertEquals("Schleswig-Holstein", zweitstimmen.get(1).getGebiet()
				.getName());
		assertEquals("FDP", zweitstimmen.get(2).getPartei().getName());
		assertEquals("Schleswig-Holstein", zweitstimmen.get(2).getGebiet()
				.getName());
	}

	@Test
	// aus der csv-Datei Wahlberechtigte Schleswig-Holstein: 2251796
	public void testGetWahlberechtigte() {
		// Es werden die Wahlberechtigten aus der Vorperiode angegeben
		assertEquals(2251796, testBundesland.getWahlberechtigte());
	}

	@Ignore
	// aus der csv-Datei: CDU - Zweitstimmen - 638756
	public void testErmittleStaerkstePartei2() {
		// TODO Text schreiben
		assertEquals("CDU", testBundesland.ermittleStaerkstePartei().getName());

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

		assertEquals("SPD", testBundesland.ermittleStaerkstePartei().getName());
	}

	@Test
	// aus der csv-Datei: CDU - Zweitstimmen - 638756
	public void testErmittleStaerkstePartei() {
		// TODO Text schreiben
		assertEquals("CDU", testBundesland.ermittleStaerkstePartei().getName());
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
		assertEquals("SPD", testBundesland.ermittleStaerkstePartei().getName());

	}

	@Test
	// aus der csv-Datei: Zweitstimmen - CDU - Schleswig-Holstein: 638756
	// aus der csv-Datei: Zweitstimmen - SPD - Schleswig-Holstein: 513725
	public void testGetAnzahlZweitstimmen() {
		// CDU
		assertEquals(638756,
				testBundesland.getAnzahlZweitstimmen(testBundesland
						.getParteien().get(0)));
		// SPD
		assertEquals(513725,
				testBundesland.getAnzahlZweitstimmen(testBundesland
						.getParteien().get(1)));
	}

	@Test
	// aus der csv-Datei: Erststimmen - CDU - Schleswig-Holstein: 708702
	// aus der csv-Datei: Erststimmen - SPD - Schleswig-Holstein: 596882
	public void testGetAnzahlErststimmen() {
		// CDU
		assertEquals(708702, testBundesland.getAnzahlErststimmen(testBundesland
				.getParteien().get(0)));
		// SPD
		assertEquals(596882, testBundesland.getAnzahlErststimmen(testBundesland
				.getParteien().get(1)));
	}

	@Test
	public void testGetDirektmandate() {
		LinkedList<Kandidat> kandidaten = testBundesland
				.getDirektMandate(testBundesland.getParteien().get(0));
		// TODO
		// getWahlkreisSieger gibt null zurück
		// wahrscheinlich wurde Sieger nicht richtig gesetzt
		for (Kandidat kand : kandidaten) {
			assertEquals(kand.getMandat(), Mandat.DIREKTMANDAT);
		}

	}
}

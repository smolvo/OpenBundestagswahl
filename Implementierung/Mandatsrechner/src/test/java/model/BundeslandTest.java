/**
 * 
 */
package test.java.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
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

	// aus der csv-Datei:
	// Schleswig-Holstein - Erststimmen - 1626998
	@Test
	public void testGetAnzahlErststimmen() {

		assertEquals(1626998, testBundesland.getAnzahlErststimmen());
	}

	@Test
	// aus der csv-Datei
	// Schleswig-Holstein - Zweitstimmen - 1628290
	public void testGetAnzahlZweitstimmen() {

		assertEquals(1628290, testBundesland.getAnzahlZweitstimmen());
	}

	@Test
	// aus der csv-Datei Wahlberechtigte Schleswig-Holstein: 2251796
	public void testGetWahlberechtigte() {
		assertEquals(2251796, testBundesland.getWahlberechtigte());
	}

	@Test
	// aus der csv-Datei: CDU - Zweitstimmen - 638756
	public void testErmittleStaerkstePartei() {
		// TODO Text schreiben
		assertEquals("CDU", testBundesland.ermittleStaerkstePartei().getName());

		List<Zweitstimme> zweitstimmmen = testWahlkreise.get(0)
				.getZweitstimmenProPartei();

		for (Wahlkreis wk : testWahlkreise) {
			for (Zweitstimme z : wk.getZweitstimmenProPartei()) {
				if (z.getPartei().getName().equals("SPD")) {
					z.erhoeheAnzahl(30000);
				}

			}
		}

		assertEquals("SPD", testBundesland.ermittleStaerkstePartei().getName());

	}

	@Test
	// aus der csv-Datei: Zweitstimmen - CDU - Schleswig-Holstein: 638756
	// aus der csv-Datei: Zweitstimmen - SPD - Schleswig-Holstein: 513725
	public void testGetAnzahlZweitstimmenProPartei() {
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
	//aus der csv-Datei
	//SPD - Schleswig-Holstein - 513725
	//CDU - Schleswig-Holstein - 638756
	//FDP - Schleswig-Holstein - 91714
	public void testGetZweitstimmenProPartei() {
		List<Zweitstimme> zweitstimmen = testBundesland
				.getZweitstimmenProPartei();

		assertEquals(35, zweitstimmen.size());
		
		assertEquals("CDU", zweitstimmen.get(0).getPartei().getName());
		assertEquals("Schleswig-Holstein", zweitstimmen.get(0).getGebiet()
				.getName());
		assertEquals(638756, zweitstimmen.get(0).getAnzahl());
		
		assertEquals("SPD", zweitstimmen.get(1).getPartei().getName());
		assertEquals("Schleswig-Holstein", zweitstimmen.get(1).getGebiet()
				.getName());
		assertEquals(513725, zweitstimmen.get(1).getAnzahl());
			
		assertEquals("FDP", zweitstimmen.get(2).getPartei().getName());
		assertEquals("Schleswig-Holstein", zweitstimmen.get(2).getGebiet()
				.getName());
		assertEquals(91714, zweitstimmen.get(2).getAnzahl());
	}
	
	@Test
	// aus der csv-Datei: Erststimmen - CDU - Schleswig-Holstein: 708702
	// aus der csv-Datei: Erststimmen - SPD - Schleswig-Holstein: 596882
	public void testGetAnzahlErststimmenProPartei() {
		// CDU
		assertEquals(708702, testBundesland.getAnzahlErststimmen(testBundesland
				.getParteien().get(0)));
		// SPD
		assertEquals(596882, testBundesland.getAnzahlErststimmen(testBundesland
				.getParteien().get(1)));
	}

	@Test
	public void testGetDirektmandate() {

		// TODO
		// getWahlkreisSieger gibt null zurück
		// wahrscheinlich wurde Sieger nicht richtig gesetzt

		for (int i = 0; i < testBundesland.getParteien().size(); i++) {
			LinkedList<Kandidat> kandidaten = testBundesland
					.getDirektMandate(testBundesland.getParteien().get(i));

			for (Kandidat kand : kandidaten) {
				assertEquals(kand.getMandat(), Mandat.DIREKTMANDAT);
			}

		}

	}

	@Test
	public void testGetDirektmandate2() {

		ArrayList<Kandidat> wahlkreisSieger = new ArrayList<Kandidat>();

		for (Partei p : testBundesland.getParteien()) {
			if (!p.getName().equals("SPD")) {
				continue;
			}

			for (Wahlkreis wk : testBundesland.getWahlkreise()) {
				if (wk.getWahlkreisSieger().getPartei().getName().equals("SPD")) {
					wahlkreisSieger.add(wk.getWahlkreisSieger());
				}

			}

			assertEquals(wahlkreisSieger.size(), testBundesland
					.getDirektMandate(p).size());

		}
	}


	
	@Test(expected = IllegalArgumentException.class)
	public void testGetDirektmandate3() {
		testBundesland.getDirektMandate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWahlkreise() {
		testBundesland.setWahlkreise(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWahlkreise2() {
		LinkedList<Wahlkreis> wahlkreise = new LinkedList<Wahlkreis>();
		testBundesland.setWahlkreise(wahlkreise);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLandesliste() {
		testBundesland.getLandesliste(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetLandesliste() {
		testBundesland.setLandeliste(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetLandesliste2() {
		List<Landesliste> landesliste = new LinkedList<Landesliste>();
		testBundesland.setLandeliste(landesliste);
	}
	
	@Test(expected = IllegalArgumentException.class) 
		public void testSetParteien() {
			testBundesland.setParteien(null);
		}
	
}

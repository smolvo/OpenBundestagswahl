/**
 * 
 */
package test.java.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

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
public class BundeslandTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;

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
			BundeslandTest.ausgangsWahl = i.importieren(csvDateien);
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gültige CSV-Datei :/");
		}

		Mandatsrechner2013.getInstance().berechne(BundeslandTest.ausgangsWahl);
	}

	public BundeslandTest() {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		BundeslandTest.testWahl = BundeslandTest.ausgangsWahl.deepCopy();
		BundeslandTest.testBundeslaender = BundeslandTest.testWahl
				.getDeutschland().getBundeslaender();
		// Testbundesland ist Schleswig-Holstein
		BundeslandTest.testBundesland = BundeslandTest.testBundeslaender.get(0);
		BundeslandTest.testWahlkreise = BundeslandTest.testBundesland
				.getWahlkreise();
	}

	@Test
	// aus der csv-Datei:
	// CDU - Schleswig-Holstein - Zweitstimmen - 638756
	// SPD - Schleswig-Holstein - Zweitstimmen - 513725
	public void testErmittleStaerkstePartei() {

		assertEquals("CDU", BundeslandTest.testBundesland
				.ermittleStaerkstePartei().getName());

		BundeslandTest.testWahlkreise.get(0).getZweitstimmenProPartei();

		for (final Wahlkreis wk : BundeslandTest.testWahlkreise) {
			for (final Zweitstimme z : wk.getZweitstimmenProPartei()) {
				if (z.getPartei().getName().equals("SPD")) {
					z.erhoeheAnzahl(30000);
				}

			}
		}

		assertEquals("SPD", BundeslandTest.testBundesland
				.ermittleStaerkstePartei().getName());

	}

	// aus der csv-Datei:
	// Schleswig-Holstein - Erststimmen - 1626998
	@Test
	public void testGetAnzahlErststimmen() {

		assertEquals(1626998,
				BundeslandTest.testBundesland.getAnzahlErststimmen());
	}

	@Test
	// aus der csv-Datei: Erststimmen - CDU - Schleswig-Holstein: 708702
	// aus der csv-Datei: Erststimmen - SPD - Schleswig-Holstein: 596882
	public void testGetAnzahlErststimmenProPartei() {
		// CDU
		assertEquals(708702,
				BundeslandTest.testBundesland
						.getAnzahlErststimmen(BundeslandTest.testBundesland
								.getParteien().get(0)));
		// SPD
		assertEquals(596882,
				BundeslandTest.testBundesland
						.getAnzahlErststimmen(BundeslandTest.testBundesland
								.getParteien().get(1)));
	}

	@Test
	// aus der csv-Datei
	// Schleswig-Holstein - Zweitstimmen - 1628290
	public void testGetAnzahlZweitstimmen() {

		assertEquals(1628290,
				BundeslandTest.testBundesland.getAnzahlZweitstimmen());
	}

	@Test
	// aus der csv-Datei: Zweitstimmen - CDU - Schleswig-Holstein: 638756
	// aus der csv-Datei: Zweitstimmen - SPD - Schleswig-Holstein: 513725
	public void testGetAnzahlZweitstimmenProPartei() {
		// CDU
		assertEquals(638756,
				BundeslandTest.testBundesland
						.getAnzahlZweitstimmen(BundeslandTest.testBundesland
								.getParteien().get(0)));
		// SPD
		assertEquals(513725,
				BundeslandTest.testBundesland
						.getAnzahlZweitstimmen(BundeslandTest.testBundesland
								.getParteien().get(1)));
	}

	@Test
	public void testGetDirektmandate() {

		for (int i = 0; i < BundeslandTest.testBundesland.getParteien().size(); i++) {
			final LinkedList<Kandidat> kandidaten = BundeslandTest.testBundesland
					.getDirektMandate(BundeslandTest.testBundesland
							.getParteien().get(i));

			for (final Kandidat kand : kandidaten) {
				assertEquals(kand.getMandat(), Mandat.DIREKTMANDAT);
			}

		}

	}

	@Test
	public void testGetDirektmandate2() {

		final ArrayList<Kandidat> wahlkreisSieger = new ArrayList<Kandidat>();

		for (final Partei p : BundeslandTest.testBundesland.getParteien()) {
			if (!p.getName().equals("SPD")) {
				continue;
			}

			for (final Wahlkreis wk : BundeslandTest.testBundesland
					.getWahlkreise()) {
				if (wk.getWahlkreisSieger().getPartei().getName().equals("SPD")) {
					wahlkreisSieger.add(wk.getWahlkreisSieger());
				}

			}

			assertEquals(wahlkreisSieger.size(), BundeslandTest.testBundesland
					.getDirektMandate(p).size());

		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDirektmandate3() {
		BundeslandTest.testBundesland.getDirektMandate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLandesliste() {
		BundeslandTest.testBundesland.getLandesliste(null);
	}

	@Test
	// aus der csv-Datei Wahlberechtigte Schleswig-Holstein: 2251796
	public void testGetWahlberechtigte() {
		assertEquals(2251796,
				BundeslandTest.testBundesland.getWahlberechtigte());
	}

	@Test
	// aus der csv-Datei
	// SPD - Schleswig-Holstein - 513725
	// CDU - Schleswig-Holstein - 638756
	// FDP - Schleswig-Holstein - 91714
	public void testGetZweitstimmenProPartei() {
		final List<Zweitstimme> zweitstimmen = BundeslandTest.testBundesland
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
	// Aus der config- Datei: Schleswig-Holstein Einwohnerzahl: 2686085
	public void testSetEinwohnerzahl() {
		BundeslandTest.testBundesland.setEinwohnerzahl(1000);

		assertEquals(1000, BundeslandTest.testBundesland.getEinwohnerzahl());

	}

	@Test(expected = IllegalArgumentException.class)
	// Aus der config- Datei: Schleswig-Holstein Einwohnerzahl: 2686085
	public void testSetEinwohnerzahl2() {
		BundeslandTest.testBundesland.setEinwohnerzahl(-1000);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetLandesliste() {
		BundeslandTest.testBundesland.setLandeliste(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetLandesliste2() {
		final List<Landesliste> landesliste = new LinkedList<Landesliste>();
		BundeslandTest.testBundesland.setLandeliste(landesliste);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetParteien() {
		BundeslandTest.testBundesland.setParteien(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWahlkreise() {
		BundeslandTest.testBundesland.setWahlkreise(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWahlkreise2() {
		final LinkedList<Wahlkreis> wahlkreise = new LinkedList<Wahlkreis>();
		BundeslandTest.testBundesland.setWahlkreise(wahlkreise);
	}

}

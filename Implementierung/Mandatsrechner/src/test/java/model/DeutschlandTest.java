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
import main.java.model.Deutschland;
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


	
	//aus der csv-Datei
	//SPD - Deutschland - 11252215
	//CDU - Deutschland - 14921877
	//FDP - Deutschland - 2083533
	@Test
	public void testGetZweitstimmenProPartei() {
		List<Zweitstimme> zweitstimmen = testDeutschland.getZweitstimmenProPartei();
		
		assertEquals(35, zweitstimmen.size());
		
		assertEquals("CDU", zweitstimmen.get(0).getPartei().getName());
		assertEquals("Deutschland", zweitstimmen.get(0).getGebiet()
				.getName());
		assertEquals(14921877, zweitstimmen.get(0).getAnzahl());
		
		assertEquals("SPD", zweitstimmen.get(1).getPartei().getName());
		assertEquals("Deutschland", zweitstimmen.get(1).getGebiet()
				.getName());
		assertEquals(11252215, zweitstimmen.get(1).getAnzahl());
			
		assertEquals("FDP", zweitstimmen.get(2).getPartei().getName());
		assertEquals("Deutschland", zweitstimmen.get(2).getGebiet()
				.getName());
		assertEquals(2083533, zweitstimmen.get(2).getAnzahl());
	}

	@Test
	// aus der csv-Datei: Wahlberechtigte - Deutschland - 61946900
	public void testGetWahlberechtigte() {
		assertEquals(61946900, testDeutschland.getWahlberechtigte());
	}
	
	@Test
	// aus der config-Datei: Einwohner aller Bundesländer zusammengezählt 74324165
	public void testGetEinwohnerzahl() {
		assertEquals(74324165, testDeutschland.getEinwohneranzahl());
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

	
	//aus der csv-Datei: Wahlberechtigte - Deutschland - 61946900
	@Test
	public void testGetWahlkreise() {
		ArrayList<Wahlkreis> wahlkreise = testDeutschland.getWahlkreise();
		
		assertEquals(299, wahlkreise.size());
		
		int wahlberechtigte = 0;
		for (Wahlkreis wk : wahlkreise) {
			wahlberechtigte += wk.getWahlberechtigte();
		}
		assertEquals(61946900 , wahlberechtigte);
		
	}
	
	@Test
	// aus der csv-Datei: Erststimmen - Gesamt - Deutschland: 43625042
	public void testGetGesamtErststimmen() {
		assertEquals(43625042, testDeutschland.getAnzahlErststimmen());
	}
	
	@Test
	// aus der csv-Datei: Zweitstimmen - Gesamt - Deutschland: 43726856
	public void testGetGesamtZweitstimmen() {
		assertEquals(43726856, testDeutschland.getAnzahlZweitstimmen());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetBundeslaender() {
		testDeutschland.setBundeslaender(null); 
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetBundeslaender2() {
		LinkedList<Bundesland> bundeslaender = new LinkedList<Bundesland>();
		testDeutschland.setBundeslaender(bundeslaender); 
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddBundesland() {
		testDeutschland.addBundesland(null);
	}
	
	

}

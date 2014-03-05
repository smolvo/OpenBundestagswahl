/**
 * 
 */
package test.java.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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
@RunWith(Parameterized.class)
public class KandidatTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;
	private static Kandidat testKandidat;
	
	
	private String ungueltigerParameter;
	private String gueltigerParameter = "test";

	public KandidatTest(String parameter) {
		this.ungueltigerParameter = parameter;
		
	}
	
	 // creates the test data
	  @Parameters
	  public static Collection<Object[]> data() {
	    Object[][] data = new Object[][] { { "" }, { null }};
	    return Arrays.asList(data);
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
		testBundeslaender = testWahl.getDeutschland().getBundeslaender();
		// Testbundesland ist Schleswig-Holstein
		testBundesland = testBundeslaender.get(0);
		testWahlkreise = testBundesland.getWahlkreise();
		testKandidat = testWahl.getParteien().getFirst().getLandesliste().get(0)
				.getListenkandidaten().get(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInfo() {
		testKandidat.setInfo(ungueltigerParameter, gueltigerParameter, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetInfo2() {
		testKandidat.setInfo(gueltigerParameter, ungueltigerParameter, 0);
	}


	@Test
	public void testKonstruktor() {
		
		Kandidat kand = new Kandidat( "Merkel","Manfred", 1963, Mandat.DIREKTMANDAT, testKandidat.getPartei());
		
		assertEquals("Manfred", kand.getVorname());
		assertEquals("Merkel", kand.getName());
		assertEquals("Merkel", kand.getName());
		assertEquals(Mandat.DIREKTMANDAT, kand.getMandat());
		assertEquals(testKandidat.getPartei(), kand.getPartei());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testKonstruktor2() {
		Kandidat kand = new Kandidat("Merkel", "Manfred", 1963, Mandat.DIREKTMANDAT, testKandidat.getPartei(), null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetErststimme() {
		testKandidat.setErststimme(null);

	}
	
	@Test
	public void testEquals() {
		Kandidat kand1 = new Kandidat("Merkel", "Manfred", 1963, Mandat.DIREKTMANDAT, testKandidat.getPartei());
		Kandidat kand2 = new Kandidat("Merkel", "Momo", 1963, Mandat.DIREKTMANDAT, testKandidat.getPartei());
		
		assertEquals(false, kand1.equals(kand2));
	}
	
}

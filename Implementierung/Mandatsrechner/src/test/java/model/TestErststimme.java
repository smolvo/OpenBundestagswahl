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
public class TestErststimme {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Deutschland testDeutschland;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static List<Wahlkreis> testWahlkreise;
	private static Erststimme testErststimme;
	
	

	private int parameter;

	public TestErststimme() {

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
		testErststimme = testBundesland.getErststimmenProPartei().get(0);
	}

	
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAnzahl() {
		testErststimme.setAnzahl(-1000);

	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testErhoeheAnzahl() {
		
	}
}

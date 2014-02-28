/**
 * 
 */
package test.java.wahlgenerator;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.wahlgenerator.Stimmanteile;
import main.java.wahlgenerator.Wahlgenerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.java.Debug;

/**
 * Diese Klasse enthält Tests für den Wahlgenerator.
 */
public class WahlgeneratorTest {
	
	private static Bundestagswahl wahl2009;
	
	private static Bundestagswahl wahl2013;

	private static ImportExportManager iem;

	/**
	 * Diese Methode wird vor dem ersten Test einmalig ausgeführt bevor die Testklasse erzeugt wurde.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		
		// Debug Level auf 6 (alle Debug Meldungen ausgeben) setzen
		Debug.setLevel(6);
		
		// Bundestagswahlen 2009 und 2013 importieren
		WahlgeneratorTest.iem = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File("src/main/resources/importexport/Wahlbewerber2013.csv");
		try {
			WahlgeneratorTest.wahl2013 = iem.importieren(csvDateien);
			csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2009.csv");
			WahlgeneratorTest.wahl2009 = iem.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Die zu importierenden csv Dateien sind ungültig!");
		}
		
	}

	/**
	 * Diese Methode wird nach dem letzten Test einmalig ausgeführt.
	 */
	@AfterClass
	public static void tearDownAfterClass() {
	}

	/**
	 * Diese Methode wird vor jedem Test ausgeführt.
	 */
	@Before
	public void setUp() {
	}

	/**
	 * Diese Methode wird nach jedem Test ausgeführt.
	 */
	@After
	public void tearDown() {
	}

	/**
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}.
	 */
	@Test
	public void testErzeugeBTW() {
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("CDU"), 40, 40));
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("SPD"), 30, 30));
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("GRÜNE"), 20, 20));
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("FDP"), 10, 10));
		
		Wahlgenerator wg = new Wahlgenerator(wahl2013, anteile);
		
		Bundestagswahl w = wg.erzeugeBTW("Test");
		
		System.out.println(w.getDeutschland().getAnzahlErststimmen() + " / " + w.getDeutschland().getAnzahlErststimmen(w.getParteiByName("CDU")) + " = " + 
				(double) w.getDeutschland().getAnzahlErststimmen() / w.getDeutschland().getAnzahlErststimmen(w.getParteiByName("CDU")) + 
				" => " + 100 / ((double) w.getDeutschland().getAnzahlErststimmen() / w.getDeutschland().getAnzahlErststimmen(w.getParteiByName("CDU"))) + "%");
		
	}

	/**
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#verteileStimmen(main.java.model.Bundestagswahl)}.
	 */
	@Test
	public void testVerteileStimmen() {
		fail("Not yet implemented");
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators mit gültiger Basiswahl und gültigen Stimmanteilen.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test
	public void testWahlgenerator() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), 7, 7));
		
		Wahlgenerator wg = new Wahlgenerator(wahl2013, anteile);
		
		assertEquals("Basiswahl wurde nicht korrekt gesetzt!", wahl2013, wg.getBasisWahl());
		assertEquals("Stimmanteile wurden nicht korrekt gesetzt!", anteile, wg.getStimmanteile());
		assertEquals("Erststimmenanzahl wurden nicht korrekt gesetzt!",
				wahl2013.getDeutschland().getAnzahlErststimmen(), (int) wg.getAnzahlErststimmen());
		assertEquals("Zweitstimmenanzahl wurden nicht korrekt gesetzt!",
				wahl2013.getDeutschland().getAnzahlZweitstimmen(), (int) wg.getAnzahlZweitstimmen());
	}
	
	/**
	 * Testet das erzeugen eines Wahlgenerators Stimmanteilen ungültiger Partei.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorUngueltigeStimmanteile() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(new Partei("Erfundene Partei", new Color(1)), 5, 5));
		
		// IllegalArgumentException sollte geworfen werden, weil es "Erfundene Partei" in der Basiswahl nicht gibt
		new Wahlgenerator(wahl2013, anteile);
	}
	
	/**
	 * Testet das erzeugen eines Wahlgenerators mit negativer Erststimmenanzahl.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileErstNegativ() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), -5, 5));
		
		// IllegalArgumentException sollte geworfen werden, weil Anteil der Erststimmen negativ ist.
		new Wahlgenerator(wahl2013, anteile);
	}
	
	/**
	 * Testet das erzeugen eines Wahlgenerators mit negativer Zweitstimmenanzahl.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileZweitNegativ() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), 5, -5));
		
		// IllegalArgumentException sollte geworfen werden, weil Anteil der Zweitstimmen negativ ist.
		new Wahlgenerator(wahl2013, anteile);
	}
	
	/**
	 * Testet das erzeugen eines Wahlgenerators mit Erststimmenanzahl > 100.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileErstUeber100() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), 60, 10));
		anteile.add(new Stimmanteile(wahl2013.getParteien().getLast(), 60, 10));
		
		// IllegalArgumentException sollte geworfen werden, weil Anteil der Erststimmen > 100 ist.
		new Wahlgenerator(wahl2013, anteile);
	}
	
	/**
	 * Testet das erzeugen eines Wahlgenerators mit Zweitstimmenanzahl > 100.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileZweitUeber100() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), 10, 60));
		anteile.add(new Stimmanteile(wahl2013.getParteien().getLast(), 10, 60));
		
		// IllegalArgumentException sollte geworfen werden, weil Anteil der Zweitstimmen > 100 ist.
		new Wahlgenerator(wahl2013, anteile);
	}
	
	/**
	 * Testet das erzeugen eines Wahlgenerators ungültigen Stimmanteilen.
	 * Stimmanteile für eine Partei doppelt.
	 * 
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileDoppelt() {
		
		LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), 7, 8));
		anteile.add(new Stimmanteile(wahl2013.getParteien().getFirst(), 12, 13));
		
		// IllegalArgumentException sollte geworfen werden, weil Stimmanteile für eine Partei doppelt vergeben sind.
		new Wahlgenerator(wahl2013, anteile);
	}

}

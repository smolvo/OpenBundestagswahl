package test.java.chronik;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.java.chronik.Chronik;
import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


import test.java.Debug;

/**
 * Eine Klasse zum Testen der Chronik.
 * @author 13genesis37
 *
 */
public class ChronikTest {
	private static Bundestagswahl btw = null, btwOrig = null;
	private static List<Zweitstimme> zweitstimmen;
	private static Chronik chronik = null;
	
	/**
	 * 
	 * @throws java.lang.Exception
	 * 		exception.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		btwOrig = null;
		try {
			btwOrig = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gï¿½ltige CSV-Datei :/");
		}
		
		zweitstimmen = new ArrayList<Zweitstimme>();
		
		chronik = new Chronik();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * @throws Exception
	 * 		exception.
	 */
	@Before
	public void setUp() throws Exception {
		btw = btwOrig.deepCopy();
		int count = 0;
		for (Wahlkreis wahlkreis : btw.getDeutschland().getWahlkreise()) {
			if (wahlkreis.getName().equals("Karlsruhe-Stadt")) {
				for (Partei partei : btw.getParteien()) {
					Zweitstimme s = wahlkreis.getZweitstimme(partei);
					if (s.getAnzahl() > 1000) {
						//Debug.print("Partei: " + partei.getName(), 5);
						s.setAnzahl(count++);
						zweitstimmen.add(s);
					}
				}
				break;
			}
		}
		chronik = new Chronik();
		System.out.println("\n\nSTART");
	}

	/**
	 * @throws Exception
	 * 		exception
	 */
	@After
	public void tearDown() throws Exception {
		zweitstimmen.clear();
		System.out.println("END");
		
	}

	/**
	 * Basistest. Testet die Chronik auf Funktionalitaet.
	 */
	@Test
	public void basisTest() {
		int counter = 0;
		int neueAnzahl = 1000;
		Zweitstimme s = (Zweitstimme) zweitstimmen.get(0).deepCopy();
		
		s.setAnzahl(neueAnzahl);
		
		// Anfangs gibt es keine Stimmen zum zuruecksetzen.
		assertFalse(btw.hatStimmenZumZuruecksetzen());
		
		//setze Eine Stimme
		assertTrue(btw.setzeStimme(s, true));
		
		// Nachdem man eine Stimme gesetzt hat gibt es was zum Zuruecksetzen.
		assertTrue(btw.hatStimmenZumZuruecksetzen());
	}
	
	/**
	 * Testet, ob ungültige Stimmen abgefangen werden.
	 */
	@Test
	public void ungueltigeStimmen() {
		Zweitstimme s = (Zweitstimme) zweitstimmen.get(0).deepCopy();
		Partei p = new Partei("TEST", java.awt.Color.BLACK);
		s.setPartei(p);
		
		assertFalse(btw.hatStimmenZumZuruecksetzen());
		assertFalse(btw.setzeStimme(s, true));
		assertFalse(btw.hatStimmenZumZuruecksetzen());
		
	}
	
	/**
	 * Simuliert eine Reihe von Stimmenänderungen und prueft
	 * ob die erwarteten Werte zuruekgegeben wird.
	 */
	@Test
	public void sichereStimmeTest() {
		
		assertFalse(chronik.hatStimmenZumZuruecksetzen());
		assertFalse(chronik.hatStimmenZumWiederherstellen());
		
		chronik.debug();
		
		Zweitstimme s0 = (Zweitstimme) zweitstimmen.get(0).deepCopy();
		s0.setAnzahl(10);
		
		chronik.sichereStimme(zweitstimmen.get(0), s0);
		chronik.debug();
		
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		assertFalse(chronik.hatStimmenZumWiederherstellen());
		
		Zweitstimme oldS = (Zweitstimme) chronik.zuruecksetzenStimme();
		//System.out.println("sichereStimmeTest: "+zweitstimmen.get(0).getPartei().getName()+"-"+oldS.getPartei().getName()+ " "+zweitstimmen.get(0).getAnzahl()+"-"+ oldS.getAnzahl());
		assertEquals(zweitstimmen.get(0).getAnzahl(), oldS.getAnzahl());
		chronik.debug();
		assertFalse(chronik.hatStimmenZumZuruecksetzen());
		assertTrue(chronik.hatStimmenZumWiederherstellen());
	}
	
	/**
	 * Testet das zuruecksetzen einer Stimme.
	 */
	@Test
	public void zurueckSetzenUndWiederherstellenTest() {
		Zweitstimme s0 = (Zweitstimme) zweitstimmen.get(0).deepCopy();
		s0.setAnzahl(10);
		
		Zweitstimme s1 = (Zweitstimme) zweitstimmen.get(1).deepCopy();
		s1.setAnzahl(11);
		
		Zweitstimme s2 = (Zweitstimme) zweitstimmen.get(2).deepCopy();
		s2.setAnzahl(12);

		chronik.debug();
		chronik.sichereStimme(zweitstimmen.get(0), s0);
		
		chronik.debug();
		chronik.sichereStimme(zweitstimmen.get(1), s1);

		chronik.debug();
		chronik.sichereStimme(zweitstimmen.get(2), s2);
		
		Zweitstimme oldS;
		chronik.debug();
		oldS = (Zweitstimme) chronik.zuruecksetzenStimme();
		assertEquals(zweitstimmen.get(2).getAnzahl(), oldS.getAnzahl());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		chronik.debug();
		oldS = (Zweitstimme) chronik.zuruecksetzenStimme();
		assertEquals(zweitstimmen.get(1).getAnzahl(), oldS.getAnzahl());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		chronik.debug();
		oldS = (Zweitstimme) chronik.zuruecksetzenStimme();
		assertEquals(zweitstimmen.get(0).getAnzahl(), oldS.getAnzahl());
		assertFalse(chronik.hatStimmenZumZuruecksetzen());
		chronik.debug();
		oldS = (Zweitstimme) chronik.wiederherstellenStimme();
		assertEquals(s0.getAnzahl(), oldS.getAnzahl());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		chronik.debug();
		oldS = (Zweitstimme) chronik.wiederherstellenStimme();
		assertEquals(s1.getAnzahl(), oldS.getAnzahl());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		chronik.debug();
		oldS = (Zweitstimme) chronik.wiederherstellenStimme();
		assertEquals(s2.getAnzahl(), oldS.getAnzahl());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		chronik.debug();
	}
	
	/**
	 * Setzt Stimmen zurueck und macht danne eine veränderung an 
	 * den Stimmen.
	 */
	@Test
	public void zurueckSetzenUndStimmeSichernTest() {
		Zweitstimme s0 = (Zweitstimme) zweitstimmen.get(0).deepCopy();
		s0.setAnzahl(10);
		
		Zweitstimme s1 = (Zweitstimme) zweitstimmen.get(1).deepCopy();
		s1.setAnzahl(11);
		
		Zweitstimme s2 = (Zweitstimme) zweitstimmen.get(2).deepCopy();
		s2.setAnzahl(12);
		
		Zweitstimme s3 = (Zweitstimme) zweitstimmen.get(3).deepCopy();
		s3.setAnzahl(13);
		
		chronik.debug();
		
		chronik.sichereStimme(zweitstimmen.get(0), s0);
		chronik.debug();
		
		chronik.sichereStimme(zweitstimmen.get(1), s1);
		chronik.debug();
		
		chronik.sichereStimme(zweitstimmen.get(2), s2);
		chronik.debug();
		
		Zweitstimme oldS;
		oldS = (Zweitstimme) chronik.zuruecksetzenStimme();
		chronik.debug();
		
		assertTrue(chronik.hatStimmenZumWiederherstellen());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		
		chronik.sichereStimme(zweitstimmen.get(3), s3);
		chronik.debug();
		assertFalse(chronik.hatStimmenZumWiederherstellen());
		assertTrue(chronik.hatStimmenZumZuruecksetzen());
		
	}
	

}

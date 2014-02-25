package test.java.chronik;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import com.sun.prism.paint.Color;

import test.java.Debug;
import java.awt.color.*;

/**
 * Eine Klasse zum Testen der Chronik.
 * @author 13genesis37
 *
 */
public class ChronikTest {
	private static Bundestagswahl btw = null, btwOrig = null;
	private static List<Zweitstimme> zweitstimmen;
	
	/**
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		Debug.setLevel(5);

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
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		btw = btwOrig.deepCopy();
		for (Wahlkreis wahlkreis : btw.getDeutschland().getWahlkreise()) {
			if (wahlkreis.getName().equals("Karlsruhe-Stadt")) {
				for (Partei partei : btw.getParteien()) {
					Zweitstimme s = wahlkreis.getZweitstimme(partei);
					if (s.getAnzahl() > 1000) {
						Debug.print("Partei: " + partei.getName(), 5);
						zweitstimmen.add(s);
					}
				}
				break;
			}
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		zweitstimmen.clear();
	}

	/**
	 * Basistest. Testet die Chronik auf Funktionalitaet.
	 */
	@Test
	public void basisTest() {
		int counter = 0;
		int neueAnzahl = 1000;
		Zweitstimme s = zweitstimmen.get(0);
		Debug.print(s.getPartei().getName() + " Anzahl: " + s.getAnzahl() + " NeueAnzahl: " + neueAnzahl, 5);
		
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
}

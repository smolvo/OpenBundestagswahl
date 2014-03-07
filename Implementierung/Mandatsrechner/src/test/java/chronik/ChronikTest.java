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
import org.junit.Test;

/**
 * Eine Klasse zum Testen der Chronik.
 * 
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
	 *             exception.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final ImportExportManager i = new ImportExportManager();
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		ChronikTest.btwOrig = null;
		try {
			ChronikTest.btwOrig = i.importieren(csvDateien);
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gï¿½ltige CSV-Datei :/");
		}

		ChronikTest.zweitstimmen = new ArrayList<Zweitstimme>();

		ChronikTest.chronik = new Chronik();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Basistest. Testet die Chronik auf Funktionalitaet.
	 */
	@Test
	public void basisTest() {
		final int neueAnzahl = 1000;
		final Zweitstimme s = (Zweitstimme) ChronikTest.zweitstimmen.get(0)
				.deepCopy();

		s.setAnzahl(neueAnzahl);

		// Anfangs gibt es keine Stimmen zum zuruecksetzen.
		assertFalse(ChronikTest.btw.hatStimmenZumZuruecksetzen());

		// setze Eine Stimme
		assertTrue(ChronikTest.btw.setzeStimme(s, true));

		// Nachdem man eine Stimme gesetzt hat gibt es was zum Zuruecksetzen.
		assertTrue(ChronikTest.btw.hatStimmenZumZuruecksetzen());
	}

	/**
	 * @throws Exception
	 *             exception.
	 */
	@Before
	public void setUp() throws Exception {
		ChronikTest.btw = ChronikTest.btwOrig.deepCopy();
		int count = 0;
		for (final Wahlkreis wahlkreis : ChronikTest.btw.getDeutschland()
				.getWahlkreise()) {
			if (wahlkreis.getName().equals("Karlsruhe-Stadt")) {
				for (final Partei partei : ChronikTest.btw.getParteien()) {
					final Zweitstimme s = wahlkreis.getZweitstimme(partei);
					if (s.getAnzahl() > 1000) {
						// Debug.print("Partei: " + partei.getName(), 5);
						s.setAnzahl(count++);
						ChronikTest.zweitstimmen.add(s);
					}
				}
				break;
			}
		}
		ChronikTest.chronik = new Chronik();
		System.out.println("\n\nSTART");
	}

	/**
	 * Simuliert eine Reihe von Stimmenänderungen und prueft ob die erwarteten
	 * Werte zuruekgegeben wird.
	 */
	@Test
	public void sichereStimmeTest() {

		assertFalse(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		assertFalse(ChronikTest.chronik.hatStimmenZumWiederherstellen());

		ChronikTest.chronik.debug();

		final Zweitstimme s0 = (Zweitstimme) ChronikTest.zweitstimmen.get(0)
				.deepCopy();
		s0.setAnzahl(10);

		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(0), s0);
		ChronikTest.chronik.debug();

		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		assertFalse(ChronikTest.chronik.hatStimmenZumWiederherstellen());

		final Zweitstimme oldS = (Zweitstimme) ChronikTest.chronik
				.zuruecksetzenStimme();
		// System.out.println("sichereStimmeTest: "+zweitstimmen.get(0).getPartei().getName()+"-"+oldS.getPartei().getName()+
		// " "+zweitstimmen.get(0).getAnzahl()+"-"+ oldS.getAnzahl());
		assertEquals(ChronikTest.zweitstimmen.get(0).getAnzahl(),
				oldS.getAnzahl());
		ChronikTest.chronik.debug();
		assertFalse(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		assertTrue(ChronikTest.chronik.hatStimmenZumWiederherstellen());
	}

	/**
	 * @throws Exception
	 *             exception
	 */
	@After
	public void tearDown() throws Exception {
		ChronikTest.zweitstimmen.clear();
		System.out.println("END");

	}

	/**
	 * Testet, ob ungültige Stimmen abgefangen werden.
	 */
	@Test
	public void ungueltigeStimmen() {
		final Zweitstimme s = (Zweitstimme) ChronikTest.zweitstimmen.get(0)
				.deepCopy();
		final Partei p = new Partei("TEST", java.awt.Color.BLACK);
		s.setPartei(p);

		assertFalse(ChronikTest.btw.hatStimmenZumZuruecksetzen());
		assertFalse(ChronikTest.btw.setzeStimme(s, true));
		assertFalse(ChronikTest.btw.hatStimmenZumZuruecksetzen());

	}

	/**
	 * Setzt Stimmen zurueck und macht danne eine veränderung an den Stimmen.
	 */
	@Test
	public void zurueckSetzenUndStimmeSichernTest() {
		final Zweitstimme s0 = (Zweitstimme) ChronikTest.zweitstimmen.get(0)
				.deepCopy();
		s0.setAnzahl(10);

		final Zweitstimme s1 = (Zweitstimme) ChronikTest.zweitstimmen.get(1)
				.deepCopy();
		s1.setAnzahl(11);

		final Zweitstimme s2 = (Zweitstimme) ChronikTest.zweitstimmen.get(2)
				.deepCopy();
		s2.setAnzahl(12);

		final Zweitstimme s3 = (Zweitstimme) ChronikTest.zweitstimmen.get(3)
				.deepCopy();
		s3.setAnzahl(13);

		ChronikTest.chronik.debug();

		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(0), s0);
		ChronikTest.chronik.debug();

		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(1), s1);
		ChronikTest.chronik.debug();

		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(2), s2);
		ChronikTest.chronik.debug();

		ChronikTest.chronik.zuruecksetzenStimme();
		ChronikTest.chronik.debug();

		assertTrue(ChronikTest.chronik.hatStimmenZumWiederherstellen());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());

		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(3), s3);
		ChronikTest.chronik.debug();
		assertFalse(ChronikTest.chronik.hatStimmenZumWiederherstellen());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());

	}

	/**
	 * Testet das zuruecksetzen einer Stimme.
	 */
	@Test
	public void zurueckSetzenUndWiederherstellenTest() {
		final Zweitstimme s0 = (Zweitstimme) ChronikTest.zweitstimmen.get(0)
				.deepCopy();
		s0.setAnzahl(10);

		final Zweitstimme s1 = (Zweitstimme) ChronikTest.zweitstimmen.get(1)
				.deepCopy();
		s1.setAnzahl(11);

		final Zweitstimme s2 = (Zweitstimme) ChronikTest.zweitstimmen.get(2)
				.deepCopy();
		s2.setAnzahl(12);

		ChronikTest.chronik.debug();
		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(0), s0);

		ChronikTest.chronik.debug();
		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(1), s1);

		ChronikTest.chronik.debug();
		ChronikTest.chronik.sichereStimme(ChronikTest.zweitstimmen.get(2), s2);

		Zweitstimme oldS;
		ChronikTest.chronik.debug();
		oldS = (Zweitstimme) ChronikTest.chronik.zuruecksetzenStimme();
		assertEquals(ChronikTest.zweitstimmen.get(2).getAnzahl(),
				oldS.getAnzahl());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		ChronikTest.chronik.debug();
		oldS = (Zweitstimme) ChronikTest.chronik.zuruecksetzenStimme();
		assertEquals(ChronikTest.zweitstimmen.get(1).getAnzahl(),
				oldS.getAnzahl());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		ChronikTest.chronik.debug();
		oldS = (Zweitstimme) ChronikTest.chronik.zuruecksetzenStimme();
		assertEquals(ChronikTest.zweitstimmen.get(0).getAnzahl(),
				oldS.getAnzahl());
		assertFalse(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		ChronikTest.chronik.debug();
		oldS = (Zweitstimme) ChronikTest.chronik.wiederherstellenStimme();
		assertEquals(s0.getAnzahl(), oldS.getAnzahl());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		ChronikTest.chronik.debug();
		oldS = (Zweitstimme) ChronikTest.chronik.wiederherstellenStimme();
		assertEquals(s1.getAnzahl(), oldS.getAnzahl());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		ChronikTest.chronik.debug();
		oldS = (Zweitstimme) ChronikTest.chronik.wiederherstellenStimme();
		assertEquals(s2.getAnzahl(), oldS.getAnzahl());
		assertTrue(ChronikTest.chronik.hatStimmenZumZuruecksetzen());
		ChronikTest.chronik.debug();
	}

}

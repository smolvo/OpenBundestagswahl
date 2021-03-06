package test.java.wahlgenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.wahlgenerator.Stimmanteile;
import main.java.wahlgenerator.Wahlgenerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Diese Klasse enthält Tests für den Wahlgenerator.
 */
public class WahlgeneratorTest {

	private static Bundestagswahl wahl2009;

	private static Bundestagswahl wahl2013;

	private static ImportExportManager iem;

	/**
	 * Diese Methode wird vor dem ersten Test einmalig ausgeführt bevor die
	 * Testklasse erzeugt wurde.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {

		// Bundestagswahlen 2009 und 2013 importieren
		WahlgeneratorTest.iem = new ImportExportManager();
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		try {
			WahlgeneratorTest.wahl2013 = WahlgeneratorTest.iem
					.importieren(csvDateien);
			csvDateien[0] = new File(
					"src/main/resources/importexport/Ergebnis2009.csv");
			WahlgeneratorTest.wahl2009 = WahlgeneratorTest.iem
					.importieren(csvDateien);
		} catch (final Exception e1) {
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
	 * Testet die Laufzeit von einer typischen Wahlgenerierung auf Basis der BTW
	 * von 2013. Test schlägt fehl, wenn die Berechnung länger als 3 Sekunden
	 * dauert.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}
	 * .
	 */
	@Test(timeout = 3000)
	public void LaufzeitmessungBTWBasis2013() {
		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("CDU"), 40, 40));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("SPD"), 30, 30));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("GRÜNE"), 20, 20));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("FDP"), 10, 10));

		final Wahlgenerator wg = new Wahlgenerator(WahlgeneratorTest.wahl2013,
				anteile);

		final Bundestagswahl w = wg.erzeugeBTW("Test");

		for (final Stimmanteile sa : anteile) {
			/*
			 * Prüfe für jede Partei, für die Stimmanteile vergeben wurden, ob
			 * sie die korrekte Anzahl tatsächlich erhalten hat
			 */

			final int erwartetErst = (int) (sa.getAnteilErststimmen() / 100.0 * wg
					.getAnzahlErststimmen());
			final int faktischErst = w.getDeutschland().getAnzahlErststimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Erststimmen von " + sa.getPartei().getName(),
					erwartetErst, faktischErst);

			final int erwartetZweit = (int) (sa.getAnteilZweitstimmen() / 100.0 * wg
					.getAnzahlZweitstimmen());
			final int faktischZweit = w.getDeutschland().getAnzahlZweitstimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Zweitstimmen von " + sa.getPartei().getName(),
					erwartetZweit, faktischZweit);
		}

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
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}
	 * .
	 */
	@Test
	public void testErzeugeBTWAlleAusserEineStimmanteileNull() {
		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		for (final Partei partei : WahlgeneratorTest.wahl2013.getParteien()) {
			anteile.add(new Stimmanteile(partei, 0, 0));
		}
		anteile.removeFirst();

		final Wahlgenerator wg = new Wahlgenerator(WahlgeneratorTest.wahl2013,
				anteile);

		final Bundestagswahl w = wg.erzeugeBTW("Test");

		for (final Stimmanteile sa : anteile) {
			/*
			 * Prüfe für jede Partei, für die Stimmanteile vergeben wurden, ob
			 * sie die korrekte Anzahl tatsächlich erhalten hat
			 */

			final int erwartetErst = (int) (sa.getAnteilErststimmen() / 100.0 * wg
					.getAnzahlErststimmen());
			final int faktischErst = w.getDeutschland().getAnzahlErststimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Erststimmen von " + sa.getPartei().getName(),
					erwartetErst, faktischErst);

			final int erwartetZweit = (int) (sa.getAnteilZweitstimmen() / 100.0 * wg
					.getAnzahlZweitstimmen());
			final int faktischZweit = w.getDeutschland().getAnzahlZweitstimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Zweitstimmen von " + sa.getPartei().getName(),
					erwartetZweit, faktischZweit);
		}

	}

	/**
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testErzeugeBTWAlleStimmanteileNull() {
		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		for (final Partei partei : WahlgeneratorTest.wahl2013.getParteien()) {
			anteile.add(new Stimmanteile(partei, 0, 0));
		}

		final Wahlgenerator wg = new Wahlgenerator(WahlgeneratorTest.wahl2013,
				anteile);

		final Bundestagswahl w = wg.erzeugeBTW("Test");

		for (final Stimmanteile sa : anteile) {
			/*
			 * Prüfe für jede Partei, für die Stimmanteile vergeben wurden, ob
			 * sie die korrekte Anzahl tatsächlich erhalten hat
			 */

			final int erwartetErst = (int) (sa.getAnteilErststimmen() / 100.0 * wg
					.getAnzahlErststimmen());
			final int faktischErst = w.getDeutschland().getAnzahlErststimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Erststimmen von " + sa.getPartei().getName(),
					erwartetErst, faktischErst);

			final int erwartetZweit = (int) (sa.getAnteilZweitstimmen() / 100.0 * wg
					.getAnzahlZweitstimmen());
			final int faktischZweit = w.getDeutschland().getAnzahlZweitstimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Zweitstimmen von " + sa.getPartei().getName(),
					erwartetZweit, faktischZweit);
		}

	}

	/**
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}
	 * .
	 */
	@Test
	public void testErzeugeBTWBasis2009() {
		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2009
				.getParteiByName("CDU"), 40, 40));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2009
				.getParteiByName("SPD"), 30, 30));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2009
				.getParteiByName("GRÜNE"), 20, 20));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2009
				.getParteiByName("FDP"), 10, 10));

		final Wahlgenerator wg = new Wahlgenerator(WahlgeneratorTest.wahl2009,
				anteile);

		final Bundestagswahl w = wg.erzeugeBTW("Test");

		for (final Stimmanteile sa : anteile) {
			/*
			 * Prüfe für jede Partei, für die Stimmanteile vergeben wurden, ob
			 * sie die korrekte Anzahl tatsächlich erhalten hat
			 */

			final int erwartetErst = (int) (sa.getAnteilErststimmen() / 100.0 * wg
					.getAnzahlErststimmen());
			final int faktischErst = w.getDeutschland().getAnzahlErststimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Erststimmen von " + sa.getPartei().getName(),
					erwartetErst, faktischErst);

			final int erwartetZweit = (int) (sa.getAnteilZweitstimmen() / 100.0 * wg
					.getAnzahlZweitstimmen());
			final int faktischZweit = w.getDeutschland().getAnzahlZweitstimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Zweitstimmen von " + sa.getPartei().getName(),
					erwartetZweit, faktischZweit);
		}

	}

	/**
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}
	 * .
	 */
	@Test
	public void testErzeugeBTWBasis2013() {
		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("CDU"), 40, 40));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("SPD"), 30, 30));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("GRÜNE"), 20, 20));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013
				.getParteiByName("FDP"), 10, 10));

		final Wahlgenerator wg = new Wahlgenerator(WahlgeneratorTest.wahl2013,
				anteile);

		final Bundestagswahl w = wg.erzeugeBTW("Test");

		for (final Stimmanteile sa : anteile) {
			/*
			 * Prüfe für jede Partei, für die Stimmanteile vergeben wurden, ob
			 * sie die korrekte Anzahl tatsächlich erhalten hat
			 */

			final int erwartetErst = (int) (sa.getAnteilErststimmen() / 100.0 * wg
					.getAnzahlErststimmen());
			final int faktischErst = w.getDeutschland().getAnzahlErststimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Erststimmen von " + sa.getPartei().getName(),
					erwartetErst, faktischErst);

			final int erwartetZweit = (int) (sa.getAnteilZweitstimmen() / 100.0 * wg
					.getAnzahlZweitstimmen());
			final int faktischZweit = w.getDeutschland().getAnzahlZweitstimmen(
					w.getParteiByName(sa.getPartei().getName()));
			assertEquals("Zweitstimmen von " + sa.getPartei().getName(),
					erwartetZweit, faktischZweit);
		}

	}

	/**
	 * Testet das erzeugen eines Wahlgenerators mit gültiger Basiswahl und
	 * gültigen Stimmanteilen.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test
	public void testWahlgenerator() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), 7, 7));

		final Wahlgenerator wg = new Wahlgenerator(WahlgeneratorTest.wahl2013,
				anteile);

		assertEquals("Basiswahl wurde nicht korrekt gesetzt!",
				WahlgeneratorTest.wahl2013, wg.getBasisWahl());
		assertEquals("Stimmanteile wurden nicht korrekt gesetzt!", anteile,
				wg.getStimmanteile());
		assertEquals("Erststimmenanzahl wurden nicht korrekt gesetzt!",
				WahlgeneratorTest.wahl2013.getDeutschland()
						.getAnzahlErststimmen(),
				(int) wg.getAnzahlErststimmen());
		assertEquals("Zweitstimmenanzahl wurden nicht korrekt gesetzt!",
				WahlgeneratorTest.wahl2013.getDeutschland()
						.getAnzahlZweitstimmen(),
				(int) wg.getAnzahlZweitstimmen());
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators ungültigen Stimmanteilen.
	 * Stimmanteile für eine Partei doppelt.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileDoppelt() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), 7, 8));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), 12, 13));

		// IllegalArgumentException sollte geworfen werden, weil Stimmanteile
		// für eine Partei doppelt vergeben sind.
		new Wahlgenerator(WahlgeneratorTest.wahl2013, anteile);
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators mit negativer Erststimmenanzahl.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileErstNegativ() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), -5, 5));

		// IllegalArgumentException sollte geworfen werden, weil Anteil der
		// Erststimmen negativ ist.
		new Wahlgenerator(WahlgeneratorTest.wahl2013, anteile);
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators mit Erststimmenanzahl > 100.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileErstUeber100() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), 60, 10));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getLast(), 60, 10));

		// IllegalArgumentException sollte geworfen werden, weil Anteil der
		// Erststimmen > 100 ist.
		new Wahlgenerator(WahlgeneratorTest.wahl2013, anteile);
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators mit negativer
	 * Zweitstimmenanzahl.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileZweitNegativ() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), 5, -5));

		// IllegalArgumentException sollte geworfen werden, weil Anteil der
		// Zweitstimmen negativ ist.
		new Wahlgenerator(WahlgeneratorTest.wahl2013, anteile);
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators mit Zweitstimmenanzahl > 100.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorStimmanteileZweitUeber100() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getFirst(), 10, 60));
		anteile.add(new Stimmanteile(WahlgeneratorTest.wahl2013.getParteien()
				.getLast(), 10, 60));

		// IllegalArgumentException sollte geworfen werden, weil Anteil der
		// Zweitstimmen > 100 ist.
		new Wahlgenerator(WahlgeneratorTest.wahl2013, anteile);
	}

	/**
	 * Testet das erzeugen eines Wahlgenerators Stimmanteilen ungültiger Partei.
	 * 
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#Wahlgenerator(main.java.model.Bundestagswahl, java.util.List)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWahlgeneratorUngueltigeStimmanteile() {

		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(new Partei("Erfundene Partei",
				new Color(1)), 5, 5));

		// IllegalArgumentException sollte geworfen werden, weil es
		// "Erfundene Partei" in der Basiswahl nicht gibt
		new Wahlgenerator(WahlgeneratorTest.wahl2013, anteile);
	}

	/**
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#erzeugeBTW(java.lang.String)}
	 * .
	 */
	@Ignore
	public void WahlenGenerierenUndBerechnen() {
		for (int i = 0; i < 300; i++) {
			final LinkedList<Stimmanteile> anteile = new LinkedList<>();

			final Wahlgenerator wg = new Wahlgenerator(
					WahlgeneratorTest.wahl2013, anteile);
			final Bundestagswahl w = wg.erzeugeBTW("Test");

			Mandatsrechner2013.getInstance().berechne(w);

			for (final Stimmanteile sa : anteile) {
				/*
				 * Prüfe für jede Partei, für die Stimmanteile vergeben wurden,
				 * ob sie die korrekte Anzahl tatsächlich erhalten hat
				 */

				final int erwartetErst = (int) (sa.getAnteilErststimmen() / 100.0 * wg
						.getAnzahlErststimmen());
				final int faktischErst = w.getDeutschland()
						.getAnzahlErststimmen(
								w.getParteiByName(sa.getPartei().getName()));
				assertEquals("Erststimmen von " + sa.getPartei().getName(),
						erwartetErst, faktischErst);

				final int erwartetZweit = (int) (sa.getAnteilZweitstimmen() / 100.0 * wg
						.getAnzahlZweitstimmen());
				final int faktischZweit = w.getDeutschland()
						.getAnzahlZweitstimmen(
								w.getParteiByName(sa.getPartei().getName()));
				assertEquals("Zweitstimmen von " + sa.getPartei().getName(),
						erwartetZweit, faktischZweit);

				System.out.print(".");
			}

		}
	}

}

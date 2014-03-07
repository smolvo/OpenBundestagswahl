package test.java.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import main.java.model.Bundestagswahl;
import main.java.model.Zweitstimme;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Diese Klasse testet die Methoden der Zweitstimme-Klasse.
 * 
 */
public class ZweitstimmeTest {

	/** repräsentiert die unverfälschte Wahl2013 */
	private static Bundestagswahl wahl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		ZweitstimmeTest.wahl = Steuerung.getInstance().importieren(csvDateien);
	}

	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;

	/**
	 * Testet das Kopieren einer Zweitstimme
	 */
	@Test
	public void deepCopyTest() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(1);
		final Zweitstimme copyCat = (Zweitstimme) stimme.deepCopy();
		assertEquals(stimme.getAnzahl(), copyCat.getAnzahl());
		assertEquals(stimme.getGebiet(), copyCat.getGebiet());
		assertEquals(stimme.getPartei(), copyCat.getPartei());
	}

	@Test(expected = IllegalArgumentException.class)
	public void erhoeheAnzahlTest1() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(1);
		stimme.erhoeheAnzahl(-200);
	}

	@Test(expected = IllegalArgumentException.class)
	public void erhoeheAnzahlTest2() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(0);
		stimme.erhoeheAnzahl(1000000);
	}

	/**
	 * Testet das erhöhen einer Zweitstimme
	 */
	@Test
	public void erhoeheAnzahlTest3() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(1);
		assertEquals(52396, stimme.getAnzahl());
		stimme.erhoeheAnzahl(200);
		assertEquals(52596, stimme.getAnzahl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void erniedrigeAnzahlTest1() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(0);
		stimme.erniedrigeAnzahl(-200);
	}

	@Test(expected = IllegalArgumentException.class)
	public void erniedrigeAnzahlTest2() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(0);
		stimme.erniedrigeAnzahl(1000000);
	}

	/**
	 * Testet das Erniedrigen einer Zweitstimme
	 */
	@Test
	public void erniedrigeAnzahlTest3() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(0);
		assertEquals(61347, stimme.getAnzahl());
		stimme.erniedrigeAnzahl(200);
		assertEquals(61147, stimme.getAnzahl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setAnzahlFail() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(1);
		stimme.setAnzahl(-1);
	}

	/**
	 * Alle nachfolgenden Tests sind zum Exceptionwurf bei null-Eingaben
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setParteiFail() {
		final Zweitstimme stimme = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getWahlkreise().get(0)
				.getZweitstimmenProPartei().get(1);
		stimme.setPartei(null);
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = ZweitstimmeTest.wahl.deepCopy();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
}

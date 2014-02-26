package test.java.model;

import static org.junit.Assert.*;

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
	
	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl = Steuerung.getInstance().importieren(csvDateien);
	}
	
	@Before
	public void setUp() throws Exception {
		this.cloneWahl = wahl.deepCopy();
	}
	
	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void erniedrigeAnzahlTest1() {
		Zweitstimme stimme = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0).getZweitstimmenProPartei().get(0);
		stimme.erniedrigeAnzahl(-200);
	}
	
	@Test
	public void erniedrigeAnzahlTest2() {
		Zweitstimme stimme = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0).getZweitstimmenProPartei().get(0);
		assertEquals(61347, stimme.getAnzahl());
		stimme.erniedrigeAnzahl(200);
		assertEquals(61147, stimme.getAnzahl());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void erhoeheAnzahlTest1() {
		Zweitstimme stimme = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0).getZweitstimmenProPartei().get(1);
		stimme.erhoeheAnzahl(-200);
	}

	@Test
	public void erhoeheAnzahlTest2() {
		Zweitstimme stimme = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0).getZweitstimmenProPartei().get(1);
		assertEquals(52396, stimme.getAnzahl());
		stimme.erhoeheAnzahl(200);
		assertEquals(52596, stimme.getAnzahl());
	}
}

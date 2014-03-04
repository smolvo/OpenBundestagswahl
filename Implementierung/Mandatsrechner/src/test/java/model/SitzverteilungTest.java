package test.java.model;

import static org.junit.Assert.*;

import java.io.File;

import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SitzverteilungTest {

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

	/**
	 * Testet ob 2 Parteien die korrekte Sitzanzahl haben
	 */
	@Test
	public void getAnzahlSitze() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		Partei spd = this.cloneWahl.getParteien().get(1);
		assertEquals(255, this.cloneWahl.getSitzverteilung().getAnzahlSitze(cdu));
		assertEquals(193, this.cloneWahl.getSitzverteilung().getAnzahlSitze(spd));
	}

	/**
	 * Alle nachfolgenden Tests sind Exceptionwruf bei null-Eingaben
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setBerichtFail() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		this.cloneWahl.getSitzverteilung().setBericht(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setAbgeordneteFail() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		this.cloneWahl.getSitzverteilung().setAbgeordnete(null);
	}
}

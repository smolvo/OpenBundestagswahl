/**
 * 
 */
package test.java.wahlgenerator;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.stimmgewichtsimulator.StimmgewichtSimulator;

import org.junit.Before;
import org.junit.Test;

import test.java.Debug;

/**
 * Tests fï¿½r den Stimmgewichtsimulator
 */
public class StimmgewichtSimulatorTest {

	private StimmgewichtSimulator simu = null;

	private Mandatsrechner2009 rechner09 = null;
	private Mandatsrechner2013 rechner13 = null;

	private Bundestagswahl wahl1 = null;
	private Bundestagswahl wahl2 = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Debug.setAktiv(false);

		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2009.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl1 = null;
		try {
			wahl1 = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gï¿½ltige CSV-Datei :/");
		}

		this.rechner09 = Mandatsrechner2009.getInstance();
		this.rechner13 = Mandatsrechner2013.getInstance();

		this.simu = new StimmgewichtSimulator(wahl1);

	}

	public void testBerechneRelevanteZweitstimmen() {
		/*
		 * simu.berechneRelevanteZweitstimmen(); for (Partei p :
		 * simu.getVerwandteWahl().getParteien()) {
		 * System.out.println(p.getName()); for (Landesliste l :
		 * p.getLandesliste()) {
		 * 
		 * if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() > 0) {
		 * System.out.print(l.getBundesland().toString() + " ");
		 * System.out.println(" ï¿½berhangmandate: " +
		 * l.getKandidaten(Mandat.UEBERHANGMADAT).size());
		 * 
		 * }
		 * 
		 * } System.out.println(p.getRelevanteZweitstimmen().getAnzahl()); }
		 */

	}

	@Test
	public void berechneNegStimmgewichtTest() {
		Debug.setAktiv(true);

		assertTrue(simu.berechneNegStimmgewicht());
	}
}

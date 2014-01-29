/**
 * 
 */
package test.java.wahlgenerator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.wahlgenerator.StimmgewichtSimulator;

import org.junit.Before;
import org.junit.Test;

import test.java.Debug;

/**
 * @author Manuel
 * 
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
		csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File("src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl1 = null;
		try {
			wahl1 = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gültige CSV-Datei :/");
		}

		this.rechner09 = Mandatsrechner2009.getInstance();
		this.rechner13 = Mandatsrechner2013.getInstance();

		this.simu = new StimmgewichtSimulator(wahl1);
		
	}

	public void testBerechneRelevanteZweitstimmen() {
		/*
		simu.berechneRelevanteZweitstimmen();
		for (Partei p : simu.getVerwandteWahl().getParteien()) {
			System.out.println(p.getName());
			for (Landesliste l : p.getLandesliste()) {

				if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() > 0) {
					System.out.print(l.getBundesland().toString() + " ");
					System.out.println(" Überhangmandate: "
							+ l.getKandidaten(Mandat.UEBERHANGMADAT).size());

				}

			}
			System.out.println(p.getRelevanteZweitstimmen().getAnzahl());
		}
		*/

	}

	public void testTest() {

		List<Partei> relevanteParteien = simu.waehleParteien();

		for (Partei p : relevanteParteien) {
			System.out.println(p.getName());
		}
		System.out.println(relevanteParteien.size());
/*
		for (Partei x : simu.getVerwandteWahl().getParteien()) {
			if (x.getName().equals("CDU")) {
				simu.erhoeheRelevantenAnteil(x);

			}
		}
		relevanteParteien = simu.waehleParteien();
		for (Partei y : relevanteParteien) {
			System.out.println(y.getName());
		}
		*/
	}

	public void testWaehleParteien() {
		List<Partei> relevanteParteien = simu.waehleParteien();

		for (Partei p : relevanteParteien) {
			System.out.println(p.getName());
		}
		System.out.println(relevanteParteien.size());
	}

	public void rechnerTest() {

		rechner09.berechneSainteLague(wahl1);
		System.out.println("Mandatsrechner2009 Ergebnisse");
		System.out.println("1. Gesamtanzahl Sitze : "
				+ wahl1.getSitzverteilung().getAbgeordnete().size());
		rechner09.berechneSainteLague(wahl1);
		System.out.println("2. Gesamtanzahl Sitze : "
				+ wahl1.getSitzverteilung().getAbgeordnete().size());
		rechner09.berechneSainteLague(wahl1);
		System.out.println("3. Gesamtanzahl Sitze : "
				+ wahl1.getSitzverteilung().getAbgeordnete().size());
		rechner09.berechneSainteLague(wahl1);
		System.out.println("4. Gesamtanzahl Sitze : "
				+ wahl1.getSitzverteilung().getAbgeordnete().size());

		rechner13.berechne(wahl2);
		System.out.println("Mandatsrechner2013 Ergebnisse");
		System.out.println("1. Gesamtanzahl Sitze : "
				+ wahl2.getSitzverteilung().getAbgeordnete().size());
		rechner13.berechne(wahl2);
		System.out.println("2. Gesamtanzahl Sitze : "
				+ wahl2.getSitzverteilung().getAbgeordnete().size());
		rechner13.berechne(wahl2);
		System.out.println("3. Gesamtanzahl Sitze : "
				+ wahl2.getSitzverteilung().getAbgeordnete().size());
		rechner13.berechne(wahl2);
		System.out.println("4. Gesamtanzahl Sitze : "
				+ wahl2.getSitzverteilung().getAbgeordnete().size());
	}

	//@Test
	public void simonTest() {
		Debug.setAktiv(true);
		//Partei p = simu.getVerwandteWahl().getParteien().get(0);
		// Anteil wird solange erhöht bis die Anteilsverhältnisse passen, also
		// Anteil relev. Zs > Mandate
		//while (!simu.bedingungErfuellt(p)) {
//
	//		simu.erhoeheRelevantenAnteil(p);
		//}
	}

	
	@Test
	public void simonTest2() {
		Debug.setAktiv(false);
		
		assertTrue(simu.berechneNegStimmgewicht());
	}
}

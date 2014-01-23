/**
 * 
 */
package test;

import static org.junit.Assert.*;
import importexport.ImportExportManager;

import java.io.File;
import java.util.List;

import mandatsrechner.Mandatsrechner2009;
import mandatsrechner.Mandatsrechner2013;
import model.Bundestagswahl;
import model.Landesliste;
import model.Mandat;
import model.Partei;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import wahlgenerator.StimmgewichtSimulator;

/**
 * @author Manuel
 * 
 */
public class StimmgewichtSimulatorTest {

	private StimmgewichtSimulator simu = null;

	private Mandatsrechner2009 rechner09 = null;
	private Mandatsrechner2013 rechner13 = null;

	private Bundestagswahl w = null;
	private Bundestagswahl w2 = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");

		try {
			this.w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.rechner09 = Mandatsrechner2009.getInstance();
		this.rechner13 = Mandatsrechner2013.getInstance();

		rechner09.berechneAlles(w);

		this.simu = new StimmgewichtSimulator(w);
		
	}

	public void testBerechneRelevanteZweitstimmen() {
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

	}

	public void testTest() {

		List<Partei> relevanteParteien = simu.waehleParteien();

		for (Partei p : relevanteParteien) {
			System.out.println(p.getName());
		}
		System.out.println(relevanteParteien.size());

		for (Partei x : simu.getVerwandteWahl().getParteien()) {
			if (x.getName().equals("CDU")) {
				simu.erhoeheRelevantenAnteil(x);

			}
		}
		relevanteParteien = simu.waehleParteien();
		for (Partei y : relevanteParteien) {
			System.out.println(y.getName());
		}
	}

	public void testWaehleParteien() {
		List<Partei> relevanteParteien = simu.waehleParteien();

		for (Partei p : relevanteParteien) {
			System.out.println(p.getName());
		}
		System.out.println(relevanteParteien.size());
	}

	public void rechnerTest() {

		rechner09.berechneAlles(w);
		System.out.println("Mandatsrechner2009 Ergebnisse");
		System.out.println("1. Gesamtanzahl Sitze : "
				+ w.getSitzverteilung().getAbgeordnete().size());
		rechner09.berechneAlles(w);
		System.out.println("2. Gesamtanzahl Sitze : "
				+ w.getSitzverteilung().getAbgeordnete().size());
		rechner09.berechneAlles(w);
		System.out.println("3. Gesamtanzahl Sitze : "
				+ w.getSitzverteilung().getAbgeordnete().size());
		rechner09.berechneAlles(w);
		System.out.println("4. Gesamtanzahl Sitze : "
				+ w.getSitzverteilung().getAbgeordnete().size());

		rechner13.berechneAlles(w2);
		System.out.println("Mandatsrechner2013 Ergebnisse");
		System.out.println("1. Gesamtanzahl Sitze : "
				+ w2.getSitzverteilung().getAbgeordnete().size());
		rechner13.berechneAlles(w2);
		System.out.println("2. Gesamtanzahl Sitze : "
				+ w2.getSitzverteilung().getAbgeordnete().size());
		rechner13.berechneAlles(w2);
		System.out.println("3. Gesamtanzahl Sitze : "
				+ w2.getSitzverteilung().getAbgeordnete().size());
		rechner13.berechneAlles(w2);
		System.out.println("4. Gesamtanzahl Sitze : "
				+ w2.getSitzverteilung().getAbgeordnete().size());
	}

	@Test
	public void simonTest() {
		Debug.setAktiv(true);
		Partei p = simu.getVerwandteWahl().getParteien().get(0);
		// Anteil wird solange erhöht bis die Anteilsverhältnisse passen, also
		// Anteil relev. Zs > Mandate
		while (!simu.bedingungErfuellt(p)) {

			simu.erhoeheRelevantenAnteil(p);
		}
	}

}

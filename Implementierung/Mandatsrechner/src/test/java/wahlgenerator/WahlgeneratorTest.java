/**
 * 
 */
package test.java.wahlgenerator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.wahlgenerator.Stimmanteile;
import main.java.wahlgenerator.Wahlgenerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.java.Debug;

/**
 * @author Simon
 * 
 */
public class WahlgeneratorTest {

	private static Bundestagswahl wahl1;

	private static ImportExportManager i;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		Debug.setAktiv(true);

		i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl1 = null;
		try {
			wahl1 = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gï¿½ltige CSV-Datei :/");
		}
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link main.java.wahlgenerator.Wahlgenerator#verteileStimmen(model.Bundestagswahl)}
	 * .
	 */
	@Test
	public final void testVerteileStimmen() {

		// Wahlgenerierung

		LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();
		// stimmAnt.add(new Stimmanteile(wahl1.getParteien().getFirst(), 2, 1));
		stimmAnt.add(new Stimmanteile(wahl1.getParteien().get(25), 100, 100));

		Wahlgenerator wg = new Wahlgenerator(wahl1, stimmAnt);

		long start = System.currentTimeMillis();

		Bundestagswahl genWahl = wg.erzeugeBTW("Name");

		System.out.println(System.currentTimeMillis() - start + "ms Laufzeit");

		System.out.println(genWahl.getDeutschland().getBundeslaender().get(7)
				.getName());
		// System.out.println(genWahl.getDeutschland().getBundeslaender().get(7).getErststimmen().get(0).getAnzahl());

		// System.out.println(wahl1.getDeutschland().getBundeslaender().get(7).getName());
		// System.out.println(wahl1.getDeutschland().getBundeslaender().get(7).getErststimmen().get(0).getAnzahl());

		genWahl = Mandatsrechner2009.getInstance().berechneSainteLague(genWahl);
		System.out.println(genWahl.getParteien().getFirst().getAnzahlMandate());
		genWahl.getParteien().getFirst().getZweitstimme().getFirst()
				.erhoeheAnzahl(200000);
		;
		genWahl = Mandatsrechner2009.getInstance().berechneSainteLague(genWahl);
		System.out.println(genWahl.getParteien().getFirst().getAnzahlMandate());

		i.exportieren("src/main/resources/importexport/3333.csv", genWahl);

	}

	@Test
	public final void verteileRestAnteileTest() {

		LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();

		for (Partei partei : wahl1.getParteien()) {
			if (partei.getName().equals("CDU")) {
				stimmAnt.add(new Stimmanteile(partei, 23, 17));
			}
			if (partei.getName().equals("SPD")) {
				stimmAnt.add(new Stimmanteile(partei, 13, 26));
			}
			if (partei.getName().equals("FDP")) {
				stimmAnt.add(new Stimmanteile(partei, 0, 3));
			}
			if (partei.getName().equals("AfD")) {
				stimmAnt.add(new Stimmanteile(partei, 5, 0));
			}
		}

		Wahlgenerator wg = new Wahlgenerator(wahl1, stimmAnt);

		wg.erzeugeBTW("Name");

		int sumErstAnteile = 0;
		int sumZweitAnteile = 0;
		for (Stimmanteile anteil : wg.getStimmanteile()) {
			sumErstAnteile += anteil.getAnteilErststimmen();
			sumZweitAnteile += anteil.getAnteilZweitstimmen();
		}

		for (Stimmanteile anteil : stimmAnt) {
			System.out
					.println(anteil.getPartei().getName()
							+ " - Erststimmenanteil: "
							+ anteil.getAnteilErststimmen()
							+ ", Zweitstimmenanteil: "
							+ anteil.getAnteilZweitstimmen());
		}

		assertTrue("Die Summe aller Erststimmenanteile ist " + sumErstAnteile
				+ " und nicht 100%!", sumErstAnteile == 100);
		assertTrue("Die Summe aller Zweitstimmenanteile ist " + sumZweitAnteile
				+ " und nicht 100%!", sumZweitAnteile == 100);
	}

	@Test
	public final void afdTest() {

		LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();

		for (Partei partei : wahl1.getParteien()) {
			if (partei.getName().equals("AfD")) {
				stimmAnt.add(new Stimmanteile(partei, 100, 100));
			}
		}

		Wahlgenerator wg = new Wahlgenerator(wahl1, stimmAnt);

		Bundestagswahl gen = wg.erzeugeBTW("Name");

		int sumErstAnteile = 0;
		int sumZweitAnteile = 0;
		for (Stimmanteile anteil : wg.getStimmanteile()) {
			sumErstAnteile += anteil.getAnteilErststimmen();
			sumZweitAnteile += anteil.getAnteilZweitstimmen();
		}

		for (Stimmanteile anteil : stimmAnt) {
			System.out
					.println(anteil.getPartei().getName()
							+ " - Erststimmenanteil: "
							+ anteil.getAnteilErststimmen()
							+ ", Zweitstimmenanteil: "
							+ anteil.getAnteilZweitstimmen());
		}

		assertTrue("Die Summe aller Erststimmenanteile ist " + sumErstAnteile
				+ " und nicht 100%!", sumErstAnteile == 100);
		assertTrue("Die Summe aller Zweitstimmenanteile ist " + sumZweitAnteile
				+ " und nicht 100%!", sumZweitAnteile == 100);

		System.out.println("--> Zweitstimmen gesamt: "
				+ gen.getDeutschland().getAnzahlZweitstimmen());
		for (Bundesland land : gen.getDeutschland().getBundeslaender()) {
			System.out.println(land.getName() + " : "
					+ land.getAnzahlZweitstimmen());
		}

		System.out.println("--> Erststimmen gesamt: "
				+ gen.getDeutschland().getAnzahlErststimmen());
		for (Bundesland land : gen.getDeutschland().getBundeslaender()) {
			System.out.println(land.getName() + " : "
					+ land.getAnzahlErststimmen());
		}

		i.exportieren(
				"src/main/resources/importexport/afd_100_prozent_test.csv", gen);
	}

	@Test
	public final void anzahlVergebeneStimmenTest() {

		Random rand = new Random();

		for (int i = 0; i < 20; i++) {
			LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();
			int anzahlParteien = wahl1.getParteien().size();

			for (int j = 0; j < 2; j++) {
				stimmAnt.add(new Stimmanteile(wahl1.getParteien().get(
						rand.nextInt(anzahlParteien)), 50, 50));
			}

			Wahlgenerator wg = new Wahlgenerator(wahl1, stimmAnt);

			Bundestagswahl gen = wg.erzeugeBTW("bla");

			// Parteien mit Stimmen
			LinkedList<Partei> relParteien = new LinkedList<>();
			for (Partei partei : gen.getParteien()) {
				for (Stimmanteile stimmanteile : stimmAnt) {
					if (partei.getName().equals(
							stimmanteile.getPartei().getName())) {
						relParteien.add(partei);
					}
				}
			}

			assertTrue("Parteienanzahl = " + relParteien.size(),
					relParteien.size() == 2);
			assertTrue(relParteien.getFirst().getZweitstimmeGesamt() == relParteien
					.getLast().getZweitstimmeGesamt());

		}

	}

}

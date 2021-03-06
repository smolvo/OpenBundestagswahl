/**
 * 
 */
package test.java.mandatsrechner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;

import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.BerichtDaten;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 *
 */
public class Mandatsrechner2009Test {

	private Mandatsrechner2009 rechner;

	private static Bundestagswahl wahl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		Mandatsrechner2009Test.wahl = Steuerung.getInstance().importieren(
				csvDateien);
	}

	private Bundestagswahl cloneWahl;

	@Test(expected = IllegalArgumentException.class)
	public void berechneDirektmandatTest1() {
		this.rechner.testBerechneDirektmandat(null);
	}

	@Test
	public void berechneDirektmandatTest2() {
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		final Kandidat gewinner = wk.getErststimmenProPartei().get(0)
				.getKandidat();
		wk.getErststimmenProPartei().get(0).setAnzahl(134650);

		this.rechner.initialisiere(this.cloneWahl);

		this.rechner.testBerechneDirektmandat(this.cloneWahl);

		assertEquals(Mandat.DIREKTMANDAT, gewinner.getMandat());
	}

	@Test
	public void berechneDirektmandatTest3() {
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		final Kandidat gewinner1 = wk.getErststimmenProPartei().get(0)
				.getKandidat();
		final Kandidat gewinner2 = wk.getErststimmenProPartei().get(1)
				.getKandidat();
		final Kandidat gewinner3 = wk.getErststimmenProPartei().get(2)
				.getKandidat();
		wk.getErststimmenProPartei().get(0).setAnzahl(65803);
		wk.getErststimmenProPartei().get(1).setAnzahl(65803);
		wk.getErststimmenProPartei().get(2).setAnzahl(65803);

		this.rechner.initialisiere(this.cloneWahl);

		this.rechner.testBerechneDirektmandat(this.cloneWahl);

		if (gewinner1.getMandat().equals(Mandat.DIREKTMANDAT)) {
			assertEquals(Mandat.KEINMANDAT, gewinner2.getMandat());
			assertEquals(Mandat.KEINMANDAT, gewinner3.getMandat());
		} else if (gewinner2.getMandat().equals(Mandat.DIREKTMANDAT)) {
			assertEquals(Mandat.KEINMANDAT, gewinner1.getMandat());
			assertEquals(Mandat.KEINMANDAT, gewinner3.getMandat());
		} else {
			assertEquals(Mandat.KEINMANDAT, gewinner1.getMandat());
			assertEquals(Mandat.KEINMANDAT, gewinner2.getMandat());
			assertEquals(Mandat.DIREKTMANDAT, gewinner3.getMandat());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void berechneRelevanteParteienTest1() {
		this.rechner.testBerechneRelevanteParteien(null);
	}

	@Test
	public void berechneRelevanteParteienTest2() {
		final Bundesland sa = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(14);
		final Bundesland rlp = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(6);

		final Partei cdu = this.cloneWahl.getParteien().get(0);

		final Partei tierschutz = this.cloneWahl.getParteien().get(8);
		// TierschutzPartei in Harz und Magdeburg gewinnen lassen
		sa.getWahlkreise().get(2).getErststimmenProPartei().get(8)
				.setAnzahl(96652);
		sa.getWahlkreise().get(3).getErststimmenProPartei().get(8)
				.setAnzahl(95457);

		final Partei rep = this.cloneWahl.getParteien().get(9);
		// Rep in Ludwigshafen/Frankenthal, Pirmasens und in der Südpfalz
		// gewinnen lassen
		rlp.getWahlkreise().get(10).getErststimmenProPartei().get(9)
				.setAnzahl(67819);
		rlp.getWahlkreise().get(13).getErststimmenProPartei().get(9)
				.setAnzahl(57788);
		rlp.getWahlkreise().get(14).getErststimmenProPartei().get(0)
				.setAnzahl(0);
		rlp.getWahlkreise().get(14).getErststimmenProPartei().get(9)
				.setAnzahl(92626);

		this.rechner.initialisiere(this.cloneWahl);
		this.rechner.testBerechneDirektmandat(this.cloneWahl);

		final LinkedList<Partei> relevante = this.rechner
				.testBerechneRelevanteParteien(this.cloneWahl);

		boolean enthaeltCDU = false;
		boolean enthaeltTS = false;
		boolean enthaeltREP = false;
		for (final Partei par : relevante) {
			if (par.equals(cdu)) {
				enthaeltCDU = true;
			} else if (par.equals(tierschutz)) {
				enthaeltTS = true;
			} else if (par.equals(rep)) {
				enthaeltREP = true;
			}
		}
		assertEquals(tierschutz, sa.getWahlkreise().get(2).getWahlkreisSieger()
				.getPartei());
		assertEquals(tierschutz, sa.getWahlkreise().get(3).getWahlkreisSieger()
				.getPartei());
		assertEquals(rep, rlp.getWahlkreise().get(10).getWahlkreisSieger()
				.getPartei());
		assertEquals(rep, rlp.getWahlkreise().get(13).getWahlkreisSieger()
				.getPartei());
		assertEquals(rep, rlp.getWahlkreise().get(14).getWahlkreisSieger()
				.getPartei());
		assertTrue(enthaeltCDU);
		assertFalse(enthaeltTS);
		assertTrue(enthaeltREP);
	}

	@Test
	public void getRelevanteZweitstimmenSummeTest() {
		final LinkedList<Partei> parteien1 = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0).getParteien();
		final LinkedList<Partei> parteien2 = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(2).getParteien();
		final Bundesland land1 = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0);
		final Bundesland land2 = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(1);

		assertEquals(1628290,
				this.rechner.getRelevanteZweitstimmenSumme(parteien1, land1));
		assertEquals(883223,
				this.rechner.getRelevanteZweitstimmenSumme(parteien1, land2));
		assertEquals(1621447,
				this.rechner.getRelevanteZweitstimmenSumme(parteien2, land1));
		assertEquals(879404,
				this.rechner.getRelevanteZweitstimmenSumme(parteien2, land2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void initialisiereTest1() {
		this.rechner.initialisiere(null);
	}

	@Test
	public void initialisiereTest2() {
		final LinkedList<Kandidat> mitgliederPartei1 = this.cloneWahl
				.getParteien().get(3).getMitglieder();
		final LinkedList<Kandidat> mitgliederPartei2 = this.cloneWahl
				.getParteien().get(0).getMitglieder();
		this.rechner.berechneSainteLague(this.cloneWahl);
		this.rechner.initialisiere(this.cloneWahl);
		final LinkedList<Kandidat> kandidaten = this.cloneWahl
				.getSitzverteilung().getAbgeordnete();
		final BerichtDaten bericht = this.cloneWahl.getSitzverteilung()
				.getBericht();

		assertEquals(0, kandidaten.size());
		assertEquals(0, bericht.getBundeslaender().size());

		for (final Kandidat kan : mitgliederPartei1) {
			assertEquals(Mandat.KEINMANDAT, kan.getMandat());
		}
		for (final Kandidat kan : mitgliederPartei2) {
			assertEquals(Mandat.KEINMANDAT, kan.getMandat());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void rundenTest1() {
		this.rechner.testRunden(-1, false);
	}

	@Test(timeout = 10)
	public void rundenTest2() {
		assertEquals(0, this.rechner.testRunden((float) 0.4, false));
		assertEquals(1, this.rechner.testRunden((float) 0.6, false));
		boolean auf = false;
		boolean ab = false;
		while (!auf || !ab) {
			if (this.rechner.testRunden((float) 0.5, true) == 0) {
				ab = true;
			} else if (this.rechner.testRunden((float) 0.5, true) == 1) {
				auf = true;
			}
		}
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = Mandatsrechner2009Test.wahl.deepCopy();
		this.rechner = Mandatsrechner2009.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
}

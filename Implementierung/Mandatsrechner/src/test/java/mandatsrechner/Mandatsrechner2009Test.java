/**
 * 
 */
package test.java.mandatsrechner;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.BerichtDaten;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Wahlkreis;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Nickes
 *
 */
public class Mandatsrechner2009Test {

	private Mandatsrechner2009 rechner;
	
	private static Bundestagswahl wahl;
	
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
		this.rechner = Mandatsrechner2009.getInstance();
	}
	
	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void initialisiereTest1() {
		rechner.initialisiere(null);
	}

	@Test
	public void initialisiereTest2() {
		LinkedList<Kandidat> mitgliederPartei1 = this.cloneWahl.getParteien().get(3).getMitglieder();
		LinkedList<Kandidat> mitgliederPartei2 = this.cloneWahl.getParteien().get(0).getMitglieder();
		rechner.initialisiere(this.cloneWahl);
		LinkedList<Kandidat> kandidaten = this.cloneWahl.getSitzverteilung().getAbgeordnete();
		BerichtDaten bericht = this.cloneWahl.getSitzverteilung().getBericht();
		
		assertEquals(0, kandidaten.size());
		assertEquals(0, bericht.getBundeslaender().size());
		
		for (Kandidat kan : mitgliederPartei1) {
			assertEquals(Mandat.KEINMANDAT, kan.getMandat());
		}
		for (Kandidat kan : mitgliederPartei2) {
			assertEquals(Mandat.KEINMANDAT, kan.getMandat());
		}
	}
	
	@Test
	public void berechneDirektmandatTest1() {
		Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0);
		Kandidat gewinner = wk.getErststimmenProPartei().get(0).getKandidat();
		wk.getErststimmenProPartei().get(0).setAnzahl(Integer.MAX_VALUE);
		
		rechner.initialisiere(this.cloneWahl);
		
		this.rechner.testBerechneDirektmandat(this.cloneWahl);
		
		assertEquals(Mandat.DIREKTMANDAT, gewinner.getMandat());
	}
	
	@Test
	public void berechneDirektmandatTest2() {
		Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0);
		Kandidat gewinner1 = wk.getErststimmenProPartei().get(0).getKandidat();
		Kandidat gewinner2 = wk.getErststimmenProPartei().get(1).getKandidat();
		Kandidat gewinner3 = wk.getErststimmenProPartei().get(2).getKandidat();
		wk.getErststimmenProPartei().get(0).setAnzahl(Integer.MAX_VALUE);
		wk.getErststimmenProPartei().get(1).setAnzahl(Integer.MAX_VALUE);
		wk.getErststimmenProPartei().get(2).setAnzahl(Integer.MAX_VALUE);
		
		rechner.initialisiere(this.cloneWahl);
		
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
}

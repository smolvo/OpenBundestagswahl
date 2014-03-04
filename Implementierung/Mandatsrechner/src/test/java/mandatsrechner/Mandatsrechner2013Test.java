/**
 * 
 */
package test.java.mandatsrechner;

import static org.junit.Assert.*;

import java.io.File;

import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 *
 */
public class Mandatsrechner2013Test {

	private Mandatsrechner2013 rechner;

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
		this.rechner = Mandatsrechner2013.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
	
	@Test
	public void berechneTest1() {
		int spd = 0, cdu = 0, csu = 0, gruene = 0, linke = 0, sonst = 0;
		this.rechner.berechne(this.cloneWahl);
		for (Kandidat kandidat : this.cloneWahl.getSitzverteilung().getAbgeordnete()) {
				switch (kandidat.getPartei().getName()) {
					case "CDU":
						cdu++;
						break;
					case "SPD":
						spd++;
						break;
					case "CSU":
						csu++;
						break;
					case "GRÜNE":
						gruene++;
						break;
					case "DIE LINKE":
						linke++;
						break;
					default:
						fail("Ungültige Partei");
			}
		}	
		assertEquals(193, spd);
		assertEquals(63, gruene);
		assertEquals(64, linke);
		assertEquals(56, csu);
		assertEquals(255, cdu);
	}
	
}

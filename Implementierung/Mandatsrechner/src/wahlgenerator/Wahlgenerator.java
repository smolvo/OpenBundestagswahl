package wahlgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.DebugGraphics;

import test.Debug;
import model.Bundestagswahl;
import model.Erststimme;
import model.Partei;
import model.Wahlkreis;
import model.Zweitstimme;

/**
 * 
 */
public class Wahlgenerator extends AbstrakterWahlgenerator {

	/**
	 * Erzeugt einen neuen Wahlgenerator.
	 * @param basisWahl Bundestagswahl mit Basisdaten (Gebiete, Kandidaten, usw...)
	 * @param stimmanteile Eine Liste von Stimmanteilen auf Basis derer die Stimmen verteilt werden.
	 */
	public Wahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile) {
		// TODO check das jede Partei in Stimmanteile auch in btw vorkommt
		// umgekehrt muss nicht sein
		super(basisWahl, stimmanteile);
	}
	
	/**
	 * Generiert eine Bundestagswahl auf basis der BTW des basisWahl Attributs und der Liste der Stimmanteile.
	 * @return eine generierte Bundestagswahl
	 */
	public Bundestagswahl erzeugeBTW() {
		
		Bundestagswahl clone = null;
		
		// erstelle tiefe Kopie
		try {
			clone = this.getBasisWahl().deepCopy();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// verteile Stimmen zufällig auf die Gebiete, Parteien und Kandidaten
		this.verteileStimmen(clone);
		
		return clone;
	}
	
	@Override
	public void verteileStimmen(Bundestagswahl btw) {
		
		ArrayList<Wahlkreis> alleWahlkreise = btw.getDeutschland().getWahlkreise();
		int anzahlWahlkreise = alleWahlkreise.size();
		
		// durchlaufe alle Wahlkreise
		for (Wahlkreis wk : alleWahlkreise) {
			// durchlaufe alle Erststimmen
			for (Erststimme erst : wk.getErststimmen()) {
				// Anzahl auf 0 setzen
				erst.setAnzahl(0);
			}
			// durchlaufe alle Zweitstimmen
			for (Zweitstimme zweit : wk.getZweitstimmen()) {
				// Anzahl auf 0 setzen
				zweit.setAnzahl(0);
			}
		}
		
		// durchlaufe Parteien für die Stimmen verteilt werden sollen
		for (Stimmanteile sa : this.getStimmanteile()) {
			Partei saPartei = sa.getPartei();
			Partei partei = null;
			
			// die aktuelle Partei
			for (Partei part : btw.getParteien()) {
				if (part.getName().equals(saPartei.getName())) {
					partei = part;
				}
			}
			
			if (partei == null) {
				throw new NullPointerException("Partei ist null! Diese Exception sollte nicht vorkommen!");
			}
			
			int anzahlErststimmen = (int) (this.getAnzahlErststimmen() * this.getAnteileVonPartei(partei).getAnteilErststimmen());
			int anzahlZweitstimmen = (int) (this.getAnzahlZweitstimmen() * this.getAnteileVonPartei(partei).getAnteilZweitstimmen());
			
			Debug.print(partei.getName() + ", anzahlErststimmen: " + anzahlErststimmen + ", anzahlZweitstimmen: " + anzahlZweitstimmen);
			
			Random rand = new Random();
			
			
			Wahlkreis wk = null;
			int vergebeneErst = 0;
			int vergebeneZweit = 0;
			int stimmzahl;
			
			while (vergebeneErst < anzahlErststimmen || vergebeneZweit < anzahlZweitstimmen) {
				
				if (vergebeneErst < anzahlErststimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));
					
					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math.min((anzahlErststimmen - vergebeneErst), (wk.getWahlberechtigte() - wk.getErststimmeGesamt()));
					
					// Wenn maximal mögliche Stimmzahl positiv, dann wähle eine
					// zufällig eine in dem Intervall [1,max]
					if (stimmzahl != 0) {
						stimmzahl = rand.nextInt(stimmzahl) + 1;
					}
					
					// zugehöriges Erststimmenobjekt finden
					Erststimme erst = wk.getErststimme(partei);
					
					// Stimmzahl erhöhen, falls Erststimmen-Objekt gefunden
					if (erst != null) {
						erst.erhoeheAnzahl(stimmzahl);
						vergebeneErst += stimmzahl;
					} else {
						System.out.println("Erststimme ist null!, " + partei.getName() + " : " + wk.getName());
					}
				}
				
				if (vergebeneZweit < anzahlZweitstimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));
					
					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math.min((anzahlZweitstimmen - vergebeneZweit), (wk.getWahlberechtigte() - wk.getZweitstimmeGesamt()));
					
					// Wenn maximal mögliche Stimmzahl positiv, dann wähle eine
					// zufällig eine in dem Intervall [1,max]
					if (stimmzahl != 0) {
						stimmzahl = rand.nextInt(stimmzahl) + 1;
					}
					
					// zugehöriges Erststimmenobjekt finden
					Zweitstimme zweit = wk.getZweitstimme(partei);
					
					// Stimmzahl erhöhen, falls Erststimmen-Objekt gefunden
					if (zweit != null) {
						zweit.erhoeheAnzahl(stimmzahl);
						vergebeneZweit += stimmzahl;
					} else {
						System.out.println("Zweitstimme ist null!, " + partei.getName() + " : " + wk.getName());
					}
				}
				
			}
			
		}
		
	}
	
	/**
	 * 
	 * @param partei
	 * @return
	 */
	private Stimmanteile getAnteileVonPartei(Partei partei) {
		for (Stimmanteile stimmanteile : this.getStimmanteile()) {
			if (stimmanteile.getPartei().getName().equals(partei.getName())) {
				return stimmanteile;
			}
		}
		throw new IllegalArgumentException("Die gegebene Partei ist in der Liste der Stimmanteile nicht vorhanden!");
	}
	
}

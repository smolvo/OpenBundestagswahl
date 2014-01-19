package wahlgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Bundesland;
import model.Bundestagswahl;
import model.Erststimme;
import model.Partei;
import model.Wahlkreis;
import model.Zweitstimme;

/**
 * 
 */
public class Wahlgenerator extends AbstrakterWahlgenerator {

	public Wahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile) {
		// TODO check das jede Partei in Stimmanteile auch in btw vorkommt
		// umgekehrt muss nicht sein
		super(basisWahl, stimmanteile);
	}
	
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
		
		// durchlaufe alle Bundesländer
		for (Bundesland bl : btw.getDeutschland().getBundeslaender()) {
			// durchlaufe alle Wahlkreise
			for (Wahlkreis wk : bl.getWahlkreise()) {
				
				/* Durchlaufe alle Erststimmen-Objekte dieses Wahlkreises.
				 * Das ist pro Kandidat in diesem Wahlkreis eins.
				 */
				for (Erststimme erst : wk.getErststimmen()) {
					// erstmal die Anzahl auf 0 setzen
					erst.setAnzahl(0);
				}
				
				/* Durchlaufe alle Zweitstimmen-Objekte dieses Wahlkreises.
				 * Das ist pro Partei in diesem Wahlkreis eins.
				 */
				for (Zweitstimme zweit : wk.getZweitstimmen()) {
					// erstmal die Anzahl auf 0 setzen
					zweit.setAnzahl(0);
				}
				
			}
		}
		
		
		for (Partei partei : btw.getParteien()) {
			
			int anzahlErststimmen = (int) (this.getAnzahlErststimmen() * this.getAnteileVonPartei(partei).getAnteilErststimmen());
			int anzahlZweitstimmen = (int) (this.getAnzahlZweitstimmen() * this.getAnteileVonPartei(partei).getAnteilZweitstimmen());
			
			ArrayList<Wahlkreis> alleWahlkreise = btw.getDeutschland().getWahlkreise();
			int anzahlWahlkreise = alleWahlkreise.size();
			
			Random rand = new Random();
			
			
			for (int i = 0; i < anzahlZweitstimmen; i++) {
				// TODO check anzahl wahlberechtigter
				alleWahlkreise.get(rand.nextInt(anzahlWahlkreise)).getZweitstimme(partei).erhoeheAnzahl(1);
				
				// es kann hier theoretisch vorkommen, dass CSU in einem anderen BL als Bayern Stimmen erhält und CDU in Bayern.
				// Aber das haben wir ja nicht ausgeschlossen
			}
			
			//TODO Erststimmen
			
		}
		
	}
	
	/**
	 * 
	 * @param partei
	 * @return
	 */
	private Stimmanteile getAnteileVonPartei(Partei partei) {
		
		for (Stimmanteile stimmanteile : this.getStimmanteile()) {
			if (stimmanteile.getPartei().getName() == partei.getName()) {
				return stimmanteile;
			}
		}
		
		throw new IllegalArgumentException("Die gegebene Partei ist in der Liste der Stimmanteile nicht vorhanden!");
	}
	
}

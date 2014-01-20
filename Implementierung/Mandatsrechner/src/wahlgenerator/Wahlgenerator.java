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
			
			System.out.println("Bundesland: " + bl.getName());
			
			// durchlaufe alle Wahlkreise
			for (Wahlkreis wk : bl.getWahlkreise()) {
				
				System.out.println("... Wahlkreis: " + wk.getName());
				
				/* Durchlaufe alle Erststimmen-Objekte dieses Wahlkreises.
				 * Das ist pro Kandidat in diesem Wahlkreis eins.
				 */
				for (Erststimme erst : wk.getErststimmen()) {
					//System.out.println("... ... " + erst.getKandidat().getName() + " - Erststimmen: " + erst.getAnzahl());
					// erstmal die Anzahl auf 0 setzen
					erst.setAnzahl(0);
				}
				
				/* Durchlaufe alle Zweitstimmen-Objekte dieses Wahlkreises.
				 * Das ist pro Partei in diesem Wahlkreis eins.
				 */
				for (Zweitstimme zweit : wk.getZweitstimmen()) {
					//System.out.println("... ... " + zweit.getPartei().getName() + " - Zweitstimmen: " + zweit.getAnzahl());
					// erstmal die Anzahl auf 0 setzen
					zweit.setAnzahl(0);
					
				}
				
			}
		}
		
		
		//for (Partei partei : btw.getParteien()) {
		for (Stimmanteile sa : this.getStimmanteile()) {
			
			Partei partei = sa.getPartei();
			
			int anzahlErststimmen = (int) (this.getAnzahlErststimmen() * this.getAnteileVonPartei(partei).getAnteilErststimmen());
			int anzahlZweitstimmen = (int) (this.getAnzahlZweitstimmen() * this.getAnteileVonPartei(partei).getAnteilZweitstimmen());
			
			System.out.println("\n" + partei.getName());
			System.out.println("anzahlErststimmen: " + anzahlErststimmen + "\nanzahlZweitstimmen: " + anzahlZweitstimmen);
			
			ArrayList<Wahlkreis> alleWahlkreise = btw.getDeutschland().getWahlkreise();
			int anzahlWahlkreise = alleWahlkreise.size();
			
			Random rand = new Random();
			
			for (int i = 0; i < anzahlZweitstimmen; i++) {
				// Wahlkreis zufällig wählen
				Wahlkreis zufaelligerWK = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));
				//System.out.println("Zufälliger Wahlkreis: " + zufaelligerWK.getName());
				//System.out.println(zufaelligerWK.getZweitstimmeGesamt());
				
				// Check ob Anzahl der Wahlberechtigten nicht überschritten wird
				if (zufaelligerWK.getZweitstimmeGesamt() < zufaelligerWK.getWahlberechtigte()) {
					// man könnte hier statt einer Stimme eine zufällige Anzahl von Stimmen innerhalb eines Intervalls vergeben
					
					//zufaelligerWK.getZweitstimme(partei).erhoeheAnzahl(1);
					for (Zweitstimme zweit : zufaelligerWK.getZweitstimmen()) {
						if (zweit.getPartei().getName() == partei.getName()) {
							zweit.erhoeheAnzahl(1);
						}
					}
					
				} else {
					i--;
				}
				// es kann hier theoretisch vorkommen, dass CSU in einem anderen BL als Bayern Stimmen erhält und CDU in Bayern.
				// Aber das haben wir ja nicht ausgeschlossen
			}
			
			for (int i = 0; i < anzahlErststimmen; i++) {
				// Wahlkreis zufällig wählen
				Wahlkreis zufaelligerWK2 = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));
				
				// Check ob Anzahl der Wahlberechtigten nicht überschritten wird
				if (zufaelligerWK2.getErststimmeGesamt() < zufaelligerWK2.getWahlberechtigte()) {
					// man könnte hier statt einer Stimme eine zufällige Anzahl von Stimmen innerhalb eines Intervalls vergeben
					for (Erststimme erst : zufaelligerWK2.getErststimmen()) {
						if (erst.getKandidat().getPartei() != null && erst.getKandidat().getPartei().getName() == partei.getName()) {
							erst.erhoeheAnzahl(1);
						}
					}
				} else {
					i--;
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
			if (stimmanteile.getPartei().getName() == partei.getName()) {
				return stimmanteile;
			}
		}
		throw new IllegalArgumentException("Die gegebene Partei ist in der Liste der Stimmanteile nicht vorhanden!");
	}
	
}

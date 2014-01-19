package wahlgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Bundesland;
import model.Bundestagswahl;
import model.Erststimme;
import model.Wahlkreis;
import model.Zweitstimme;

/**
 * 
 */
public class Wahlgenerator extends AbstrakterWahlgenerator {

	public Wahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile) {
		super(basisWahl, stimmanteile);
	}
	
	public Bundestagswahl erzeugeBTW() {
		// erstelle tiefe Kopie
		try {
			this.setBasisWahl(this.getBasisWahl().deepCopy());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// verteile Stimmen zufällig auf die Gebiete, Parteien und Kandidaten
		this.verteileStimmen();
		
		return this.getBasisWahl();
	}
	
	@Override
	public void verteileStimmen() {
		
		
		// durchlaufe alle Bundesländer
		for (Bundesland bl : this.getBasisWahl().getDeutschland().getBundeslaender()) {
			// durchlaufe alle Wahlkreise
			for (Wahlkreis wk : bl.getWahlkreise()) {
				
				/* Durchlaufe alle Erststimmen-Objekte dieses Wahlkreises.
				 * Das ist pro Kandidat in diesem Wahlkreis eins.
				 */
				for (Erststimme erst : wk.getErststimmen()) {
					// erstmal die Anzahl auf 0 setzen
					erst.setAnzahl(0);
					/*
					 * TODO: geeignete Listen erstellen und Stimmen randomisiert verteilen
					 */
				}
				
				/* Durchlaufe alle Zweitstimmen-Objekte dieses Wahlkreises.
				 * Das ist pro Partei in diesem Wahlkreis eins.
				 */
				for (Zweitstimme zweit : wk.getZweitstimmen()) {
					// erstmal die Anzahl auf 0 setzen
					zweit.setAnzahl(0);
					/*
					 * TODO: geeignete Listen erstellen und Stimmen randomisiert verteilen
					 */
				}
				
			}
		}
		
	}
	
}

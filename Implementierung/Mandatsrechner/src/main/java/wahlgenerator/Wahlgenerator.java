package main.java.wahlgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.DebugGraphics;

import test.java.Debug;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

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
		
		this.verteileRestAnteile();
		
		Bundestagswahl clone = null;
		
		// erstelle tiefe Kopie
		try {
			clone = this.getBasisWahl().deepCopy();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		float sumErstAnteil = 0;
		float sumZweitAnteil = 0;
		
		for (Stimmanteile sa : this.getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
			Debug.print("Partei: " + sa.getPartei().getName() + ", ErstAnteil: " + sa.getAnteilErststimmen() + ", ZweitAnteil " + sa.getAnteilZweitstimmen());
		}
		Debug.print("===> sumErstAnteil: " + sumErstAnteil + ", sumZweitAnteil: " + sumZweitAnteil);
		
		
		
		
		
		// verteile Stimmen zufällig auf die Gebiete, Parteien und Kandidaten
		this.verteileStimmen(clone);
		
		return clone;
	}
	
	private void verteileRestAnteile() {
		
		float sumErstAnteil = 0;
		float sumZweitAnteil = 0;
		
		for (Stimmanteile sa : this.getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
		}
		
		float restErstAnteil = 1 - sumErstAnteil;
		float restZweitAnteil = 1- sumZweitAnteil;
		Debug.print("\nrestErstAnteil: " + restErstAnteil + ", restZweitAnteil: " + restZweitAnteil + "\n");
		
		ArrayList<Partei> partOhneAnteile = this.getParteienOhneAnteile();
		Random rand = new Random();
		
		while (restErstAnteil > 0 || restZweitAnteil > 0) {
			// wähle zufällige Partei aus der Liste der Parteien ohne Anteile
			Partei partei = partOhneAnteile.get(rand.nextInt(partOhneAnteile.size()));
			
			// Prüfe ob für diese Partei in dieser Schleife schonmal Anteile erzeugt wurden und wähle diese aus
			Stimmanteile anteil = this.getAnteileVonPartei(partei);
			if (anteil == null) {
				anteil = new Stimmanteile(partei, 0, 0);
				this.getStimmanteile().add(anteil);
			}
			
			// füge eine zufällige Anzahl von Erst- und Zweit-Anteilen hinzu
			float anteilErst = 0;
			float anteilZweit = 0;
			if (restErstAnteil < 0.01) {
				anteilErst = restErstAnteil;
			} else {
				anteilErst = (rand.nextInt(100) * restErstAnteil) / 100;
			}
			if (restZweitAnteil < 0.01) {
				anteilZweit = restZweitAnteil;
			} else {
				anteilZweit = (rand.nextInt(100) * restZweitAnteil) / 100;
			}
			
			anteil.setAnteilErststimmen(anteilErst);
			anteil.setAnteilZweitstimmen(anteilZweit);
			restErstAnteil -= anteilErst;
			restZweitAnteil -= anteilZweit;
		}
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
	
	private boolean hatParteiStimmanteile(Partei partei) {
		for (Stimmanteile sa : this.getStimmanteile()) {
			if (sa.getPartei().equals(partei)) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Partei> getParteienOhneAnteile() {
		ArrayList<Partei> parteien = new ArrayList<>();
		for (Partei partei : this.getBasisWahl().getParteien()) {
			if (!this.hatParteiStimmanteile(partei)) {
				parteien.add(partei);
			}
		}
		return parteien;
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
		return null;
	}
	
}

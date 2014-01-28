package main.java.wahlgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.java.Debug;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse dient zum gernerieren von Bundestagswahlen.
 */
public class Wahlgenerator extends AbstrakterWahlgenerator {

	/**
	 * Erzeugt einen neuen Wahlgenerator.
	 * @param basisWahl Bundestagswahl mit Basisdaten (Gebiete, Kandidaten, usw...)
	 * @param stimmanteile Eine Liste von Stimmanteilen auf Basis derer die Stimmen verteilt werden.
	 */
	public Wahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile) {
		
		super(basisWahl, stimmanteile);
		
		//this.getBasisWahl().setSitzverteilung(new Sitzverteilung(new LinkedList<Kandidat>(), new BerichtDaten()));
		
		int summeErst = 0;
		int summeZweit = 0;
		for (Stimmanteile sa : stimmanteile) {
			if (!basisWahl.getParteien().contains(sa.getPartei())) {
				throw new IllegalArgumentException("Partei \"" + sa.getPartei().getName() + "\" existiert in der BasisWahl nicht!");
			}
			summeErst += sa.getAnteilErststimmen();
			summeZweit += sa.getAnteilZweitstimmen();
		}
		
		if (summeErst > 100 || summeZweit > 100) {
			throw new IllegalArgumentException("Die Summe der Erst- und/oder Zweitstimmenanteile sind größer als 100!");
		} else if (summeZweit < 0 || summeZweit < 0) {
			throw new IllegalArgumentException("Die Summe der Erst- und/oder Zweitstimmenanteile sind negativ!");
		}
	}
	
	/**
	 * Generiert eine Bundestagswahl auf basis der BTW des basisWahl Attributs und der Liste der Stimmanteile.
	 * @return eine generierte Bundestagswahl
	 */
	public Bundestagswahl erzeugeBTW(String name) {
		
		if (name == null) {
			throw new IllegalArgumentException("Der Parameter \"name\" ist null!");
		}
		
		this.verteileRestAnteile();
		
		Bundestagswahl clone = null;
		
		// erstelle tiefe Kopie
		try {
			clone = this.getBasisWahl().deepCopy();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Setze die komplette Auswertung auf NULL
		Mandatsrechner2009.getInstance().initialisiere(clone);
		
		// Name der neuen Wahl setzen
		clone.setName(name);
		
		int sumErstAnteil = 0;
		int sumZweitAnteil = 0;
		
		for (Stimmanteile sa : this.getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
			Debug.print("Partei: " + sa.getPartei().getName() + ", ErstAnteil: " + sa.getAnteilErststimmen() + "%, ZweitAnteil " + sa.getAnteilZweitstimmen() + "%");
		}
		Debug.print("===> sumErstAnteil: " + sumErstAnteil + "%, sumZweitAnteil: " + sumZweitAnteil + "%");
		
		
		// verteile Stimmen zufällig auf die Gebiete, Parteien und Kandidaten
		this.verteileStimmen(clone);
		
		return clone;
	}
	
	/**
	 * Verteilt die übrigen Anteile der Erst- und Zweitstimmen auf zufällige Parteien.
	 */
	private void verteileRestAnteile() {
		
		int sumErstAnteil = 0;
		int sumZweitAnteil = 0;
		
		for (Stimmanteile sa : this.getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
		}
		
		int restErstAnteil = 100 - sumErstAnteil;
		int restZweitAnteil = 100 - sumZweitAnteil;
		Debug.print("restErstAnteil: " + restErstAnteil + "%, restZweitAnteil: " + restZweitAnteil + "%\n");
		
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
			//Debug.print("übrig: restErstAnteil: " + restErstAnteil + ", restZweitAnteil: " + restZweitAnteil);
			
			if (restErstAnteil > 0) {
				int anteilErst = rand.nextInt(restErstAnteil) + 1;
				anteil.setAnteilErststimmen(anteil.getAnteilErststimmen() + anteilErst);
				restErstAnteil -= anteilErst;
			}
			
			if (restZweitAnteil > 0) {
				int anteilZweit = rand.nextInt(restZweitAnteil) + 1;
				anteil.setAnteilZweitstimmen(anteil.getAnteilZweitstimmen() + anteilZweit);
				restZweitAnteil -= anteilZweit;
			}
			
		}
		Debug.print("fertig...");
	}
	
	/**
	 * Verteilt die Stimmen der Parteien für diejenigen Stimmanteile angegeben sind 
	 * auf zufällige Wahlkreise. Die Bundesweiten Stimmanteile der Parteien bleiben dabei erhalten.
	 * Bundesland- und Wahlkreisweit werden die Stimmen zufällig verteilt. 
	 */
	@Override
	public void verteileStimmen(Bundestagswahl btw) {
		
		ArrayList<Wahlkreis> alleWahlkreise = btw.getDeutschland().getWahlkreise();
		int anzahlWahlkreise = alleWahlkreise.size();
		
		// durchlaufe alle Wahlkreise
		for (Wahlkreis wk : alleWahlkreise) {
			// durchlaufe alle Erststimmen
			for (Erststimme erst : wk.getErststimmenProPartei()) {
				// Anzahl auf 0 setzen
				erst.setAnzahl(0);
				
				if (erst.getKandidat().getPartei() == null) {
					Debug.print("Wahlkreis: " + wk.getName() + ", Kandidat: " + erst.getKandidat().getName() + ", Partei: NULL");
				}
				
				//if (erst.getKandidat().equals(ImportExportManager.unbekannterKandidat)) {
				//	erst = null;
				//}
				//erst = null;
			}
			// durchlaufe alle Zweitstimmen
			for (Zweitstimme zweit : wk.getZweitstimmenProPartei()) {
				// Anzahl auf 0 setzen
				zweit.setAnzahl(0);
				//zweit = null;
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
				throw new NullPointerException("Partei ist null! Das sollte eigentlich nicht vorkommen ;-)!");
			}
			
			
			int anzahlErststimmen = (int)(this.getAnzahlErststimmen() * (this.getAnteileVonPartei(partei).getAnteilErststimmen() / 100.0));
			int anzahlZweitstimmen = (int)(this.getAnzahlZweitstimmen() * (this.getAnteileVonPartei(partei).getAnteilZweitstimmen() / 100.0));
			
			Debug.print(partei.getName() 
					+ ", anzahlErststimmen: " + anzahlErststimmen + " (" + this.getAnteileVonPartei(partei).getAnteilErststimmen() 
					+ "%), anzahlZweitstimmen: " + anzahlZweitstimmen + " (" + this.getAnteileVonPartei(partei).getAnteilZweitstimmen() +"%)");
			
			Random rand = new Random();
			
			
			Wahlkreis wk = null;
			int vergebeneErst = 0;
			int vergebeneZweit = 0;
			int stimmzahl;
			
			while (vergebeneErst < anzahlErststimmen || vergebeneZweit < anzahlZweitstimmen) {
				
				if (vergebeneErst < anzahlErststimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));
					
					
					if (wk.getErststimmenProPartei().size() != 35) {
						System.out.println("Anzahl Erstimmen: " + wk.getErststimmenProPartei().size());
					}
					
					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math.min((anzahlErststimmen - vergebeneErst), (wk.getWahlberechtigte() - wk.getAnzahlErststimmen()));
					
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
						//System.out.println("Erststimme ist null!, " + partei.getName() + " : " + wk.getName());
						//Kandidat kan = new Kandidat(Mandat.KEINMANDAT, partei, erst);
						Kandidat kan = new Kandidat("unbekannt", "-", 0, Mandat.KEINMANDAT, partei);
						
						erst = new Erststimme(stimmzahl, wk, kan);
						//erst.setKandidat(kan);
						
						wk.getErststimmenProPartei().add(erst);
						vergebeneErst += stimmzahl;
					}
				}
				
				if (vergebeneZweit < anzahlZweitstimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));
					
					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math.min((anzahlZweitstimmen - vergebeneZweit), (wk.getWahlberechtigte() - wk.getAnzahlZweitstimmen()));
					
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
						//wk.getZweitstimmen().add(new Zweitstimme(stimmzahl, wk, partei));
					}
					//vergebeneZweit += stimmzahl;
				}
				
			}
			
		}
		
	}
	
	/**
	 * Prüft ob für die gegebene Partei Stimmanteile gegeben sind.
	 * @param partei Die Partei für diejenige geprüft werden soll ob Stimmanteile gegeben sind.
	 * @return Ob für die gegebene Partei Stimmanteile gegeben sind.
	 */
	private boolean hatParteiStimmanteile(Partei partei) {
		if (partei == null) {
			throw new IllegalArgumentException("Der Parameter \"partei\" ist null!");
		}
		for (Stimmanteile sa : this.getStimmanteile()) {
			if (sa.getPartei().equals(partei)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gibt eine Liste aller Parteien zurück, für diejenigen keine
	 * @return
	 */
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

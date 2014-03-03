package main.java.wahlgenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import test.java.Debug;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Mit dieser Klasse können Bundestagswahlen anhand gegebener
 * Stimmanteile auf Bundesebene generiert werden.
 */
public class Wahlgenerator {
	
	/** Die Basiswahl mit relevanten Strukturdaten. */
	private Bundestagswahl basisWahl;

	/** Die Liste der Stimmanteile pro Partei. */
	private List<Stimmanteile> stimmanteile;

	/** Die Anzahl der Erststimmen aller Wahlberechtigten. */
	private Integer anzahlErststimmen;

	/** Die Anzahl der Zweitstimmen aller Wahlberechtigten. */
	private Integer anzahlZweitstimmen;

	
	/**
	 * Erzeugt einen neuen Wahlgenerator.
	 * 
	 * @param basisWahl
	 *            Bundestagswahl mit Basisdaten (Gebiete, Kandidaten, usw...)
	 * @param stimmanteile
	 *            Eine Liste von Stimmanteilen auf Basis derer die Stimmen
	 *            verteilt werden.
	 */
	public Wahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile) {

		Debug.setLevel(5);
		
		this.setBasisWahl(basisWahl);
		this.setStimmanteile(stimmanteile);
		
		berechneGesamtanzahlStimmen();

		/*
		 * Prüft ob die gegebenen Stimmanteile gültig sind.
		 */
		int summeErst = 0;
		int summeZweit = 0;
		for (Stimmanteile sa : stimmanteile) {
			if (!basisWahl.getParteien().contains(sa.getPartei())) {
				throw new IllegalArgumentException("Partei \""
						+ sa.getPartei().getName()
						+ "\" existiert in der BasisWahl nicht!");
			}
			summeErst += sa.getAnteilErststimmen();
			summeZweit += sa.getAnteilZweitstimmen();
		}
		if (summeErst > 100 || summeZweit > 100) {
			throw new IllegalArgumentException(
					"Die Summe der Erst- und/oder Zweitstimmenanteile sind größer als 100!");
		}
		if (summeErst < 0 || summeZweit < 0) {
			throw new IllegalArgumentException(
					"Die Summe der Erst- und/oder Zweitstimmenanteile sind negativ!");
		}
		
		/*
		 * Prüft, ob eine Partei in den Stimmanteilen mehr als einmal vorkommt
		 */
		for (Stimmanteile sa1 : stimmanteile) {
			int count = 0;
			for (Stimmanteile sa2 : stimmanteile) {
				if (sa1.getPartei().getName().equals(sa2.getPartei().getName())) {
					count++;
				}
			}
			if (count != 1) {
				throw new IllegalArgumentException("Die Partei " + sa1.getPartei().getName()
						+ " kommt " + count + " mal in der Liste der Stimmanteile vor!");
			}
		}
	}
	
	/**
	 * Bestimmt anzahlZweitstimmen und anzahlErststimmen
	 */
	private void berechneGesamtanzahlStimmen() {
		this.setAnzahlErststimmen(this.basisWahl.getDeutschland().getAnzahlErststimmen());
		this.setAnzahlZweitstimmen(this.basisWahl.getDeutschland().getAnzahlZweitstimmen());
	}

	/**
	 * Generiert eine Bundestagswahl auf basis der BTW des basisWahl Attributs
	 * und der Liste der Stimmanteile.
	 * 
	 * @param name
	 *            der name der BTW
	 * @return eine generierte Bundestagswahl
	 */
	public Bundestagswahl erzeugeBTW(String name) {

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					"Der Parameter \"name\" ist null oder ein leerer String!");
		}

		// verteile restliche Anteile sofern nicht jeweils 100% Erst- und Zweitstimmen vergeben sind
		this.verteileRestAnteile();

		// erstelle eine tiefe Kopie der Basiswahl als Grundlage
		Bundestagswahl clone = this.getBasisWahl().deepCopy();

		// Setze die komplette Auswertung auf NULL
		Mandatsrechner2009.getInstance().initialisiere(clone);
		
		// Name der neuen Wahl setzen
		clone.setName(name);
		
		// Debug Ausgabe der kompletten Anteile pro Partei
		int sumErstAnteil = 0;
		int sumZweitAnteil = 0;
		for (Stimmanteile sa : this.getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
			Debug.print("Partei: " + sa.getPartei().getName()
					+ ", ErstAnteil: " + sa.getAnteilErststimmen()
					+ "%, ZweitAnteil " + sa.getAnteilZweitstimmen() + "%", 4);
		}
		Debug.print("===> sumErstAnteil: " + sumErstAnteil + "%, sumZweitAnteil: " + sumZweitAnteil + "%", 4);

		// verteile Stimmen zufällig auf die Gebiete, Parteien und Kandidaten
		this.verteileStimmen(clone);

		Debug.print("Wahlgenerierung fertig!", 3);
		Debug.setLevel(6);
		
		// Gebe die generierte Wahl zurück
		return clone;
	}

	/**
	 * Verteilt die übrigen Anteile der Erst- und Zweitstimmen auf zufällige
	 * Parteien.
	 */
	private void verteileRestAnteile() {

		/*
		 * berechne die restlichen Anteile, die Parteien zugewiesen werden sollen
		 */
		int sumErstAnteil = 0;
		int sumZweitAnteil = 0;

		for (Stimmanteile sa : this.getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
		}

		int restErstAnteil = 100 - sumErstAnteil;
		int restZweitAnteil = 100 - sumZweitAnteil;
		Debug.print("restErstAnteil: " + restErstAnteil
				+ "%, restZweitAnteil: " + restZweitAnteil + "%\n", 4);

		
		/*
		 * Verteile die restlichen Anteile auf zufällige Parteien
		 */
		ArrayList<Partei> partOhneAnteile = this.getParteienOhneAnteile();
		Random rand = new Random();

		while (restErstAnteil > 0 || restZweitAnteil > 0) {
			// wähle zufällige Partei aus der Liste der Parteien ohne Anteile
			Partei partei = partOhneAnteile.get(rand.nextInt(partOhneAnteile.size()));

			// Prüfe ob für diese Partei in dieser Schleife schonmal Anteile
			// erzeugt wurden und wähle diese aus
			Stimmanteile anteil = this.getAnteileVonPartei(partei);
			if (anteil == null) {
				anteil = new Stimmanteile(partei, 0, 0);
				this.getStimmanteile().add(anteil);
			}

			// füge eine zufällige Anzahl von Erst- und Zweit-Anteilen hinzu
			// Debug.print("übrig: restErstAnteil: " + restErstAnteil +
			// ", restZweitAnteil: " + restZweitAnteil);

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
	}

	/**
	 * Verteilt die Stimmen der Parteien für diejenigen Stimmanteile angegeben
	 * sind auf zufufällige Wahlkreise. Die Bundesweiten Stimmanteile der Parteien
	 * bleiben dabei erhalten. Bundesland- und Wahlkreisweit werden die Stimmen
	 * zufällig verteilt.
	 * 
	 * @param btw
	 *            die BTW auf die verteilt werden soll
	 */
	private void verteileStimmen(Bundestagswahl btw) {

		ArrayList<Wahlkreis> alleWahlkreise = btw.getDeutschland().getWahlkreise();
		int anzahlWahlkreise = alleWahlkreise.size();

		// durchlaufe alle Wahlkreise
		for (Wahlkreis wk : alleWahlkreise) {
			// durchlaufe alle Erststimmen
			for (Erststimme erst : wk.getErststimmenProPartei()) {
				if (erst.getKandidat().getPartei() == null) {
					Debug.print("Wahlkreis: " + wk.getName() + ", Kandidat: " + erst.getKandidat().getName() + ", Partei: NULL", 4);
				}
				if (this.hatParteiStimmanteile(erst.getKandidat().getPartei())
						&& this.getAnteileVonPartei(erst.getKandidat().getPartei()).getAnteilErststimmen() > 0) {
					erst.setAnzahl(1);
				} else {
					erst.setAnzahl(0);
				}
			}
			// durchlaufe alle Zweitstimmen
			for (Zweitstimme zweit : wk.getZweitstimmenProPartei()) {
				if (this.hatParteiStimmanteile(zweit.getPartei())
						&& this.getAnteileVonPartei(zweit.getPartei()).getAnteilZweitstimmen() > 0) {
					zweit.setAnzahl(1);
				} else {
					zweit.setAnzahl(0);
				}
			}
		}
		
		for (Wahlkreis wahlkreis : alleWahlkreise) {
			if (wahlkreis.getAnzahlErststimmen() < 1) {
				Debug.print(wahlkreis.getName() + " hat keine Erststimmen!", 1);
			}
			if (wahlkreis.getAnzahlErststimmen() < 1) {
				Debug.print(wahlkreis.getName() + " hat keine Zweitstimmen!", 1);
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

			int anzahlErststimmen = (int) (this.getAnzahlErststimmen() * (this
					.getAnteileVonPartei(partei).getAnteilErststimmen() / 100.0));
			int anzahlZweitstimmen = (int) (this.getAnzahlZweitstimmen() * (this
					.getAnteileVonPartei(partei).getAnteilZweitstimmen() / 100.0));

			Debug.print(partei.getName() + ", anzahlErststimmen: "
					+ anzahlErststimmen + " ("
					+ this.getAnteileVonPartei(partei).getAnteilErststimmen()
					+ "%), anzahlZweitstimmen: " + anzahlZweitstimmen + " ("
					+ this.getAnteileVonPartei(partei).getAnteilZweitstimmen()
					+ "%)", 5);

			Random rand = new Random();

			Wahlkreis wk = null;
			int vergebeneErst = 0;
			int vergebeneZweit = 0;
			int stimmzahl;

			while (vergebeneErst < anzahlErststimmen
					|| vergebeneZweit < anzahlZweitstimmen) {

				if (vergebeneErst < anzahlErststimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));

					if (wk.getErststimmenProPartei().size() != 35) {
						System.out.println("Anzahl Erstimmen: "
								+ wk.getErststimmenProPartei().size());
					}

					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math.min((anzahlErststimmen - vergebeneErst),
							(wk.getWahlberechtigte() - wk
									.getAnzahlErststimmen()));

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
						Debug.print("ACHTUNG!!!! : Erststimme ist null!, " + partei.getName() + " : " + wk.getName(), 2);
						
						//Kandidat kan = new Kandidat(Mandat.KEINMANDAT, partei, erst);
						Kandidat kan = new Kandidat("unbekannt", "-", 0, Mandat.KEINMANDAT, partei);

						erst = new Erststimme(stimmzahl, wk, kan);

						wk.getErststimmenProPartei().add(erst);
						partei.addMitglied(kan);
						vergebeneErst += stimmzahl;
					}
				}

				if (vergebeneZweit < anzahlZweitstimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));

					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math.min((anzahlZweitstimmen - vergebeneZweit),
							(wk.getWahlberechtigte() - wk.getAnzahlZweitstimmen()));

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
						Debug.print("ACHTUNG!!!! : Zweitstimme ist null!, " + partei.getName() + " : " + wk.getName(), 2);
						// wk.getZweitstimmen().add(new Zweitstimme(stimmzahl, wk, partei));
					}
				}

			}

		}

		/*
		 * korrigiere die Parteilisten der Bundeslï¿½nder
		 */
		for (Bundesland bundesland : btw.getDeutschland().getBundeslaender()) {
			bundesland.setParteien(new LinkedList<Partei>());
			for (Partei partei : btw.getParteien()) {
				if (bundesland.getAnzahlZweitstimmen(partei) > 0) {
					bundesland.getParteien().add(partei);
				}
			}
		}

	}

	/**
	 * Prüft ob für die gegebene Partei Stimmanteile gegeben sind.
	 * 
	 * @param partei
	 *            Die Partei für diejenige geprüft werden soll ob Stimmanteile
	 *            gegeben sind.
	 * @return Ob für die gegebene Partei Stimmanteile gegeben sind.
	 */
	private boolean hatParteiStimmanteile(Partei partei) {
		if (partei == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"partei\" ist null!");
		}
		for (Stimmanteile sa : this.getStimmanteile()) {
			if (sa.getPartei().getName().equals(partei.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gibt eine Liste aller Parteien zurï¿½ck, fï¿½r diejenigen keine
	 * 
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
	
	/**
	 * Gibt die Basiswahl mit relevanten Strukturdaten zurück.
	 * 
	 * @return Die Basiswahl mit relevanten Strukturdaten.
	 */
	public Bundestagswahl getBasisWahl() {
		return basisWahl;
	}

	/**
	 * Setzt die Basiswahl mit relevanten Strukturdaten.
	 * 
	 * @param basisWahl
	 *            Die Basiswahl mit relevanten Strukturdaten.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter basisWahl null ist.
	 */
	public void setBasisWahl(Bundestagswahl basisWahl)
			throws IllegalArgumentException {
		if (basisWahl == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"basisWahl\" ist null!");
		}
		this.basisWahl = basisWahl;
	}

	/**
	 * Gibt die Liste der Stimmanteile pro Partei zurück.
	 * 
	 * @return die Liste der Stimmanteile pro Partei.
	 */
	public List<Stimmanteile> getStimmanteile() {
		return stimmanteile;
	}

	/**
	 * Setzt die Liste der Stimmanteile pro Partei.
	 * 
	 * @param stimmanteile
	 *            Die Liste der Stimmanteile pro Partei.
	 * @throws IllegalArgumentException
	 *             Wenn der Parameter stimmanteile leer oder null ist.
	 */
	public void setStimmanteile(List<Stimmanteile> stimmanteile)
			throws IllegalArgumentException {
		if (stimmanteile == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"stimmanteile\" ist null!");
		}
		this.stimmanteile = stimmanteile;
	}

	/**
	 * Gibt die Gesamtanzahl der Erststimmen zurück.
	 * 
	 * @return die Gesamtzahl der Erststimmen
	 */
	public Integer getAnzahlErststimmen() {
		return anzahlErststimmen;
	}

	/**
	 * Setzt die Gesamtzahl der Erststimmen.
	 * 
	 * @param anzahlErststimmen
	 *            Die Gesamtzahl der Erststimmen.
	 * @throws IllegalArgumentException
	 *             Wenn der Parameter anzahlErststimmen ist negativ ist.
	 */
	public void setAnzahlErststimmen(Integer anzahlErststimmen)
			throws IllegalArgumentException {
		if (anzahlErststimmen < 0) {
			throw new IllegalArgumentException(
					"Der Parameter \"anzahlErststimmen\" ist negativ!");
		}
		this.anzahlErststimmen = anzahlErststimmen;
	}

	/**
	 * Gibt die Gesamtanzahl der Zweitstimmen zurück.
	 * 
	 * @return die Gesamtzahl der Zweitstimmen
	 */
	public Integer getAnzahlZweitstimmen() {
		return anzahlZweitstimmen;
	}

	/**
	 * Setzt die Gesamtzahl der Zweitstimmen.
	 * 
	 * @param anzahlZweitstimmen
	 *            Die Gesamtzahl der Zweitstimmen.
	 * @throws IllegalArgumentException
	 *             Wenn der Parameter anzahlZweitstimmen ist negativ ist.
	 */
	public void setAnzahlZweitstimmen(Integer anzahlZweitstimmen)
			throws IllegalArgumentException {
		if (anzahlZweitstimmen < 0) {
			throw new IllegalArgumentException(
					"Der Parameter \"anzahlErststimmen\" ist negativ!");
		}
		this.anzahlZweitstimmen = anzahlZweitstimmen;
	}

}

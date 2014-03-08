package main.java.wahlgenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;
import test.java.Debug;

/**
 * Mit dieser Klasse können Bundestagswahlen anhand gegebener Stimmanteile auf
 * Bundesebene generiert werden.
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
	public Wahlgenerator(Bundestagswahl basisWahl,
			List<Stimmanteile> stimmanteile) {

		setBasisWahl(basisWahl);
		setStimmanteile(stimmanteile);

		berechneGesamtanzahlStimmen();

		/*
		 * Prüft ob die gegebenen Stimmanteile gültig sind.
		 */
		int summeErst = 0;
		int summeZweit = 0;
		for (final Stimmanteile sa : stimmanteile) {
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
		if ((summeErst == 0 || summeZweit == 0)
				&& getParteienOhneAnteile().size() == 0) {
			throw new IllegalArgumentException(
					"Es existieren keine Parteien ohne Stimmanteile"
							+ " auf die zufällig Stimmanteile verteilt werden können"
							+ " und insgesamt sind Erst- und/oder Zweitstimmenanteile 0!");
		}

		/*
		 * Prüft, ob eine Partei in den Stimmanteilen mehr als einmal vorkommt
		 */
		for (final Stimmanteile sa1 : stimmanteile) {
			int count = 0;
			for (final Stimmanteile sa2 : stimmanteile) {
				if (sa1.getPartei().getName().equals(sa2.getPartei().getName())) {
					count++;
				}
			}
			if (count != 1) {
				throw new IllegalArgumentException("Die Partei "
						+ sa1.getPartei().getName() + " kommt " + count
						+ " mal in der Liste der Stimmanteile vor!");
			}
		}
	}

	/**
	 * Bestimmt anzahlZweitstimmen und anzahlErststimmen
	 */
	private void berechneGesamtanzahlStimmen() {
		setAnzahlErststimmen(this.basisWahl.getDeutschland()
				.getAnzahlErststimmen());
		setAnzahlZweitstimmen(this.basisWahl.getDeutschland()
				.getAnzahlZweitstimmen());
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

		// verteile restliche Anteile sofern nicht jeweils 100% Erst- und
		// Zweitstimmen vergeben sind
		verteileRestAnteile();

		// erstelle eine tiefe Kopie der Basiswahl als Grundlage
		final Bundestagswahl clone = getBasisWahl().deepCopy();

		// Setze die komplette Auswertung auf NULL
		Mandatsrechner2009.getInstance().initialisiere(clone);

		// Name der neuen Wahl setzen
		clone.setName(name);

		// verteile Stimmen zufällig auf die Gebiete, Parteien und Kandidaten
		verteileStimmen(clone);

		Debug.print("Wahlgenerierung fertig!", 3);

		// Gebe die generierte Wahl zurück
		return clone;
	}

	/**
	 * 
	 * @param partei
	 * @return
	 */
	private Stimmanteile getAnteileVonPartei(Partei partei) {
		for (final Stimmanteile stimmanteile : getStimmanteile()) {
			if (stimmanteile.getPartei().getName().equals(partei.getName())) {
				return stimmanteile;
			}
		}
		return null;
	}

	/**
	 * Gibt die Gesamtanzahl der Erststimmen zurück.
	 * 
	 * @return die Gesamtzahl der Erststimmen
	 */
	public Integer getAnzahlErststimmen() {
		return this.anzahlErststimmen;
	}

	/**
	 * Gibt die Gesamtanzahl der Zweitstimmen zurück.
	 * 
	 * @return die Gesamtzahl der Zweitstimmen
	 */
	public Integer getAnzahlZweitstimmen() {
		return this.anzahlZweitstimmen;
	}

	/**
	 * Gibt die Basiswahl mit relevanten Strukturdaten zurück.
	 * 
	 * @return Die Basiswahl mit relevanten Strukturdaten.
	 */
	public Bundestagswahl getBasisWahl() {
		return this.basisWahl;
	}

	/**
	 * Gibt eine Liste aller Parteien zurück, für diejenigen keine
	 * 
	 * @return
	 */
	private ArrayList<Partei> getParteienOhneAnteile() {
		final ArrayList<Partei> parteien = new ArrayList<>();
		for (final Partei partei : getBasisWahl().getParteien()) {
			if (!hatParteiStimmanteile(partei)) {
				parteien.add(partei);
			}
		}
		return parteien;
	}

	/**
	 * Gibt die Liste der Stimmanteile pro Partei zurück.
	 * 
	 * @return die Liste der Stimmanteile pro Partei.
	 */
	public List<Stimmanteile> getStimmanteile() {
		return this.stimmanteile;
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
		for (final Stimmanteile sa : getStimmanteile()) {
			if (sa.getPartei().getName().equals(partei.getName())) {
				return true;
			}
		}
		return false;
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

	/**
	 * Setzt die Basiswahl mit relevanten Strukturdaten.
	 * 
	 * @param basisWahl
	 *            Die Basiswahl mit relevanten Strukturdaten.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter basisWahl null ist.
	 */
	public final void setBasisWahl(Bundestagswahl basisWahl)
			throws IllegalArgumentException {
		if (basisWahl == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"basisWahl\" ist null!");
		}
		this.basisWahl = basisWahl;
	}

	/**
	 * Setzt die Liste der Stimmanteile pro Partei.
	 * 
	 * @param stimmanteile
	 *            Die Liste der Stimmanteile pro Partei.
	 * @throws IllegalArgumentException
	 *             Wenn der Parameter stimmanteile leer oder null ist.
	 */
	public final void setStimmanteile(List<Stimmanteile> stimmanteile)
			throws IllegalArgumentException {
		if (stimmanteile == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"stimmanteile\" ist null!");
		}
		this.stimmanteile = stimmanteile;
	}

	/**
	 * Verteilt die übrigen Anteile der Erst- und Zweitstimmen auf zufällige
	 * Parteien.
	 */
	private void verteileRestAnteile() {

		/*
		 * berechne die restlichen Anteile, die Parteien zugewiesen werden
		 * sollen
		 */
		int sumErstAnteil = 0;
		int sumZweitAnteil = 0;

		for (final Stimmanteile sa : getStimmanteile()) {
			sumErstAnteil += sa.getAnteilErststimmen();
			sumZweitAnteil += sa.getAnteilZweitstimmen();
		}

		int restErstAnteil = 100 - sumErstAnteil;
		int restZweitAnteil = 100 - sumZweitAnteil;
		Debug.print("\nrestErstAnteil: " + restErstAnteil
				+ "%, restZweitAnteil: " + restZweitAnteil + "%", 4);

		/*
		 * Verteile die restlichen Anteile auf zufällige Parteien
		 */
		final ArrayList<Partei> partOhneAnteileVonBenutzer = getParteienOhneAnteile();
		final Random rand = new Random();

		while ((restErstAnteil > 0 || restZweitAnteil > 0)
				&& partOhneAnteileVonBenutzer.size() != 0) {

			Partei partei;

			if (getParteienOhneAnteile().size() == 0) {
				/*
				 * Es existieren bereits für alle Parteien Stimmanteile, aber es
				 * müssen noch Stimmanteile vergeben werden.
				 * 
				 * => Gebe einer Partei deren Stimmanteile zuvor zufällig
				 * Anteile vergeben wurden noch mehr Anteile
				 */
				partei = partOhneAnteileVonBenutzer.get(rand
						.nextInt(partOhneAnteileVonBenutzer.size()));
			} else {
				// wähle zufällige Partei aus der Liste der Parteien ohne
				// Anteile
				// Partei partei =
				// partOhneAnteile.get(rand.nextInt(partOhneAnteile.size()));
				partei = getParteienOhneAnteile().get(
						rand.nextInt(getParteienOhneAnteile().size()));
			}

			// gibt NULL zurück wenn es zu dieser Partei noch keine Anteile gibt
			Stimmanteile anteil = getAnteileVonPartei(partei);
			/*
			 * Prüfe ob für diese Partei in dieser Schleife schonmal Anteile
			 * erzeugt wurden
			 */
			if (anteil == null) {
				// Für diese Partei existieren noch keine Stimmanteile
				anteil = new Stimmanteile(partei, 0, 0);
				getStimmanteile().add(anteil);
			}

			// füge eine zufällige Anzahl von Erst- und Zweit-Anteilen hinzu
			// Debug.print("übrig: restErstAnteil: " + restErstAnteil +
			// ", restZweitAnteil: " + restZweitAnteil);

			if (restErstAnteil > 0) {
				final int anteilErst = rand.nextInt(restErstAnteil) + 1;
				anteil.setAnteilErststimmen(anteil.getAnteilErststimmen()
						+ anteilErst);
				restErstAnteil -= anteilErst;
			}

			if (restZweitAnteil > 0) {
				final int anteilZweit = rand.nextInt(restZweitAnteil) + 1;
				anteil.setAnteilZweitstimmen(anteil.getAnteilZweitstimmen()
						+ anteilZweit);
				restZweitAnteil -= anteilZweit;
			}

		}
	}

	/**
	 * Verteilt die Stimmen der Parteien für diejenigen Stimmanteile angegeben
	 * sind auf zufufällige Wahlkreise. Die Bundesweiten Stimmanteile der
	 * Parteien bleiben dabei erhalten. Bundesland- und Wahlkreisweit werden die
	 * Stimmen zufällig verteilt.
	 * 
	 * @param btw
	 *            die BTW auf die verteilt werden soll
	 */
	private void verteileStimmen(Bundestagswahl btw) {

		final ArrayList<Wahlkreis> alleWahlkreise = btw.getDeutschland()
				.getWahlkreise();
		final int anzahlWahlkreise = alleWahlkreise.size();

		// durchlaufe alle Wahlkreise
		for (final Wahlkreis wk : alleWahlkreise) {
			// durchlaufe alle Erststimmen
			for (final Erststimme erst : wk.getErststimmenProPartei()) {
				if (erst.getKandidat().getPartei() == null) {
					Debug.print("Wahlkreis: " + wk.getName() + ", Kandidat: "
							+ erst.getKandidat().getName() + ", Partei: NULL",
							4);
				}
				if (hatParteiStimmanteile(erst.getKandidat().getPartei())
						&& getAnteileVonPartei(erst.getKandidat().getPartei())
								.getAnteilErststimmen() > 0) {
					erst.setAnzahl(1);
				} else {
					erst.setAnzahl(0);
				}
			}
			// durchlaufe alle Zweitstimmen
			for (final Zweitstimme zweit : wk.getZweitstimmenProPartei()) {
				if (hatParteiStimmanteile(zweit.getPartei())
						&& getAnteileVonPartei(zweit.getPartei())
								.getAnteilZweitstimmen() > 0) {
					zweit.setAnzahl(1);
				} else {
					zweit.setAnzahl(0);
				}
			}
		}

		for (final Wahlkreis wahlkreis : alleWahlkreise) {
			if (wahlkreis.getAnzahlErststimmen() < 1) {
				Debug.print(wahlkreis.getName() + " hat keine Erststimmen!", 1);
			}
			if (wahlkreis.getAnzahlErststimmen() < 1) {
				Debug.print(wahlkreis.getName() + " hat keine Zweitstimmen!", 1);
			}
		}

		Debug.print("Anzahl Stimmanteile:" + this.stimmanteile.size(), 4);

		// durchlaufe Parteien für die Stimmen verteilt werden sollen
		for (final Stimmanteile sa : getStimmanteile()) {

			final Partei saPartei = sa.getPartei();
			Partei partei = null;

			// die aktuelle Partei
			for (final Partei part : btw.getParteien()) {
				if (part.getName().equals(saPartei.getName())) {
					partei = part;
				}
			}

			if (partei == null) {
				throw new NullPointerException(
						"Partei ist null! Das sollte eigentlich nicht vorkommen ;-)!");
			}

			int anzahlErststimmen = (int) (getAnzahlErststimmen() * (getAnteileVonPartei(
					partei).getAnteilErststimmen() / 100.0));
			if (sa.getAnteilErststimmen() > 0) {
				// Um die Anzahl der Wahlkreise vermindern
				anzahlErststimmen -= getBasisWahl().getDeutschland()
						.getWahlkreise().size();
			}
			int anzahlZweitstimmen = (int) (getAnzahlZweitstimmen() * (getAnteileVonPartei(
					partei).getAnteilZweitstimmen() / 100.0));
			if (sa.getAnteilZweitstimmen() > 0) {
				// Um die Anzahl der Wahlkreise vermindern
				anzahlZweitstimmen -= getBasisWahl().getDeutschland()
						.getWahlkreise().size();
			}

			Debug.print(partei.getName() + ", anzahlErststimmen: "
					+ anzahlErststimmen + " ("
					+ getAnteileVonPartei(partei).getAnteilErststimmen()
					+ "%), anzahlZweitstimmen: " + anzahlZweitstimmen + " ("
					+ getAnteileVonPartei(partei).getAnteilZweitstimmen()
					+ "%)", 5);

			final Random rand = new Random();

			Wahlkreis wk = null;
			int vergebeneErst = 0;
			int vergebeneZweit = 0;
			int stimmzahl;

			while (vergebeneErst < anzahlErststimmen
					|| vergebeneZweit < anzahlZweitstimmen) {

				if (vergebeneErst < anzahlErststimmen) {
					// Wahlkreis zufällig wählen
					wk = alleWahlkreise.get(rand.nextInt(anzahlWahlkreise));

					// Die maximale Stimmzahl die vergeben werden darf ermitteln
					stimmzahl = Math
							.min(anzahlErststimmen - vergebeneErst,
									wk.getWahlberechtigte()
											- wk.getAnzahlErststimmen());

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
						Debug.print("ACHTUNG!!!! : Erststimme ist null!, "
								+ partei.getName() + " : " + wk.getName(), 2);

						// Kandidat kan = new Kandidat(Mandat.KEINMANDAT,
						// partei, erst);
						final Kandidat kan = new Kandidat("unbekannt", "-", 0,
								Mandat.KEINMANDAT, partei);

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
					stimmzahl = Math.min(
							anzahlZweitstimmen - vergebeneZweit,
							wk.getWahlberechtigte()
									- wk.getAnzahlZweitstimmen());

					// Wenn maximal mögliche Stimmzahl positiv, dann wähle eine
					// zufällig eine in dem Intervall [1,max]
					if (stimmzahl != 0) {
						stimmzahl = rand.nextInt(stimmzahl) + 1;
					}

					// zugehöriges Erststimmenobjekt finden
					final Zweitstimme zweit = wk.getZweitstimme(partei);

					// Stimmzahl erhöhen, falls Erststimmen-Objekt gefunden
					if (zweit != null) {
						zweit.erhoeheAnzahl(stimmzahl);
						vergebeneZweit += stimmzahl;
					} else {
						Debug.print("ACHTUNG!!!! : Zweitstimme ist null!, "
								+ partei.getName() + " : " + wk.getName(), 2);
						// wk.getZweitstimmen().add(new Zweitstimme(stimmzahl,
						// wk, partei));
					}
				}

			}

		}

		/*
		 * korrigiere die Parteilisten der Bundeslï¿½nder
		 */
		for (final Bundesland bundesland : btw.getDeutschland()
				.getBundeslaender()) {
			bundesland.setParteien(new LinkedList<Partei>());
			for (final Partei partei : btw.getParteien()) {
				if (bundesland.getAnzahlZweitstimmen(partei) > 0) {
					bundesland.getParteien().add(partei);
				}
			}
		}

	}

}

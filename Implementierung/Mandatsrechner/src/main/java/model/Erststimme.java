package main.java.model;

import java.io.Serializable;

/**
 * Assoziationsklasse die Anzahl aller Erststimmen beinhaltet und mit den
 * Klassen Gebiet und Kandidat zusammen arbeitet. Ausserdem erbt diese von der
 * Klasse Stimme.
 */
public class Erststimme extends Stimme implements Serializable,
		Comparable<Erststimme> {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -950583325552486249L;

	/** Der zugehoerige Kanditat. */
	private Kandidat kandidat;

	/**
	 * Mit diesem Konsruktor ist es möglich alle Attribute auf einmal zu setzen
	 * 
	 * @param anzahl
	 *            Die Anzahl der Stimmen
	 * @param gebiet
	 *            Das zugehörige Gebiet.
	 * @param kandidat
	 *            Der zugehörige Kanditat.
	 */
	public Erststimme(int anzahl, Gebiet gebiet, Kandidat kandidat) {
		setGebiet(gebiet);
		setKandidat(kandidat);
		setAnzahl(anzahl);
	}

	@Override
	public int compareTo(Erststimme andere) {
		return Integer.compare(andere.getAnzahl(), getAnzahl());
	}

	@Override
	public Stimme deepCopy() {
		return new Erststimme(this.anzahl, this.gebiet, this.kandidat);
	}

	@Override
	public void erhoeheAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}

		if (getGebiet().getWahlberechtigte() < getGebiet()
				.getAnzahlErststimmen() + anzahl) {
			throw new IllegalArgumentException(
					"Neu gesetzte Anzahl an Erststimmen übersteigt Anzahl der Wahlberechtigten um "
							+ (getGebiet().getAnzahlErststimmen() + anzahl - getGebiet()
									.getWahlberechtigte()) + "!");
		}

		this.anzahl += anzahl;
	}

	/**
	 * Gibt den verbundenen Kandidaten zurueck.
	 * 
	 * @return der verbundene Kandidat.
	 */
	public Kandidat getKandidat() {
		return this.kandidat;
	}

	@Override
	public void setAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}

		// int gesamtErst = this.getGebiet().getAnzahlErststimmen();
		// int wahlberechtigte = this.getGebiet().getWahlberechtigte();
		//
		// int diffStimme = anzahl - this.getAnzahl();
		// if ((gesamtErst + diffStimme) <= wahlberechtigte) {
		//
		this.anzahl = anzahl;

	}

	/**
	 * Setze den Kandidaten.
	 * 
	 * @param kandidat
	 *            der neue Kanditat.
	 * @throws IllegalArgumentException
	 *             wenn der Kandidat leer ist.
	 */
	public void setKandidat(Kandidat kandidat) throws IllegalArgumentException {
		if (kandidat == null) {
			throw new IllegalArgumentException("Kandidat ist null!");
		}
		this.kandidat = kandidat;
	}

}

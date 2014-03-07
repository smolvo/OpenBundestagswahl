package main.java.mandatsrechner;

import java.util.List;

import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import test.java.Debug;

/**
 * Mandatsrechner mit dem Verteilungsverfahren von SainteLague/Schepers. Diese
 * Klasse ist aehnlich aufgebaut wie Mandatsrechner2009 nur das auch
 * Ausgleichmandate berechnet werden.
 * 
 */
public class Mandatsrechner2013 {

	/*
	 * Da der Mandatsrechner 2009 fast das gleiche Verafahren benutzt werden die
	 * Funktionen der Klasse fuer die Berechnung der Sitzverteilung in dieser
	 * Klasse genutzt.
	 */
	private final Mandatsrechner2009 rechner2009 = Mandatsrechner2009
			.getInstance();

	/** Entwurdmuster: Einzelstueck */
	private static Mandatsrechner2013 instanz;

	/**
	 * Gibt die Instanz der Klasse zurueck.
	 * 
	 * @return die Instanz der Klasse.
	 */
	public static Mandatsrechner2013 getInstance() {
		if (Mandatsrechner2013.instanz == null) {
			Mandatsrechner2013.instanz = new Mandatsrechner2013();
		}
		return Mandatsrechner2013.instanz;
	}

	/**
	 * Privater Konstruktor fuer das Entwurfsmuster Einzelstueck.
	 */
	private Mandatsrechner2013() {
	}

	/**
	 * Berechnet eine Bundestagswahl.
	 * 
	 * @param bw
	 *            die zu berechnende Bundestagswahl
	 * @return eine berechnete Bundestagswahl
	 */
	public Bundestagswahl berechne(Bundestagswahl bw) {

		// Bestimme die Mandate der Zweitstimmen ( + Unterverteilung)
		this.rechner2009.berechneSainteLague(bw);

		// Bestimme Relevante Parteien.
		final List<Partei> relevanteParteien = this.rechner2009
				.berechneRelevanteParteien(bw);

		Debug.print("\nSitzverteilung", 6);
		int summe = 0;
		for (final Partei partei : relevanteParteien) {
			Debug.print(
					partei.getName() + ": " + partei.getMindestsitzAnzahl(), 6);
			summe += partei.getMindestsitzAnzahl();
		}
		Debug.print("Summe: " + summe, 6);

		// Ausgleichsmandate (Schritt 3 - Oberverteilung)
		berechneSitzeZweitstimmenverhaeltnis(bw, relevanteParteien);
		return bw;

	}

	/**
	 * Berechnet den Parteidivisor für die relevanten Parteien.
	 * 
	 * @param relevanteParteien
	 *            die Parteien, die beachtet werden sollen.
	 * @return den berechneten Parteidivisor.
	 * @throws IllegalArgumentException
	 *             wenn die Liste null ist.
	 */
	private float berechneParteidivisor(List<Partei> relevanteParteien)
			throws IllegalArgumentException {
		if (relevanteParteien == null) {
			throw new IllegalArgumentException("Liste ist null!");
		}
		/*
		 * Finden eines geeigneten minimalen Parteidivisors um die anzahl der
		 * neuen Mindestsitze zu bestimmen. Der Parteidivisor ist
		 * min(zweitstimme einer partei / (mindestsitze - 0.5));
		 */
		float parteidivisor = 0;
		for (final Partei partei : relevanteParteien) {
			// System.out.println("RP: " + partei.getName() + " " +
			// partei.getZweitstimmeGesamt() + " " +
			// partei.getMindestsitzAnzahl());
			if (partei.getZweitstimmeGesamt() == 0) {
				continue;
			}
			if (parteidivisor == 0) {
				parteidivisor = (float) (partei.getZweitstimmeGesamt() / (partei
						.getMindestsitzAnzahl() - 0.5));
			} else {
				parteidivisor = (float) Math.min(
						parteidivisor,
						partei.getZweitstimmeGesamt()
								/ (partei.getMindestsitzAnzahl() - 0.5));
			}
		}
		Debug.print("Berechne Parteidivisor. Anfangsdivisor: " + parteidivisor,
				6);
		/**
		 * Der Parteidivisor wird so lange erniedrigt, bis alle Parteien ihre
		 * Anzahl an Mindestsitze erfuellen. TODO: Ordentliche Schleife
		 */
		boolean isCorrect = false;
		while (!isCorrect) {
			isCorrect = true;
			for (final Partei partei : relevanteParteien) {
				final int mindestSitze = partei.getMindestsitzAnzahl();
				// Debug.print("Parteidivisor: " + parteidivisor);
				if (partei.getZweitstimmeGesamt() == 0) {
					continue;
				} else if (Math.round(partei.getZweitstimmeGesamt()
						/ parteidivisor) < mindestSitze) {
					isCorrect = false;
					break;
				}
			}
			if (!isCorrect) {
				parteidivisor -= 1;
				if (parteidivisor < 0) {
					throw new IllegalArgumentException(
							"Ungueltige Eingabewerte (Zweitstimmen).");
				}
			}

		}

		Debug.print("Berechneter Parteidivisor: " + parteidivisor, 5);
		Debug.print("\nNeu Parteidivisor: " + parteidivisor, 5);
		int summe = 0;
		for (final Partei partei : relevanteParteien) {
			Debug.print(
					partei.getName()
							+ ": "
							+ Math.round(partei.getZweitstimmeGesamt()
									/ parteidivisor), 5);
			summe += Math.round(partei.getZweitstimmeGesamt() / parteidivisor);
		}
		Debug.print("Summe: " + summe, 5);

		return parteidivisor;
	}

	private void berechneSitzeZweitstimmenverhaeltnis(Bundestagswahl bw,
			List<Partei> relevanteParteien) {
		/**
		 * Die Zweitstimmen einer Partei dividiert durch den parteidivisor
		 * ergeben nun die neue Mindestanzahl an Sitzen. Nun wird ein
		 * Multiplikator bestimmt, der die Ausgleichsmandate einer Parei auf die
		 * Landeslisten dieser Partei im Verhältnis der Zweitstimmen verteilt.
		 */
		final float parteidivisor = berechneParteidivisor(relevanteParteien);
		if (parteidivisor == 0.f) {
			throw new IllegalArgumentException(
					"Ungueltiger Parteidivisor. (Parteidivisor = 0)");
		}
		for (final Partei partei : relevanteParteien) {
			final int neueSitzanzahl = Math.round(partei.getZweitstimmeGesamt()
					/ parteidivisor);
			final int diffSitze = neueSitzanzahl - partei.getAnzahlMandate(); // partei.getMindestsitzAnzahl();

			if (diffSitze > 0) {
				int sitzeBundesland = 0;
				float divisor = 0f;

				divisor = partei.getZweitstimmeGesamt() / neueSitzanzahl;

				if (Debug.getLevel() > 0) {
					for (final Partei relPartei : relevanteParteien) {
						Debug.print(relPartei.getName(), 5);
					}
				}

				Debug.print("Berechne Divisor. Anfangsdivisor: " + divisor, 5);
				int insgesamt = 0;
				boolean underflow = false, overflow = false;
				while (insgesamt != neueSitzanzahl) {
					insgesamt = 0;
					for (final Bundesland bl : bw.getDeutschland()
							.getBundeslaender()) {

						sitzeBundesland = this.rechner2009.runden(
								bl.getAnzahlZweitstimmen(partei) / divisor,
								false);

						if (sitzeBundesland < bl.getDirektMandate(partei)
								.size()) {
							insgesamt += bl.getDirektMandate(partei).size();
						} else {
							insgesamt += sitzeBundesland;
						}
					}
					if (insgesamt == neueSitzanzahl) {
						break;
					} else if (insgesamt > neueSitzanzahl) {
						divisor += 5;
						if (overflow && underflow) {
							// System.out.println(divisor);
							// System.exit(0);
							break;
						} else {
							overflow = true;
							underflow = false;
						}

					} else {
						if (divisor == 0) {
							throw new IllegalAccessError("insgesamt: "
									+ insgesamt + ", neueSitzanzahl: "
									+ neueSitzanzahl);
						}
						if (overflow) {
							underflow = true;
						}
						divisor -= 1;

					}
				}
				Debug.print("Berechneter Divisor: " + divisor, 5);
				for (final Bundesland bl : bw.getDeutschland()
						.getBundeslaender()) {
					sitzeBundesland = this.rechner2009.runden(
							bl.getAnzahlZweitstimmen(partei) / divisor, false);

					// Gibt es ausgleichsmandate?
					if (sitzeBundesland != partei.getMindestsitzanzahl(bl)) {
						// Anzahl der Ausgleichmandate:
						int diffSitzeBundesland = sitzeBundesland
								- partei.getMindestsitzanzahl(bl);
						/*
						 * int diffSitzeBundesland = sitzeBundesland -
						 * (partei.getUeberhangMandate(bl) +
						 * partei.getAnzahlMandate(Mandat.DIREKTMANDAT, bl));
						 */
						diffSitzeBundesland += partei.getUeberhangMandate(bl);
						Debug.print(
								partei.getName()
										+ " "
										+ bl.getName()
										+ " "
										+ diffSitzeBundesland
										+ " = "
										+ sitzeBundesland
										+ " - "
										+ partei.getMindestsitzanzahl(bl)
										+ " UM: "
										+ partei.getUeberhangMandate(bl)
										+ " "
										+ (partei.getAnzahlMandate(
												Mandat.DIREKTMANDAT, bl) + partei
												.getAnzahlMandate(
														Mandat.LISTENMANDAT, bl)),
								5);

						// Suche Kandidaten ohne Mandat und fuege sie als
						// Ausgleichsmandat hinzu.

						if (diffSitzeBundesland < 0) {
							for (int i = 0; i < Math.abs(diffSitzeBundesland); i++) {
								Debug.print("ABZIEHEN " + partei.getName()
										+ " " + bl.getName(), 5);
								for (int j = partei.getLandesliste().size() - 1; j >= 0; j--) {
									if (partei.getLandesliste(bl)
											.getListenkandidaten().size() > j) {
										final Kandidat mandat = partei
												.getLandesliste(bl)
												.getListenkandidaten().get(j);
										if (mandat.getMandat().equals(
												Mandat.LISTENMANDAT)) {
											mandat.setMandat(Mandat.KEINMANDAT);
											Debug.print(
													"****"
															+ mandat.getName()
															+ " "
															+ mandat.getPartei()
																	.getName()
															+ " wurde Entfernt",
													6);
											// Da nun der Kandidat aus der Liste
											// entfernt werden muss, muss auch
											// sein Eintrag im Berichtsfenster
											// gelöscht werden, dafür ermitteln
											// wir den index des Kandidaten der
											// mit den Index im Bericht
											// übereinstimmt
											for (int k = 0; k < bw
													.getSitzverteilung()
													.getAbgeordnete().size(); k++) {
												if (mandat.equals(bw
														.getSitzverteilung()
														.getAbgeordnete()
														.get(k))) {
													bw.getSitzverteilung()
															.getAbgeordnete()
															.remove(k);
													bw.getSitzverteilung()
															.getBericht()
															.zeileEntfernen(k);
												}
											}

											partei.decrementAusgleichsMandate(bl);
											break;
										}
									}
								}
							}
						} else {
							for (int i = 0; i < diffSitzeBundesland; i++) {
								if (partei.getLandesliste(bl)
										.getListenkandidaten().size() <= i) {
									// Wahlgenerator verursacht Wahlen ohne
									// Landeslisten.
									Debug.print(
											"Notice: Keine Listenplaetze mehr :(",
											4);
									break;
								}
								final Kandidat neuerAbgeordneter = partei
										.getLandesliste(bl)
										.getListenkandidaten().get(i);
								if (neuerAbgeordneter == null) {
									// Negatives Stimmgewicht.
									Debug.print("Kein Abgeordneter gefunden.",
											5);
								} else if (neuerAbgeordneter.getMandat() == Mandat.KEINMANDAT) {
									bw.getSitzverteilung().addAbgeordnete(
											neuerAbgeordneter);
									neuerAbgeordneter
											.setMandat(Mandat.LISTENMANDAT);

									bw.getSitzverteilung()
											.getBericht()
											.zeileHinzufuegen(
													neuerAbgeordneter.getName(),
													neuerAbgeordneter
															.getPartei()
															.getName(),
													Mandat.LISTENMANDAT
															.toString(),
													bl.getName(), "");

									partei.incrementAusgleichsMandate(bl);
								} else {
									diffSitzeBundesland++;
								}
							}
						}
					}
				}
			} else {
				if (diffSitze != 0) {
					Debug.print(
							"Fehler bei den Ausgleichsmandaten. Mindestsitzanzahl nicht erfuellt.",
							2);
					// throw new
					// IllegalArgumentException("Fehler bei den Ausgleichsmandaten. Mindestsitzanzahl nicht erfuellt.");
				}
			}

		}

	}

	/**
	 * Öffentliche Methode zum Testen der Methode berechneParteidivisor(...).
	 * 
	 * @param relevanteParteien
	 *            die Liste mit den zu testenden PArteien.
	 * @return den berechneten PArteidivisor.
	 */
	public float testBerechneParteidivisor(List<Partei> relevanteParteien) {
		return berechneParteidivisor(relevanteParteien);
	}
}

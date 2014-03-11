package main.java.gui.ansicht.tabellenfenster;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import main.java.gui.GUISteuerung;
import main.java.model.Erststimme;
import main.java.model.Gebiet;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und soll die Tabelle im
 * Tabellenfenster der Wahlkreisansicht darstellen.
 * 
 */
public class WahlkreisTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6901614554989527176L;

	/** repräsentiert die Spaltennamen */
	private final String[] columns = new String[] { "Partei", "Kandidat",
			"Erststimmen", "%", "Zweitstimmen", "%", "Direktmandate" };

	/** hält alle relevanten Daten */
	private final WahlkreisDaten daten;

	/** das Tabellenfenster */
	private final TabellenFenster tabellenfenster;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * 
	 * @param daten
	 *            Daten
	 * @param tabellenfenster
	 *            das Tabellenfenster
	 * @throws IllegalArgumentException
	 *             wenn die Eingabeparameter null sind.
	 */
	public WahlkreisTableModel(WahlkreisDaten daten,
			TabellenFenster tabellenfenster) {
		if (daten == null || tabellenfenster == null) {
			throw new IllegalArgumentException("Einer der Parameter ist null.");
		}
		this.daten = daten;
		this.tabellenfenster = tabellenfenster;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columns[columnIndex];
	}

	@Override
	public int getRowCount() {
		return this.daten.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.daten.getParteiName(rowIndex);
		} else if (columnIndex == 1) {
			return this.daten.getKandidatName(rowIndex);
		} else if (columnIndex == 2) {
			return this.daten.getErststimmen(rowIndex).getAnzahl();
		} else if (columnIndex == 3) {
			return this.daten.getErstprozent(rowIndex);
		} else if (columnIndex == 4) {
			return this.daten.getZweitstimmen(rowIndex).getAnzahl();
		} else if (columnIndex == 5) {
			return this.daten.getZweitprozent(rowIndex);
		} else if (columnIndex == 6) {
			return this.daten.getDirektmandate(rowIndex);
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 2 || columnIndex == 4) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		if (columnIndex == 2) {
			final int alterWert = this.daten.getErststimmen(rowIndex)
					.getAnzahl();
			final String stringAnzahl = (String) obj;
			final Erststimme erststimme = this.daten.getErststimmen(rowIndex);
			final GUISteuerung guiSteuerung = this.tabellenfenster.getAnsicht()
					.getFenster().getSteuerung();
			int anzahl = -1;
			final int gesamtErst = erststimme.getGebiet()
					.getAnzahlErststimmen();
			final int wahlberechtigte = erststimme.getGebiet()
					.getWahlberechtigte();
			// zeigt an ob eine Änderung möglich war
			boolean aenderung = false;
			// versuche Integer umzuwandeln
			try {
				anzahl = Integer.parseInt(stringAnzahl);
				if (anzahl < 0) {
					throw new NumberFormatException();
				}
				final int diffStimme = anzahl - alterWert;
				if (gesamtErst + diffStimme <= wahlberechtigte) {
					if (anzahl != alterWert) {
						aenderung = guiSteuerung.wertAenderung(erststimme,
								anzahl);
						// wenn die Stimme intern geändert wurde auch in der
						// Tabelle
						// ändern
						// und berechne-Knopf aufrufen
						if (aenderung) {
							this.tabellenfenster.getAnsicht()
									.berechnungNotwendig();
							final Gebiet gebiet = this.tabellenfenster
									.getAnsicht().getAktuellesGebiet();
							this.tabellenfenster.getAnsicht().getFenster()
									.getSteuerung()
									.aktualisiereWahlfenster(gebiet);
							this.daten.getErststimmen(rowIndex).setAnzahl(
									anzahl);
							this.tabellenfenster.getAnsicht().getFenster()
									.getPf().getMenu().setzeSichtbarkeit();
						}
					}
				} else {
					final int restStimmen = wahlberechtigte - gesamtErst;
					JOptionPane
							.showMessageDialog(
									this.tabellenfenster,
									"In dem Wahlkreis " + erststimme.getGebiet().getName() + " gibt es nur " + wahlberechtigte + " Wahlberechtigte. \n Sie haben die Anzahl um " + restStimmen + " überschritten.",
									"Meldung", JOptionPane.INFORMATION_MESSAGE,
									null);
				}
			} catch (final NumberFormatException e) {

				JOptionPane
						.showMessageDialog(
								this.tabellenfenster,
								"Nur positive ganze Zahlen erlaubt.\nStimme konnte nicht geändert werden.",
								"Meldung", JOptionPane.INFORMATION_MESSAGE,
								null);

			}
		} else if (columnIndex == 4) {
			final int alterWert = this.daten.getZweitstimmen(rowIndex)
					.getAnzahl();
			final String stringAnzahl = (String) obj;
			final Zweitstimme zweitstimme = this.daten
					.getZweitstimmen(rowIndex);
			final GUISteuerung guiSteuerung = this.tabellenfenster.getAnsicht()
					.getFenster().getSteuerung();
			// zeigt an ob eine Änderung möglich war
			int anzahl = -1;
			final int gesamtZweit = zweitstimme.getGebiet()
					.getAnzahlZweitstimmen();
			final int wahlberechtigte = zweitstimme.getGebiet()
					.getWahlberechtigte();
			boolean aenderung = false;
			// versuche Integer umzuwandeln
			try {
				anzahl = Integer.parseInt(stringAnzahl);
				if (anzahl < 0) {
					throw new NumberFormatException();
				}
				final int diffStimme = anzahl - alterWert;
				if (gesamtZweit + diffStimme <= wahlberechtigte) {
					if (anzahl != alterWert) {
						aenderung = guiSteuerung.wertAenderung(zweitstimme,
								anzahl);
						// wenn die Stimme intern geändert wurde auch in der
						// Tabelle
						// ändern
						// und berechne-Knopf aufrufen
						if (aenderung) {
							this.tabellenfenster.getAnsicht()
									.berechnungNotwendig();
							final Gebiet gebiet = this.tabellenfenster
									.getAnsicht().getAktuellesGebiet();
							this.tabellenfenster.getAnsicht().getFenster()
									.getSteuerung()
									.aktualisiereWahlfenster(gebiet);
							this.daten.getZweitstimmen(rowIndex).setAnzahl(
									anzahl);

							this.tabellenfenster.getAnsicht().getFenster()
									.getPf().getMenu().setzeSichtbarkeit();
						}
					}
				} else {
					final int restStimmen = wahlberechtigte - gesamtZweit;
					JOptionPane
							.showMessageDialog(
									this.tabellenfenster,
									"In dem Wahlkreis " + zweitstimme.getGebiet().getName() + " gibt es nur " + wahlberechtigte + " Wahlberechtigte. \n Sie haben die Anzahl um " + restStimmen + " überschritten.",
									"Meldung", JOptionPane.INFORMATION_MESSAGE,
									null);
				}
			} catch (final NumberFormatException e) {

				JOptionPane
						.showMessageDialog(
								this.tabellenfenster,
								"Nur positive ganze Zahlen erlaubt.\nStimme konnte nicht geändert werden.",
								"Meldung", JOptionPane.INFORMATION_MESSAGE,
								null);

			}
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}

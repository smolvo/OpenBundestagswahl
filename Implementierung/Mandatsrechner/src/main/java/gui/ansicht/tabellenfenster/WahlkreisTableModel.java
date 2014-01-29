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
	private String[] columns = new String[] { "Partei", "Kandidat",
			"Erststimmen", "%", "Zweitstimmen", "%", "Direktmandate" };

	/** hält alle relevanten Daten */
	private WahlkreisDaten daten;

	/** das Tabellenfenster */
	private TabellenFenster tabellenfenster;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * 
	 * @param daten
	 *            Daten
	 * @param tabellenfenster
	 *            das Tabellenfenster
	 * @throws NullPointerException
	 */
	public WahlkreisTableModel(WahlkreisDaten daten,
			TabellenFenster tabellenfenster) {
		if (daten == null || tabellenfenster == null) {
			throw new NullPointerException("Einer der Parameter ist null.");
		}
		this.daten = daten;
		this.tabellenfenster = tabellenfenster;
	}

	@Override
	public int getRowCount() {
		return daten.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return daten.getParteiName(rowIndex);
		} else if (columnIndex == 1) {
			return daten.getKandidatName(rowIndex);
		} else if (columnIndex == 2) {
			return daten.getErststimmen(rowIndex).getAnzahl();
		} else if (columnIndex == 3) {
			return daten.getErstprozent(rowIndex);
		} else if (columnIndex == 4) {
			return daten.getZweitstimmen(rowIndex).getAnzahl();
		} else if (columnIndex == 5) {
			return daten.getZweitprozent(rowIndex);
		} else if (columnIndex == 6) {
			return daten.getDirektmandate(rowIndex);
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if ((columnIndex == 2) || (columnIndex == 4)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		if (columnIndex == 2) {
			int alterWert = daten.getErststimmen(rowIndex).getAnzahl();
			String stringAnzahl = (String) obj;
			Erststimme erststimme = daten.getErststimmen(rowIndex);
			GUISteuerung guiSteuerung = tabellenfenster.getAnsicht()
					.getFenster().getSteuerung();
			int anzahl = -1;
			// zeigt an ob eine Änderung möglich war
			boolean aenderung = false;
			// versuche Integer umzuwandeln
			try {
				anzahl = Integer.parseInt(stringAnzahl);
				aenderung = guiSteuerung.wertAenderung(erststimme, anzahl);
			} catch (NumberFormatException e) {
				
				JOptionPane.showMessageDialog(this.tabellenfenster,
						"Nur positive ganze Zahlen erlaubt.", "Meldung",
						JOptionPane.INFORMATION_MESSAGE, null);
				
			}
			if (anzahl != alterWert) {
				// wenn die Stimme intern geändert wurde auch in der Tabelle ändern
				// und berechne-Knopf aufrufen
				if (aenderung) {
					this.tabellenfenster.getAnsicht().berechnungNotwendig();
					Gebiet gebiet = tabellenfenster.getAnsicht().getAktuellesGebiet();
					tabellenfenster.getAnsicht().getFenster().getSteuerung().aktualisiereWahlfenster(gebiet);
					daten.getErststimmen(rowIndex).setAnzahl(anzahl);
				} else {
					
					JOptionPane.showMessageDialog(this.tabellenfenster,
							"Stimme konnte nicht geändert werden.", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, null);
					
				}
			}
		} else if (columnIndex == 4) {
			int alterWert = daten.getZweitstimmen(rowIndex).getAnzahl();
			String stringAnzahl = (String) obj;
			Zweitstimme zweitstimme = daten.getZweitstimmen(rowIndex);
			GUISteuerung guiSteuerung = tabellenfenster.getAnsicht()
					.getFenster().getSteuerung();
			// zeigt an ob eine Änderung möglich war
			int anzahl = -1;
			boolean aenderung = false;
			// versuche Integer umzuwandeln
			try {
				anzahl = Integer.parseInt(stringAnzahl);
				aenderung = guiSteuerung.wertAenderung(zweitstimme, anzahl);
			} catch (NumberFormatException e) {
				
				JOptionPane.showMessageDialog(this.tabellenfenster,
						"Nur positive ganze Zahlen erlaubt.", "Meldung",
						JOptionPane.INFORMATION_MESSAGE, null);
				
			}
			if (anzahl != alterWert) {
				// wenn die Stimme intern geändert wurde auch in der Tabelle ändern
				// und berechne-Knopf aufrufen
				if (aenderung) {
					this.tabellenfenster.getAnsicht().berechnungNotwendig();
					Gebiet gebiet = tabellenfenster.getAnsicht().getAktuellesGebiet();
					tabellenfenster.getAnsicht().getFenster().getSteuerung().aktualisiereWahlfenster(gebiet);
					daten.getZweitstimmen(rowIndex).setAnzahl(anzahl);
				} else {
					
					JOptionPane.showMessageDialog(this.tabellenfenster,
							"Stimme konnte nicht geändert werden.", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, null);
					
				}
			}
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}

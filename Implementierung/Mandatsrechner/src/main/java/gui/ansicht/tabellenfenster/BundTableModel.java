package main.java.gui.ansicht.tabellenfenster;

import java.io.IOException;

import javax.swing.table.AbstractTableModel;

import main.java.gui.GUISteuerung;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und soll die Tabelle im
 * Tabellenfenster der Bundesansicht darstellen.
 * 
 */
public class BundTableModel extends AbstractTableModel {

	/** repr‰sentiert die Spaltennamen */
	private String[] columns = new String[] { "Partei", "Zweitstimmen", "%",
			"Sitze", "Direktmandate", "‹berhangsmandate", "Ausgleichsmandate" };

	/** h‰lt alle relevanten Daten */
	private BundDaten daten;

	/** das Tabellenfenster */
	private TabellenFenster tabellenfenster;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * 
	 * @param daten
	 *            die Daten
	 * @param tabellenfenster
	 *            das tabellenfenster
	 */
	public BundTableModel(BundDaten daten, TabellenFenster tabellenfenster) {
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
			return daten.getParteien(rowIndex);
		} else if (columnIndex == 1) {
			return daten.getStimmen(rowIndex).getAnzahl();
		} else if (columnIndex == 2) {
			return daten.getProzent(rowIndex);
		} else if (columnIndex == 3) {
			return daten.getSitze(rowIndex);
		} else if (columnIndex == 4) {
			return daten.getDirektmandate(rowIndex);
		} else if (columnIndex == 5) {
			return daten.getUeberhangsmandate(rowIndex);
		} else if (columnIndex == 6) {
			return daten.getAusgleichsmandate(rowIndex);
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex != 1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			String stringAnzahl = (String) obj;
			Zweitstimme stimme = daten.getStimmen(rowIndex);
			GUISteuerung guiSteuerung = tabellenfenster.getAnsicht()
					.getFenster().getSteuerung();
			int anzahl = -1;
			boolean aenderung = false;
			try {
				anzahl = Integer.parseInt(stringAnzahl);
				if (anzahl >= 0) {
					aenderung = guiSteuerung.wertAenderung(stimme, anzahl);
				}
			} catch (NumberFormatException e) {
				System.out.println("‰‰‰tsch");
			}
			if (aenderung) {
				daten.getStimmen(rowIndex).setAnzahl(anzahl);
			}
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}

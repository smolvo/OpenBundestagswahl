package main.java.gui.ansicht.tabellenfenster;

import javax.swing.table.AbstractTableModel;

import main.java.gui.GUISteuerung;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und soll die Tabelle im
 * Tabellenfenster der Landesansicht darstellen.
 * 
 */
public class LandTableModel extends AbstractTableModel {

	/** repräsentiert die Spaltennamen */
	private String[] columns = new String[] { "Partei", "Zweitstimmen", "%",
			"Direktmandate", "Überhangsmandate" };

	/** hält alle relevanten Daten */
	private LandDaten daten;

	/** das Tabellenfenster */
	private TabellenFenster tabellenfenster;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * 
	 * @param daten
	 *            Daten
	 * @param tabellenfenster das Tabellenfenster
	 * @throws NullPointerException
	 */
	public LandTableModel(LandDaten daten, TabellenFenster tabellenfenster) {
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
		return 5;
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
			return daten.getDirektmandate(rowIndex);
		} else if (columnIndex == 4) {
			return daten.getUeberhangsmandate(rowIndex);
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}
}

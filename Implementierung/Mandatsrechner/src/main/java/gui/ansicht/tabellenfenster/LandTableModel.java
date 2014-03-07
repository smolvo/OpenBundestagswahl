package main.java.gui.ansicht.tabellenfenster;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und soll die Tabelle im
 * Tabellenfenster der Landesansicht darstellen.
 * 
 */
public class LandTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6973781659566829816L;

	/** repräsentiert die Spaltennamen */
	private final String[] columns = new String[] { "Partei", "Zweitstimmen",
			"%", "Direktmandate", "Überhangmandate" };

	/** hält alle relevanten Daten */
	private final LandDaten daten;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * 
	 * @param daten
	 *            Daten
	 * @throws IllegalArgumentException
	 *             wenn das LandDaten Objekt null ist.
	 */
	public LandTableModel(LandDaten daten) {
		if (daten == null) {
			throw new IllegalArgumentException("Das LandDaten objekt ist null.");
		}
		this.daten = daten;
	}

	@Override
	public int getColumnCount() {
		return 5;
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
			return this.daten.getParteien(rowIndex);
		} else if (columnIndex == 1) {
			return this.daten.getStimmen(rowIndex).getAnzahl();
		} else if (columnIndex == 2) {
			return this.daten.getProzent(rowIndex);
		} else if (columnIndex == 3) {
			return this.daten.getDirektmandate(rowIndex);
		} else if (columnIndex == 4) {
			return this.daten.getUeberhangsmandate(rowIndex);
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}

package main.java.gui.ansicht.tabellenfenster;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und soll die Tabelle im
 * Tabellenfenster der Bundesansicht darstellen.
 * 
 * @author Anton
 * 
 */
public class BundTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -5572048315837858653L;

	/** repräsentiert die Spaltennamen */
	private String[] columns = new String[] { "Partei", "Zweitstimmen", "%",
			"Sitze", "Direktmandate", "Überhangsmandate", "Ausgleichsmandate" };

	/** hï¿½lt alle relevanten Daten */
	private BundDaten daten;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * 
	 * @param daten
	 *            die Daten
	 * @throws IllegalArgumentException
	 *             wenn das Bunddaten-Objekt null ist.
	 */
	public BundTableModel(BundDaten daten) {
		if (daten == null) {
			throw new IllegalArgumentException("Einer der Parameter ist null.");
		}
		this.daten = daten;
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
		return false;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}
}

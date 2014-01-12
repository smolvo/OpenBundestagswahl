package gui.ansicht.tabellenfenster;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und
 * soll die Tabelle im Tabellenfenster darstellen.
 *
 */
public class GUITableModel extends AbstractTableModel {

	/** repr�sentiert die Spaltennamen */
	private String[] spalten;
	
	/** h�lt alle relevanten Daten */
	private BundDaten daten;
	
	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 */
	public GUITableModel(BundDaten daten) {
		this.spalten = new String[] {"Partei", "Zweitstimmen", "%", "Sitze", 
				"Direktmandate", "�berhangsmandate", "Ausgleichsmandate"};
		this.daten = daten;
	}
	
	@Override
	public int getRowCount() {
		return daten.size();
	}

	@Override
	public int getColumnCount() {
		return this.spalten.length;
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
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}

package gui.ansicht.tabellenfenster;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und
 * soll die Tabelle im Tabellenfenster der Wahlkreisansicht darstellen.
 *
 */
public class WahlkreisTableModel extends AbstractTableModel {

	/** repräsentiert die Spaltennamen */
	private String[] columns = new String[] {"Partei", "Kandidat", "Erststimmen", "%", "Zweitstimmen", 
			"%", "Direktmandate"};
	
	/** hält alle relevanten Daten */
	private WahlkreisDaten daten;
	
	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 */
	public WahlkreisTableModel(WahlkreisDaten daten) {
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
			
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}

package gui.ansicht.tabellenfenster;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und
 * soll die Tabelle im Tabellenfenster der Landesansicht darstellen.
 *
 */
public class LandTableModel extends AbstractTableModel {

	/** repräsentiert die Spaltennamen */
	private String[] columns = new String[] {"Partei", "Zweitstimmen", "%", 
			"Direktmandate", "Überhangsmandate"};
	
	/** hält alle relevanten Daten */
	private LandDaten daten;
	
	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 */
	public LandTableModel(LandDaten daten) {
		this.daten = daten;
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

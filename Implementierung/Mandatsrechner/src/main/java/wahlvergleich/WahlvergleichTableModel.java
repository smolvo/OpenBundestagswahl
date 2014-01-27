package main.java.wahlvergleich;

import javax.swing.table.AbstractTableModel;

/**
 * Diese Klasse repräsentiert das Tabellenmodell des Wahlvergleiches. In ihr
 * werden Spaltennamen und die Daten gehalten.
 * 
 * @author Anton
 * 
 */
public class WahlvergleichTableModel extends AbstractTableModel {

	/** repräsentiert die Spaltennamen */
	private String[] columns = new String[] { "Partei", "Erststimmenanzahl",
			"%", "Differenz", "Zweitstimmenanzahl", "%", "Differenz",
			"Erststimmenanzahl", "%", "Zweitstimmenanzahl", "%" };

	/** hält alle relevanten Daten */
	private WahlvergleichDaten daten;

	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 */
	public WahlvergleichTableModel(WahlvergleichDaten daten) {
		this.daten = daten;
	}

	@Override
	public int getRowCount() {
		return daten.size();
	}

	@Override
	public int getColumnCount() {
		return 11;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return daten.getParteien(rowIndex);
		} else if (columnIndex == 1) {
			return daten.getAnzahlEinsErst(rowIndex);
		} else if (columnIndex == 2) {
			return daten.getProzentEinsErst(rowIndex);
		} else if (columnIndex == 3) {
			return daten.getDiffErst(rowIndex);
		} else if (columnIndex == 4) {
			return daten.getAnzahlEinsZweit(rowIndex);
		} else if (columnIndex == 5) {
			return daten.getProzentEinsZweit(rowIndex);
		} else if (columnIndex == 6) {
			return daten.getDiffZweit(rowIndex);
		} else if (columnIndex == 7) {
			return daten.getAnzahlZweiErst(rowIndex);
		} else if (columnIndex == 8) {
			return daten.getProzentZweiErst(rowIndex);
		} else if (columnIndex == 9) {
			return daten.getAnzahlZweiZweit(rowIndex);
		} else if (columnIndex == 10) {
			return daten.getProzentZweiZweit(rowIndex);
		} else {
			return null;
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}
}

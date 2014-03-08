package main.java.gui;

import javax.swing.table.AbstractTableModel;

import main.java.model.BerichtDaten;

/**
 * Diese Klasse repräsentiert das Tabellenmodell der Berichtsklasse.
 * 
 */
public class BerichtTableModel extends AbstractTableModel {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = 5458984414665530040L;

	/** repräsentiert die Spaltennamen */
	private final String[] columns = new String[] { "Abgeordneter", "Partei",
			"Mandat", "Bundesland", "Wahlkreis" };

	/** hält alle relevanten Daten */
	private final BerichtDaten daten;

	/**
	 * Der Konstruktor initialisiert die Daten.
	 * 
	 * @param daten
	 *            Daten
	 */
	public BerichtTableModel(BerichtDaten daten) {
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
			return this.daten.getName(rowIndex);
		} else if (columnIndex == 1) {
			return this.daten.getPartei(rowIndex);
		} else if (columnIndex == 2) {
			return this.daten.getMandat(rowIndex);
		} else if (columnIndex == 3) {
			return this.daten.getBundesland(rowIndex);
		} else if (columnIndex == 4) {
			return this.daten.getWahlkreis(rowIndex);
		} else {
			return null;
		}
	}
}

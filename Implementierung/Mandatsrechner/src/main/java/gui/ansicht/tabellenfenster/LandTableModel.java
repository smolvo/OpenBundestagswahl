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
		this.setTabellenfenster(tabellenfenster);
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

	/**
	 * Gibt das Tabellenfenster zurück.
	 * @return das Tabellenfenster
	 */
	public TabellenFenster getTabellenfenster() {
		return tabellenfenster;
	}

	/**
	 * Setzt das Tabellenfenster
	 * @param tabellenfenster das Tabellenfenster das gesetzt werden soll.
	 */
	public void setTabellenfenster(TabellenFenster tabellenfenster) {
		if (tabellenfenster == null) {
			throw new IllegalArgumentException("Der Parameter 'Tabellenfenster ist null!'");
		}
		this.tabellenfenster = tabellenfenster;
	}
	
}

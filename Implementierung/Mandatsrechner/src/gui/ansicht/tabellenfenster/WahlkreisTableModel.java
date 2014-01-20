package gui.ansicht.tabellenfenster;

import gui.GUISteuerung;

import javax.swing.table.AbstractTableModel;

import model.Zweitstimme;

/**
 * Diese Klasse erweitert die AbstractTableModel Klasse und
 * soll die Tabelle im Tabellenfenster der Wahlkreisansicht darstellen.
 *
 */
public class WahlkreisTableModel extends AbstractTableModel {

	/** repr‰sentiert die Spaltennamen */
	private String[] columns = new String[] {"Partei", "Kandidat", "Erststimmen", "%", "Zweitstimmen", 
			"%", "Direktmandate"};
	
	/** h‰lt alle relevanten Daten */
	private WahlkreisDaten daten;

	/** das Tabellenfenster */
	private TabellenFenster tabellenfenster;
	
	/**
	 * Der Konstruktor initialisiert die Spaltennamen und Daten.
	 * @param daten Daten
	 */
	public WahlkreisTableModel(WahlkreisDaten daten, TabellenFenster tabellenfenster) {
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
		if (columnIndex != 3) {
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
		if (columnIndex == 3) {
			String stringAnzahl = (String) obj;
			Zweitstimme stimme = daten.getZweitstimmen(rowIndex);
			GUISteuerung guiSteuerung = tabellenfenster.getAnsicht().getFenster().getSteuerung();
			int anzahl = -1;
			try{
				anzahl = Integer.parseInt(stringAnzahl);
				guiSteuerung.wertAenderung(stimme, anzahl);
			} catch (NumberFormatException e) {
				System.out.println("‰‰‰tsch");
			}
//			daten.getStimmen(rowIndex).setAnzahl(Integer.parseInt(anzahl));
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}

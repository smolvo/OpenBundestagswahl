package main.java.gui.dialoge;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import main.java.gui.Programmfenster;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repräsentiert den Dialog für den Export, der ermöglicht Wahlen
 * als .csv-Datei abzuspeichern.
 * 
 */
public class ExportDialog extends JDialog {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -6257552145843360427L;

	/** repräsentiert den Dateiaussucher */
	private final JFileChooser dateiAuswahl;

	/** repräsentiert das Programmfenster */
	private final Programmfenster pf;

	/**
	 * Der Konstruktor erstellt den FileChooser und führt den Export durch.
	 * 
	 * @param pf
	 *            Programmfenster
	 */
	public ExportDialog(Programmfenster pf) {
		this.pf = pf;
		dateiAuswahl = new JFileChooser();

		// allgemeine Anpassungen des Fensters
		setTitle("Exportieren");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		int rueckgabeWert = dateiAuswahl.showSaveDialog(null);

		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			String pfad = dateiAuswahl.getSelectedFile().toString();
			Steuerung.getInstance().exportieren(pfad);
		} else {
			JOptionPane.showMessageDialog(pf, "Speichern abgebrochen.",
					"Meldung", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}

	/**
	 * Gibt das Programmfenster zurück.
	 * 
	 * @return das Programmfenster zurück.
	 */
	public Programmfenster getPf() {
		return pf;
	}
}

package main.java.gui.dialoge;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import main.java.gui.Programmfenster;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repr�sentiert den Dialog f�r den Export, der erm�glicht Wahlen
 * als .csv-Datei abzuspeichern.
 * 
 */
public class ExportDialog extends JDialog {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -6257552145843360427L;

	/** repr�sentiert den Dateiaussucher */
	private final JFileChooser dateiAuswahl;

	/** repr�sentiert das Programmfenster */
	private final Programmfenster pf;

	/**
	 * Der Konstruktor erstellt den FileChooser und f�hrt den Export durch.
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
	 * Gibt das Programmfenster zur�ck.
	 * 
	 * @return das Programmfenster zur�ck.
	 */
	public Programmfenster getPf() {
		return pf;
	}
}

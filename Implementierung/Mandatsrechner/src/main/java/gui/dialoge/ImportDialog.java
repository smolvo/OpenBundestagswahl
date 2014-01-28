package main.java.gui.dialoge;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.gui.Programmfenster;
import main.java.gui.TabLeiste;
import main.java.gui.WahlFenster;
import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repr�sentiert den Dialog, der sich �ffnet, wenn man im Men�
 * "Importieren" oder in der Tab- Leiste den "+"- Button klickt
 * 
 * Der Benutzer wird aufgefordert die zwei f�r die Berechnung der Sitzverteilung
 * notwendigen Dateien zu �bergeben.
 * 
 * @author Manuel
 * 
 */
public class ImportDialog extends JDialog {

	/** zeigt an ob es sich um die erste Ausf�hrung handelt */
	boolean ersteAusfuehrung = true;

	/** repr�sentiert den FileChooser f�r die Ergebnisse einer Bundestagswahl */
	JFileChooser ergebnisseAuswahl = null;

	/** repr�sentiert den FileChooser f�r die Bewerber */
	JFileChooser bewerberAuswahl = null;

	/** repr�sentiert den Vektor mit den eingelesenen Daten */
	private File[] eingeleseneDateien = new File[2];

	/** repr�sentiert das Programmfenster */
	private Programmfenster pf;

	/**
	 * Der Konstruktor erstellt einen neuen Importdialog mit dem zwei Dateien
	 * 
	 * @param pf
	 *            Programmfenster
	 * @throws NullPointerException
	 */
	public ImportDialog(Programmfenster pf) {
		if (pf == null) {
			throw new NullPointerException("Bitte ein Programmfenster angeben.");
		}
		this.pf = pf;
	}

	/**
	 * Diese Methode importiert eine Wahl in das Programmfenster.
	 */
	public void importiereWahl() {
		// bei der ersten Ausf�hrung wird der FileChooser gesichert
		if (ersteAusfuehrung) {
			ergebnisseAuswahl = new JFileChooser();
			ergebnisseAuswahl.setDialogTitle("Wahlergebnisse importieren");
			bewerberAuswahl = new JFileChooser();
			bewerberAuswahl.setDialogTitle("Wahlbewerber importieren");
			ersteAusfuehrung = false;
		}

		int rueckgabeWert = ergebnisseAuswahl.showOpenDialog(pf);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			rueckgabeWert = bewerberAuswahl.showOpenDialog(pf);
			if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
				eingeleseneDateien[0] = ergebnisseAuswahl.getSelectedFile();
				eingeleseneDateien[1] = bewerberAuswahl.getSelectedFile();

				Bundestagswahl w = Steuerung.getInstance().importieren(
						eingeleseneDateien);
				Steuerung.getInstance().setBtw(w);
				Steuerung.getInstance().berechneSitzverteilung();
				pf.wahlHinzufuegen(w);

			} else {
				JOptionPane.showMessageDialog(pf,
						"Keine Datei mit Wahlbewerber ausgew�hlt.", "Meldung",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		} else {
			JOptionPane.showMessageDialog(pf,
					"Keine Datei mit Wahlergebnisse ausgew�hlt.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}

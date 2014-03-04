package main.java.gui.dialoge;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.gui.Programmfenster;
import main.java.model.Bundestagswahl;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repräsentiert den Dialog, der sich öffnet, wenn man im Menü
 * "Importieren" oder in der Tab- Leiste den "+"- Button klickt
 * 
 * Der Benutzer wird aufgefordert die zwei für die Berechnung der Sitzverteilung
 * notwendigen Dateien zu übergeben.
 * 
 */
public class ImportDialog extends JDialog {

	private static final long serialVersionUID = -1969759039710597635L;

	/** zeigt an ob es sich um die erste Ausführung handelt */
	boolean ersteAusfuehrung = true;

	/** repräsentiert den FileChooser für die Ergebnisse einer Bundestagswahl */
	JFileChooser ergebnisseAuswahl = null;

	/** repräsentiert den FileChooser für die Bewerber */
	JFileChooser bewerberAuswahl = null;

	/** repräsentiert den Vektor mit den eingelesenen Daten */
	private File[] eingeleseneDateien = new File[2];
	
	private FileFilter filter = new FileNameExtensionFilter("CSV File","csv");

	/** repräsentiert das Programmfenster */
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
		// bei der ersten Ausführung wird der FileChooser gesichert
		if (ersteAusfuehrung) {
			ergebnisseAuswahl = new JFileChooser();
			ergebnisseAuswahl.setFileFilter(filter);
			ergebnisseAuswahl.setAcceptAllFileFilterUsed(false);
			ergebnisseAuswahl.setDialogTitle("Wahlergebnisse importieren");
			bewerberAuswahl = new JFileChooser();
			bewerberAuswahl.setFileFilter(filter);
			ergebnisseAuswahl.setAcceptAllFileFilterUsed(false);
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
						"Keine Datei mit Wahlbewerber ausgewählt.", "Meldung",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		} else {
			JOptionPane.showMessageDialog(pf,
					"Keine Datei mit Wahlergebnisse ausgewählt.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}

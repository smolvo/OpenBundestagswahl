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
	private final File[] eingeleseneDateien = new File[2];

	private final FileFilter filter = new FileNameExtensionFilter("CSV Datei",
			"csv");

	/** repräsentiert das Programmfenster */
	private final Programmfenster pf;

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
		if (this.ersteAusfuehrung) {
			this.ergebnisseAuswahl = new JFileChooser();
			this.ergebnisseAuswahl.setFileFilter(this.filter);
			this.ergebnisseAuswahl.setAcceptAllFileFilterUsed(false);
			this.ergebnisseAuswahl.setDialogTitle("Wahlergebnisse importieren");
			this.bewerberAuswahl = new JFileChooser();
			this.bewerberAuswahl.setFileFilter(this.filter);
			this.ergebnisseAuswahl.setAcceptAllFileFilterUsed(false);
			this.bewerberAuswahl.setDialogTitle("Wahlbewerber importieren");
			this.ersteAusfuehrung = false;
		}

		int rueckgabeWert = this.ergebnisseAuswahl.showOpenDialog(this.pf);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			rueckgabeWert = this.bewerberAuswahl.showOpenDialog(this.pf);
			if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
				this.eingeleseneDateien[0] = this.ergebnisseAuswahl
						.getSelectedFile();
				this.eingeleseneDateien[1] = this.bewerberAuswahl
						.getSelectedFile();
				Bundestagswahl w = null;
				try {
					w = Steuerung.getInstance().importieren(
							this.eingeleseneDateien);
				} catch (final IllegalArgumentException e) {
					JOptionPane
							.showMessageDialog(
									this.pf,
									"Die .csv-Datei entspricht nicht dem gewünschten Format.",
									"Meldung", JOptionPane.INFORMATION_MESSAGE,
									null);
					return;
				}
				Steuerung.getInstance().setBtw(w);
				Steuerung.getInstance().berechneSitzverteilung();
				this.pf.wahlHinzufuegen(w);

			} else {
				JOptionPane.showMessageDialog(this.pf,
						"Keine Datei mit Wahlbewerber ausgewählt.", "Meldung",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		} else {
			JOptionPane.showMessageDialog(this.pf,
					"Keine Datei mit Wahlergebnisse ausgewählt.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}

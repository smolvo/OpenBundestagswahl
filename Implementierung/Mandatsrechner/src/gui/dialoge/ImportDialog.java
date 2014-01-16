package gui.dialoge;

import gui.Programmfenster;
import gui.TabLeiste;
import gui.WahlFenster;
import importexport.ImportExportManager;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Bundestagswahl;

/**
 * Diese Klasse repräsentiert den Dialog, der sich öffnet, wenn man im Menü "Importieren" oder 
 * in der Tab- Leiste den "+"- Button klickt
 * 
 * Der Benutzer wird aufgefordert die zwei für die Berechnung der Sitzverteilung notwendigen Dateien
 * zu übergeben.
 * @author Manuel
 *
 */
public class ImportDialog extends JDialog {

	JFileChooser ergebnisseAuswahl = new JFileChooser();
	JFileChooser bewerberAuswahl = new JFileChooser();
	TabLeiste tabs;
	File[] eingeleseneDateien = new File[2];
	Programmfenster pf;

	public ImportDialog(TabLeiste tabs) {

		this.tabs = tabs;
		this.pf = tabs.getPf();


		/* Lässt bei beiden Dialogen nur csv- Dateien zu und beschreibt, welche
		 * Datei in welchem Dialog eingebeben werden soll
		 */
		//FileFilter ergebnisFilter = new FileNameExtensionFilter("Datei mit Wahlergebnissen", "csv");
		//FileFilter bewerberFilter = new FileNameExtensionFilter("Datei mit Wahlbewerbern", "csv");
		//ergebnisseAuswahl.setFileFilter(ergebnisFilter);
		//bewerberAuswahl.setFileFilter(bewerberFilter);
		ergebnisseAuswahl.setDialogTitle("Wahlergebnisse importieren");
		bewerberAuswahl.setDialogTitle("Wahlbewerber importieren");
		
		int rueckgabeWert = ergebnisseAuswahl.showOpenDialog(pf);
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {

			rueckgabeWert = bewerberAuswahl.showOpenDialog(pf);
			if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
				eingeleseneDateien[0] = ergebnisseAuswahl.getSelectedFile();
				eingeleseneDateien[1] = bewerberAuswahl.getSelectedFile();

				ImportExportManager i = new ImportExportManager();
				Bundestagswahl w = i.importieren(eingeleseneDateien);
				
				tabs.neuerTab(new WahlFenster(w), w.getName());
				
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

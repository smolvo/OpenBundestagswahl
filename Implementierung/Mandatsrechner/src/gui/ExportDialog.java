package gui;

import importexport.ImportExportManager;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Bundestagswahl;

public class ExportDialog extends JDialog{

	JFileChooser dateiAuswahl = new JFileChooser();
	Programmfenster pf;
	TabLeiste tabLeiste;
	
	public ExportDialog(TabLeiste tabs) {
		
		 this.tabLeiste = tabs;
		 this.pf = tabs.getPf();
		
		//allgemeine Anpassungen des Fensters
		setTitle("Exportieren");
		setSize(400,400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//fileChooser
		//lässt nur csv Dateien zu
		//FileFilter filter = new FileNameExtensionFilter("Datei, um Wahlergebnis zu speichern", "csv");
		//dateiAuswahl.setFileFilter(filter);
	
		int rueckgabeWert = dateiAuswahl.showSaveDialog(null);
		
		
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {

        	ImportExportManager i = new ImportExportManager();
        	Bundestagswahl w = ((WahlFenster) tabLeiste.getSelectedComponent()).getBtw();
			i.exportieren(dateiAuswahl.getSelectedFile().toString(), w);
        	
        } else {
        	JOptionPane.showMessageDialog(pf,
					"Speichern abgebrochen.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
        }
	
        
        
        

	
}
}

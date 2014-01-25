package main.java.gui.dialoge;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.gui.Programmfenster;
import main.java.gui.TabLeiste;
import main.java.gui.WahlFenster;
import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;
import main.java.steuerung.Steuerung;

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

        	
        	
        	String pfad = dateiAuswahl.getSelectedFile().toString();

        	Steuerung.getInstance().exportieren(pfad);
        } else {
        	JOptionPane.showMessageDialog(pf,
					"Speichern abgebrochen.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
        }
	
        
        
        

	
}
}

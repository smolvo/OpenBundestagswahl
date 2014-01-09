package gui;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportDialog extends JDialog{

	JFileChooser dateiAuswahl = new JFileChooser();
	
	public ImportDialog() {
		
		//allgemeine Anpassungen des Fensters
		setTitle("Importieren");
		setSize(400,400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//fileChooser
		//lässt nur csv Dateien zu
		FileFilter filter = new FileNameExtensionFilter("WahlDaten", "csv");
		dateiAuswahl.setFileFilter(filter);
		int rueckgabeWert = dateiAuswahl.showOpenDialog(null);
		
		
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
             //TODO Weiterleitung an Steuerung
            System.out.println("Die zu öffnende Datei ist: " +
                  dateiAuswahl.getSelectedFile().getName());
        } else {
        	System.out.println("keine datei ausgewählt");
        }
	
	
}
}

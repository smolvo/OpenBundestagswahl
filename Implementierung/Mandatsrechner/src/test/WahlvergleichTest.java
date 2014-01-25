package test;

import gui.VergleichsFenster;
import importexport.ImportExportManager;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import wahlvergleich.Wahlvergleich;
import model.Bundestagswahl;

public class WahlvergleichTest {

	
	public static void main (String[] args) {		
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w1 = null;
		try {
			w1 = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bundestagswahl w2 = w1;
		
		Wahlvergleich vergleich = new Wahlvergleich(w1, w2);

		VergleichsFenster fenster = new VergleichsFenster(vergleich);
		
		fenster.setVisible(true);
	}
}

package main.java.gui.ansicht.diagrammfenster;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Zweitstimme;

public class Test {

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Wahl 2013
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Zweitstimme> stimmen = w.getDeutschland().getZweitstimmen();
		for (int k = 0; k < 5; k++) {
			for (Kandidat kand : stimmen.get(k).getPartei().getMitglieder()) {
				kand.setMandat(Mandat.DIREKTMANDAT);
			}
		}
				
//		BundDiagramm dia = new BundDiagramm(w.getDeutschland());
//		frame.add(dia);
		frame.setVisible(true);
	}
}

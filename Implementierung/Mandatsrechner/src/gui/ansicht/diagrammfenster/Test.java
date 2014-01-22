package gui.ansicht.diagrammfenster;

import importexport.ImportExportManager;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import model.Bundestagswahl;
import model.Deutschland;
import model.Kandidat;
import model.Mandat;
import model.Zweitstimme;

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
				kand.setMandat(Mandat.DIREKMANDAT);
			}
		}
				
//		BundDiagramm dia = new BundDiagramm(w.getDeutschland());
//		frame.add(dia);
		frame.setVisible(true);
	}
}
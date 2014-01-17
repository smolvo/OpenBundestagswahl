package gui.ansicht.tabellenfenster;


import importexport.ImportExportManager;

import java.io.File;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Bundesland;
import model.Bundestagswahl;
import model.Partei;
import model.Zweitstimme;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		
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
		
		i.exportieren("files/Exported.csv",w);

		JTabbedPane reiter = new JTabbedPane();
		TabellenFenster tabelle1 = new TabellenFenster();
		TabellenFenster tabelle2 = new TabellenFenster();
		TabellenFenster tabelle3 = new TabellenFenster();
		
		tabelle1.tabellenFuellen(w.getDeutschland());
		tabelle2.tabellenFuellen(w.getDeutschland().getBundeslaender().get(7));
		tabelle3.tabellenFuellen(w.getDeutschland().getBundeslaender().get(7).getWahlkreise().get(12));
		
		
		frame.add(reiter);
		reiter.add("Deutschland", tabelle1);
		reiter.add("Baden-Württemberg", tabelle2);
		reiter.add("Aalen-Heidenheim", tabelle3);
		
		frame.setVisible(true);
		
		
	}

}

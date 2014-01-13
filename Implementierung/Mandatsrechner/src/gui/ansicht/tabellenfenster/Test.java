package gui.ansicht.tabellenfenster;


import importexport.ImportExportManager;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Bundestagswahl;
import model.Partei;
import model.Zweitstimme;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		
		ImportExportManager i = new ImportExportManager();
		File csvDatei = new File("files/Ergebnis2013.csv");
		Bundestagswahl w = i.importieren(csvDatei);
		
		i.exportieren("files/Exported.csv",w);

		JTabbedPane reiter = new JTabbedPane();
		TabellenFenster tabelle = new TabellenFenster();
		
		tabelle.tabellenFuellen(w.getDeutschland());
		
		frame.add(reiter);
		reiter.add("Deutschland", tabelle);
		
		frame.setVisible(true);
		
		
	}

}

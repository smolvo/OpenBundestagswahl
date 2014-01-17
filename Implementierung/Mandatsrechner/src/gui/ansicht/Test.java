package gui.ansicht;

import importexport.ImportExportManager;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Bundestagswahl;

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
		
		JTabbedPane tab = new JTabbedPane();
		Listenansicht liste = new Listenansicht(w.getDeutschland());
		tab.add(liste);
		
		frame.add(tab);
		frame.setVisible(true);
	}

}

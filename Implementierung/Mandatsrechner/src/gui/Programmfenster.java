package gui;

import importexport.ImportExportManager;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import model.Bundestagswahl;

/**
 * Die Klasse Programmfenster repräsentiert die allgemeine Ansicht des Programms
 * 
 * @author Manuel
 *
 */

public final class Programmfenster extends JFrame{

	private Menu menu = null;
	private List<WahlFenster> wahlen = new ArrayList<WahlFenster>();
	private TabLeiste tabs;
	
	public static void main(String[] args) {
		
		new Programmfenster();
	}
	
	public Programmfenster() {	
		//allgemeine Anpassungen des Programmfensters
	    setTitle("Mandatsverteilung im Deutschen Bundestag");
		setSize(1024,768);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Image icon = new ImageIcon("src/gui/resources/images/wahl2.png").getImage();
		setIconImage(icon);
	
		//Menü- Leiste erstellen
		menu = new Menu(this);
		setJMenuBar(menu);
		
		//Wahl 2013
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = i.importieren(csvDateien);

		
		wahlen.add(new WahlFenster(w));
		
		
		
		//Tab- Leiste erstellen	
		tabs = new TabLeiste(this);
		this.add(tabs);
		
		setVisible(true);
	}
	
	
	/**
	 * Diese Methode wechselt zu einem anderen geöffneten Tab
	 * @param wf Tab, zu dem gewechselt werden soll
	 */
	
	public void wechsleTab(WahlFenster wf) {
		for (WahlFenster w : wahlen) {
			if (w.equals(wf)) {
				tabs.setSelectedComponent(wf);
			}
			
		}
	}
	
	
	
	
   
   
  
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}


	public List<WahlFenster> getWahlen() {
		return wahlen;
	}

	public void setWahlen(List<WahlFenster> wahlen) {
		this.wahlen = wahlen;
	}

	public TabLeiste getTabs() {
		return tabs;
	}

	public void setTabs(TabLeiste tabs) {
		this.tabs = tabs;
	}

	 
	 
	 

}

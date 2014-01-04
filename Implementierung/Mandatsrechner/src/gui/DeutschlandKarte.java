package gui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


public class DeutschlandKarte extends JPanel {

	
	public void paintComponent(Graphics g) {
		ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bayern.png");
		ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Baden-Württemberg.png");
		ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Berlin.png");
		ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Brandenburg.png");
		ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bremen.png");
		ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Hamburg.png");
		ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Hessen.png");
		ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Mecklenburg-Vorpommern.png");
		ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Niedersachsen.png");
		ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Nordrhein-Westfalen.png");
		ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Rheinland-Pfalz.png");
		ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Saarland.png");
		ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Sachsen.png");
		ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Sachsen-Anhalt.png");
		ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Schleswig-Holstein.png");
		ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Thüringen.png");

		
		badenWuerttemberg.paintIcon(this, g, 30, 229);
		bayern.paintIcon(this, g, 79, 202);
		berlin.paintIcon(this, g, 207, 108);
		brandenburg.paintIcon(this, g, 167, 75);
		bremen.paintIcon(this, g, 84, 70);
		hamburg.paintIcon(this, g, 119, 54);
		hessen.paintIcon(this, g, 47, 147);
		mecklenburgVorpommern.paintIcon(this, g, 145, 27);
		niedersachsen.paintIcon(this, g, 30, 45);
		nordrheinWestfalen.paintIcon(this, g, 0, 100);
		rheinlandPfalz.paintIcon(this, g, 1, 170);
		saarland.paintIcon(this, g, 5, 227);
		sachsen.paintIcon(this, g, 169, 154);
		sachsenAnhalt.paintIcon(this, g, 134, 89);
		schleswigHolstein.paintIcon(this, g, 94, 0);
		thueringen.paintIcon(this, g, 108, 150);
	}
	
	
}

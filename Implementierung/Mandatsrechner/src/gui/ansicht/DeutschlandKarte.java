package gui.ansicht;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Bundesland;
import model.Deutschland;


/**
 * Diese Klasse repräsentiert die kartographische Ansicht im Kartenfenster.
 *
 */
public class DeutschlandKarte extends JPanel {

	/** alle Bundesländer, nötig für Färbung */
	private Deutschland land;
	
	/**
	 * Konstruktor der Klasse.
	 * @param land das gesamte Land
	 */
	public DeutschlandKarte(Deutschland land) {
		this.land = land;
	}
	
	/**
	 * @param g  
	 * 
	 */
	public void paintComponent(Graphics g) {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		
		for (Bundesland bl: bundeslaender) {
			if (bl.getName().equals("Baden-Württemberg")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Baden-Württemberg.png");
				BufferedImage badenWuerttemberg = faerbeLand(bwfarbe, landimg);
				g.drawImage(badenWuerttemberg, 30, 229, this);
			} else if (bl.getName().equals("Bayern")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Bayern.png");
				BufferedImage bayern = faerbeLand(bwfarbe, landimg);
				g.drawImage(bayern, 79, 202, this);
			}  else if (bl.getName().equals("Berlin")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Berlin.png");
				BufferedImage berlin = faerbeLand(bwfarbe, landimg);
				g.drawImage(berlin, 207, 108, this);
			}  else if (bl.getName().equals("Brandenburg")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Brandenburg.png");
				BufferedImage brandenburg = faerbeLand(bwfarbe, landimg);
				g.drawImage(brandenburg, 167, 75, this);
			}  else if (bl.getName().equals("Bremen")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Bremen.png");
				BufferedImage bremen = faerbeLand(bwfarbe, landimg);
				g.drawImage(bremen, 84, 70, this);
			}  else if (bl.getName().equals("Hamburg")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Hamburg.png");
				BufferedImage hamburg = faerbeLand(bwfarbe, landimg);
				g.drawImage(hamburg, 119, 54, this);
			}  else if (bl.getName().equals("Hessen")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Hessen.png");
				BufferedImage hessen = faerbeLand(bwfarbe, landimg);
				g.drawImage(hessen, 47, 147, this);
			}  else if (bl.getName().equals("Mecklenburg-Vorpommern")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Mecklenburg-Vorpommern.png");
				BufferedImage meckpom = faerbeLand(bwfarbe, landimg);
				g.drawImage(meckpom, 145, 27, this);
			}  else if (bl.getName().equals("Niedersachsen")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Niedersachsen.png");
				BufferedImage niedersachsen = faerbeLand(bwfarbe, landimg);
				g.drawImage(niedersachsen, 30, 45, this);
			}  else if (bl.getName().equals("Nordrhein-Westfalen")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Nordrhein-Westfalen.png");
				BufferedImage nrw = faerbeLand(bwfarbe, landimg);
				g.drawImage(nrw, 0, 100, this);
			}  else if (bl.getName().equals("Rheinland-Pfalz")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Rheinland-Pfalz.png");
				BufferedImage rlp = faerbeLand(bwfarbe, landimg);
				g.drawImage(rlp, 1, 170, this);
			}  else if (bl.getName().equals("Saarland")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Saarland.png");
				BufferedImage saarland = faerbeLand(bwfarbe, landimg);
				g.drawImage(saarland, 5, 227, this);
			}  else if (bl.getName().equals("Sachsen-Anhalt")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Sachsen-Anhalt.png");
				BufferedImage sachsenAnhalt = faerbeLand(bwfarbe, landimg);
				g.drawImage(sachsenAnhalt, 134, 89, this);
			}  else if (bl.getName().equals("Sachsen")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Sachsen.png");
				BufferedImage sachsen = faerbeLand(bwfarbe, landimg);
				g.drawImage(sachsen, 169, 154, this);
			}  else if (bl.getName().equals("Schleswig-Holstein")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Schleswig-Holstein.png");
				BufferedImage schleswigHolstein = faerbeLand(bwfarbe, landimg);
				g.drawImage(schleswigHolstein, 94, 0, this);
			}  else if (bl.getName().equals("Thüringen")) {
				Color bwfarbe = bl.getFarbe();
				BufferedImage landimg = importImg("src\\gui\\resources\\Bundeslaender\\Thüringen.png");
				BufferedImage thueringen = faerbeLand(bwfarbe, landimg);
				g.drawImage(thueringen, 108, 150, this);
			}
			}
			}
		
		private BufferedImage faerbeLand(Color bwfarbe, BufferedImage bild) {
			/*int width = bild.getWidth();
	        int height = bild.getHeight();

	        for (int xx = 0; xx < width; xx++) {
	            for (int yy = 0; yy < height; yy++) {
	                Color originalColor = new Color(bild.getRGB(xx, yy), true);
	                if (originalColor.equals(Color.WHITE) && originalColor.getAlpha() == 255) {
	                    bild.setRGB(xx, yy, bwfarbe.getRGB());
	                }
	            }
	        }*/
	        return bild;
		}
	
	
	
		
		
		
		
		
		
		private BufferedImage importImg(String pfad) {
			try {
				BufferedImage landimg = ImageIO.read(new File(pfad));
				return landimg;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
}

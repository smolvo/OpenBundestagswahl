package main.java.gui.ansicht;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Collections;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.java.model.Bundesland;
import main.java.model.Deutschland;


/**
 * Diese Klasse repräsentiert die kartographische Ansicht im Kartenfenster.
 *
 */
public class DeutschlandKarte extends JPanel {

	/** alle Bundeslaender, nötig für Färbung */
	private Deutschland land;
	
	/**
	 * Konstruktor der Klasse.
	 * @param land das gesamte Land
	 */
	public DeutschlandKarte(Deutschland land) {
		this.land = land;
	}
	
	/** wahr wenn bereits bilder importiert wurden, sonst falsch */
	boolean bilderImportiert = false;
	
	/** in diesem array werden die importierten Bilder der Bundeslaender gespeichert */
	BufferedImage[] bundeslandBilder = new BufferedImage[16];
	
	/** in diesem Array werden die Namen der importierten Bundeslaender gespeichert */
	String[] bundeslandNamen = new String[16];
	
	/** in diesem Array werden die Bilder der Bundeslaender nachdem sie eingefärbt wurden gespeichert */
	BufferedImage[] bundeslandBilderGefaerbt = new BufferedImage[16];

	/** Dieser String gibt den Pfad des Speicherortes der Bundesland-Bilder an */
	String pfad = "src/gui/resources/Bundeslaender/";
	
	/** erstellt ein BufferedImage in der größe 900 * 1024  */
	BufferedImage grossVersion = new BufferedImage(900, 1024, BufferedImage.TYPE_INT_ARGB);
	
	/**  */
	Image skaliert = null;
	
	/** skaliert das Bild nachdem die Bilder der Bundeslaender hinzugefügt 
	 * wurden entsprechend der aktuellen größe des JPanels 
	 */
	public void skaliere() {
	skaliert = grossVersion.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
	}
	
	/**
	 * @param g  
	 * führt alle noetigen Aktionen aus um die eingefaerbte Deutschlandkare zu erstellen
	 */
	public void paintComponent(Graphics g) {
		if (!bilderImportiert) {
			importieren();
			skaliere();
		}
			
			faerbeLand();
			erstelleGrafik();
			g.drawImage(skaliert, 0, 0, this);
		
	}
	
	/**
	 * ruft der Reihe nach die Bilder der Bundeslaender Deutschlands ab und legt sie in einem Array ab
	 */
	private void importieren() {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		Collections.sort(bundeslaender);
		for (int i = 0; i < 16; i++) {
				bundeslandBilder[i] = importImg(pfad + bundeslaender.get(i).getName() + ".png");
				bundeslandNamen[i] = bundeslaender.get(i).getName();
		}
		bilderImportiert = true;
	}
	
	/**
	 * erstellt die Deutschlandkarte indem es die eingefaerbten Bilder anhand der Koordinaten abbildet
	 */
	private void erstelleGrafik() {
		Graphics2D grossVersionGraphics = grossVersion.createGraphics();
		grossVersionGraphics.setBackground(Color.WHITE);
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[0], 175, 654, this);
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[1], 303, 552, this);
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[2], 620, 294, this);
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[3], 480, 182, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[4], 272, 226, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[5], 366, 165, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[6], 209, 428, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[7], 403, 34, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[8], 124, 94, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[9], 64, 319, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[10], 77, 506, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[11], 93, 664, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[12], 534, 418, this);
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[13], 421, 249, this);
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[14], 230, 0, this);			
		grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[15], 376, 426, this);
	}

	
	/**
	 * faerbt die Bilder der Bundeslaender entsprechend der Farbe der Partei die 
	 * in diesem Bundesland den höchsten Zweitstimmenanteil hat. Das gefaerbte Bild wird in
	 * dem Array bundeslandBilderGefaerbt abgelegt.
	 */
	private void faerbeLand() {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		Collections.sort(bundeslaender);
		for (int i = 0; i < 16; i++) {
			BufferedImage aktuellesBild = bundeslandBilder[i];
			Color bwfarbe = bundeslaender.get(i).getFarbe();
			try {
				int hoehe = aktuellesBild.getHeight();
				int breite = aktuellesBild.getWidth();
					for (int xx = 0; xx < breite; xx++) {
						for (int yy = 0; yy < hoehe; yy++) {
							Color originalColor = new Color(aktuellesBild.getRGB(xx, yy), true);
							if (originalColor.equals(Color.WHITE) && originalColor.getAlpha() == 255) {
								aktuellesBild.setRGB(xx, yy, bwfarbe.getRGB());
							}
						}
					}
			} catch (NullPointerException nPE) {
				System.out.println("Keine Farbe vorhanden!");
			}
	    bundeslandBilderGefaerbt[i] = aktuellesBild;
		}
	}
	
	/**
	 * importiert die am Uebergebenen Pfad vorgefundene Datei
	 * @param pfad der Speicherort der zu importierenden Datei
	 * @return die importierte Datei
	 */
	private BufferedImage importImg(String pfad) {
			try {
				BufferedImage landBild = ImageIO.read(new File(pfad));
				return landBild;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
}
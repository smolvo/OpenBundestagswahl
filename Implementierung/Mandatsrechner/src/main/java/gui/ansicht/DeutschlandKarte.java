package main.java.gui.ansicht;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.java.model.Bundesland;
import main.java.model.Deutschland;


/**
 * Diese Klasse repr�sentiert die kartographische Ansicht im Kartenfenster.
 *
 */
public class DeutschlandKarte extends JPanel {

	/** alle Bundeslaender, n�tig f�r F�rbung */
	private Deutschland land;
	
	boolean groesseGeaendert = true;
	
	/**
	 * Konstruktor der Klasse.
	 * @param land das gesamte Land
	 */
	public DeutschlandKarte(Deutschland land) {
		this.land = land;
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				System.out.println("geklickt an: " + e.getPoint());
				if (klickAufKarte(e.getPoint())) {
					System.out.println("auf karte geklickt");
				}
				
			}
		});
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				skaliere();
			}
		});
	}
	
	
	
	/** wahr wenn bereits bilder importiert wurden, sonst falsch */
	boolean bilderImportiert = false;
	
	/** in diesem array werden die importierten Bilder der Bundeslaender gespeichert */
	BufferedImage[] bundeslandBilder = new BufferedImage[16];
	
	/** in diesem Array werden die Bilder der Bundeslaender nachdem sie eingef�rbt wurden gespeichert */
	BufferedImage[] bundeslandBilderGefaerbt = new BufferedImage[16];
	
	/** in diesem Array werden die Namen der importierten Bundeslaender gespeichert */
	String[] bundeslandNamen = new String[16];
	
	Double[] posX = new Double[] {0.1944, 0.3366, 0.6888, 0.5333, 0.3022, 0.4066, 0.2322, 0.4477, 0.1377, 0.0711, 0.0855, 0.1033, 0.5933, 0.4677, 0.2555, 0.4177};
	
	Double[] posY = new Double[] {0.6387, 0.5391, 0.2871, 0.1777, 0.2207, 0.1611, 0.4180, 0.0332, 0.0918, 0.3115, 0.4941, 0.6484, 0.4082, 0.2432, 0.0, 0.4160};

	/** Dieser String gibt den Pfad des Speicherortes der Bundesland-Bilder an */
	String pfad = "src/main/resources/gui/bundeslaender/";
	
	/** erstellt ein BufferedImage in der gr��e 900 * 1024  */
	BufferedImage grossVersion = new BufferedImage(900, 1024, BufferedImage.TYPE_INT_ARGB);
	
	/**  */
	Image skaliert = null;
	
	/** skaliert das Bild nachdem die Bilder der Bundeslaender hinzugef�gt 
	 * wurden entsprechend der aktuellen gr��e des JPanels 
	 */
	private void skaliere() {
		skaliert = grossVersion.getScaledInstance((int)(0.879*this.getHeight()), this.getHeight(), Image.SCALE_SMOOTH);
	}
	
	private boolean klickAufKarte(Point klick) {
		if ((klick.getX() < skaliert.getWidth(null)) && (klick.getY() < skaliert.getHeight(null))) {
		return true;
		}
		return false;
	}
	
	/**
	 * @param g  
	 * f�hrt alle noetigen Aktionen aus um die eingefaerbte Deutschlandkare zu erstellen
	 */
	public void paintComponent(Graphics g) {
		
		if (!bilderImportiert) {
			importieren();
			faerbeLand();
			erstelleGrafik();
			skaliere();
		}
		g.drawImage(skaliert,0, 0, this);
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
		int panelBreite = grossVersion.getWidth();
		int panelHoehe = grossVersion.getHeight();
		for (int i = 0; i < 16; i++) {
			
			grossVersionGraphics.drawImage(bundeslandBilderGefaerbt[i], (int)(posX[i]*panelBreite), (int)(posY[i]*panelHoehe),null);
		}
	}

	/**
	 * faerbt die Bilder der Bundeslaender entsprechend der Farbe der Partei die 
	 * in diesem Bundesland den h�chsten Zweitstimmenanteil hat. Das gefaerbte Bild wird in
	 * dem Array bundeslandBilderGefaerbt abgelegt.
	 */
	private void faerbeLand() {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		Collections.sort(bundeslaender);
		for (Bundesland bl: bundeslaender) {
			bl.setFarbe(bl.ermittleStaerkstePartei().getFarbe());
		}
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
				bilderImportiert = true;
				return landBild;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}


}
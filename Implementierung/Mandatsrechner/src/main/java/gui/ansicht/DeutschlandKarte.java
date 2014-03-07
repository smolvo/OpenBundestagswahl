package main.java.gui.ansicht;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.java.model.Bundesland;
import main.java.model.Deutschland;
import main.java.model.Partei;
import test.java.Debug;

/**
 * Diese Klasse repräsentiert die kartographische Ansicht im Kartenfenster.
 * 
 */
public class DeutschlandKarte extends JPanel {

	/**
	 * Automatisch generierte serialVersionUID die für das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -3499619191276735615L;

	/** alle Bundeslaender, nötig für Färbung */
	private final Deutschland land;

	/**
	 * true wenn das Bild bereits an die Fenstergroesse angepasst wurde, sonst
	 * false
	 */
	private boolean skaliert = false;

	/** wahr wenn bereits bilder importiert wurden, sonst falsch */
	private boolean bilderImportiert = false;

	/**
	 * in diesem array werden die importierten Bilder der Bundeslaender
	 * gespeichert
	 */
	private final BufferedImage[] bundeslandBilder = new BufferedImage[16];

	/**
	 * in diesem Array werden die Bilder der Bundeslaender nachdem sie
	 * eingefï¿½rbt wurden gespeichert
	 */
	private final BufferedImage[] bundeslandBilderGefaerbt = new BufferedImage[16];

	/**
	 * in diesem Array werden die Namen der importierten Bundeslaender
	 * gespeichert
	 */
	private final String[] bundeslandNamen = new String[16];

	/** die x-Koordinaten der Bilder relativ zur Bildgroesse */
	private final Double[] posX = new Double[] { 0.1944, 0.3366, 0.6888,
			0.5333, 0.3022, 0.4066, 0.2322, 0.4477, 0.1377, 0.0711, 0.0855,
			0.1033, 0.5933, 0.4677, 0.2555, 0.4177 };

	/** die y-Koordinaten der Bilder relativ zur Bildgroesse */
	private final Double[] posY = new Double[] { 0.6387, 0.5391, 0.2871,
			0.1777, 0.2207, 0.1611, 0.4180, 0.0332, 0.0918, 0.3115, 0.4941,
			0.6484, 0.4082, 0.2432, 0.0, 0.4160 };

	/** Dieser String gibt den Pfad des Speicherortes der Bundesland-Bilder an */
	private final String pfad = "src/main/resources/gui/bundeslaender/";

	/** erstellt ein BufferedImage in der grï¿½ï¿½e 900 * 1024 */
	private final BufferedImage grossVersion = new BufferedImage(900, 1024,
			BufferedImage.TYPE_INT_ARGB);

	/** die darzustellende Karte nach der skalierung */
	private Image skalierteKarte = null;

	/**
	 * Konstruktor der Klasse.
	 * 
	 * @param land
	 *            das gesamte Land
	 * @throws IllegalArgumentException
	 *             wenn das Deutschland-objekt leer ist.
	 */
	public DeutschlandKarte(Deutschland land) {
		if (land == null) {
			throw new IllegalArgumentException("Deutschland-Objekt ist null.");
		}
		this.land = land;
		if (!this.bilderImportiert) {
			importieren();
		}
		faerbeLand();
		erstelleGrafik();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				DeutschlandKarte.this.skaliert = false;
			}
		});
	}

	/**
	 * erstellt die Deutschlandkarte indem es die eingefaerbten Bilder anhand
	 * der Koordinaten abbildet
	 */
	private void erstelleGrafik() {
		final Graphics2D grossVersionGraphics = this.grossVersion
				.createGraphics();
		grossVersionGraphics.setBackground(Color.WHITE);
		final int panelBreite = this.grossVersion.getWidth();
		final int panelHoehe = this.grossVersion.getHeight();
		for (int i = 0; i < 16; i++) {
			grossVersionGraphics.drawImage(this.bundeslandBilderGefaerbt[i],
					(int) (this.posX[i] * panelBreite),
					(int) (this.posY[i] * panelHoehe), null);
		}
	}

	/**
	 * faerbt die Bilder der Bundeslaender entsprechend der Farbe der Partei die
	 * in diesem Bundesland den hï¿½chsten Zweitstimmenanteil hat. Das gefaerbte
	 * Bild wird in dem Array bundeslandBilderGefaerbt abgelegt.
	 */
	private void faerbeLand() {
		final LinkedList<Bundesland> bundeslaender = this.land
				.getBundeslaender();
		Collections.sort(bundeslaender);
		for (final Bundesland bl : bundeslaender) {
			final Partei staerkstePartei = bl.ermittleStaerkstePartei();
			if (staerkstePartei != null) {
				bl.setFarbe(bl.ermittleStaerkstePartei().getFarbe());
			}
		}
		for (int i = 0; i < 16; i++) {
			final BufferedImage aktuellesBild = this.bundeslandBilder[i];
			final Color bwfarbe = bundeslaender.get(i).getFarbe();
			Debug.print(bundeslaender.get(i) + " "
					+ bundeslaender.get(i).getFarbe(), 5);
			try {
				final int hoehe = aktuellesBild.getHeight();
				final int breite = aktuellesBild.getWidth();
				for (int xx = 0; xx < breite; xx++) {
					for (int yy = 0; yy < hoehe; yy++) {
						final Color originalColor = new Color(
								aktuellesBild.getRGB(xx, yy), true);
						if (originalColor.equals(Color.WHITE)
								&& originalColor.getAlpha() == 255) {
							aktuellesBild.setRGB(xx, yy, bwfarbe.getRGB());
						}
					}
				}
			} catch (final NullPointerException nPE) {
				System.out.println("Keine Farbe vorhanden!");
			}
			this.bundeslandBilderGefaerbt[i] = aktuellesBild;
		}
	}

	/**
	 * ruft der Reihe nach die Bilder der Bundeslaender Deutschlands ab und legt
	 * sie in einem Array ab
	 */
	private void importieren() {
		final LinkedList<Bundesland> bundeslaender = this.land
				.getBundeslaender();
		Collections.sort(bundeslaender);
		for (int i = 0; i < 16; i++) {
			this.bundeslandBilder[i] = importImg(this.pfad
					+ bundeslaender.get(i).getName() + ".png");
			this.bundeslandNamen[i] = bundeslaender.get(i).getName();
		}
		this.bilderImportiert = true;
	}

	/**
	 * importiert die am Uebergebenen Pfad vorgefundene Datei
	 * 
	 * @param pfad
	 *            der Speicherort der zu importierenden Datei
	 * @return die importierte Datei
	 */
	private BufferedImage importImg(String pfad) {
		try {
			final BufferedImage landBild = ImageIO.read(new File(pfad));
			this.bilderImportiert = true;
			return landBild;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Zeichnet
	 * 
	 * @param g
	 *            grafik
	 * @throws IllegalArgumentException
	 *             wenn das Graphics-Objekt ist null.
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (g == null) {
			throw new IllegalArgumentException("Graphics-Objekt ist null.");
		}
		if (!this.skaliert) {
			skaliere();
		}
		g.drawImage(this.skalierteKarte, 0, 0, null);
	}

	/**
	 * skaliert das Bild nachdem die Bilder der Bundeslaender hinzugefügt wurden
	 * entsprechend der aktuellen Größe des JPanels
	 */
	private void skaliere() {
		this.skalierteKarte = this.grossVersion.getScaledInstance(
				(int) (0.879 * getHeight()), getHeight(), Image.SCALE_SMOOTH);
		this.skaliert = true;
	}
}
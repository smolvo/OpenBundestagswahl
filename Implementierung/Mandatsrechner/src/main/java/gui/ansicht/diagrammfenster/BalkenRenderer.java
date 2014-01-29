package main.java.gui.ansicht.diagrammfenster;

import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer;

/**
 * Diese Klasse hilft die Farben der Balkendiagramme festzulegen.
 * 
 */
public class BalkenRenderer extends BarRenderer {

	private static final long serialVersionUID = 3223840021896221244L;

	/** repr�sentiert die Farben */
	private Paint[] farben;

	/**
	 * Der Konstruktor legt die Farben fest.
	 * 
	 * @param farben
	 *            Farben
	 * @throws IllegalArgumentException
	 *             wenn die Farben null sind
	 */
	public BalkenRenderer(final Paint[] farben) {
		if (farben == null) {
			throw new IllegalArgumentException("Farben sind null");
		}
		this.farben = farben;
	}

	/**
	 * Diese Methode legt die Farben der Farben fest. �berschreibt das Verhalten
	 * des AbstractSeriesRenderer.
	 * 
	 * @param zeile
	 *            Zeile
	 * @param spalte
	 *            Balken
	 * @throws IllegalArgumentException
	 *             wenn negative Werte �bergeben werden.
	 * 
	 * @return Farbe
	 */
	public Paint getItemPaint(final int zeile, final int spalte) {
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Negative Werte f�r Zeilen und/oder Spalte.");
		}
		return this.farben[spalte % this.farben.length];
	}
}
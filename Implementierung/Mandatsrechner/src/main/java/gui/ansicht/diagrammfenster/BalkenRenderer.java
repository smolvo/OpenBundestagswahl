package main.java.gui.ansicht.diagrammfenster;

import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer;

/**
 * Diese Klasse hilft die Farben der Balkendiagramme festzulegen.
 * 
 * @author Anton
 * 
 */
public class BalkenRenderer extends BarRenderer {

	/** repräsentiert die Farben */
	private Paint[] farben;

	/**
	 * Der Konstruktor legt die Farben fest.
	 * 
	 * @param farben
	 *            Farben
	 */
	public BalkenRenderer(final Paint[] farben) {
		this.farben = farben;
	}

	/**
	 * Diese Methode legt die Farben der Farben fest. Überschreibt das Verhalten
	 * des AbstractSeriesRenderer.
	 * 
	 * @param zeile
	 *            Zeile
	 * @param spalte
	 *            Balken
	 * 
	 * @return Farbe
	 */
	public Paint getItemPaint(final int zeile, final int spalte) {
		return this.farben[spalte % this.farben.length];
	}
}
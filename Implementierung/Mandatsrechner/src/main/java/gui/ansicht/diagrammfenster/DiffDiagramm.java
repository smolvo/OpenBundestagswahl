package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import main.java.wahlvergleich.ParteiDifferenzen;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 */
public class DiffDiagramm extends JPanel {
	
	private static final long serialVersionUID = -2433019298048096112L;

	/**
	 * Der Konstruktor erstellt ein Differenzen-Diagramm.
	 * @param diff die Differenzen
	 */
	public DiffDiagramm(ParteiDifferenzen[] diff) {
		JFreeChart chart = createChart(diff);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				ChartPanel panel = (ChartPanel) e.getComponent();
				panel.setSize(resize());
				add(panel, BorderLayout.LINE_START);
			}

		});
		chartPanel.setPreferredSize(new Dimension(450, 250));
		this.add(chartPanel);
	}
	
	/**
	 * Erstellt das Diagramm.
	 * @param diff Differenzen
	 * @return Diagramm
	 */
	private JFreeChart createChart(ParteiDifferenzen[] diff) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		for (int i = 0; i < diff.length; i++) {
			result.setValue(diff[i].getDiff(), " ", diff[i].getPartei().getName());
		}
		JFreeChart chart = ChartFactory.createBarChart("Sitzdifferenzen", null,
				null, result, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        int min = getKleinste(diff) - 5;
        int max = getGroesstes(diff) + 5;
        rangeAxis.setRange(new Range(min, max));
        plot.setRangeAxis(rangeAxis);
        
		// färben der Parteienbalken
		Paint[] farben = new Paint[diff.length];
		for (int i = 0; i < diff.length; i++) {
			farben[i] = diff[i].getPartei().getFarbe();
		}
		BarRenderer barRenderer = new BalkenRenderer(farben);
		plot.setRenderer(barRenderer);
		plot.setForegroundAlpha(1.0f);

		return chart;
	}

	/**
	 * Diese Methode gibt eine Dimension, abhängig von der Fläche auf der sich
	 * das Diagramm befindet, aus.
	 * 
	 * @return Dimension
	 */
	public Dimension resize() {
		return new Dimension(this.getWidth(),
				(int) (this.getHeight()));
	}
	
	/**
	 * Gibt die kleinste Differenz aus.
	 * @param diff Differenzen-Vektor
	 * @return kleinstes Element
	 */
	private int getKleinste(ParteiDifferenzen[] diff) {
		int kleinstes = Integer.MAX_VALUE;
		for (int i = 0; i < diff.length; i++) {
			if (diff[i].getDiff() < kleinstes) {
				kleinstes = diff[i].getDiff();
			}
		}
		return kleinstes;
	}
	
	/**
	 * Gibt die größte Differenz aus.
	 * @param diff Differenzen-Vektor
	 * @return größtes Element
	 */
	private int getGroesstes(ParteiDifferenzen[] diff) {
		int groesstes = Integer.MIN_VALUE;
		for (int i = 0; i < diff.length; i++) {
			if (diff[i].getDiff() > groesstes) {
				groesstes = diff[i].getDiff();
			}
		}
		return groesstes;
	}
}

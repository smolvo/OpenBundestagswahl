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
 * Diese Klasse zeigt die Diagramme der Differenzen.
 */
public class DiffDiagramm extends JPanel {

	private static final long serialVersionUID = -2433019298048096112L;

	/**
	 * Der Konstruktor erstellt ein Differenzen-Diagramm.
	 * 
	 * @param diff
	 *            die Differenzen
	 * @throws IllegalArgumentException
	 *             wenn die Parteidifferenzen null sind.
	 */
	public DiffDiagramm(ParteiDifferenzen[] diff) {
		if (diff == null) {
			throw new IllegalArgumentException("PartDifferenzen ist null.");
		}
		final JFreeChart chart = createChart(diff);
		final ChartPanel chartPanel = new ChartPanel(chart);
		setLayout(new BorderLayout());
		chartPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				final ChartPanel panel = (ChartPanel) e.getComponent();
				panel.setSize(resize());
				add(panel, BorderLayout.LINE_START);
			}

		});
		chartPanel.setPreferredSize(new Dimension(450, 250));
		this.add(chartPanel);
	}

	/**
	 * Erstellt das Diagramm.
	 * 
	 * @param diff
	 *            Differenzen
	 * @throws IllegalArgumentException
	 *             wenn die Parteidifferenzen null sind.
	 * @return Diagramm
	 */
	private JFreeChart createChart(ParteiDifferenzen[] diff) {
		if (diff == null) {
			throw new IllegalArgumentException("PartDifferenzen ist null.");
		}
		final DefaultCategoryDataset result = new DefaultCategoryDataset();
		for (final ParteiDifferenzen element : diff) {
			result.setValue(element.getDiff(), " ", element.getPartei()
					.getName());
		}
		final JFreeChart chart = ChartFactory.createBarChart("Sitzdifferenzen",
				null, null, result, PlotOrientation.VERTICAL, false, false,
				false);
		final CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		final int min = getKleinste(diff) - 5;
		final int max = getGroesstes(diff) + 5;
		rangeAxis.setRange(new Range(min, max));
		plot.setRangeAxis(rangeAxis);

		// färben der Parteienbalken
		final Paint[] farben = new Paint[diff.length];
		for (int i = 0; i < diff.length; i++) {
			farben[i] = diff[i].getPartei().getFarbe();
		}
		final BarRenderer barRenderer = new BalkenRenderer(farben);
		plot.setRenderer(barRenderer);
		plot.setForegroundAlpha(1.0f);

		return chart;
	}

	/**
	 * Gibt die größte Differenz aus.
	 * 
	 * @param diff
	 *            Differenzen-Vektor
	 * @throws IllegalArgumentException
	 *             wenn die Parteidifferenzen null sind.
	 * @return größtes Element
	 */
	private int getGroesstes(ParteiDifferenzen[] diff) {
		if (diff == null) {
			throw new IllegalArgumentException("PartDifferenzen ist null.");
		}
		int groesstes = Integer.MIN_VALUE;
		for (final ParteiDifferenzen element : diff) {
			if (element.getDiff() > groesstes) {
				groesstes = element.getDiff();
			}
		}
		return groesstes;
	}

	/**
	 * Gibt die kleinste Differenz aus.
	 * 
	 * @param diff
	 *            Differenzen-Vektor
	 * @throws IllegalArgumentException
	 *             wenn die PArteidifferenzen null sind.
	 * @return kleinstes Element
	 */
	private int getKleinste(ParteiDifferenzen[] diff) {
		if (diff == null) {
			throw new IllegalArgumentException("PartDifferenzen ist null.");
		}
		int kleinstes = Integer.MAX_VALUE;
		for (final ParteiDifferenzen element : diff) {
			if (element.getDiff() < kleinstes) {
				kleinstes = element.getDiff();
			}
		}
		return kleinstes;
	}

	/**
	 * Diese Methode gibt eine Dimension, abhängig von der Fläche auf der sich
	 * das Diagramm befindet, aus.
	 * 
	 * @return Dimension
	 */
	public Dimension resize() {
		return new Dimension(getWidth(), getHeight());
	}
}

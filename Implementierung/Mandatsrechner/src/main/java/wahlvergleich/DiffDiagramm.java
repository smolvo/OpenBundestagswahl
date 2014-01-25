package main.java.wahlvergleich;

import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JPanel;

import main.java.gui.ansicht.diagrammfenster.BalkenRenderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

public class DiffDiagramm extends JPanel {

	/**
	 * Der Konstruktor erstellt ein Differenzen-Diagramm.
	 * @param diff die Differenzen
	 */
	public DiffDiagramm(ParteiDifferenzen[] diff) {
		JFreeChart chart = createChart(diff);
		ChartPanel chartPanel = new ChartPanel(chart);
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
		JFreeChart chart = ChartFactory.createBarChart("Stimmenanteile", null,
				"proz. Zweitstimmen", result, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(new Range(-20, 20));
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
}

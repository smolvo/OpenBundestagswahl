package gui.ansicht;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import model.Bundesland;
import model.Partei;
import model.Zweitstimme;

/**
 * Diese Klasse stellt das Diagramm der Landesansicht dar.
 * @author Anton
 *
 */
public class LandDiagramm {


	private static final long serialVersionUID = 5982851750497212488L;

	/** reptäsentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final JPanel flaeche;
	
	/**
	 * Der Konstruktor erstellt das Diagramm.
	 * @param bundLand Bundesland
	 */
	public LandDiagramm(Bundesland bundLand, JPanel flaeche) {
		this.flaeche = flaeche;
        JFreeChart chart = createChart(bundLand);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 200));
        flaeche.add(chartPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Diese Methode erstellt das Diagramm.
	 * @param bundLand Bundesland
	 * @return Diagramm
	 */
	private JFreeChart createChart(Bundesland bundLand) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Zweitstimme> zw = bundLand.getZweitstimmen();
		for (int i = 0; i < 4; i++) {
			double proZweit = (Math.rint(((double) zw.get(i).getAnzahl()
					/ (double) bundLand.getZweitstimmeGesamt()) * 1000) / 10);
			parteien.add(zw.get(i).getPartei());
			result.setValue(proZweit, " ", zw.get(i).getPartei().getName());
		}
		double sonstige = 0;
		for (int i = 4; i < zw.size(); i++) {
			sonstige += (Math.rint(((double) zw.get(i).getAnzahl()
					/ (double) bundLand.getZweitstimmeGesamt()) * 1000) / 10);
		}
		result.setValue(sonstige, " ", "Sonstige");
		
		JFreeChart chart = ChartFactory.createBarChart("Stimmenanteile", null, null,
	    		result, PlotOrientation.VERTICAL, false, false, false);
	    CategoryPlot plot = chart.getCategoryPlot();
	    BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
	    
	    	// färben
	    for (int i = 0; i < parteien.size(); i++) {
	       	barRenderer.setSeriesPaint(i, parteien.get(i).getFarbe());
        }
	    plot.setForegroundAlpha(1.0f);
	  
	    return chart;
	}
}

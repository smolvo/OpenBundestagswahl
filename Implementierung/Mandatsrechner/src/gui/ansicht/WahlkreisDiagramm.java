package gui.ansicht;

import java.awt.BorderLayout;
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

import model.Erststimme;
import model.Partei;
import model.Wahlkreis;

/**
 * Diese Klasse repr�sentiert das Diagramm der Wahlkreisansicht.
 * @author Anton
 *
 */
public class WahlkreisDiagramm extends JPanel {

	/**
	 * Der Konstruktor erstellt das Diagramm.
	 * @param wk Wahlkreis
	 */
	public WahlkreisDiagramm(Wahlkreis wk) {
		JFreeChart chart = createChart(wk);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Diese Mehthode wird vom Konstruktor verwendet, um das
	 * Diagramm zu erstellen.
	 * @param wk der Wahlkreis
	 * @return JFreeChart
	 */
	private JFreeChart createChart(Wahlkreis wk) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Erststimme> er = wk.getErststimmen();
		for (int i = 0; i < 4; i++) {
			double proZweit = (Math.rint(((double) er.get(i).getAnzahl() / 
					(double) wk.getErststimmeGesamt()) * 1000) / 10);
			parteien.add(er.get(i).getKandidat().getPartei());
			result.setValue(proZweit, " ", er.get(i).getKandidat().getPartei().getName());
		}
		double sonstige = 0;
		for (int i = 4; i < er.size(); i++) {
			sonstige += (Math.rint(((double) er.get(i).getAnzahl() / 
					(double) wk.getErststimmeGesamt()) * 1000) / 10);
		}
		result.setValue(sonstige, " ", "Sonstige");
		
		JFreeChart chart = ChartFactory.createBarChart("Stimmenanteile", null, null,
	    		result, PlotOrientation.VERTICAL, false, false, false);
	    CategoryPlot plot = chart.getCategoryPlot();
	    BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
	    
	    	// f�rben
	    for (int i = 0; i < parteien.size(); i++) {
	       	barRenderer.setSeriesPaint(i, parteien.get(i).getFarbe());
        }
	    plot.setForegroundAlpha(1.0f);
	  
	    return chart;
	}
}
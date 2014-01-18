package gui.ansicht;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.Deutschland;
import model.Kandidat;
import model.Mandat;
import model.Partei;
import model.Zweitstimme;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

public class BundDiagramm extends JPanel {

	public BundDiagramm(Deutschland land) {
		this.setLayout(new BorderLayout());
        JFreeChart chart = createChart("Sitzverteilung", land);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        add(chartPanel, BorderLayout.CENTER);
    }
    
    
/** * Creates a chart */

    private JFreeChart createChart(String title, Deutschland land) {
    	ArrayList<Integer> daten = new ArrayList<Integer>();
    	ArrayList<Partei> parteien = new ArrayList<Partei>();
    	DefaultPieDataset result = new DefaultPieDataset();
    	int sum = 0;
    	for (Zweitstimme zw : land.getZweitstimmen()) {
    		int sitze = 0;
    		for (Kandidat kan : zw.getPartei().getMitglieder()) {
    			if (!(kan.getMandat().equals(Mandat.KEINMANDAT))) {
    				sitze++;
    			}
    		}
    		if (sitze > 0) {
	    		daten.add(sitze);
	    		parteien.add(zw.getPartei());
	    		result.setValue(zw.getPartei().getName(), sitze);
	    		sum += sitze;
    		}
    	}
    	// unteres viertel soll leer sein
    	float angle = (float) ((sum / 270.0) * 90.0);
    	result.setValue("", angle);
    	
    	JFreeChart chart = ChartFactory.createPieChart3D(title,
    		result, true, true, false);
    	PiePlot3D plot = (PiePlot3D) chart.getPlot();

    	// färben
        for (int i = 0; i < daten.size(); i++) {
        	plot.setSectionPaint(result.getKey(i), parteien.get(i).getFarbe());
        }
        Color nichts = new Color(0, 0, 0, 0);
        plot.setSectionPaint("", nichts);
        plot.setLabelLinksVisible(false);
        plot.setLabelBackgroundPaint(Color.white);
        
        plot.setStartAngle(225);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(1.0f);
        return chart;
        
    }
}

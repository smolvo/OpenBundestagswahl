package main.java.gui.dialoge;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 */
public class HandbuchDialog extends JDialog {
	
	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -8178331482830745352L;

	
	/** Die HTML-Datei die in der Webview angezeigt wird. */
	private static final File handbuchFile = 
			new File("src/main/resources/hilfe/tutorial1.html");
	
	/**
	 * 
	 */
	public HandbuchDialog() {
		
		// allgemeine Anpassungen des Fensters
		setTitle("Handbuch");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setResizable(true);
		setAlwaysOnTop(true);
		
		// wird benötigt um JavaFX Panel zu DISPOSEN
		Platform.setImplicitExit(false);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		GridLayout layout = new GridLayout();
		//GridBagLayout layout = new GridBagLayout();
		//GridBagConstraints gbc=new GridBagConstraints();
		// Festlegen, dass die GUI-Elemente die Gitterfelder in waagerechter Richtung ausfüllen:
		//bc.fill=GridBagConstraints.VERTICAL;
		// Als Layout festlegen
		
		
		setLayout(layout);
		
		/*
		 * Erste Spalte (index 0)
		 */
		//JLabel label = new JLabel("asdasd");
		//gbc.gridx = 0;
		//gbc.gridy = 0;
		//gbc.gridwidth = 1;
		//layout.setConstraints(label, gbc);
		//this.add(label, gbc);
		
		/*
		 * Zweite (index 1) bis vierte (index 3) Spalte 
		 */
		final JFXPanel fxPanel = new JFXPanel();
		//gbc.gridx = 1;
		//gbc.gridy = 0;
		//gbc.gridwidth = 3;
		//layout.setConstraints(fxPanel, gbc);
		this.add(fxPanel);
		
		//JScrollPane scrollPane = new JScrollPane();
        //scrollPane.add(fxPanel);
        //fxPanel.add(scrollPane);
		
		
		Platform.runLater(new Runnable() {
			// fxPanel als JavaFX-Thread starten
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
		
		setVisible(true);
		
	}
	
	/**
	 * Erzeugt eine Webview und läd hilfeFile
	 * @param fxPanel das Panel in dem die Webview gestartet wird
	 */
    private static void initFX(final JFXPanel fxPanel) {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);
        
        WebView webView = new WebView();
        
        group.getChildren().add(webView);
        //webView.setMinSize(300, 300);
        //webView.setMaxSize(300, 300);
        
        WebEngine webEngine = webView.getEngine();
        
        
        try {
			webEngine.load(handbuchFile.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }

}

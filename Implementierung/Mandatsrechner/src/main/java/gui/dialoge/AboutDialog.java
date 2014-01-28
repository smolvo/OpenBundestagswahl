package main.java.gui.dialoge;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 * 
 * @author Manuel
 *
 * Diese Klasse repräsentiert die "About"- Informationen, d.h. Wissenswertes zu dem Programm
 */
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = -1091530997927819189L;
	JLabel informationen;

	public AboutDialog() {

		// allgemeine Anpassungen des Fensters
		setTitle("About");
		setSize(1000, 1000);
		setLocationRelativeTo(null);
		setResizable(true);
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		GridLayout layout = new GridLayout(1, 1);
		//layout.setHgap(3);
		//layout.setVgap(3);
		setLayout(layout);
		
		final JFXPanel fxPanel = new JFXPanel();
		this.add(fxPanel);
		
		Platform.runLater(new Runnable() { // this will run initFX as JavaFX-Thread
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
		
		// TODO
		informationen = new JLabel("Informationen zu dieser Anwendug.");
		//this.add(informationen);
		//JButton schliessen = new JButton("schliessen");
		//schliessen.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent arg0) {
		//		dispose();
		//	}
		//});
		//this.add(schliessen);
		setVisible(true);
	}
	
	/* Creates a WebView and fires up google.com */
    private static void initFX(final JFXPanel fxPanel) {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);

        WebView webView = new WebView();

        group.getChildren().add(webView);
        //webView.setMinSize(300, 300);
        //webView.setMaxSize(300, 300);

            // Obtain the webEngine to navigate
        WebEngine webEngine = webView.getEngine();
        
        //webEngine.load("src/main/resources/hilfe/about.html");
        webEngine.load("http://wahlrecht.de");
    }

}

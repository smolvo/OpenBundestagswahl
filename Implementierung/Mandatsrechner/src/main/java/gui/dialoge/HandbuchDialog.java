package main.java.gui.dialoge;

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
import javax.swing.WindowConstants;

/**
 *
 */
public class HandbuchDialog extends JDialog {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -8178331482830745352L;

	/** Die HTML-Datei die in der Webview angezeigt wird. */
	private static final File HANDBUCH_FILE = new File(
			"src/main/resources/hilfe/tutorial1.html");

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
		
		// Als Layout festlegen
		setLayout(layout);
		final JFXPanel fxPanel = new JFXPanel();
		this.add(fxPanel);

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
	 * 
	 * @param fxPanel
	 *            das Panel in dem die Webview gestartet wird
	 */
	private static void initFX(final JFXPanel fxPanel) {
		Scene scene = createScene();
		fxPanel.setScene(scene);
	}
	private static Scene createScene() {
		Group group = new Group();
		Scene scene = new Scene(group);
		WebView webView = new WebView();
		webView.setMaxWidth(585);
		webView.setMaxHeight(565);
		group.getChildren().add(webView);
		WebEngine webEngine = webView.getEngine();

		try {
			webEngine.load(
					HANDBUCH_FILE.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return (scene);
	}
}

package main.java.gui.dialoge;

import java.awt.GridLayout;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Diese Klasse repräsentiert die "Lizenz" View in der GUI. Es werden
 * Informationen zur verwendeten Lizenz in einer Webview angezeigt.
 */
public class LizenzDialog extends JDialog {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = 4951470770450608239L;

	/** Die HTML-Datei die in der Webview angezeigt wird. */
	private static final File LIZENZ_FILE = new File(
			"src/main/resources/hilfe/lizenz.html");

	/**
	 * Das Diallogfenster, dass die Webview enthält
	 */
	public LizenzDialog() {

		// allgemeine Anpassungen des Fensters
		setTitle("Lizenz");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setResizable(true);
		setAlwaysOnTop(true);

		// wird benötigt um JavaFX Panel zu DISPOSEN
		Platform.setImplicitExit(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		GridLayout layout = new GridLayout(1, 1);
		// layout.setHgap(3);
		// layout.setVgap(3);
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
	 * Erzeugt eine Webview und läd about.html
	 * 
	 * @param fxPanel
	 *            das Panel in dem die Webview gestartet wird
	 */
	private static void initFX(final JFXPanel fxPanel) {
		Group group = new Group();
		Scene scene = new Scene(group);
		fxPanel.setScene(scene);

		WebView webView = new WebView();

		group.getChildren().add(webView);
		// webView.setMinSize(300, 300);
		// webView.setMaxSize(300, 300);

		WebEngine webEngine = webView.getEngine();

		try {
			webEngine.load(LIZENZ_FILE.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}

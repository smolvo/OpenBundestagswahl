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
 * Diese Klasse repräsentiert die "About" View in der GUI. Es werden
 * Informationen zu diesem Programm und den Entwicklern angezeigt. Es wird eine
 * Webview erzeugt die, die genannten Informationen anzeigt.
 */
public class AboutDialog extends JDialog {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -1091530997927819189L;

	/** Die HTML-Datei die in der Webview angezeigt wird. */
	private static final File ABOUT_FILE = new File(
			"src/main/resources/hilfe/about.html");

	/**
	 * Das Diallogfenster, dass die Webview enthï¿½lt
	 */
	public AboutDialog() {

		// allgemeine Anpassungen des Fensters
		setTitle("Über diese Anwendung");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setResizable(true);
		setAlwaysOnTop(true);

		// wird benï¿½tigt um JavaFX Panel zu DISPOSEN
		Platform.setImplicitExit(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		GridLayout layout = new GridLayout(1, 1);

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
	 * Erzeugt eine Webview und läd about.html
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
			webEngine.load(ABOUT_FILE.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return (scene);
	}
}

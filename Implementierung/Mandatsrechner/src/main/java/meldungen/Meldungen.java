package main.java.meldungen;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Diese Klasse dient zur Auswahl der Lokalisierten Texte
 * 
 */
public class Meldungen {

	private ResourceBundle resourceBundle;

	/**
	 * Diese Methdode wï¿½hlt die zu verwendende lokalisierte Textdatei anhand der
	 * ausgewï¿½hlten Sprache aus.
	 * 
	 * @param sprache
	 *            die ausgewï¿½hlte Sprache
	 */
	public Meldungen(Locale sprache) {
		resourceBundle = ResourceBundle.getBundle("meldungen.meldungen",
				sprache);
	}

	/**
	 * Diese Methode wï¿½hlt den zum Schlï¿½ssel gehï¿½renden Text aus der
	 * ausgewï¿½hlten lokalisierten Textatei und gibt ihn zurï¿½ck.
	 * 
	 * @param schluessel
	 *            der schlï¿½ssel dient als platzhalter fï¿½r den lokalisierten Text
	 * @return den Text falls vorhanden, sonst den fehlerhaften Schlï¿½ssel unter
	 *         der Angabe das er unbekannt ist.
	 */
	public String getString(String schluessel) {
		try {
			return resourceBundle.getString(schluessel);
		} catch (MissingResourceException e) {
			return "!" + schluessel + " NICHT BEKANNT!";
		}
	}
}
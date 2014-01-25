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
	 * Diese Methdode wählt die zu verwendende lokalisierte Textdatei anhand der ausgewählten Sprache aus.
	 * @param sprache 
	 * 				die ausgewählte Sprache
	 */
	public Meldungen(Locale sprache) {
		resourceBundle = ResourceBundle.getBundle("meldungen.meldungen", sprache);
	}
	
	/**
	 * Diese Methode wählt den zum Schlüssel gehörenden Text aus der ausgewählten lokalisierten Textatei und gibt ihn zurück.
	 * @param schluessel 
	 * 				der schlüssel dient als platzhalter für den lokalisierten Text
	 * @return	den Text falls vorhanden, sonst den fehlerhaften Schlüssel unter der Angabe das er unbekannt ist.
	 */
	public String getString(String schluessel) {
		try {
			return resourceBundle.getString(schluessel);
		}
		catch (MissingResourceException e) {
			return "!" + schluessel + " NICHT BEKANNT!";
		}
	}
}
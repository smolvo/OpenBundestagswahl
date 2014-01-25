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
	 * Diese Methdode w�hlt die zu verwendende lokalisierte Textdatei anhand der ausgew�hlten Sprache aus.
	 * @param sprache 
	 * 				die ausgew�hlte Sprache
	 */
	public Meldungen(Locale sprache) {
		resourceBundle = ResourceBundle.getBundle("meldungen.meldungen", sprache);
	}
	
	/**
	 * Diese Methode w�hlt den zum Schl�ssel geh�renden Text aus der ausgew�hlten lokalisierten Textatei und gibt ihn zur�ck.
	 * @param schluessel 
	 * 				der schl�ssel dient als platzhalter f�r den lokalisierten Text
	 * @return	den Text falls vorhanden, sonst den fehlerhaften Schl�ssel unter der Angabe das er unbekannt ist.
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
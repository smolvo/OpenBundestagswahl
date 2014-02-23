package test.java;

/**
 * Diese statische Klasse dient dazu Debug Meldungen aktivieren und deaktivieren zu können.
 * Es können außerdem unterschiedliche Debug Levels verwendet werden um den Überblick zu behalten.
 */
public class Debug {

	/**
	 * <p>Attribut um das aktuellen Debug Level setzen und lesen zu können.</p>
	 * 
	 * Hier eine Auflistung der einzelnen Debug Level:<br />
	 *    <b>0</b> - <i>Debug Mode ausgeschaltet, es werden keinerlei Debug Nachrichten ausgegeben</i><br />
	 *    <b>1</b> - <i>Nur sehr wichtige Debug Meldungen und sehr kritische Fehler werden ausgegeben</i><br />
	 *    <b>2</b> - <i>Nur wichtige Debug Meldungen und kritische Fehler werden ausgegeben</i><br />
	 *    <b>3</b> - <i>Auch allgemeine Warnungen und Hinweise werden ausgegeben</i><br />
	 *    <b>4</b> - <i>Nützliche Debug Meldungen aller Art werden ausgegeben</i><br />
	 *    <b>5</b> - <i>Sehr gesprächig, jede Kleinigkeit wird ausgegeben</i><br />
	 *    <b>6</b> - <i>Noch mehr Spam braucht wirklich niemand ;-)</i><br />
	 */
	private static int level = 0;

	
	/**
	 * Der Konstruktor ist privat, da alle Attribute und Methoden statisch sind.
	 */
	private Debug() {}
	
	
	/**
	 * <p>Gibt eine Debug Meldung aus, 
	 * wenn der aktuell gesetzte Debug mode >= dem dieser Nachricht ist.</p>
	 * 
	 * Hier eine Auflistung wie die einzelnen Debug Level verwendet werden sollten:<br />
	 *    <b>1</b> - sehr wichtige Debug Meldung oder sehr kritischer Fehler<br />
	 *    <b>2</b> - wichtige Debug Meldunge oder kritischer Fehler<br />
	 *    <b>3</b> - allgemeine Warnung oder ein wichtiger Hinweis<br />
	 *    <b>4</b> - Meldung die von allgemeinem Interesse ist<br />
	 *    <b>5</b> - relativ unwichtige Meldung die nicht ständig ausgegeben werden soll<br />
	 *    <b>6</b> - Sehr spezielle Meldung die nur in wenigen Fällen von Interesse ist<br />
	 * 
	 * @param meldung Nachricht die ausgegeben werden soll
	 * @param level Der Debug Level dieser Nachricht
	 */
	public static void print(String meldung, int level) {
		/* 
		 * checkt den aktuell gesetzten Debug Level und entscheidet,
		 * ob diese Meldung ausgegeben werden soll oder nicht.
		 */
		if (level <= Debug.level) {
			System.out.println(meldung);
		}
	}

	
	/**
	 * <p>Gibt den aktuellen Debug Level zurück.</p>
	 * 
	 * Hier eine Auflistung der einzelnen Debug Level:<br />
	 *    <b>0</b> - <i>Debug Mode ausgeschaltet, es werden keinerlei Debug Nachrichten ausgegeben</i><br />
	 *    <b>1</b> - <i>Nur sehr wichtige Debug Meldungen und sehr kritische Fehler werden ausgegeben</i><br />
	 *    <b>2</b> - <i>Nur wichtige Debug Meldungen und kritische Fehler werden ausgegeben</i><br />
	 *    <b>3</b> - <i>Auch allgemeine Warnungen und Hinweise werden ausgegeben</i><br />
	 *    <b>4</b> - <i>Nützliche Debug Meldungen aller Art werden ausgegeben</i><br />
	 *    <b>5</b> - <i>Sehr gesprächig, jede Kleinigkeit wird ausgegeben</i><br />
	 *    <b>6</b> - <i>Noch mehr Spam braucht wirklich niemand ;-)</i><br />
	 */
	public static int getLevel() {
		return level;
	}

	
	/**
	 * <p>Setzt den Debug Level.</p>
	 * 
	 * Hier eine Auflistung der einzelnen Debug Level:<br />
	 *    <b>0</b> - <i>Debug Mode ausgeschaltet, es werden keinerlei Debug Nachrichten ausgegeben</i><br />
	 *    <b>1</b> - <i>Nur sehr wichtige Debug Meldungen und sehr kritische Fehler werden ausgegeben</i><br />
	 *    <b>2</b> - <i>Nur wichtige Debug Meldungen und kritische Fehler werden ausgegeben</i><br />
	 *    <b>3</b> - <i>Auch allgemeine Warnungen und Hinweise werden ausgegeben</i><br />
	 *    <b>4</b> - <i>Nützliche Debug Meldungen aller Art werden ausgegeben</i><br />
	 *    <b>5</b> - <i>Sehr gesprächig, jede Kleinigkeit wird ausgegeben</i><br />
	 *    <b>6</b> - <i>Noch mehr Spam braucht wirklich niemand ;-)</i><br />
	 * 
	 * @param level Das Debug Level das gesetzt werden soll. Zwischen 0 (Aus) und 6 (sehr gesprächig)
	 */
	public static void setLevel(int level) {
		if (level < 0 || level > 6) {
			throw new IllegalArgumentException("Es sind nur Debug Level zwischen 1 und 6 erlaubt!");
		}
		Debug.level = level;
	}
	
}

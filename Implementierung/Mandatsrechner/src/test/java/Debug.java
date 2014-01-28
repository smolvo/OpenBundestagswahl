package test.java;


/**
 * Diese statische Klasse leitet Debug Meldungen weiter, wenn der Debug Modus aktiv ist.
 */
public class Debug {

	/** Attribut um Debug Modus aktiv zu schalten */
	private static boolean aktiv;
	
	/**
	 * Der Konstruktor ist privat, da alle Attribute und Methoden statisch sind.
	 */
	private Debug() {}
	
	/**
	 * Gibt die gegebene Meldung aus, wenn der Debug Modus aktiv ist.
	 * @param nachricht
	 */
	public static void print(String meldung) {
		if (aktiv) {
			System.out.println(meldung);
		}
	}

	/**
	 * Gibt den aktuellen Debug Status zurück.
	 * @return Den aktuellen Debug Status zurück.
	 */
	public static boolean isAktiv() {
		return aktiv;
	}

	/**
	 * Setzt den Debug status.
	 * @param aktiv Der Debug Status. Entweder true oder false
	 */
	public static void setAktiv(boolean aktiv) {
		Debug.aktiv = aktiv;
	}
}

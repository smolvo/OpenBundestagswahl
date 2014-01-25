package test.java;


/**
 * Diese statische Klasse leitet Debug Meldungen weiter, wenn der Debug Modus aktiv ist.
 */
public class Debug {
	
	private static boolean aktiv;
	
	
	private Debug() {}
	
	public static void print(String nachricht) {
		if (aktiv) {
			System.out.println(nachricht);
		}
	}

	public static boolean isAktiv() {
		return aktiv;
	}

	public static void setAktiv(boolean aktiv) {
		Debug.aktiv = aktiv;
	}
}

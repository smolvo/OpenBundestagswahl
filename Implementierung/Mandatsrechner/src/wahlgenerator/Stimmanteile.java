package wahlgenerator;

import model.Partei;

/**
 * Diese Klasse repräsentiert die Stimmanteile einer Partei.
 */
public class Stimmanteile {
	
	/** Die zugehörige Partei. */
	private final Partei partei;
	
	/** Anteil der Erststimmen dieser Partei. Dieser Wert muss zwischen 0 und 1 liegen.*/
	private final float anteilErststimmen;
	
	/** Anteil der Zweitstimmen dieser Partei. Dieser Wert muss zwischen 0 und 1 liegen.*/
	private final float anteilZweitstimmen;
	
	
	/**
	 * Parametrisierter Konstruktor.
	 * @param partei Die zugehörige Partei.
	 * @param anteilErststimmen Anteil der Erststimmen dieser Partei [0,1].
	 * @param anteilZweitstimmen Anteil der Zweitstimmen dieser Partei [0,1].
	 */
	public Stimmanteile(Partei partei, float anteilErststimmen, float anteilZweitstimmen) {
		if (partei == null) {
			throw new IllegalArgumentException("Der Parameter 'partei' ist null!");
		}
		if (anteilErststimmen < 0 || anteilErststimmen > 1) {
			throw new IllegalArgumentException("Der Wert des Parameters 'anteilErststimmen' liegt nicht im Intervall [0,1]!");
		}
		if (anteilZweitstimmen < 0 || anteilZweitstimmen > 1) {
			throw new IllegalArgumentException("Der Wert des Parameters 'anteilZweitstimmen' liegt nicht im Intervall [0,1]!");
		}		
		this.partei = partei;
		this.anteilErststimmen = anteilErststimmen;
		this.anteilZweitstimmen = anteilZweitstimmen;
	}

	/**
	 * Gibt die zugehörige Partei zurück.
	 * @return Die zugehörige Partei.
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Gibt den Anteil der Erststimmen dieser Partei zurück.
	 * Dieser Wert liegt zwischen 0 und 1.
	 * @return den Anteil der Erststimmen dieser Partei
	 */
	public float getAnteilErststimmen() {
		return anteilErststimmen;
	}

	/**
	 * Gibt den Anteil der Zweitstimmen dieser Partei zurück.
	 * Dieser Wert liegt zwischen 0 und 1.
	 * @return den Anteil der Zweitstimmen dieser Partei
	 */
	public float getAnteilZweitstimmen() {
		return anteilZweitstimmen;
	}
}

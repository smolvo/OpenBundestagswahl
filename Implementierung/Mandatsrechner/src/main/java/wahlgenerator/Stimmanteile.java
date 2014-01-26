package main.java.wahlgenerator;

import main.java.model.Partei;

/**
 * Diese Klasse repräsentiert die Stimmanteile einer Partei.
 */
public class Stimmanteile {
	
	/** Die zugehörige Partei. */
	private final Partei partei;
	
	/** Anteil der Erststimmen dieser Partei. Dieser Wert muss zwischen 0 und 100 liegen.*/
	private int anteilErststimmen;

	/** Anteil der Zweitstimmen dieser Partei. Dieser Wert muss zwischen 0 und 100 liegen.*/
	private int anteilZweitstimmen;
	
	
	/**
	 * Parametrisierter Konstruktor.
	 * @param partei Die zugehörige Partei.
	 * @param anteilErststimmen Anteil der Erststimmen dieser Partei [0,100].
	 * @param anteilZweitstimmen Anteil der Zweitstimmen dieser Partei [0,100].
	 */
	public Stimmanteile(Partei partei, int anteilErststimmen, int anteilZweitstimmen) {
		if (partei == null) {
			throw new IllegalArgumentException("Der Parameter 'partei' ist null!");
		}
		this.partei = partei;
		this.setAnteilErststimmen(anteilErststimmen);
		this.setAnteilZweitstimmen(anteilZweitstimmen);
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
	public int getAnteilErststimmen() {
		return anteilErststimmen;
	}
	

	/**
	 * Setzt den Parameter anteilErststimmen.
	 * @param anteilErststimmen Anteil der Erststimmen für diese Partei. Muss im Intervall [0,100] liegen.
	 * @throws IllegalArgumentException Wenn der Wert des Parameters 'anteilErststimmen' nicht im Intervall [0,100] liegt.
	 */
	public void setAnteilErststimmen(int anteilErststimmen) {
		if (anteilErststimmen < 0 || anteilErststimmen > 100) {
			throw new IllegalArgumentException("Der Wert des Parameters 'anteilErststimmen' liegt nicht im Intervall [0,100]!");
		}
		this.anteilErststimmen = anteilErststimmen;
	}

	/**
	 * Gibt den Anteil der Zweitstimmen dieser Partei zurück.
	 * Dieser Wert liegt zwischen 0 und 1.
	 * @return den Anteil der Zweitstimmen dieser Partei
	 */
	public int getAnteilZweitstimmen() {
		return anteilZweitstimmen;
	}
	
	/**
	 * Setzt den Parameter anteilZweitstimmen.
	 * @param anteilErststimmen Anteil der Zweitstimmen für diese Partei. Muss im Intervall [0,100] liegen.
	 * @throws IllegalArgumentException Wenn der Wert des Parameters 'anteilZweitstimmen' nicht im Intervall [0,100] liegt.
	 */
	public void setAnteilZweitstimmen(int anteilZweitstimmen) {
		if (anteilZweitstimmen < 0 || anteilZweitstimmen > 100) {
			throw new IllegalArgumentException("Der Wert des Parameters 'anteilZweitstimmen' liegt nicht im Intervall [0,100]!");
		}
		this.anteilZweitstimmen = anteilZweitstimmen;
	}
}

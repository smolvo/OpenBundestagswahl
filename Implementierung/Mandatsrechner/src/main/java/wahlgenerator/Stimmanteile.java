package main.java.wahlgenerator;

import main.java.model.Partei;

/**
 * Diese Klasse reprï¿½sentiert die Stimmanteile einer Partei.
 */
public class Stimmanteile {

	/** Die zugehï¿½rige Partei. */
	private final Partei partei;

	/**
	 * Anteil der Erststimmen dieser Partei. Dieser Wert muss zwischen 0 und 100
	 * liegen.
	 */
	private int anteilErststimmen;

	/**
	 * Anteil der Zweitstimmen dieser Partei. Dieser Wert muss zwischen 0 und
	 * 100 liegen.
	 */
	private int anteilZweitstimmen;

	/**
	 * Parametrisierter Konstruktor.
	 * 
	 * @param partei
	 *            Die zugehï¿½rige Partei.
	 * @param anteilErststimmen
	 *            Anteil der Erststimmen dieser Partei [0,100].
	 * @param anteilZweitstimmen
	 *            Anteil der Zweitstimmen dieser Partei [0,100].
	 */
	public Stimmanteile(Partei partei, int anteilErststimmen,
			int anteilZweitstimmen) {
		if (partei == null) {
			throw new IllegalArgumentException(
					"Der Parameter 'partei' ist null!");
		}
		this.partei = partei;
		setAnteilErststimmen(anteilErststimmen);
		setAnteilZweitstimmen(anteilZweitstimmen);
	}

	/**
	 * Gibt den Anteil der Erststimmen dieser Partei zurï¿½ck. Dieser Wert liegt
	 * zwischen 0 und 1.
	 * 
	 * @return den Anteil der Erststimmen dieser Partei
	 */
	public int getAnteilErststimmen() {
		return this.anteilErststimmen;
	}

	/**
	 * Gibt den Anteil der Zweitstimmen dieser Partei zurï¿½ck. Dieser Wert
	 * liegt zwischen 0 und 1.
	 * 
	 * @return den Anteil der Zweitstimmen dieser Partei
	 */
	public int getAnteilZweitstimmen() {
		return this.anteilZweitstimmen;
	}

	/**
	 * Gibt die zugehï¿½rige Partei zurï¿½ck.
	 * 
	 * @return Die zugehï¿½rige Partei.
	 */
	public Partei getPartei() {
		return this.partei;
	}

	/**
	 * Setzt den Parameter anteilErststimmen.
	 * 
	 * @param anteilErststimmen
	 *            Anteil der Erststimmen fï¿½r diese Partei. Muss im Intervall
	 *            [0,100] liegen.
	 * @throws IllegalArgumentException
	 *             Wenn der Wert des Parameters 'anteilErststimmen' nicht im
	 *             Intervall [0,100] liegt.
	 */
	public final void setAnteilErststimmen(int anteilErststimmen) {
		if (anteilErststimmen < 0 || anteilErststimmen > 100) {
			throw new IllegalArgumentException(
					"Der Wert des Parameters 'anteilErststimmen' liegt nicht im Intervall [0,100]!");
		}
		this.anteilErststimmen = anteilErststimmen;
	}

	/**
	 * Setzt den Parameter anteilZweitstimmen.
	 * 
	 * @param anteilZweitstimmen
	 *            Anteil der Zweitstimmen fï¿½r diese Partei. Muss im Intervall
	 *            [0,100] liegen.
	 * @throws IllegalArgumentException
	 *             Wenn der Wert des Parameters 'anteilZweitstimmen' nicht im
	 *             Intervall [0,100] liegt.
	 */
	public final void setAnteilZweitstimmen(int anteilZweitstimmen) {
		if (anteilZweitstimmen < 0 || anteilZweitstimmen > 100) {
			throw new IllegalArgumentException(
					"Der Wert des Parameters 'anteilZweitstimmen' liegt nicht im Intervall [0,100]!");
		}
		this.anteilZweitstimmen = anteilZweitstimmen;
	}
}

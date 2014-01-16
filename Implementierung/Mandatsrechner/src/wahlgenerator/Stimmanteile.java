package wahlgenerator;

import model.Partei;

/**
 * Diese Klasse repräsentiert die Stimmanteile einer Partei.
 */
public class Stimmanteile {
	
	/** Die zugehörige Partei. */
	private Partei partei;
	
	/** Anteil der Erststimmen dieser Partei. */
	private float prozErststimmen;
	
	/**  */
	private float prozZweitstimmen;
	
	
	/**
	 * Parametrisierter Konstruktor.
	 * @param partei Die zugehörige Partei.
	 * @param prozErststimmen Anteil der Erststimmen dieser Partei.
	 * @param prozZweitstimmen Anteil der Zweitstimmen dieser Partei.
	 */
	public Stimmanteile(Partei partei, float prozErststimmen, float prozZweitstimmen) {
		this.setPartei(partei);
		this.setProzErststimmen(prozErststimmen);
		this.setProzZweitstimmen(prozZweitstimmen);
	}


	/**
	 * Gibt die zugehörige Partei zurück.
	 * @return Die zugehörige Partei.
	 */
	public Partei getPartei() {
		return partei;
	}


	public void setPartei(Partei partei) {
		this.partei = partei;
	}


	public float getProzErststimmen() {
		return prozErststimmen;
	}


	public void setProzErststimmen(float prozErststimmen) {
		this.prozErststimmen = prozErststimmen;
	}


	public float getProzZweitstimmen() {
		return prozZweitstimmen;
	}


	public void setProzZweitstimmen(float prozZweitstimmen) {
		this.prozZweitstimmen = prozZweitstimmen;
	}
	
}

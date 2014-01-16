package wahlgenerator;

import model.Partei;

/**
 * Diese Klasse repr�sentiert die Stimmanteile einer Partei.
 */
public class Stimmanteile {
	
	/** Die zugeh�rige Partei. */
	private Partei partei;
	
	/** Anteil der Erststimmen dieser Partei. */
	private float prozErststimmen;
	
	/**  */
	private float prozZweitstimmen;
	
	
	/**
	 * Parametrisierter Konstruktor.
	 * @param partei Die zugeh�rige Partei.
	 * @param prozErststimmen Anteil der Erststimmen dieser Partei.
	 * @param prozZweitstimmen Anteil der Zweitstimmen dieser Partei.
	 */
	public Stimmanteile(Partei partei, float prozErststimmen, float prozZweitstimmen) {
		this.setPartei(partei);
		this.setProzErststimmen(prozErststimmen);
		this.setProzZweitstimmen(prozZweitstimmen);
	}


	/**
	 * Gibt die zugeh�rige Partei zur�ck.
	 * @return Die zugeh�rige Partei.
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

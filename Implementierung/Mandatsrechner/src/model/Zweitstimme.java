package model;

/**
 * Assoziationsklasse die Anzahl aller Zweitstimmen beinhaltet und mit den Klassen 
 * Deutschland und Partei zusammen arbeitet. Außerdem erbt diese von der Klasse Stimme.
 */
public class Zweitstimme extends Stimme{
	
		/** Die verbundene Partei. */
		private Partei partei;
		
		/** Das verbundene gebiet. */
		private Gebiet gebiet;
		
		
		/**
		 * Angepasster Konstruktor.
		 * @param anzahl
		 * @param partei
		 * @param deutschland
		 */
		public Zweitstimme(int anzahl, Partei partei, Gebiet gebiet){
			this.setAnzahl(anzahl);
			this.setGebiet(gebiet);
			this.setPartei(partei);
		}
		
		/**
		 * Gibt die verbundene Partei zurück.
		 * @return die verbunde Partei
		 */
		public Partei getPartei() {
			return partei;
		}
		
		/**
		 * Setzt eine neue Verbindung mit einer Partei.
		 * @param partei die neu Partei
		 * @exception wenn die Partei leer ist
		 */
		public void setPartei(Partei partei) {
			if(partei == null){
				throw new IllegalArgumentException("Partei ist leer!");
			}
			this.partei = partei;
		}
		
		/**
		 * Gibt das verbundene Gebiet-Objekt zurück
		 * @return das verbundene Gebiet-Objekt
		 */
		public Gebiet getGebiet() {
			return gebiet;
		}
		
		/**
		 * Setzt eine neue Verbindung mit dem Gebiet-Objekt
		 * @param gebiet das neue Objekt
		 * @exception wenn das Objekt leer ist
		 */
		public void setGebiet(Gebiet gebiet) {
			if(gebiet == null){
				throw new IllegalArgumentException("Gebiet ist leer!");
			}
			this.gebiet = gebiet;
		}

}

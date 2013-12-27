package model;

/**
 * Assoziationsklasse die Anzahl aller Zweitstimmen beinhaltet und mit den Klassen 
 * Deutschland und Partei zusammen arbeitet. Außerdem erbt diese von der Klasse Stimme.
 */
public class Zweitstimme extends Stimme{
		/**
		 * Die verbundene Partei.
		 */
		private Partei partei;
		/**
		 * Das verbundene Deutschland.
		 */
		private Deutschland deutschland;
		/**
		 * Angepasster Konstruktor.
		 * @param anzahl
		 * @param partei
		 * @param deutschland
		 */
		public Zweitstimme(int anzahl, Partei partei, Deutschland deutschland){
			this.setAnzahl(anzahl);
			this.setDeutschland(deutschland);
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
		 * Gibt das verbundene Deutschland-Objekt zurück
		 * @return das verbundene Deutschland-Objekt
		 */
		public Deutschland getDeutschland() {
			return deutschland;
		}
		/**
		 * Setzt eine neue Verbindung mit dem Deutschland-Objekt
		 * @param deutschland das neue Objekt
		 * @exception wenn das Objekt leer ist
		 */
		public void setDeutschland(Deutschland deutschland) {
			if(deutschland == null){
				throw new IllegalArgumentException("Deutschland ist leer!");
			}
			this.deutschland = deutschland;
		}

}

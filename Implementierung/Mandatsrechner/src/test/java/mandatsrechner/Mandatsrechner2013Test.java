package test.java.mandatsrechner;

import java.io.File;

import test.java.Debug;
import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Mandat;
import main.java.model.Partei;

public class Mandatsrechner2013Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2009.csv");
		csvDateien[1] = new File("src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
			System.out.println("Leine gueltige CSV-Datei :/");
		}
		Debug.setAktiv(true);
		
		Mandatsrechner2013 m = Mandatsrechner2013.getInstance();
		
		if (w != null) {
			//System.out.println("Test");
			Bundestagswahl newW = m.berechneAlles(w);
			//newW = m.berechneAlles(newW);
			int count = 0;
			for (Partei partei : newW.getParteien()) {
				//if(partei.getName().equals("CDU")){
					/*for (Bundesland bl : newW.getDeutschland().getBundeslaender()) {
						System.out.println(partei.getName()+" "+bl.getName());
						int sum = partei.getAnzahlMandate(Mandat.MANDAT,bl) + 
								partei.getAnzahlMandate(Mandat.DIREKTMANDAT,bl) + 
								partei.getAnzahlMandate(Mandat.AUSGLEICHSMANDAT,bl) + 
								partei.getAnzahlMandate(Mandat.UEBERHANGMADAT,bl);
						System.out.println("Mandate: "+partei.getAnzahlMandate(Mandat.MANDAT,bl));
						System.out.println("Direktmandate: "+partei.getAnzahlMandate(Mandat.DIREKTMANDAT,bl));
						System.out.println("Ausgleichsmandate: "+partei.getAnzahlMandate(Mandat.AUSGLEICHSMANDAT,bl));
						System.out.println("Ueberhangsmandate: "+partei.getAnzahlMandate(Mandat.UEBERHANGMADAT,bl));
						System.out.println("Summe: "+sum);
						System.out.println("\n");
					}*/
					
					int sum = partei.getAnzahlMandate(Mandat.LISTENMANDAT) + 
							partei.getAnzahlMandate(Mandat.DIREKTMANDAT);
					System.out.println(partei.getName()+": ");
					System.out.println("Mandate: "+partei.getAnzahlMandate(Mandat.LISTENMANDAT));
					System.out.println("Direktmandate: "+partei.getAnzahlMandate(Mandat.DIREKTMANDAT));
					System.out.println("Ausgleichsmandate: "+partei.getAnzahlMandate(Mandat.AUSGLEICHSMANDAT));
					System.out.println("Ueberhangsmandate: "+partei.getUeberhangMandate());
					System.out.println("Summe: "+sum);
					System.out.println("\n");
					
				//}
				if(count<5){
					count++;
				}else {
					break;
				}
			}
			
			//Debug.print(w.getSitzverteilung().getBericht().toString());
			//System.out.println(w.getSitzverteilung().getAbgeordnete().size());
		}
	}

}

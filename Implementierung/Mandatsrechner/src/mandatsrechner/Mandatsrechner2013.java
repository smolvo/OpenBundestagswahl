package mandatsrechner;

import java.util.HashMap;
import java.util.List;

import model.Bundesland;
import model.Bundestagswahl;
import model.Mandat;
import model.Wahlkreis;
import model.Partei;

public class Mandatsrechner2013 extends Mandatsrechner {

	private static Mandatsrechner2013 instance;
	private Mandatsrechner2009 rechner2009;
	
	public Mandatsrechner2013(){
		this.rechner2009 = new Mandatsrechner2009();
	}
	
	public static Mandatsrechner2013 getInstance() {
		if(Mandatsrechner2013.instance==null){
			Mandatsrechner2013.instance = new Mandatsrechner2013();
		}
		return Mandatsrechner2013.instance;
	}

	public Bundestagswahl berechneAlles(Bundestagswahl bw){
		bw = rechner2009.berechneAlles(bw);
		float parteidivisor = 0;
		boolean isCorrect = false;
		
		List<Partei> relevanteParteien = rechner2009.getRelevanteParteien();

		if (rechner2009.debug) {
			System.out.println("\nSitzverteilung");
			int summe = 0;
			for (Partei partei : relevanteParteien) {
				System.out.println(partei.getName() + ": " + partei.getMindestsitzAnzahl());
				summe +=  partei.getMindestsitzAnzahl();
			}
			System.out.println("Summe: " + summe);
		}

		for (Partei partei : relevanteParteien) {
			if (parteidivisor == 0) {
				parteidivisor = (float) (partei.getZweitstimmeGesamt() / (partei.getMindestsitzAnzahl() - 0.5));
			} else {
				parteidivisor = Math.min(parteidivisor,  partei.getZweitstimmeGesamt() / partei.getMindestsitzAnzahl());
			}
			//System.err.println(partei.getZweitstimmeGesamt() + " "+ partei.getMindestsitzAnzahl() +" "+parteidivisor);
		}
		
		
		if (rechner2009.debug) {
			System.out.println("\nAlt Parteidivisor: " + parteidivisor);
			int summe = 0;
			for (Partei partei : relevanteParteien) {
				System.out.println(partei.getName() + ": " + ((int) (partei.getZweitstimmeGesamt() / parteidivisor)));
				summe +=  ((int) (partei.getZweitstimmeGesamt() / parteidivisor));
			}
			System.out.println("Summe: " + summe);
			
		}
		
		while (!isCorrect) {
			isCorrect = true;
			for (Partei partei : relevanteParteien) {
				int mindestSitze = partei.getMindestsitzAnzahl();
				//System.out.println(Math.floor(partei.getZweitstimmeGesamt() / parteidivisor)+ " " + mindestSitze);
				if (Math.floor(partei.getZweitstimmeGesamt() / parteidivisor) < mindestSitze) {
					isCorrect = false;
					break;
				}
			}
			if (!isCorrect) {
				parteidivisor -= 1;
			}
		}
		
	
		
		if (rechner2009.debug) {
			System.out.println("\nNeu Parteidivisor: " + parteidivisor);
			int summe = 0;
			for (Partei partei : relevanteParteien) {
				System.out.println(partei.getName() + ": " + ((int) (partei.getZweitstimmeGesamt() / parteidivisor)));
				summe +=  ((int) (partei.getZweitstimmeGesamt() / parteidivisor));
			}
			System.out.println("Summe: " + summe);
		}
		return bw;
		
	}
	
	@Override
	public Bundestagswahl berechne(Bundestagswahl bw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Bundesland berechne(Bundesland bl, int sitzeBundesland) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Wahlkreis berechne(Wahlkreis wk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void erstelleBericht(String zeile) {
		// TODO Auto-generated method stub
		
	}

}

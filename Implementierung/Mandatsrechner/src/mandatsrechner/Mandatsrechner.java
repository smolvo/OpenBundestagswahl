package mandatsrechner;

import model.*;

abstract class Mandatsrechner {
	
	public boolean debug = true;
	
	protected String Sitzbericht;
	
	abstract public Bundestagswahl berechne(Bundestagswahl bw);
	
	abstract protected Bundesland berechne(Bundesland bl, int sitzeBundesland);
	
	abstract protected Wahlkreis berechne(Wahlkreis wk);
	
	abstract protected void erstelleBericht(String zeile);
	
}

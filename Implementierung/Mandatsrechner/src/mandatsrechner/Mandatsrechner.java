package mandatsrechner;

import model.*;

abstract class Mandatsrechner {
	protected Mandatsrechner instance = null;
	protected String Sitzbericht;

	abstract public Mandatsrechner getInstance();
	
	abstract public Bundestagswahl berechne(Bundestagswahl bw);
	
	abstract protected Bundesland berechne(Bundesland bl, int sitzeBundesland);
	
	abstract protected Wahlkreis berechne(Wahlkreis wk);
	
	abstract protected void erstelleBericht(String zeile);
	
}

package main.java.gui.ansicht.tabellenfenster;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.java.gui.GUIPartei;
import main.java.gui.ansicht.Ansicht;
import main.java.model.Bundesland;
import main.java.model.Deutschland;
import main.java.model.Erststimme;
import main.java.model.Gebiet;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse repräsentiert das Tabellenfenster einer Ansicht. In diesem
 * werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * 
 */
public class TabellenFenster extends JScrollPane {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -8830377095217386485L;

	/** repräsentiert die Ansicht in der sich die Tabelle befindet */
	private final Ansicht ansicht;

	/**
	 * Der Kostruktor erstellt ein neues Tabellenfenster.
	 * 
	 * @param ansicht
	 *            die Ansicht
	 * @throws IllegalArgumentException
	 *             wenn das Ansicht-Objekt null ist.
	 */
	public TabellenFenster(Ansicht ansicht) {
		if (ansicht == null) {
			throw new IllegalArgumentException("Ansicht ist null.");
		}
		this.ansicht = ansicht;
	}

	/**
	 * Gibt die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return this.ansicht;
	}

	/**
	 * Diese private Methode wird von tabellenFuellen für Deutschland verwendet
	 * und erstellt aus einer Sitzverteilung und einer Partei ein
	 * GUIPartei-Objekt, welches alle Daten, die angezeigt werden müssen
	 * beinhaltet.
	 * 
	 * @param partei
	 *            Partei
	 * @param bundestag
	 *            die Sitzplatzverteilung
	 * @return GUIPartei-Objekt
	 */
	private GUIPartei parteiDatenDeutschland(Partei partei,
			Sitzverteilung bundestag) {
		if (partei == null || bundestag.getAbgeordnete() == null) {
			return new GUIPartei(0, 0, 0, 0);
		}
		// Anzahl Direkt-, Überhangs-, und Ausgleichsmandate
		// int sitze = partei.getAnzahlMandate();
		final int sitze = bundestag.getAnzahlSitze(partei);
		int direktMan = 0;
		final int ueberMan = partei.getUeberhangMandate();
		int ausglMan = partei.getAusgleichsMandate();
		for (final Kandidat kan : bundestag.getAbgeordnete()) {
			if (kan.getPartei().getName().equals(partei.getName())) {
				if (kan.getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktMan++;
				} else if (kan.getMandat().equals(Mandat.AUSGLEICHSMANDAT)) {
					ausglMan++;
				}
			}
		}
		final GUIPartei gp = new GUIPartei(sitze, direktMan, ueberMan, ausglMan);
		return gp;
	}

	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param bl
	 *            Bundesland-Objekt welches visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland-Objekt null ist.
	 */
	private void tabellenFuellen(Bundesland bl) {
		if (bl == null) {
			throw new IllegalArgumentException(
					"Das Bundesland-Objekt ist null.");
		}
		final LandDaten daten = new LandDaten();
		final List<Zweitstimme> stimmen = bl.getZweitstimmenProPartei();
		Collections.sort(stimmen);
		for (final Zweitstimme zw : stimmen) {
			int direktMan = 0;
			for (final Wahlkreis wk : bl.getWahlkreise()) {
				if (wk.getWahlkreisSieger().getPartei().getName()
						.equals(zw.getPartei().getName())) {
					direktMan++;
				}
			}
			final double proZweit = Math.rint((double) zw.getAnzahl()
					/ (double) bl.getAnzahlZweitstimmen() * 1000) / 10;
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit), Integer.toString(direktMan),
					Integer.toString(zw.getPartei().getUeberhangMandate(bl)));
		}

		final TableRowSorter<TableModel> sorterLand = new TableRowSorter<TableModel>();
		final LandTableModel tabelle = new LandTableModel(daten);
		final JTable jTabelle = new JTable(tabelle);
		jTabelle.setRowSorter(sorterLand);
		sorterLand.setModel(jTabelle.getModel());
		sorterLand.setComparator(3, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterLand.setComparator(4, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterLand.setComparator(2, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterLand.setComparator(1, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		});

		setViewportView(jTabelle);

	}

	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Bundesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param land
	 *            Deutschland-Objekt welches visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Deutschland-Objekt null ist.
	 */
	private void tabellenFuellen(Deutschland land) {
		if (land == null) {
			throw new IllegalArgumentException("Deutschland-Objekt ist null");
		}
		final Sitzverteilung bundestag = this.ansicht.getFenster().getBtw()
				.getSitzverteilung();
		final BundDaten daten = new BundDaten();
		final List<Zweitstimme> stimmen = land.getZweitstimmenProPartei();
		Collections.sort(stimmen);
		for (final Zweitstimme zw : stimmen) {
			final GUIPartei gp = parteiDatenDeutschland(zw.getPartei(),
					bundestag);
			final double proZweit = Math.rint((double) zw.getAnzahl()
					/ (double) land.getAnzahlZweitstimmen() * 1000) / 10;
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit), Integer.toString(gp.getSitze()),
					Integer.toString(gp.getDirektmandate()),
					Integer.toString(gp.getUeberhangsmandate()),
					Integer.toString(gp.getAusgleichsmandate()));
		}

		final TableRowSorter<TableModel> sorterBund = new TableRowSorter<TableModel>();
		final BundTableModel tabelle = new BundTableModel(daten);
		final JTable jTabelle = new JTable(tabelle);
		jTabelle.setRowSorter(sorterBund);
		sorterBund.setModel(jTabelle.getModel());
		sorterBund.setComparator(3, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterBund.setComparator(4, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterBund.setComparator(5, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterBund.setComparator(6, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterBund.setComparator(2, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterBund.setComparator(1, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		});

		setViewportView(jTabelle);
	}

	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * 
	 * @param gebiet
	 *            Gebiet
	 */
	public void tabellenFuellen(Gebiet gebiet) {
		// wenn kein Gebiet ï¿½bergeben wird wird eine leere Tabelle erstellt
		if (gebiet == null) {
			final BundDaten daten = new BundDaten();
			final BundTableModel tabelle = new BundTableModel(daten);
			final JTable jTabelle = new JTable(tabelle);
			setViewportView(jTabelle);
		}
		if (gebiet instanceof Deutschland) {
			final Deutschland land = (Deutschland) gebiet;
			tabellenFuellen(land);
		} else if (gebiet instanceof Bundesland) {
			final Bundesland bundLand = (Bundesland) gebiet;
			tabellenFuellen(bundLand);
		} else {
			final Wahlkreis wk = (Wahlkreis) gebiet;
			tabellenFuellen(wk);
		}
	}

	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht mit
	 * den relevanten Daten.
	 * 
	 * @param wk
	 *            Wahlkreis-Objekt welcher visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Wahlkreis-Objekt leer ist.
	 */
	private void tabellenFuellen(Wahlkreis wk) {
		if (wk == null) {
			throw new IllegalArgumentException("Wahlkreis-Objekt ist null.");
		}
		final WahlkreisDaten daten = new WahlkreisDaten();
		for (final Erststimme er : wk.getErststimmenProPartei()) {
			Zweitstimme korresZweit = null;
			for (final Zweitstimme zw : wk.getZweitstimmenProPartei()) {
				if (er.getKandidat().getPartei() != null
						&& zw.getPartei().getName()
								.equals(er.getKandidat().getPartei().getName())) {
					korresZweit = zw;
				}
			}
			if (korresZweit != null) {
				boolean direktManBoolean = false;
				if (er.getKandidat().getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktManBoolean = true;
				}
				double prozentualeErst = 0;
				if (wk.getAnzahlErststimmen() != 0) {
					prozentualeErst = Math.rint((double) er.getAnzahl()
							/ (double) wk.getAnzahlErststimmen() * 1000) / 10;
				}
				double prozentualeZweit = 0;
				if (wk.getAnzahlZweitstimmen() != 0) {
					prozentualeZweit = Math.rint((double) korresZweit
							.getAnzahl()
							/ (double) wk.getAnzahlZweitstimmen()
							* 1000) / 10;
				}
				String direktMan = "nein";
				if (direktManBoolean) {
					direktMan = "ja";
				}
				daten.addZeile(korresZweit.getPartei().getName(), er
						.getKandidat().getName(), korresZweit, er, Double
						.toString(prozentualeZweit), Double
						.toString(prozentualeErst), direktMan);
			}
		}

		final TableRowSorter<TableModel> sorterWk = new TableRowSorter<TableModel>();
		final WahlkreisTableModel tabelle = new WahlkreisTableModel(daten, this);
		final JTable jTabelle = new JTable(tabelle);
		jTabelle.setRowSorter(sorterWk);
		sorterWk.setModel(jTabelle.getModel());
		sorterWk.setComparator(3, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterWk.setComparator(5, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterWk.setComparator(2, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		});
		sorterWk.setComparator(4, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		});

		setViewportView(jTabelle);

	}
}

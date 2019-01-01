package dao.cartes;

import carte.motif.IMotif;
import carte.motif.Motif_Memori;

public class Cartes_MemoriDAO extends CartesDAO {

	public IMotif[] motifCarte = Motif_Memori.values();
			
	private static Cartes_MemoriDAO instance = null;

	public static Cartes_MemoriDAO getInstance() {
		if (instance == null) {
			instance = new Cartes_MemoriDAO();
		}
		return instance;
	}
	
	public IMotif getMotif(int index)
	{
		return this.motifCarte[index];
	}
}

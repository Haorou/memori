package dao.cartes;

import carte.motif.IMotif;
import carte.motif.Motif_PetitVerger;

public class Cartes_PetitVergerDAO extends CartesDAO {

	public IMotif[] motifCarte = Motif_PetitVerger.values();
	
	private static Cartes_PetitVergerDAO instance = null;

	public static Cartes_PetitVergerDAO getInstance() {
		if (instance == null) {
			instance = new Cartes_PetitVergerDAO();
		}
		return instance;
	}
	
	public IMotif getMotif(int index)
	{
		return this.motifCarte[index];
	}


}

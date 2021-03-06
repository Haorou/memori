package dao.plateau;

public class Plateau_PetitVergerDAO extends PlateauDAO 
{
	private static final String JEU = "Petit Verger";

	private static Plateau_PetitVergerDAO instance = null;

	public static Plateau_PetitVergerDAO getInstance() {
		if (instance == null) {
			instance = new Plateau_PetitVergerDAO();
		}
		return instance;
	}

	@Override
	public String getJeu() {
		return JEU;
	}  
}

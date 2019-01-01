package dao.gestionnaire;

import dao.PlateauDAO;
import dao.Plateau_PetitVergerDAO;

public class GestionnairePetitVerger extends Gestionnaire 
{
	private final PlateauDAO gestionnairePlateau = Plateau_PetitVergerDAO.getInstance();
	
	@Override
	public PlateauDAO getGestionnairePlateau() 
	{
		return gestionnairePlateau;
	}	
}

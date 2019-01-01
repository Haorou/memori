package dao.gestionnaire;

import dao.PlateauDAO;
import dao.Plateau_MemoriDAO;

public class GestionnaireMemori extends Gestionnaire 
{
	private final PlateauDAO gestionnairePlateau = Plateau_MemoriDAO.getInstance();

	@Override
	public PlateauDAO getGestionnairePlateau() 
	{
		return gestionnairePlateau;
	}	

}

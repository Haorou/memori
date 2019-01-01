package dao.gestionnaire;

import java.util.List;

import carte.Carte;
import carte.PaquetCartes;
import dao.CartesDAO;
import dao.JoueurDAO;
import dao.PlateauDAO;
import jeu.Joueur;
import jeu.Plateau;

public abstract class Gestionnaire {
	private static final CartesDAO gestionnaireCartes = CartesDAO.getInstance();
	private static final JoueurDAO gestionnaireJoueur = JoueurDAO.getInstance();
	
	public abstract PlateauDAO getGestionnairePlateau();
	
	public void createDataPartie(Plateau plateauACreer)
	{	
		getGestionnairePlateau().create(plateauACreer);
		for (Carte carte : PaquetCartes.paquetCartes) 
		{
			gestionnaireCartes.create(carte);	
		}
		
		int id_joueur;
		Joueur joueur_index = null;
		for (int i = 1; i <= Plateau.nombreDeJoueurs(); i++) 
		{
			joueur_index = Plateau.getJoueur(i);
			id_joueur = gestionnaireJoueur.isPlayerExistReturnId(Plateau.getJoueur(i));
			
			if(id_joueur <1)
				gestionnaireJoueur.create(joueur_index);				
			else
				joueur_index.setId(id_joueur);
			
			gestionnaireJoueur.insertIntoParticipe(joueur_index);
		}
		getGestionnairePlateau().create_joueur_courant();
	}
	
	public void updateDataPartie()
	{
		getGestionnairePlateau().update();
		
		for (Carte carte : PaquetCartes.paquetCartes) 
		{
			gestionnaireCartes.update(carte);
		}
		for (int i = 1; i <= Plateau.nombreDeJoueurs(); i++) 
		{
			gestionnaireJoueur.update(Plateau.getJoueur(i));	
		}
		getGestionnairePlateau().update_joueur_courant();
	}
	
	public void supprimerDataPartie()
	{
		gestionnaireCartes.deleteAll();
		gestionnaireJoueur.deleteAll();
		getGestionnairePlateau().deleteAll();
	}
	
	
	public Plateau preparerCartesJoueursEtPlateau()
	{
		int choix_joueur = Joueur.SCANNER.nextInt();
		int id_partie = PlateauDAO.dicoCompteurPlateau.get(choix_joueur);
		
		gestionnaireCartes.lireCartesDuPlateau(id_partie);
		return getGestionnairePlateau().read(id_partie);
	}
	
	
	public List<String> listDePartieEnCours()
	{
		return getGestionnairePlateau().listDePartieEnCours();
	}
	
	public List<String> listDePartieFinie()
	{
		return  getGestionnairePlateau().listDePartieFinie();
	}
		
	public static boolean enregistrerVainqueur()
	{
		return gestionnaireJoueur.enregistrerJoueurVainqueur();
	}

	public static boolean deleteCartesEnMain() {
		return gestionnaireCartes.deleteCartesEnMain();
	}

	public static boolean createCartesEnMain() {
		return gestionnaireCartes.createCartesEnMain();
	}

	public static boolean supprimerCartesDuPaquet() 
	{
		return gestionnaireCartes.supprimerCartesDuPaquet();
	}

	public static boolean supprimerJoueurCourant() {
		return gestionnaireJoueur.supprimerJoueurCourant();
	}
}

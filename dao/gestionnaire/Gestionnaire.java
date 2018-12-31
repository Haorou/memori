package dao.gestionnaire;

import java.util.List;

import carte.Carte;
import carte.PaquetCartes;
import dao.CartesDAO;
import dao.JoueurDAO;
import dao.PlateauDAO;
import dao.Plateau_MemoriDAO;
import dao.Plateau_PetitVergerDAO;
import jeu.Joueur;
import jeu.Plateau;

public abstract class Gestionnaire {
	private static final CartesDAO gestionnaireCartes = CartesDAO.getInstance();
	private static final JoueurDAO gestionnaireJoueur = JoueurDAO.getInstance();

	private static final PlateauDAO gestionnairePlateau_Memori = Plateau_MemoriDAO.getInstance();
	private static final PlateauDAO gestionnairePlateau_PetitVerger = Plateau_PetitVergerDAO.getInstance();
	
	
	public static void createDataPartie()
	{	
		gestionnairePlateau_Memori.create();
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
		gestionnairePlateau_Memori.create_joueur_courant();
	}
	
	public static void updateDataPartie()
	{
		gestionnairePlateau_Memori.update();
		
		for (Carte carte : PaquetCartes.paquetCartes) 
		{
			gestionnaireCartes.update(carte);
		}
		for (int i = 1; i <= Plateau.nombreDeJoueurs(); i++) 
		{
			gestionnaireJoueur.update(Plateau.getJoueur(i));	
		}
		gestionnairePlateau_Memori.update_joueur_courant();
	}
	
	public static void supprimerDataPartie()
	{
		gestionnaireCartes.deleteAll();
		gestionnaireJoueur.deleteAll();
		gestionnairePlateau_Memori.deleteAll();
	}
	
	
	public static Plateau preparerCartesJoueursEtPlateau()
	{
		int choix_joueur = Joueur.SCANNER.nextInt();
		int id_partie = PlateauDAO.dicoCompteurPlateau.get(choix_joueur);
		
		gestionnaireCartes.lireCartesDuPlateau(id_partie);
		return gestionnairePlateau_Memori.read(id_partie);
	}
	
	
	public static List<String> listDePartieEnCours()
	{
		return gestionnairePlateau_Memori.listDePartieEnCours();
	}
	
	public static List<String> listDePartieFinie()
	{
		return  gestionnairePlateau_Memori.listDePartieFinie();
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

package dao;

import java.util.List;

import carte.Carte;
import carte.PaquetCartes;
import jeu.Joueur;
import jeu.Plateau;

public class Gestionnaire {
	private static final CartesDAO gestionnaireCartes = CartesDAO.getInstance();
	private static final JoueurDAO gestionnaireJoueur = JoueurDAO.getInstance();
	private static final Plateau_MemoriDAO gestionnairePlateau = Plateau_MemoriDAO.getInstance();
	
	public static void createDataPartie()
	{	
		gestionnairePlateau.create();
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
		gestionnairePlateau.create_joueur_courant();
	}
	
	public static void updateDataPartie()
	{
		gestionnairePlateau.update();
		
		for (Carte carte : PaquetCartes.paquetCartes) 
		{
			gestionnaireCartes.update(carte);
		}
		for (int i = 1; i <= Plateau.nombreDeJoueurs(); i++) 
		{
			gestionnaireJoueur.update(Plateau.getJoueur(i));	
		}
		gestionnairePlateau.update_joueur_courant();
	}
	
	public static void supprimerDataPartie()
	{
		gestionnaireCartes.deleteAll();
		gestionnaireJoueur.deleteAll();
		gestionnairePlateau.deleteAll();
	}
	
	
	public static void preparerCartesJoueursEtPlateau()
	{
		int choix_joueur = Joueur.SCANNER.nextInt();
		int id_partie = Plateau_MemoriDAO.dicoCompteurPlateau.get(choix_joueur);
		
		gestionnairePlateau.read_Memori(id_partie);
		gestionnaireCartes.lireCartesDuPlateau_Memori(Plateau.id_plateau);
	}
	
	
	public static List<String> listDePartieEnCours()
	{
		return gestionnairePlateau.listDePartieEnCours();
	}
	
	public static List<String> listDePartieFinie()
	{
		return  gestionnairePlateau.listDePartieFinie();
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

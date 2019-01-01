package dao.gestionnaire;

import java.util.List;

import carte.Carte;
import carte.PaquetCartes;
import dao.cartes.CartesDAO;
import dao.joueur.JoueurDAO;
import dao.plateau.PlateauDAO;
import jeu.Joueur;
import jeu.Plateau;

public abstract class Gestionnaire {	
	public abstract JoueurDAO getGestionnaireJoueur();
	public abstract CartesDAO getGestionnaireCartes();
	public abstract PlateauDAO getGestionnairePlateau();
	
	public void createDataPartie(Plateau plateauACreer)
	{	
		getGestionnairePlateau().create(plateauACreer);
		for (Carte carte : PaquetCartes.paquetCartes) 
		{
			getGestionnaireCartes().create(carte);	
		}
		
		int id_joueur;
		Joueur joueur_index = null;
		for (int i = 1; i <= Plateau.nombreDeJoueurs(); i++) 
		{
			joueur_index = Plateau.getJoueur(i);
			id_joueur = getGestionnaireJoueur().isPlayerExistReturnId(Plateau.getJoueur(i));
			
			if(id_joueur <1)
				getGestionnaireJoueur().create(joueur_index);				
			else
				joueur_index.setId(id_joueur);
			
			getGestionnaireJoueur().insertIntoParticipe(joueur_index);
		}
		getGestionnairePlateau().create_joueur_courant();
	}
	
	public void updateDataPartie()
	{
		getGestionnairePlateau().update();
		
		for (Carte carte : PaquetCartes.paquetCartes) 
		{
			getGestionnaireCartes().update(carte);
		}
		for (int i = 1; i <= Plateau.nombreDeJoueurs(); i++) 
		{
			getGestionnaireJoueur().update(Plateau.getJoueur(i));	
		}
		getGestionnairePlateau().update_joueur_courant();
	}
	
	public void supprimerDataPartie()
	{
		getGestionnaireCartes().deleteAll();
		getGestionnaireJoueur().deleteAll();
		getGestionnairePlateau().deleteAll();
	}
	
	
	public Plateau preparerCartesJoueursEtPlateau()
	{
		int choix_joueur = Joueur.SCANNER.nextInt();
		int id_partie = PlateauDAO.dicoCompteurPlateau.get(choix_joueur);
		System.out.println("id partie : " + id_partie);
		getGestionnaireCartes().lireCartesDuPlateau(id_partie);
		
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
		
	public boolean enregistrerVainqueur()
	{
		return getGestionnaireJoueur().enregistrerJoueurVainqueur();
	}

	public boolean deleteCartesEnMain() {
		return getGestionnaireCartes().deleteCartesEnMain();
	}

	public boolean createCartesEnMain() {
		return getGestionnaireCartes().createCartesEnMain();
	}

	public boolean supprimerCartesDuPaquet() 
	{
		return getGestionnaireCartes().supprimerCartesDuPaquet();
	}

	public boolean supprimerJoueurCourant() {
		return getGestionnaireJoueur().supprimerJoueurCourant();
	}
}

package jeu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import carte.PaquetCartes;
import dao.Gestionnaire;
import view.consoleView;

public class Plateau_PetitVerger extends Plateau 
{
	final static int POINTS_JOUEURS_A_ATTEINDRE = 5;
	final static int POINTS_CORBEAU_A_ATTEINDRE = 6;
	static int points_corbeau = 0;
	
	public Plateau_PetitVerger(long tempsDeJeuMillis, Date date_derniere_utilisation, List<Joueur> joueursDB,
			int indexDB) {
		super(tempsDeJeuMillis, date_derniere_utilisation, joueursDB, indexDB);
		// TODO Auto-generated constructor stub
	}
	
	public Plateau_PetitVerger(long tempsDeJeuMillis, Date date_derniere_utilisation, List<Joueur> joueursDB,
			int indexDB, int id_plateauDB) {
		super(tempsDeJeuMillis, date_derniere_utilisation, joueursDB, indexDB, id_plateauDB);
		// TODO Auto-generated constructor stub
	}

	//------------------GESTION JEU MEMORI------------------//	
	public static void combienCreerDeJoueurs() 
	{
		int reponse = 0;
		while(reponse < 1 || reponse >4)
		{
			consoleView.afficherMessage("");
			consoleView.afficherMessage("Souhaitez vous jouer seul ou � plusieur ? [1-4]");
			consoleView.afficherMessage("");
			reponse = Joueur.SCANNER.nextInt();
		}

		joueurs = new ArrayList<Joueur>(reponse);
		for(int x = 0; x < reponse; x++)
			joueurs.add(new Joueur());
		
		joueurActuel = joueurs.get(0);
	}
	
	public static void jouerAuPetitVerger()
	{	
		afficherPlateau();
		
		while(!estVictorieux())
		{
			if(nombreDeJoueurs() > 1)
				consoleView.afficherMessage("C'est le tour du joueur " + joueurActuel.getNumeroJoueur() + " [ " + joueurActuel.getNom() + " ]");

			//----- CHOIX DE LA PREMIERE CARTE -----// 
			if(joueurActuel.getPremiereCarte().getPositionIndexPaquet() == -1)
			{
				consoleView.afficherMessage("");
				consoleView.afficherMessage("Temps de jeu : " + tempsDeJeu());
				consoleView.afficherMessage("Veuillez choisir une premi�re carte : [1-" + getPaquetJeuTaille() + "]");
				
				int valeurChoix1Joueur = Joueur.SCANNER.nextInt() -1;
				while(valeurChoix1Joueur < 0 || valeurChoix1Joueur > getPaquetJeuTaille() -1 ||
						retournerCarte(valeurChoix1Joueur).getEstTrouve())
				{
					consoleView.afficherMessage("Veuillez choisir une autre premi�re carte : [1-" + getPaquetJeuTaille() + "]");	
					valeurChoix1Joueur = Joueur.SCANNER.nextInt() -1;				
				}
				
				joueurActuel.setPremiereCarte(retournerCarte(valeurChoix1Joueur));
				updateData();
				createCartesEnMain();
			}
			else
			{
				System.out.println(joueurActuel.getPremiereCarte().getPositionIndexPaquet());
				retournerCarte(joueurActuel.getPremiereCarte().getPositionIndexPaquet());
			}
			afficherPlateau();	
			//----- CHOIX DE LA DEUXIEME CARTE -----//
			consoleView.afficherMessage("");
			consoleView.afficherMessage("Temps de jeu : " + tempsDeJeu());
			consoleView.afficherMessage("Veuillez choisir une seconde carte : [1-" + getPaquetJeuTaille() + "]");
			
			int valeurChoix2Joueur = Joueur.SCANNER.nextInt() -1;

			while(valeurChoix2Joueur < 0 || valeurChoix2Joueur > getPaquetJeuTaille() -1 ||
					retournerCarte(valeurChoix2Joueur).getEstTrouve() ||  
					retournerCarte(valeurChoix2Joueur) == joueurActuel.getPremiereCarte())
			{
				consoleView.afficherMessage("Veuillez choisir une autre seconde carte : [1-" + getPaquetJeuTaille() + "]");	
				valeurChoix2Joueur = Joueur.SCANNER.nextInt() -1;				
			}
			
			joueurActuel.setSecondeCarte(retournerCarte(valeurChoix2Joueur));

			//----- VERIFICATION MEMORI -----//				
			afficherPlateau();
			verifierSiDouble();
			
			joueurActuel.reinitialiserSesCartes();
			deleteCartesEnMain();
			
			if(nombreDeJoueurs() > 1)
				passerAuJoueurSuivant();
			
			updateData();
		}
		afficherMessageVainqueur();
	}

	private static boolean estVictorieux()
	{
		int compteurEstTrouve = 0;
		
		while(compteurEstTrouve != getPaquetJeuTaille() && PaquetCartes.get(compteurEstTrouve).getEstTrouve())
			compteurEstTrouve +=1;
		
		return compteurEstTrouve == getPaquetJeuTaille();
	}
	
	private static void verifierSiDouble()
	{
		if(joueurActuel.getPremiereCarte().equals(joueurActuel.getSecondeCarte()))
		{
			joueurActuel.getPremiereCarte().setEstTrouve(true);
			joueurActuel.getSecondeCarte().setEstTrouve(true);
			joueurActuel.ajouterUnPoint();
		}
		else
		{
			joueurActuel.getPremiereCarte().carteRetourneVersDos();
			joueurActuel.getSecondeCarte().carteRetourneVersDos();
			joueurActuel.ajouterUneErreur();
		}
	}

	private static void afficherMessageVainqueur()
	{
		List<String> messages = new ArrayList<String>();
		if(nombreDeJoueurs() == 1)
		{
			joueurVainqueur = joueurActuel;
			messages.add("BRAVO " + joueurActuel.getNom() + " vous avez gagnez !");
			messages.add("Vous avez eu " + joueurActuel.getNombreErreurs() + " erreur(s) en " + tempsDeJeu());			
		}
		else
		{

			if(joueurs.get(0).getNombrePoints() > joueurs.get(1).getNombrePoints())
			{
				joueurVainqueur = joueurs.get(0);
				messages.add("BRAVO " + joueurs.get(0).getNom() + " vous avez gagnez !");
				messages.add("Vous avez le plus de points : " + joueurs.get(0).getNombrePoints());
				messages.add("D�sol� pour " + joueurs.get(1).getNom());
				messages.add("Vous avez eu moins de points : " + joueurs.get(1).getNombrePoints());
				messages.add("La partie s'est d�roul�e en : " +  tempsDeJeu());
			}
			else if(joueurs.get(1).getNombrePoints() > joueurs.get(0).getNombrePoints())
			{
				joueurVainqueur = joueurs.get(1);
				messages.add("BRAVO " + joueurs.get(1).getNom() + " vous avez gagnez !");
				messages.add("Vous avez le plus de points : " + joueurs.get(1).getNombrePoints());
				messages.add("D�sol� pour " + joueurs.get(0).getNom());
				messages.add("Vous avez eu moins de points : " + joueurs.get(0).getNombrePoints());
				messages.add("La partie s'est d�roul�e en : " +  tempsDeJeu());
			}
			else if(joueurs.get(0).getNombrePoints() == joueurs.get(1).getNombrePoints() && 
					joueurs.get(0).getNombreErreurs() < joueurs.get(1).getNombreErreurs())
			{
				joueurVainqueur = joueurs.get(0);
				messages.add(joueurs.get(0).getNom() + " et " + joueurs.get(1).getNom() + " �tes � �galit� en terme de points");
				messages.add("Mais "+ joueurs.get(0).getNom() + " a moins d'erreurs : " + joueurs.get(0).getNombreErreurs());
				messages.add("Alors que " + joueurs.get(1).getNom() + " a plus d'erreurs : " + joueurs.get(1).getNombreErreurs());
				messages.add("D�sol� pour " + joueurs.get(1).getNom() + ", " + joueurs.get(0).getNom() +" a gagn� ! BRAVO !");
				messages.add("La partie s'est d�roul�e en : " +  tempsDeJeu());				
			}
			else if(joueurs.get(0).getNombrePoints() == joueurs.get(1).getNombrePoints() && 
					joueurs.get(1).getNombreErreurs() < joueurs.get(0).getNombreErreurs())
			{
				joueurVainqueur = joueurs.get(1);
				messages.add(joueurs.get(1).getNom() + " et " + joueurs.get(0).getNom() + " �tes � �galit� en terme de points");
				messages.add("Mais "+ joueurs.get(1).getNom() + " a moins d'erreurs : " + joueurs.get(1).getNombreErreurs());
				messages.add("Alors que " + joueurs.get(0).getNom() + " a plus d'erreurs : " + joueurs.get(0).getNombreErreurs());
				messages.add("D�sol� pour " + joueurs.get(0).getNom() + ", " + joueurs.get(1).getNom() +" a gagn� ! BRAVO !");
				messages.add("La partie s'est d�roul�e en : " +  tempsDeJeu());		
			}
			else
			{
				messages.add(joueurs.get(1).getNom() + " et " + joueurs.get(0).getNom() + " �tes � �galit� en terme de points");
				messages.add("Vous �tes �galement � �galit� en terme de nombre d'erreurs (" + joueurs.get(0).getNombreErreurs() + ")");
				messages.add("Bravo � vous ! C'�tait malgr� tout une belle partie. Elle s'est d�roul�e en : " +  tempsDeJeu());						
			}
		}
		consoleView.afficherMessages(messages);
		
		Gestionnaire.enregistrerVainqueur();
		Gestionnaire.supprimerCartesDuPaquet();
		Gestionnaire.supprimerJoueurCourant();
	}
}

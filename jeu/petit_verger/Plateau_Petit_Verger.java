package jeu.petit_verger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import carte.Carte;
import carte.PaquetCartes;
import carte.motif.Motif_Memori;
import dao.Gestionnaire;
import jeu.Joueur;
import view.consoleView;

public class Plateau_Petit_Verger {
	public static int id_plateau;
	
	private static long tempsDeJeuMillisDB;
	public static Date date_nouvelle_utilisation = new Date();
	public static Date date_derniere_utilisationDB;
	public static long tempsDeJeuMillis;
	
	public static List<Joueur> joueurs = new ArrayList<Joueur>();
	public static Joueur joueurActuel;
	public static Joueur joueurVainqueur = null;

	public Plateau_Petit_Verger(long tempsDeJeuMillis, Date date_derniere_utilisation, List<Joueur> joueursDB, int indexDB, int id_plateauDB)
	{
		date_derniere_utilisationDB = date_derniere_utilisation;
		tempsDeJeuMillisDB = tempsDeJeuMillis;
		
		joueurs = joueursDB;

		joueurActuel = getJoueur(indexDB);
		
		id_plateau = id_plateauDB;
	}
	
	public Plateau_Petit_Verger(long tempsDeJeuMillis, Date date_derniere_utilisation, List<Joueur> joueursDB, int indexDB)
	{
		date_derniere_utilisationDB = date_derniere_utilisation;
		tempsDeJeuMillisDB = tempsDeJeuMillis;
		
		joueurs = joueursDB;

		joueurActuel = getJoueur(indexDB);
	}

	public static String getDate()
	{
		@SuppressWarnings("deprecation")
		String message = "Du " + date_derniere_utilisationDB.getDate() + "/" +
				date_derniere_utilisationDB.getMonth() + "/" 
				+ (1900 + date_derniere_utilisationDB.getYear()) +
				" à " +date_derniere_utilisationDB.getHours() + 
				"h et "+ date_derniere_utilisationDB.getMinutes() + "min";
		return message;
	}
	
	public static Joueur getJoueur(int index) {
		return joueurs.get(index - 1);
	}

	public static int getPaquetJeuTaille()
	{
		return PaquetCartes.getTaillePaquet();
	}
	
	public static int nombreDeJoueurs()
	{
		return joueurs.size();
	}

	//------------------GESTION JEU MEMORI------------------//	
	public static void combienCreerDeJoueurs() 
	{
		int reponse = 0;
		while(reponse < 1 || reponse >2)
		{
			consoleView.afficherMessage("");
			consoleView.afficherMessage("Souhaitez vous jouer seul ou à deux ? [1-2]");
			consoleView.afficherMessage("");
			reponse = Joueur.SCANNER.nextInt();
		}

		switch(reponse)
		{
		case 1:
			joueurs = new ArrayList<Joueur>(1);
			joueurs.add(new Joueur());
			break;
		case 2:
			joueurs = new ArrayList<Joueur>(2);
			joueurs.add(new Joueur());
			joueurs.add(new Joueur());
		}
		joueurActuel = joueurs.get(0);
	}
	
	public static void jouerAuMemori()
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
				consoleView.afficherMessage("Veuillez choisir une premiére carte : [1-" + getPaquetJeuTaille() + "]");
				
				int valeurChoix1Joueur = Joueur.SCANNER.nextInt() -1;
				while(valeurChoix1Joueur < 0 || valeurChoix1Joueur > getPaquetJeuTaille() -1 ||
						retournerCarte(valeurChoix1Joueur).getEstTrouve())
				{
					consoleView.afficherMessage("Veuillez choisir une autre première carte : [1-" + getPaquetJeuTaille() + "]");	
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
	
	private static String tempsDeJeu()
	{
		tempsDeJeuMillis = ( new Date().getTime() - date_nouvelle_utilisation.getTime() ) + tempsDeJeuMillisDB;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tempsDeJeuMillis);
		long seconds  = TimeUnit.MILLISECONDS.toSeconds(tempsDeJeuMillis);
		return minutes + " minutes " + seconds + " secondes.";
	}
	
	private static Carte retournerCarte(int indice)
	{
		PaquetCartes.get(indice).carteRetourneVersMotif();
		
		return PaquetCartes.get(indice);
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
	
	private static void passerAuJoueurSuivant()
	{
		switch(joueurs.indexOf(joueurActuel))
		{
		case 0:
			joueurActuel = joueurs.get(1);
			break;
		case 1:
			joueurActuel = joueurs.get(0);
		}
	}
			
	//----------------GERER AFFICHAGE PLATEAU----------------//
	private static void afficherPlateau()
	{	
		List<String> affichagePlateau = new ArrayList<String>();
		int tourDeBoucle;
		for (int i = 0; i < getPaquetJeuTaille(); i++) 
		{
			tourDeBoucle = i;
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,0));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,1));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,2));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,1));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,3));
			i += 3;
		}		
		consoleView.afficherMessages(affichagePlateau);
	}

	private static String lignePlateau(int tourDeboucle, int tourDeBoucleIntra, int numeroTemplate)
	{
		String ligne = "";

		for (tourDeBoucleIntra = tourDeboucle; tourDeBoucleIntra < tourDeboucle + 4; tourDeBoucleIntra++) {
			ligne += retournerTemplate(numeroTemplate, tourDeBoucleIntra);
		}
		return ligne;
	}
	
	private static String retournerTemplate(int numeroTemplate, int tourDeBoucleIntra)
	{
		String template = "";
		switch(numeroTemplate)
		{
		case 0:
			if(tourDeBoucleIntra + 1 >= 10)
				template = "|-----"+ (tourDeBoucleIntra+1) +"-----|";
			else
				template = "|------"+ (tourDeBoucleIntra+1) +"-----|";				
			break;
		case 1:
			template = "|            |";							
			break;
		case 2:
			template = "| "+ afficherCarte(tourDeBoucleIntra)  + "  |";
			break;
		default:
			template = "|------------|";					
		}
		return template;
	}
	
	private static Motif_Memori afficherCarte(int indice)
	{
		return indice < PaquetCartes.getTaillePaquet()? PaquetCartes.get(indice).getAffichage():Motif_Memori.VIDE;
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
				messages.add("Désolé pour " + joueurs.get(1).getNom());
				messages.add("Vous avez eu moins de points : " + joueurs.get(1).getNombrePoints());
				messages.add("La partie s'est déroulée en : " +  tempsDeJeu());
			}
			else if(joueurs.get(1).getNombrePoints() > joueurs.get(0).getNombrePoints())
			{
				joueurVainqueur = joueurs.get(1);
				messages.add("BRAVO " + joueurs.get(1).getNom() + " vous avez gagnez !");
				messages.add("Vous avez le plus de points : " + joueurs.get(1).getNombrePoints());
				messages.add("Désolé pour " + joueurs.get(0).getNom());
				messages.add("Vous avez eu moins de points : " + joueurs.get(0).getNombrePoints());
				messages.add("La partie s'est déroulée en : " +  tempsDeJeu());
			}
			else if(joueurs.get(0).getNombrePoints() == joueurs.get(1).getNombrePoints() && 
					joueurs.get(0).getNombreErreurs() < joueurs.get(1).getNombreErreurs())
			{
				joueurVainqueur = joueurs.get(0);
				messages.add(joueurs.get(0).getNom() + " et " + joueurs.get(1).getNom() + " êtes à égalité en terme de points");
				messages.add("Mais "+ joueurs.get(0).getNom() + " a moins d'erreurs : " + joueurs.get(0).getNombreErreurs());
				messages.add("Alors que " + joueurs.get(1).getNom() + " a plus d'erreurs : " + joueurs.get(1).getNombreErreurs());
				messages.add("Désolé pour " + joueurs.get(1).getNom() + ", " + joueurs.get(0).getNom() +" a gagné ! BRAVO !");
				messages.add("La partie s'est déroulée en : " +  tempsDeJeu());				
			}
			else if(joueurs.get(0).getNombrePoints() == joueurs.get(1).getNombrePoints() && 
					joueurs.get(1).getNombreErreurs() < joueurs.get(0).getNombreErreurs())
			{
				joueurVainqueur = joueurs.get(1);
				messages.add(joueurs.get(1).getNom() + " et " + joueurs.get(0).getNom() + " êtes à égalité en terme de points");
				messages.add("Mais "+ joueurs.get(1).getNom() + " a moins d'erreurs : " + joueurs.get(1).getNombreErreurs());
				messages.add("Alors que " + joueurs.get(0).getNom() + " a plus d'erreurs : " + joueurs.get(0).getNombreErreurs());
				messages.add("Désolé pour " + joueurs.get(0).getNom() + ", " + joueurs.get(1).getNom() +" a gagné ! BRAVO !");
				messages.add("La partie s'est déroulée en : " +  tempsDeJeu());		
			}
			else
			{
				messages.add(joueurs.get(1).getNom() + " et " + joueurs.get(0).getNom() + " êtes à égalité en terme de points");
				messages.add("Vous êtes également à égalité en terme de nombre d'erreurs (" + joueurs.get(0).getNombreErreurs() + ")");
				messages.add("Bravo à vous ! C'était malgré tout une belle partie. Elle s'est déroulée en : " +  tempsDeJeu());						
			}
		}
		consoleView.afficherMessages(messages);
		
		Gestionnaire.enregistrerVainqueur();
		Gestionnaire.supprimerCartesDuPaquet();
		Gestionnaire.supprimerJoueurCourant();
	}

	//----------------GERER SAUVEGARDE BD----------------//
	private static void updateData()
	{
		Gestionnaire.updateDataPartie();
	}
	
	private static void createCartesEnMain()
	{
		Gestionnaire.createCartesEnMain();
	}
	
	private static void deleteCartesEnMain()
	{
		Gestionnaire.deleteCartesEnMain();
	}
}

package jeu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import carte.motif.Motif_PetitVerger;
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
			consoleView.afficherMessage("Souhaitez vous jouer seul ou à plusieur ? [1-4]");
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
		
		while(!unParticipantEstGagnant())
		{
			if(nombreDeJoueurs() > 1)
				consoleView.afficherMessage("C'est le tour du joueur " + joueurActuel.getNumeroJoueur() + " [ " + joueurActuel.getNom() + " ]");

			//----- CHOIX CARTE JOUEUR -----// 
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

			//----- VERIFICATION MEMORI -----//				
			afficherPlateau();
			verifierCarte();
			afficherPlateau();

			
			joueurActuel.reinitialiserSesCartes();
			
			if(nombreDeJoueurs() > 1)
				passerAuJoueurSuivant();
			
//			updateData();
		}
		afficherMessageVainqueur();
	}

	private static boolean corbeauEstVictorieux()
	{
		return points_corbeau == POINTS_CORBEAU_A_ATTEINDRE;
	}
	
	private static boolean joueursSontVictorieux()
	{
		int nombreDePoints = 0;
		
		for (Joueur joueur : joueurs) {
			nombreDePoints += joueur.getNombrePoints();
		}
		
		return nombreDePoints == POINTS_JOUEURS_A_ATTEINDRE;
	}
	
	public static boolean unParticipantEstGagnant()
	{
		return joueursSontVictorieux()?true:corbeauEstVictorieux();
	}
	
	private static void verifierCarte()
	{
		String message = "";
		if(joueurActuel.getPremiereCarte().getMotif().equals(Motif_PetitVerger.CERISE))
		{
			joueurActuel.getPremiereCarte().setEstTrouve(true);
			joueurActuel.getPremiereCarte().carteEnleve();
			joueurActuel.ajouterUnPoint();
			message = joueurActuel.getNom() + " à trouvé une cerise ! Super !";
		}
		else if(joueurActuel.getPremiereCarte().getMotif().equals(Motif_PetitVerger.ANIMAL))
		{
			joueurActuel.getPremiereCarte().carteRetourneVersDos();
			message = joueurActuel.getNom() + " à trouvé un animal. Une papouille et on le laisse dormir";
		}
		else
		{
			joueurActuel.getPremiereCarte().carteRetourneVersDos();
			joueurActuel.ajouterUneErreur();
			points_corbeau++;
			message = joueurActuel.getNom() + " à trouvé un corbeau ! :o Il s'approche du cerisier !";
		}

		consoleView.afficherMessage("");
		consoleView.afficherMessage(message);
		consoleView.afficherMessage("");
	}

	private static void afficherMessageVainqueur()
	{
		List<String> messages = new ArrayList<String>();
		String noms_des_joueurs = "";
		
		for (Joueur joueur : joueurs) {
			noms_des_joueurs += joueur.getNom() + " ";
		}
		
		messages.add("");
		
		if(joueursSontVictorieux())
		{
			messages.add("BRAVO " + noms_des_joueurs);
			messages.add("Vous avez gagnez !");
		}
		else
		{
			messages.add("Malheuresement le corbeau vient de gagner 'Croa croa !'");
		}
		
		messages.add("La partie s'est déroulée en : " +  tempsDeJeu());
		
		consoleView.afficherMessages(messages);
		
//		Gestionnaire.enregistrerVainqueur();
//		Gestionnaire.supprimerCartesDuPaquet();
//		Gestionnaire.supprimerJoueurCourant();
	}
}

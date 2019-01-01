package main;

import controller.Controller;
import controller.ControllerMemori;
import controller.ControllerPetitVerger;
import jeu.Joueur;
import view.ConsoleView;

public class Main {

	public static Controller controller;
	
	public static void main(String[] args) 
	{
		ConsoleView.afficherTitre("JEUX DISPONIBLES");

		ConsoleView.afficherMessage("");
		ConsoleView.afficherMessage("1 - MEMORI");
		ConsoleView.afficherMessage("2 - PETIT VERGER");
		ConsoleView.afficherMessage("");
		
		ConsoleView.afficherOptions("CHOISISSEZ UN JEU");
		
		int choix_joueur = Joueur.SCANNER.nextInt();
		while(choix_joueur < 1 || choix_joueur > 2)
			choix_joueur = Joueur.SCANNER.nextInt();
		
		switch(choix_joueur)
		{
		case 1:
			controller = new ControllerMemori();
			break;
		case 2:
			controller = new ControllerPetitVerger();
		}
		controller.lancerPartie();
	}

}

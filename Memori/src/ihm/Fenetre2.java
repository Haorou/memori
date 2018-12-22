package ihm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre2 extends JFrame{

  public Fenetre2(){
    this.setTitle("MEMORI : Menu principal");
    this.setSize(1024, 768);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    // Affichage Menu Principal
    JPanel titre = new Panneau("Bienvenue dans le Memori");
    
    JPanel recaps = new JPanel();
    JPanel interaction = new JPanel();
    JPanel combineMP = new JPanel();
    

    
    //On crée un conteneur avec gestion horizontale
    Box b1 = Box.createHorizontalBox();
    b1.add(new JButton("Bouton 1"));
    //Idem
    Box b2 = Box.createHorizontalBox();
    b2.add(new JButton("Bouton 2"));
    b2.add(new JButton("Bouton 3"));
    //Idem
    Box b3 = Box.createHorizontalBox();
    b3.add(new JButton("Bouton 4"));
    b3.add(new JButton("Bouton 5"));
    b3.add(new JButton("Bouton 6"));

    GridLayout b5 = new GridLayout(3,2);
    b5.addLayoutComponent("Blabla", new JButton());
    /*
    top.setLayout(new BoxLayout(top,BoxLayout.PAGE_AXIS));
    top.add(b1);
    top.add(b2);
    top.add(b3);

    
    down.setLayout(new GridLayout(3,2));
    down.add("Blabla", new JButton());
    down.add("Blabla", new JButton());
    down.add("Blabla", new JButton("DADAD"));
    down.add("Blabla", new JButton());
    down.add("Blabla", new JButton());
    */
    
    combineMP.setLayout(new BoxLayout(combineMP,BoxLayout.PAGE_AXIS));
    combineMP.add(titre);
    combineMP.add(recaps);
    combineMP.add(interaction);
    
    this.getContentPane().add(combineMP);
    
    this.setVisible(true);
  }
  
  int indice = 0;
  public void test2()
  {
	  CardLayout cl = new CardLayout();
	  JPanel content = new JPanel();
	  //Liste des noms de nos conteneurs pour la pile de cartes
	  String[] listContent = {"CARD_1", "CARD_2", "CARD_3"};


	this.setTitle("CardLayout");
	this.setSize(300, 120);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocationRelativeTo(null);
		
	//On crée trois conteneurs de couleur différente
	JPanel card1 = new JPanel();
	card1.setBackground(Color.blue);		
	JPanel card2 = new JPanel();
	card2.setBackground(Color.red);		
	JPanel card3 = new JPanel();
	card3.setBackground(Color.green);
	
	JPanel boutonPane = new JPanel();
	JButton bouton = new JButton("Contenu suivant");
	//Définition de l'action du bouton
	bouton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent event){
	    //Via cette instruction, on passe au prochain conteneur de la pile
	    cl.next(content);
	  }
	});
		
	JButton bouton2 = new JButton("Contenu par indice");
	//Définition de l'action du bouton2
	bouton2.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent event){				
	    if(++indice > 2)
	      indice = 0;
	    //Via cette instruction, on passe au conteneur correspondant au nom fourni en paramètre
	    cl.show(content, listContent[indice]);
	  }
	  });
		
	boutonPane.add(bouton);
	boutonPane.add(bouton2);
	//On définit le layout
	content.setLayout(cl);
	//On ajoute les cartes à la pile avec un nom pour les retrouver
	content.add(card1, listContent[0]);
	content.add(card2, listContent[1]);
	content.add(card3, listContent[2]);
	
	this.getContentPane().add(boutonPane, BorderLayout.NORTH);
	this.getContentPane().add(content, BorderLayout.CENTER);
	this.setVisible(true);
  }
  
  public void test()
  {
	    this.setTitle("Box Layout");
	    this.setSize(500, 350);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);

	    JPanel top = new JPanel();
	    JPanel down = new JPanel();
	    JPanel combine = new JPanel();
	    
	    //On crée un conteneur avec gestion horizontale
	    Box b1 = Box.createHorizontalBox();
	    b1.add(new JButton("Bouton 1"));
	    //Idem
	    Box b2 = Box.createHorizontalBox();
	    b2.add(new JButton("Bouton 2"));
	    b2.add(new JButton("Bouton 3"));
	    //Idem
	    Box b3 = Box.createHorizontalBox();
	    b3.add(new JButton("Bouton 4"));
	    b3.add(new JButton("Bouton 5"));
	    b3.add(new JButton("Bouton 6"));

	    GridLayout b5 = new GridLayout(3,2);
	    b5.addLayoutComponent("Blabla", new JButton());
	    
	    top.setLayout(new BoxLayout(top,BoxLayout.PAGE_AXIS));
	    top.add(b1);
	    top.add(b2);
	    top.add(b3);

	    
	    down.setLayout(new GridLayout(3,2));
	    down.add("Blabla", new JButton());
	    down.add("Blabla", new JButton());
	    down.add("Blabla", new JButton("DADAD"));
	    down.add("Blabla", new JButton());
	    down.add("Blabla", new JButton());
	    
	    
	    combine.setLayout(new BoxLayout(combine,BoxLayout.PAGE_AXIS));
	    combine.add(top);
	    combine.add(down);
	    
	    this.getContentPane().add(combine);
	    
	    this.setVisible(true);
  }
}
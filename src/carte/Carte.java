package carte;

public class Carte {
	
	private int id_carte;
	
	private final Motif motifCarte;	
	private Motif affichage = Motif.DOS;
	private boolean estTrouve = false;
	private int positionIndexPaquet = -1;
	
	public Carte()
	{
		this.motifCarte = Motif.get(11);
	}
	
	public Carte(int indiceMotif) {
		this.motifCarte = Motif.get(indiceMotif);
	}
	
	public Carte(Motif motifDB, boolean estTrouveDB, int positionIndexPaquetDB, int id_carteDB)
	{
		this.motifCarte = motifDB;
		this.estTrouve = estTrouveDB;
		if(this.estTrouve)
		{
			this.affichage = this.motifCarte;
		}
		this.positionIndexPaquet = positionIndexPaquetDB;
		this.id_carte = id_carteDB;
	}
	
	public Carte(Motif motifDB, boolean estTrouveDB, int positionIndexPaquetDB)
	{
		this.motifCarte = motifDB;
		this.estTrouve = estTrouveDB;
		if(this.estTrouve)
		{
			this.affichage = this.motifCarte;
		}
		this.positionIndexPaquet = positionIndexPaquetDB;
	}
	
	public int getId()              {   return this.id_carte;   }
	public void setId(int id)       {   this.id_carte=id;   	}
	public Motif getMotif()			{	return this.motifCarte;	}
	public Motif getAffichage()		{	return this.affichage;	}
	public boolean getEstTrouve()	{	return this.estTrouve;	}
	
	
	public int getPositionIndexPaquet() {
		return positionIndexPaquet;
	}

	public void setPositionIndexPaquet(int positionIndexPaquet) {
		this.positionIndexPaquet = positionIndexPaquet;
	}

	public void setEstTrouve(boolean bool) {
		this.estTrouve = bool;
	}

	public void carteRetourneVersMotif() {
		this.affichage = this.motifCarte;
	}
	
	public void carteRetourneVersDos() {
		this.affichage = Motif.DOS;
	}
	
	public boolean equals(Carte autre)
	{
		return this.getMotif() == autre.getMotif();
	}

}
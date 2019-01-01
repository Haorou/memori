package carte;

import carte.motif.IMotif;
import carte.motif.Motif_PetitVerger;

public class Carte_PetitVerger extends Carte {
	
	private IMotif dos;
	private IMotif enleve = Motif_PetitVerger.VIDE;
	
	public Carte_PetitVerger() {
		super();
	}
	
	public Carte_PetitVerger(Motif_PetitVerger iMotif) {
		super(iMotif);
	}

	public Carte_PetitVerger(IMotif motif, boolean b, int positionIndex) {
		super(motif,b,positionIndex);
	}
	
	@Override
	public IMotif getDos() {
		// TODO Auto-generated method stub
		return dos;
	}

	@Override
	public void setDos(IMotif dos) {
		this.dos = dos; 		
		carteRetourneVersDos();		
	}

	@Override
	public IMotif getEnleve() {
		return enleve;
	}

}

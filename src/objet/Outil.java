package objet;

import java.util.Random;

public class Outil extends Objet {
    private String nom;
    private int niveau;
    public static Random random = new Random();
    public Outil(String nom) {
    	this.nom=nom;
    }
	public String getNom() {
		return nom;
	}
	
	public int getNiveau() {
		return niveau;
	}
	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

}

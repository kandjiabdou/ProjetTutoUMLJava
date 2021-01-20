package personnage;


import java.util.Random;

import carte.Carte;
import objet.PotionDeSoin;
import objet.PotionExplosion;
import objet.Vetement;

public class Monstre extends Personnage {
	private int precedentXl;
	private int precedentYL;
	private int niveau;
	public static String[] NOM_MONSTRE={"Lapin Garou","Troll","Dragon"};
	public static String[] TO_STRING={"L","T","D"};
	public static int[] EXPERIANCE={20,50,100};
	public static int[] INITIATIVE={4,9,10};
	public static int[] ATTAQUE={7,8,12};
	public static int[] DEFENSE={3,6,7};
	public static int[] ESQUIVE={5,7,8};
	public static int[] DEGAT={6,8,11};
	
	private static int nombreDeMonstre=0; 
	
	private static Random rdm = new Random(); 
    public Monstre(Carte c,String nom) {
		super(c,nom);
	}
    public Monstre(Carte c, int niveau) {
    	this(c,NOM_MONSTRE[niveau]);
    	this.niveau=niveau;
		this.setInitiative(INITIATIVE[niveau]);
		this.setAttaque(ATTAQUE[niveau]);
		this.setEsquive(ESQUIVE[niveau]);
		this.setDefense(DEFENSE[niveau]);
		this.setDegat(DEGAT[niveau]);
		this.setExperiance(EXPERIANCE[niveau]);
		nombreDeMonstre=nombreDeMonstre+1;
	}
    public static Monstre creerMonstre(Carte c) {
    	int n=rdm.nextInt(3);
    	return new Monstre(c, n);
    }

    public int getPrecedentXl() {
		return precedentXl;
	}

	public void setPrecedentXl(int precedentXl) {
		this.precedentXl = precedentXl;
	}

	public int getPrecedentYL() {
		return precedentYL;
	}

	public void setPrecedentYL(int precedentYL) {
		this.precedentYL = precedentYL;
	}

	public void attaquer() {
    }

    public void deplacer() {
    	int n =1+rdm.nextInt(4);
    	String sens = null;
    	switch (n) {
    	case 1:
    		sens="h";
    		break;
    	case 2:
    		sens="b";
    		break;
    	case 3:
    		sens="d";
    		break;
    	case 4:
    		sens="g";
    		break;
    	}
    	super.deplacer(sens);
    }
    
    public String toString() {
        return TO_STRING[this.niveau];
    }

    public void mourir() {
		super.mourir();
		if (this.niveau==2) {
			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=PotionDeSoin.creerPotionDeSoin();
		}
		if (this.niveau==1) {
			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=PotionExplosion.creerPotionExplosion(carte);
		}
		if (this.niveau==0) {
			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=Vetement.creerVetement();
		}
		
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

package objet;

import carte.Carte;

public abstract class Arme extends Outil {
    private int degat;

    private int portee;
    
    private int maniabilite;

    public Carte carte;

    public Arme( Carte carte,String nom) {
        super(nom);
        this.carte=carte;
    }

    public int getDegat() {
        return degat;
    }

    public void setDegat(int degat) {
        this.degat = degat;
    }

    public abstract int faireAttaque(int y, int x,int attaque,int degat, String sens,int exp);

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    public int getManiabilite() {
        return this.maniabilite;
    }

    public void setManiabilite(int value) {
        this.maniabilite = value;
    }

}

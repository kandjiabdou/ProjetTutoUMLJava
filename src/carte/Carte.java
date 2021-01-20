package carte;

import objet.*;
import personnage.*;

public class Carte {
// Attributs
    public static int LONGUEUR = 22;
    public static int LARGEUR = 20;
    
    public Objet[][] casePlateau;

    public PersonnageJoueur joueur1=new PersonnageJoueur(this,"1");
    public PersonnageJoueur joueur2=new PersonnageJoueur(this,"2");
    
    public Monstre[] monstres = {null,null,null};
    public PersonnageNonJoueur[] pnjs= {null,null,null};
    
    public static final String JOUEUR_1 = "1";
    public static final String JOUEUR_2 = "2";
	public static final String AUTRE_JOUEUR = "P";
	public static final String VIDE = "_";
	public static final String MUR = "#";
	public static final String MONSTRE = "m";
	public static final String OBJET = "o"; 

// Constructeurs
    public Carte() {

    	this.casePlateau= new Objet[LONGUEUR][LARGEUR];
        String chaineOjbet =FichierCarte.chargerListeObjet();
        
        for(int i=0; i <LONGUEUR;i++) {
        	for(int j=0;j<LARGEUR;j++) {
        		switch ((String.valueOf(chaineOjbet.charAt(i*LARGEUR+j)))) {
	        		case MUR:
	        			this.casePlateau[i][j] = new Mur();
	        			break;
	        		case VIDE:
	        			this.casePlateau[i][j] = new Vide();
	        			break;
	        		case JOUEUR_1:
	        			this.casePlateau[i][j] = joueur1;
	        			joueur1.setPositionYLxl(i, j);
	        			break;
	        		case JOUEUR_2:
	        			this.casePlateau[i][j] = joueur2;
	        			joueur2.setPositionYLxl(i, j);
	        			break;
	        		case AUTRE_JOUEUR:
	        			break;
	        		case OBJET:
	        			break;
	        		case MONSTRE:
	        			break;
        		}
        	}
        }
        /*
        casePlateau[12][15]=Arc.creerArc(this);
        casePlateau[1][1]=Arc.creerArc(this);
        casePlateau[7][16]=Epee.creerEpee(this);
        casePlateau[3][4]=Epee.creerEpee(this);
        casePlateau[2][1]=Vetement.creerVetement();
        casePlateau[2][2]=Vetement.creerVetement();
        casePlateau[3][1]=Casque.creerCasque();
        casePlateau[4][1]=Casque.creerCasque();
        casePlateau[5][1]=Chaussure.creerChaussure();
        casePlateau[6][3]=Chaussure.creerChaussure();
        casePlateau[7][4]=PotionDeSoin.creerPotionDeSoin();
        casePlateau[7][5]=PotionDeSoin.creerPotionDeSoin();
        casePlateau[8][4]=Bouclier.creerBouclier(this);
        casePlateau[8][5]=Bouclier.creerBouclier(this);
        */
        for(int k=0;k<3;k++) {
        	int j,i;
        	do {
        		j=Outil.random.nextInt(LONGUEUR-2)+1;
        		i=Outil.random.nextInt(LARGEUR-2)+1;
        	}while(estVide(j, i));
        	monstres[k]=Monstre.creerMonstre(this);
        	monstres[k].setPositionYLxl(j, i);
        	do {
        		j=Outil.random.nextInt(LONGUEUR-2)+1;
        		i=Outil.random.nextInt(LARGEUR-2)+1;
        	}while(estVide(j, i));
        	pnjs[k]=PersonnageNonJoueur.creerPNJ(this);
        	pnjs[k].setPositionYLxl(j, i);
        }
        
    }

    public void afficher() {
    	for(int i=0; i <LONGUEUR;i++) {
        	for(int j=0;j<LARGEUR;j++) {
        		System.out.print(this.casePlateau[i][j]);
        	}
        	System.out.println();
        }
    }
    
    public String afficherFen() {
    	String c="";
    	for(int i=0; i <LONGUEUR;i++) {
        	for(int j=0;j<LARGEUR;j++) {
        		c+=this.casePlateau[i][j];
        	}
        	c+="\n";
        }
    	
    	return c;
    }

// Methodes
    public int getLongueur() {
        return LONGUEUR;
    }

    void setLongueur(int value) {
        LONGUEUR = value;
    }

    public int getLargeur() {
        return LARGEUR;
    }

    void setLargeur(int value) {
        LARGEUR = value;
    }

    public boolean estVide(int yL,int xl) {
    	if(yL<0 || yL>LONGUEUR || xl<0 || xl>LARGEUR) return false;
    	else return this.casePlateau[yL][xl].toString()==VIDE;
    }
    public boolean estOutil(int y,int x) {
		return this.getObjet(y, x) instanceof Outil;
	}

    public boolean estPasBarriere(int yL,int xl) {
        return this.casePlateau[yL][xl].toString()==MUR;
    }

   

    public void charger() {
    	
    }

    public void sauver() {
    }

    public void setObjet(int x,int y, Objet o) {
        if (this.estVide( y, x) && ! this.estPasBarriere(y,x)) this.casePlateau[y][x]=o;
    }

    public Objet getObjet(int y,int x) {
        return this.casePlateau[y][x];
    }

}

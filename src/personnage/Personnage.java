package personnage;
import carte.Carte;
import carte.Vide;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;
import objet.*;

public abstract class Personnage extends Objet {
    private int positionXl;
    private int positionYL;
    
    private String nom;
    
    private int niveauVie=100;
    private int experiance;
    
    private int initiative=2;
    private int attaque=4;
    private int degat=8;
    private int esquive=5;
    private int defense=6;
    
	public Carte carte ;
	
	private Outil saveOjet;
	private Outil saveOjet2;
	
	
	private String directionFace=HAUT;
	
	public static final String BAS = "b";
	public static final String GAUCHE = "g";
	public static final String HAUT = "h";
	public static final String DROITE = "d";
	
	private boolean deplacemet=true;
	public Personnage(Carte c, String nom) {
		this.carte=c;
		this.nom=nom;
	}
	public void attaquer() {
    }
    public int getPositionXl() {
        return this.positionXl;
    }

    public void setPositionXl(int xl) {
    	this.positionXl = xl;
    }
    public int getPositionYL() {
        return this.positionYL;
    }

    public void setPositionYL(int yL) {
        this.positionYL = yL;
    }
    
    public void setPositionYLxl(int yL,int xl) {
        this.positionXl = xl; this.positionYL = yL;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public Outil getSaveOjet() {
		return saveOjet;
	}
	public int getNiveauVie() {
		return niveauVie;
	}

	public void setNiveauVie(int niveauVie) {
		this.niveauVie = niveauVie;
	}
	
	public int getExperiance() {
		return experiance;
	}

	public void setExperiance(int experiance) {
		this.experiance = experiance;
	}
	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public int getAttaque() {
		return attaque;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}

	public int getEsquive() {
		return esquive;
	}

	public void setEsquive(int esquive) {
		this.esquive = esquive;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public Outil getSaveOjet2() {
		return saveOjet2;
	}

	public void setSaveOjet2(Outil saveOjet2) {
		this.saveOjet2 = saveOjet2;
	}
	public void setSaveOjet(Outil saveOjet) {
		this.saveOjet = saveOjet;
	}

	public String getDirectionFace() {
		return directionFace;
	}

	public void setDirectionFace(String directionFace) {
		this.directionFace = directionFace;
	}

	public boolean estOutil(int y,int x) {
		return this.carte.getObjet(y, x) instanceof Outil;
	}

	public void setCarte (Carte carte) {
		if(this.carte == null){
			this.carte = carte;
		}
	}
	public void deplacer(String sens) {
		if(this.directionFace==sens && this.isDeplacemet()) {
			switch (sens) {
	        case Personnage.HAUT:	
	        	if (this.carte.estVide(this.getPositionYL()-1,this.getPositionXl())) {
	            	this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()]=this;
	            	this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
	                this.setPositionYL(this.getPositionYL()-1);
	                if(!(this.saveOjet==null)) {
	                	this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()] = (Objet) this.saveOjet;
	                	this.saveOjet=null;
	                }
	        	}else if (this.estOutil(this.getPositionYL()-1,this.getPositionXl())) {
	        		if(!(this.saveOjet==null)) {
	        			this.saveOjet2 = (Outil)this.carte.getObjet(this.getPositionYL()-1,this.getPositionXl());
	        			this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()]=this;
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=this.saveOjet;
	        			this.saveOjet = this.saveOjet2;
	        			this.saveOjet2=null;
	        			System.out.println("Objet dessous : "+this.saveOjet);
	        			this.setPositionYL(this.getPositionYL()-1);
	        		}else {
	        			this.saveOjet = (Outil)this.carte.getObjet(this.getPositionYL()-1,this.getPositionXl());
	        			System.out.println("Objet dessous : "+this.saveOjet);
	        			this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()]=this;
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
	        			this.setPositionYL(this.getPositionYL()-1);
	        		}
	        	}
	        	break;
	        case Personnage.BAS :
	        	if (this.carte.estVide(this.getPositionYL()+1,this.getPositionXl())) {
	        		this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()]=this;
	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
	                this.setPositionYL(this.getPositionYL()+1);
	                if(!(this.saveOjet==null)) {
	                	this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()] = (Objet) this.saveOjet;
	                	this.saveOjet=null;
	                }
	        	}else if (this.estOutil(this.getPositionYL()+1,this.getPositionXl())) {
	        		if(!(this.saveOjet==null)) {
	        			this.saveOjet2 = (Outil)this.carte.getObjet(this.getPositionYL()+1,this.getPositionXl());
	        			this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()]=this;
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=this.saveOjet;
	        			this.saveOjet = this.saveOjet2;
	        			this.saveOjet2=null;
	        			System.out.println("Objet dessous : "+this.saveOjet);
	        			this.setPositionYL(this.getPositionYL()+1);
	        		}else {
	        			this.saveOjet= (Outil)this.carte.getObjet(this.getPositionYL()+1,this.getPositionXl());
		        		System.out.println("Objet dessous : "+this.saveOjet);
		        		this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()]=this;
		        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
		        		this.setPositionYL(this.getPositionYL()+1);
	        		}
	        	}
	            break;
	        case Personnage.GAUCHE :
	        	if (this.carte.estVide(this.getPositionYL(),this.getPositionXl()-1)) {
	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1]=this;
	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
	                this.setPositionXl(this.getPositionXl()-1);
	                if(!(this.saveOjet==null)) {
	                	this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1] = (Objet) this.saveOjet;
	                	this.saveOjet=null;
	                }
	            }else if (this.estOutil(this.getPositionYL(),this.getPositionXl()-1)) {
	            	if(!(this.saveOjet==null)) {
	            		this.saveOjet2 = (Outil)this.carte.getObjet(this.getPositionYL(),this.getPositionXl()-1);
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1]=this;
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=this.saveOjet;
	        			this.saveOjet = this.saveOjet2;
	        			this.saveOjet2=null;
	        			System.out.println("Objet dessous : "+this.saveOjet);
	        			this.setPositionXl(this.getPositionXl()-1);
                	}else {
                		this.saveOjet= (Outil)this.carte.getObjet(this.getPositionYL(),this.getPositionXl()-1);
	            		System.out.println("Objet dessous : "+this.saveOjet);
	            		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1]=this;
	            		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
	                    this.setPositionXl(this.getPositionXl()-1);
                	}
	            }
	            break;
	        case Personnage.DROITE :
	        	if (this.carte.estVide(this.getPositionYL(),this.getPositionXl()+1)) {
	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1]=this;
	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
	                this.setPositionXl(this.getPositionXl()+1);
	                if(!(this.saveOjet==null)) {
	                	this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1] = (Objet) this.saveOjet;
	                	this.saveOjet=null;
	                }
	        	}else if (this.estOutil(this.getPositionYL(),this.getPositionXl()+1)) {
	        		if(!(this.saveOjet==null)) {
	        			this.saveOjet2 = (Outil)this.carte.getObjet(this.getPositionYL(),this.getPositionXl()+1);
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1]=this;
	        			this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=this.saveOjet;
	        			this.saveOjet = this.saveOjet2;
	        			this.saveOjet2=null;
	        			System.out.println("Objet dessous : "+this.saveOjet);
	        			this.setPositionXl(this.getPositionXl()+1);
                	}else {
                		this.saveOjet= (Outil)this.carte.getObjet(this.getPositionYL(),this.getPositionXl()+1);
    	        		System.out.println("Objet dessous : "+this.saveOjet);
    	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1]=this;
    	        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
    	                this.setPositionXl(this.getPositionXl()+1);
                	}
	        	}
	            break;
	        }
		}else {//Dans tous les cas la direction de sa face va changer
			this.directionFace=sens;
		}
    }
	public int attaquer(Personnage p, int exp) {
	    System.out.println("personnage : "+p);
	    int e=p.getEsquive();
    	int a=this.getAttaque();
    	System.out.println("J'attaque un personnage avec "+a+" | il esquive "+e);
    	if(e<a) {
    		//on procede à la determination des blessure
    		int blessure= this.getDegat()-p.getDefense();
    		System.out.println("\tDétermination de blessure qui est de "+blessure);
    		if(blessure>0) {
    			// on inflige les degats
    			System.out.println("\t\tInflige des degats "+blessure);
    			System.out.println("\t\tAvt attak "+p.getNiveauVie());
    			p.setNiveauVie(p.getNiveauVie()-blessure);
    			System.out.println("\t\tAps attak "+p.getNiveauVie());
    			if(p.getNiveauVie()<15 && p.getNiveauVie()>0) {
    				// le personnage est inconscient
    				System.out.println("\t\t\tle personnage est inconscient PA nuls!!!");
    				if(p instanceof PersonnageJoueur) ((PersonnageJoueur) p).setPointAction(0);
    			}
    			if(p.getNiveauVie()<0) {
    				// le personnage meurt
    				System.out.println("\t\t\tle personnage est meurt !!!!!");
    				int exper= (p.getExperiance()/exp);
    				p.mourir();
    				return exper;
    			}
    			System.out.println("\t\tFin Inflige des degats ......");
    		}
    		System.out.println("\tFin détermination de blessure ..........");
    	}
    	System.out.println("Fin attaque ................");
    	return 0;
    }
	
	public void mourir() {
		this.setDeplacemet(false);
	};

	public boolean isDeplacemet() {
		return deplacemet;
	}

	public void setDeplacemet(boolean deplacemet) {
		this.deplacemet = deplacemet;
	}
	
}

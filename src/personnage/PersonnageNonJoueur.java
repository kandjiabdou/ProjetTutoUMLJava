package personnage;

import java.util.Random;

import carte.Carte;
import objet.*;


public class PersonnageNonJoueur extends Personnage {
	// Attributs
    private int force;
    private int adresse;
    private int resistance;
    
    private Outil mainGauche=null;
    private Outil mainDroite=null;
    
    //    private Armure armure=new Armure();
    private Armure armure=new Armure();
	private int niveau;
	public static String[] NOM_PNJ={"Gladiateur","Viking","Magicien"};
	public static String[] TO_STRING={"G","V","M"};
	public static int[] EXPERIANCE={20,50,100};
	public static int[] FORCE={3,5,8};
	public static int[] ADRESSE={5,6,6};
	public static int[] RESISTANCE={4,3,4};
	public static int[] INITIATIVE={4,9,10};
	public static int[] ATTAQUE={7,8,12};
	public static int[] DEFENSE={3,6,7};
	public static int[] ESQUIVE={5,7,8};
	public static int[] DEGAT={6,8,11};
	
	private static int nombreDeMonstre=0; 
	
	private static Random rdm = new Random(); 
	
// consstructeur
    public PersonnageNonJoueur(Carte c,String nom) {
		super(c,nom);
	}
    public PersonnageNonJoueur(Carte c, int niveau) {
    	this(c,NOM_PNJ[niveau]);
    	this.setNiveau(niveau);
		this.setInitiative(INITIATIVE[niveau]);
		this.setAttaque(ATTAQUE[niveau]);
		this.setEsquive(ESQUIVE[niveau]);
		this.setDefense(DEFENSE[niveau]);
		this.setDegat(DEGAT[niveau]);
		this.setExperiance(EXPERIANCE[niveau]);
		this.mainGauche=new Bouclier(c, niveau);
		if (niveau==2) {
			this.mainDroite=new PotionExplosion(c, niveau);
			this.mainGauche=new Bouclier(c, niveau);
			}
		if (niveau==1) {
			this.mainDroite=new Arc(c, niveau);
			this.mainGauche=new Bouclier(c, niveau);
			}
		if (niveau==0) {
			this.mainDroite=new Epee(c, niveau);
			this.mainGauche=new Bouclier(c, niveau);
			}
		
		nombreDeMonstre=nombreDeMonstre+1;
	}
    public static PersonnageNonJoueur creerPNJ(Carte c) {
    	int n=rdm.nextInt(3);
    	return new PersonnageNonJoueur(c, n);
	}
// Mï¿½thodes
    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getAdresse() {
        return adresse;
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
    
    public Outil getMainGauche() {
		return mainGauche;
	}

	public void setManGauche(Outil manGauche) {
		this.mainGauche = manGauche;
	}

	public Outil getMainDroite() {
		return mainDroite;
	}

	public void setMainDroite(Outil mainDroite) {
		this.mainDroite = mainDroite;
	}
	public Casque getCasque() {
		return armure.getCasque();
	}

	public void setCasque(Casque casque) {
		this.armure.setCasque(casque);
	}

	public Vetement getVetement() {
		return armure.getVetement();
	}

	public void setVetement(Vetement vetement) {
		this.armure.setVetement(vetement);
	}

	public Chaussure getChaussure() {
		return this.armure.getChaussure();
	}

	public void setChaussure(Chaussure chaussure) {
		this.armure.setChaussure(chaussure);
	}
	public void infoArmure() {
    	armure.infoArmure();
    }
	public int getSolidite() {
    	
    	return armure.getSolidite();
    }
    public int getEncombrement() { 
    	return armure.getEncombrement();
    }
    public String armureEquipe() {
    	return armure.armureEquipe();
    }
    public String toString() {
        return TO_STRING[this.niveau];
    }
    public String infoADR() {
    	String a="Adresse : ";
    	a+=String.valueOf(this.adresse/3)+"D";
    	a+= this.adresse%3==0 ? "" : "+"+String.valueOf(this.adresse%3);
    	return a;
    }
    public String infoFRC() {
    	String f="Fore : ";
    	f+=String.valueOf(this.getForce()/3)+"D";
    	f+= this.getForce()%3==0 ? "" : "+"+String.valueOf(this.getForce()%3);
    	return f;
    }
    public String infoRST() {
    	String r="Resistance : ";
    	r+=String.valueOf(this.resistance/3)+"D";
    	r+= this.resistance%3==0 ? "" : "+"+String.valueOf(this.resistance%3);
    	return r;
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
    public void attaquer(int cote) {
    	// si les points d'actions ne suff
    	// Si le cote d'attaque est 1 on attaque avec la main droite sinon avec la gauche
    	Outil outil = cote==1 ? this.getMainDroite() : (Outil) this.getMainGauche();
    	// Si cette main pocéde un outil ou il n'a pas de point
		if(outil!=null) {//Attaque avec la main qui a l'outil
			int exp=((Arme) outil).faireAttaque(this.getPositionYL(), this.getPositionXl(), this.getAttaque(),this.getDegat(),this.getDirectionFace(),this.getExperiance());
			this.setExperiance(this.getExperiance()+exp);
		}else {// Sinon il attaque avec la main
    		// mais le dégat peut etre lié à l'expériance !!
    		int j=this.getPositionYL();
            int i=this.getPositionXl();
            System.out.println("Je fais un attaque avec la main ");
            System.out.println("Position : y : "+j+", x : "+i+", face "+this.getDirectionFace());
            switch (this.getDirectionFace()) {
                case "h":
                    j--;
                    break;
                case "b":
                    j++;
                    break;
                case "d":
                    i++;
                    break;
                case "g":
                    i--;
                    break;
                default:
                    break;
            }
            
            Objet o= carte.getObjet(j, i);
            System.out.println("Position impacte y : "+j+", x : "+i+"| objet : "+o.getClass());
            if(o instanceof Personnage) {
            	super.attaquer((Personnage)o,this.getExperiance());
            }
    	}
    }

	public void mourir() {
		super.mourir();
		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=this.mainDroite;
//		for(int j=this.getPositionYL()-1;j<Carte.LONGUEUR ;j++) {
//			for(int i=this.getPositionXl()-1;(j<Carte.LARGEUR && j >0);i++) {
//				if(j<Carte.LONGUEUR && j)
//				if(carte.estVide(j, j)) {
//					
//				}
//			}
//		}
		
	}
	/**
	 * @return the niveau
	 */
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

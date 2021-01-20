package objet;

import carte.Carte;
import personnage.Personnage;

public class Arc extends Arme {
    private int capaciteBalle;
	public static String[] NOM_ARC={"Arc traditionnel (I)","Arc à poulie (II)","Arc Longbow(III)"};
	public static String[] TO_STRING={"a","a","a"};
	public static int[] DEAGAT={2,4,5};
	public static int[] MANIABILITE={3,4,5};
	public static int[] PORTEE={3,5,10};
	public static int[] CAPACITE_BALLE={10,15,20};
	
    public Arc(Carte carte,String nom) {
        super(carte,nom);
    }
    public Arc(Carte carte, int niveau) {
        this(carte,NOM_ARC[niveau]);
        this.setNiveau(niveau);
        this.setDegat(DEAGAT[niveau]);
        this.setManiabilite(MANIABILITE[niveau]);
        this.setPortee(PORTEE[niveau]);
        this.setCapaciteBalle(CAPACITE_BALLE[niveau]);
    }
    
    public static Arc creerArc(Carte c) {
    	int n=Arme.random.nextInt(3);
    	return new Arc(c,n);
    }
    int getCapaciteBalle() {
        return this.capaciteBalle;
    }

    void setCapaciteBalle(int value) {
        this.capaciteBalle = value;
    }

    public int faireAttaque(int y, int x, int a, int d, String sens, int exp) {

        int j=y;
        int i=x;
        int distance=0;
        System.out.println("Je fais un attaque avec un fusil ");
        System.out.println("Position : y : "+j+", x : "+i+", face "+sens);
        do {
            switch (sens) {
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
            distance++;
            }while((carte.estVide(j, i) || carte.estOutil(j, i)) && distance<this.getPortee());        
        Objet o= carte.getObjet(j, i);
        System.out.println("Position impacte y : "+j+", x : "+i+"| objet : "+o);
        if(o instanceof Personnage) {
        	int e=((Personnage) o).getEsquive();
        	System.out.println("J'attaque un personnage avec "+a+" | il esquive "+e);
        	if(e<a) {
        		//on procede à la determination des blessure
        		int blessure= d-((Personnage) o).getDefense();
        		System.out.println("\tDétermination de blessure qui est de "+blessure);
        		if(blessure>0) {
        			// on inflige les degats
        			System.out.println("\t\tInflige des degats"+blessure);
        			((Personnage) o).setNiveauVie(((Personnage) o).getNiveauVie()-blessure);
        			if(((Personnage) o).getNiveauVie()<15 && ((Personnage) o).getNiveauVie()>0) {
        				// le personnage meurt
        				
        			}
        			if(((Personnage) o).getNiveauVie()<0) {
        				// le personnage meurt
        				System.out.println("\t\t\tle personnage est meurt !!!!!");
        				int exper= (((Personnage) o).getExperiance()/exp);
        				((Personnage) o).mourir();
        				return exper;
        			}
        			System.out.println("\t\tFin Inflige des degats ......");
        		}
        		System.out.println("\tFin détermination de blessure ..........");
        	}
        	System.out.println("Fin attaque ................");
        }
		return 0;
    }
    public String toString() {
        return TO_STRING[this.getNiveau()];
    }
}

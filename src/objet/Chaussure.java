package objet;


public class Chaussure extends Protege {

	public static String[] NOM_CHAUSSURE={"Sandale(I)","Botte Chevalier(II)","Chaussure Viking(III)"};
	public static String[] TO_STRING={"c","c","c"};
	public static int[] SOLIDITE={2,3,4};
	public static int[] ENCOMBREMENT={1,2,3};
	
    public Chaussure(String nom) {
        super(nom);
    }
    public Chaussure(int niveau) {
        this(NOM_CHAUSSURE[niveau]);
        this.setNiveau(niveau);
        this.setSolidite(SOLIDITE[niveau]);
        this.setEncombrement(ENCOMBREMENT[niveau]);
    }
    public static Chaussure creerChaussure() {
    	int n=Outil.random.nextInt(3);
    	return new Chaussure(n);
    }

    public String toString() {
        return TO_STRING[this.getNiveau()];
    }
}

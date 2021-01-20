package objet;

public class Casque extends Protege {

	public static String[] NOM_CHAUSSURE={"Bonnet(I)","Casque Soldat(II)","Casque Rangvaldur(III)"};
	public static String[] TO_STRING={"k","k","k"};
	public static int[] SOLIDITE={3,4,5};
	public static int[] ENCOMBREMENT={2,3,4};
	
    public Casque(String nom) {
        super(nom);
    }
    public Casque(int niveau) {
        this(NOM_CHAUSSURE[niveau]);
        this.setNiveau(niveau);
        this.setSolidite(SOLIDITE[niveau]);
        this.setEncombrement(ENCOMBREMENT[niveau]);
    }
    public static Casque creerCasque() {
    	int n=Outil.random.nextInt(3);
    	return new Casque(n);
    }

    public String toString() {
        return TO_STRING[this.getNiveau()];
    }
}
package objet;

public class Protege extends Outil {
    private int solidite;
    private int encombrement;
    
    public Protege(String nom) {
    	super(nom);
    }

	public int getSolidite() {
		return solidite;
	}

	public void setSolidite(int solidite) {
		this.solidite = solidite;
	}

	public int getEncombrement() {
		return encombrement;
	}

	public void setEncombrement(int encombrement) {
		this.encombrement = encombrement;
	}

}

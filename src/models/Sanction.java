package models;

public class Sanction {
    public final static double amende = 2000;
    private int nbrJoursRetards;
    private int idContrat;
    private int idSanction;
    //Constructors
    public Sanction(int nbrJoursRetards, int idContrat,int idSanction) {
        this.nbrJoursRetards = nbrJoursRetards;
        this.idContrat = idContrat;
        this.idSanction=idSanction;
    }
    //Getters and Setters

    public int getIdSanction() {
        return idSanction;
    }

    public void setIdSanction(int idSanction) {
        this.idSanction = idSanction;
    }

    public static double getAmende() {
        return amende;
    }

    public int getNbrJoursRetards() {
        return nbrJoursRetards;
    }

    public void setNbrJoursRetards(int nbrJoursRetards) {
        this.nbrJoursRetards = nbrJoursRetards;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }
}

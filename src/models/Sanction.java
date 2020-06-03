package models;

public class Sanction {
    public final static double amende = 2000;
    private int nbrJoursRetard;
    private int idContrat;
    private int idSanction;
    //Constructors
    public Sanction(int nbrJoursRetard, int idContrat,int idSanction) {
        this.nbrJoursRetard = nbrJoursRetard;
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

    public int getNbrJoursRetard() {
        return nbrJoursRetard;
    }

    public void setNbrJoursRetards(int nbrJoursRetards) {
        this.nbrJoursRetard = nbrJoursRetards;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }
}

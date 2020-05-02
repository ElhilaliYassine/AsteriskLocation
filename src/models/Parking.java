package models;

import java.util.*;

public class Parking {
    private int NParking;
    private int capacité;
    private String rue;
    private String arrondissement;
    private int nbrPlacesOccupées;
    //Constructors
    public Parking(int NParking, int capacité, String rue, String arrondissement,int nbrPlacesOccupées) {
        this.NParking = NParking;
        this.capacité = capacité;
        this.rue = rue;
        this.arrondissement = arrondissement;
        this.nbrPlacesOccupées = nbrPlacesOccupées;
    }
    //Getters and setters

    public int getNbrPlacesOccupées() {
        return nbrPlacesOccupées;
    }

    public void setNbrPlacesOccupées(int nbrPlacesOccupées) {
        this.nbrPlacesOccupées = nbrPlacesOccupées;
    }

    public int getNParking() {
        return NParking;
    }

    public void setNParking(int NParking) {
        this.NParking = NParking;
    }

    public int getCapacité() {
        return capacité;
    }

    public void setCapacité(int capacité) {
        this.capacité = capacité;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public int getPlacesVides()
    {
        return getCapacité()-getNbrPlacesOccupées();
    }
}

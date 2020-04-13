package models;

import java.util.*;

public class Parking {
    private int NParking;
    private int capacité;
    private String rue;
    private String arrondissement;
    private Set<Véhicule> listeVéhicules=new HashSet<>();
    //Constructors
    public Parking(int NParking, int capacité, String rue, String arrondissement, Set<Véhicule> listeVéhicules) {
        this.NParking = NParking;
        this.capacité = capacité;
        this.rue = rue;
        this.arrondissement = arrondissement;
        this.listeVéhicules = listeVéhicules;
    }
    //Getters and setters
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

    public Set<Véhicule> getListeVéhicules() {
        return listeVéhicules;
    }

    public void setListeVéhicules(Set<Véhicule> listeVéhicules) {
        this.listeVéhicules = listeVéhicules;
    }
}

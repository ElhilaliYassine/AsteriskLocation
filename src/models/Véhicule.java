package models;

import java.time.LocalDate;

public class Véhicule {
    private int NImmatriculation;
    private String marque;
    private String type;
    private String carburant;
    private double compteurKm;
    private LocalDate dateMiseEnCirculation;
    //Constructors
    public Véhicule(int NImmatriculation, String marque, String type, String carburant, double compteurKm, LocalDate dateMiseEnCirculation) {
        this.NImmatriculation = NImmatriculation;
        this.marque = marque;
        this.type = type;
        this.carburant = carburant;
        this.compteurKm = compteurKm;
        this.dateMiseEnCirculation = dateMiseEnCirculation;
    }
    //Getters and setters
    public int getNImmatriculation() {
        return NImmatriculation;
    }

    public void setNImmatriculation(int NImmatriculation) {
        this.NImmatriculation = NImmatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public double getCompteurKm() {
        return compteurKm;
    }

    public void setCompteurKm(double compteurKm) {
        this.compteurKm = compteurKm;
    }

    public LocalDate getDateMiseEnCirculation() {
        return dateMiseEnCirculation;
    }

    public void setDateMiseEnCirculation(LocalDate dateMiseEnCirculation) {
        this.dateMiseEnCirculation = dateMiseEnCirculation;
    }
}

package models;

import java.time.LocalDate;

public class Véhicule {
    private int NImmatriculation;
    private String marque;
    private String type;
    private String carburant;
    private double compteurKm;
    private LocalDate dateMiseEnCirculation;
    private int idParking;
    private boolean disponibilite;
    private double prix;
    //Constructors
    public Véhicule(int NImmatriculation, String marque, String type, String carburant, double compteurKm, LocalDate dateMiseEnCirculation, int idParking, boolean disponibilite, double prix) {
        this.NImmatriculation = NImmatriculation;
        this.marque = marque;
        this.type = type;
        this.carburant = carburant;
        this.compteurKm = compteurKm;
        this.dateMiseEnCirculation = dateMiseEnCirculation;
        this.idParking=idParking;
        this.disponibilite = disponibilite;
        this.prix=prix;
    }
    //Getters and setters
    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }

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

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}

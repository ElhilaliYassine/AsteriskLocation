package models;

import java.time.LocalDate;

public class
Réservation {
    private int codeRéservation;
    private LocalDate dateReservation;
    private LocalDate dateDépart;
    private LocalDate dateRetour;
    private int idClient;
    private int idVehicule;
    private String etatReservation;

    //constructors
    public Réservation(int codeRéservation, LocalDate dateReservation, LocalDate dateDépart, LocalDate dateRetour, int idClient, int idVehicule, String etatReservation) {
        this.codeRéservation = codeRéservation;
        this.dateReservation = dateReservation;
        this.dateDépart = dateDépart;
        this.dateRetour = dateRetour;
        this.idClient=idClient;
        this.idVehicule = idVehicule;
        this.etatReservation = etatReservation;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    //Getters and setters
    public int getCodeRéservation() {
        return codeRéservation;
    }

    public void setCodeRéservation(int codeRéservation) {
        this.codeRéservation = codeRéservation;
    }

    public LocalDate getDateDépart() {
        return dateDépart;
    }

    public void setDateDépart(LocalDate dateDépart) {
        this.dateDépart = dateDépart;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getEtatReservation() {
        return etatReservation;
    }

    public void setEtatReservation(String etatReservation) {
        this.etatReservation = etatReservation;
    }
}
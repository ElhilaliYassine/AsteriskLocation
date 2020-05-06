package models;

import java.time.LocalDate;

public class
Réservation {
    private int codeRéservation;
    private LocalDate dateDépart;
    private LocalDate dateRetour;
    private int idClient;
    private int idVehicule;

    //constructors
    public Réservation(int codeRéservation, LocalDate dateDépart, LocalDate dateRetour, int idClient, int idVehicule) {
        this.codeRéservation = codeRéservation;
        this.dateDépart = dateDépart;
        this.dateRetour = dateRetour;
        this.idClient=idClient;
        this.idVehicule = idVehicule;
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
}
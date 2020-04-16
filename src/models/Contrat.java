package models;

import java.time.LocalDate;

public class Contrat {
    private int NContrat;
    private LocalDate dateContrat;
    private LocalDate dateEchéance;
    private int idReservation;

    //Constructors
    public Contrat(int NContrat, LocalDate dateContrat, LocalDate dateEchéance,int idReservation) {
        this.NContrat = NContrat;
        this.dateContrat = dateContrat;
        this.dateEchéance = dateEchéance;
        this.idReservation=idReservation;
    }

    //Getters and setters

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getNContrat() {
        return NContrat;
    }

    public void setNContrat(int NContrat) {
        this.NContrat = NContrat;
    }

    public LocalDate getDateContrat() {
        return dateContrat;
    }

    public void setDateContrat(LocalDate dateContrat) {
        this.dateContrat = dateContrat;
    }

    public LocalDate getDateEchéance() {
        return dateEchéance;
    }

    public void setDateEchéance(LocalDate dateEchéance) {
        this.dateEchéance = dateEchéance;
    }
}

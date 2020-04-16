package models;

import java.time.LocalDate;

public class Facture {
    private int NFacture;
    private LocalDate dateFacture;
    private double MontantAPayer;
    private int idContrat;
    //Constructors
    public Facture(int NFacture, LocalDate dateFacture, double montantAPayer,int idContrat) {
        this.NFacture = NFacture;
        this.dateFacture = dateFacture;
        this.MontantAPayer = montantAPayer;
        this.idContrat=idContrat;
    }
    //Getters and setters

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public int getNFacture() {
        return NFacture;
    }

    public void setNFacture(int NFacture) {
        this.NFacture = NFacture;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getMontantAPayer() {
        return MontantAPayer;
    }

    public void setMontantAPayer(double montantAPayer) {
        MontantAPayer = montantAPayer;
    }
}

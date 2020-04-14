package models;

public class Utilisateur {
    private int codeUtilisateur;
    private String nomComplet;
    private String adresse;
    private int numGsm;
    private String uriImage;
    private String password;
    private String email;
    //Constructors
    public Utilisateur(int codeUtilisateur, String nomComplet, String adresse, int numGsm, String uriImage,String password,String email) {
        this.codeUtilisateur = codeUtilisateur;
        this.nomComplet = nomComplet;
        this.adresse = adresse;
        this.numGsm = numGsm;
        this.uriImage = uriImage;
        this.password=password;
        this.email=email;
    }

    //Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodeUtilisateur() {
        return codeUtilisateur;
    }

    public void setCodeUtilisateur(int codeUtilisateur) {
        this.codeUtilisateur = codeUtilisateur;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumGsm() {
        return numGsm;
    }

    public void setNumGsm(int numGsm) {
        this.numGsm = numGsm;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}

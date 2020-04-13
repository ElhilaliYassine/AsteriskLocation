package models;

public class Client {
    private int codeClient;
    private String nomComplet;
    private String adresse;
    private int numGsm;
    private String uriImage;

    //Constructors

    public Client(int codeClient, String nomComplet, String adresse, int numGsm, String uriImage) {
        this.codeClient = codeClient;
        this.nomComplet = nomComplet;
        this.adresse = adresse;
        this.numGsm = numGsm;
        this.uriImage = uriImage;
    }

    //Getters
    public int getCodeClient() {
        return codeClient;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getNumGsm() {
        return numGsm;
    }

    public String getUriImage() {
        return uriImage;
    }

    //Setters
    public void setCodeClient(int codeClient) {
        this.codeClient = codeClient;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNumGsm(int numGsm) {
        this.numGsm = numGsm;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}

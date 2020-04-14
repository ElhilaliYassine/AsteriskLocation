package models;

public class Administrateur extends Utilisateur {
    private Administrateur(int codeUtilisateur, String nomComplet, String adresse, int numGsm, String uriImage, String password, String email) {
        super(codeUtilisateur, nomComplet, adresse, numGsm, uriImage, password, email);
    }
    private static Administrateur admin = new Administrateur(1,"John Doe","123 Main St Anytown, USA",+41940-766-2155,"johndoe.jpg","0000","johndoe@asterisk.com");

    public static Administrateur getAdmin() {
        return admin;
    }

}

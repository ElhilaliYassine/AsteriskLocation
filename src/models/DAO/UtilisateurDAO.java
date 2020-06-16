package models.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import models.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO extends DAO<Utilisateur>{



    public UtilisateurDAO(Connection conn) throws SQLException {
        super(conn);
    }


    @Override
    public boolean create(Utilisateur obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO utilisateur(nomComplet,adresse,numGsm,password,email) VALUES(?,?,?,?,?)");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setString(4,obj.getPassword());
            preparedStmt.setString(5,obj.getEmail());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Utilisateur obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM utilisateur WHERE codeUtilisateur=?");
            preparedStmt.setInt(1,obj.getCodeUtilisateur());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Utilisateur obj, int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE utilisateur SET nomComplet=?,adresse=?,numGsm=?,password=? WHERE codeUtilisateur=?");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setString(4,obj.getPassword());
            preparedStmt.setInt(5,id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }
    public boolean update(Client obj, String fullName) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE utilisateur SET nomComplet=?,adresse=?,numGsm=? WHERE nomComplet=?");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setString(4,fullName);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public Utilisateur find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM utilisateur WHERE codeUtilisateur=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            return new Utilisateur(id,resultSet.getString("nomComplet"),resultSet.getString("adresse"),resultSet.getInt("numGsm"),resultSet.getString("password"),resultSet.getString("email"));
        }
        catch(SQLException e)
        {
            return new Utilisateur(id,"","",0,"","");
        }
    }
    public Utilisateur find(String fullName) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM utilisateur WHERE nomComplet=?");
            preparedStmt.setString(1,fullName);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()) {
                return new Utilisateur(resultSet.getInt("codeUtilisateur"), fullName, resultSet.getString("adresse"), resultSet.getInt("numGsm"), resultSet.getString("password"), resultSet.getString("email"));
            }
        }
        catch(SQLException e)
        {
            return new Utilisateur(0,fullName,"",0,"","");
        }
        return new Utilisateur(0,fullName,"",0,"","");
    }
    //In javafx we use ObservableList to show Data
    public ObservableList<Utilisateur> list()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM utilisateur ORDER BY nomComplet");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Utilisateur> listUtilisateurs = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                listUtilisateurs.add(new Utilisateur(resultSet.getInt("codeUtilisateur"),resultSet.getString("nomComplet"),resultSet.getString("adresse"),resultSet.getInt("numGsm"),resultSet.getString("password"),resultSet.getString("email")));
            }
            return listUtilisateurs;
        }
        catch(SQLException e)
        {
            return null;
        }
    }


}

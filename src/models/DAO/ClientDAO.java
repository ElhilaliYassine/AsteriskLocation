package models.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;

public class ClientDAO extends DAO<Client>{

    public ClientDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Client obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO client(nomComplet,adresse,numGsm) VALUES(?,?,?)");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }
    @Override
    public boolean delete(Client obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM client WHERE codeClient=?");
            preparedStmt.setInt(1,obj.getCodeClient());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Client obj,int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE client SET nomComplet=?,adresse=?,numGsm=? WHERE codeClient=?");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setInt(4,id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }
    public boolean update(Client obj,String fullName) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE client SET nomComplet=?,adresse=?,numGsm=? WHERE nomComplet=?");
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
    public Client find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM client WHERE codeClient=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            resultSet.next();
            return new Client(id,resultSet.getString("nomComplet"),resultSet.getString("adresse"),resultSet.getInt("numGsm"));
        }
        catch(SQLException e)
        {
            return new Client(id,"","",0);
        }
    }
    public ObservableList<Client> list()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM client");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Client> listClients = FXCollections.observableArrayList();;
            while(resultSet.next())
            {
                listClients.add(new Client(resultSet.getInt("codeClient"),resultSet.getString("nomComplet"),resultSet.getString("adresse"),resultSet.getInt("numGsm")));
            }
            Collections.sort(listClients, Comparator.comparing(Client::getNomComplet));
            return listClients;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public Client find(String fullName) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM client WHERE nomComplet=?");
            preparedStmt.setString(1,fullName);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()) {
                return new Client(resultSet.getInt("codeClient"), fullName, resultSet.getString("adresse"), resultSet.getInt("numGsm"));
            }
        }
        catch(SQLException e)
        {
            return new Client(0,fullName,"",0);
        }
        return new Client(0,fullName,"",0);
    }
    public ObservableList<String> select(){
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT nomComplet FROM client");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<String> listClient = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                listClient.add(resultSet.getString("nomComplet"));
            }
            return listClient;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public int nombreClient()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT COUNT(*) FROM client");
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next())
            {
                return resultSet.getInt("COUNT(*)");
            }
            return 0;
        }
        catch(SQLException e)
        {
            return 0;
        }
    }
}

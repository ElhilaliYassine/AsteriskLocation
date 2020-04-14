package models.DAO;

import models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientDAO extends DAO<Client>{

    public ClientDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Client obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO client(nomComplet,adresse,numGsm,uriImage) VALUES(?,?,?,?)");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setString(4,obj.getUriImage());
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
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE client SET nomComplet=?,adresse=?,numGsm=?,uriImage=? WHERE codeClient=?");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setString(4,obj.getUriImage());
            preparedStmt.setInt(5,id);
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
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE client SET nomComplet=?,adresse=?,numGsm=?,uriImage=? WHERE nomComplet=?");
            preparedStmt.setString(1,obj.getNomComplet());
            preparedStmt.setString(2,obj.getAdresse());
            preparedStmt.setInt(3,obj.getNumGsm());
            preparedStmt.setString(4,obj.getUriImage());
            preparedStmt.setString(5,fullName);
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
            return new Client(id,resultSet.getString("nomComplet"),resultSet.getString("adresse"),resultSet.getInt("numGsm"),resultSet.getString("uriImage"));
        }
        catch(SQLException e)
        {
            return new Client(id,"","",0,"");
        }
    }
    public List<Client> list()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM client");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Client> listClients = new ArrayList<>();
            while(resultSet.next())
            {
                listClients.add(new Client(resultSet.getInt("codeClient"),resultSet.getString("nomComplet"),resultSet.getString("adresse"),resultSet.getInt("numGsm"),resultSet.getString("uriImage")));
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
            return new Client(resultSet.getInt("codeClient"),fullName,resultSet.getString("adresse"),resultSet.getInt("numGsm"),resultSet.getString("uriImage"));
        }
        catch(SQLException e)
        {
            return new Client(0,fullName,"",0,"");
        }
    }
}

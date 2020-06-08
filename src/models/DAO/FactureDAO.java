package models.DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contrat;
import models.Facture;
import models.Réservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FactureDAO extends DAO<Facture>{
    public FactureDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Facture obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO facture(dateFacture,MontantAPayer,idContrat) VALUES(?,?,?)");
            preparedStmt.setObject(1,obj.getDateFacture());
            preparedStmt.setObject(2,obj.getMontantAPayer());
            preparedStmt.setInt(3,obj.getIdContrat());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Facture obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM facture WHERE NFacture=?");
            preparedStmt.setInt(1,obj.getNFacture());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Facture obj, int id) {
        try {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE facture SET dateFacture=?,MontantAPayer=?,idContrat=? WHERE NFacture=?");
            preparedStmt.setObject(1,obj.getDateFacture());
            preparedStmt.setObject(2,obj.getMontantAPayer());
            preparedStmt.setInt(3,obj.getIdContrat());
            preparedStmt.setInt(4, id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public Facture find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM facture WHERE NFacture=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next())
            {
                Date _dateFacture = resultSet.getDate("dateFacture");
                LocalDate dateFacture = _dateFacture.toLocalDate();
                return new Facture(id,dateFacture,resultSet.getDouble("MontantAPayer"),resultSet.getInt("idContrat"));
            }
        }
        catch(SQLException e)
        {
            return new Facture(id,LocalDate.now(),0.0,0);
        }
        return new Facture(id,LocalDate.now(),0.0,0);
    }

    @Override
    public ObservableList<Facture> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM facture");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Facture> listFactures = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date _dateFacture = resultSet.getDate("dateFacture");
                LocalDate dateFacture = _dateFacture.toLocalDate();
                listFactures.add(new Facture(resultSet.getInt("NFacture"),dateFacture,resultSet.getDouble("MontantAPayer"),resultSet.getInt("idContrat")));
            }
            return listFactures;
        }
        catch(SQLException e)
        {
            return null;
        }
    }

    public ObservableList<Integer> listStrings()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM contrat");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Integer> list = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                list.add(resultSet.getInt("NContrat"));
            }
            return list;
        }
        catch(SQLException e)
        {
            return null;
        }
    }

    public boolean containsContratId(int idContrat)
    {
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM facture");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                if (resultSet.getInt("idContrat") == idContrat) return true;
            }
            return false;
        }catch(SQLException e)
        {
            return false;
        }
    }
    public Facture findByContrat(int idContrat) {
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM facture WHERE idContrat =?");
            preparedStatement.setInt(1,idContrat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Date _dateFacture = resultSet.getDate("dateFacture");
                LocalDate dateFacture = _dateFacture.toLocalDate();
                Facture facture = new Facture(resultSet.getInt("NFacture"),dateFacture,resultSet.getDouble("MontantAPayer"),idContrat);
                return facture;
            }
        }
        catch(SQLException e)
        {
            return new Facture(0,LocalDate.now(),0.0,0);
        }
        return new Facture(0,LocalDate.now(),0.0,0);
    }
    public int totalFacture()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT SUM(MontantAPayer) FROM facture");
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next())
            {
                return resultSet.getInt("SUM(MontantAPayer)");
            }
            return 0;
        }
        catch(SQLException e)
        {
            return 0;
        }
    }
    public int nombreFacture()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT COUNT(*) FROM facture");
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
    public ObservableList<Facture> listLastFacture() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM facture ORDER BY NFacture DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Facture> listFactures = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date _dateFacture = resultSet.getDate("dateFacture");
                LocalDate dateFacture = _dateFacture.toLocalDate();
                listFactures.add(new Facture(resultSet.getInt("NFacture"),dateFacture,resultSet.getDouble("MontantAPayer"),resultSet.getInt("idContrat")));
            }
            ObservableList<Facture> lastFacture = FXCollections.observableArrayList();
            lastFacture.add(listFactures.get(0));
            return lastFacture;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}
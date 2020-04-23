package models.DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contrat;
import models.Réservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContratDAO extends DAO<Contrat>{
    public ContratDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Contrat obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO Contrat(dateContrat,dateEcheance,idReservation) VALUES(?,?,?)");
            preparedStmt.setObject(1,obj.getDateContrat());
            preparedStmt.setObject(2,obj.getDateEchéance());
            preparedStmt.setInt(3,obj.getIdReservation());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Contrat obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE * FROM contrat WHERE NContrat=?");
            preparedStmt.setInt(1,obj.getNContrat());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Contrat obj, int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE contrat SET dateContrat=?,dateEcheance=?,idReservation=? WHERE Ncontrat=?");
            preparedStmt.setObject(1,obj.getDateContrat());
            preparedStmt.setObject(2,obj.getDateEchéance());
            preparedStmt.setInt(3,obj.getIdReservation());
            preparedStmt.setInt(4,id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public Contrat find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM contrat WHERE NContrat=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            LocalDate dateContrat = (LocalDate) resultSet.getObject("dateContrat");
            LocalDate dateEcheance = (LocalDate) resultSet.getObject("dateEcheance");
            return new Contrat(id,dateContrat,dateEcheance,resultSet.getInt("idReservation"));
        }
        catch(SQLException e)
        {
            return new Contrat(id,null,null,0);
        }
    }

    @Override
    public ObservableList<Contrat> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM contrat DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Contrat> listContrats = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                LocalDate dateContrat = (LocalDate) resultSet.getObject("dateContrat");
                LocalDate dateEcheance = (LocalDate) resultSet.getObject("dateEcheance");
                listContrats.add(new Contrat(resultSet.getInt("NContrat"),dateContrat,dateEcheance,resultSet.getInt("idReservation")));
            }
            Collections.sort(listContrats, Comparator.comparing(Contrat::getDateContrat).reversed());
            return listContrats;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}

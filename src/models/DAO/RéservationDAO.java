package models.DAO;

import models.Client;
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

public class RéservationDAO extends DAO<Réservation>{

    public RéservationDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Réservation obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO reservation(dateDépart,dateRetour,idClient) VALUES(?,?,?)");
            preparedStmt.setObject(1,obj.getDateDépart());
            preparedStmt.setObject(2,obj.getDateRetour());
            preparedStmt.setObject(3,obj.getIdClient());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Réservation obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM reservation WHERE codeReservation=?");
            preparedStmt.setInt(1,obj.getCodeRéservation());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Réservation obj, int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE reservation SET dateDepart=?,dateRetour=?,idClient=? WHERE codeReservation=?");
            preparedStmt.setObject(1,obj.getDateDépart());
            preparedStmt.setObject(2,obj.getDateRetour());
            preparedStmt.setObject(3,obj.getIdClient());
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
    public Réservation find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation WHERE codeReservation=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            LocalDate dateDepart = (LocalDate) resultSet.getObject("dateDepart");
            LocalDate dateRetour = (LocalDate) resultSet.getObject("dateRetour");
            return new Réservation(id,dateDepart,dateRetour,resultSet.getInt("idClient"));
        }
        catch(SQLException e)
        {
            return new Réservation(id,null,null,0);
        }
    }

    @Override
    public List<Réservation> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Réservation> listRéservations = new ArrayList<>();
            while(resultSet.next())
            {
                LocalDate dateDepart = (LocalDate) resultSet.getObject("dateDepart");
                LocalDate dateRetour = (LocalDate) resultSet.getObject("dateRetour");
                listRéservations.add(new Réservation(resultSet.getInt("codeReservation"),dateDepart,dateRetour,resultSet.getInt("idClient")));
            }
            Collections.sort(listRéservations, Comparator.comparing(Réservation::getDateDépart).reversed());
            return listRéservations;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}

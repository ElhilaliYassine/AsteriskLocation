package models.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contrat;

import java.sql.*;
import java.time.LocalDate;

public class ContratDAO extends DAO<Contrat>{
    public ContratDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Contrat obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO contrat(dateContrat,dateEchéance,idReservation) VALUES(?,?,?)");
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
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM contrat WHERE NContrat=?");
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
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE contrat SET dateContrat=?,dateEchéance=?,idReservation=? WHERE Ncontrat=?");
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
            resultSet.next();
            Date _dateContrat = resultSet.getDate("dateContrat");
            Date _dateEcheance = resultSet.getDate("dateEchéance");
            LocalDate dateContrat = _dateContrat.toLocalDate();
            LocalDate dateEcheance = _dateEcheance.toLocalDate();
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
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM contrat");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Contrat> listContrats = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date _dateContrat = resultSet.getDate("dateContrat");
                Date _dateEcheance = resultSet.getDate("dateEchéance");
                LocalDate dateContrat = _dateContrat.toLocalDate();
                LocalDate dateEcheance = _dateEcheance.toLocalDate();
                listContrats.add(new Contrat(resultSet.getInt("NContrat"),dateContrat,dateEcheance,resultSet.getInt("idReservation")));
            }
            return listContrats;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public boolean containsReservationId(int idReserv)
    {
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM contrat");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                if (resultSet.getInt("idReservation") == idReserv) return true;
            }
            return false;
        }catch(SQLException e)
        {
            return false;
        }
    }

    public Contrat findByVehicule(int idVehicule) {
        try
        {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM contrat,reservation,vehicule WHERE contrat.idReservation = reservation.codeReservation AND reservation.idVehicule = vehicule.NImmatriculation AND vehicule.NImmatriculation = ? ORDER BY contrat.NContrat DESC");
            preparedStatement.setInt(1,idVehicule);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Date _dateContrat = resultSet.getDate("dateContrat");
                LocalDate dateContrat = _dateContrat.toLocalDate();
                Date _dateEchéance = resultSet.getDate("dateEchéance");
                LocalDate dateEchéance = _dateEchéance.toLocalDate();
                Contrat contrat = new Contrat(resultSet.getInt("NContrat"),dateContrat,dateEchéance,resultSet.getInt("idReservation"));
                return contrat;
            }
        }
        catch(SQLException e)
        {
            return new Contrat(0,LocalDate.now(),LocalDate.now(),0);
        }
        return new Contrat(0,LocalDate.now(),LocalDate.now(),0);
    }
}

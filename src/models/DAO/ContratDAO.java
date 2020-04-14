package models.DAO;

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
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO Contrat(dateContrat,dateEcheance) VALUES(?,?)");
            preparedStmt.setObject(1,obj.getDateContrat());
            preparedStmt.setObject(2,obj.getDateEchéance());
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
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE contrat SET dateContrat=?,dateEcheance=? WHERE Ncontrat=?");
            preparedStmt.setObject(1,obj.getDateContrat());
            preparedStmt.setObject(2,obj.getDateEchéance());
            preparedStmt.setInt(3,id);
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
            return new Contrat(id,dateContrat,dateEcheance);
        }
        catch(SQLException e)
        {
            return new Contrat(id,null,null);
        }
    }

    @Override
    public List<Contrat> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM contrat DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Contrat> listContrats = new ArrayList<>();
            while(resultSet.next())
            {
                LocalDate dateContrat = (LocalDate) resultSet.getObject("dateContrat");
                LocalDate dateEcheance = (LocalDate) resultSet.getObject("dateEcheance");
                listContrats.add(new Contrat(resultSet.getInt("NContrat"),dateContrat,dateEcheance));
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

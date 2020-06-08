package models.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Sanction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SanctionDAO extends DAO<Sanction>{

    public SanctionDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Sanction obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO sanction(nbrJoursRetard,idContrat,montantAPayer) VALUES(?,?,?)");
            preparedStmt.setInt(1,obj.getNbrJoursRetard());
            preparedStmt.setInt(2,obj.getIdContrat());
            preparedStmt.setInt(3,obj.getMontantAPayer());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Sanction obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM sanction WHERE idSanction=?");
            preparedStmt.setInt(1,obj.getIdSanction());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Sanction obj, int id) {
        try {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE sanction SET nbrJoursRetard=?,idContrat=?,idSanction=? WHERE NParking=?");
            preparedStmt.setInt(1,obj.getNbrJoursRetard());
            preparedStmt.setInt(2,obj.getIdContrat());
            preparedStmt.setInt(3,obj.getIdSanction());
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
    public Sanction find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM sanction WHERE idSanction=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            return new Sanction(resultSet.getInt("nbrJoursRetard"),resultSet.getInt("idContrat"),id, resultSet.getInt("montantAPayer"));
        }
        catch(SQLException e)
        {
            return new Sanction(0,0,id, 0);
        }
    }

    @Override
    public ObservableList<Sanction> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM sanction");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Sanction> listSanctions = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                listSanctions.add(new Sanction(resultSet.getInt("nbrJoursRetard"),resultSet.getInt("idContrat"),resultSet.getInt("idSanction"), resultSet.getInt("montantAPayer")));
            }
            return listSanctions;
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
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM sanction");
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

    public int totalSanction()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT SUM(montantAPayer) FROM sanction");
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next())
            {
                return resultSet.getInt("SUM(montantAPayer)");
            }
            return 0;
        }
        catch(SQLException e)
        {
            return 0;
        }
    }
    public int nombreSanction()
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT COUNT(*) FROM sanction");
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

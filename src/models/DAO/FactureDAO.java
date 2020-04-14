package models.DAO;

import models.Facture;
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

public class FactureDAO extends DAO<Facture>{
    public FactureDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Facture obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO facture(dateFacture,MontantAPayer) VALUES(?,?)");
            preparedStmt.setObject(1,obj.getDateFacture());
            preparedStmt.setObject(2,obj.getMontantAPayer());
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
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE facture SET dateFacture=?,MontantAPayer=? WHERE NFacture=?");
            preparedStmt.setObject(1,obj.getDateFacture());
            preparedStmt.setObject(2,obj.getMontantAPayer());
            preparedStmt.setInt(3, id);
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
            LocalDate dateFacture = (LocalDate) resultSet.getObject("dateFacture");
            return new Facture(id,dateFacture,resultSet.getDouble("MontantAPayer"));
        }
        catch(SQLException e)
        {
            return new Facture(id,null,0.0);
        }
    }

    @Override
    public List<Facture> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM facture DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Facture> listFactures = new ArrayList<>();
            while(resultSet.next())
            {
                LocalDate dateFacture = (LocalDate) resultSet.getObject("dateFacture");
                listFactures.add(new Facture(resultSet.getInt("NFacture"),dateFacture,resultSet.getDouble("MontantAPayer")));
            }
            Collections.sort(listFactures, Comparator.comparing(Facture::getDateFacture).reversed());
            return listFactures;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}

package models.DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE * FROM facture WHERE NFacture=?");
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
            LocalDate dateFacture = (LocalDate) resultSet.getObject("dateFacture");
            return new Facture(id,dateFacture,resultSet.getDouble("MontantAPayer"),resultSet.getInt("idContrat"));
        }
        catch(SQLException e)
        {
            return new Facture(id,null,0.0,0);
        }
    }

    @Override
    public ObservableList<Facture> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM facture DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Facture> listFactures = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                LocalDate dateFacture = (LocalDate) resultSet.getObject("dateFacture");
                listFactures.add(new Facture(resultSet.getInt("NFacture"),dateFacture,resultSet.getDouble("MontantAPayer"),resultSet.getInt("idContrat")));
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

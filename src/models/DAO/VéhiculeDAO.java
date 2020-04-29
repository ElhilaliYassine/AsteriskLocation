package models.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Véhicule;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

public class VéhiculeDAO extends DAO<Véhicule>{

    public VéhiculeDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Véhicule obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO vehicule(marque,type,carburant,compteurKm,dateMiseEnCirculation,idParking) VALUES(?,?,?,?,?,?)");
            preparedStmt.setString(1,obj.getMarque());
            preparedStmt.setString(2,obj.getType());
            preparedStmt.setString(3,obj.getCarburant());
            preparedStmt.setDouble(4,obj.getCompteurKm());
            preparedStmt.setObject(5,obj.getDateMiseEnCirculation());
            preparedStmt.setInt(6,obj.getIdParking());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(Véhicule obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE * FROM vehicule WHERE NImmatriculation=?");
            preparedStmt.setInt(1,obj.getNImmatriculation());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Véhicule obj, int id) {
        try {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE vehicule SET marque=?,type=?,carburant=?,compteurKm=?,dateMiseEnCirculation=?,idParking=?,disponibilite=? WHERE NImmatriculation=?");
            preparedStmt.setString(1,obj.getMarque());
            preparedStmt.setString(2,obj.getType());
            preparedStmt.setString(3,obj.getCarburant());
            preparedStmt.setDouble(4,obj.getCompteurKm());
            preparedStmt.setObject(5,obj.getDateMiseEnCirculation());
            preparedStmt.setInt(6,obj.getIdParking());
            preparedStmt.setBoolean(7,obj.isDisponibilite());
            preparedStmt.setInt(8, id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public Véhicule find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM vehicule WHERE NImmatriculation=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            LocalDate dateMiseEnCirculation = (LocalDate) resultSet.getObject("dateMiseEnCirculation");
            return new Véhicule(id,resultSet.getString("marque"),resultSet.getString("type"),resultSet.getString("carburant"),resultSet.getDouble("compteurKm"),dateMiseEnCirculation,resultSet.getInt("idParking"),resultSet.getBoolean("disponibilite"));
        }
        catch(SQLException e)
        {
            return new Véhicule(id,"","","",0.0,null,0, false);
        }
    }

    @Override
    public ObservableList<Véhicule> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM vehicule");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Véhicule> listVéhicules = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date date = resultSet.getDate("dateMiseEnCirculation");
                LocalDate dateMiseEnCirculation = date.toLocalDate();
                listVéhicules.add(new Véhicule(resultSet.getInt("NImmatriculation"),resultSet.getString("marque"),resultSet.getString("type"),resultSet.getString("carburant"),resultSet.getDouble("compteurKm"),dateMiseEnCirculation,resultSet.getInt("idParking"), resultSet.getBoolean("disponibilite")));
            }
            Collections.sort(listVéhicules, Comparator.comparing(Véhicule::getNImmatriculation).reversed());
            return listVéhicules;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}

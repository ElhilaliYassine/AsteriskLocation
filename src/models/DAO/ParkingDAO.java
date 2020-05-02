package models.DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Parking;
import models.Véhicule;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParkingDAO extends DAO<Parking>{

    public ParkingDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Parking obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO parking(capacite,rue,arrondissement,nbrPlacesOccupees) VALUES(?,?,?,?)");
            preparedStmt.setInt(1,obj.getCapacité());
            preparedStmt.setString(2,obj.getRue());
            preparedStmt.setString(3,obj.getArrondissement());
            preparedStmt.setInt(4,obj.getNbrPlacesOccupées());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Parking obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM parking WHERE NParking=?");
            preparedStmt.setInt(1,obj.getNParking());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Parking obj, int id) {
        try {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE parking SET capacite=?,rue=?,arrondissement=?,nbrPlacesOccupees=? WHERE NParking=?");
            preparedStmt.setInt(1,obj.getCapacité());
            preparedStmt.setString(2,obj.getRue());
            preparedStmt.setString(3,obj.getArrondissement());
            preparedStmt.setInt(4,obj.getNbrPlacesOccupées());
            preparedStmt.setInt(5, id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public Parking find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM parking WHERE NParking=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()){
                return new Parking(id,resultSet.getInt("capacite"),resultSet.getString("rue"),resultSet.getString("arrondissement"),resultSet.getInt("nbrPlacesOccupees"));
            }
        }
        catch(SQLException e)
        {
            return new Parking(id,0,"","",0);
        }
        return new Parking(id,0,"","",0);

    }

    public Parking find(String rue) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM parking WHERE rue=?");
            preparedStmt.setString(1,rue);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()){
                return new Parking(resultSet.getInt("NParking"),resultSet.getInt("capacite"),rue,resultSet.getString("arrondissement"),resultSet.getInt("nbrPlacesOccupees"));
            }
        }
        catch(SQLException e)
        {
            return new Parking(0,0,rue,"",0);
        }
        return new Parking(0,0,rue,"",0);

    }

    @Override
    public ObservableList<Parking> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM parking");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Parking> listParkings = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                listParkings.add(new Parking(resultSet.getInt("NParking"),resultSet.getInt("capacite"),resultSet.getString("rue"),resultSet.getString("arrondissement"),resultSet.getInt("nbrPlacesOccupees")));
            }
            //Collections.sort(listParkings, Comparator.comparing(Parking::getPlacesVides).reversed());
            return listParkings;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    //bessmellah
    public ObservableList<String> select(){
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT rue FROM parking");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<String> listParkings = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                listParkings.add(resultSet.getString("rue"));
            }
            return listParkings;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public ObservableList<Véhicule> vehiculeParking(int id){
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM vehicule WHERE idParking = ?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Véhicule> listParkings = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date date = resultSet.getDate("dateMiseEnCirculation");
                LocalDate dateMiseEnCirculation = date.toLocalDate();
                listParkings.add(new Véhicule(resultSet.getInt("NImmatriculation"),resultSet.getString("marque"),resultSet.getString("type"),resultSet.getString("carburant"),resultSet.getDouble("compteurKm"),dateMiseEnCirculation,id, resultSet.getBoolean("disponibilite")));
            }
            return listParkings;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public int nombreVehicule(int id)
    {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT COUNT(*) FROM vehicule WHERE idParking = ?");
            preparedStmt.setInt(1,id);
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

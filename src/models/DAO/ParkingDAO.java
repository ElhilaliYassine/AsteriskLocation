package models.DAO;

import models.Parking;
import models.Véhicule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO parking(capacite,rue,arronidissement) VALUES(?,?,?)");
            preparedStmt.setInt(1,obj.getCapacité());
            preparedStmt.setString(2,obj.getRue());
            preparedStmt.setString(3,obj.getArrondissement());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
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
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE parking SET capacite=?,rue=?,arronidissement=? WHERE NParking=?");
            preparedStmt.setInt(1,obj.getCapacité());
            preparedStmt.setString(2,obj.getRue());
            preparedStmt.setString(3,obj.getArrondissement());
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
    public Parking find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM vehicule WHERE NImmatriculation=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            return new Parking(id,resultSet.getInt("capacite"),resultSet.getString("rue"),resultSet.getString("arrondissement"),resultSet.getInt("nbrPlacesOccupees"));
        }
        catch(SQLException e)
        {
            return new Parking(id,0,"","",0);
        }
    }

    @Override
    public List<Parking> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM parking DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<Parking> listParkings = new ArrayList<>();
            while(resultSet.next())
            {
                listParkings.add(new Parking(resultSet.getInt("NParking"),resultSet.getInt("capacite"),resultSet.getString("rue"),resultSet.getString("arrondissement"),resultSet.getInt("nbrPlacesOccupees")));
            }
            Collections.sort(listParkings, Comparator.comparing(Parking::getPlacesVides).reversed());
            return listParkings;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}

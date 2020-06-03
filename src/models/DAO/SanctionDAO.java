package models.DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Sanction;
import models.Véhicule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SanctionDAO extends DAO<Sanction>{

    public SanctionDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Sanction obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO sanction(nbrJoursRetard,idContrat) VALUES(?,?)");
            preparedStmt.setInt(1,obj.getNbrJoursRetard());
            preparedStmt.setInt(2,obj.getIdContrat());
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
            return new Sanction(resultSet.getInt("nbrJoursRetard"),resultSet.getInt("idContrat"),id);
        }
        catch(SQLException e)
        {
            return new Sanction(0,0,id);
        }
    }

    @Override
    public ObservableList<Sanction> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM sanction DESC");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Sanction> listSanctions = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                listSanctions.add(new Sanction(resultSet.getInt("nbrJoursRetard"),resultSet.getInt("idContrat"),resultSet.getInt("idSanction")));
            }
            return listSanctions;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}

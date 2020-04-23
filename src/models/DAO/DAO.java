package models.DAO;

import javafx.collections.ObservableList;
import models.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DAO<T> {
    public static Connection connect;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DB_NAME = "asterisklocation";



    //Constructor
    public DAO(Connection conn) {
        this.connect = conn;
    }
    static {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("connection failed !");
        }
    }
    public abstract boolean create(T obj);
    public abstract boolean delete(T obj);
    public abstract boolean update(T obj,int id);
    public abstract T find(int id);
    public abstract ObservableList<T> list();
}
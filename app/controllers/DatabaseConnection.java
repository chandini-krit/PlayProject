package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import play.db.DB;
class DatabaseConnection {
    private static DatabaseConnection dbObj=null;
    private Connection connection;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        if (dbObj == null) {
            dbObj = new DatabaseConnection();
        }
        System.out.println("Connected to the database.");
        return dbObj;
    }

    public Connection getConnection() throws SQLException,ClassNotFoundException{
        connection=DB.getConnection();
        return connection;
    }

}

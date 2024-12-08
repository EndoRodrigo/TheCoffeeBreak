package org.endorodrigo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String url;
    private final String user;
    private final String pss;
    private final String driver;
    Connection conn = null;

    public DatabaseConnection(Config config){
        this.url = config.get("db.url");
        this.user = config.get("db.username");
        this.pss = config.get("db.password");
        this.driver = config.get("db.driverClassName");
    }


    public Connection getIntances() {
        try {
            Class.forName(driver);
            if (conn == null){
                conn = DriverManager.getConnection(url,user,pss);
                System.out.println("Conexion establecida.......");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al establece la conexion...." + e.getMessage());
        }
        return conn;
    }
}

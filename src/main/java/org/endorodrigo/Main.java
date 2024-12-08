package org.endorodrigo;

import org.endorodrigo.config.Config;
import org.endorodrigo.config.DatabaseConnection;
import org.endorodrigo.repository.CoffeesTable;
import org.endorodrigo.repository.SuppliersTable;
import org.mariadb.jdbc.Connection;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {

            Config config = new Config("src/main/resources/db.properties");

            // Crear la conexión a la base de datos
            DatabaseConnection conn = new DatabaseConnection(config);

            CoffeesTable coffeesTable = new CoffeesTable();
            //coffeesTable.createTable(conn.getIntances());
            //coffeesTable.populateTable(conn.getIntances());
            //coffeesTable.updateCoffeeSales(conn.getIntances());

            System.out.println(" ========================================== ");

            //Creacion de la estructura de supplier
            SuppliersTable suppliersTable = new SuppliersTable();
            //suppliersTable.createTable(conn.getIntances());
            //suppliersTable.dropTable(conn.getIntances());
            //suppliersTable.populateTable(conn.getIntances());
            //suppliersTable.viewSuppliers(conn.getIntances());
            //suppliersTable.viewTable(conn.getIntances());

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }
}
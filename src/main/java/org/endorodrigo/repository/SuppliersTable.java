package org.endorodrigo.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SuppliersTable {
    public void createTable(Connection conn){
        String createString = """
                CREATE TABLE SUPPLIERS
                (SUP_ID integer NOT NULL,
                SUP_NAME varchar(40) NOT NULL,
                STREET varchar(40) NOT NULL,
                CITY varchar(20) NOT NULL,
                STATE char(2) NOT NULL,
                ZIP char(5),
                PRIMARY KEY (SUP_ID));
                """;

        try(Statement stmt = conn.createStatement()){
            stmt.executeUpdate(createString);
            System.out.println("Se creo la tabla SUPPLIERS");
        }catch (SQLException e) {
            System.out.println("conn = " + e.getMessage());
        }
    }

    public void dropTable(Connection conn){
        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS SUPPLIERS");
            System.out.println("Se elimino la tabla SUPPLIERS");
        } catch (SQLException e) {
            System.out.println("conn = " + e.getMessage());
        }
    }

    public void populateTable(Connection conn){
        try(Statement stmt = conn.createStatement()) {
           stmt.executeUpdate("INSERT INTO SUPPLIERS VALUES (49,'Superior Coffee','1 Party Place','Mendocino','CA','95460')");
           stmt.executeUpdate("INSERT INTO SUPPLIERS VALUES (01,'Acme, Inc.','99 Market Street','Groundsville','CA','95199')");
           stmt.executeUpdate("INSERT INTO SUPPLIERS VALUES (150,'The High Ground','100 Coffee Lane','Meadows','CA','93966')");
            System.out.println("Registros insertados con exito");
        } catch (SQLException e) {
            System.out.println("conn = " + e.getMessage());
        }
    }

    public void viewSuppliers(Connection conn){
        String query = "SELECT SUP_NAME,SUP_ID FROM SUPPLIERS";
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Suppliers and their ID Numbers: ");
            while (rs.next()){
                String s = rs.getString("SUP_NAME");
                int n = rs.getInt("SUP_ID");
                System.out.println(s + "   " + n);
            }
        } catch (SQLException e) {
            System.out.println("conn = " + e.getMessage());
        }

    }


    public void viewTable(Connection conn){
        String query = "SELECT SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP FROM SUPPLIERS";
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                int supplierID = rs.getInt("SUP_ID");
                String supplierName = rs.getString("SUP_NAME");
                String street = rs.getString("STREET");
                String city = rs.getString("CITY");
                String state = rs.getString("STATE");
                String zip = rs.getString("ZIP");
                System.out.println(supplierName + "(" + supplierID + "): " + street +
                        ", " + city + ", " + state + ", " + zip);
            }

        } catch (SQLException e) {
            System.out.println("conn = " + e.getMessage());
        }
    }
}

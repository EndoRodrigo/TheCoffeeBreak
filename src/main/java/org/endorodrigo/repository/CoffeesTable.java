package org.endorodrigo.repository;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoffeesTable {

    public void createTable(Connection conn) {
        String createString = """
                CREATE TABLE COFFEES(COF_NAME varchar(32) NOT NULL,
                SUP_ID INT NOT NULL,
                PRICE NUMERIC(10,2) NOT NULL,
                SALES INTEGER NOT NULL,
                TOTAL INTEGER NOT NULL,
                PRIMARY KEY (COF_NAME),
                FOREIGN KEY (SUP_ID) REFERENCES SUPPLIERS (SUP_ID))
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla " + e.getMessage());
        }
    }

    public void populateTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("insert into COFFEES " +
                    "values('Colombian', 00001, 7.99, 0, 0)");
            stmt.executeUpdate("insert into COFFEES " +
                    "values('Espresso', 00150, 9.99, 0, 0)");
            stmt.executeUpdate("insert into COFFEES " +
                    "values('Colombian_Decaf', 00001, 8.99, 0, 0)");
            stmt.executeUpdate("insert into COFFEES " +
                    "values('French_Roast_Decaf', 00049, 9.99, 0, 0)");
            System.out.println("Datos insertados con estos");
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
    }


    public void updateCoffeeSales(Connection conn, HashMap<String, Integer> salesForWeek) throws SQLException {
        String updateString =
                "update COFFEES set SALES = ? where COF_NAME = ?";
        String updateStatement =
                "update COFFEES set TOTAL = TOTAL + ? where COF_NAME = ?";

        try (PreparedStatement updateSales = conn.prepareStatement(updateString);
             PreparedStatement updateTotal = conn.prepareStatement(updateStatement)) {
            conn.setAutoCommit(false);
            for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
                updateSales.setInt(1, e.getValue().intValue());
                updateSales.setString(2, e.getKey());
                updateSales.executeUpdate();

                updateTotal.setInt(1, e.getValue().intValue());
                updateTotal.setString(2, e.getKey());
                updateTotal.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e) {
            System.err.print("Transaction is being rolled back");
            conn.rollback();
        }
    }


    public void modifyPrices(Connection conn, float percentage) {
        try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet uprs = stmt.executeQuery("SELECT * FROM COFFEES");
            while (uprs.next()) {
                float f = uprs.getFloat("PRICE");
                uprs.updateFloat("PRICE", f * percentage);
                uprs.updateRow();
            }
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
    }

    public void modifyPricesByPercentage(Connection conn, String coffeeName, float priceModifier, float maximumPrice) throws SQLException {
        conn.setAutoCommit(false);
        ResultSet rs = null;
        String priceQuery = "SELECT COF_NAME, PRICE FROM COFFEES " +
                "WHERE COF_NAME = ?";
        String updateQuery = "UPDATE COFFEES SET PRICE = ? " +
                "WHERE COF_NAME = ?";
        try (PreparedStatement getPrice = conn.prepareStatement(priceQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             PreparedStatement updatePrice = conn.prepareStatement(updateQuery)) {
            Savepoint save1 = conn.setSavepoint();
            getPrice.setString(1, coffeeName);
            if (!getPrice.execute()) {
                System.out.println("Could not find entry for coffee named " + coffeeName);
            } else {
                rs = getPrice.getResultSet();
                rs.first();
                float oldPrice = rs.getFloat("PRICE");
                float newPrice = oldPrice + (oldPrice * priceModifier);
                System.out.printf("Old price of %s is $%.2f%n", coffeeName, oldPrice);
                System.out.printf("New price of %s is $%.2f%n", coffeeName, newPrice);
                System.out.println("Performing update...");
                updatePrice.setFloat(1, newPrice);
                updatePrice.setString(2, coffeeName);
                updatePrice.executeUpdate();
                System.out.println("\nCOFFEES table after update:");
                CoffeesTable.viewTable(conn);
                if (newPrice > maximumPrice) {
                    System.out.printf("The new price, $%.2f, is greater " +
                                    "than the maximum price, $%.2f. " +
                                    "Rolling back the transaction...%n",
                            newPrice, maximumPrice);
                    conn.rollback(save1);
                    System.out.println("\nCOFFEES table after rollback:");
                    CoffeesTable.viewTable(conn);
                }
                conn.commit();
            }
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void insertRow(Connection conn, String coffeeName, int supplierID, float price, int sales, int total) throws SQLException {

        try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet uprs = stmt.executeQuery("SELECT * FROM COFFEES");
            uprs.moveToInsertRow();
            uprs.updateString("COF_NAME", coffeeName);
            uprs.updateInt("SUP_ID", supplierID);
            uprs.updateFloat("PRICE", price);
            uprs.updateInt("SALES", sales);
            uprs.updateInt("TOTAL", total);

            uprs.insertRow();
            uprs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
    }

    public void batchUpdate(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        try (Statement stmt = conn.createStatement()) {

            stmt.addBatch("INSERT INTO COFFEES " +
                    "VALUES('Amaretto', 49, 9.99, 0, 0)");
            stmt.addBatch("INSERT INTO COFFEES " +
                    "VALUES('Hazelnut', 49, 9.99, 0, 0)");
            stmt.addBatch("INSERT INTO COFFEES " +
                    "VALUES('Amaretto_decaf', 49, 10.99, 0, 0)");
            stmt.addBatch("INSERT INTO COFFEES " +
                    "VALUES('Hazelnut_decaf', 49, 10.99, 0, 0)");

            int[] updateCounts = stmt.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            System.out.println("conn " + ex.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void viewTable(Connection con) throws SQLException {
        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(coffeeName + ", " + supplierID + ", " + price +
                        ", " + sales + ", " + total);
            }
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
    }

    public static void alternateViewTable(Connection con) throws SQLException {
        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString(1);
                int supplierID = rs.getInt(2);
                float price = rs.getFloat(3);
                int sales = rs.getInt(4);
                int total = rs.getInt(5);
                System.out.println(coffeeName + ", " + supplierID + ", " + price +
                        ", " + sales + ", " + total);
            }
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
    }

    public Set<String> getKeys(Connection conn) throws SQLException {
        HashSet<String> keys = new HashSet<String>();
        String query = "select COF_NAME from COFFEES";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                keys.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
        return keys;
    }

    public void dropTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS COFFEES");
            System.out.println("Tabla eliminada exo exito COFFEES");
        } catch (SQLException e) {
            System.out.println("conn " + e.getMessage());
        }
    }
}

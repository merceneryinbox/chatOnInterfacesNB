/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mercenery
 */
public class ConnectToChatProDBPostgres implements DBConnect {

    private static final String dbDriver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/chatPro";
    private static final String logEnterDB = "postgres";
    private static final String pasEnterDB = "postgres";
    private static Connection connection;

    @Override
    public void driverLoading() {
        System.out.println("Loading DB driver");
        try {
            Class.forName(dbDriver);
            System.out.println("Create DB connection");
        } catch (ClassNotFoundException e) {
        }

    }

    @Override
    public Connection connectionEstablish() {
        try {
            connection = DriverManager.getConnection(url, logEnterDB, pasEnterDB);
        } catch (SQLException e) {
        }
        return connection;
    }

}

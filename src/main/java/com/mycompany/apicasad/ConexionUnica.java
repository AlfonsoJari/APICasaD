package com.mycompany.apicasad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionUnica {

    private ConexionUnica() {
    }

    private static ConexionUnica cu;
    private static Connection cn;

    public static ConexionUnica getInstance() {
        if (cu == null) {
            try {
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConexionUnica.class.getName()).log(Level.SEVERE, null, ex);
                }
                cu = new ConexionUnica();
                cn = DriverManager.getConnection("jdbc:postgresql://34.71.158.192:5432/domotic", "user", "123456");
            } catch (SQLException ex) {
                Logger.getLogger(ConexionUnica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cu;
    }

    public Connection getConnection() {
        return cn;
    }

}

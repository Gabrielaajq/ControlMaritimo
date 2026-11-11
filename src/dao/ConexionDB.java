/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gabri
 */
public class ConexionDB {

        private static final String URL = "jdbc:sqlserver://localhost:1436;databaseName=ControlMaritimo;encrypt=true;trustServerCertificate=true;";
        private static final String USER = "sa";
        private static final String PASSWORD = "PasswordFEI2026!"; 

        public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver JDBC en las librerías del proyecto.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
        
    }
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona el establecimiento de conexiones JDBC hacia la base de datos
 * relacional en SQL Server
 */
public class ConexionDB {

        private static final String URL = "jdbc:sqlserver://localhost:1436;databaseName=ControlMaritimo;encrypt=true;trustServerCertificate=true;";
        private static final String USER = "sa";
        private static final String PASSWORD = "PasswordFEI2026!"; 

    /**
     * Abre y retorna una nueva conexión activa con la base de datos 
     *
     * @return Objeto {@link Connection} listo para ejecutar sentencias SQL
     * @throws SQLException Si ocurre un error de red, credenciales o driver JDBC
     */
        public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("No hay driver");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
        
    }
}

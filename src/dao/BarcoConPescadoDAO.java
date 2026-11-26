package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pescado;

/**
 * Implementa las operaciones de persistencia sobre la tabla puente
 * {@code BarcoConPescado} para administrar las especies 
 */
public class BarcoConPescadoDAO 
        implements RelacionBarcoPescadoDAO<Pescado, String, Integer>{

    @Override
    public boolean asignarPescado(String matricula, Integer pescadoId) {
        String sql = "INSERT INTO BarcoConPescado "
                + "(matricula_FK, pescadoId_FK) VALUES (?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            ps.setInt(2, pescadoId);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { 
                System.out.println("El pescado ya está asociado a este barco");
            } else {
                System.out.println("Error al registrar el barco: " 
                        + error.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<Pescado> consultarTodos(String matricula) {
        
        List<Pescado> listaPescados = new ArrayList<>();

        String sql = "SELECT P.pescadoId, P.nombre "
            + "FROM Pescado P "
            + "INNER JOIN BarcoConPescado BCP "
            + "ON P.pescadoId = BCP.pescadoId_FK "
            + "WHERE BCP.matricula_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, matricula);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pescado pescado = new Pescado(
                    rs.getInt("pescadoId"),
                    rs.getString("nombre")
                );

                listaPescados.add(pescado);
            }
        }

    } catch (SQLException error) {
        System.out.println("Error al consultar los pescados del barco: "
                + error.getMessage());
    }

    return listaPescados;
    }

    @Override
    public boolean eliminar(String matricula, Integer pescadoId) {

        String sql = "DELETE FROM BarcoConPescado "
                + "WHERE matricula_FK = ? AND pescadoId_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula);
            ps.setInt(2, pescadoId);

            return ps.executeUpdate() > 0;

        } catch (SQLException error) {
            System.out.println("Error al eliminar el pescado del barco: "
                    + error.getMessage());

            return false;
        }
    }
}

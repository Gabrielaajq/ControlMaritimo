package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pescado;

/**
 * Proporciona acceso a los datos del catálogo de especies
 * en la tabla {@code Pescado}
 */
public class PescadoDAO implements CrudDAO<Pescado, Integer> {

    @Override
    public boolean registrar(Pescado pescado) {
        String sql = "INSERT INTO Pescado (nombre) VALUES (?)";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, pescado.getNombre());
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pescado.setPescadoId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException error) {
            System.out.println("Error al registrar la especie de pescado: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<Pescado> consultarTodos() {
        List<Pescado> listaPescados = new ArrayList<>();
        String sql = "SELECT pescadoId, nombre FROM Pescado";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Pescado pescado = new Pescado(
                    rs.getInt("pescadoId"),
                    rs.getString("nombre")
                );
                listaPescados.add(pescado);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar el catálogo de pescados: " + e.getMessage());
        }
        return listaPescados;
    }

    @Override
    public boolean editar(Pescado pescado) {
        String sql = "UPDATE Pescado SET nombre = ? WHERE pescadoId = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, pescado.getNombre());
            ps.setInt(2, pescado.getPescadoId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el pescado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Pescado buscarPorId(Integer id) {
        String sql = "SELECT pescadoId, nombre FROM Pescado WHERE pescadoId = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pescado(
                        rs.getInt("pescadoId"),
                        rs.getString("nombre")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar el pescado: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM Pescado WHERE pescadoId = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 547) { // Error de restricción FK en SQL Server
                System.out.println("Error. Esta especie está asociada a una zona o a un barco pesquero.");
            } else {
                System.out.println("Error al eliminar el pescado: " + e.getMessage());
            }
            return false;
        }
    }
}
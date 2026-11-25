package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.BarcoPesca;

/**
 * Gestiona las operaciones de persistencia {@link BarcoPesca} y su asociación
 * con especies mediante la tabla {@code BarcoConPescado}
 */
public class BarcoPescaDAO implements CrudDAO<BarcoPesca, String> {
    
    @Override
    public boolean registrar(BarcoPesca barcoPesca) {
        
        String sql = "INSERT INTO BarcoPesca "
                + "(matricula, bandera, nombre, pesoEnToneladas, "
                + "fechaBotadura, cantidadToneladas) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoPesca.getMatricula());
            ps.setString(2, barcoPesca.getBandera());
            ps.setString(3, barcoPesca.getNombre());
            ps.setDouble(4, barcoPesca.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcoPesca.getFechaBotadura()));
            ps.setDouble(6, barcoPesca.getCantidadToneladas());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { 
                System.out.println("Error. Ya existe un barco"
                        + " registrado con esa matricula");
            } else {
                System.out.println("Error al registrar el barco: " 
                        + error.getMessage());
            }
            return false;
        }
    }
    
    @Override
    public List<BarcoPesca> consultarTodos() {
        
        List<BarcoPesca> listaBPesca = new ArrayList<>();

        String sql = "SELECT * FROM BarcoPesca";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                BarcoPesca barco = new BarcoPesca(
                    rs.getString("matricula"),
                    rs.getString("bandera"),
                    rs.getString("nombre"),
                    rs.getDouble("pesoEnToneladas"),
                    rs.getDate("fechaBotadura").toLocalDate(),
                    rs.getDouble("cantidadToneladas")
                );

                listaBPesca.add(barco);
            }

        } catch (SQLException error) {
            System.out.println("Error al consultar barcos de carga: "
                    + error.getMessage());
        }

        return listaBPesca;
    }
    
    @Override
    public boolean editar(BarcoPesca barcoPesca) {
        
        String sql = "UPDATE BarcoPesca SET bandera = ?,"
                + "nombre = ?, pesoEnToneladas = ?, fechaBotadura = ?, "
                + "cantidadToneladas = ? WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoPesca.getBandera());
            ps.setString(2, barcoPesca.getNombre());
            ps.setDouble(3, barcoPesca.getPesoTonelada());
            ps.setDate(4, java.sql.Date.valueOf
                      (barcoPesca.getFechaBotadura()));
            ps.setDouble(5, barcoPesca.getCantidadToneladas());
            ps.setString(6, barcoPesca.getMatricula());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el puerto: " 
                    + e.getMessage());
            return false;
            
        }  
        
    }
    
    @Override
    public BarcoPesca buscarPorId(String matricula) {
        
        String sql = "SELECT matricula, bandera, nombre, pesoEnToneladas,"
                + " fechaBotadura, cantidadToneladas "
                + "FROM BarcoPesca WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BarcoPesca(
                        rs.getString("matricula"),
                        rs.getString("bandera"),
                        rs.getString("nombre"),
                        rs.getDouble("pesoEnToneladas"),
                        rs.getDate("fechaBotadura").toLocalDate(),
                        rs.getDouble("cantidadToneladas")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar el puerto: " + e.getMessage());
        }
        
        return null;
        
    }

    @Override
    public boolean eliminar(String matricula) {
                
        String sql = "DELETE FROM BarcoPesca WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 547) { 
                System.out.println("Error. El barco "
                        + "tiene registros asociados.");
            } else {
                System.out.println("Error al eliminar el barco: " 
                        + e.getMessage());
            }
            return false;
            
        }
        
    }
    
}
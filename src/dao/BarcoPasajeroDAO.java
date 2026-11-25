package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.BarcoPasajero;

/**
 * Gestiona las operaciones de persistencia para barcos de
 * {@link BarcoPasajero}
 */
public class BarcoPasajeroDAO implements CrudDAO<BarcoPasajero, String> {
    
    @Override
    public boolean registrar(BarcoPasajero barcoPasajero) {
        
        String sql = "INSERT INTO BarcoPasajero "
                + "(matricula, bandera, nombre, pesoEnToneladas, fechaBotadura,"
                + " numPasajeros, numPisos, tipoViaje) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoPasajero.getMatricula());
            ps.setString(2, barcoPasajero.getBandera());
            ps.setString(3, barcoPasajero.getNombre());
            ps.setDouble(4, barcoPasajero.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcoPasajero.getFechaBotadura()));
            ps.setInt(6, barcoPasajero.getNumPasajeros());
            ps.setInt(7, barcoPasajero.getNumPisos());
            ps.setString(8, barcoPasajero.getTipoViaje());
            
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
    public List<BarcoPasajero> consultarTodos() {

        List<BarcoPasajero> listaBPasajeros = new ArrayList<>();

        String sql = "SELECT * FROM BarcoPasajero";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                BarcoPasajero barco = new BarcoPasajero(
                    rs.getString("matricula"),
                    rs.getString("bandera"),
                    rs.getString("nombre"),
                    rs.getDouble("pesoEnToneladas"),
                    rs.getDate("fechaBotadura").toLocalDate(),
                    rs.getInt("numPasajeros"),
                    rs.getInt("numPisos"),
                    rs.getString("tipoViaje")
                );

                listaBPasajeros.add(barco);
            }

        } catch (SQLException error) {
            System.out.println("Error al consultar barcos de pasajeros: "
                    + error.getMessage());
        }

        return listaBPasajeros;
    }

    @Override
    public BarcoPasajero buscarPorId(String matricula) {
        
        String sql = "SELECT matricula, bandera, nombre, pesoEnToneladas,"
                + " fechaBotadura, numPasajeros, numPisos, tipoViaje "
                + "FROM BarcoPasajero WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BarcoPasajero(
                        rs.getString("matricula"),
                        rs.getString("bandera"),
                        rs.getString("nombre"),
                        rs.getDouble("pesoEnToneladas"),
                        rs.getDate("fechaBotadura").toLocalDate(),
                        rs.getInt("numPasajeros"),
                        rs.getInt("numPisos"),
                        rs.getString("tipoViaje")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar el puerto: " + e.getMessage());
        }
        
        return null;
        
    }

    @Override
    public boolean editar(BarcoPasajero barcoPasajeros) {
        
        String sql = "UPDATE BarcoPasajero SET bandera = ?, nombre = ?,"
                + " pesoEnToneladas = ?, fechaBotadura = ?, numPasajeros = ?,"
                + " numpisos = ?, tipoViaje = ? WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoPasajeros.getBandera());
            ps.setString(2, barcoPasajeros.getNombre());
            ps.setDouble(3, barcoPasajeros.getPesoTonelada());
            ps.setDate(4, java.sql.Date.valueOf
                      (barcoPasajeros.getFechaBotadura()));
            ps.setInt(5, barcoPasajeros.getNumPasajeros());
            ps.setInt(6, barcoPasajeros.getNumPisos());
            ps.setString(7, barcoPasajeros.getTipoViaje());
            ps.setString(8, barcoPasajeros.getMatricula());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el puerto: " 
                    + e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean eliminar(String matricula) {
                
        String sql = "DELETE FROM BarcoPasajero WHERE matricula = ?";
        
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
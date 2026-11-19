/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.BarcoCarga;

/**
 *
 * @author emman
 */
public class BarcoCargaDAO implements CrudDAO<BarcoCarga, String> {
    
    @Override
    public boolean registrar(BarcoCarga barcoCarga) {
        
        String sql = "INSERT INTO BarcoCarga "
                + "(matricula, bandera, nombre, pesoEnToneladas, "
                + "fechaBotadura, numContenedores) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoCarga.getMatricula());
            ps.setString(2, barcoCarga.getBandera());
            ps.setString(3, barcoCarga.getNombre());
            ps.setDouble(4, barcoCarga.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcoCarga.getFechaBotadura()));
            ps.setInt(6, barcoCarga.getNumContenedores());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { // Error de llave primaria duplicada en SQL Server
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
    public List<BarcoCarga> consultarTodos() {
        
        List<BarcoCarga> listaBCarga = new ArrayList<>();

        String sql = "SELECT * FROM BarcoCarga";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                BarcoCarga barco = new BarcoCarga(
                    rs.getString("matricula"),
                    rs.getString("bandera"),
                    rs.getString("nombre"),
                    rs.getDouble("pesoEnToneladas"),
                    rs.getDate("fechaBotadura").toLocalDate(),
                    rs.getInt("numContenedores")
                );

                listaBCarga.add(barco);
            }

        } catch (SQLException error) {
            System.out.println("Error al consultar barcos de carga: "
                    + error.getMessage());
        }

        return listaBCarga;
    }
    
    @Override
    public boolean editar(BarcoCarga barcoCarga) {
        
        String sql = "UPDATE BarcoCarga SET bandera = ?,"
                + "nombre = ?, pesoEnToneladas = ?, fechaBotadura = ?, "
                + "numContenedores = ? WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoCarga.getMatricula());
            ps.setString(2, barcoCarga.getBandera());
            ps.setString(3, barcoCarga.getNombre());
            ps.setDouble(4, barcoCarga.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcoCarga.getFechaBotadura()));
            ps.setInt(6, barcoCarga.getNumContenedores());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el puerto: " 
                    + e.getMessage());
            return false;
            
        }  
        
    }
    
    @Override
    public BarcoCarga buscarPorId(String matricula) {
        
        String sql = "SELECT matricula, bandera, nombre, pesoEnToneladas,"
                + " fechaBotadura, numContenedores "
                + "FROM BarcoCarga WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BarcoCarga(
                        rs.getString("matricula"),
                        rs.getString("bandera"),
                        rs.getString("nombre"),
                        rs.getDouble("pesoEnToneladas"),
                        rs.getDate("fechaBotadura").toLocalDate(),
                        rs.getInt("numContenedores")
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
                
        String sql = "DELETE FROM BarcoCarga WHERE matricula = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 547) { // Error de restricción FK en SQL 
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
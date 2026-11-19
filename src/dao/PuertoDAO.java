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
import model.Puerto;


public class PuertoDAO implements CrudDAO<Puerto, String> {

    @Override
    public boolean registrar(Puerto puerto) {
        
        String sql = "INSERT INTO Puerto (codigoPuerto, nombre, ciudad, pais, numBarcos) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, puerto.getCodigoPuerto());
            ps.setString(2, puerto.getNombre());
            ps.setString(3, puerto.getCiudad());
            ps.setString(4, puerto.getPais());
            ps.setInt(5, puerto.getNumBarcos());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { // Error de llave primaria duplicada en SQL Server
                System.out.println("Error. Ya existe un puerto registrado con ese código");
            } else {
                System.out.println("Error al registrar el puerto: " + error.getMessage());
            }
            return false;
        }
        
    }

    @Override
    public List<Puerto> consultarTodos() {
        
        List<Puerto> listaPuertos = new ArrayList<>();
        String sql = "SELECT codigoPuerto, nombre, ciudad, pais, numBarcos FROM Puerto";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Puerto puerto = new Puerto(
                    rs.getString("codigoPuerto"),
                    rs.getString("nombre"),
                    rs.getString("ciudad"),
                    rs.getString("pais"),
                    rs.getInt("numBarcos")
                );
                listaPuertos.add(puerto);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar los puertos: " + e.getMessage());
        }
        return listaPuertos;
        
    }

    @Override
    public boolean editar(Puerto puerto) {
        
        String sql = "UPDATE Puerto SET nombre = ?, ciudad = ?, pais = ?, numBarcos = ? WHERE codigoPuerto = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, puerto.getNombre());
            ps.setString(2, puerto.getCiudad());
            ps.setString(3, puerto.getPais());
            ps.setInt(4, puerto.getNumBarcos());
            ps.setString(5, puerto.getCodigoPuerto()); // La PK va al final en el WHERE
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el puerto: " + e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean eliminar(String codigoPuerto) {
        
        String sql = "DELETE FROM Puerto WHERE codigoPuerto = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, codigoPuerto);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 547) { // Error de restricción FK en SQL 
                System.out.println("Error. El puerto tiene atracos o registros asociados.");
            } else {
                System.out.println("Error al eliminar el puerto: " + e.getMessage());
            }
            return false;
        }
    }
    
    @Override
    public Puerto buscarPorId(String codigoPuerto){
        
        String sql = "SELECT codigoPuerto, nombre, ciudad, pais, numBarcos FROM Puerto WHERE codigoPuerto = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, codigoPuerto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Puerto(
                        rs.getString("codigoPuerto"),
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getString("pais"),
                        rs.getInt("numBarcos")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el puerto: " + e.getMessage());
        }
        return null;
    }
    
}

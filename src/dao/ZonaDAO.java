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
import model.Pescado;
import model.Zona;

/**
 *
 * @author gabri
 */
public class ZonaDAO implements CrudDAO<Zona, String> {
   
 @Override
    public boolean registrar(Zona zona) {
        
        String sql = "INSERT INTO Zona (ClaveZona, Latitud, Longitud, Radio) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, zona.getClaveZona());
            ps.setDouble(2, zona.getLatitud());
            ps.setDouble (3, zona.getLongitud());
            ps.setDouble(4, zona.getRadio());
            
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { // Error de llave primaria duplicada en SQL Server
                System.out.println("Error. Ya existe una zona registrada con ese código");
            } else {
                System.out.println("Error al registrar la zona: " + error.getMessage());
            }
            return false;
        }
    }
        
     
    @Override
    public List<Zona> consultarTodos() {
        List<Zona> listaZonas = new ArrayList<>();
        String sql = "SELECT ClaveZona, Latitud, Longitud, Radio FROM Zona";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                listaZonas.add(mapearZona(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar el catálogo de zonas: " + e.getMessage());
        }
        return listaZonas;
    }

     @Override
    public boolean editar(Zona zona) {
        String sql = "UPDATE Zona SET Latitud = ?, Longitud = ?, Radio =?  WHERE ClaveZona = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDouble(1, zona.getLatitud());
            ps.setDouble(2, zona.getLongitud());
            ps.setDouble(3, zona.getRadio());
            ps.setString(4, zona.getClaveZona());   
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar la zona: " + e.getMessage());
            return false;
        }
    }


   @Override
    public Zona buscarPorId(String ClaveZona) {
        String sql = "SELECT ClaveZona, Latitud, Longitud, Radio FROM Zona WHERE ClaveZona = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ClaveZona);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearZona(rs);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar la Zona: " + e.getMessage());
        }
        return null;
    } 

   @Override
    public boolean eliminar(String ClaveZona) {
        String sql = "DELETE FROM Zona WHERE ClaveZona = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ClaveZona);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if(e.getErrorCode()==547){
                System.out.println("Error La zona tiene especies o registros de barco asociados");
            }else{
                System.out.println("Error al eliminar la zona: " + e.getMessage());
   
            }
            return false;
        }
    }
    
    private Zona mapearZona(ResultSet rs) throws SQLException {
         return new Zona(
            rs.getString("ClaveZona"),
            rs.getDouble("Latitud"),
            rs.getDouble("Longitud"),
            rs.getDouble("Radio")
         );
     }

    ////// pescaos
   
    public boolean asociarPescado (String claveZona, Integer pescadoId){
        String sql = "INSERT INTO Zona_Pescado (ClaveZona, ClavePescado) VALUES (?, ?)";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, claveZona);
            ps.setInt(2, pescadoId);
            
            return ps.executeUpdate() > 0;
        }catch(SQLException error){
            if(error.getErrorCode()== 2627){ // llave duplicada en sql 
                System.out.println("Error esa especia ya está asociada a esta zona");
                
            }else if (error.getErrorCode()==547){ // llave no existe 
                System.out.println("Error la clave de la zona o del pescado no existe");
                
            }else{
                System.out.println("Error, no se pudo asociar pescado a zona"+ error.getMessage()) ;
            }
            return false; 
        }    
    }
    
    public boolean quitarPescado (String claveZona, Integer pescadoId){
        String sql = "DELETE FROM Zona_Pescado WHERE ClaveZona =? AND ClavePescado = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, claveZona);
            ps.setInt(2, pescadoId);
            
            return ps.executeUpdate() > 0;
            
        }catch (SQLException e){
            System.out.println("Errore al quitar el pescado de la zona: "+ e.getMessage());
            return false; 
        }
    }
    
    public List<Pescado> consultarPescadoPorZona (String claveZona){
        List<Pescado> listaPescados = new ArrayList<>();
        
        String sql = "SELECT p.PescadoId, p.Nombre " + 
                     "FROM Pescado p " + 
                     "INNER JOIN Zona_Pescado zp ON p.PescadoId = zp.PescadoId " + 
                     "WHERE zp.ClaveZona = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, claveZona);
           
            try (ResultSet rs= ps.executeQuery()){
                while (rs.next()){
                    Pescado pescado= new Pescado(
                       rs.getInt("pescadoId"),
                       rs.getString("nombre")
                    );
                    listaPescados.add(pescado);
                    
                }    
            }
        }catch (SQLException e){
                System.out.println("Error al consultar los pescados de la zona"+ e.getMessage());
        }
       return listaPescados; 
       
    }

}




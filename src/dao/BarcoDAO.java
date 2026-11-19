/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.BarcoCarga;
import model.BarcoPasajero;
import model.BarcoPesca;
import java.sql.ResultSet;

/**
 *
 * @author gabri
 */
public class BarcoDAO {
     
    private ArrayList<BarcoPasajero> barcosPasajeros;
    private ArrayList<BarcoCarga> barcosCarga;
    private ArrayList<BarcoPesca> barcosPesca;
    
    public BarcoDAO(){
    }

    public ArrayList<BarcoPasajero> getBarcosPasajero() {
        return barcosPasajeros;
    }

    public void setBarcosPasajero(ArrayList<BarcoPasajero> barcosPasajero) {
        this.barcosPasajeros = barcosPasajero;
    }

    public ArrayList<BarcoCarga> getBarcosCarga() {
        return barcosCarga;
    }

    public void setBarcosCarga(ArrayList<BarcoCarga> barcosCarga) {
        this.barcosCarga = barcosCarga;
    }

    public ArrayList<BarcoPesca> getBarcosPesca() {
        return barcosPesca;
    }

    public void setBarcosPesca(ArrayList<BarcoPesca> barcosPesca) {
        this.barcosPesca = barcosPesca;
    }
    
    //-------------------------------Metodos----------------------------------//
    
    //Registrar barco de Pasajeros//
    public boolean registrarBarcoPasajeros(BarcoPasajero barcosPasajeros) {
        
        String sql = "INSERT INTO BarcoPasajero "
                + "(matricula, bandera, nombre, pesoEnToneladas, fechaBotadura,"
                + "numPasajeros, numPisos, tipoViaje) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcosPasajeros.getMatricula());
            ps.setString(2, barcosPasajeros.getBandera());
            ps.setString(3, barcosPasajeros.getNombre());
            ps.setDouble(4, barcosPasajeros.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcosPasajeros.getFechaBotadura()));
            ps.setInt(6, barcosPasajeros.getNumPasajeros());
            ps.setInt(7, barcosPasajeros.getNumPisos());
            ps.setString(8, barcosPasajeros.getTipoViaje());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { // Error de llave primaria duplicada en SQL Server
                System.out.println("Error. Ya existe un barco registrado con esa matricula");
            } else {
                System.out.println("Error al registrar el barco: " + error.getMessage());
            }
            return false;
        }
    }
    
    
    public List<BarcoPasajero> consultarBarcosPasajeros() {

        List<BarcoPasajero> listaPasajeros = new ArrayList<>();

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

                listaPasajeros.add(barco);
            }

        } catch (SQLException error) {
            System.out.println("Error al consultar barcos de pasajeros: "
                    + error.getMessage());
        }

        return listaPasajeros;
    }
    
    public boolean editarBarcoPasajeros(BarcoPasajero barcoPasajeros) {
        
        String sql = "UPDATE BarcoPasajero SET matricula = ?, bandera = ?,"
                + "nombre = ?, pesoEnToneladas = ?, fechaBotadura = ?,"
                + " numPasajeros = ?, numpisos = ?, tipoViaje = ? WHERE numBarcos = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoPasajeros.getMatricula());
            ps.setString(2, barcoPasajeros.getBandera());
            ps.setString(3, barcoPasajeros.getNombre());
            ps.setDouble(4, barcoPasajeros.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcoPasajeros.getFechaBotadura()));
            ps.setInt(6, barcoPasajeros.getNumPasajeros());
            ps.setInt(7, barcoPasajeros.getNumPisos());
            ps.setString(8, barcoPasajeros.getTipoViaje());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el puerto: " + e.getMessage());
            return false;
        }
        
    }
    
    public boolean eliminarBarcoPasajeros(String matricula) {
        
        String sql = "DELETE FROM Puerto WHERE codigoPuerto = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 547) { // Error de restricción FK en SQL 
                System.out.println("Error. El barco tiene registros asociados.");
            } else {
                System.out.println("Error al eliminar el barco: " + e.getMessage());
            }
            return false;
        }
    }
    
    public boolean registrarBarcoCarga(BarcoPasajero barcoPasajeros) {
        
        String sql = "INSERT INTO BarcoCarga "
                + "(matricula, bandera, nombre, pesoEnToneladas, fechaBotadura"
                + "numPasajeros, numPisos, tipoViaje) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, barcoPasajeros.getMatricula());
            ps.setString(2, barcoPasajeros.getBandera());
            ps.setString(3, barcoPasajeros.getNombre());
            ps.setDouble(4, barcoPasajeros.getPesoTonelada());
            ps.setDate(5, java.sql.Date.valueOf
                      (barcoPasajeros.getFechaBotadura()));
            ps.setInt(6, barcoPasajeros.getNumPasajeros());
            ps.setInt(7, barcoPasajeros.getNumPisos());
            ps.setString(8, barcoPasajeros.getTipoViaje());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) { // Error de llave primaria duplicada en SQL Server
                System.out.println("Error. Ya existe un barco registrado con esa matricula");
            } else {
                System.out.println("Error al registrar el barco: " + error.getMessage());
            }
            return false;
        }
    }
    
    public List<BarcoCarga> consultarBarcosCarga() {

    List<BarcoCarga> listaBCarga = new ArrayList<>();

        String sql = "SELECT * FROM BarcoCarga";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                BarcoCarga barco = new BarcoCarga(
                    rs.getInt("numContenedores"),
                    rs.getString("nombre"),
                    rs.getString("matricula"),
                    rs.getString("bandera"),
                    rs.getDouble("pesoEnToneladas"),
                    rs.getDate("fechaBotadura").toLocalDate()
                );

                listaBCarga.add(barco);
            }

        } catch (SQLException error) {
            System.out.println("Error al consultar barcos de carga: "
                    + error.getMessage());
        }

        return listaBCarga;
    }
    
    public boolean registrarBarcoPesca(BarcoPesca barcoPesca) {
        
        String sql = "INSERT INTO BarcoPesca "
                + "(matricula, bandera, nombre, pesoEnToneladas, fechaBotadura"
                + "cantidadToneladas) "
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
            if (error.getErrorCode() == 2627) { // Error de llave primaria duplicada en SQL Server
                System.out.println("Error. Ya existe un barco registrado con esa matricula");
            } else {
                System.out.println("Error al registrar el barco: " + error.getMessage());
            }
            return false;
        }
    }
    
    
    
    //Eliminar Barco de Pasajeros por medio de la matricula//
    public boolean eliminarBarcoPasajero(String matricula){
        
        if (barcosPasajeros == null || matricula == null || matricula.isEmpty()) {
            return false;
        }
        
        for(int i = 0; i < barcosPasajeros.size(); i++){
            if (barcosPasajeros.get(i).getMatricula().compareToIgnoreCase(matricula) == 0) {
                barcosPasajeros.remove(i);
                return true;
            }
        }
        return false;
    }
    
    //Eliminar Barco de Carga por medio de la matricula//
    public boolean eliminarBarcoCarga(String matricula){
        
        if (barcosCarga == null || matricula == null || matricula.isEmpty()) {
            return false;
        }
        
        for(int i = 0; i < barcosCarga.size(); i++){
            if(barcosCarga.get(i).getMatricula().compareToIgnoreCase(matricula) == 0){
                barcosCarga.remove(i);
                return true;
            }
        }
        return false;
    }
    
    //Eliminar Barco de Pesca por medio de la matricula//
    public boolean eliminarBarcoPesca(String matricula){
        
        if (barcosPesca == null || matricula == null || matricula.isEmpty()) {
            return false;
        }
        
        for(int i = 0; i < barcosPesca.size(); i++){
            if(barcosPesca.get(i).getMatricula().compareToIgnoreCase(matricula) == 0){
                barcosPesca.remove(i);
                return true;
            }
        }
        return false;
    }
    
    //Verifica si hay Barcos de Pasajeros registrados en su respectiva lista//
    public boolean hayBarcosPasajerosRegistrados() {
        return !(barcosPasajeros == null || barcosPasajeros.isEmpty());
    }
    
    //Verifica si hay Barcos de Carga registrados en su respectiva lista//
    public boolean hayBarcosCargaRegistrados() {
        return !(barcosCarga == null || barcosCarga.isEmpty());
    }
    
    //Verifica si hay Barcos de Pesca registrados en su respectiva lista//
    public boolean hayBarcosPescaRegistrados() {
        return !(barcosPesca == null || barcosPesca.isEmpty());
    }
    
    //Verifica si hay barcos registrados//
    public boolean hayBarcosRegistrados() {
        return (hayBarcosPasajerosRegistrados() || hayBarcosCargaRegistrados()
                 ||  hayBarcosPescaRegistrados());
    }
    
    public void consultarBarcosPasajeros(){
        if(!hayBarcosPasajerosRegistrados()){
            System.out.println("No hay Barcos de Pasajeros Registrados...\n");
            return;
        }
        System.out.println("\n Barcos de Pasajeros registrados: \n");
        for (BarcoPasajero bPj : barcosPasajeros){
            System.out.println(String.format("""
                                         DATOS DEL BARCO
                                         Matricula: %s
                                         Bandera: %s
                                         Nombre: %s
                                         Peso del Barco (En Toneladas): %s
                                         Fecha de Botadura: %s
                                         Capacidad de Pasajeros Maximo: %s
                                         Numero de Pisos del Barco: %s
                                         Tipo de Viaje: %s
                                         
                                         --------------------------------
                                         """,
                bPj.getMatricula(),
                bPj.getBandera(),
                bPj.getNombre(),
                bPj.getPesoTonelada(),
                bPj.getFechaBotadura(),
                bPj.getNumPasajeros(),
                bPj.getNumPisos(),
                bPj.getTipoViaje()));
        }
        System.out.println("...");
    }
    
    public void consultarBarcosCarga(){
        if(!hayBarcosPasajerosRegistrados()){
            System.out.println("No hay Barcos de Carga Registrados...\n");
            return;
        }
        System.out.println("\n Barcos de Carga registrados: \n");
        for (BarcoCarga bCg : barcosCarga){
            System.out.println(String.format("""
                                         DATOS DEL BARCO
                                         Matricula: %s
                                         Bandera: %s
                                         Nombre: %s
                                         Peso del Barco (En Toneladas): %s
                                         Fecha de Botadura: %s
                                         Numero de Contenedores: %s
                                         
                                         --------------------------------
                                         """,
                bCg.getMatricula(),
                bCg.getBandera(),
                bCg.getNombre(),
                bCg.getPesoTonelada(),
                bCg.getFechaBotadura(),
                bCg.getNumContenedores()));
        }
    }
    
    public void consultarBarcosPesca(){
        if(!hayBarcosPasajerosRegistrados()){
            System.out.println("No hay Barcos de Pesca Registrados...\n");
            return;
        }
        System.out.println("\n Barcos de Pesca registrados: \n");
        for (BarcoPesca bPc : barcosPesca){
            System.out.println(String.format("""
                                         DATOS DEL BARCO
                                         Matricula: %s
                                         Bandera: %s
                                         Nombre: %s
                                         Peso del Barco (En Toneladas): %s
                                         Fecha de Botadura: %s
                                         Numero de Contenedores: %s
                                         
                                         --------------------------------
                                         """,
                bPc.getMatricula(),
                bPc.getBandera(),
                bPc.getNombre(),
                bPc.getPesoTonelada(),
                bPc.getFechaBotadura(),
                bPc.getCantidadToneladas()));
        }
    }
    
    public BarcoPasajero buscarBarcoPasajeros(String matricula){
        BarcoPasajero  bPj = null;
        if(matricula == null || matricula.isEmpty()){
            return null;
        }
        for(BarcoPasajero bsPj : barcosPasajeros){
            if(bsPj.getMatricula().compareToIgnoreCase(matricula)== 0){
                bPj = bsPj;
                break;
            }
        }
        return bPj;
    }
    
    public BarcoPesca buscarBarcoPesca(String matricula){
        BarcoPesca  bPc = null;
        if(matricula == null || matricula.isEmpty()){
            return null;
        }
        for(BarcoPesca bsPc : barcosPesca){
            if(bsPc.getMatricula().compareToIgnoreCase(matricula)== 0){
                bPc = bsPc;
                break;
            }
        }
        return bPc;
    }
}

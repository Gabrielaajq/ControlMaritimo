/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ZonaDAO;
import java.util.List;
import model.Pescado;
import model.Zona;

/**
 *
 * @author gabri
 */
public class ZonaController {
    
    private final ZonaDAO zonaDAO; 
    
    public ZonaController(){
        this.zonaDAO= new ZonaDAO();
    }
    public boolean registrarZona(String claveZona, double latitud, double longitud, double radio){
        if (claveZona== null || claveZona.trim().isEmpty()){
            System.out.println("Error la clave no puede estar vacia");
            return false; 
    }
        if(radio <= 0){
            System.out.println("Error, el radio de la zona debe ser mayor de 0 ");
            return false; 
        }
        if (latitud < -90 || latitud >90 || longitud < -180 || longitud >180){
            System.out.println("Error, las cordenadas no son validas ");
            return false; 
        }
        Zona nuevaZona= new Zona(claveZona.trim(), latitud, longitud, radio);
        return zonaDAO.registrar(nuevaZona);
    }
    
    public List<Zona> listarZonas(){
        return zonaDAO.consultarTodos();
    }
    
    public Zona buscarZona (String claveZona){
        if(claveZona == null || claveZona.trim().isEmpty()){
            System.out.println("Ingresa una clave valida");
            return null; 
        }
        return zonaDAO.buscarPorId(claveZona.trim());
    }
    
    public boolean editarZona (String claveZona, double latitud, double longitud, double radio){
        if(claveZona== null || claveZona.trim().isEmpty()){
            System.out.println("La clave es obligatoria");
            return false; 
        }
        
        if (radio <=0){
            System.out.println("Error el radio deber ser mayor de 0");
            return false; 
        }
        
        if(latitud < -90 || latitud >90 || longitud <-180 || longitud >180){
            System.out.println("Error, las coordenadas no son validas ");
            return false; 
        }
        
        Zona zonaActualizada= new Zona (claveZona.trim(), latitud, longitud, radio);
        return zonaDAO.editar(zonaActualizada);
        
    }
    
    public boolean eliminarZona (String claveZona){
        if(claveZona== null || claveZona.trim().isEmpty()){
            System.out.println("La clave no puede estar vacia ");
            return false; 
        }
        return zonaDAO.eliminar(claveZona.trim());
        
    }
    
    public boolean asociarPescadoAZona (String claveZona, Integer pescadoId){
        if( claveZona == null || claveZona.trim().isEmpty()|| pescadoId== null || pescadoId <=0){
            System.out.println("Datos inválidos");
            return false;
        }
        return zonaDAO.asociarPescado(claveZona.trim(), pescadoId);
    }
    
    public boolean quitarPescadosDeZona (String claveZona, Integer pescadoId){
        if (claveZona == null || claveZona.trim().isEmpty()|| pescadoId== null || pescadoId <=0){
            System.out.println("Datos inválidos ");
            return false;
        }
        return zonaDAO.quitarPescado(claveZona.trim(), pescadoId);
    }
    
    public List<Pescado> obtenerPescadosDeZona (String claveZona){
        if (claveZona == null|| claveZona.trim().isEmpty()){
        System.out.println("La clave es obligatoria");
        return List.of();
    }
        return zonaDAO.consultarPescadoPorZona(claveZona.trim());
    }
}

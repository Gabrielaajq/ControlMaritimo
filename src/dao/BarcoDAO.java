/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import model.BarcoCarga;
import model.BarcoPasajero;
import model.BarcoPesca;

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
    
    //Añadir barco de pasajeros//
    public boolean agregarBarcoPasajeros(BarcoPasajero barcoPasajero){
        if(barcoPasajero == null){
            return false;
        }
        
        if(barcosPasajeros == null){
            barcosPasajeros = new ArrayList<BarcoPasajero>();
        }
        
        barcosPasajeros.add(barcoPasajero);
        return true;
    }
    
    //Añadir barco de carga//
    public boolean agregarBarcoCarga(BarcoCarga barcoCarga) {
        if (barcoCarga == null) {
            return false;
        }
        
        if(barcosCarga == null) {
            barcosCarga = new ArrayList<BarcoCarga>();
        }
        
        barcosCarga.add(barcoCarga);
        return true;
    }
    
    //Añadir barco de pesca//
    public boolean agregarBarcoPesca(BarcoPesca barcoPesca) {
        if(barcoPesca == null) {
            return false;
        }
        
        if(barcosPesca == null) {
            barcosPesca = new ArrayList<BarcoPesca>();
        }
       
        barcosPesca.add(barcoPesca);
        return true;
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
        for (BarcoPasajero bPs : barcosPasajeros){
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
                bPs.getMatricula(),
                bPs.getBandera(),
                bPs.getNombre(),
                bPs.getPesoTonelada(),
                bPs.getFechaBotadura(),
                bPs.getNumPasajeros(),
                bPs.getNumPisos(),
                bPs.getTipoViaje()));
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
        BarcoPasajero  bP= null;
        if(matricula == null || matricula.isEmpty()){
            return null;
        }
        for(BarcoPasajero bPs : barcosPasajeros){
            if(b.getNombre().compareToIgnoreCase(nombre)== 0){
                c = b;
                break;
            }
        }
        return c;
    }
}

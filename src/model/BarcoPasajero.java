/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author gabri
 */
public class BarcoPasajero extends Barco{
     
    private Integer numPasajeros;
    private Integer numPisos;
    private String tipoViaje;

    public BarcoPasajero(Integer numPasajeros, Integer numPisos, String tipoViaje, String nombre, String matricula, String bandera, Double pesoTonelada, LocalDate fechaBotadura) {
        super(nombre, matricula, bandera, pesoTonelada, fechaBotadura);
        this.numPasajeros = numPasajeros;
        this.numPisos = numPisos;
        this.tipoViaje = tipoViaje;
    }

    public Integer getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(Integer numPasajeros) {
        this.numPasajeros = numPasajeros;
    }

    public Integer getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(Integer numPisos) {
        this.numPisos = numPisos;
    }

    public String getTipoViaje() {
        return tipoViaje;
    }

    public void setTipoViaje(String tipoViaje) {
        this.tipoViaje = tipoViaje;
    }
    
    @Override
    public String detalles(){
        return null;
    }
    
    @Override
    public Double capacidadCarga(){
        return null;
    }
    
}

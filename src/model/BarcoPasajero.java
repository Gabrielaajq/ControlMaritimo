package model;

import java.time.LocalDate;

/**
 * Representa un barco destinado al traslado de personas, agregando
 * capacidad de pasajeros, número de pisos y tipo de viaje
 */
public class BarcoPasajero extends Barco{
     
    private Integer numPasajeros;
    private Integer numPisos;
    private String tipoViaje;

    public BarcoPasajero (String matricula, String bandera, String nombre, 
            Double pesoTonelada, LocalDate fechaBotadura, 
            Integer numPasajeros, Integer numPisos, String tipoViaje) {
        super(matricula, bandera, nombre, pesoTonelada, fechaBotadura);
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

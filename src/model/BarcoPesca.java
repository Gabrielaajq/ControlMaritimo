package model;

import java.time.LocalDate;

/**
 * Representa un barco pesquer, añadiendo su capacidad máxima de captura
 * en toneladas y su relación con las especies que busca
 */
public class BarcoPesca extends Barco{
    
    private Double cantidadToneladas;

    public BarcoPesca(String matricula, String bandera, String nombre, Double pesoTonelada, 
            LocalDate fechaBotadura, Double cantidadToneladas) {
        super(matricula, bandera, nombre, pesoTonelada, fechaBotadura);
        this.cantidadToneladas = cantidadToneladas;
    }

    public Double getCantidadToneladas() {
        return cantidadToneladas;
    }

    public void setCantidadToneladas(Double cantidadToneladas) {
        this.cantidadToneladas = cantidadToneladas;
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
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
public class BarcoPesca extends Barco{
    
    private Double cantidadToneladas;

    public BarcoPesca(Double cantidadToneladas, String nombre, String matricula, String bandera, Double pesoTonelada, LocalDate fechaBotadura) {
        super(nombre, matricula, bandera, pesoTonelada, fechaBotadura);
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

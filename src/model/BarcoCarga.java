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
public class BarcoCarga extends Barco{
    
    private Integer numContenedores;

    public BarcoCarga(Integer numContenedores, String nombre, String matricula, String bandera, Double pesoTonelada, LocalDate fechaBotadura) {
        super(nombre, matricula, bandera, pesoTonelada, fechaBotadura);
        this.numContenedores = numContenedores;
    }

    public Integer getNumContenedores() {
        return numContenedores;
    }

    public void setNumContenedores(Integer numContenedores) {
        this.numContenedores = numContenedores;
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

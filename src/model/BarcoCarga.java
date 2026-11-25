package model;

import java.time.LocalDate;

/**
 * Representa una embarcación especializada en el transporte
 * de mercancías, extendiendo los datos generales de 
 * {@link Barco} con su capacidad de contenedores
 */
public class BarcoCarga extends Barco{
    
    private Integer numContenedores;

    public BarcoCarga(String matricula, String bandera, String nombre,
                      Double pesoTonelada, LocalDate fechaBotadura,
                      Integer numContenedores) {
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

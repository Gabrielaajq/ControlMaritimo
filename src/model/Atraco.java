package model;

import java.time.LocalDate;

/**
 * Representa el evento de arribo de un barco a un puerto específico
 * en una fecha determinada
 */
public class Atraco {
    
    private Integer atracoId;
    private LocalDate fechaAtraco;
    private String codigoPuerto; 
    private String matricula; 

    public Atraco() {
    }

    public Atraco(LocalDate fechaAtraco, String codigoPuerto, String matricula) {
        this.fechaAtraco = fechaAtraco;
        this.codigoPuerto = codigoPuerto;
        this.matricula = matricula;
    }

    public Atraco(Integer atracoId, LocalDate fechaAtraco, String codigoPuerto, String matricula) {
        this.atracoId = atracoId;
        this.fechaAtraco = fechaAtraco;
        this.codigoPuerto = codigoPuerto;
        this.matricula = matricula;
    }

    public Integer getAtracoId() {
        return atracoId;
    }

    public void setAtracoId(Integer atracoId) {
        this.atracoId = atracoId;
    }

    public LocalDate getFechaAtraco() {
        return fechaAtraco;
    }

    public void setFechaAtraco(LocalDate fechaAtraco) {
        this.fechaAtraco = fechaAtraco;
    }

    public String getCodigoPuerto() {
        return codigoPuerto;
    }

    public void setCodigoPuerto(String codigoPuerto) {
        this.codigoPuerto = codigoPuerto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}

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
public class Atraco {
    
    private Integer atracoId;
    private LocalDate fechaAtraco;
    private String codigoPuerto; //FK
    private String matricula; //FK

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

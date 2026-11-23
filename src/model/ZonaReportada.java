/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author gabri
 */
public class ZonaReportada {
    
    private Integer zonaReportadaId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private String claveZona; //fk
    private String matricula; //fk

    public ZonaReportada() {
    }

    public ZonaReportada(LocalDateTime fechaEntrada, LocalDateTime fechaSalida, 
                         String claveZona, String matricula) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.claveZona = claveZona;
        this.matricula = matricula;
    }

    public ZonaReportada(Integer zonaReportadaId, LocalDateTime fechaEntrada, 
                         LocalDateTime fechaSalida, String claveZona, String matricula) {
        this.zonaReportadaId = zonaReportadaId;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.claveZona = claveZona;
        this.matricula = matricula;
    }

    public Integer getZonaReportadaId() {
        return zonaReportadaId;
    }

    public void setZonaReportadaId(Integer zonaReportadaId) {
        this.zonaReportadaId = zonaReportadaId;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getClaveZona() {
        return claveZona;
    }

    public void setClaveZona(String claveZona) {
        this.claveZona = claveZona;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}

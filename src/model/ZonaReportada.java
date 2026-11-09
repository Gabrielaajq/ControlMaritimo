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
public class ZonaReportada {
    
    private Integer zonaReportadaId;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    public Integer getZonaReportadaId() {
        return zonaReportadaId;
    }

    public void setZonaReportadaId(Integer zonaReportadaId) {
        this.zonaReportadaId = zonaReportadaId;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
}

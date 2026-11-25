package model;

import java.time.LocalDateTime;

/**
 * Registra la actividad de un barco pesquero dentro de una zona de pesca,
 * almacenando las fechas de entrada y salida
 */
public class ZonaReportada {
    
    private Integer zonaReportadaId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private String claveZona; 
    private String matricula;

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

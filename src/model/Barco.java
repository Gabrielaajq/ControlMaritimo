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
public abstract class Barco {
    
  private String nombre;
  private String matricula;
  private String bandera;
  private Double pesoTonelada;
  private LocalDate fechaBotadura;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public Double getPesoTonelada() {
        return pesoTonelada;
    }

    public void setPesoTonelada(Double pesoTonelada) {
        this.pesoTonelada = pesoTonelada;
    }

    public LocalDate getFechaBotadura() {
        return fechaBotadura;
    }

    public void setFechaBotadura(LocalDate fechaBotadura) {
        this.fechaBotadura = fechaBotadura;
    }

    public Barco(String nombre, String matricula, String bandera, Double pesoTonelada, LocalDate fechaBotadura) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.bandera = bandera;
        this.pesoTonelada = pesoTonelada;
        this.fechaBotadura = fechaBotadura;
    }
  
    public abstract String  detalles();
    public abstract Double capacidadCarga();
    
}

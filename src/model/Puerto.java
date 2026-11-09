/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gabri
 */
public class Puerto {
    
    private String codigoPuerto;
    private String nombre;
    private String ciudad;
    private String pais;
    private Integer numBarcos;

    public Puerto(String codigoPuerto, String nombre, String ciudad, String pais, Integer numBarcos) {
        this.codigoPuerto = codigoPuerto;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.numBarcos = numBarcos;
    }

    public String getCodigoPuerto() {
        return codigoPuerto;
    }

    public void setCodigoPuerto(String codigoPuerto) {
        this.codigoPuerto = codigoPuerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getNumBarcos() {
        return numBarcos;
    }

    public void setNumBarcos(Integer numBarcos) {
        this.numBarcos = numBarcos;
    }
    
}

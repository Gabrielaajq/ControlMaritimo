/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enums.Rol;

/**
 *
 * @author gabri
 */
public class Usuario {
    
    private Integer usuarioId;
    private String username;
    private String password;
    private Rol rol;
    
    //Constructor donde SQL pone pordefault el usuario id porque es incremental
    public Usuario(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }
    
    //Constructor para cuando venga de SQL
    public Usuario(Integer usuarioId, String username, String password, Rol rol) {
        this.usuarioId = usuarioId;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }
    
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}

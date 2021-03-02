/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Z420WK
 */

@Entity
@Table(name = "Cartera")
public class Cartera implements Serializable{    
    
    @Id
    @Column(name = "codigo_cartera")
    private String codigo_cartera;
    
    @Column(name = "nombre_cartera")
    private String nombre_cartera;

    public Cartera() {}
    
    public Cartera(String codigo_cartera) {
        this.codigo_cartera = codigo_cartera;
    }    

    public Cartera(String codigo_cartera, String nombre_cartera) {
        this.codigo_cartera = codigo_cartera;
        this.nombre_cartera = nombre_cartera;
    }
    
    public String getCodigo_cartera() {
        return codigo_cartera;
    }

    public void setCodigo_cartera(String codigo_cartera) {
        this.codigo_cartera = codigo_cartera;
    }

    public String getNombre_cartera() {
        return nombre_cartera;
    }

    public void setNombre_cartera(String nombre_cartera) {
        this.nombre_cartera = nombre_cartera;
    }    

    @Override
    public String toString() {
        return "Cartera{" + "codigo_cartera=" + codigo_cartera + ", nombre_cartera=" + nombre_cartera + '}';
    }    
}

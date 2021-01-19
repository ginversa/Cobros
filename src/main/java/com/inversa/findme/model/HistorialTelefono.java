/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Z420WK
 */

@Entity
@Table(name="HistorialTelefono")
public class HistorialTelefono implements Serializable{
    
    @Id
    private Integer id;
    
    @Column(name="cedula")
    private String cedula;
    
    @Column(name="telefono")
    private String telefono;
    
    @Column(name="tipo_telefono")
    private String tipo_telefono;

    @Column(name="fecha_del_dato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_del_dato;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo_telefono() {
        return tipo_telefono;
    }

    public void setTipo_telefono(String tipo_telefono) {
        this.tipo_telefono = tipo_telefono;
    }

    public Date getFecha_del_dato() {
        return fecha_del_dato;
    }

    public void setFecha_del_dato(Date fecha_del_dato) {
        this.fecha_del_dato = fecha_del_dato;
    }

    @Override
    public String toString() {
        return "HistorialTelefono{" + "id=" + id + ", cedula=" + cedula + ", telefono=" + telefono + ", tipo_telefono=" + tipo_telefono + ", fecha_del_dato=" + fecha_del_dato + '}';
    }

    
    
}

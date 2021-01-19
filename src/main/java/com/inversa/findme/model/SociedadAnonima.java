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
@Table(name="SociedadAnonima")
public class SociedadAnonima implements Serializable{
    
    @Id
    private Integer id;
    
    @Column(name="cedula")
    private String cedula;
    
    @Column(name="posicion")
    private String posicion;
    
    @Column(name="nombre_sociedad")
    private String nombre_sociedad;
    
    @Column(name="cedula_juridica")
    private String cedula_juridica;
    
    @Column(name="direccion")
    private String direccion;
    
    @Column(name="telefono")
    private String telefono;
    
    @Column(name="fax")
    private String fax;
    
    @Column(name="verificado")
    private String verificado;
    
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

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getNombre_sociedad() {
        return nombre_sociedad;
    }

    public void setNombre_sociedad(String nombre_sociedad) {
        this.nombre_sociedad = nombre_sociedad;
    }

    public String getCedula_juridica() {
        return cedula_juridica;
    }

    public void setCedula_juridica(String cedula_juridica) {
        this.cedula_juridica = cedula_juridica;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getVerificado() {
        return verificado;
    }

    public void setVerificado(String verificado) {
        this.verificado = verificado;
    }

    public Date getFecha_del_dato() {
        return fecha_del_dato;
    }

    public void setFecha_del_dato(Date fecha_del_dato) {
        this.fecha_del_dato = fecha_del_dato;
    }

    @Override
    public String toString() {
        return "SociedadAnonima{" + "id=" + id + ", cedula=" + cedula + ", posicion=" + posicion + ", nombre_sociedad=" + nombre_sociedad + ", cedula_juridica=" + cedula_juridica + ", direccion=" + direccion + ", telefono=" + telefono + ", fax=" + fax + ", verificado=" + verificado + ", fecha_del_dato=" + fecha_del_dato + '}';
    }
    
}

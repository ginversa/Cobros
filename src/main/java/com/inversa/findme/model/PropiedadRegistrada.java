/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PropiedadRegistrada")
public class PropiedadRegistrada implements Serializable {

    @Id
    private Integer id;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "canton")
    private String canton;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "moneda")
    private String moneda;

    @Column(name = "pais")
    private String pais;

    @Column(name = "fecha_del_dato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_del_dato;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }    

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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getFecha_del_dato() {
        return fecha_del_dato;
    }

    public void setFecha_del_dato(Date fecha_del_dato) {
        this.fecha_del_dato = fecha_del_dato;
    }

    @Override
    public String toString() {
        return "PropiedadRegistrada{" + "id=" + id + ", cedula=" + cedula + ", provincia=" + provincia + ", canton=" + canton + ", distrito=" + distrito + ", valor=" + valor + ", pais=" + pais + ", fecha_del_dato=" + fecha_del_dato + '}';
    }

}

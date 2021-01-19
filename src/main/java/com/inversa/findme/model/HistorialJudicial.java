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
@Table(name="HistorialJudicial")
public class HistorialJudicial implements Serializable{
    
    @Id
    private Integer id;
    
    @Column(name="cedula")
    private String cedula;
    
    @Column(name="expediente")
    private String expediente;
    
    @Column(name="asunto")
    private String asunto;
    
    @Column(name="oficina")
    private String oficina;
    
    @Column(name="caso")
    private String caso;
    
    @Column(name="tipo_parte")
    private String tipo_parte;
    
    @Column(name="fecha_caso")
    private String fecha_caso;
    
    @Column(name="estado")
    private String estado;
    
    @Column(name="pais")
    private String pais;
    
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

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getCaso() {
        return caso;
    }

    public void setCaso(String caso) {
        this.caso = caso;
    }

    public String getTipo_parte() {
        return tipo_parte;
    }

    public void setTipo_parte(String tipo_parte) {
        this.tipo_parte = tipo_parte;
    }

    public String getFecha_caso() {
        return fecha_caso;
    }

    public void setFecha_caso(String fecha_caso) {
        this.fecha_caso = fecha_caso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        return "HistorialJudicial{" + "id=" + id + ", cedula=" + cedula + ", expediente=" + expediente + ", asunto=" + asunto + ", oficina=" + oficina + ", caso=" + caso + ", tipo_parte=" + tipo_parte + ", fecha_caso=" + fecha_caso + ", estado=" + estado + ", pais=" + pais + ", fecha_del_dato=" + fecha_del_dato + '}';
    }
    
}

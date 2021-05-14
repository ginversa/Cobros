/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "laboral_historico")
public class LaboralHistorico implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Size(max = 15)
    @Column(name = "cedula")
    private String cedula;

    @Size(max = 24)
    @Column(name = "periodo")
    private String periodo;

    @Size(max = 24)
    @Column(name = "ingreso")
    private String ingreso;

    @Size(max = 20)
    @Column(name = "patrono")
    private String patrono;

    @Size(max = 130)
    @Column(name = "nombre_patrono")
    private String nombre_patrono;

    @Size(max = 3)
    @Column(name = "pais")
    private String pais;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;

    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.DATE)
    private Date ultima_modificacion;

    @Size(max = 1)
    @Column(name = "estado")
    private String estado;

    public LaboralHistorico() {
    }

    public LaboralHistorico(Integer id, String cedula, String periodo, String ingreso, String patrono, String nombre_patrono, String pais, Date fecha_creacion, Date ultima_modificacion, String estado) {
        this.id = id;
        this.cedula = cedula;
        this.periodo = periodo;
        this.ingreso = ingreso;
        this.patrono = patrono;
        this.nombre_patrono = nombre_patrono;
        this.pais = pais;
        this.fecha_creacion = fecha_creacion;
        this.ultima_modificacion = ultima_modificacion;
        this.estado = estado;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    public String getPatrono() {
        return patrono;
    }

    public void setPatrono(String patrono) {
        this.patrono = patrono;
    }

    public String getNombre_patrono() {
        return nombre_patrono;
    }

    public void setNombre_patrono(String nombre_patrono) {
        this.nombre_patrono = nombre_patrono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getUltima_modificacion() {
        return ultima_modificacion;
    }

    public void setUltima_modificacion(Date ultima_modificacion) {
        this.ultima_modificacion = ultima_modificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.cedula);
        hash = 97 * hash + Objects.hashCode(this.periodo);
        hash = 97 * hash + Objects.hashCode(this.ingreso);
        hash = 97 * hash + Objects.hashCode(this.patrono);
        hash = 97 * hash + Objects.hashCode(this.nombre_patrono);
        hash = 97 * hash + Objects.hashCode(this.pais);
        hash = 97 * hash + Objects.hashCode(this.fecha_creacion);
        hash = 97 * hash + Objects.hashCode(this.ultima_modificacion);
        hash = 97 * hash + Objects.hashCode(this.estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LaboralHistorico other = (LaboralHistorico) obj;
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        if (!Objects.equals(this.ingreso, other.ingreso)) {
            return false;
        }
        if (!Objects.equals(this.patrono, other.patrono)) {
            return false;
        }
        if (!Objects.equals(this.nombre_patrono, other.nombre_patrono)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fecha_creacion, other.fecha_creacion)) {
            return false;
        }
        if (!Objects.equals(this.ultima_modificacion, other.ultima_modificacion)) {
            return false;
        }
        return true;
    }

}

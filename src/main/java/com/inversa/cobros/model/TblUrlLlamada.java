/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_url_llamada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUrlLlamada.findAll", query = "SELECT t FROM TblUrlLlamada t"),
    @NamedQuery(name = "TblUrlLlamada.findById", query = "SELECT t FROM TblUrlLlamada t WHERE t.id = :id"),
    @NamedQuery(name = "TblUrlLlamada.findByServicio", query = "SELECT t FROM TblUrlLlamada t WHERE t.servicio = :servicio"),
    @NamedQuery(name = "TblUrlLlamada.findByParametro", query = "SELECT t FROM TblUrlLlamada t WHERE t.parametro = :parametro"),
    @NamedQuery(name = "TblUrlLlamada.findByDescripcion", query = "SELECT t FROM TblUrlLlamada t WHERE t.descripcion = :descripcion")})
public class TblUrlLlamada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "servicio")
    private String servicio;
    @Size(max = 100)
    @Column(name = "parametro")
    private String parametro;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_central", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblCentral idCentral;

    public TblUrlLlamada() {
    }

    public TblUrlLlamada(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TblCentral getIdCentral() {
        return idCentral;
    }

    public void setIdCentral(TblCentral idCentral) {
        this.idCentral = idCentral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUrlLlamada)) {
            return false;
        }
        TblUrlLlamada other = (TblUrlLlamada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblUrlLlamada[ id=" + id + " ]";
    }
    
}

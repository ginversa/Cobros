/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tipodescuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipodescuento.findAll", query = "SELECT t FROM Tipodescuento t"),
    @NamedQuery(name = "Tipodescuento.findByIdtipodescuento", query = "SELECT t FROM Tipodescuento t WHERE t.idtipodescuento = :idtipodescuento"),
    @NamedQuery(name = "Tipodescuento.findByCodigo", query = "SELECT t FROM Tipodescuento t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Tipodescuento.findByNombre", query = "SELECT t FROM Tipodescuento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tipodescuento.findByDescripcion", query = "SELECT t FROM Tipodescuento t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipodescuento.findByUsuarioingreso", query = "SELECT t FROM Tipodescuento t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Tipodescuento.findByFechaingreso", query = "SELECT t FROM Tipodescuento t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Tipodescuento.findByUsuariomodifico", query = "SELECT t FROM Tipodescuento t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Tipodescuento.findByFechamodifico", query = "SELECT t FROM Tipodescuento t WHERE t.fechamodifico = :fechamodifico")})
public class Tipodescuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipodescuento")
    private Integer idtipodescuento;
    @Size(max = 3)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "usuarioingreso")
    private String usuarioingreso;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaingreso;
    @Size(max = 50)
    @Column(name = "usuariomodifico")
    private String usuariomodifico;
    @Column(name = "fechamodifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodifico;
    @OneToMany(mappedBy = "idtipodescuento", fetch = FetchType.LAZY)
    private List<TblPromesa> tblPromesaList;

    public Tipodescuento() {
    }

    public Tipodescuento(Integer idtipodescuento) {
        this.idtipodescuento = idtipodescuento;
    }

    public Integer getIdtipodescuento() {
        return idtipodescuento;
    }

    public void setIdtipodescuento(Integer idtipodescuento) {
        this.idtipodescuento = idtipodescuento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioingreso() {
        return usuarioingreso;
    }

    public void setUsuarioingreso(String usuarioingreso) {
        this.usuarioingreso = usuarioingreso;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getUsuariomodifico() {
        return usuariomodifico;
    }

    public void setUsuariomodifico(String usuariomodifico) {
        this.usuariomodifico = usuariomodifico;
    }

    public Date getFechamodifico() {
        return fechamodifico;
    }

    public void setFechamodifico(Date fechamodifico) {
        this.fechamodifico = fechamodifico;
    }

    @XmlTransient
    public List<TblPromesa> getTblPromesaList() {
        return tblPromesaList;
    }

    public void setTblPromesaList(List<TblPromesa> tblPromesaList) {
        this.tblPromesaList = tblPromesaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipodescuento != null ? idtipodescuento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodescuento)) {
            return false;
        }
        Tipodescuento other = (Tipodescuento) object;
        if ((this.idtipodescuento == null && other.idtipodescuento != null) || (this.idtipodescuento != null && !this.idtipodescuento.equals(other.idtipodescuento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
}

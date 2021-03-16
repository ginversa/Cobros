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
import javax.persistence.CascadeType;
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
@Table(name = "tipificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipificacion.findAll", query = "SELECT t FROM Tipificacion t"),
    @NamedQuery(name = "Tipificacion.findByIdTipificacion", query = "SELECT t FROM Tipificacion t WHERE t.idTipificacion = :idTipificacion"),
    @NamedQuery(name = "Tipificacion.findByDescripcion", query = "SELECT t FROM Tipificacion t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipificacion.findByCodigoCartera", query = "SELECT t FROM Tipificacion t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "Tipificacion.findByEstado", query = "SELECT t FROM Tipificacion t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tipificacion.findByUsuarioingreso", query = "SELECT t FROM Tipificacion t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Tipificacion.findByFechaingreso", query = "SELECT t FROM Tipificacion t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Tipificacion.findByUsuariomodifico", query = "SELECT t FROM Tipificacion t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Tipificacion.findByFechamodifico", query = "SELECT t FROM Tipificacion t WHERE t.fechamodifico = :fechamodifico")})
public class Tipificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipificacion")
    private Integer idTipificacion;

    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 5)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;

    @Size(max = 3)
    @Column(name = "estado")
    private String estado;

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

    @OneToMany(mappedBy = "idTipificacion", fetch = FetchType.LAZY)
    private List<TblResultadotercero> tblResultadoterceroList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipificacion", fetch = FetchType.LAZY)
    private List<TblLlamada> tblLlamadaList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipificacion", fetch = FetchType.LAZY)
    private List<Subtipificacion> subtipificacionList;

    @OneToMany(mappedBy = "idTipificacion", fetch = FetchType.LAZY)
    private List<TblResultadogestion> tblResultadogestionList;

    public Tipificacion() {
    }

    public Tipificacion(Integer idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public Integer getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(Integer idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
    public List<TblResultadotercero> getTblResultadoterceroList() {
        return tblResultadoterceroList;
    }

    public void setTblResultadoterceroList(List<TblResultadotercero> tblResultadoterceroList) {
        this.tblResultadoterceroList = tblResultadoterceroList;
    }

    @XmlTransient
    public List<TblLlamada> getTblLlamadaList() {
        return tblLlamadaList;
    }

    public void setTblLlamadaList(List<TblLlamada> tblLlamadaList) {
        this.tblLlamadaList = tblLlamadaList;
    }

    @XmlTransient
    public List<Subtipificacion> getSubtipificacionList() {
        return subtipificacionList;
    }

    public void setSubtipificacionList(List<Subtipificacion> subtipificacionList) {
        this.subtipificacionList = subtipificacionList;
    }

    @XmlTransient
    public List<TblResultadogestion> getTblResultadogestionList() {
        return tblResultadogestionList;
    }

    public void setTblResultadogestionList(List<TblResultadogestion> tblResultadogestionList) {
        this.tblResultadogestionList = tblResultadogestionList;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipificacion != null ? idTipificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipificacion)) {
            return false;
        }
        Tipificacion other = (Tipificacion) object;
        if ((this.idTipificacion == null && other.idTipificacion != null) || (this.idTipificacion != null && !this.idTipificacion.equals(other.idTipificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

}

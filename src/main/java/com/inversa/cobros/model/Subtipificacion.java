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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "subtipificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subtipificacion.findAll", query = "SELECT s FROM Subtipificacion s"),
    @NamedQuery(name = "Subtipificacion.findByIdSubtipificacion", query = "SELECT s FROM Subtipificacion s WHERE s.idSubtipificacion = :idSubtipificacion"),
    @NamedQuery(name = "Subtipificacion.findByDescripcion", query = "SELECT s FROM Subtipificacion s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Subtipificacion.findByCodigoCartera", query = "SELECT s FROM Subtipificacion s WHERE s.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "Subtipificacion.findByEstado", query = "SELECT s FROM Subtipificacion s WHERE s.estado = :estado"),
    @NamedQuery(name = "Subtipificacion.findByUsuarioingreso", query = "SELECT s FROM Subtipificacion s WHERE s.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Subtipificacion.findByFechaingreso", query = "SELECT s FROM Subtipificacion s WHERE s.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Subtipificacion.findByUsuariomodifico", query = "SELECT s FROM Subtipificacion s WHERE s.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Subtipificacion.findByFechamodifico", query = "SELECT s FROM Subtipificacion s WHERE s.fechamodifico = :fechamodifico")})
public class Subtipificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subtipificacion")
    private Integer idSubtipificacion;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSubtipificacion", fetch = FetchType.EAGER)
    private List<TblLlamada> tblLlamadaList;
    @JoinColumn(name = "id_tipificacion", referencedColumnName = "id_tipificacion")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Tipificacion idTipificacion;

    public Subtipificacion() {
    }

    public Subtipificacion(Integer idSubtipificacion) {
        this.idSubtipificacion = idSubtipificacion;
    }

    public Integer getIdSubtipificacion() {
        return idSubtipificacion;
    }

    public void setIdSubtipificacion(Integer idSubtipificacion) {
        this.idSubtipificacion = idSubtipificacion;
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
    public List<TblLlamada> getTblLlamadaList() {
        return tblLlamadaList;
    }

    public void setTblLlamadaList(List<TblLlamada> tblLlamadaList) {
        this.tblLlamadaList = tblLlamadaList;
    }

    public Tipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(Tipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubtipificacion != null ? idSubtipificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subtipificacion)) {
            return false;
        }
        Subtipificacion other = (Subtipificacion) object;
        if ((this.idSubtipificacion == null && other.idSubtipificacion != null) || (this.idSubtipificacion != null && !this.idSubtipificacion.equals(other.idSubtipificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

   
    
}

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
@Table(name = "tbl_rolusuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRolusuario.findAll", query = "SELECT t FROM TblRolusuario t"),
    @NamedQuery(name = "TblRolusuario.findByIdRolusuario", query = "SELECT t FROM TblRolusuario t WHERE t.idRolusuario = :idRolusuario"),
    @NamedQuery(name = "TblRolusuario.findByNombre", query = "SELECT t FROM TblRolusuario t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblRolusuario.findByDescripcion", query = "SELECT t FROM TblRolusuario t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblRolusuario.findByEstado", query = "SELECT t FROM TblRolusuario t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblRolusuario.findByUsuarioingreso", query = "SELECT t FROM TblRolusuario t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblRolusuario.findByFechaingreso", query = "SELECT t FROM TblRolusuario t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblRolusuario.findByUsuariomodifico", query = "SELECT t FROM TblRolusuario t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblRolusuario.findByFechamodifico", query = "SELECT t FROM TblRolusuario t WHERE t.fechamodifico = :fechamodifico")})
public class TblRolusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rolusuario")
    private Integer idRolusuario;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(mappedBy = "idRolusuario", fetch = FetchType.EAGER)
    private List<TblUsuario> tblUsuarioList;

    public TblRolusuario() {
    }

    public TblRolusuario(Integer idRolusuario) {
        this.idRolusuario = idRolusuario;
    }

    public Integer getIdRolusuario() {
        return idRolusuario;
    }

    public void setIdRolusuario(Integer idRolusuario) {
        this.idRolusuario = idRolusuario;
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
    public List<TblUsuario> getTblUsuarioList() {
        return tblUsuarioList;
    }

    public void setTblUsuarioList(List<TblUsuario> tblUsuarioList) {
        this.tblUsuarioList = tblUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolusuario != null ? idRolusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRolusuario)) {
            return false;
        }
        TblRolusuario other = (TblRolusuario) object;
        if ((this.idRolusuario == null && other.idRolusuario != null) || (this.idRolusuario != null && !this.idRolusuario.equals(other.idRolusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblRolusuario[ idRolusuario=" + idRolusuario + " ]";
    }
    
}

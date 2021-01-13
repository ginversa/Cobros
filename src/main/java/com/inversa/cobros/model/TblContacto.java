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
@Table(name = "tbl_contacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblContacto.findAll", query = "SELECT t FROM TblContacto t"),
    @NamedQuery(name = "TblContacto.findByIdContacto", query = "SELECT t FROM TblContacto t WHERE t.idContacto = :idContacto"),
    @NamedQuery(name = "TblContacto.findByNombre", query = "SELECT t FROM TblContacto t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblContacto.findByCedula", query = "SELECT t FROM TblContacto t WHERE t.cedula = :cedula"),
    @NamedQuery(name = "TblContacto.findByEstado", query = "SELECT t FROM TblContacto t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblContacto.findByUsuarioingreso", query = "SELECT t FROM TblContacto t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblContacto.findByFechaingreso", query = "SELECT t FROM TblContacto t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblContacto.findByUsuariomodifico", query = "SELECT t FROM TblContacto t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblContacto.findByFechamodifico", query = "SELECT t FROM TblContacto t WHERE t.fechamodifico = :fechamodifico")})
public class TblContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contacto")
    private Integer idContacto;
    @Size(max = 300)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 60)
    @Column(name = "cedula")
    private String cedula;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContacto", fetch = FetchType.EAGER)
    private List<TblTelefono> tblTelefonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContacto", fetch = FetchType.EAGER)
    private List<TblDireccion> tblDireccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContacto", fetch = FetchType.EAGER)
    private List<TblCorreo> tblCorreoList;

    public TblContacto() {
    }

    public TblContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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
    public List<TblTelefono> getTblTelefonoList() {
        return tblTelefonoList;
    }

    public void setTblTelefonoList(List<TblTelefono> tblTelefonoList) {
        this.tblTelefonoList = tblTelefonoList;
    }

    @XmlTransient
    public List<TblDireccion> getTblDireccionList() {
        return tblDireccionList;
    }

    public void setTblDireccionList(List<TblDireccion> tblDireccionList) {
        this.tblDireccionList = tblDireccionList;
    }

    @XmlTransient
    public List<TblCorreo> getTblCorreoList() {
        return tblCorreoList;
    }

    public void setTblCorreoList(List<TblCorreo> tblCorreoList) {
        this.tblCorreoList = tblCorreoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContacto != null ? idContacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblContacto)) {
            return false;
        }
        TblContacto other = (TblContacto) object;
        if ((this.idContacto == null && other.idContacto != null) || (this.idContacto != null && !this.idContacto.equals(other.idContacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblContacto[ idContacto=" + idContacto + " ]";
    }
    
}

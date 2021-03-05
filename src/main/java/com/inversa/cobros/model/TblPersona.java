/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPersona.findAll", query = "SELECT t FROM TblPersona t"),
    @NamedQuery(name = "TblPersona.findByIdPersona", query = "SELECT t FROM TblPersona t WHERE t.idPersona = :idPersona"),
    @NamedQuery(name = "TblPersona.findByNombre", query = "SELECT t FROM TblPersona t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblPersona.findByCedula", query = "SELECT t FROM TblPersona t WHERE t.cedula = :cedula"),
    @NamedQuery(name = "TblPersona.findByUsuarioingreso", query = "SELECT t FROM TblPersona t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblPersona.findByFechaingreso", query = "SELECT t FROM TblPersona t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblPersona.findByUsuariomodifico", query = "SELECT t FROM TblPersona t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblPersona.findByFechamodifico", query = "SELECT t FROM TblPersona t WHERE t.fechamodifico = :fechamodifico")})
public class TblPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Integer idPersona;
    
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 50)
    @Column(name = "cedula")
    private String cedula;
    
    @Size(max = 50)
    @Column(name = "usuarioingreso")
    private String usuarioingreso;
    
    @Size(max = 50)
    @Column(name = "usuariomodifico")
    private String usuariomodifico;    
    
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaingreso;
    
    @Column(name = "fechamodifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodifico;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tblPersona", fetch = FetchType.LAZY)
    private TblUsuario tblUsuario;

    public TblPersona() {
    }

    public TblPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }


    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }


    public Date getFechamodifico() {
        return fechamodifico;
    }

    public void setFechamodifico(Date fechamodifico) {
        this.fechamodifico = fechamodifico;
    }

    public TblUsuario getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(TblUsuario tblUsuario) {
        this.tblUsuario = tblUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPersona)) {
            return false;
        }
        TblPersona other = (TblPersona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblPersona[ idPersona=" + idPersona + " ]";
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

    public String getUsuarioingreso() {
        return usuarioingreso;
    }

    public void setUsuarioingreso(String usuarioingreso) {
        this.usuarioingreso = usuarioingreso;
    }

    public String getUsuariomodifico() {
        return usuariomodifico;
    }

    public void setUsuariomodifico(String usuariomodifico) {
        this.usuariomodifico = usuariomodifico;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblDireccion.findAll", query = "SELECT t FROM TblDireccion t"),
    @NamedQuery(name = "TblDireccion.findByIdDireccion", query = "SELECT t FROM TblDireccion t WHERE t.idDireccion = :idDireccion"),
    @NamedQuery(name = "TblDireccion.findByDireccion", query = "SELECT t FROM TblDireccion t WHERE t.direccion = :direccion"),
    @NamedQuery(name = "TblDireccion.findByRanking", query = "SELECT t FROM TblDireccion t WHERE t.ranking = :ranking"),
    @NamedQuery(name = "TblDireccion.findByEstado", query = "SELECT t FROM TblDireccion t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblDireccion.findByUsuarioingreso", query = "SELECT t FROM TblDireccion t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblDireccion.findByFechaingreso", query = "SELECT t FROM TblDireccion t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblDireccion.findByUsuariomodifico", query = "SELECT t FROM TblDireccion t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblDireccion.findByFechamodifico", query = "SELECT t FROM TblDireccion t WHERE t.fechamodifico = :fechamodifico")})
public class TblDireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_direccion")
    private Integer idDireccion;
    @Size(max = 300)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "ranking")
    private Integer ranking;
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
    @JoinColumn(name = "id_contacto", referencedColumnName = "id_contacto")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblContacto idContacto;

    public TblDireccion() {
    }

    public TblDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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

    public TblContacto getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(TblContacto idContacto) {
        this.idContacto = idContacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDireccion != null ? idDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDireccion)) {
            return false;
        }
        TblDireccion other = (TblDireccion) object;
        if ((this.idDireccion == null && other.idDireccion != null) || (this.idDireccion != null && !this.idDireccion.equals(other.idDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblDireccion[ idDireccion=" + idDireccion + " ]";
    }
    
}

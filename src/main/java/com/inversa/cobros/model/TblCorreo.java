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
@Table(name = "tbl_correo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCorreo.findAll", query = "SELECT t FROM TblCorreo t"),
    @NamedQuery(name = "TblCorreo.findByIdCorreo", query = "SELECT t FROM TblCorreo t WHERE t.idCorreo = :idCorreo"),
    @NamedQuery(name = "TblCorreo.findByCorreo", query = "SELECT t FROM TblCorreo t WHERE t.correo = :correo"),
    @NamedQuery(name = "TblCorreo.findByRanking", query = "SELECT t FROM TblCorreo t WHERE t.ranking = :ranking"),
    @NamedQuery(name = "TblCorreo.findByEstado", query = "SELECT t FROM TblCorreo t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblCorreo.findByUsuarioingreso", query = "SELECT t FROM TblCorreo t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblCorreo.findByFechaingreso", query = "SELECT t FROM TblCorreo t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblCorreo.findByUsuariomodifico", query = "SELECT t FROM TblCorreo t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblCorreo.findByFechamodifico", query = "SELECT t FROM TblCorreo t WHERE t.fechamodifico = :fechamodifico")})
public class TblCorreo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo")
    private Integer idCorreo;
    @Size(max = 100)
    @Column(name = "correo")
    private String correo;
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

    public TblCorreo() {
    }

    public TblCorreo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public Integer getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
        hash += (idCorreo != null ? idCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCorreo)) {
            return false;
        }
        TblCorreo other = (TblCorreo) object;
        if ((this.idCorreo == null && other.idCorreo != null) || (this.idCorreo != null && !this.idCorreo.equals(other.idCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCorreo[ idCorreo=" + idCorreo + " ]";
    }
    
}

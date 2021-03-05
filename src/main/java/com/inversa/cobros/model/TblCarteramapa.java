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
@Table(name = "tbl_carteramapa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCarteramapa.findAll", query = "SELECT t FROM TblCarteramapa t"),
    @NamedQuery(name = "TblCarteramapa.findByIdCarteramapa", query = "SELECT t FROM TblCarteramapa t WHERE t.idCarteramapa = :idCarteramapa"),
    @NamedQuery(name = "TblCarteramapa.findByNombreColumna", query = "SELECT t FROM TblCarteramapa t WHERE t.nombreColumna = :nombreColumna"),
    @NamedQuery(name = "TblCarteramapa.findByTipoDato", query = "SELECT t FROM TblCarteramapa t WHERE t.tipoDato = :tipoDato"),
    @NamedQuery(name = "TblCarteramapa.findByEstado", query = "SELECT t FROM TblCarteramapa t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblCarteramapa.findByUsuarioingreso", query = "SELECT t FROM TblCarteramapa t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblCarteramapa.findByFechaingreso", query = "SELECT t FROM TblCarteramapa t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblCarteramapa.findByUsuariomodifico", query = "SELECT t FROM TblCarteramapa t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblCarteramapa.findByFechamodifico", query = "SELECT t FROM TblCarteramapa t WHERE t.fechamodifico = :fechamodifico")})
public class TblCarteramapa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_carteramapa")
    private Integer idCarteramapa;
    @Size(max = 100)
    @Column(name = "nombre_columna")
    private String nombreColumna;
    @Size(max = 50)
    @Column(name = "tipo_dato")
    private String tipoDato;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCarteramapa", fetch = FetchType.LAZY)
    private List<TblPerfilcartera> tblPerfilcarteraList;

    public TblCarteramapa() {
    }

    public TblCarteramapa(Integer idCarteramapa) {
        this.idCarteramapa = idCarteramapa;
    }

    public Integer getIdCarteramapa() {
        return idCarteramapa;
    }

    public void setIdCarteramapa(Integer idCarteramapa) {
        this.idCarteramapa = idCarteramapa;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
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
    public List<TblPerfilcartera> getTblPerfilcarteraList() {
        return tblPerfilcarteraList;
    }

    public void setTblPerfilcarteraList(List<TblPerfilcartera> tblPerfilcarteraList) {
        this.tblPerfilcarteraList = tblPerfilcarteraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarteramapa != null ? idCarteramapa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCarteramapa)) {
            return false;
        }
        TblCarteramapa other = (TblCarteramapa) object;
        if ((this.idCarteramapa == null && other.idCarteramapa != null) || (this.idCarteramapa != null && !this.idCarteramapa.equals(other.idCarteramapa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCarteramapa[ idCarteramapa=" + idCarteramapa + " ]";
    }
    
}

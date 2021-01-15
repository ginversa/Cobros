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
@Table(name = "tbl_resultadogestion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblResultadogestion.findAll", query = "SELECT t FROM TblResultadogestion t"),
    @NamedQuery(name = "TblResultadogestion.findByIdResultadogestion", query = "SELECT t FROM TblResultadogestion t WHERE t.idResultadogestion = :idResultadogestion"),
    @NamedQuery(name = "TblResultadogestion.findByDescripcion", query = "SELECT t FROM TblResultadogestion t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblResultadogestion.findByCodigo", query = "SELECT t FROM TblResultadogestion t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TblResultadogestion.findByUsuarioingreso", query = "SELECT t FROM TblResultadogestion t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblResultadogestion.findByFechaingreso", query = "SELECT t FROM TblResultadogestion t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblResultadogestion.findByUsuariomodifico", query = "SELECT t FROM TblResultadogestion t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblResultadogestion.findByFechamodifico", query = "SELECT t FROM TblResultadogestion t WHERE t.fechamodifico = :fechamodifico")})
public class TblResultadogestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultadogestion")
    private Integer idResultadogestion;
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 3)
    @Column(name = "codigo")
    private String codigo;
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
    @OneToMany(mappedBy = "idResultadogestion", fetch = FetchType.EAGER)
    private List<TblResultadotercero> tblResultadoterceroList;
    @OneToMany(mappedBy = "idResultadogestion", fetch = FetchType.EAGER)
    private List<TblLlamada> tblLlamadaList;
    @JoinColumn(name = "id_subtipificacion", referencedColumnName = "id_subtipificacion")
    @ManyToOne(fetch = FetchType.EAGER)
    private Subtipificacion idSubtipificacion;
    @JoinColumn(name = "id_tipificacion", referencedColumnName = "id_tipificacion")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipificacion idTipificacion;

    public TblResultadogestion() {
    }

    public TblResultadogestion(Integer idResultadogestion) {
        this.idResultadogestion = idResultadogestion;
    }

    public Integer getIdResultadogestion() {
        return idResultadogestion;
    }

    public void setIdResultadogestion(Integer idResultadogestion) {
        this.idResultadogestion = idResultadogestion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Subtipificacion getIdSubtipificacion() {
        return idSubtipificacion;
    }

    public void setIdSubtipificacion(Subtipificacion idSubtipificacion) {
        this.idSubtipificacion = idSubtipificacion;
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
        hash += (idResultadogestion != null ? idResultadogestion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblResultadogestion)) {
            return false;
        }
        TblResultadogestion other = (TblResultadogestion) object;
        if ((this.idResultadogestion == null && other.idResultadogestion != null) || (this.idResultadogestion != null && !this.idResultadogestion.equals(other.idResultadogestion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
    
}

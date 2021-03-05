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
@Table(name = "tbl_resultadotercero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblResultadotercero.findAll", query = "SELECT t FROM TblResultadotercero t"),
    @NamedQuery(name = "TblResultadotercero.findByIdResultadotercero", query = "SELECT t FROM TblResultadotercero t WHERE t.idResultadotercero = :idResultadotercero"),
    @NamedQuery(name = "TblResultadotercero.findByDescripcion", query = "SELECT t FROM TblResultadotercero t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblResultadotercero.findByCodigo", query = "SELECT t FROM TblResultadotercero t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TblResultadotercero.findByUsuarioingreso", query = "SELECT t FROM TblResultadotercero t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblResultadotercero.findByFechaingreso", query = "SELECT t FROM TblResultadotercero t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblResultadotercero.findByUsuariomodifico", query = "SELECT t FROM TblResultadotercero t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblResultadotercero.findByFechamodifico", query = "SELECT t FROM TblResultadotercero t WHERE t.fechamodifico = :fechamodifico")})
public class TblResultadotercero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultadotercero")
    private Integer idResultadotercero;
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
    @JoinColumn(name = "id_resultadogestion", referencedColumnName = "id_resultadogestion")
    @ManyToOne(fetch = FetchType.LAZY)
    private TblResultadogestion idResultadogestion;
    @JoinColumn(name = "id_tipificacion", referencedColumnName = "id_tipificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipificacion idTipificacion;
    @OneToMany(mappedBy = "idResultadotercero", fetch = FetchType.LAZY)
    private List<TblLlamada> tblLlamadaList;

    public TblResultadotercero() {
    }

    public TblResultadotercero(Integer idResultadotercero) {
        this.idResultadotercero = idResultadotercero;
    }

    public Integer getIdResultadotercero() {
        return idResultadotercero;
    }

    public void setIdResultadotercero(Integer idResultadotercero) {
        this.idResultadotercero = idResultadotercero;
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

    public TblResultadogestion getIdResultadogestion() {
        return idResultadogestion;
    }

    public void setIdResultadogestion(TblResultadogestion idResultadogestion) {
        this.idResultadogestion = idResultadogestion;
    }

    public Tipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(Tipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    @XmlTransient
    public List<TblLlamada> getTblLlamadaList() {
        return tblLlamadaList;
    }

    public void setTblLlamadaList(List<TblLlamada> tblLlamadaList) {
        this.tblLlamadaList = tblLlamadaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResultadotercero != null ? idResultadotercero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblResultadotercero)) {
            return false;
        }
        TblResultadotercero other = (TblResultadotercero) object;
        if ((this.idResultadotercero == null && other.idResultadotercero != null) || (this.idResultadotercero != null && !this.idResultadotercero.equals(other.idResultadotercero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
    
}

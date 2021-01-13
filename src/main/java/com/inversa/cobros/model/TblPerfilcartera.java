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
@Table(name = "tbl_perfilcartera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPerfilcartera.findAll", query = "SELECT t FROM TblPerfilcartera t"),
    @NamedQuery(name = "TblPerfilcartera.findByIdPerfilcartera", query = "SELECT t FROM TblPerfilcartera t WHERE t.idPerfilcartera = :idPerfilcartera"),
    @NamedQuery(name = "TblPerfilcartera.findByCodigoCartera", query = "SELECT t FROM TblPerfilcartera t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblPerfilcartera.findByColumnaExcel", query = "SELECT t FROM TblPerfilcartera t WHERE t.columnaExcel = :columnaExcel"),
    @NamedQuery(name = "TblPerfilcartera.findByEstado", query = "SELECT t FROM TblPerfilcartera t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblPerfilcartera.findByUsuarioingreso", query = "SELECT t FROM TblPerfilcartera t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblPerfilcartera.findByFechaingreso", query = "SELECT t FROM TblPerfilcartera t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblPerfilcartera.findByUsuariomodifico", query = "SELECT t FROM TblPerfilcartera t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblPerfilcartera.findByFechamodifico", query = "SELECT t FROM TblPerfilcartera t WHERE t.fechamodifico = :fechamodifico")})
public class TblPerfilcartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_perfilcartera")
    private Integer idPerfilcartera;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Column(name = "columna_excel")
    private Integer columnaExcel;
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
    @JoinColumn(name = "id_carteramapa", referencedColumnName = "id_carteramapa")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblCarteramapa idCarteramapa;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblCliente idCliente;

    public TblPerfilcartera() {
    }

    public TblPerfilcartera(Integer idPerfilcartera) {
        this.idPerfilcartera = idPerfilcartera;
    }

    public Integer getIdPerfilcartera() {
        return idPerfilcartera;
    }

    public void setIdPerfilcartera(Integer idPerfilcartera) {
        this.idPerfilcartera = idPerfilcartera;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public Integer getColumnaExcel() {
        return columnaExcel;
    }

    public void setColumnaExcel(Integer columnaExcel) {
        this.columnaExcel = columnaExcel;
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

    public TblCarteramapa getIdCarteramapa() {
        return idCarteramapa;
    }

    public void setIdCarteramapa(TblCarteramapa idCarteramapa) {
        this.idCarteramapa = idCarteramapa;
    }

    public TblCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TblCliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfilcartera != null ? idPerfilcartera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPerfilcartera)) {
            return false;
        }
        TblPerfilcartera other = (TblPerfilcartera) object;
        if ((this.idPerfilcartera == null && other.idPerfilcartera != null) || (this.idPerfilcartera != null && !this.idPerfilcartera.equals(other.idPerfilcartera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblPerfilcartera[ idPerfilcartera=" + idPerfilcartera + " ]";
    }
    
}

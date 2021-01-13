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
@Table(name = "tbl_cartera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCartera.findAll", query = "SELECT t FROM TblCartera t"),
    @NamedQuery(name = "TblCartera.findByIdCartera", query = "SELECT t FROM TblCartera t WHERE t.idCartera = :idCartera"),
    @NamedQuery(name = "TblCartera.findByNombre", query = "SELECT t FROM TblCartera t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblCartera.findByCodigoCartera", query = "SELECT t FROM TblCartera t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblCartera.findByUsuarioingreso", query = "SELECT t FROM TblCartera t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblCartera.findByFechaingreso", query = "SELECT t FROM TblCartera t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblCartera.findByUsuariomodifico", query = "SELECT t FROM TblCartera t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblCartera.findByFechamodifico", query = "SELECT t FROM TblCartera t WHERE t.fechamodifico = :fechamodifico")})
public class TblCartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cartera")
    private Long idCartera;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
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
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblCliente idCliente;

    public TblCartera() {
    }

    public TblCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
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

    public TblCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TblCliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCartera != null ? idCartera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCartera)) {
            return false;
        }
        TblCartera other = (TblCartera) object;
        if ((this.idCartera == null && other.idCartera != null) || (this.idCartera != null && !this.idCartera.equals(other.idCartera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCartera[ idCartera=" + idCartera + " ]";
    }
    
}

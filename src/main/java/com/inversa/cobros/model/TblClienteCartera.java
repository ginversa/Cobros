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
@Table(name = "tbl_cliente_cartera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblClienteCartera.findAll", query = "SELECT t FROM TblClienteCartera t"),
    @NamedQuery(name = "TblClienteCartera.findByIdClienteCartera", query = "SELECT t FROM TblClienteCartera t WHERE t.idClienteCartera = :idClienteCartera"),
    @NamedQuery(name = "TblClienteCartera.findByCodigoCartera", query = "SELECT t FROM TblClienteCartera t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblClienteCartera.findByNombre", query = "SELECT t FROM TblClienteCartera t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblClienteCartera.findByEstado", query = "SELECT t FROM TblClienteCartera t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblClienteCartera.findByUsuarioingreso", query = "SELECT t FROM TblClienteCartera t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblClienteCartera.findByFechaingreso", query = "SELECT t FROM TblClienteCartera t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblClienteCartera.findByUsuariomodifico", query = "SELECT t FROM TblClienteCartera t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblClienteCartera.findByFechamodifico", query = "SELECT t FROM TblClienteCartera t WHERE t.fechamodifico = :fechamodifico")})
public class TblClienteCartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente_cartera")
    private Integer idClienteCartera;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
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
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCliente idCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClienteCartera", fetch = FetchType.LAZY)
    private List<TblClienteUsuario> tblClienteUsuarioList;

    public TblClienteCartera() {
    }

    public TblClienteCartera(Integer idClienteCartera) {
        this.idClienteCartera = idClienteCartera;
    }

    public Integer getIdClienteCartera() {
        return idClienteCartera;
    }

    public void setIdClienteCartera(Integer idClienteCartera) {
        this.idClienteCartera = idClienteCartera;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public TblCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TblCliente idCliente) {
        this.idCliente = idCliente;
    }

    @XmlTransient
    public List<TblClienteUsuario> getTblClienteUsuarioList() {
        return tblClienteUsuarioList;
    }

    public void setTblClienteUsuarioList(List<TblClienteUsuario> tblClienteUsuarioList) {
        this.tblClienteUsuarioList = tblClienteUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClienteCartera != null ? idClienteCartera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblClienteCartera)) {
            return false;
        }
        TblClienteCartera other = (TblClienteCartera) object;
        if ((this.idClienteCartera == null && other.idClienteCartera != null) || (this.idClienteCartera != null && !this.idClienteCartera.equals(other.idClienteCartera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblClienteCartera[ idClienteCartera=" + idClienteCartera + " ]";
    }
    
}

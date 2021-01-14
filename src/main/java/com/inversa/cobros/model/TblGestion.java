/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tbl_gestion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblGestion.findAll", query = "SELECT t FROM TblGestion t"),
    @NamedQuery(name = "TblGestion.findByIdGestion", query = "SELECT t FROM TblGestion t WHERE t.idGestion = :idGestion"),
    @NamedQuery(name = "TblGestion.findByCodigoCartera", query = "SELECT t FROM TblGestion t WHERE t.codigoCartera = :codigoCartera order by t.fechaingreso desc"),
    @NamedQuery(name = "TblGestion.findByNombreCliente", query = "SELECT t FROM TblGestion t WHERE t.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "TblGestion.findByDocumento", query = "SELECT t FROM TblGestion t WHERE t.documento = :documento"),
    @NamedQuery(name = "TblGestion.findByCodigoGestor", query = "SELECT t FROM TblGestion t WHERE t.codigoGestor = :codigoGestor order by t.fechaingreso desc"),
    @NamedQuery(name = "TblGestion.findBySaldo", query = "SELECT t FROM TblGestion t WHERE t.saldo = :saldo"),
    @NamedQuery(name = "TblGestion.findByMoneda", query = "SELECT t FROM TblGestion t WHERE t.moneda = :moneda"),
    @NamedQuery(name = "TblGestion.findByFechaGestion", query = "SELECT t FROM TblGestion t WHERE t.fechaGestion = :fechaGestion"),
    @NamedQuery(name = "TblGestion.findByDescripcion", query = "SELECT t FROM TblGestion t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblGestion.findByEstado", query = "SELECT t FROM TblGestion t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblGestion.findByUsuarioingreso", query = "SELECT t FROM TblGestion t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblGestion.findByFechaingreso", query = "SELECT t FROM TblGestion t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblGestion.findByUsuariomodifico", query = "SELECT t FROM TblGestion t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblGestion.findByFechamodifico", query = "SELECT t FROM TblGestion t WHERE t.fechamodifico = :fechamodifico")})
public class TblGestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestion")
    private Long idGestion;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 50)
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    @Size(max = 50)
    @Column(name = "documento")
    private String documento;
    @Size(max = 5)
    @Column(name = "codigo_gestor")
    private String codigoGestor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Size(max = 3)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "fecha_gestion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGestion;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestion", fetch = FetchType.EAGER)
    private List<TblLlamada> tblLlamadaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestion", fetch = FetchType.EAGER)
    private List<TblPromesa> tblPromesaList;

    public TblGestion() {
    }

    public TblGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public Long getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Date getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(Date fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public List<TblLlamada> getTblLlamadaList() {
        return tblLlamadaList;
    }

    public void setTblLlamadaList(List<TblLlamada> tblLlamadaList) {
        this.tblLlamadaList = tblLlamadaList;
    }

    @XmlTransient
    public List<TblPromesa> getTblPromesaList() {
        return tblPromesaList;
    }

    public void setTblPromesaList(List<TblPromesa> tblPromesaList) {
        this.tblPromesaList = tblPromesaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestion != null ? idGestion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblGestion)) {
            return false;
        }
        TblGestion other = (TblGestion) object;
        if ((this.idGestion == null && other.idGestion != null) || (this.idGestion != null && !this.idGestion.equals(other.idGestion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblGestion[ idGestion=" + idGestion + " ]";
    }
    
}

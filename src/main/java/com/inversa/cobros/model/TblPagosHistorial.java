/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_pagos_historial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPagosHistorial.findAll", query = "SELECT t FROM TblPagosHistorial t"),
    @NamedQuery(name = "TblPagosHistorial.findById", query = "SELECT t FROM TblPagosHistorial t WHERE t.id = :id"),
    @NamedQuery(name = "TblPagosHistorial.findByCodigoCartera", query = "SELECT t FROM TblPagosHistorial t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblPagosHistorial.findByNumeroCuenta", query = "SELECT t FROM TblPagosHistorial t WHERE t.numeroCuenta = :numeroCuenta"),    
    @NamedQuery(name = "TblPagosHistorial.findByNumeroCuentaANDCodigoCartera", query = "SELECT t FROM TblPagosHistorial t WHERE t.numeroCuenta = :numeroCuenta AND t.codigoCartera = :codigoCartera ORDER BY t.fechaPago DESC"),    
    @NamedQuery(name = "TblPagosHistorial.findByFechaPago", query = "SELECT t FROM TblPagosHistorial t WHERE t.fechaPago = :fechaPago"),
    @NamedQuery(name = "TblPagosHistorial.findByCodigoGestor", query = "SELECT t FROM TblPagosHistorial t WHERE t.codigoGestor = :codigoGestor"),
    @NamedQuery(name = "TblPagosHistorial.findByTipoPago", query = "SELECT t FROM TblPagosHistorial t WHERE t.tipoPago = :tipoPago"),
    @NamedQuery(name = "TblPagosHistorial.findByTotalColones", query = "SELECT t FROM TblPagosHistorial t WHERE t.totalColones = :totalColones"),
    @NamedQuery(name = "TblPagosHistorial.findByTotalDolares", query = "SELECT t FROM TblPagosHistorial t WHERE t.totalDolares = :totalDolares"),
    @NamedQuery(name = "TblPagosHistorial.findByUsuarioIngreso", query = "SELECT t FROM TblPagosHistorial t WHERE t.usuarioIngreso = :usuarioIngreso"),
    @NamedQuery(name = "TblPagosHistorial.findByFechaIngreso", query = "SELECT t FROM TblPagosHistorial t WHERE t.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "TblPagosHistorial.findByUsuarioModifico", query = "SELECT t FROM TblPagosHistorial t WHERE t.usuarioModifico = :usuarioModifico"),
    @NamedQuery(name = "TblPagosHistorial.findByFechaModifico", query = "SELECT t FROM TblPagosHistorial t WHERE t.fechaModifico = :fechaModifico")})
public class TblPagosHistorial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Size(max = 20)
    @Column(name = "codigo_gestor")
    private String codigoGestor;
    @Size(max = 20)
    @Column(name = "tipo_pago")
    private String tipoPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_colones")
    private BigDecimal totalColones;
    @Column(name = "total_dolares")
    private BigDecimal totalDolares;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usuario_ingreso")
    private String usuarioIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Size(max = 30)
    @Column(name = "usuario_modifico")
    private String usuarioModifico;
    @Column(name = "fecha_modifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifico;

    public TblPagosHistorial() {
    }

    public TblPagosHistorial(Integer id) {
        this.id = id;
    }

    public TblPagosHistorial(Integer id, String codigoCartera, String numeroCuenta, String usuarioIngreso, Date fechaIngreso) {
        this.id = id;
        this.codigoCartera = codigoCartera;
        this.numeroCuenta = numeroCuenta;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public BigDecimal getTotalColones() {
        return totalColones;
    }

    public void setTotalColones(BigDecimal totalColones) {
        this.totalColones = totalColones;
    }

    public BigDecimal getTotalDolares() {
        return totalDolares;
    }

    public void setTotalDolares(BigDecimal totalDolares) {
        this.totalDolares = totalDolares;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(String usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public Date getFechaModifico() {
        return fechaModifico;
    }

    public void setFechaModifico(Date fechaModifico) {
        this.fechaModifico = fechaModifico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPagosHistorial)) {
            return false;
        }
        TblPagosHistorial other = (TblPagosHistorial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblPagosHistorial[ id=" + id + " ]";
    }
    
}

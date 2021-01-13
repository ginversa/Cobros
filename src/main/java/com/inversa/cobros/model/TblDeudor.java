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
import javax.validation.constraints.Size;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_deudor")
@NamedQueries({
    @NamedQuery(name = "TblDeudor.findAll", query = "SELECT t FROM TblDeudor t"),
    @NamedQuery(name = "TblDeudor.findByIdDeudor", query = "SELECT t FROM TblDeudor t WHERE t.idDeudor = :idDeudor"),
    @NamedQuery(name = "TblDeudor.findByCodigoCartera", query = "SELECT t FROM TblDeudor t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblDeudor.findByCodigoGestor", query = "SELECT t FROM TblDeudor t WHERE t.codigoGestor = :codigoGestor"),
    @NamedQuery(name = "TblDeudor.findByDocumento", query = "SELECT t FROM TblDeudor t WHERE t.documento = :documento"),
    @NamedQuery(name = "TblDeudor.findByCarteraGestorDocumento", query = "SELECT t FROM TblDeudor t WHERE t.codigoCartera = :codigoCartera AND t.codigoGestor = :codigoGestor AND t.documento = :documento"),
    @NamedQuery(name = "TblDeudor.findByClienteOperacion", query = "SELECT t FROM TblDeudor t WHERE t.clienteOperacion = :clienteOperacion"),
    @NamedQuery(name = "TblDeudor.findByNombre", query = "SELECT t FROM TblDeudor t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblDeudor.findBySaldo", query = "SELECT t FROM TblDeudor t WHERE t.saldo = :saldo"),
    @NamedQuery(name = "TblDeudor.findBySaldoDolares", query = "SELECT t FROM TblDeudor t WHERE t.saldoDolares = :saldoDolares"),
   // @NamedQuery(name = "TblDeudor.findByTipificacion", query = "SELECT t FROM TblDeudor t WHERE t.tipificacion = :tipificacion"),
    @NamedQuery(name = "TblDeudor.findBySubtipificacion", query = "SELECT t FROM TblDeudor t WHERE t.subtipificacion = :subtipificacion"),
    @NamedQuery(name = "TblDeudor.findByFecUltGest", query = "SELECT t FROM TblDeudor t WHERE t.fecUltGest = :fecUltGest"),
    @NamedQuery(name = "TblDeudor.findByFecUltProm", query = "SELECT t FROM TblDeudor t WHERE t.fecUltProm = :fecUltProm"),
    @NamedQuery(name = "TblDeudor.findByFecUltPago", query = "SELECT t FROM TblDeudor t WHERE t.fecUltPago = :fecUltPago"),
    @NamedQuery(name = "TblDeudor.findByFecIngreso", query = "SELECT t FROM TblDeudor t WHERE t.fecIngreso = :fecIngreso"),
    @NamedQuery(name = "TblDeudor.findByFecActualizacion", query = "SELECT t FROM TblDeudor t WHERE t.fecActualizacion = :fecActualizacion"),
    @NamedQuery(name = "TblDeudor.findByEstatus", query = "SELECT t FROM TblDeudor t WHERE t.estatus = :estatus"),
    @NamedQuery(name = "TblDeudor.findByMarca1", query = "SELECT t FROM TblDeudor t WHERE t.marca1 = :marca1"),
    @NamedQuery(name = "TblDeudor.findByMarca2", query = "SELECT t FROM TblDeudor t WHERE t.marca2 = :marca2"),
    @NamedQuery(name = "TblDeudor.findByMarca3", query = "SELECT t FROM TblDeudor t WHERE t.marca3 = :marca3"),
    @NamedQuery(name = "TblDeudor.findByUsuarioIngreso", query = "SELECT t FROM TblDeudor t WHERE t.usuarioIngreso = :usuarioIngreso"),
    @NamedQuery(name = "TblDeudor.findByFechaIngreso", query = "SELECT t FROM TblDeudor t WHERE t.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "TblDeudor.findByUsuarioModifico", query = "SELECT t FROM TblDeudor t WHERE t.usuarioModifico = :usuarioModifico"),
    @NamedQuery(name = "TblDeudor.findByFechaModifico", query = "SELECT t FROM TblDeudor t WHERE t.fechaModifico = :fechaModifico"),
    @NamedQuery(name = "TblDeudor.findByGestorIfNotExistsGestion", query = "SELECT td FROM TblDeudor td WHERE NOT EXISTS (SELECT tg.documento FROM TblGestion tg INNER JOIN TblPromesa tp on tp.idGestion = tg.idGestion WHERE tg.codigoCartera = td.codigoCartera AND tg.codigoGestor = :codigoGestor AND tg.documento = td.documento) AND td.saldo > 0")
})
public class TblDeudor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_deudor")
    private Integer idDeudor;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 5)
    @Column(name = "codigo_gestor")
    private String codigoGestor;
    @Size(max = 50)
    @Column(name = "documento")
    private String documento;
    @Size(max = 50)
    @Column(name = "cliente_operacion")
    private String clienteOperacion;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "saldo_dolares")
    private BigDecimal saldoDolares;   
    @Size(max = 5)
    @Column(name = "subtipificacion")
    private String subtipificacion;
    @Column(name = "fec_ult_gest")
    @Temporal(TemporalType.DATE)
    private Date fecUltGest;
    @Column(name = "fec_ult_prom")
    @Temporal(TemporalType.DATE)
    private Date fecUltProm;
    @Column(name = "fec_ult_pago")
    @Temporal(TemporalType.DATE)
    private Date fecUltPago;
    @Column(name = "fec_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fecIngreso;
    @Column(name = "fec_actualizacion")
    @Temporal(TemporalType.DATE)
    private Date fecActualizacion;
    @Size(max = 1)
    @Column(name = "estatus")
    private String estatus;
    @Size(max = 250)
    @Column(name = "marca1")
    private String marca1;
    @Size(max = 250)
    @Column(name = "marca2")
    private String marca2;
    @Size(max = 250)
    @Column(name = "marca3")
    private String marca3;
    @Size(max = 50)
    @Column(name = "usuarioingreso")
    private String usuarioIngreso;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Size(max = 50)
    @Column(name = "usuariomodifico")
    private String usuarioModifico;
    @Column(name = "fechamodifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifico;

    public TblDeudor() {
    }

    public TblDeudor(Integer idDeudor) {
        this.idDeudor = idDeudor;
    }

    public Integer getIdDeudor() {
        return idDeudor;
    }

    public void setIdDeudor(Integer idDeudor) {
        this.idDeudor = idDeudor;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getClienteOperacion() {
        return clienteOperacion;
    }

    public void setClienteOperacion(String clienteOperacion) {
        this.clienteOperacion = clienteOperacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldoDolares() {
        return saldoDolares;
    }

    public void setSaldoDolares(BigDecimal saldoDolares) {
        this.saldoDolares = saldoDolares;
    }

    public String getSubtipificacion() {
        return subtipificacion;
    }

    public void setSubtipificacion(String subtipificacion) {
        this.subtipificacion = subtipificacion;
    }

    public Date getFecUltGest() {
        return fecUltGest;
    }

    public void setFecUltGest(Date fecUltGest) {
        this.fecUltGest = fecUltGest;
    }

    public Date getFecUltProm() {
        return fecUltProm;
    }

    public void setFecUltProm(Date fecUltProm) {
        this.fecUltProm = fecUltProm;
    }

    public Date getFecUltPago() {
        return fecUltPago;
    }

    public void setFecUltPago(Date fecUltPago) {
        this.fecUltPago = fecUltPago;
    }

    public Date getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(Date fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public Date getFecActualizacion() {
        return fecActualizacion;
    }

    public void setFecActualizacion(Date fecActualizacion) {
        this.fecActualizacion = fecActualizacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMarca1() {
        return marca1;
    }

    public void setMarca1(String marca1) {
        this.marca1 = marca1;
    }

    public String getMarca2() {
        return marca2;
    }

    public void setMarca2(String marca2) {
        this.marca2 = marca2;
    }

    public String getMarca3() {
        return marca3;
    }

    public void setMarca3(String marca3) {
        this.marca3 = marca3;
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
        hash += (idDeudor != null ? idDeudor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDeudor)) {
            return false;
        }
        TblDeudor other = (TblDeudor) object;
        if ((this.idDeudor == null && other.idDeudor != null) || (this.idDeudor != null && !this.idDeudor.equals(other.idDeudor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblDeudor[ idDeudor=" + idDeudor + " ]";
    }
    
}

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "TblCartera.findById", query = "SELECT t FROM TblCartera t WHERE t.id = :id"),
    @NamedQuery(name = "TblCartera.findByCodigoCartera", query = "SELECT t FROM TblCartera t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblCartera.findByCodigoGestor", query = "SELECT t FROM TblCartera t WHERE t.codigoGestor = :codigoGestor"),
    @NamedQuery(name = "TblCartera.findByCodigoGestorANDCodigoCartera", query = "SELECT t FROM TblCartera t WHERE t.codigoGestor = :codigoGestor AND t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblCartera.findByCarteraGestorIdentificacion", query = "SELECT t FROM TblCartera t WHERE t.codigoCartera = :codigoCartera AND t.codigoGestor = :codigoGestor AND t.identificacion = :identificacion"),
    @NamedQuery(name = "TblCartera.findByNumeroClienteCif", query = "SELECT t FROM TblCartera t WHERE t.numeroClienteCif = :numeroClienteCif"),
    @NamedQuery(name = "TblCartera.findByNombreCliente", query = "SELECT t FROM TblCartera t WHERE t.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "TblCartera.findByIdentificacion", query = "SELECT t FROM TblCartera t WHERE t.identificacion = :identificacion"),
    @NamedQuery(name = "TblCartera.findByNumeroCuenta", query = "SELECT t FROM TblCartera t WHERE t.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "TblCartera.findByNumeroTarjeta", query = "SELECT t FROM TblCartera t WHERE t.numeroTarjeta = :numeroTarjeta"),
    @NamedQuery(name = "TblCartera.findBySaldoColones", query = "SELECT t FROM TblCartera t WHERE t.saldoColones = :saldoColones"),
    @NamedQuery(name = "TblCartera.findByInteresesColones", query = "SELECT t FROM TblCartera t WHERE t.interesesColones = :interesesColones"),
    @NamedQuery(name = "TblCartera.findBySaldoDolares", query = "SELECT t FROM TblCartera t WHERE t.saldoDolares = :saldoDolares"),
    @NamedQuery(name = "TblCartera.findByInteresesDolares", query = "SELECT t FROM TblCartera t WHERE t.interesesDolares = :interesesDolares"),
    @NamedQuery(name = "TblCartera.findByTipoProducto", query = "SELECT t FROM TblCartera t WHERE t.tipoProducto = :tipoProducto"),
    @NamedQuery(name = "TblCartera.findByBucket", query = "SELECT t FROM TblCartera t WHERE t.bucket = :bucket"),
    @NamedQuery(name = "TblCartera.findByDiasMora", query = "SELECT t FROM TblCartera t WHERE t.diasMora = :diasMora"),
    @NamedQuery(name = "TblCartera.findByPlacement", query = "SELECT t FROM TblCartera t WHERE t.placement = :placement"),
    @NamedQuery(name = "TblCartera.findByFechaAsignacion", query = "SELECT t FROM TblCartera t WHERE t.fechaAsignacion = :fechaAsignacion"),
    @NamedQuery(name = "TblCartera.findByEstado", query = "SELECT t FROM TblCartera t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblCartera.findByUsuarioIngreso", query = "SELECT t FROM TblCartera t WHERE t.usuarioIngreso = :usuarioIngreso"),
    @NamedQuery(name = "TblCartera.findByFechaIngreso", query = "SELECT t FROM TblCartera t WHERE t.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "TblCartera.findByUsuarioModifico", query = "SELECT t FROM TblCartera t WHERE t.usuarioModifico = :usuarioModifico"),
    @NamedQuery(name = "TblCartera.findByFechaModifico", query = "SELECT t FROM TblCartera t WHERE t.fechaModifico = :fechaModifico"),
    @NamedQuery(name = "TblCartera.findByLeyusura", query = "SELECT t FROM TblCartera t WHERE t.leyusura = :leyusura")})
public class TblCartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_gestor")
    private String codigoGestor;
    @Size(max = 30)
    @Column(name = "numero_cliente_cif")
    private String numeroClienteCif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    @Size(max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 30)
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    @Size(max = 30)
    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_colones")
    private BigDecimal saldoColones;
    @Column(name = "intereses_colones")
    private BigDecimal interesesColones;
    @Column(name = "saldo_dolares")
    private BigDecimal saldoDolares;
    @Column(name = "intereses_dolares")
    private BigDecimal interesesDolares;
    @Size(max = 20)
    @Column(name = "tipo_producto")
    private String tipoProducto;
    @Size(max = 15)
    @Column(name = "bucket")
    private String bucket;
    @Size(max = 15)
    @Column(name = "dias_mora")
    private String diasMora;
    @Size(max = 15)
    @Column(name = "placement")
    private String placement;
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    @Size(max = 3)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usuario_ingreso")
    private String usuarioIngreso;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Size(max = 30)
    @Column(name = "usuario_modifico")
    private String usuarioModifico;
    @Column(name = "fecha_modifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifico;
    @Size(max = 1)
    @Column(name = "leyusura")
    private String leyusura;
    @JoinColumn(name = "id_moneda_colones", referencedColumnName = "id_moneda")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda idMonedaColones;
    @JoinColumn(name = "id_moneda_dolares", referencedColumnName = "id_moneda")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda idMonedaDolares;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCliente idCliente;

    /* datos a buscar */
    @Transient
    private Date fechaUltimaGestion;

    @Transient
    private TblPromesa ultimaPromesa;

    @Transient
    private String razonMora;

    public TblCartera() {
    }

    public TblCartera(Integer id) {
        this.id = id;
    }

    public TblCartera(Integer id, String codigoCartera, String codigoGestor, String nombreCliente, String usuarioIngreso) {
        this.id = id;
        this.codigoCartera = codigoCartera;
        this.codigoGestor = codigoGestor;
        this.nombreCliente = nombreCliente;
        this.usuarioIngreso = usuarioIngreso;
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

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getNumeroClienteCif() {
        return numeroClienteCif;
    }

    public void setNumeroClienteCif(String numeroClienteCif) {
        this.numeroClienteCif = numeroClienteCif;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public BigDecimal getSaldoColones() {
        return saldoColones;
    }

    public void setSaldoColones(BigDecimal saldoColones) {
        this.saldoColones = saldoColones;
    }

    public BigDecimal getInteresesColones() {
        return interesesColones;
    }

    public void setInteresesColones(BigDecimal interesesColones) {
        this.interesesColones = interesesColones;
    }

    public BigDecimal getSaldoDolares() {
        return saldoDolares;
    }

    public void setSaldoDolares(BigDecimal saldoDolares) {
        this.saldoDolares = saldoDolares;
    }

    public BigDecimal getInteresesDolares() {
        return interesesDolares;
    }

    public void setInteresesDolares(BigDecimal interesesDolares) {
        this.interesesDolares = interesesDolares;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(String diasMora) {
        this.diasMora = diasMora;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getLeyusura() {
        return leyusura;
    }

    public void setLeyusura(String leyusura) {
        this.leyusura = leyusura;
    }

    public Moneda getIdMonedaColones() {
        return idMonedaColones;
    }

    public void setIdMonedaColones(Moneda idMonedaColones) {
        this.idMonedaColones = idMonedaColones;
    }

    public Moneda getIdMonedaDolares() {
        return idMonedaDolares;
    }

    public void setIdMonedaDolares(Moneda idMonedaDolares) {
        this.idMonedaDolares = idMonedaDolares;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCartera)) {
            return false;
        }
        TblCartera other = (TblCartera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCartera[ id=" + id + " ]";
    }

    public Date getFechaUltimaGestion() {
        return fechaUltimaGestion;
    }

    public void setFechaUltimaGestion(Date fechaUltimaGestion) {
        this.fechaUltimaGestion = fechaUltimaGestion;
    }

    public TblPromesa getUltimaPromesa() {
        return ultimaPromesa;
    }

    public void setUltimaPromesa(TblPromesa ultimaPromesa) {
        this.ultimaPromesa = ultimaPromesa;
    }

    public String getRazonMora() {
        return razonMora;
    }

    public void setRazonMora(String razonMora) {
        this.razonMora = razonMora;
    }

}

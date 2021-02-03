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
@Table(name = "tbl_carga_cartera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCargaCartera.findAll", query = "SELECT t FROM TblCargaCartera t"),
    @NamedQuery(name = "TblCargaCartera.findById", query = "SELECT t FROM TblCargaCartera t WHERE t.id = :id"),
    @NamedQuery(name = "TblCargaCartera.findByCodigoCliente", query = "SELECT t FROM TblCargaCartera t WHERE t.codigoCliente = :codigoCliente"),
    @NamedQuery(name = "TblCargaCartera.findByCodigoCartera", query = "SELECT t FROM TblCargaCartera t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblCargaCartera.findByCodigoGestor", query = "SELECT t FROM TblCargaCartera t WHERE t.codigoGestor = :codigoGestor"),
    @NamedQuery(name = "TblCargaCartera.findByNumeroClienteCif", query = "SELECT t FROM TblCargaCartera t WHERE t.numeroClienteCif = :numeroClienteCif"),
    @NamedQuery(name = "TblCargaCartera.findByNombreCliente", query = "SELECT t FROM TblCargaCartera t WHERE t.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "TblCargaCartera.findByIdentificacion", query = "SELECT t FROM TblCargaCartera t WHERE t.identificacion = :identificacion"),
    @NamedQuery(name = "TblCargaCartera.findByNumeroCuenta", query = "SELECT t FROM TblCargaCartera t WHERE t.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "TblCargaCartera.findByNumeroTarjeta", query = "SELECT t FROM TblCargaCartera t WHERE t.numeroTarjeta = :numeroTarjeta"),
    @NamedQuery(name = "TblCargaCartera.findByMontoPrincipalColones", query = "SELECT t FROM TblCargaCartera t WHERE t.montoPrincipalColones = :montoPrincipalColones"),
    @NamedQuery(name = "TblCargaCartera.findByInteresesColones", query = "SELECT t FROM TblCargaCartera t WHERE t.interesesColones = :interesesColones"),
    @NamedQuery(name = "TblCargaCartera.findByCodigoMoneda1", query = "SELECT t FROM TblCargaCartera t WHERE t.codigoMoneda1 = :codigoMoneda1"),
    @NamedQuery(name = "TblCargaCartera.findBySaldoColones", query = "SELECT t FROM TblCargaCartera t WHERE t.saldoColones = :saldoColones"),
    @NamedQuery(name = "TblCargaCartera.findBySaldoDolares", query = "SELECT t FROM TblCargaCartera t WHERE t.saldoDolares = :saldoDolares"),
    @NamedQuery(name = "TblCargaCartera.findByCodigoMoneda2", query = "SELECT t FROM TblCargaCartera t WHERE t.codigoMoneda2 = :codigoMoneda2"),
    @NamedQuery(name = "TblCargaCartera.findByInteresesDolares", query = "SELECT t FROM TblCargaCartera t WHERE t.interesesDolares = :interesesDolares"),
    @NamedQuery(name = "TblCargaCartera.findByTipoProducto", query = "SELECT t FROM TblCargaCartera t WHERE t.tipoProducto = :tipoProducto"),
    @NamedQuery(name = "TblCargaCartera.findByBucket", query = "SELECT t FROM TblCargaCartera t WHERE t.bucket = :bucket"),
    @NamedQuery(name = "TblCargaCartera.findByDiasMora", query = "SELECT t FROM TblCargaCartera t WHERE t.diasMora = :diasMora"),
    @NamedQuery(name = "TblCargaCartera.findByPlacement", query = "SELECT t FROM TblCargaCartera t WHERE t.placement = :placement"),
    @NamedQuery(name = "TblCargaCartera.findByFechaAsignacion", query = "SELECT t FROM TblCargaCartera t WHERE t.fechaAsignacion = :fechaAsignacion"),
    @NamedQuery(name = "TblCargaCartera.findByTelefono01", query = "SELECT t FROM TblCargaCartera t WHERE t.telefono01 = :telefono01"),
    @NamedQuery(name = "TblCargaCartera.findByTelefono02", query = "SELECT t FROM TblCargaCartera t WHERE t.telefono02 = :telefono02"),
    @NamedQuery(name = "TblCargaCartera.findByTelefono03", query = "SELECT t FROM TblCargaCartera t WHERE t.telefono03 = :telefono03"),
    @NamedQuery(name = "TblCargaCartera.findByTelefono04", query = "SELECT t FROM TblCargaCartera t WHERE t.telefono04 = :telefono04"),
    @NamedQuery(name = "TblCargaCartera.findByTelefono05", query = "SELECT t FROM TblCargaCartera t WHERE t.telefono05 = :telefono05"),
    @NamedQuery(name = "TblCargaCartera.findByEstadoCarga", query = "SELECT t FROM TblCargaCartera t WHERE t.estadoCarga = :estadoCarga"),
    @NamedQuery(name = "TblCargaCartera.findByUsuarioIngreso", query = "SELECT t FROM TblCargaCartera t WHERE t.usuarioIngreso = :usuarioIngreso"),
    @NamedQuery(name = "TblCargaCartera.findByFechaIngreso", query = "SELECT t FROM TblCargaCartera t WHERE t.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "TblCargaCartera.findByUsuarioModifico", query = "SELECT t FROM TblCargaCartera t WHERE t.usuarioModifico = :usuarioModifico"),
    @NamedQuery(name = "TblCargaCartera.findByFechaModifico", query = "SELECT t FROM TblCargaCartera t WHERE t.fechaModifico = :fechaModifico")})
public class TblCargaCartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_cliente")
    private String codigoCliente;
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
    @Size(min = 1, max = 80)
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
    @Size(max = 20)
    @Column(name = "monto_principal_colones")
    private String montoPrincipalColones;
    @Size(max = 20)
    @Column(name = "intereses_colones")
    private String interesesColones;
    @Size(max = 3)
    @Column(name = "codigo_moneda1")
    private String codigoMoneda1;
    @Size(max = 20)
    @Column(name = "saldo_colones")
    private String saldoColones;
    @Size(max = 20)
    @Column(name = "saldo_dolares")
    private String saldoDolares;
    @Size(max = 3)
    @Column(name = "codigo_moneda2")
    private String codigoMoneda2;
    @Size(max = 20)
    @Column(name = "intereses_dolares")
    private String interesesDolares;
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
    @Size(max = 20)
    @Column(name = "fecha_asignacion")
    private String fechaAsignacion;
    @Size(max = 20)
    @Column(name = "telefono01")
    private String telefono01;
    @Size(max = 20)
    @Column(name = "telefono02")
    private String telefono02;
    @Size(max = 20)
    @Column(name = "telefono03")
    private String telefono03;
    @Size(max = 20)
    @Column(name = "telefono04")
    private String telefono04;
    @Size(max = 20)
    @Column(name = "telefono05")
    private String telefono05;
    @Size(max = 3)
    @Column(name = "estado_carga")
    private String estadoCarga;
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

    public TblCargaCartera() {
    }

    public TblCargaCartera(Integer id) {
        this.id = id;
    }

    public TblCargaCartera(Integer id, String codigoCliente, String codigoCartera, String codigoGestor, String nombreCliente, String usuarioIngreso, Date fechaIngreso) {
        this.id = id;
        this.codigoCliente = codigoCliente;
        this.codigoCartera = codigoCartera;
        this.codigoGestor = codigoGestor;
        this.nombreCliente = nombreCliente;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
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

    public String getMontoPrincipalColones() {
        return montoPrincipalColones;
    }

    public void setMontoPrincipalColones(String montoPrincipalColones) {
        this.montoPrincipalColones = montoPrincipalColones;
    }

    public String getInteresesColones() {
        return interesesColones;
    }

    public void setInteresesColones(String interesesColones) {
        this.interesesColones = interesesColones;
    }

    public String getCodigoMoneda1() {
        return codigoMoneda1;
    }

    public void setCodigoMoneda1(String codigoMoneda1) {
        this.codigoMoneda1 = codigoMoneda1;
    }

    public String getSaldoColones() {
        return saldoColones;
    }

    public void setSaldoColones(String saldoColones) {
        this.saldoColones = saldoColones;
    }

    public String getSaldoDolares() {
        return saldoDolares;
    }

    public void setSaldoDolares(String saldoDolares) {
        this.saldoDolares = saldoDolares;
    }

    public String getCodigoMoneda2() {
        return codigoMoneda2;
    }

    public void setCodigoMoneda2(String codigoMoneda2) {
        this.codigoMoneda2 = codigoMoneda2;
    }

    public String getInteresesDolares() {
        return interesesDolares;
    }

    public void setInteresesDolares(String interesesDolares) {
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

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getTelefono01() {
        return telefono01;
    }

    public void setTelefono01(String telefono01) {
        this.telefono01 = telefono01;
    }

    public String getTelefono02() {
        return telefono02;
    }

    public void setTelefono02(String telefono02) {
        this.telefono02 = telefono02;
    }

    public String getTelefono03() {
        return telefono03;
    }

    public void setTelefono03(String telefono03) {
        this.telefono03 = telefono03;
    }

    public String getTelefono04() {
        return telefono04;
    }

    public void setTelefono04(String telefono04) {
        this.telefono04 = telefono04;
    }

    public String getTelefono05() {
        return telefono05;
    }

    public void setTelefono05(String telefono05) {
        this.telefono05 = telefono05;
    }

    public String getEstadoCarga() {
        return estadoCarga;
    }

    public void setEstadoCarga(String estadoCarga) {
        this.estadoCarga = estadoCarga;
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
        if (!(object instanceof TblCargaCartera)) {
            return false;
        }
        TblCargaCartera other = (TblCargaCartera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCargaCartera[ id=" + id + " ]";
    }
    
}

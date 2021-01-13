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
@Table(name = "tbl_dato_financiero")
@NamedQueries({
    @NamedQuery(name = "TblDatoFinanciero.findAll", query = "SELECT t FROM TblDatoFinanciero t"),
    @NamedQuery(name = "TblDatoFinanciero.findByIdDatoFinanciero", query = "SELECT t FROM TblDatoFinanciero t WHERE t.idDatoFinanciero = :idDatoFinanciero"),
    @NamedQuery(name = "TblDatoFinanciero.findByCodigoCartera", query = "SELECT t FROM TblDatoFinanciero t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblDatoFinanciero.findByDocumento", query = "SELECT t FROM TblDatoFinanciero t WHERE t.documento = :documento"),
    @NamedQuery(name = "TblDatoFinanciero.findByOperacion", query = "SELECT t FROM TblDatoFinanciero t WHERE t.operacion = :operacion"),
    @NamedQuery(name = "TblDatoFinanciero.findBySaldoTotal", query = "SELECT t FROM TblDatoFinanciero t WHERE t.saldoTotal = :saldoTotal"),
    @NamedQuery(name = "TblDatoFinanciero.findBySaldoVencido", query = "SELECT t FROM TblDatoFinanciero t WHERE t.saldoVencido = :saldoVencido"),
    @NamedQuery(name = "TblDatoFinanciero.findBySaldoCorriente", query = "SELECT t FROM TblDatoFinanciero t WHERE t.saldoCorriente = :saldoCorriente"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora30", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora30 = :mora30"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora60", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora60 = :mora60"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora90", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora90 = :mora90"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora120", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora120 = :mora120"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora150", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora150 = :mora150"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora180", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora180 = :mora180"),
    @NamedQuery(name = "TblDatoFinanciero.findByMora180mas", query = "SELECT t FROM TblDatoFinanciero t WHERE t.mora180mas = :mora180mas"),
    @NamedQuery(name = "TblDatoFinanciero.findByFechaUltPago", query = "SELECT t FROM TblDatoFinanciero t WHERE t.fechaUltPago = :fechaUltPago"),
    @NamedQuery(name = "TblDatoFinanciero.findByValorUltPago", query = "SELECT t FROM TblDatoFinanciero t WHERE t.valorUltPago = :valorUltPago")})
public class TblDatoFinanciero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dato_financiero")
    private Integer idDatoFinanciero;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 50)
    @Column(name = "documento")
    private String documento;
    @Size(max = 50)
    @Column(name = "operacion")
    private String operacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_total")
    private BigDecimal saldoTotal;
    @Column(name = "saldo_vencido")
    private BigDecimal saldoVencido;
    @Column(name = "saldo_corriente")
    private BigDecimal saldoCorriente;
    @Column(name = "mora30")
    private BigDecimal mora30;
    @Column(name = "mora60")
    private BigDecimal mora60;
    @Column(name = "mora90")
    private BigDecimal mora90;
    @Column(name = "mora120")
    private BigDecimal mora120;
    @Column(name = "mora150")
    private BigDecimal mora150;
    @Column(name = "mora180")
    private BigDecimal mora180;
    @Column(name = "mora180mas")
    private BigDecimal mora180mas;
    @Column(name = "fecha_ult_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaUltPago;
    @Column(name = "valor_ult_pago")
    private BigDecimal valorUltPago;

    public TblDatoFinanciero() {
    }

    public TblDatoFinanciero(Integer idDatoFinanciero) {
        this.idDatoFinanciero = idDatoFinanciero;
    }

    public Integer getIdDatoFinanciero() {
        return idDatoFinanciero;
    }

    public void setIdDatoFinanciero(Integer idDatoFinanciero) {
        this.idDatoFinanciero = idDatoFinanciero;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public BigDecimal getSaldoVencido() {
        return saldoVencido;
    }

    public void setSaldoVencido(BigDecimal saldoVencido) {
        this.saldoVencido = saldoVencido;
    }

    public BigDecimal getSaldoCorriente() {
        return saldoCorriente;
    }

    public void setSaldoCorriente(BigDecimal saldoCorriente) {
        this.saldoCorriente = saldoCorriente;
    }

    public BigDecimal getMora30() {
        return mora30;
    }

    public void setMora30(BigDecimal mora30) {
        this.mora30 = mora30;
    }

    public BigDecimal getMora60() {
        return mora60;
    }

    public void setMora60(BigDecimal mora60) {
        this.mora60 = mora60;
    }

    public BigDecimal getMora90() {
        return mora90;
    }

    public void setMora90(BigDecimal mora90) {
        this.mora90 = mora90;
    }

    public BigDecimal getMora120() {
        return mora120;
    }

    public void setMora120(BigDecimal mora120) {
        this.mora120 = mora120;
    }

    public BigDecimal getMora150() {
        return mora150;
    }

    public void setMora150(BigDecimal mora150) {
        this.mora150 = mora150;
    }

    public BigDecimal getMora180() {
        return mora180;
    }

    public void setMora180(BigDecimal mora180) {
        this.mora180 = mora180;
    }

    public BigDecimal getMora180mas() {
        return mora180mas;
    }

    public void setMora180mas(BigDecimal mora180mas) {
        this.mora180mas = mora180mas;
    }

    public Date getFechaUltPago() {
        return fechaUltPago;
    }

    public void setFechaUltPago(Date fechaUltPago) {
        this.fechaUltPago = fechaUltPago;
    }

    public BigDecimal getValorUltPago() {
        return valorUltPago;
    }

    public void setValorUltPago(BigDecimal valorUltPago) {
        this.valorUltPago = valorUltPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDatoFinanciero != null ? idDatoFinanciero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDatoFinanciero)) {
            return false;
        }
        TblDatoFinanciero other = (TblDatoFinanciero) object;
        if ((this.idDatoFinanciero == null && other.idDatoFinanciero != null) || (this.idDatoFinanciero != null && !this.idDatoFinanciero.equals(other.idDatoFinanciero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblDatoFinanciero[ idDatoFinanciero=" + idDatoFinanciero + " ]";
    }
    
}

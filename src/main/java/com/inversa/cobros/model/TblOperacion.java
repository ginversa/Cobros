/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_operacion")
@NamedQueries({
    @NamedQuery(name = "TblOperacion.findAll", query = "SELECT t FROM TblOperacion t"),
    @NamedQuery(name = "TblOperacion.findByIdOperacion", query = "SELECT t FROM TblOperacion t WHERE t.idOperacion = :idOperacion"),
    @NamedQuery(name = "TblOperacion.findByOperacion", query = "SELECT t FROM TblOperacion t WHERE t.operacion = :operacion"),
    @NamedQuery(name = "TblOperacion.findByCodigoCartera", query = "SELECT t FROM TblOperacion t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblOperacion.findByDocumento", query = "SELECT t FROM TblOperacion t WHERE t.documento = :documento"),
    @NamedQuery(name = "TblOperacion.findBySaldo", query = "SELECT t FROM TblOperacion t WHERE t.saldo = :saldo"),
    @NamedQuery(name = "TblOperacion.findBySaldoDolares", query = "SELECT t FROM TblOperacion t WHERE t.saldoDolares = :saldoDolares"),
    @NamedQuery(name = "TblOperacion.findByMora", query = "SELECT t FROM TblOperacion t WHERE t.mora = :mora"),
    @NamedQuery(name = "TblOperacion.findByMarca1", query = "SELECT t FROM TblOperacion t WHERE t.marca1 = :marca1"),
    @NamedQuery(name = "TblOperacion.findByMarca2", query = "SELECT t FROM TblOperacion t WHERE t.marca2 = :marca2"),
    @NamedQuery(name = "TblOperacion.findByMarca3", query = "SELECT t FROM TblOperacion t WHERE t.marca3 = :marca3"),
    @NamedQuery(name = "TblOperacion.findByEstatus", query = "SELECT t FROM TblOperacion t WHERE t.estatus = :estatus")})
public class TblOperacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion")
    private Integer idOperacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "operacion")
    private String operacion;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 50)
    @Column(name = "documento")
    private String documento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "saldo_dolares")
    private BigDecimal saldoDolares;
    @Size(max = 250)
    @Column(name = "mora")
    private String mora;
    @Size(max = 250)
    @Column(name = "marca1")
    private String marca1;
    @Size(max = 250)
    @Column(name = "marca2")
    private String marca2;
    @Size(max = 250)
    @Column(name = "marca3")
    private String marca3;
    @Size(max = 1)
    @Column(name = "estatus")
    private String estatus;

    public TblOperacion() {
    }

    public TblOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public TblOperacion(Integer idOperacion, String operacion) {
        this.idOperacion = idOperacion;
        this.operacion = operacion;
    }

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
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

    public String getMora() {
        return mora;
    }

    public void setMora(String mora) {
        this.mora = mora;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperacion != null ? idOperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblOperacion)) {
            return false;
        }
        TblOperacion other = (TblOperacion) object;
        if ((this.idOperacion == null && other.idOperacion != null) || (this.idOperacion != null && !this.idOperacion.equals(other.idOperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblOperacion[ idOperacion=" + idOperacion + " ]";
    }
    
}

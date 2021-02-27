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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_gestionsaldo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblGestionsaldo.findAll", query = "SELECT t FROM TblGestionsaldo t"),
    @NamedQuery(name = "TblGestionsaldo.findByIdGestionsaldo", query = "SELECT t FROM TblGestionsaldo t WHERE t.idGestionsaldo = :idGestionsaldo"),
    @NamedQuery(name = "TblGestionsaldo.findBySaldoCartera", query = "SELECT t FROM TblGestionsaldo t WHERE t.saldoCartera = :saldoCartera"),
    @NamedQuery(name = "TblGestionsaldo.findByIntereses", query = "SELECT t FROM TblGestionsaldo t WHERE t.intereses = :intereses"),
    @NamedQuery(name = "TblGestionsaldo.findBySaldoGestion", query = "SELECT t FROM TblGestionsaldo t WHERE t.saldoGestion = :saldoGestion"),
    @NamedQuery(name = "TblGestionsaldo.findBySaldoRestante", query = "SELECT t FROM TblGestionsaldo t WHERE t.saldoRestante = :saldoRestante"),
    @NamedQuery(name = "TblGestionsaldo.findByUsuarioingreso", query = "SELECT t FROM TblGestionsaldo t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblGestionsaldo.findByFechaingreso", query = "SELECT t FROM TblGestionsaldo t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblGestionsaldo.findByUsuariomodifico", query = "SELECT t FROM TblGestionsaldo t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblGestionsaldo.findByFechamodifico", query = "SELECT t FROM TblGestionsaldo t WHERE t.fechamodifico = :fechamodifico")})
public class TblGestionsaldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestionsaldo")
    private Integer idGestionsaldo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_cartera")
    private BigDecimal saldoCartera;
    @Column(name = "intereses")
    private BigDecimal intereses;
    @Column(name = "saldo_gestion")
    private BigDecimal saldoGestion;
    @Column(name = "saldo_restante")
    private BigDecimal saldoRestante;
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
    @JoinColumn(name = "id_moneda", referencedColumnName = "id_moneda")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Moneda idMoneda;
    @JoinColumn(name = "id_gestion", referencedColumnName = "id_gestion")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblGestion idGestion;

    public TblGestionsaldo() {
    }

    public TblGestionsaldo(Integer idGestionsaldo) {
        this.idGestionsaldo = idGestionsaldo;
    }

    public Integer getIdGestionsaldo() {
        return idGestionsaldo;
    }

    public void setIdGestionsaldo(Integer idGestionsaldo) {
        this.idGestionsaldo = idGestionsaldo;
    }

    public BigDecimal getSaldoCartera() {
        return saldoCartera;
    }

    public void setSaldoCartera(BigDecimal saldoCartera) {
        this.saldoCartera = saldoCartera;
    }

    public BigDecimal getIntereses() {
        return intereses;
    }

    public void setIntereses(BigDecimal intereses) {
        this.intereses = intereses;
    }

    public BigDecimal getSaldoGestion() {
        return saldoGestion;
    }

    public void setSaldoGestion(BigDecimal saldoGestion) {
        this.saldoGestion = saldoGestion;
    }

    public BigDecimal getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(BigDecimal saldoRestante) {
        this.saldoRestante = saldoRestante;
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

    public Moneda getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public TblGestion getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(TblGestion idGestion) {
        this.idGestion = idGestion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestionsaldo != null ? idGestionsaldo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblGestionsaldo)) {
            return false;
        }
        TblGestionsaldo other = (TblGestionsaldo) object;
        if ((this.idGestionsaldo == null && other.idGestionsaldo != null) || (this.idGestionsaldo != null && !this.idGestionsaldo.equals(other.idGestionsaldo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblGestionsaldo[ idGestionsaldo=" + idGestionsaldo + " ]";
    }
    
}

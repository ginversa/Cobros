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
import javax.persistence.OneToOne;
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
@Table(name = "tbl_cola")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCola.findAll", query = "SELECT t FROM TblCola t"),
    @NamedQuery(name = "TblCola.findByIdCola", query = "SELECT t FROM TblCola t WHERE t.idCola = :idCola"),
    @NamedQuery(name = "TblCola.findByCodigoCartera", query = "SELECT t FROM TblCola t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblCola.findByCodigoGestor", query = "SELECT t FROM TblCola t WHERE t.codigoGestor = :codigoGestor"),
    @NamedQuery(name = "TblCola.findByIdentificacion", query = "SELECT t FROM TblCola t WHERE t.identificacion = :identificacion"),
    @NamedQuery(name = "TblCola.findBySaldoColones", query = "SELECT t FROM TblCola t WHERE t.saldoColones = :saldoColones"),
    @NamedQuery(name = "TblCola.findBySaldoDolares", query = "SELECT t FROM TblCola t WHERE t.saldoDolares = :saldoDolares"),
    @NamedQuery(name = "TblCola.findByEstado", query = "SELECT t FROM TblCola t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblCola.findByUsuarioingreso", query = "SELECT t FROM TblCola t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblCola.findByFechaingreso", query = "SELECT t FROM TblCola t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblCola.findByUsuariomodifico", query = "SELECT t FROM TblCola t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblCola.findByFechamodifico", query = "SELECT t FROM TblCola t WHERE t.fechamodifico = :fechamodifico")})
public class TblCola implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cola")
    private Long idCola;

    @Size(max = 10)
    @Column(name = "codigo_cartera")
    private String codigoCartera;

    @Size(max = 10)
    @Column(name = "codigo_gestor")
    private String codigoGestor;

    @Size(max = 20)
    @Column(name = "identificacion")
    private String identificacion;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_colones")
    private BigDecimal saldoColones;

    @Column(name = "saldo_dolares")
    private BigDecimal saldoDolares;

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

    @JoinColumn(name = "id_filtrocola", referencedColumnName = "id_filtrocola")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblFiltrocola idFiltrocola;

    /*
    @JoinColumn(name = "id_gestion", referencedColumnName = "id_gestion")
    @ManyToOne(fetch = FetchType.LAZY)    
    private TblGestion idGestion;
     */
    public TblCola() {
    }

    public Long getIdCola() {
        return idCola;
    }

    public void setIdCola(Long idCola) {
        this.idCola = idCola;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public BigDecimal getSaldoColones() {
        return saldoColones;
    }

    public void setSaldoColones(BigDecimal saldoColones) {
        this.saldoColones = saldoColones;
    }

    public BigDecimal getSaldoDolares() {
        return saldoDolares;
    }

    public void setSaldoDolares(BigDecimal saldoDolares) {
        this.saldoDolares = saldoDolares;
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

    public TblFiltrocola getIdFiltrocola() {
        return idFiltrocola;
    }

    public void setIdFiltrocola(TblFiltrocola idFiltrocola) {
        this.idFiltrocola = idFiltrocola;
    }

    /*
    public TblGestion getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(TblGestion idGestion) {
        this.idGestion = idGestion;
    }
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCola != null ? idCola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCola)) {
            return false;
        }
        TblCola other = (TblCola) object;
        if ((this.idCola == null && other.idCola != null) || (this.idCola != null && !this.idCola.equals(other.idCola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCola[ idCola=" + idCola + " ]";
    }

}

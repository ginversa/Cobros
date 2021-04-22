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
@Table(name = "tbl_promesa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPromesa.findAll", query = "SELECT t FROM TblPromesa t"),
    @NamedQuery(name = "TblPromesa.findByIdPromesa", query = "SELECT t FROM TblPromesa t WHERE t.idPromesa = :idPromesa"),
    @NamedQuery(name = "TblPromesa.findByOperacion", query = "SELECT t FROM TblPromesa t WHERE t.operacion = :operacion"),
    @NamedQuery(name = "TblPromesa.findByTelefono", query = "SELECT t FROM TblPromesa t WHERE t.telefono = :telefono"),
    @NamedQuery(name = "TblPromesa.findByMtoporcentaje", query = "SELECT t FROM TblPromesa t WHERE t.mtoporcentaje = :mtoporcentaje"),
    @NamedQuery(name = "TblPromesa.findByMtopago", query = "SELECT t FROM TblPromesa t WHERE t.mtopago = :mtopago"),
    @NamedQuery(name = "TblPromesa.findByFechaPago", query = "SELECT t FROM TblPromesa t WHERE t.fechaPago = :fechaPago"),
    @NamedQuery(name = "TblPromesa.findByFechaPagoAndUsuarioIngreso", query = "SELECT t FROM TblPromesa t WHERE t.fechaPago = :fechaPago AND t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblPromesa.findByUsuarioingreso", query = "SELECT t FROM TblPromesa t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblPromesa.findByFechaingreso", query = "SELECT t FROM TblPromesa t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblPromesa.findByUsuariomodifico", query = "SELECT t FROM TblPromesa t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblPromesa.findByFechamodifico", query = "SELECT t FROM TblPromesa t WHERE t.fechamodifico = :fechamodifico")})
public class TblPromesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_promesa")
    private Long idPromesa;
    
    @Size(max = 50)
    @Column(name = "operacion")
    private String operacion;
    
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mtoporcentaje")
    private BigDecimal mtoporcentaje;
    
    @Column(name = "mtopago")
    private BigDecimal mtopago;
    
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    
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
    
    @JoinColumn(name = "idarreglopago", referencedColumnName = "idarreglopago")
    @ManyToOne(fetch = FetchType.LAZY)
    private Arreglopago idarreglopago;
    
    @JoinColumn(name = "idestadopromesa", referencedColumnName = "idestadopromesa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Estadopromesa idestadopromesa;
    
    @JoinColumn(name = "id_moneda", referencedColumnName = "id_moneda")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda idMoneda;
    
    @JoinColumn(name = "idtipodescuento", referencedColumnName = "idtipodescuento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipodescuento idtipodescuento;

    @JoinColumn(name = "id_gestion", referencedColumnName = "id_gestion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblGestion idGestion;

    @JoinColumn(name = "id_llamada", referencedColumnName = "id_llamada")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblLlamada idLlamada;

    public TblPromesa() {
    }

    public TblPromesa(Long idPromesa) {
        this.idPromesa = idPromesa;
    }

    public Long getIdPromesa() {
        return idPromesa;
    }

    public void setIdPromesa(Long idPromesa) {
        this.idPromesa = idPromesa;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getMtoporcentaje() {
        return mtoporcentaje;
    }

    public void setMtoporcentaje(BigDecimal mtoporcentaje) {
        this.mtoporcentaje = mtoporcentaje;
    }

    public BigDecimal getMtopago() {
        return mtopago;
    }

    public void setMtopago(BigDecimal mtopago) {
        this.mtopago = mtopago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    public Arreglopago getIdarreglopago() {
        return idarreglopago;
    }

    public void setIdarreglopago(Arreglopago idarreglopago) {
        this.idarreglopago = idarreglopago;
    }

    public Estadopromesa getIdestadopromesa() {
        return idestadopromesa;
    }

    public void setIdestadopromesa(Estadopromesa idestadopromesa) {
        this.idestadopromesa = idestadopromesa;
    }

    public Moneda getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Tipodescuento getIdtipodescuento() {
        return idtipodescuento;
    }

    public void setIdtipodescuento(Tipodescuento idtipodescuento) {
        this.idtipodescuento = idtipodescuento;
    }

    public TblGestion getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(TblGestion idGestion) {
        this.idGestion = idGestion;
    }

    public TblLlamada getIdLlamada() {
        return idLlamada;
    }

    public void setIdLlamada(TblLlamada idLlamada) {
        this.idLlamada = idLlamada;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPromesa != null ? idPromesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPromesa)) {
            return false;
        }
        TblPromesa other = (TblPromesa) object;
        if ((this.idPromesa == null && other.idPromesa != null) || (this.idPromesa != null && !this.idPromesa.equals(other.idPromesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblPromesa[ idPromesa=" + idPromesa + " ]";
    }

}

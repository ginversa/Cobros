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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_filtrocola")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFiltrocola.findAll", query = "SELECT t FROM TblFiltrocola t"),
    @NamedQuery(name = "TblFiltrocola.findByIdFiltrocola", query = "SELECT t FROM TblFiltrocola t WHERE t.idFiltrocola = :idFiltrocola"),
    @NamedQuery(name = "TblFiltrocola.findByNombre", query = "SELECT t FROM TblFiltrocola t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblFiltrocola.findByPoseetrabajo", query = "SELECT t FROM TblFiltrocola t WHERE t.poseetrabajo = :poseetrabajo"),
    @NamedQuery(name = "TblFiltrocola.findByRangobalance", query = "SELECT t FROM TblFiltrocola t WHERE t.rangobalance = :rangobalance"),
    @NamedQuery(name = "TblFiltrocola.findByCantidad", query = "SELECT t FROM TblFiltrocola t WHERE t.cantidad = :cantidad"),
    @NamedQuery(name = "TblFiltrocola.findByTiposalario", query = "SELECT t FROM TblFiltrocola t WHERE t.tiposalario = :tiposalario"),
    @NamedQuery(name = "TblFiltrocola.findByDiasSingestion", query = "SELECT t FROM TblFiltrocola t WHERE t.diasSingestion = :diasSingestion"),
    @NamedQuery(name = "TblFiltrocola.findByUltimopagoMes", query = "SELECT t FROM TblFiltrocola t WHERE t.ultimopagoMes = :ultimopagoMes"),
    @NamedQuery(name = "TblFiltrocola.findByUltimapromesaMes", query = "SELECT t FROM TblFiltrocola t WHERE t.ultimapromesaMes = :ultimapromesaMes"),
    @NamedQuery(name = "TblFiltrocola.findByCodigoCartera", query = "SELECT t FROM TblFiltrocola t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblFiltrocola.findByEstado", query = "SELECT t FROM TblFiltrocola t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblFiltrocola.findByFechaaplicainicial", query = "SELECT t FROM TblFiltrocola t WHERE t.fechaaplicainicial = :fechaaplicainicial"),
    @NamedQuery(name = "TblFiltrocola.findByFechaaplicafinal", query = "SELECT t FROM TblFiltrocola t WHERE t.fechaaplicafinal = :fechaaplicafinal"),
    @NamedQuery(name = "TblFiltrocola.findByUsuarioingreso", query = "SELECT t FROM TblFiltrocola t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblFiltrocola.findByFechaingreso", query = "SELECT t FROM TblFiltrocola t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblFiltrocola.findByUsuariomodifico", query = "SELECT t FROM TblFiltrocola t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblFiltrocola.findByFechamodifico", query = "SELECT t FROM TblFiltrocola t WHERE t.fechamodifico = :fechamodifico")})
public class TblFiltrocola implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filtrocola")
    private Long idFiltrocola;
    @Size(max = 250)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "poseetrabajo")
    private Integer poseetrabajo;
    @Column(name = "rangobalance")
    private Integer rangobalance;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Size(max = 20)
    @Column(name = "tiposalario")
    private String tiposalario;
    @Size(max = 20)
    @Column(name = "dias_singestion")
    private String diasSingestion;
    @Size(max = 20)
    @Column(name = "ultimopago_mes")
    private String ultimopagoMes;
    @Size(max = 20)
    @Column(name = "ultimapromesa_mes")
    private String ultimapromesaMes;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 3)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechaaplicainicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaaplicainicial;
    @Column(name = "fechaaplicafinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaaplicafinal;
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
    @JoinColumn(name = "idrazonmora", referencedColumnName = "idrazonmora")
    @ManyToOne(fetch = FetchType.LAZY)
    private Razonmora idrazonmora;
    @JoinColumn(name = "id_subtipificacion", referencedColumnName = "id_subtipificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subtipificacion idSubtipificacion;
    @JoinColumn(name = "id_tipificacion", referencedColumnName = "id_tipificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipificacion idTipificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFiltrocola", fetch = FetchType.LAZY)
    private List<TblCola> tblColaList;

    public TblFiltrocola() {
    }

    public Long getIdFiltrocola() {
        return idFiltrocola;
    }

    public void setIdFiltrocola(Long idFiltrocola) {
        this.idFiltrocola = idFiltrocola;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPoseetrabajo() {
        return poseetrabajo;
    }

    public void setPoseetrabajo(Integer poseetrabajo) {
        this.poseetrabajo = poseetrabajo;
    }

    public Integer getRangobalance() {
        return rangobalance;
    }

    public void setRangobalance(Integer rangobalance) {
        this.rangobalance = rangobalance;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTiposalario() {
        return tiposalario;
    }

    public void setTiposalario(String tiposalario) {
        this.tiposalario = tiposalario;
    }

    public String getDiasSingestion() {
        return diasSingestion;
    }

    public void setDiasSingestion(String diasSingestion) {
        this.diasSingestion = diasSingestion;
    }

    public String getUltimopagoMes() {
        return ultimopagoMes;
    }

    public void setUltimopagoMes(String ultimopagoMes) {
        this.ultimopagoMes = ultimopagoMes;
    }

    public String getUltimapromesaMes() {
        return ultimapromesaMes;
    }

    public void setUltimapromesaMes(String ultimapromesaMes) {
        this.ultimapromesaMes = ultimapromesaMes;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaaplicainicial() {
        return fechaaplicainicial;
    }

    public void setFechaaplicainicial(Date fechaaplicainicial) {
        this.fechaaplicainicial = fechaaplicainicial;
    }

    public Date getFechaaplicafinal() {
        return fechaaplicafinal;
    }

    public void setFechaaplicafinal(Date fechaaplicafinal) {
        this.fechaaplicafinal = fechaaplicafinal;
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

    public Razonmora getIdrazonmora() {
        return idrazonmora;
    }

    public void setIdrazonmora(Razonmora idrazonmora) {
        this.idrazonmora = idrazonmora;
    }

    public Subtipificacion getIdSubtipificacion() {
        return idSubtipificacion;
    }

    public void setIdSubtipificacion(Subtipificacion idSubtipificacion) {
        this.idSubtipificacion = idSubtipificacion;
    }

    public Tipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(Tipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    @XmlTransient
    public List<TblCola> getTblColaList() {
        return tblColaList;
    }

    public void setTblColaList(List<TblCola> tblColaList) {
        this.tblColaList = tblColaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFiltrocola != null ? idFiltrocola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblFiltrocola)) {
            return false;
        }
        TblFiltrocola other = (TblFiltrocola) object;
        if ((this.idFiltrocola == null && other.idFiltrocola != null) || (this.idFiltrocola != null && !this.idFiltrocola.equals(other.idFiltrocola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblFiltrocola[ idFiltrocola=" + idFiltrocola + " ]";
    }
    
}

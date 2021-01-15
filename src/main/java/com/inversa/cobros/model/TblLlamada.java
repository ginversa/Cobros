/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
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
@Table(name = "tbl_llamada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLlamada.findAll", query = "SELECT t FROM TblLlamada t"),
    @NamedQuery(name = "TblLlamada.findByIdLlamada", query = "SELECT t FROM TblLlamada t WHERE t.idLlamada = :idLlamada"),
    @NamedQuery(name = "TblLlamada.findByCallLogId", query = "SELECT t FROM TblLlamada t WHERE t.callLogId = :callLogId"),
    @NamedQuery(name = "TblLlamada.findByDateIni", query = "SELECT t FROM TblLlamada t WHERE t.dateIni = :dateIni"),
    @NamedQuery(name = "TblLlamada.findByDateEnd", query = "SELECT t FROM TblLlamada t WHERE t.dateEnd = :dateEnd"),
    @NamedQuery(name = "TblLlamada.findByCallFromNumber", query = "SELECT t FROM TblLlamada t WHERE t.callFromNumber = :callFromNumber"),
    @NamedQuery(name = "TblLlamada.findByCallToNumber", query = "SELECT t FROM TblLlamada t WHERE t.callToNumber = :callToNumber"),
    @NamedQuery(name = "TblLlamada.findByDialstatus", query = "SELECT t FROM TblLlamada t WHERE t.dialstatus = :dialstatus"),
    @NamedQuery(name = "TblLlamada.findByCallLength", query = "SELECT t FROM TblLlamada t WHERE t.callLength = :callLength"),
    @NamedQuery(name = "TblLlamada.findByConversationLength", query = "SELECT t FROM TblLlamada t WHERE t.conversationLength = :conversationLength"),
    @NamedQuery(name = "TblLlamada.findByEstado", query = "SELECT t FROM TblLlamada t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblLlamada.findByUsuarioingreso", query = "SELECT t FROM TblLlamada t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblLlamada.findByFechaingreso", query = "SELECT t FROM TblLlamada t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblLlamada.findByUsuariomodifico", query = "SELECT t FROM TblLlamada t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblLlamada.findByFechamodifico", query = "SELECT t FROM TblLlamada t WHERE t.fechamodifico = :fechamodifico")})
public class TblLlamada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_llamada")
    private Long idLlamada;
    @Size(max = 2147483647)
    @Column(name = "call_log_id")
    private String callLogId;
    @Column(name = "date_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIni;
    @Column(name = "date_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @Size(max = 5)
    @Column(name = "call_from_number")
    private String callFromNumber;
    @Size(max = 50)
    @Column(name = "call_to_number")
    private String callToNumber;
    @Size(max = 50)
    @Column(name = "dialstatus")
    private String dialstatus;
    @Column(name = "call_length")
    @Temporal(TemporalType.TIMESTAMP)
    private Date callLength;
    @Column(name = "conversation_length")
    private Integer conversationLength;
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
    @JoinColumn(name = "idrazonmora", referencedColumnName = "idrazonmora")
    @ManyToOne(fetch = FetchType.EAGER)
    private Razonmora idrazonmora;
    @JoinColumn(name = "id_subtipificacion", referencedColumnName = "id_subtipificacion")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Subtipificacion idSubtipificacion;
    @JoinColumn(name = "id_gestion", referencedColumnName = "id_gestion")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblGestion idGestion;
    @JoinColumn(name = "id_resultadogestion", referencedColumnName = "id_resultadogestion")
    @ManyToOne(fetch = FetchType.EAGER)
    private TblResultadogestion idResultadogestion;
    @JoinColumn(name = "id_resultadotercero", referencedColumnName = "id_resultadotercero")
    @ManyToOne(fetch = FetchType.EAGER)
    private TblResultadotercero idResultadotercero;
    @JoinColumn(name = "id_tipificacion", referencedColumnName = "id_tipificacion")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Tipificacion idTipificacion;
    @JoinColumn(name = "id_tipotelefono", referencedColumnName = "id_tipotelefono")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipotelefono idTipotelefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLlamada", fetch = FetchType.EAGER)
    private List<TblPromesa> tblPromesaList;

    public TblLlamada() {
    }

    public TblLlamada(Long idLlamada) {
        this.idLlamada = idLlamada;
    }

    public Long getIdLlamada() {
        return idLlamada;
    }

    public void setIdLlamada(Long idLlamada) {
        this.idLlamada = idLlamada;
    }

    public String getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(String callLogId) {
        this.callLogId = callLogId;
    }

    public Date getDateIni() {
        return dateIni;
    }

    public void setDateIni(Date dateIni) {
        this.dateIni = dateIni;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCallFromNumber() {
        return callFromNumber;
    }

    public void setCallFromNumber(String callFromNumber) {
        this.callFromNumber = callFromNumber;
    }

    public String getCallToNumber() {
        return callToNumber;
    }

    public void setCallToNumber(String callToNumber) {
        this.callToNumber = callToNumber;
    }

    public String getDialstatus() {
        return dialstatus;
    }

    public void setDialstatus(String dialstatus) {
        this.dialstatus = dialstatus;
    }

    public Date getCallLength() {
        return callLength;
    }

    public void setCallLength(Date callLength) {
        this.callLength = callLength;
    }

    public Integer getConversationLength() {
        return conversationLength;
    }

    public void setConversationLength(Integer conversationLength) {
        this.conversationLength = conversationLength;
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

    public TblGestion getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(TblGestion idGestion) {
        this.idGestion = idGestion;
    }

    public TblResultadogestion getIdResultadogestion() {
        return idResultadogestion;
    }

    public void setIdResultadogestion(TblResultadogestion idResultadogestion) {
        this.idResultadogestion = idResultadogestion;
    }

    public TblResultadotercero getIdResultadotercero() {
        return idResultadotercero;
    }

    public void setIdResultadotercero(TblResultadotercero idResultadotercero) {
        this.idResultadotercero = idResultadotercero;
    }

    public Tipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(Tipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public Tipotelefono getIdTipotelefono() {
        return idTipotelefono;
    }

    public void setIdTipotelefono(Tipotelefono idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }

    @XmlTransient
    public List<TblPromesa> getTblPromesaList() {
        return tblPromesaList;
    }

    public void setTblPromesaList(List<TblPromesa> tblPromesaList) {
        this.tblPromesaList = tblPromesaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLlamada != null ? idLlamada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblLlamada)) {
            return false;
        }
        TblLlamada other = (TblLlamada) object;
        if ((this.idLlamada == null && other.idLlamada != null) || (this.idLlamada != null && !this.idLlamada.equals(other.idLlamada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblLlamada[ idLlamada=" + idLlamada + " ]";
    }
    
}

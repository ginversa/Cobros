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
@Table(name = "tbl_telefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTelefono.findAll", query = "SELECT t FROM TblTelefono t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblTelefono.findByIdTelefono", query = "SELECT t FROM TblTelefono t WHERE t.idTelefono = :idTelefono AND t.estado = :estado"),
    @NamedQuery(name = "TblTelefono.findByTelefono", query = "SELECT t FROM TblTelefono t WHERE t.telefono = :telefono AND t.estado = :estado"),
    @NamedQuery(name = "TblTelefono.findByRanking", query = "SELECT t FROM TblTelefono t WHERE t.ranking = :ranking AND t.estado = :estado"),
    @NamedQuery(name = "TblTelefono.findByEstado", query = "SELECT t FROM TblTelefono t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblTelefono.findByUsuarioingreso", query = "SELECT t FROM TblTelefono t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblTelefono.findByFechaingreso", query = "SELECT t FROM TblTelefono t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblTelefono.findByUsuariomodifico", query = "SELECT t FROM TblTelefono t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblTelefono.findByFechamodifico", query = "SELECT t FROM TblTelefono t WHERE t.fechamodifico = :fechamodifico")})
public class TblTelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_telefono")
    private Integer idTelefono;
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "ranking")
    private Integer ranking;
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
    @JoinColumn(name = "id_contacto", referencedColumnName = "id_contacto")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblContacto idContacto;
    @JoinColumn(name = "id_tipotelefono", referencedColumnName = "id_tipotelefono")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipotelefono idTipotelefono;

    public TblTelefono() {
    }

    public TblTelefono(Integer idTelefono) {
        this.idTelefono = idTelefono;
    }

    public Integer getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(Integer idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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

    public TblContacto getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(TblContacto idContacto) {
        this.idContacto = idContacto;
    }

    public Tipotelefono getIdTipotelefono() {
        return idTipotelefono;
    }

    public void setIdTipotelefono(Tipotelefono idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelefono != null ? idTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTelefono)) {
            return false;
        }
        TblTelefono other = (TblTelefono) object;
        if ((this.idTelefono == null && other.idTelefono != null) || (this.idTelefono != null && !this.idTelefono.equals(other.idTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblTelefono[ idTelefono=" + idTelefono + " ]";
    }
    
}

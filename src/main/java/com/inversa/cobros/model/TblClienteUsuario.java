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
@Table(name = "tbl_cliente_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblClienteUsuario.findAll", query = "SELECT t FROM TblClienteUsuario t"),
    @NamedQuery(name = "TblClienteUsuario.findByIdClienteUsuario", query = "SELECT t FROM TblClienteUsuario t WHERE t.idClienteUsuario = :idClienteUsuario"),
    @NamedQuery(name = "TblClienteUsuario.findByUsuarioingreso", query = "SELECT t FROM TblClienteUsuario t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblClienteUsuario.findByFechaingreso", query = "SELECT t FROM TblClienteUsuario t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblClienteUsuario.findByUsuariomodifico", query = "SELECT t FROM TblClienteUsuario t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblClienteUsuario.findByFechamodifico", query = "SELECT t FROM TblClienteUsuario t WHERE t.fechamodifico = :fechamodifico")})
public class TblClienteUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente_usuario")
    private Integer idClienteUsuario;
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
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(fetch = FetchType.EAGER)
    private TblCliente idCliente;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(fetch = FetchType.EAGER)
    private TblUsuario idPersona;
    
    @Size(max = 5)
    @Column(name = "codigo_cartera")    
    private String codigo_cartera;

    public TblClienteUsuario() {
    }

    public TblClienteUsuario(Integer idClienteUsuario) {
        this.idClienteUsuario = idClienteUsuario;
    }

    public Integer getIdClienteUsuario() {
        return idClienteUsuario;
    }

    public void setIdClienteUsuario(Integer idClienteUsuario) {
        this.idClienteUsuario = idClienteUsuario;
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

    public TblCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TblCliente idCliente) {
        this.idCliente = idCliente;
    }

    public TblUsuario getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(TblUsuario idPersona) {
        this.idPersona = idPersona;
    }

    public String getCodigo_cartera() {
        return codigo_cartera;
    }

    public void setCodigo_cartera(String codigo_cartera) {
        this.codigo_cartera = codigo_cartera;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClienteUsuario != null ? idClienteUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblClienteUsuario)) {
            return false;
        }
        TblClienteUsuario other = (TblClienteUsuario) object;
        if ((this.idClienteUsuario == null && other.idClienteUsuario != null) || (this.idClienteUsuario != null && !this.idClienteUsuario.equals(other.idClienteUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblClienteUsuario[ idClienteUsuario=" + idClienteUsuario + " ]";
    }
    
}

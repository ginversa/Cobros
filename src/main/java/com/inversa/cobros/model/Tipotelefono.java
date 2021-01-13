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
@Table(name = "tipotelefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipotelefono.findAll", query = "SELECT t FROM Tipotelefono t"),
    @NamedQuery(name = "Tipotelefono.findByIdTipotelefono", query = "SELECT t FROM Tipotelefono t WHERE t.idTipotelefono = :idTipotelefono"),
    @NamedQuery(name = "Tipotelefono.findByDescripcion", query = "SELECT t FROM Tipotelefono t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipotelefono.findByCodigo", query = "SELECT t FROM Tipotelefono t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Tipotelefono.findByEstado", query = "SELECT t FROM Tipotelefono t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tipotelefono.findByUsuarioingreso", query = "SELECT t FROM Tipotelefono t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Tipotelefono.findByFechaingreso", query = "SELECT t FROM Tipotelefono t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Tipotelefono.findByUsuariomodifico", query = "SELECT t FROM Tipotelefono t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Tipotelefono.findByFechamodifico", query = "SELECT t FROM Tipotelefono t WHERE t.fechamodifico = :fechamodifico")})
public class Tipotelefono implements Serializable {

    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 3)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 3)
    @Column(name = "estado")
    private String estado;
    @Size(max = 50)
    @Column(name = "usuarioingreso")
    private String usuarioingreso;
    @Size(max = 50)
    @Column(name = "usuariomodifico")
    private String usuariomodifico;
    @OneToMany(mappedBy = "idTipotelefono", fetch = FetchType.EAGER)
    private List<TblTelefono> tblTelefonoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipotelefono")
    private Integer idTipotelefono;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaingreso;
    @Column(name = "fechamodifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodifico;
    
/*    
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "idTipotelefono", fetch = FetchType.EAGER)
    private List<TblLlamada> tblLlamadaList;
*/
    public Tipotelefono() {
    }

    public Tipotelefono(Integer idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }

    public Integer getIdTipotelefono() {
        return idTipotelefono;
    }

    public void setIdTipotelefono(Integer idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }


    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }


    public Date getFechamodifico() {
        return fechamodifico;
    }

    public void setFechamodifico(Date fechamodifico) {
        this.fechamodifico = fechamodifico;
    }
/*
    @XmlTransient
    public List<TblLlamada> getTblLlamadaList() {
        return tblLlamadaList;
    }

    public void setTblLlamadaList(List<TblLlamada> tblLlamadaList) {
        this.tblLlamadaList = tblLlamadaList;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipotelefono != null ? idTipotelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipotelefono)) {
            return false;
        }
        Tipotelefono other = (Tipotelefono) object;
        if ((this.idTipotelefono == null && other.idTipotelefono != null) || (this.idTipotelefono != null && !this.idTipotelefono.equals(other.idTipotelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getUsuariomodifico() {
        return usuariomodifico;
    }

    public void setUsuariomodifico(String usuariomodifico) {
        this.usuariomodifico = usuariomodifico;
    }

    @XmlTransient
    public List<TblTelefono> getTblTelefonoList() {
        return tblTelefonoList;
    }

    public void setTblTelefonoList(List<TblTelefono> tblTelefonoList) {
        this.tblTelefonoList = tblTelefonoList;
    }


    
}

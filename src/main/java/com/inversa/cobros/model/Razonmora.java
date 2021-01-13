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

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "razonmora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Razonmora.findAll", query = "SELECT r FROM Razonmora r"),
    @NamedQuery(name = "Razonmora.findByIdrazonmora", query = "SELECT r FROM Razonmora r WHERE r.idrazonmora = :idrazonmora"),
    @NamedQuery(name = "Razonmora.findByDescripcion", query = "SELECT r FROM Razonmora r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "Razonmora.findByCodigoCartera", query = "SELECT r FROM Razonmora r WHERE r.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "Razonmora.findByEstado", query = "SELECT r FROM Razonmora r WHERE r.estado = :estado"),
    @NamedQuery(name = "Razonmora.findByUsuarioingreso", query = "SELECT r FROM Razonmora r WHERE r.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Razonmora.findByFechaingreso", query = "SELECT r FROM Razonmora r WHERE r.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Razonmora.findByUsuariomodifico", query = "SELECT r FROM Razonmora r WHERE r.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Razonmora.findByFechamodifico", query = "SELECT r FROM Razonmora r WHERE r.fechamodifico = :fechamodifico")})
public class Razonmora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrazonmora")
    private Integer idrazonmora;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
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
    
  /*  
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "idrazonmora", fetch = FetchType.EAGER)
    private List<TblLlamada> tblLlamadaList;
*/
    
    public Razonmora() {
    }

    public Razonmora(Integer idrazonmora) {
        this.idrazonmora = idrazonmora;
    }

    public Integer getIdrazonmora() {
        return idrazonmora;
    }

    public void setIdrazonmora(Integer idrazonmora) {
        this.idrazonmora = idrazonmora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idrazonmora != null ? idrazonmora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Razonmora)) {
            return false;
        }
        Razonmora other = (Razonmora) object;
        if ((this.idrazonmora == null && other.idrazonmora != null) || (this.idrazonmora != null && !this.idrazonmora.equals(other.idrazonmora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
    
}

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
@Table(name = "tbl_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCliente.findAll", query = "SELECT t FROM TblCliente t"),
    @NamedQuery(name = "TblCliente.findByIdCliente", query = "SELECT t FROM TblCliente t WHERE t.idCliente = :idCliente"),
    @NamedQuery(name = "TblCliente.findByNombre", query = "SELECT t FROM TblCliente t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblCliente.findByCodigo", query = "SELECT t FROM TblCliente t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TblCliente.findByEstado", query = "SELECT t FROM TblCliente t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblCliente.findByUsuarioingreso", query = "SELECT t FROM TblCliente t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblCliente.findByFechaingreso", query = "SELECT t FROM TblCliente t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblCliente.findByUsuariomodifico", query = "SELECT t FROM TblCliente t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblCliente.findByFechamodifico", query = "SELECT t FROM TblCliente t WHERE t.fechamodifico = :fechamodifico")})
public class TblCliente implements Serializable {

    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 50)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente", fetch = FetchType.LAZY)
    private List<TblCartera> tblCarteraList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaingreso;
    @Column(name = "fechamodifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodifico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente", fetch = FetchType.LAZY)
    private List<TblPrefijoSalida> tblPrefijoSalidaList;

    public TblCliente() {
    }

    public TblCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    @XmlTransient
    public List<TblPrefijoSalida> getTblPrefijoSalidaList() {
        return tblPrefijoSalidaList;
    }

    public void setTblPrefijoSalidaList(List<TblPrefijoSalida> tblPrefijoSalidaList) {
        this.tblPrefijoSalidaList = tblPrefijoSalidaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCliente)) {
            return false;
        }
        TblCliente other = (TblCliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCliente[ idCliente=" + idCliente + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public List<TblCartera> getTblCarteraList() {
        return tblCarteraList;
    }

    public void setTblCarteraList(List<TblCartera> tblCarteraList) {
        this.tblCarteraList = tblCarteraList;
    }
    
}

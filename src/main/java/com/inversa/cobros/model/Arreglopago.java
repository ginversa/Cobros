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
@Table(name = "arreglopago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Arreglopago.findAll", query = "SELECT a FROM Arreglopago a"),
    @NamedQuery(name = "Arreglopago.findByIdarreglopago", query = "SELECT a FROM Arreglopago a WHERE a.idarreglopago = :idarreglopago"),
    @NamedQuery(name = "Arreglopago.findByCodigo", query = "SELECT a FROM Arreglopago a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Arreglopago.findByNombre", query = "SELECT a FROM Arreglopago a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Arreglopago.findByDescripcion", query = "SELECT a FROM Arreglopago a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Arreglopago.findByUsuarioingreso", query = "SELECT a FROM Arreglopago a WHERE a.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Arreglopago.findByFechaingreso", query = "SELECT a FROM Arreglopago a WHERE a.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Arreglopago.findByUsuariomodifico", query = "SELECT a FROM Arreglopago a WHERE a.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Arreglopago.findByFechamodifico", query = "SELECT a FROM Arreglopago a WHERE a.fechamodifico = :fechamodifico")})
public class Arreglopago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarreglopago")
    private Integer idarreglopago;
    @Size(max = 3)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(mappedBy = "idarreglopago", fetch = FetchType.LAZY)
    private List<TblPromesa> tblPromesaList;

    public Arreglopago() {
    }

    public Arreglopago(Integer idarreglopago) {
        this.idarreglopago = idarreglopago;
    }

    public Integer getIdarreglopago() {
        return idarreglopago;
    }

    public void setIdarreglopago(Integer idarreglopago) {
        this.idarreglopago = idarreglopago;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idarreglopago != null ? idarreglopago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Arreglopago)) {
            return false;
        }
        Arreglopago other = (Arreglopago) object;
        if ((this.idarreglopago == null && other.idarreglopago != null) || (this.idarreglopago != null && !this.idarreglopago.equals(other.idarreglopago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
}

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
@Table(name = "estadopromesa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadopromesa.findAll", query = "SELECT e FROM Estadopromesa e"),
    @NamedQuery(name = "Estadopromesa.findByIdestadopromesa", query = "SELECT e FROM Estadopromesa e WHERE e.idestadopromesa = :idestadopromesa"),
    @NamedQuery(name = "Estadopromesa.findByCodigo", query = "SELECT e FROM Estadopromesa e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Estadopromesa.findByNombre", query = "SELECT e FROM Estadopromesa e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estadopromesa.findByDescripcion", query = "SELECT e FROM Estadopromesa e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Estadopromesa.findByUsuarioingreso", query = "SELECT e FROM Estadopromesa e WHERE e.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "Estadopromesa.findByFechaingreso", query = "SELECT e FROM Estadopromesa e WHERE e.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Estadopromesa.findByUsuariomodifico", query = "SELECT e FROM Estadopromesa e WHERE e.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "Estadopromesa.findByFechamodifico", query = "SELECT e FROM Estadopromesa e WHERE e.fechamodifico = :fechamodifico")})
public class Estadopromesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestadopromesa")
    private Integer idestadopromesa;
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
    @OneToMany(mappedBy = "idestadopromesa", fetch = FetchType.LAZY)
    private List<TblPromesa> tblPromesaList;

    public Estadopromesa() {
    }

    public Estadopromesa(Integer idestadopromesa) {
        this.idestadopromesa = idestadopromesa;
    }

    public Integer getIdestadopromesa() {
        return idestadopromesa;
    }

    public void setIdestadopromesa(Integer idestadopromesa) {
        this.idestadopromesa = idestadopromesa;
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
        hash += (idestadopromesa != null ? idestadopromesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadopromesa)) {
            return false;
        }
        Estadopromesa other = (Estadopromesa) object;
        if ((this.idestadopromesa == null && other.idestadopromesa != null) || (this.idestadopromesa != null && !this.idestadopromesa.equals(other.idestadopromesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
}

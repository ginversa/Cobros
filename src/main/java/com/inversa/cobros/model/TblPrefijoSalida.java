/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_prefijo_salida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPrefijoSalida.findAll", query = "SELECT t FROM TblPrefijoSalida t"),
    @NamedQuery(name = "TblPrefijoSalida.findById", query = "SELECT t FROM TblPrefijoSalida t WHERE t.id = :id"),
    @NamedQuery(name = "TblPrefijoSalida.findByPrefijo", query = "SELECT t FROM TblPrefijoSalida t WHERE t.prefijo = :prefijo"),
    @NamedQuery(name = "TblPrefijoSalida.findByNombre", query = "SELECT t FROM TblPrefijoSalida t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblPrefijoSalida.findByDescripcion", query = "SELECT t FROM TblPrefijoSalida t WHERE t.descripcion = :descripcion")})
public class TblPrefijoSalida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "prefijo")
    private String prefijo;
    @Size(max = 20)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 60)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSalida", fetch = FetchType.EAGER)
    private List<TblClientePrefijo> tblClientePrefijoList;

    public TblPrefijoSalida() {
    }

    public TblPrefijoSalida(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
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

    @XmlTransient
    public List<TblClientePrefijo> getTblClientePrefijoList() {
        return tblClientePrefijoList;
    }

    public void setTblClientePrefijoList(List<TblClientePrefijo> tblClientePrefijoList) {
        this.tblClientePrefijoList = tblClientePrefijoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPrefijoSalida)) {
            return false;
        }
        TblPrefijoSalida other = (TblPrefijoSalida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblPrefijoSalida[ id=" + id + " ]";
    }
    
}

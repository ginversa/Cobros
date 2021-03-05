/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    
    @JoinColumns({
        @JoinColumn(name = "id_central", referencedColumnName = "id"),
        @JoinColumn(name = "id_central", referencedColumnName = "id")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCentral tblCentral;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblCliente idCliente;

    public TblPrefijoSalida() {
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

    public TblCentral getTblCentral() {
        return tblCentral;
    }

    public void setTblCentral(TblCentral tblCentral) {
        this.tblCentral = tblCentral;
    }

    public TblCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TblCliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TblPrefijoSalida other = (TblPrefijoSalida) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TblPrefijoSalida{" + "id=" + id + ", prefijo=" + prefijo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tblCentral=" + tblCentral + ", idCliente=" + idCliente + '}';
    }  
    
}
